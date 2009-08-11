/**
 * E24_RealityCheck.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:49:09
 */
package pa.modules;

import config.clsBWProperties;
import pa.interfaces.I2_12;
import pa.interfaces.I6_1;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:49:09
 * 
 */
public class E24_RealityCheck extends clsModuleBase implements I2_12, I6_1 {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:49:30
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E24_RealityCheck(String poPrefix, clsBWProperties poProp,
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
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I2_12(int pnData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I6_1#receive_I6_1(int)
	 */
	@Override
	public void receive_I6_1(int pnData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.itfStep#step()
	 */
	@Override
	public void step() {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
