/**
 * @author Benny Dönz
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
import bw.factories.clsSingletonUniqueIdGenerator;
import decisionunit.itf.actions.*;
import enums.eCallPriority;
import bw.entities.clsEntity;
import bw.utils.enums.*;
import bw.exceptions.*;

/**
 * The action processor provides functions to control the execution of commands
 * 
 * @author Benny Dönz
 * 15.04.2009, 18:40:19
 * 
 */
public class clsActionProcessor implements itfActionProcessor {
	
	 clsEntity moEntity;
	 HashMap<String, clsActionExecutor> moMap = new HashMap<String, clsActionExecutor>();
	 ArrayList<Class<itfActionCommand>> moDisabledCommands=new ArrayList<Class<itfActionCommand>> ();
	 ArrayList<clsProcessorInhibition> moInhibitedCommands=new ArrayList<clsProcessorInhibition> ();
	 ArrayList<clsProcessorCall> moCommandStack=new ArrayList<clsProcessorCall>();
	 ArrayList<clsProcessorResult> moExecutionHistory=new ArrayList<clsProcessorResult>();

	 private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();

	 public String getName() {
		return "Action processor";
	 }
	 public long getUniqueId() {
		return mnUniqueId;
	 }
	 
	 public clsActionProcessor(clsEntity poEntity) {
		 moEntity=poEntity;	 
	 }
	 
	 /*
	  * Function for resetting the processor and clearing the action stack
	  */
	 public void clear() {
		 moInhibitedCommands=new ArrayList<clsProcessorInhibition> ();
		 moCommandStack=new ArrayList<clsProcessorCall>();
		 moExecutionHistory=new ArrayList<clsProcessorResult>();
	 }
	 
	 /*
	  * Adds the given command to the disabled-list. If this type of command is 
	  * called an exception will be thrown immediately at the instance of calling.
	  */
	 public void disableCommand(Class poCommand)  {
		 if (moDisabledCommands.contains(poCommand)) return;
		 moDisabledCommands.add(poCommand);
	 }

	 /*
	  * Removes the given command from the disabled-list
	  */
	 public void enableCommand(Class poCommand) {
		 if (moDisabledCommands.contains(poCommand)) moDisabledCommands.remove(poCommand);
	 }

	 /*
	  * Add a command/executor pair
	  */
  	 public void addCommand(Class poCommand, clsActionExecutor poExecutor) {
  		 moMap.put(poCommand.getName(), poExecutor);
	 }
	 
	 /*
	  * Adds the given command to the inhibition-list. The given command will be inhibited for the number of cycles given as duration.
	  */
	 public void inhibitCommand(Class poCommand, int pnDuration) {
		 moInhibitedCommands.add(new clsProcessorInhibition(poCommand,pnDuration));	 
	 }
	 
	/*
	 * Adds a Command to the stack. 
	 * The duration can be set to any number greater or equal to one and defines the 
	 * number of simulation cycles the command should be executed for.
	 */
	public void call(itfActionCommand poCommand) {
		call(poCommand, eCallPriority.CALLPRIORITY_NORMAL,1);
	}
	public void call(itfActionCommand poCommand, eCallPriority pePriority) {
		call(poCommand, pePriority,1);
	}
	public void call(itfActionCommand poCommand, eCallPriority pePriority, int pnDuration) {
		//Explicit exception was disabled because having to use try/catch blocks when calling is annoying...
		//if (moDisabledCommands.contains(poCommand.getClass())) throw (new exCommandDisabled());
		moCommandStack.add(new clsProcessorCall(poCommand,pePriority,pnDuration));
	}
	
	/*
	 * Adds Sequences to the stack.
	 */
	public void call(clsActionSequence poSequence) {
		call(poSequence,eCallPriority.CALLPRIORITY_NORMAL);
	}
	public void call(clsActionSequence poSequence, eCallPriority pePriority) {
		moCommandStack.add(new clsProcessorCall(poSequence,pePriority));
	}
	
