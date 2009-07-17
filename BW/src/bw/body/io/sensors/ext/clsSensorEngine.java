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
import bw.exceptions.exInvalidSensorRange;

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
	
	private HashMap<Double,ArrayList<clsSensorData>> meRegisteredSensor;
	private HashMap <Double,ArrayList<PhysicalObject2D>> meDetectedObj;
	private TreeMap<Double,clsEntitySensorEngine> meEntities;
	private double[] mnRange = {20,40,60};
		
	public clsSensorEngine(clsEntity poHostEntity){
		meRegisteredSensor = new HashMap<Double,ArrayList<clsSensorData>>();
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
	
		    if(meRegisteredSensor.containsKey(poSensorData.getSensorRange())){
				((ArrayList<clsSensorData>)this.meRegisteredSensor.get(poSensorData.getSensorRange()))
																  .add(poSensorData);
			}
			else if (checkRange(poSensorData)){
				ArrayList <clsSensorData> meSensorData = new ArrayList<clsSensorData>();
				meSensorData.add(poSensorData); 
				meRegisteredSensor.put(poSensorData.getSensorRange(),meSensorData);
			}
			else{
				try {
					throw new exInvalidSensorRange(mnRange,poSensorData.getSensorRange());
				} catch (exInvalidSensorRange e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public boolean checkRange(clsSensorData poSensorData){
		for(int index=0; index<mnRange.length;index++ ){
			if(((Double)mnRange[index]).equals(poSensorData.getSensorRange())){
				return true; 
			}
		}
		return false; 
	}
	
	public void updateSensorData() {
		// TODO Auto-generated method stub
		HashMap <Double,ArrayList<PhysicalObject2D>> eDetectedObj = requestSensorData(); 
	   	Iterator <Double> rangeItr = meRegisteredSensor.keySet().iterator();
			
		while(rangeItr.hasNext()){
			double nRange = rangeItr.next();
			Iterator <clsSensorData> itr = ((ArrayList<clsSensorData>)meRegisteredSensor.get(nRange)).iterator();
						
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
		getSensorData(); 
		sortOutData(); 
		
		return meDetectedObj; 
	}
	
	private void getSensorData(){
		for(int index=0; index < mnRange.length; index++){
			meDetectedObj.put(mnRange[index], meEntities.get(mnRange[index]).requestSensorData());
		}
	}
	
	private void sortOutData(){
		ArrayList <PhysicalObject2D> eDummyList;
		
		for(int index=mnRange.length-1; index > 0; index--){
			eDummyList = meDetectedObj.get(mnRange[index]); 
			eDummyList.removeAll(meDetectedObj.get(mnRange[index-1])); 
			meDetectedObj.put(mnRange[index],eDummyList);
			eDummyList.clear();
		}
	}
	
}
