/**
 * E32_Actuators.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 */
package pa.modules;

import memory.tempframework.clsActionContainer;
import config.clsBWProperties;
import pa.interfaces.I8_2;
import pa.interfaces.itfReturnActionCommands;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 * 
 */
public class E32_Actuators extends clsModuleBase implements I8_2, itfReturnActionCommands {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:01:43
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E32_Actuators(String poPrefix, clsBWProperties poProp,
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
		mnProcessType = eProcessType.BODY;
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
		mnPsychicInstances = ePsychicInstances.BODY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:01:51
	 * 
	 * @see pa.interfaces.I8_2#receive_I8_2(int)
	 */
	@Override
	public void receive_I8_2(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:03:00
	 * 
	 * @see pa.interfaces.itfReturnActionCommands#getActionCommands()
	 */
	@Override
	public clsActionContainer getActionCommands() {
		// TODO (deutsch) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:59
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
	 * 11.08.2009, 16:16:59
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
