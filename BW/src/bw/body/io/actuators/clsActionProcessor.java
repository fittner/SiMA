/**
 * @author Benny D�nz
 * 15.04.2009, 18:40:19
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.clsBWProperties;

import statictools.clsSingletonUniqueIdGenerator;
import du.enums.eCallPriority;
import du.itf.actions.*;
import bw.body.clsComplexBody;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.*;

/**
 * The action processor provides functions to control the execution of commands
 * 
 * @author Benny D�nz
 * 15.04.2009, 18:40:19
 * 
 */
public class clsActionProcessor implements itfActionProcessor {
	
	 String msLogXML = "";
	 clsEntity moEntity;
	 HashMap<String, clsActionExecutor> moMap = new HashMap<String, clsActionExecutor>();
	 ArrayList<Class<?>> moDisabledCommands=new ArrayList<Class<?>> ();
	 ArrayList<clsProcessorInhibition> moInhibitedCommands=new ArrayList<clsProcessorInhibition> ();
	 ArrayList<clsProcessorCall> moCommandStack=new ArrayList<clsProcessorCall>();
	 ArrayList<clsProcessorResult> moExecutionHistory=new ArrayList<clsProcessorResult>();

	 static double srInventoryStaminaDemand = 0.25f;		//Stamina demand for a full inventory  
	 static double srInventoryEnergyRelation = 0.2f;		//Relation energy to stamina

	 private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();

	 public String getName() {
		return "Action processor";
	 }
	 public long getUniqueId() {
		return mnUniqueId;
	 }
	 
	 public clsActionProcessor(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		 moEntity=poEntity;	 
	 }

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();

