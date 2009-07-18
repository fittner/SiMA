/**
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.body.io.clsBaseIO;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.sensors.itfSensorUpdate;
import bw.utils.container.clsConfigMap;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 */
public abstract class clsSensorExt extends clsSensorActuatorBaseExt implements itfSensorUpdate {

	protected clsConfigMap moConfig;
	protected clsSensorData moSensorData; 
	private clsSensorEngine moSensorEngine;  
	
	/**
	 * @param poBaseIO
	 */
	public clsSensorExt(clsBaseIO poBaseIO, clsConfigMap poConfig, clsSensorEngine poSensorEngine) {
		super(poBaseIO);
		moSensorEngine = poSensorEngine; 
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
	
	public void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj){}


	public void assignSensorData(clsSensorExt poSensor,Double2D poSensorPosition, Double moRange){
		moSensorData = new clsSensorData(poSensor, poSensorPosition, moRange); 
		moSensorEngine.registerSensor(this);
	}
	
	public clsSensorData getSensorData(){
		return moSensorData; 
	}
}