	/*
	 *Returns an array of ActionCommands from the last round. 
	 *The parameter “result” can be used to filter by result(Default=Executed) 
	 */
	public ArrayList<itfActionCommand> getExecutionHistory() {
		return getExecutionHistory(eExecutionResult.EXECUTIONRESULT_EXECUTED);
	}
	public ArrayList<itfActionCommand> getExecutionHistory(eExecutionResult peResult) {
		ArrayList<itfActionCommand> oReturnList = new ArrayList<itfActionCommand>();

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
	public ArrayList<itfActionCommand> getCommandStack() {
		ArrayList<itfActionCommand> oReturnList = new ArrayList<itfActionCommand>();

		Iterator<clsProcessorCall> oItStck = moCommandStack.iterator();
		while (oItStck.hasNext()) {
			clsProcessorCall oPCall = oItStck.next();
			oReturnList.addAll(oPCall.getCommands());
		}
		
		return oReturnList;
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
		
		//3.	Check binding states: Consume energy and stamina when carrying objects. If the energy or stamina – levels are not sufficient the objects will be dropped automatically.
		//TODO BD: Will be done when implementing the binding functions
		
		//4.	Check energy and stamina levels: Consume energy and stamina for all remaining actions in order of priority and calling. If the energy and stamina levels are not sufficient for all the remaining commands no command (except priority “update state”) will be executed but energy and stamina levels will still be reduced down to the total minimum.
		//TODO BD: Will be done when implementing the first "real" actions
		
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
			Iterator<itfActionCommand> oItCmd = oPCall.getCommands().iterator();
			while (oItCmd.hasNext()) {
				itfActionCommand oCmd = oItCmd.next();
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
	 *(except priority “update state”). There are no consequences other than that 
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
				//both commands are active and inner command is excluded by the outer command
				if (oOuter.getActive() && oInner.getActive() && oOuter.getExecutor().getMutualExclusions(oOuter.getCommand()).contains(oInner.getCommand().getClass())) {
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
			if (oExRes.getActive()) {
				if (oExRes.getExecutor().execute(oExRes.getCommand(), moEntity)) {
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
		
	}
	 	 

 	/*
	  * Private class for storing execution details for a sequence or command
	  */
	 private class clsProcessorCall {
		 
		 clsActionSequence moSequence;
		 eCallPriority meCallPriority;
		 int mnRound = 0;
		 boolean mbDispose = false;
		 
		 public clsProcessorCall(clsActionSequence poSequence,eCallPriority peCallPriority) {
			 moSequence=poSequence;
			 meCallPriority=peCallPriority;
		 }

		 public clsProcessorCall(itfActionCommand poCommand,eCallPriority peCallPriority, int pnDuration) {
			 moSequence=new clsActionSequence();
			 moSequence.add(0,poCommand,pnDuration);
			 meCallPriority=peCallPriority;
		 }

		 public ArrayList<itfActionCommand> getCommands() {
			 return moSequence.getCommands(mnRound);
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
			 if (mbDispose) return false;
			 if (moSequence.getRounds()<(mnRound+1)) return false;
			 return true;
		 }		 		 
	 }
	 
	 /*
	  * Private class for storing inhibitions
	  */
	 private class clsProcessorInhibition {
		 Class<itfActionCommand> moCommand;
		 int mnDuration;
		 int mnRound = 0;
		 
		 public clsProcessorInhibition(Class<itfActionCommand> poCommand,int pnDuration) {
			 moCommand=poCommand;
			 mnDuration=pnDuration;
		 }
		 
		 public Class<itfActionCommand>getCommand() {
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
		 private itfActionCommand moCommand;
		 private clsActionExecutor moExecutor;
		 private eExecutionResult meResult = eExecutionResult.EXECUTIONRESULT_NOTEXECUTED;
		 private clsProcessorCall moCall;
		 
		 public clsProcessorResult(clsProcessorCall poCall, itfActionCommand poCommand, clsActionExecutor poExecutor) {
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
		 
		 public itfActionCommand getCommand() {
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
