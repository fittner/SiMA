/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public class clsSensorAcoustic extends clsSensorExt {

	public clsSensorAcoustic(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		// TODO Auto-generated constructor stub
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
