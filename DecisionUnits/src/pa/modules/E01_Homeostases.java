/**
 * E1_Homeostases.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 */
package pa.modules;

import java.util.HashMap;

import pa.interfaces.I1_1;
import pa.interfaces.itfProcessHomeostases;
import config.clsBWProperties;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorData;
import enums.eSensorIntType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 * 
 */
public class E01_Homeostases extends clsModuleBase implements itfProcessHomeostases {

	private HashMap<eSensorIntType, clsDataBase> moHomeostasis;
	
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
	 * 11.08.2009, 15:50:35
	 * 
	 * @see pa.interfaces.itfProcessHomeostases#processHomeostases(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveHomeostases(clsSensorData poData) {
		//filter out homeostatic values only!
		moHomeostasis = poData.getHomeostaticData();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:04:44
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
	 * 11.08.2009, 16:04:44
	 * 
	 * @see pa.modules.clsModuleBase#transmit()
	 */
	@Override
	protected void send() {
		((I1_1)moEnclosingContainer).receive_I1_1(mnTest);		
	}

}
