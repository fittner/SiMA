/**
 * clsStomachTension.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 10.08.2009, 15:56:22
 */
package bw.body.io.sensors.internal;

import config.clsBWProperties;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsStomachSystem;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 10.08.2009, 15:56:22
 * 
 */
public class clsStomachTensionSensor  extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrTension;
	

	public clsStomachTensionSensor(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		mrTension = 0;
		setEntity(poBody);
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
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "int. Stomach Tension Sensor";
	}	
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsStomachSystem oStomachSystem = ((clsComplexBody)moBody).getInternalSystem().getStomachSystem();

			try {
				mrTension = oStomachSystem.getWeight() / oStomachSystem.getMaxWeight();
			} catch (java.lang.ArithmeticException e) {
				mrTension = 0; // per definition.
			}
		}
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getTension() {
		return mrTension;
	}		
}