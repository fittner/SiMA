/**
 * clsActionsLessTestDU.java: DecisionUnits - testbrains
 * 
 * @author deutsch
 * 02.09.2010, 10:34:18
 */
package _MOVEOUTOFPROJECTtestbrains;

import memorymgmt.interfaces.itfModuleMemoryAccess;
import properties.clsProperties;
import control.clsPsychoAnalysis;
import du.enums.eDecisionType;


/**
 * TESTBRAIN for Clemens Muchitsch. It is a specialization of pa.clsPsychoAnalysis. What it does, it resets the actionprocessor in each process step. Thus, this decision unit processes incommiung data and performs every step but has no impact on the world or on the body.  
 * 
 * @author deutsch
 * 02.09.2010, 10:34:18
 * 
 */
public class clsActionlessTestPA extends clsPsychoAnalysis {
	
	/**
	 * @author deutsch
	 * 02.09.2010, 10:42:55
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsActionlessTestPA(String poPrefix, clsProperties poProp, int uid,
            itfModuleMemoryAccess poMemory) {
		super(poPrefix, poProp, uid, poMemory);
	}

	/* (non-Javadoc)
	 *	executes applySensorData and step, but not getActionCommand.
	 *
	 * @author deutsch
	 * 02.09.2010, 10:34:18
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#process()
	 */
	@Override
	public void process() {
		//getProcessor().applySensorData( getSensorData() );
		//getProcessor().step();
		//moProcessor.getActionCommands( getActionProcessor() ); //removed on pupose
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:57:34
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.ActionlessTestPA;
		
	}	
}
