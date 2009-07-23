/**
 * @author zeilinger
 * 13.07.2009, 11:33:45
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.entities.clsEntity;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.exceptions.exInvalidSensorRange;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.07.2009, 11:33:45
 * 
 *  * FILES changed for the integration of the Sensor-Engine
	 * 
	 * clsSensorExternalIO
	 * clsRegisterEntity
	 * clsBrainSocket
	 * itfGetSensorEngine
	 * 
	 * eSensorExtType - only temporary changes - VISION_new - should be changed when VISION is
	 * 					integrated 
	 * eConfigEntries - only temporary changes - VISION_new - should be changed when VISION is
	 * 					integrated
 */
public class clsSensorEngine{
	
	private HashMap<Double,ArrayList<clsSensorExt>> meRegisteredSensor;
	private HashMap <Double,ArrayList<PhysicalObject2D>> meDetectedObj;
	private HashMap <Double, HashMap<Integer, Double2D>> meCollisionPoints;
	private TreeMap<Double,clsEntitySensorEngine> meEntities;
	private double[] mnRange = {20,40,60}; //HZ -- has to be set in increasing sequence 
		
	public clsSensorEngine(clsEntity poHostEntity){
		meRegisteredSensor = new HashMap<Double,ArrayList<clsSensorExt>>();
		meDetectedObj = new HashMap <Double,ArrayList<PhysicalObject2D>>();
		meCollisionPoints = new HashMap<Double,HashMap <Integer, Double2D>>(); 
		meEntities = new TreeMap<Double, clsEntitySensorEngine>(); 
		
		this.registerEngineEntity(poHostEntity); 
	}
	
	private void registerEngineEntity(clsEntity poHostEntity){
		for(int index=0; index < mnRange.length; index++){
			meEntities.put(mnRange[index],new clsEntitySensorEngine(poHostEntity,mnRange[index]));
		}
	}

	//every sensor has to register itself in the Sensor List
	public void registerSensor(clsSensorExt poSensor){
		double nSensorRange = poSensor.getSensorDataObj().getSensorRange(); 
		
		if(checkRange(nSensorRange)){
			fillMeRegisteredSensor(nSensorRange, poSensor);
		}
		else{
			throwExInvalidSensorRange(nSensorRange); 
		}
	}
	
	public void fillMeRegisteredSensor(double pnSensorRange, clsSensorExt poSensor){
		
		for(int index = 0; index< mnRange.length; index++){
		    if(meRegisteredSensor.containsKey(mnRange[index])){
				((ArrayList<clsSensorExt>)this.meRegisteredSensor.get(mnRange[index]))
																  .add(poSensor);
			}
			else {
				ArrayList <clsSensorExt> meSensor = new ArrayList<clsSensorExt>();
				meSensor.add(poSensor); 
				meRegisteredSensor.put(mnRange[index],meSensor);
			}
		    if(pnSensorRange == mnRange[index]){
		    		break; 
		    }
		}
	}
	
	public boolean checkRange(double pnRange){
		for(int index=0; index<mnRange.length;index++ ){
			if(((Double)mnRange[index]).equals(pnRange)){
				return true; 
			}
		}
		return false; 
	}
	
	
	public void updateSensorData() {
		// TODO Auto-generated method stub
		requestSensorData(); 
	   	Iterator <Double> rangeItr = meRegisteredSensor.keySet().iterator();
			
		while(rangeItr.hasNext()){
			double nRange = rangeItr.next();
			Iterator <clsSensorExt> itr = ((ArrayList<clsSensorExt>)meRegisteredSensor.get(nRange)).iterator();
						
			while(itr.hasNext()){
				((clsSensorExt)itr.next()).updateSensorData(nRange, meDetectedObj.get(nRange),
															        meCollisionPoints.get(nRange)); 
			}
		}
	}
	
	public TreeMap<Double, clsEntitySensorEngine> getMeSensorAreas(){
		return meEntities; 
	}
	
	public void requestSensorData(){
		meDetectedObj.clear(); 
		meCollisionPoints.clear(); 
		getSensorData(); 
		sortOutData(); 
	}
	
	
	private void getSensorData(){
		for(int index=0; index < mnRange.length; index++){
			meDetectedObj.put(mnRange[index], 
						      meEntities.get(mnRange[index]).requestDetectedObjList());
			meCollisionPoints.put(mnRange[index],
								  meEntities.get(mnRange[index]).requestCollisionPointMap());
		}
	}
	
	/* The SensorEngine works on the base of three entities which cover a different range
	 * of area. Hence, a larger entity contains the objects of smaller entities too. The 
	 * method sortOutData sorts out objects which are shown in the list of a large area  
	 * and occur in a smaller area too. It is implemented for the list of detected objects
	 * only and NOT for the list of collision points. However the collision points are 
	 * placed in a HashMap which has the object ID of the colliding list as a key. Hence,
	 * the search for the object's colliding points should not be difficult.   
	 *
	 */
	private void sortOutData(){
				
		for(int index=mnRange.length-1; index > 0; index--){
			ArrayList <PhysicalObject2D> eObjectList = meDetectedObj.get(mnRange[index]);
						
			eObjectList.removeAll(meDetectedObj.get(mnRange[index-1])); 
			meDetectedObj.put(mnRange[index],eObjectList);
		}
	}
	
	private void throwExInvalidSensorRange(double pnSensorRange){
		try {
			throw new exInvalidSensorRange(mnRange,pnSensorRange);
		} catch (exInvalidSensorRange e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
