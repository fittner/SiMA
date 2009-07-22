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
	
	private HashMap<Double,ArrayList<clsSensorExt>> meRegisteredSensor;
	private HashMap <Double,ArrayList<PhysicalObject2D>> meDetectedObj;
	private TreeMap<Double,clsEntitySensorEngine> meEntities;
	private double[] mnRange = {20,40,60}; //HZ -- has to be set in increasing sequence 
		
	public clsSensorEngine(clsEntity poHostEntity){
		meRegisteredSensor = new HashMap<Double,ArrayList<clsSensorExt>>();
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
	public void registerSensor(clsSensorExt poSensor){
		double nSensorRange = poSensor.getSensorData().getSensorRange(); 
		
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
		HashMap <Double,ArrayList<PhysicalObject2D>> eDetectedObj = requestSensorData(); 
	   	Iterator <Double> rangeItr = meRegisteredSensor.keySet().iterator();
			
		while(rangeItr.hasNext()){
			double nRange = rangeItr.next();
			Iterator <clsSensorExt> itr = ((ArrayList<clsSensorExt>)meRegisteredSensor.get(nRange)).iterator();
						
			while(itr.hasNext()){
				((clsSensorExt)itr.next()).updateSensorData(nRange, eDetectedObj.get(nRange)); 
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
	
	@SuppressWarnings("unchecked")
	private void getSensorData(){
		for(int index=0; index < mnRange.length; index++){
			meDetectedObj.put(mnRange[index], 
								((ArrayList<PhysicalObject2D>)(meEntities.get(mnRange[index]).requestSensorData()).clone()));
		}
	}
	
	private void sortOutData(){
		//ArrayList <PhysicalObject2D> eDummyList;
		
		for(int index=mnRange.length-1; index > 0; index--){
			ArrayList <PhysicalObject2D> eDummyList = meDetectedObj.get(mnRange[index]); 
			
			eDummyList.removeAll(meDetectedObj.get(mnRange[index-1])); 
			meDetectedObj.put(mnRange[index],eDummyList);
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
