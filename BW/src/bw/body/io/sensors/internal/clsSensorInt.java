/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import bw.body.io.clsBaseIO;
import bw.body.io.clsSensorActuatorBaseInt;
import bw.body.io.sensors.itfSensorUpdate;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public abstract class clsSensorInt extends clsSensorActuatorBaseInt implements itfSensorUpdate {

	/**
	 * @param poBaseIO
	 */
	public clsSensorInt(clsBaseIO poBaseIO) {
		super(poBaseIO);
		// TODO Auto-generated constructor stub
	}

}
