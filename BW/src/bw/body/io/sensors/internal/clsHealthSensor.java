/**
 * @author langr
 * 12.05.2009, 17:39:42
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import config.clsBWProperties;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 12.05.2009, 17:39:42
 * 
 */
public class clsHealthSensor extends clsSensorInt {
	private clsBaseBody moBody; // reference
	
	private double mrHealthValue;
	
	public clsHealthSensor(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
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
		moName = "int. Health Sensor";

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
			clsHealthSystem oHealthSystem = ((clsComplexBody)moBody).getInternalSystem().getHealthSystem();

			mrHealthValue = oHealthSystem.getHealth().getContent();
		}
		
	}
	
	/**
	 * @return the mrHealthValue
	 */
	public double getHealthValue() {
		return mrHealthValue;
	}
}
