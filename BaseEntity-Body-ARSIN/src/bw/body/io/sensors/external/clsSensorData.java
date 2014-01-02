/**
 * @author zeilinger
 * 13.07.2009, 11:34:52
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import ARSsim.physics2D.physicalObject.clsCollidingObject;

import java.util.ArrayList;
import java.util.HashMap;

import sim.physics2D.util.Double2D;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.07.2009, 11:34:52
 * 
 */
public class clsSensorData {
	
	protected Double mnRange;
	protected Double mnFieldOfView;
	protected Double2D moSensorOffset; 
	private HashMap<Double, ArrayList<clsCollidingObject>> meDetectedObjects; 
		
	public clsSensorData(Double2D poSensorOffset,Double pnSensorRange, Double pnFieldOfview)
	{		
		moSensorOffset = poSensorOffset;
		mnFieldOfView = pnFieldOfview; 
		mnRange = pnSensorRange;
		meDetectedObjects = new HashMap<Double, ArrayList<clsCollidingObject>>();
	}
	
	public void setMeDetectedObjectList(Double pnAreaRange,ArrayList<clsCollidingObject> pePhysicalObject){
		meDetectedObjects.put(pnAreaRange, pePhysicalObject); 
	}
	
	public HashMap <Double, ArrayList<clsCollidingObject>> getMeDetectedObject(){
		return meDetectedObjects;
	}
}
