/**
 * @author zeilinger
 * 13.07.2009, 11:34:52
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;
import java.util.HashMap;

import bw.body.io.sensors.ext.clsSensorExt;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.07.2009, 11:34:52
 * 
 */
public class clsSensorData {
	
	private Double mnRange;
	private Double2D moPosition; 
	private HashMap<Double, ArrayList<PhysicalObject2D>> meDetectedObjects; 
	
	public clsSensorData(clsSensorExt poSensorTyp, Double2D poSensorPos, Double pnSensorRange)
	{		
		moPosition = poSensorPos;
		mnRange = pnSensorRange;
		meDetectedObjects = new HashMap<Double, ArrayList<PhysicalObject2D>>();
	}

	public Double2D getSensorPos(){
		return moPosition; 
	}
	
	public void setSensorPos(Double2D poSensorPos){
		moPosition = poSensorPos;
	}
	
	public Double getSensorRange(){
		return mnRange; 
	}
	
	public void setMeDetectedObject(Double poSensorRange,ArrayList<PhysicalObject2D> pePhysicalObject){
		meDetectedObjects.put(poSensorRange, pePhysicalObject); 
	}
	
	public HashMap <Double, ArrayList<PhysicalObject2D>> getMeDetectedObject(){
		return meDetectedObjects;
	}
}
