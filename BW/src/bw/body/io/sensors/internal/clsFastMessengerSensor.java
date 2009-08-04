/**
 * clsFastMessengerSensor.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 04.08.2009, 13:59:02
 */
package bw.body.io.sensors.internal;

import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsTemperatureSystem;
import bw.body.io.clsBaseIO;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 04.08.2009, 13:59:02
 * 
 */
public class clsFastMessengerSensor extends clsSensorInt {

	private clsFastMessengerSystem moFMS; // reference
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 04.08.2009, 13:59:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	public clsFastMessengerSensor(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsFastMessengerSystem poFMS) {
		super(poPrefix, poProp, poBaseIO);
		moFMS = poFMS;
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
	 * @author langr
	 * 12.05.2009, 17:39:42
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_INT_HEALTH;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:39:42
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "Fast Messenger Sensor";

	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:39:42
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsTemperatureSystem oTemperatureSystem = ((clsComplexBody)moBody).getInternalSystem().getTemperatureSystem();

			mrTemperatureValue = oTemperatureSystem.getTemperature().getContent();
		}
		
	}
	
	/**
	 * @return the mrHealthValue
	 */
	public double getTemperatureValue() {
		return mrTemperatureValue;
	}
	
}
