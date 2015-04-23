/**
 * @author Benny D�nz
 * 15.04.2009, 18:40:19
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;

import properties.clsProperties;
import utils.clsUniqueIdGenerator;
import body.clsComplexBody;
import body.itfget.itfGetBody;

import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.itfInternalActionProcessor;
import complexbody.io.sensors.datatypes.enums.eCallPriority;

import datatypes.clsMutableDouble;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsMobile;
import entities.enums.eBodyParts;
import entities.enums.eExecutionResult;

/**
 * The action processor provides functions to control the execution of commands
 * 
 * @author Benny D�nz
 * 15.04.2009, 18:40:19
 * 
 */
public class clsInternalActionProcessor implements itfInternalActionProcessor {
	
	 protected final Logger log = logger.clsLogger.getLog("InternalActionProcessor");
	 clsEntity moEntity;
	 HashMap<String, clsInternalActionExecutor> moMap = new HashMap<String, clsInternalActionExecutor>();
	 ArrayList<Class<?>> moDisabledInternalCommands=new ArrayList<Class<?>> ();
	 ArrayList<clsInternalProcessorInhibition> moInhibitedInternalCommands=new ArrayList<clsInternalProcessorInhibition> ();
	 ArrayList<clsInternalProcessorCall> moInternalCommandStack=new ArrayList<clsInternalProcessorCall>();
	 ArrayList<clsInternalProcessorResult> moInternalExecutionHistory=new ArrayList<clsInternalProcessorResult>();
	 
	 protected double srEnergyRelation = 0.01;		//Relation energy to stamina
	 static double srInventoryStaminaDemand = 0.25f;		//Stamina demand for a full inventory  
	 static double srInventoryEnergyRelation = 0.2f;		//Relation energy to stamina

	 static int snExecutionHistoryOutputLength = 10;

	 private static final int mnUniqueId = clsUniqueIdGenerator.getUniqueId();

	 public String getName() {
		return "Internal Action Processor";
	 }
	 public long getUniqueId() {
		return mnUniqueId;
	 }
	 
	 public clsInternalActionProcessor(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		 moEntity=poEntity;	 
	 }

	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();

