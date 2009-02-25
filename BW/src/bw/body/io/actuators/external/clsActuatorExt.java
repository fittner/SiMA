/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.external;

import bw.body.io.clsBaseIO;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.actuators.itfActuatorUpdate;

/**
 * Baseclass of all external actuators
 * Forces the subclasses to implement itfActuatorUpdate that 
 * is automatically called in the sensor-cycle of the entity    
 * 
 * @author langr
 * 
 */
public abstract class clsActuatorExt extends clsSensorActuatorBaseExt implements itfActuatorUpdate {

	/**
	 * @param poBaseIO
	 */
	public clsActuatorExt(clsBaseIO poBaseIO) {
		super(poBaseIO);
		// TODO Auto-generated constructor stub
	}

}
