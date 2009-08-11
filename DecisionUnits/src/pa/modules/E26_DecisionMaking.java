/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 */
package pa.modules;

import config.clsBWProperties;
import pa.interfaces.I1_7;
import pa.interfaces.I2_13;
import pa.interfaces.I3_3;
import pa.interfaces.I5_5;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 * 
 */
public class E26_DecisionMaking extends clsModuleBase implements I1_7, I2_13, I3_3, I5_5 {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:52:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E26_DecisionMaking(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
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
		mnProcessType = eProcessType.SECONDARY;
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
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@Override
	public void receive_I2_13(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:33
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
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// TODO (deutsch) - Auto-generated method stub
		
	}
}
