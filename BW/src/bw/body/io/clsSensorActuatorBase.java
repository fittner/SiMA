/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import bw.body.itfStep;



/**
 * Base class for all sensors and actuators, contains methods and definitions for the extending classes
 * 
 * @author muchitsch
 * 
 */
public class clsSensorActuatorBase implements itfStep {

	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void step() {
		//do nothing
	}

}
