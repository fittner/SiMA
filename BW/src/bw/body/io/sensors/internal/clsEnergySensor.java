/**
 * clsEnergySensor.java: BW - bw.body.io.sensors.internal
 * 
 * @author deutsch
 * 10.08.2009, 15:51:36
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
 * 10.08.2009, 15:51:36
 * 
 */
public class clsEnergySensor extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrEnergy;

	public clsEnergySensor(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
		mrEnergy = 0;
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
	
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @param poEntity
	 */
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
		mePartId = eBodyParts.SENSOR_INT_ENERGY;

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
		moName = "int. Energy Sensor";
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

			mrEnergy = oStomachSystem.getEnergy();
		}
		
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getEnergy() {
		return mrEnergy;
	}	
}