		return oProp;
	}

	 /*
	  * Function for resetting the processor and clearing the action stack
	  */
	 public void clear()  {
		 moInhibitedInternalCommands=new ArrayList<clsInternalProcessorInhibition> ();
		 moInternalCommandStack=new ArrayList<clsInternalProcessorCall>();
		 moInternalExecutionHistory=new ArrayList<clsInternalProcessorResult>();
	 }
	 
	 /*
	  * Adds the given command to the disabled-list. If this type of command is 
	  * called an exception will be thrown immediately at the instance of calling.
	  */
	 public void disableCommand(Class<?> poCommand)  {
		 if (moDisabledInternalCommands.contains(poCommand)) return;
		 moDisabledInternalCommands.add(poCommand);
	 }

	 /*
	  * Removes the given command from the disabled-list
	  */
	 public void enableCommand(Class<?> poCommand) {
		 if (moDisabledInternalCommands.contains(poCommand)) moDisabledInternalCommands.remove(poCommand);
	 }

	 /*
	  * Add a command/executor pair
	  */
  	 public void addCommand(Class<?> poCommand, clsInternalActionExecutor poExecutor) {
  		 moMap.put(poCommand.getName(), poExecutor);
 	 }
	 
	 /*
	  * Adds the given command to the inhibition-list. The given command will be inhibited for the number of cycles given as duration.
	  */
	 @Override
	public void inhibitCommand(Class<?> poCommand, int pnDuration) {
		 moInhibitedInternalCommands.add(new clsInternalProcessorInhibition(poCommand,pnDuration));	 
	 }
	 
	/*
	 * Adds a Command to the stack. 
	 * The duration can be set to any number greater or equal to one and defines the 
	 * number of simulation cycles the command should be executed for.
	 */
	@Override
	public void call(clsInternalActionCommand poCommand) {
		call(poCommand, eCallPriority.CALLPRIORITY_NORMAL);
	}
	@Override
	public void call(clsInternalActionCommand poCommand, eCallPriority pePriority) {
		//Explicit exception was disabled because having to use try/catch blocks when calling is annoying...
		//if (moDisabledCommands.contains(poCommand.getClass())) throw (new exCommandDisabled());
		//msLogXML += poCommand.getLog();
		moInternalCommandStack.add(new clsInternalProcessorCall(poCommand,pePriority));		
	}
	
	/*
	 *Returns an array of ActionCommands from the last round. 
	 *The parameter �result� can be used to filter by result(Default=Executed) 
	 */
	public ArrayList<clsInternalActionCommand> getExecutionHistory() {
		return getExecutionHistory(eExecutionResult.EXECUTIONRESULT_EXECUTED);
	}
	public ArrayList<clsInternalActionCommand> getExecutionHistory(eExecutionResult peResult) {
		ArrayList<clsInternalActionCommand> oReturnList = new ArrayList<clsInternalActionCommand>();

		Iterator<clsInternalProcessorResult> oItSeq = moInternalExecutionHistory.iterator();
		while (oItSeq.hasNext()) {
			clsInternalProcessorResult oPCall = oItSeq.next();
			if (peResult==eExecutionResult.EXECUTIONRESULT_ANY ||
			   (peResult==eExecutionResult.EXECUTIONRESULT_NOTEXECUTED && oPCall.getResult()!=eExecutionResult.EXECUTIONRESULT_EXECUTED) ||
			   peResult==oPCall.getResult())  {
				oReturnList.add(oPCall.getCommand());
			}
		}
		
		return oReturnList;
	}
	
	/*
	 * Returns an array of ActionCommands which are scheduled for dispatch in the current round
	 */
	public ArrayList<clsInternalActionCommand> getCommandStack() {
		ArrayList<clsInternalActionCommand> oReturnList = new ArrayList<clsInternalActionCommand>();

		Iterator<clsInternalProcessorCall> oItStck = moInternalCommandStack.iterator();
		while (oItStck.hasNext()) {
			clsInternalProcessorCall oPCall = oItStck.next();
			oReturnList.addAll(oPCall.getCommands());
		}
		
		return oReturnList;
	}
		
	
	
	/*
	 *Start Dispatch Phase 
	 */
	public void dispatch() {

		//Create the execution-stack
		ArrayList<clsInternalProcessorResult> oExecutionStack=CreateExecutionStack();		

		//1. Delete disabled commands
		disableCommands(oExecutionStack);
		
		//2.	Delete inhibited commands
		inhibitCommands(oExecutionStack);
	
		//3.	Check binding states: Consume energy and stamina for keeping objects in the inventory
		ConsumeBindingEnergy();
		
		//4.	Check energy and stamina levels: Consume energy and stamina for all remaining actions in order of priority and calling. If the energy and stamina levels are not sufficient for all the remaining commands no command (except priority �update state�) will be executed but energy and stamina levels will still be reduced down to the total minimum.
		//ConsumeEnergy(oExecutionStack); Consuming energy at this point gives an exeption, todo
		
		//5.	Check for mutual exclusions
		mutexCommands(oExecutionStack);
		
		//6.	Dispatch
		dispatchCommands(oExecutionStack);
		
		//7.	Clean-up
		dispatchFinalize(oExecutionStack);
		
		moInternalExecutionHistory=oExecutionStack;
		
		if (moInternalExecutionHistory.isEmpty()==false) {
			if (moInternalExecutionHistory.get(0).meResult.toString().equals("EXECUTIONRESULT_EXECUTED")==false) {
				//Kollmann: writing the whole execution history floods the console, we need to restrict the output
				int nTempSize = moInternalExecutionHistory.size();
				//System.out.println(moInternalExecutionHistory.subList(Math.max(nTempSize - snExecutionHistoryOutputLength, 0), nTempSize));
				log.info(moInternalExecutionHistory.subList(Math.max(nTempSize - snExecutionHistoryOutputLength, 0), nTempSize).toString());
			}
		}
	}

	/*
	 * Creates a stack of executors for the commands dispatched
	 */
	private ArrayList<clsInternalProcessorResult> CreateExecutionStack() {
		ArrayList<clsInternalProcessorResult> oExecutionStack=new ArrayList<clsInternalProcessorResult>();
		Iterator<clsInternalProcessorCall> oItStck = moInternalCommandStack.iterator();
		while (oItStck.hasNext()) {
			clsInternalProcessorCall oPCall = oItStck.next();
			Iterator<clsInternalActionCommand> oItCmd = oPCall.getCommands().iterator();
			while (oItCmd.hasNext()) {
				clsInternalActionCommand oCmd = oItCmd.next();
				oExecutionStack.add(new clsInternalProcessorResult(oPCall, oCmd, moMap.get(oCmd.getClass().getName()) ));
			}
		}
		return oExecutionStack;
	}
	
	/*
	 *Delete disabled commands: Disabled commands are deleted from the stack. 
	 *There are no consequences other than that the command is not dispatched 
	 *(This can only happen if a command was disabled after calling it, otherwise 
	 *an exception would have been thrown) 
	 */
	private void disableCommands(ArrayList<clsInternalProcessorResult> opExecutionStack) {
		Iterator<clsInternalProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsInternalProcessorResult oExRes = oItStck.next();
			//Disable if contained in disabled-list 
			if (oExRes.getActive() && moDisabledInternalCommands.contains(oExRes.getCommand().getClass())) {
				oExRes.setResult(eExecutionResult.EXECUTIONRESULT_DISABLED);
			}
		}
	}
	
	/*
	 *Delete inhibited commands: Inhibited commands are deleted from the stack 
	 *(except priority �update state�). There are no consequences other than that 
	 *the command is not dispatched 
	 */
	private void inhibitCommands(ArrayList<clsInternalProcessorResult> opExecutionStack) {
		Iterator<clsInternalProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsInternalProcessorResult oExRes = oItStck.next();
			//Disable if contained in inhibition-list and if it's not a StateUpdate
			if (oExRes.getActive() && oExRes.getCall().getCallPriority()!=eCallPriority.CALLPRIORITY_STATEUPDATE) {
				Iterator<clsInternalProcessorInhibition> oItInh = moInhibitedInternalCommands.iterator();
				while (oItInh.hasNext()) {
					clsInternalProcessorInhibition oInh = oItInh.next();
					if (oInh.getCommand() == oExRes.getCommand().getClass()) oExRes.setResult(eExecutionResult.EXECUTIONRESULT_INHIBITED);
				}				
			}
		}
	}
	
	/*
	 * Consume stamina and Energy for keeping objects in the inventory => Only Stamina will be checked for >=0, if energy goes below zero... RIP
	 */
	private void ConsumeBindingEnergy() {
		if (moEntity==null) return;

		if (!(moEntity instanceof clsMobile)) return;
		clsMobile oMEntity = (clsMobile) moEntity;
		
		if (oMEntity.getInventory()==null) return;
		if (oMEntity.getInventory().getMaxMass()==0 ) return;
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//get values
		double rStaminaDemand = oMEntity.getInventory().getMass() / oMEntity.getInventory().getMaxMass() * srInventoryStaminaDemand ;
		double rEnergyDemand = rStaminaDemand * srInventoryEnergyRelation;
		double rStaminaAvailable = oBody.getInternalSystem().getStaminaSystem().getStamina().getContent();
		
		//If not enough stamina then consume what's left
		if (rStaminaAvailable<rStaminaDemand && rStaminaDemand!=0) {
			rEnergyDemand = rEnergyDemand * rStaminaAvailable/rStaminaDemand;
			rStaminaDemand =rStaminaAvailable;
		}

		//Consume
		if (rStaminaDemand>0) oBody.getInternalSystem().getStaminaSystem().consumeStamina(rStaminaDemand);
		if (rEnergyDemand>0) oBody.getInternalEnergyConsumption().setValueOnce(eBodyParts.ACTIONEX_BINDING, new clsMutableDouble(rEnergyDemand));		
		
	}

	/*
	 * Consume stamina and Energy => Only Stamina will be checked for >=0, if energy goes below zero... RIP
	 */
	private void ConsumeEnergy(ArrayList<clsInternalProcessorResult> opExecutionStack) {
		if (moEntity==null) return; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		Iterator<clsInternalProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsInternalProcessorResult oExRes = oItStck.next();
			if (oExRes.getActive() ) {

				//get values
				double rEnergyDemand = oExRes.getExecutor().getEnergyDemand(oExRes.getCommand());
				double rStaminaDemand = oExRes.getExecutor().getStaminaDemand(oExRes.getCommand());
				double rStaminaAvailable = oBody.getInternalSystem().getStaminaSystem().getStamina().getContent();
				
				//If not enough stamina then consume what's left, reduce energy but no execution
				if (rStaminaAvailable<rStaminaDemand && rStaminaDemand!=0) {
					rEnergyDemand = rEnergyDemand * rStaminaAvailable/rStaminaDemand;
					rStaminaDemand =rStaminaAvailable;
					oExRes.setResult(eExecutionResult.EXECUTIONRESULT_NOSTAMINA );
				}
				
				//Consume
				if (rStaminaDemand>0) oBody.getInternalSystem().getStaminaSystem().consumeStamina(rStaminaDemand);
				if (rEnergyDemand>0) oBody.getInternalEnergyConsumption().setValueOnce(oExRes.getExecutor().getBodyPartId(), new clsMutableDouble(rEnergyDemand));
				
			}
		}
	}
	
	/*
	 *Check for mutual exclusions: If a mutual exclusion exists, then the command 
	 *with the highest priority is selected and all others are deleted. If more 
	 *than one command with the same priority is left, then all commands are discarded. 
	 *There are no consequences other than that the command is not dispatched and the 
	 *energy was consumed. 
	 */
	private void mutexCommands(ArrayList<clsInternalProcessorResult> opExecutionStack) {
		
		//double loop to check every combination of every two commands
		Iterator<clsInternalProcessorResult> oItOuter = opExecutionStack.iterator();
		while (oItOuter.hasNext()) {
			clsInternalProcessorResult oOuter = oItOuter.next();
			Iterator<clsInternalProcessorResult> oItInner = opExecutionStack.iterator();
			while (oItInner.hasNext()) {
				clsInternalProcessorResult oInner = oItInner.next();
				//both commands are active 
				if (oOuter != oInner && oOuter.getActive() && oInner.getActive()) {
					//inner command is excluded by the outer command or inner and outer command are the same
					if (oOuter.getExecutor().getMutualExclusions(oOuter.getCommand()).contains(oInner.getCommand().getClass())) {
						//different priorities=>Disable lower, equal priorities=>Disable both (Never disable State-Updates!)
						if (oOuter.getCall().getCallPriority()==oInner.getCall().getCallPriority()) {
							if (oOuter.getCall().getCallPriority()!=eCallPriority.CALLPRIORITY_STATEUPDATE) oOuter.setResult(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION);
							if (oInner.getCall().getCallPriority()!=eCallPriority.CALLPRIORITY_STATEUPDATE) oInner.setResult(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION);
						} else {
							if ((oOuter.getCall().getCallPriority()==eCallPriority.CALLPRIORITY_STATEUPDATE || oOuter.getCall().getCallPriority()==eCallPriority.CALLPRIORITY_REFLEX) && oInner.getCall().getCallPriority()!=eCallPriority.CALLPRIORITY_STATEUPDATE) oInner.setResult(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION);
							if ((oInner.getCall().getCallPriority()==eCallPriority.CALLPRIORITY_STATEUPDATE || oInner.getCall().getCallPriority()==eCallPriority.CALLPRIORITY_REFLEX) && oOuter.getCall().getCallPriority()!=eCallPriority.CALLPRIORITY_STATEUPDATE) oOuter.setResult(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION);
						}
					}
				}
			}
		}
	}

	/*
	 * Dispatch: All remaining commands are dispatched in order of priority and calling. 
	 * The execute function of the command checks for constraint violations of the specific 
	 * command before execution. The execution result for each command is logged in the execution 
	 * history.
	 */
	private void dispatchCommands(ArrayList<clsInternalProcessorResult> opExecutionStack) {
		Iterator<clsInternalProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsInternalProcessorResult oExRes = oItStck.next();
			
			// dirty Andi, Clemens hack
			// if you want hare and lynx to work, comment the next if-clause
			if (oExRes.getActive()) {				
				if (oExRes.getExecutor().execute(oExRes.getCommand())) {
					oExRes.setResult(eExecutionResult.EXECUTIONRESULT_EXECUTED);
				} else {
					oExRes.setResult(eExecutionResult.EXECUTIONRESULT_CONSTRAINTVIOLATION);
				}
			}
		}
	}

	/*
	 *Clean-up: Advance rounds, remove completed sequences from the stack, etc. 
	 */
	private void dispatchFinalize(ArrayList<clsInternalProcessorResult> opExecutionStack) {
		//Advance sequences and remove completed or interrupted ones
		for (int i=(moInternalCommandStack.size()-1);i>=0;i--) {
			clsInternalProcessorCall oPCall = moInternalCommandStack.get(i);
			oPCall.advanceRound();
			if (oPCall.getActive()==false) moInternalCommandStack.remove(oPCall);
		}
		
		//Advance inhibitions and remove completed ones
		for (int i=(moInhibitedInternalCommands.size()-1);i>=0;i--) {
			clsInternalProcessorInhibition oPInh = moInhibitedInternalCommands.get(i);
			oPInh.advanceRound();
			if (oPInh.getActive()==false) moInhibitedInternalCommands.remove(oPInh);
		}
		
		//Clear log
		//msLogXML="";
	}
	 	 

 	/*
	  * Private class for storing execution details for a sequence or command
	  */
	 private class clsInternalProcessorCall {
		 
		 clsInternalActionCommand moCommand;
		 eCallPriority meCallPriority;
		 int mnRound = 0;
		 boolean mbDispose = false;
		 
		 public clsInternalProcessorCall(clsInternalActionCommand poCommand,eCallPriority peCallPriority) {
			 moCommand=poCommand;
			 meCallPriority=peCallPriority;
		 }

		 public ArrayList<clsInternalActionCommand> getCommands() {
			 return moCommand.getCommands(mnRound);
		 }
		 
		 public void dispose() {
			mbDispose=true; 
		 }
		 
		 public void advanceRound() {
			 mnRound++;
		 }
		 
		 public eCallPriority getCallPriority() {
			 return meCallPriority;
		 }
		 
		 //Returns if the sequence is still active (not disposed and contains commands)
		 public boolean getActive() {
			 if (mbDispose) {
				 return false;
			 }
			 if (moCommand.isComplete(mnRound)) {
				 return false;
			 }
			 return true;
		 }		 		 
	 }
	 
	 /*
	  * Private class for storing inhibitions
	  */
	 private class clsInternalProcessorInhibition {
		 Class<?> moCommand;
		 int mnDuration;
		 int mnRound = 0;
		 
		 public clsInternalProcessorInhibition(Class<?> poCommand,int pnDuration) {
			 moCommand=poCommand;
			 mnDuration=pnDuration;
		 }
		 
		 public Class<?>getCommand() {
			 return moCommand;
		 }
		 
		 public void advanceRound() {
			 mnRound++;
		 }

		 //Returns if the inhibition is still active
		 public boolean getActive() {
			 if (mnDuration<=(mnRound+1)) return false;
			 return true;
		 }
	 }
	 
	 /*
	  * Private class for storing executed commands and their result
	  */
	 private class clsInternalProcessorResult {
		 private clsInternalActionCommand moCommand;
		 private clsInternalActionExecutor moExecutor;
		 private eExecutionResult meResult = eExecutionResult.EXECUTIONRESULT_NOTEXECUTED;
		 private clsInternalProcessorCall moCall;
		 
		 public clsInternalProcessorResult(clsInternalProcessorCall poCall, clsInternalActionCommand poCommand, clsInternalActionExecutor poExecutor) {
			 moExecutor=poExecutor;
			 moCommand=poCommand;
			 moCall=poCall;
		 }

		 //Set result and disable call on failure
		 public void setResult(eExecutionResult peResult) {
			 meResult=peResult;
			 if (meResult != eExecutionResult.EXECUTIONRESULT_EXECUTED) moCall.dispose();
		 }

		 public eExecutionResult getResult() {
			return meResult;
		 }
		 
		 public clsInternalActionCommand getCommand() {
			 return moCommand;
		 }

		 public clsInternalActionExecutor getExecutor() {
			 return moExecutor;
		 }

		 public clsInternalProcessorCall getCall() {
			 return moCall;
		 }
		 
		 //Returns if the command is still active (no response was set and sequence is active)
		 public boolean getActive() {
			 if (moCall.getActive() && meResult == eExecutionResult.EXECUTIONRESULT_NOTEXECUTED) return true;
			 return false;
		 }
		 
		 @Override
		public String toString() {
			
			return "Command: " + this.getCommand() + " | Executor: " + this.moExecutor + " | Result: " + this.getResult(); 
		}
	 }


 
	 
}
