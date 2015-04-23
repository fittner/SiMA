/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 */
package pa.modules;

import pa.interfaces.I1_5;
import pa.interfaces.I1_6;
import pa.interfaces.I3_1;
import pa.interfaces.I4_1;
import pa.interfaces.I4_3;
import pa.interfaces.I5_1;
import pa.interfaces.I6_3;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 * 
 */
public class E06_DefenseMechanismsForDriveContents extends clsModuleBase implements I1_5, I3_1, I4_3, I6_3 {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:01:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E06_DefenseMechanismsForDriveContents(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@Override
	public void receive_I1_5(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:36
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I1_6)moEnclosingContainer).receive_I1_6(mnTest);
		((I4_1)moEnclosingContainer).receive_I4_1(mnTest);
		((I5_1)moEnclosingContainer).receive_I5_1(mnTest);	
	}
}
