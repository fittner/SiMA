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

import bw.body.io.sensors.external.clsSensorExt;
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
	
	private HashMap <String, Object> moSensorData = new HashMap<String, Object>(); 
	private HashMap <Double, ArrayList<PhysicalObject2D>> meDetectedObject;
		
	public clsSensorData(clsSensorExt poSensorTyp, Double2D poSensorPos, Double poSensorRange)
	{
		this.moSensorData.put("POSITION", poSensorPos);
		this.moSensorData.put("TYPE", poSensorTyp);
		this.moSensorData.put("RANGE", poSensorRange);
		
		this.meDetectedObject= new HashMap<Double, ArrayList<PhysicalObject2D>>();
	}

	public Double2D getSensorPos(){
		return (Double2D)this.moSensorData.get("POSITION"); 
	}
	
	public void setSensorPos(Double2D poSensorPos){
		this.moSensorData.put("POSITION",poSensorPos);
	}
	
	public clsSensorExt getSensorTyp(){
		return (clsSensorExt)this.moSensorData.get("TYPE"); 
	}
	
	public Double getSensorRange(){
		return (Double)this.moSensorData.get("RANGE"); 
	}
	
	public void setMeDetectedObject(Double poSensorRange,ArrayList<PhysicalObject2D> pePhysicalObject){
		meDetectedObject.put(poSensorRange, pePhysicalObject); 
	}
	
	public HashMap <Double, ArrayList<PhysicalObject2D>> getMeDetectedObject(){
		return meDetectedObject; 
	}
}
