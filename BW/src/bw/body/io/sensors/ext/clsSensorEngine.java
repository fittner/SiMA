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

import bw.entities.clsEntity;
import bw.physicalObjects.sensors.clsEntitySensorEngine;


/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.07.2009, 11:33:45
 * 
 */
public class clsSensorEngine{
	
	/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 13.07.2009, 23:00:20
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Double,ArrayList<clsSensorData>> moRegisteredSensor;
	private HashMap <Double,ArrayList<PhysicalObject2D>> meDetectedObj;
	private TreeMap<Double,clsEntitySensorEngine> meEntities;
	private double[] mnRange = {20,40,60};
		
	public clsSensorEngine(clsEntity poHostEntity){
		moRegisteredSensor = new HashMap<Double,ArrayList<clsSensorData>>();
		meDetectedObj = new HashMap <Double,ArrayList<PhysicalObject2D>>();	
		meEntities = new TreeMap<Double, clsEntitySensorEngine>(); 
				
		this.registerEngineEntity(poHostEntity); 
	}
	
	private void registerEngineEntity(clsEntity poHostEntity){
		for(int index=0; index < mnRange.length; index++){
			meEntities.put(mnRange[index],new clsEntitySensorEngine(poHostEntity,mnRange[index]));
		}
	}

	//every sensor has to register itself in the Sensor List
	public void registerSensor(clsSensorData poSensorData){
	
		    if(moRegisteredSensor.containsKey(poSensorData.getSensorRange())){
				((ArrayList<clsSensorData>)this.moRegisteredSensor.get(poSensorData.getSensorRange()))
																  .add(poSensorData);
			}
			else{
				ArrayList <clsSensorData> meSensorData = new ArrayList<clsSensorData>();
				meSensorData.add(poSensorData); 
				moRegisteredSensor.put(poSensorData.getSensorRange(),meSensorData);
			}
	}

	public void updateSensorData() {
		// TODO Auto-generated method stub
		HashMap <Double,ArrayList<PhysicalObject2D>> eDetectedObj = requestSensorData(); 
	   	Iterator <Double> rangeItr = moRegisteredSensor.keySet().iterator();
			
		while(rangeItr.hasNext()){
			double nRange = rangeItr.next();
			Iterator <clsSensorData> itr = ((ArrayList<clsSensorData>)moRegisteredSensor.get(nRange)).iterator();
						
			while(itr.hasNext()){
				((clsSensorData)itr.next()).getSensorTyp().updateSensorData(nRange, eDetectedObj.get(nRange)); 
			}
		}
	}
	
	public TreeMap<Double, clsEntitySensorEngine> getMeSensorAreas(){
		return meEntities; 
	}
	
	public HashMap <Double,ArrayList<PhysicalObject2D>> requestSensorData(){
		meDetectedObj.clear(); 
		ArrayList <PhysicalObject2D> eDummyList;
		
		for(int index=0; index < mnRange.length; index++){
			meDetectedObj.put(mnRange[index], meEntities.get(mnRange[index]).requestSensorData());
			
		}
		for(int index=mnRange.length-1; index > 0; index--){
			eDummyList = meDetectedObj.get(mnRange[index]); 
			eDummyList.removeAll(meDetectedObj.get(mnRange[index-1])); 
			meDetectedObj.put(mnRange[index],eDummyList);
			eDummyList.clear();
		}
		return meDetectedObj; 
	}
}
