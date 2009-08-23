/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import config.clsBWProperties;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
@Deprecated
public class clsSensorOlfactoric extends clsSensorExt {

	public clsSensorOlfactoric(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		// TODO (zeilinger) - Auto-generated constructor stub
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
		mePartId = eBodyParts.SENSOR_EXT_OLFACTORIC;
		
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Olfactoric";
		
	}

	/* (non-Javadoc)
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

}
