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

import enums.eSensorExtType;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.entities.clsEntity;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.utils.config.clsBWProperties;
import bw.exceptions.exInvalidSensorRange;

/**
 * DOCUMENT (zeilinger) - insert description 
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
	
	private HashMap<eSensorExtType,clsSensorExt> meRegisteredSensors; 
	private HashMap<Double,ArrayList<clsSensorExt>> meRegisteredSensorAtRange;
	private HashMap <Double,ArrayList<PhysicalObject2D>> meDetectedObj;
	private HashMap <Double, HashMap<Integer, Double2D>> meCollisionPoints;
	private TreeMap<Double,clsEntitySensorEngine> meEntities;
	private double[] mnRange; //HZ -- has to be set in increasing sequence
		
	public clsSensorEngine(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO){
		meRegisteredSensors = new HashMap<eSensorExtType,clsSensorExt>();
		meRegisteredSensorAtRange = new HashMap<Double,ArrayList<clsSensorExt>>();
		meDetectedObj = new HashMap <Double,ArrayList<PhysicalObject2D>>();
		meCollisionPoints = new HashMap<Double,HashMap <Integer, Double2D>>(); 
		meEntities = new TreeMap<Double, clsEntitySensorEngine>(); 
		
		applyProperties(poPrefix, poProp);
		registerEngineEntity(((clsExternalIO)poBaseIO).moEntity); 
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsExternalIO.getDefaultProperties(pre));
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);
		
//		int nNumSteps = poProp.getPropertyInt(pre+clsExternalIO.P_NUMSTEPS);
//		double nStepRange = poProp.getPropertyDouble(pre+clsExternalIO.P_MAX_RANGE)/nNumSteps;
//		adaptSensorEngineRange(nNumSteps, nStepRange); 
	}
	
	/*The default diameters of the sensor-engine entities are defined in mnRange. 
	 * As the hostEntities - entity which the sensors are assigned to - is not 
	 * punctual but from a specific size, mnRange is adapted to this size during 
	 * the initialization.    
	 * 
	 */
//	private void adaptSensorEngineRange(Integer pnNumSteps, Double pnStepRange){
//		mnRange = new double[pnNumSteps];
//		
//		for(int index = 0;index < pnNumSteps; index++){
//			mnRange[index] = pnStepRange * (index+1); 
//		}
//	}
	
	private void registerEngineEntity(clsEntity poHostEntity){
		for(int index=0; index < mnRange.length; index++){
			meEntities.put(mnRange[index],new clsEntitySensorEngine(poHostEntity,mnRange[index]));
		} 
	}
	
	//every sensor has to register itself in the Sensor List
	public void registerSensor(eSensorExtType oSensorType, clsSensorExt poSensor){
		double nSensorRange = poSensor.moSensorData.mnRange; 
	
		if(checkRange(nSensorRange)){
			setMeRegisteredSensors(oSensorType, poSensor);
			registerSensorAtRanges(poSensor);
		}
		else{
			throwExInvalidSensorRange(nSensorRange); 
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
	
	private void setMeRegisteredSensors(eSensorExtType poSensorType,clsSensorExt peSensor){
		meRegisteredSensors.put(poSensorType,peSensor); 
	}
	
	public HashMap<eSensorExtType, clsSensorExt> getMeRegisteredSensors(){
		return meRegisteredSensors;
	}
	
	public void registerSensorAtRanges(clsSensorExt poSensor){
		double maxSensorRange = poSensor.moSensorData.mnRange; 
				
		for(int index = 0; index< mnRange.length; index++){
			if(mnRange[index]>maxSensorRange)
				fillMeRegisteredSensor(poSensor, mnRange[index]); 
		}
	}
	
	private void fillMeRegisteredSensor(clsSensorExt poSensor, double pnRange){
	
		if(meRegisteredSensorAtRange.containsKey(pnRange)){
			registerSensorToExistingRange(poSensor, pnRange);
		}
		else {
			defineNewRangeAndRegisterSensor(poSensor, pnRange); 
		}
	}
	
	public void registerSensorToExistingRange(clsSensorExt poSensor, double pnRange){
		((ArrayList<clsSensorExt>)this.meRegisteredSensorAtRange.get(pnRange)).add(poSensor);
	}
	
	public void defineNewRangeAndRegisterSensor(clsSensorExt poSensor, double pnRange){
		ArrayList <clsSensorExt> meSensor = new ArrayList<clsSensorExt>();
		meSensor.add(poSensor); 
		meRegisteredSensorAtRange.put(pnRange,meSensor);
	}
	
	public void updateSensorData() {
		// TODO (zeilinger) - Auto-generated method stub
		clearActualCollisonAndDetectedObjList();
		requestSensorData(); 
		updateSensorDataAtRanges(); 
	}
	
	public void clearActualCollisonAndDetectedObjList(){
		meDetectedObj.clear(); 
		meCollisionPoints.clear();
	}
	
	public void requestSensorData(){
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
	
	public void updateSensorDataAtRanges(){
		Iterator <Double> rangeItr = meRegisteredSensorAtRange.keySet().iterator();
		
		while(rangeItr.hasNext()){
			double nRange = rangeItr.next();
			updateSensorDataAtSpecificRange(nRange);
		}
	}
	
	public void updateSensorDataAtSpecificRange(double pnRange){
		Iterator <clsSensorExt> itr = ((ArrayList<clsSensorExt>)meRegisteredSensorAtRange.get(pnRange)).iterator();
	
		while(itr.hasNext()){
			((clsSensorExt)itr.next()).updateSensorData(pnRange, meDetectedObj.get(pnRange),
														        meCollisionPoints.get(pnRange)); 
		}
	}
	
	public TreeMap<Double, clsEntitySensorEngine> getMeSensorAreas(){
		return meEntities; 
	}
	
	private void throwExInvalidSensorRange(double pnSensorRange){
		try {
			throw new exInvalidSensorRange(mnRange,pnSensorRange);
		} catch (exInvalidSensorRange e) {
			e.printStackTrace();
		}
	}
	
}
