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
import bw.utils.container.clsConfigMap;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public abstract class clsSensorInt extends clsSensorActuatorBaseInt implements itfSensorUpdate {

	protected clsConfigMap moConfig;
	
	/**
	 * @param poBaseIO
	 */
	public clsSensorInt(clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO);
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}

	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}	
}
