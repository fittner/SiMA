/**
 * @author Benny Dönz
 * 16.04.2009, 10:07:52
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.body.io.actuators;

import static org.junit.Assert.*;
import org.junit.Test;

import decisionunit.itf.actions.*;

import enums.eCallPriority;

import java.util.ArrayList;
import bw.body.io.actuators.*;
import bw.entities.clsEntity;
import bw.utils.enums.eExecutionResult;

/**
 * Test-Cases for action processor
 * 
 * @author Benny Dönz
 * 16.04.2009, 10:07:52
 * 
 */
public class tstActionProcessor {

	/*
	 * Check if mutual exclusions work by dispatching commands and checking if both
	 * are block when they have the same priority and if the hight priority is chosen in
	 * case of differing priority
	 */
	@Test
	public void testExecution_Mutex() {
		clsActionProcessor oAPr;
		
		//Test for mutual exclusion where both are blocked
		oAPr = getActionProcessor();
		tstTestCommand_A oMutex_Err1 = new tstTestCommand_A();
		tstTestCommand_B oMutex_Err2 = new tstTestCommand_B();
		oMutex_Err1.addMutualExclusion(tstTestCommand_B.class);
		oAPr.call(oMutex_Err1, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.call(oMutex_Err2, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.dispatch();
		assertFalse("MutEx: Command 1 was not blocked", oMutex_Err1.getExecuted());
		assertFalse("MutEx: Command 2 was not blocked", oMutex_Err2.getExecuted());
		assertTrue("MutEx: Executionstates are wrong", oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION).contains(oMutex_Err1) && oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION).contains(oMutex_Err2) );
	
		//Test for mutual exclusion where only one is blocked
		oAPr = getActionProcessor();
		tstTestCommand_A oMutex_High = new tstTestCommand_A();
		tstTestCommand_B oMutex_Low = new tstTestCommand_B();
		oMutex_High.addMutualExclusion(tstTestCommand_B.class);
		oAPr.call(oMutex_High, eCallPriority.CALLPRIORITY_REFLEX);
		oAPr.call(oMutex_Low, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.dispatch();
		assertTrue("MutEx(2): Command 1 was not executed", oMutex_High.getExecuted());
		assertFalse("MutEx(2): Command 2 was not blocked", oMutex_Low.getExecuted());
		assertTrue("MutEx(2): Executionstates are wrong", oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_EXECUTED).contains(oMutex_High) && oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_MUTUALEXCLUSION).contains(oMutex_Low) );
		
	}

	/*
	 * Create a processor and register testcommands
	 */
	private clsActionProcessor getActionProcessor() {
		clsActionProcessor oProcessor = new clsActionProcessor(null);
		clsActionExecutor oExecutor = new tstTestExecutor();
		oProcessor.addCommand(tstTestCommand.class, oExecutor);
		oProcessor.addCommand(tstTestCommand_A.class, oExecutor);
		oProcessor.addCommand(tstTestCommand_B.class, oExecutor);
		return oProcessor;
	}
	
	/*
	 * Check if inhibitions work by dispatching an inhibted command and a not inhibited
	 */
	@Test
	public void testExecution_Inhibition() {
		clsActionProcessor oAPr;
		
		oAPr = getActionProcessor();
		tstTestCommand_A oCmd_Inhibit = new tstTestCommand_A();
		tstTestCommand_B oCmd_NonInhibit = new tstTestCommand_B();
		oAPr.call(oCmd_Inhibit, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.call(oCmd_NonInhibit, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.inhibitCommand(tstTestCommand_A.class, 1);
		oAPr.dispatch();
		assertFalse("Inhibit: Command 1 was not blocked", oCmd_Inhibit.getExecuted());
		assertTrue("Inhibit: Command 2 was not executed", oCmd_NonInhibit.getExecuted());
		assertTrue("Inhibit: Executionstates are wrong", oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_EXECUTED).contains(oCmd_NonInhibit) && oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_INHIBITED).contains(oCmd_Inhibit) );
			
	}


	/*
	 * Check if disabling works by dispatching a disabled command and a command which 
	 * was disabled an was re-enabled again
	 */
	@Test
	public void testExecution_Disabling() {
		clsActionProcessor oAPr;
		
		oAPr = getActionProcessor();
		tstTestCommand_A oCmd_Disabled = new tstTestCommand_A();
		tstTestCommand_B oCmd_Enabled = new tstTestCommand_B();
		oAPr.call(oCmd_Disabled, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.call(oCmd_Enabled, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.disableCommand(tstTestCommand_A.class);
		oAPr.disableCommand(tstTestCommand_B.class);
		oAPr.enableCommand(tstTestCommand_B.class);
		oAPr.dispatch();
		assertFalse("Disabling: Command 1 was not blocked", oCmd_Disabled.getExecuted());
		assertTrue("Disabling: Command 2 was not executed", oCmd_Enabled.getExecuted());
		assertTrue("Disabling: Executionstates are wrong", oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_EXECUTED).contains(oCmd_Enabled) && oAPr.getExecutionHistory(eExecutionResult.EXECUTIONRESULT_DISABLED).contains(oCmd_Disabled) );
			
	}
	
	/*
	 * Dispatches two sequences: One auto-generated by duration, one manual.
	 * One sequence is interrupted after round 2 and should be discontinued. 
	 */
	@Test
	public void testExecution_Sequences() {
		clsActionProcessor oAPr;
		
		//Create actions
		oAPr = getActionProcessor();
		tstTestCommand_A oCmd_A0 = new tstTestCommand_A();
		tstTestCommand_A oCmd_A1 = new tstTestCommand_A();
		tstTestCommand_A oCmd_A2 = new tstTestCommand_A();
		tstTestCommand_A oCmd_A3 = new tstTestCommand_A();
		tstTestCommand_B oCmd_B = new tstTestCommand_B();
		clsActionSequence oSeq = new clsActionSequence();
		oSeq.add(0, oCmd_A0, 1);
		oSeq.add(1, oCmd_A1, 1);
		oSeq.add(2, oCmd_A2, 1);
		oSeq.add(3, oCmd_A3, 1);
		oAPr.call(oSeq, eCallPriority.CALLPRIORITY_NORMAL);
		oAPr.call(oCmd_B, eCallPriority.CALLPRIORITY_NORMAL,4);
		
		//Round 1 - A0 + B should be performed
		oAPr.dispatch();
		assertTrue(oCmd_A0.getExecuted());
		assertFalse(oCmd_A1.getExecuted());
		assertFalse(oCmd_A2.getExecuted());
		assertFalse(oCmd_A3.getExecuted());
		assertTrue(oCmd_B.getExecuted());
		
		//Reset flags after round 1
		oCmd_A0.resetExecuted();
		oCmd_A1.resetExecuted();
		oCmd_A2.resetExecuted();
		oCmd_A3.resetExecuted();
		oCmd_B.resetExecuted();
		
		//Round 2 - A1 and B should be performed
		oAPr.dispatch();
		assertFalse(oCmd_A0.getExecuted());
		assertTrue(oCmd_A1.getExecuted());
		assertFalse(oCmd_A2.getExecuted());
		assertFalse(oCmd_A3.getExecuted());
		assertTrue(oCmd_B.getExecuted());
		
		//Reset flags after round 2 and disable command A
		oCmd_A0.resetExecuted();
		oCmd_A1.resetExecuted();
		oCmd_A2.resetExecuted();
		oCmd_A3.resetExecuted();
		oCmd_B.resetExecuted();
		oAPr.disableCommand(tstTestCommand_A.class);
		
		//Round 3 - Only B should be performed
		oAPr.dispatch();
		assertFalse(oCmd_A0.getExecuted());
		assertFalse(oCmd_A1.getExecuted());
		assertFalse(oCmd_A2.getExecuted());
		assertFalse(oCmd_A3.getExecuted());
		assertTrue(oCmd_B.getExecuted());
		
		//Reset flags after round 3 and enable command A
		oCmd_A0.resetExecuted();
		oCmd_A1.resetExecuted();
		oCmd_A2.resetExecuted();
		oCmd_A3.resetExecuted();
		oCmd_B.resetExecuted();
		oAPr.enableCommand(tstTestCommand_A.class);
		
		//Round 4 - still only B should be performed (Sequence was interrupted)
		oAPr.dispatch();
		assertFalse(oCmd_A0.getExecuted());
		assertFalse(oCmd_A1.getExecuted());
		assertFalse(oCmd_A2.getExecuted());
		assertFalse(oCmd_A3.getExecuted());
		assertTrue(oCmd_B.getExecuted());
		
		//Reset flags after round 4 
		oCmd_A0.resetExecuted();
		oCmd_A1.resetExecuted();
		oCmd_A2.resetExecuted();
		oCmd_A3.resetExecuted();
		oCmd_B.resetExecuted();
		
		//Round 5 - Nothing should be performed
		oAPr.dispatch();
		assertFalse(oCmd_A0.getExecuted());
		assertFalse(oCmd_A1.getExecuted());
		assertFalse(oCmd_A2.getExecuted());
		assertFalse(oCmd_A3.getExecuted());
		assertFalse(oCmd_B.getExecuted());
		
	}

	/*
	 * test-executor for processor. Can check if it was executed and mutual exclusions 
	 * can be set externally. Also energy/stamina demands can be set externally 
	 */
	private class tstTestExecutor extends clsActionExecutor {

		protected void setBodyPart() {
		}
		protected void setBodyPartId() {
		}
		protected void setName() {
			moName= "Test Executor";
		}
		
		public boolean execute(itfActionCommand poCommand, clsEntity poEntity) {
			tstTestCommand oCommand = (tstTestCommand) poCommand;
			oCommand.setExecuted(true);
			return true;
		}

		public ArrayList<Class> getMutualExclusions(itfActionCommand poCommand) {
			return ((tstTestCommand) poCommand).getMutualExclusions(); 
		}
}
	
	/*
	 * test-command for processor. Can check if it was executed and mutual exclusions 
	 * can be set externally. Also energy/stamina demands can be set externally 
	 */
	private class tstTestCommand implements itfActionCommand {
		private boolean mbExecuted=false;
		private ArrayList<Class> moMutEx = new ArrayList<Class>();
		double mnEnergy;
		double mnStamina;

		public void setEnergyDemand(double pnEnergy) {
			mnEnergy=pnEnergy;
		}
		public double getEnergyDemand() {
			return mnEnergy;
		}

		public void setStaminaDemand(double pnStamina) {
			mnStamina=pnStamina;
		}
		public double getStaminaDemand() {
			return mnStamina;
		}
		
		public ArrayList<Class> getMutualExclusions() {
			return moMutEx; 
		}
		
		public void addMutualExclusion(Class poMutEx) {
			moMutEx.add(poMutEx);
		}
		
		public void resetExecuted() {
			mbExecuted=false;
		}
		
		public boolean getExecuted() {
			return mbExecuted;
		}

		public void setExecuted(boolean pbExecuted) {
			mbExecuted=pbExecuted;
		}
		
		public String getLog() {
			return "";
		}
	}
	private class tstTestCommand_A extends tstTestCommand {
	}
	private class tstTestCommand_B extends tstTestCommand {
	}
	
}