		return oProp;
	}

	 /*
	  * Function for resetting the processor and clearing the action stack
	  */
	 public void clear()  {
		 moInhibitedCommands=new ArrayList<clsProcessorInhibition> ();
		 moCommandStack=new ArrayList<clsProcessorCall>();
		 moExecutionHistory=new ArrayList<clsProcessorResult>();
		 msLogXML="";
	 }
	 
	 /*
	  * Adds the given command to the disabled-list. If this type of command is 
	  * called an exception will be thrown immediately at the instance of calling.
	  */
	 public void disableCommand(Class<?> poCommand)  {
		 if (moDisabledCommands.contains(poCommand)) return;
		 moDisabledCommands.add(poCommand);
	 }

	 /*
	  * Removes the given command from the disabled-list
	  */
	 public void enableCommand(Class<?> poCommand) {
		 if (moDisabledCommands.contains(poCommand)) moDisabledCommands.remove(poCommand);
	 }

	 /*
	  * Add a command/executor pair
	  */
  	 public void addCommand(Class<?> poCommand, clsActionExecutor poExecutor) {
  		 moMap.put(poCommand.getName(), poExecutor);
 	 }
	 
	 /*
	  * Adds the given command to the inhibition-list. The given command will be inhibited for the number of cycles given as duration.
	  */
	 @Override
	public void inhibitCommand(Class<?> poCommand, int pnDuration) {
		 moInhibitedCommands.add(new clsProcessorInhibition(poCommand,pnDuration));	 
	 }
	 
	/*
	 * Adds a Command to the stack. 
	 * The duration can be set to any number greater or equal to one and defines the 
	 * number of simulation cycles the command should be executed for.
	 */
	@Override
	public void call(clsActionCommand poCommand) {
		call(poCommand, eCallPriority.CALLPRIORITY_NORMAL);
	}
	@Override
	public void call(clsActionCommand poCommand, eCallPriority pePriority) {
		//Explicit exception was disabled because having to use try/catch blocks when calling is annoying...
		//if (moDisabledCommands.contains(poCommand.getClass())) throw (new exCommandDisabled());
		msLogXML += poCommand.getLog();
		moCommandStack.add(new clsProcessorCall(poCommand,pePriority));		
	}
	
	/*
	 *Returns an array of ActionCommands from the last round. 
	 *The parameter �result� can be used to filter by result(Default=Executed) 
	 */
	public ArrayList<clsActionCommand> getExecutionHistory() {
		return getExecutionHistory(eExecutionResult.EXECUTIONRESULT_EXECUTED);
	}
	public ArrayList<clsActionCommand> getExecutionHistory(eExecutionResult peResult) {
		ArrayList<clsActionCommand> oReturnList = new ArrayList<clsActionCommand>();

		Iterator<clsProcessorResult> oItSeq = moExecutionHistory.iterator();
		while (oItSeq.hasNext()) {
			clsProcessorResult oPCall = oItSeq.next();
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
	public ArrayList<clsActionCommand> getCommandStack() {
		ArrayList<clsActionCommand> oReturnList = new ArrayList<clsActionCommand>();

		Iterator<clsProcessorCall> oItStck = moCommandStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorCall oPCall = oItStck.next();
			oReturnList.addAll(oPCall.getCommands());
		}
		
		return oReturnList;
	}
		
	/*
	 * Returns a XML-log containing the dispatched calls of the current simulation cycle
	 */
	@Override
	public String logXML() {
		return "<Actions>" + msLogXML + "</Actions>";
	}
	
	/*
	 *Start Dispatch Phase 
	 */
	public void dispatch() {

		//Create the execution-stack
		ArrayList<clsProcessorResult> oExecutionStack=CreateExecutionStack();		

		//1. Delete disabled commands
		disableCommands(oExecutionStack);
		
		//2.	Delete inhibited commands
		inhibitCommands(oExecutionStack);
	
		//3.	Check binding states: Consume energy and stamina for keeping objects in the inventory
		ConsumeBindingEnergy();
		
		//4.	Check energy and stamina levels: Consume energy and stamina for all remaining actions in order of priority and calling. If the energy and stamina levels are not sufficient for all the remaining commands no command (except priority �update state�) will be executed but energy and stamina levels will still be reduced down to the total minimum.
		ConsumeEnergy(oExecutionStack);
		
		//5.	Check for mutual exclusions
		mutexCommands(oExecutionStack);
		
		//6.	Dispatch
		dispatchCommands(oExecutionStack);
		
		//7.	Clean-up
		dispatchFinalize(oExecutionStack);
		
		moExecutionHistory=oExecutionStack;
	}

	/*
	 * Creates a stack of executors for the commands dispatched
	 */
	private ArrayList<clsProcessorResult> CreateExecutionStack() {
		ArrayList<clsProcessorResult> oExecutionStack=new ArrayList<clsProcessorResult>();
		Iterator<clsProcessorCall> oItStck = moCommandStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorCall oPCall = oItStck.next();
			Iterator<clsActionCommand> oItCmd = oPCall.getCommands().iterator();
			while (oItCmd.hasNext()) {
				clsActionCommand oCmd = oItCmd.next();
				oExecutionStack.add(new clsProcessorResult(oPCall, oCmd, moMap.get(oCmd.getClass().getName()) ));
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
	private void disableCommands(ArrayList<clsProcessorResult> opExecutionStack) {
		Iterator<clsProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorResult oExRes = oItStck.next();
			//Disable if contained in disabled-list 
			if (oExRes.getActive() && moDisabledCommands.contains(oExRes.getCommand().getClass())) {
				oExRes.setResult(eExecutionResult.EXECUTIONRESULT_DISABLED);
			}
		}
	}
	
	/*
	 *Delete inhibited commands: Inhibited commands are deleted from the stack 
	 *(except priority �update state�). There are no consequences other than that 
	 *the command is not dispatched 
	 */
	private void inhibitCommands(ArrayList<clsProcessorResult> opExecutionStack) {
		Iterator<clsProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorResult oExRes = oItStck.next();
			//Disable if contained in inhibition-list and if it's not a StateUpdate
			if (oExRes.getActive() && oExRes.getCall().getCallPriority()!=eCallPriority.CALLPRIORITY_STATEUPDATE) {
				Iterator<clsProcessorInhibition> oItInh = moInhibitedCommands.iterator();
				while (oItInh.hasNext()) {
					clsProcessorInhibition oInh = oItInh.next();
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
	private void ConsumeEnergy(ArrayList<clsProcessorResult> opExecutionStack) {
		if (moEntity==null) return; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		Iterator<clsProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorResult oExRes = oItStck.next();
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
	private void mutexCommands(ArrayList<clsProcessorResult> opExecutionStack) {
		
		//double loop to check every combination of every two commands
		Iterator<clsProcessorResult> oItOuter = opExecutionStack.iterator();
		while (oItOuter.hasNext()) {
			clsProcessorResult oOuter = oItOuter.next();
			Iterator<clsProcessorResult> oItInner = opExecutionStack.iterator();
			while (oItInner.hasNext()) {
				clsProcessorResult oInner = oItInner.next();
				//both commands are active 
				if (oOuter != oInner && oOuter.getActive() && oInner.getActive()) {
					//inner command is excluded by the outer command or inner and outer command are the same
					if (oInner.getCommand().getClass() == oOuter.getCommand().getClass() || oOuter.getExecutor().getMutualExclusions(oOuter.getCommand()).contains(oInner.getCommand().getClass())) {
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
	private void dispatchCommands(ArrayList<clsProcessorResult> opExecutionStack) {
		Iterator<clsProcessorResult> oItStck = opExecutionStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorResult oExRes = oItStck.next();
			
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
	private void dispatchFinalize(ArrayList<clsProcessorResult> opExecutionStack) {
		//Advance sequences and remove completed or interrupted ones
		for (int i=(moCommandStack.size()-1);i>=0;i--) {
			clsProcessorCall oPCall = moCommandStack.get(i);
			oPCall.advanceRound();
			if (oPCall.getActive()==false) moCommandStack.remove(oPCall);
		}
		
		//Advance inhibitions and remove completed ones
		for (int i=(moInhibitedCommands.size()-1);i>=0;i--) {
			clsProcessorInhibition oPInh = moInhibitedCommands.get(i);
			oPInh.advanceRound();
			if (oPInh.getActive()==false) moInhibitedCommands.remove(oPInh);
		}
		
		//Clear log
		msLogXML="";
	}
	 	 

 	/*
	  * Private class for storing execution details for a sequence or command
	  */
	 private class clsProcessorCall {
		 
		 clsActionCommand moCommand;
		 eCallPriority meCallPriority;
		 int mnRound = 0;
		 boolean mbDispose = false;
		 
		 public clsProcessorCall(clsActionCommand poCommand,eCallPriority peCallPriority) {
			 moCommand=poCommand;
			 meCallPriority=peCallPriority;
		 }

		 public ArrayList<clsActionCommand> getCommands() {
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
	 private class clsProcessorInhibition {
		 Class<?> moCommand;
		 int mnDuration;
		 int mnRound = 0;
		 
		 public clsProcessorInhibition(Class<?> poCommand,int pnDuration) {
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
	 private class clsProcessorResult {
		 private clsActionCommand moCommand;
		 private clsActionExecutor moExecutor;
		 private eExecutionResult meResult = eExecutionResult.EXECUTIONRESULT_NOTEXECUTED;
		 private clsProcessorCall moCall;
		 
		 public clsProcessorResult(clsProcessorCall poCall, clsActionCommand poCommand, clsActionExecutor poExecutor) {
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
		 
		 public clsActionCommand getCommand() {
			 return moCommand;
		 }

		 public clsActionExecutor getExecutor() {
			 return moExecutor;
		 }

		 public clsProcessorCall getCall() {
			 return moCall;
		 }
		 
		 //Returns if the command is still active (no response was set and sequence is active)
		 public boolean getActive() {
			 if (moCall.getActive() && meResult == eExecutionResult.EXECUTIONRESULT_NOTEXECUTED) return true;
			 return false;
		 }
	 }
 
	 
}
