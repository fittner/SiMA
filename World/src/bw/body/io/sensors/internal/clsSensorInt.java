/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import config.clsProperties;
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

	
	public clsSensorInt(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = clsSensorActuatorBaseInt.getDefaultProperties(poPrefix);
		
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		//nothing to do
	}		
}
