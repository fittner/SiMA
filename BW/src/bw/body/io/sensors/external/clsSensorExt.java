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
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.sensors.itfSensorUpdate;
import bw.utils.container.clsConfigMap;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 
 */
public abstract class clsSensorExt extends clsSensorActuatorBaseExt implements itfSensorUpdate {

	protected clsConfigMap moConfig;
	
	/**
	 * @param poBaseIO
	 */
	public clsSensorExt(clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO);
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}
	
	public clsSensorExt(clsConfigMap poConfig) {
		super();
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
	
	public void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj) {
	}

}
