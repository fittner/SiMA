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
import bw.utils.sensors.clsSensorDataCalculation;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.07.2009, 11:34:52
 * 
 */
public class clsSensorData extends clsSensorDataCalculation{
	
	private Double mnRange;
	private Double mnAngle;
	private Double2D moPosition; 
	private HashMap<Double, ArrayList<PhysicalObject2D>> meDetectedObjects; 
	private HashMap<Double, HashMap<Integer, Double2D>> meCollisionPoints; 
	
	public clsSensorData(clsSensorExt poSensorTyp, Double2D poSensorPosition,
						 Double pnSensorRange, Double pnSensorAngle)
	{		
		moPosition = poSensorPosition;
		mnAngle = pnSensorAngle; 
		mnRange = pnSensorRange;
		meDetectedObjects = new HashMap<Double, ArrayList<PhysicalObject2D>>();
		meCollisionPoints = new HashMap<Double, HashMap<Integer, Double2D>>(); 
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
	
	public Double getSensorAngle(){
		return mnAngle; 
	}
	
	public void setMeDetectedObjectList(Double pnAreaRange,ArrayList<PhysicalObject2D> pePhysicalObject){
		meDetectedObjects.put(pnAreaRange, pePhysicalObject); 
	}
	
	public void setMeCollisionPointList(Double poSensorRange, HashMap<Integer,Double2D> poCollisionPointList){
		meCollisionPoints.put(poSensorRange, poCollisionPointList); 
	}
	
	public HashMap <Double, ArrayList<PhysicalObject2D>> getMeDetectedObject(){
		return meDetectedObjects;
	}
	
	public HashMap<Double,HashMap<Integer, Double2D>> getMeCollisionPointList(){
		return meCollisionPoints;
	}
	
}
