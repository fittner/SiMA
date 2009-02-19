/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import bw.clsEntity;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.sensors.itfSensorUpdate;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public abstract class clsSensorExt extends clsSensorActuatorBaseExt implements itfSensorUpdate {

	/* (non-Javadoc)
	 * @see bw.body.io.itfSensorUpdate#updateSensorData(bw.clsEntity)
	 */
	@Override
	public abstract void updateSensorData();
}
