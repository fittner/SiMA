/**
 * @author langr
 * 12.05.2009, 17:37:09
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.io.clsBaseIO;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 12.05.2009, 17:37:09
 * 
 */
public class clsEnergyConsumptionSensor extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrEnergy;
	
	public clsEnergyConsumptionSensor(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
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
	 * TODO (muchitsch) - insert description
	 *
	 * @param poEntity
	 */
	private void setEntity(clsBaseBody poBody) {
		this.moBody = poBody;
	}
	
	/**
	 * @return the mrEnergy
	 */
	public double getEnergy() {
		return mrEnergy;
	}
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:37:09
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_INT_ENERGYCONSUMPTION;

	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:37:09
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
	 * 12.05.2009, 17:37:09
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsInternalEnergyConsumption oInternalEnergyConsumption = ((clsComplexBody)moBody).getInternalEnergyConsumption();

			mrEnergy = oInternalEnergyConsumption.getSum();
			
		}


	}

}
