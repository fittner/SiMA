/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import config.clsBWProperties;
import bw.body.io.clsBaseIO;
import bw.body.io.clsSensorActuatorBaseInt;
import bw.body.io.sensors.itfSensorUpdate;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public abstract class clsSensorInt extends clsSensorActuatorBaseInt implements itfSensorUpdate {

	
	public clsSensorInt(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO) {
		super(poBaseIO);
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
}
