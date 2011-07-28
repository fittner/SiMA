/**
 * clsSlowMessengerSensor.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 23.09.2009, 13:45:29
 */
package bw.body.io.sensors.internal;

import java.util.HashMap;
import java.util.Map;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsSlowMessengerSystem;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;
import bw.utils.tools.clsDecayColumn;
import config.clsBWProperties;
import du.enums.eSlowMessenger;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.09.2009, 13:45:29
 * 
 */
public class clsSlowMessengerSensor extends clsSensorInt {

	private clsSlowMessengerSystem moSMS; // reference
	private HashMap<eSlowMessenger, Double> moMessenges;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.09.2009, 13:45:38
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	public clsSlowMessengerSensor(String poPrefix, clsBWProperties poProp,
			clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);

		moMessenges = new HashMap<eSlowMessenger, Double>();
		
		if (poBody  instanceof clsComplexBody) {
			moSMS =  ((clsComplexBody)poBody).getInternalSystem().getSlowMessengerSystem();	
		} else {
			moSMS = null;
		}
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
	}	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 13:45:29
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_INT_SLOWMESSENGER;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 13:45:29
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "Slow Messenger Sensor";

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.09.2009, 13:45:29
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		if ( moSMS != null) {
			moMessenges.clear();
			
			HashMap<eSlowMessenger, clsDecayColumn> moSlowMessengers = moSMS.getSlowMessengers();
			for (Map.Entry<eSlowMessenger, clsDecayColumn> entry:moSlowMessengers.entrySet()) {
				moMessenges.put(entry.getKey(), new Double(entry.getValue().getContent()));
			}
			
		}
	}
	
	
	/**
	 * @return the mrHealthValue
	 */
	public HashMap<eSlowMessenger, Double> getSlowMessages() {
		return moMessenges;
	}	

}
