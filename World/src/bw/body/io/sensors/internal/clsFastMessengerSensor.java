/**
 * clsFastMessengerSensor.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 04.08.2009, 13:59:02
 */
package bw.body.io.sensors.internal;

import java.util.ArrayList;

import config.clsProperties;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFastMessengerEntry;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.io.clsBaseIO;
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
	private ArrayList<clsFastMessengerEntry> moMessages;
	private eBodyParts moTargetFilter;
	
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
	public clsFastMessengerSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		if (poBody  instanceof clsComplexBody) {
			moFMS =  ((clsComplexBody)poBody).getInternalSystem().getFastMessengerSystem();	
		} else {
			moFMS = null;
		}
		
		moTargetFilter = eBodyParts.BRAIN;
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		 String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

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
		mePartId = eBodyParts.SENSOR_INT_FASTMESSENGER;
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
	@Override
	public void updateSensorData() {

		if ( moFMS != null) {
			moMessages = moFMS.getMessagesForTarget(moTargetFilter);
		}
		
	}
	
	/**
	 * @return the mrHealthValue
	 */
	public ArrayList<clsFastMessengerEntry> getFastMessages() {
		return moMessages;
	}
	
}
