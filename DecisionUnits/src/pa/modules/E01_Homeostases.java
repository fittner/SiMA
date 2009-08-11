/**
 * E1_Homeostases.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 */
package pa.modules;

import pa.interfaces.itfProcessSensorData;
import config.clsBWProperties;
import decisionunit.itf.sensors.clsSensorData;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 * 
 */
public class E01_Homeostases extends clsModuleBase implements itfProcessSensorData {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 12:09:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E01_Homeostases(String poPrefix, clsBWProperties poProp,
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
	 * 11.08.2009, 12:16:41
	 * 
	 * @see pa.interfaces.itfProcessSensorData#process(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void process(clsSensorData poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:39:22
	 * 
	 * @see pa.interfaces.itfStep#step()
	 */
	@Override
	public void step() {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
