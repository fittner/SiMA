/**
 * @author langr
 * 12.05.2009, 17:37:09
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import config.clsProperties;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 12.05.2009, 17:37:09
 * 
 */
public class clsEnergyConsumptionSensor extends clsSensorInt {

	private clsBaseBody moBody; // reference
	
	private double mrEnergy;
	
	public clsEnergyConsumptionSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
		super(poPrefix, poProp, poBaseIO);
		
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
	
	
	/**
	 * DOCUMENT (muchitsch) - insert description
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
	@Override
	public void updateSensorData() {

		if ( moBody instanceof clsComplexBody) {
			clsInternalEnergyConsumption oInternalEnergyConsumption = ((clsComplexBody)moBody).getInternalEnergyConsumption();

			mrEnergy = oInternalEnergyConsumption.getSum();
			
		}


	}

}
