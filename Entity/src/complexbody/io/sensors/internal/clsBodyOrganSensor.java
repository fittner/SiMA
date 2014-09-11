/**
 * CHANGELOG
 *
 * 11.09.2014 herret - File created
 *
 */
package complexbody.io.sensors.internal;

import body.attributes.clsBodyOrgan;
import properties.clsProperties;

import complexbody.io.clsBaseIO;
import entities.enums.eBodyParts;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 11.09.2014, 14:11:33
 * 
 */
public class clsBodyOrganSensor extends clsSensorInt {

	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since 11.09.2014 14:11:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	
	private clsBodyOrgan moOrganSystem;
	
	public clsBodyOrganSensor(String poPrefix, clsProperties poProp,
			clsBaseIO poBaseIO, clsBodyOrgan poOrganSystem) {
		super(poPrefix, poProp, poBaseIO);
		
		moOrganSystem=poOrganSystem;
	
	}

	/* (non-Javadoc)
	 *
	 * @since 11.09.2014 14:11:33
	 * 
	 * @see complexbody.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since 11.09.2014 14:11:33
	 * 
	 * @see complexbody.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_INT_ORGAN;


	}

	/* (non-Javadoc)
	 *
	 * @since 11.09.2014 14:11:33
	 * 
	 * @see complexbody.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "int. Organ Sensor";

	}

}
