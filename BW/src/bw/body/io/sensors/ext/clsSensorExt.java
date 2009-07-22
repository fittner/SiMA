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
import bw.utils.config.clsBWProperties;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 */
public abstract class clsSensorExt extends clsSensorActuatorBaseExt implements itfSensorUpdate {

	protected clsSensorData moSensorData; 
	private clsSensorEngine moSensorEngine;  
	
	public clsSensorExt(String poPrefix, clsBWProperties poProp) {
		super();
		applyProperties(poPrefix, poProp);
	}
	
	public clsSensorExt(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsSensorEngine poSensorEngine) {
		super(poBaseIO);
		moSensorEngine = poSensorEngine; 
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
	
	public abstract void updateSensorData(Double pnRange, ArrayList<PhysicalObject2D> peObj);


	public void assignSensorData(clsSensorExt poSensor,Double2D poSensorPosition, Double moRange){
		moSensorData = new clsSensorData(poSensor, poSensorPosition, moRange); 
		moSensorEngine.registerSensor(this);
	}
	
	public clsSensorData getSensorData(){
		return moSensorData; 
	}
}
