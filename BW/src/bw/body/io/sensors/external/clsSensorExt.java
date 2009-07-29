/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import bw.body.io.clsBaseIO;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.sensors.itfSensorUpdate;
import bw.utils.config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public abstract class clsSensorExt extends clsSensorActuatorBaseExt implements itfSensorUpdate {


	public clsSensorExt(String poPrefix, clsBWProperties poProp) {
		super();
		applyProperties(poPrefix, poProp);
	}
	
	public clsSensorExt(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO) {
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
