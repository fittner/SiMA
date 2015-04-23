/**
 * @author langr
 * 12.05.2009, 17:39:42
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.sensors.internal;

import properties.clsProperties;
import body.clsBaseBody;
import body.clsComplexBody;

import complexbody.internalSystems.clsHealthSystem;
import complexbody.io.clsBaseIO;

import entities.enums.eBodyParts;


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
	
	public clsHealthSensor(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO, clsBaseBody poBody) {
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
	@Override
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
