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
import body.itfget.itfGetMuscleTension;

import complexbody.io.clsBaseIO;

import entities.enums.eBodyParts;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 
 */
public class clsMuscleTensionSensor  extends clsSensorInt {

	private clsBaseBody moBody; // reference
	private itfGetMuscleTension moOrgan;
	
	private double mrMuscleTension;
	

	public clsMuscleTensionSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody, itfGetMuscleTension poOrgan) {
		super(poPrefix, poProp, poBaseIO);
		moOrgan=poOrgan;
		mrMuscleTension = 0;
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
		mePartId = eBodyParts.SENSOR_INT_MUSCLETENSION;

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
		moName = "int. Muscle Tension Sensor";
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
			mrMuscleTension = moOrgan.getMuscleTensionIntensity();

		} // updated by volkan
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getMuscleTension() {
		return mrMuscleTension;
	}		
}
