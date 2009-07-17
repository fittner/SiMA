/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;
import bw.body.io.clsBaseIO;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public class clsSensorTactile extends clsSensorExt {

	/**
	 * @param poBaseIO
	 */
	public clsSensorTactile(clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO, poConfig);
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
		moName = "ext. Sensor Acoustic";
		
	}

	/* (non-Javadoc)
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {
		// TODO Auto-generated method stub
		
	}

	public void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj){
		
	}
}
