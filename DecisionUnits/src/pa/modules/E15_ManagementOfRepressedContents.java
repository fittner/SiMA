/**
 * E15_ManagementOfRepressedContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 */
package pa.modules;

import config.clsBWProperties;
import pa.interfaces.I2_5;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:28:49
 * 
 */
public class E15_ManagementOfRepressedContents extends clsModuleBase implements I2_5, I4_1, I4_2 {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:29:22
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E15_ManagementOfRepressedContents(String poPrefix,
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
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:29:39
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:29:39
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:29:39
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:45
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
	 * 11.08.2009, 16:15:45
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
