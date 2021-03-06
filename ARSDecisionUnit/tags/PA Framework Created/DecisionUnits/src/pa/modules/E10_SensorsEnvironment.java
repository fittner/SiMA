/**
 * E10_SensorsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:13:27
 */
package pa.modules;

import config.clsBWProperties;
import pa.interfaces.I2_1;
import decisionunit.itf.sensors.clsSensorData;
import pa.interfaces.itfProcessSensorEnvironment;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:13:27
 * 
 */
public class E10_SensorsEnvironment extends clsModuleBase implements itfProcessSensorEnvironment {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:13:49
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E10_SensorsEnvironment(String poPrefix, clsBWProperties poProp,
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
	 * 11.08.2009, 15:50:31
	 * 
	 * @see pa.interfaces.itfProcessSensorEnvironment#processEnvironment(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveEnvironment(clsSensorData poData) {
		mnTest = 0;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:23
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
	 * 11.08.2009, 16:15:23
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_1)moEnclosingContainer).receive_I2_1(mnTest);
		
	}


}
