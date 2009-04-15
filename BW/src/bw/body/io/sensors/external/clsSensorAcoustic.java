/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public class clsSensorAcoustic extends clsSensorExt {

	/**
	 * @param poBaseIO
	 */
	public clsSensorAcoustic(clsBaseIO poBaseIO) {
		super(poBaseIO);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_ACOUSTIC;		
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName="ext. Sensor Acoustic";
	}

	/* (non-Javadoc)
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {
		// TODO Auto-generated method stub
		
	}

}
