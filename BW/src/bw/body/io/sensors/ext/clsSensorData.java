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

import bw.utils.sensors.clsSensorDataCalculation;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.07.2009, 11:34:52
 * 
 */
public class clsSensorData extends clsSensorDataCalculation{
	
	protected Double mnRange;
	protected Double mnFieldOfView;
	protected Double2D moSensorOffset; 
	private HashMap<Double, ArrayList<PhysicalObject2D>> meDetectedObjects; 
	private HashMap<Double, HashMap<Integer, Double2D>> meCollisionPoints; 
	
	public clsSensorData(Double2D poSensorOffset,Double pnSensorRange, Double pnFieldOfview)
	{		
		moSensorOffset = poSensorOffset;
		mnFieldOfView = pnFieldOfview; 
		mnRange = pnSensorRange;
		meDetectedObjects = new HashMap<Double, ArrayList<PhysicalObject2D>>();
		meCollisionPoints = new HashMap<Double, HashMap<Integer, Double2D>>(); 
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
