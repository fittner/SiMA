/**
 * clsStomachTension.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 10.08.2009, 15:56:22
 */
package complexbody.io.sensors.internal;

import properties.clsProperties;
import body.clsBaseBody;
import body.clsComplexBody;
//import bw.body.internalSystems.clsStomachSystem;

import complexbody.io.clsBaseIO;

import entities.enums.eBodyParts;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 
 */
public class clsHeartbeatSensor  extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrHeartbeat;
	

	public clsHeartbeatSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		mrHeartbeat = 0;
		setEntity(poBody);
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
	
	private void setEntity(clsBaseBody poBody) {
		this.moBody = poBody;
	}	
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_INT_STOMACHTENSION;

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "int. Heartbeat Sensor";
	}	
	

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
//			clsStomachSystem oStomachSystem = ((clsComplexBody)moBody).getInternalSystem().getStomachSystem();

//			try {
//				mrHeartbeat = 1; //TODO
//			} catch (java.lang.ArithmeticException e) {
//				mrHeartbeat = 0; // per definition.
//			}
			
			mrHeartbeat = ((clsComplexBody)moBody).getInternalSystem().getBOrganSystem().getBOHeart().getHeartRate();

		} // updated by volkan
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getHeartbeat() {
		return mrHeartbeat;
	}		
}
