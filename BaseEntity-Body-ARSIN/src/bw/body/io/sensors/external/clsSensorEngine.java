/**
 * @author zeilinger
 * 13.07.2009, 11:33:45
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import physics2D.physicalObject.clsCollidingObject;
import physics2D.physicalObject.sensors.clsEntitySensorEngine;


import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Circle;
import sim.physics2D.util.Double2D;
import tools.clsPolarcoordinate;
import config.clsProperties;
import du.enums.eSensorExtType;


import bfg.utils.enums.eSide;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.entities.base.clsEntity;
import bw.entities.base.clsMobile;
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
	public static final String P_MAX_RANGE = "maxrange";
	public static final String P_RANGEDIVISION = "rangedivision";
	
	private HashMap <Double,ArrayList<clsCollidingObject>> moDetectedObj;
	private HashMap<eSensorExtType,clsSensorExt> meRegisteredSensors; 
	private HashMap<Double,ArrayList<clsSensorExt>> moRegisteredSensorAtRange;
	private TreeMap<Double,clsEntitySensorEngine> meEntities;
	private double[] mnRange; 
	private clsEntity moHostEntity; 
		
	public clsSensorEngine(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO){
		meRegisteredSensors = new HashMap<eSensorExtType,clsSensorExt>();
		moRegisteredSensorAtRange = new HashMap<Double,ArrayList<clsSensorExt>>();
		moDetectedObj = new HashMap <Double,ArrayList<clsCollidingObject>>();
		meEntities = new TreeMap<Double, clsEntitySensorEngine>(); 
		moHostEntity = ((clsExternalIO)poBaseIO).moEntity; 
		
		applyProperties(poPrefix, poProp);
		registerEngineEntity(); 
	}
	
	public clsEntity getHostEntity(){
		return moHostEntity;
	}
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre + P_MAX_RANGE, 60.0);
		oProp.setProperty(pre + P_RANGEDIVISION, 3);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		int nNumSteps = poProp.getPropertyInt(pre+P_RANGEDIVISION);
		double nStepRange = poProp.getPropertyDouble(pre+P_MAX_RANGE)/nNumSteps;
		adaptSensorEngineRange(nNumSteps, nStepRange); 
	}
	
	/*The default diameters of the sensor-engine entities are defined in mnRange. 
	 * As the hostEntities - entity which the sensors are assigned to - is not 
	 * punctual but from a specific size, mnRange is adapted to this size during 
	 * the initialization.    
	 * 
	 */

	private void adaptSensorEngineRange(Integer pnNumSteps, Double pnStepRange){
		mnRange = new double[pnNumSteps+1];
		
		for(int index = 0;index < mnRange.length; index++){
			mnRange[index] = pnStepRange * (index); 
		}
	}

	
	private void registerEngineEntity(){
		/*index = 0 => delivers the data of the objects, colliding with the HostEntity 
		 * required for e. g. Bump Sensor			
		*/
		for(int index=1; index < mnRange.length; index++){
			meEntities.put(mnRange[index],new clsEntitySensorEngine(moHostEntity,mnRange[index]));
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
		for(int index=0; index < mnRange.length;index++ ){
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
				
		if(maxSensorRange == 0.0){
			fillMeRegisteredSensor(poSensor, mnRange[0]);
		}
		else{
			iterateThroughRanges(maxSensorRange, poSensor); 
		}
	}
	
	private void iterateThroughRanges(double nMaxSensorRange, clsSensorExt poSensor){
		for(int index = 1; index< mnRange.length; index++){
			if(mnRange[index]<=nMaxSensorRange)
				fillMeRegisteredSensor(poSensor, mnRange[index]);
			}
	}
	
	private void fillMeRegisteredSensor(clsSensorExt poSensor, double pnRange){
	
		if(moRegisteredSensorAtRange.containsKey(pnRange)){
			registerSensorToExistingRange(poSensor, pnRange);
		}
		else {
			defineNewRangeAndRegisterSensor(poSensor, pnRange); 
		}
	}
	
	private void registerSensorToExistingRange(clsSensorExt poSensor, double pnRange){
		((ArrayList<clsSensorExt>)this.moRegisteredSensorAtRange.get(pnRange)).add(poSensor);
	}
	
	public void defineNewRangeAndRegisterSensor(clsSensorExt poSensor, double pnRange){
		ArrayList <clsSensorExt> meSensor = new ArrayList<clsSensorExt>();
		meSensor.add(poSensor); 
		moRegisteredSensorAtRange.put(pnRange,meSensor);
	}
	
	public void updateSensorData() {
		moDetectedObj.clear();
		requestSensorData(); 
		updateSensorDataAtRanges(); 
	}
	
	public void requestSensorData(){
		getSensorData(); 
		sortOutData(); 
	}
	/*Cast to clsMobile! no clsStationary
	 * mnRange[0] => List of objects colliding with the entity itself
	 * */
	private void getSensorData(){
		ArrayList<clsCollidingObject> oObjects = ((clsMobile)moHostEntity).getMobileObject2D().moCollisionList;
		//add entity itsself to the Sensor Data
		oObjects.add(new clsCollidingObject(moHostEntity.getPhsycalObject2D(),new clsPolarcoordinate(0,0),eSide.CENTER));
		moDetectedObj.put(mnRange[0],oObjects);
		for(int index=1; index < mnRange.length; index++){
			moDetectedObj.put(mnRange[index],meEntities.get(mnRange[index]).requestDetectedObjList());
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
				
		sortOutDuplicatedObjects(); 
		sortOutNoCollisionObjects(); 
		updateCollisionDistance();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.09.2009, 15:56:54
	 *
	 */
	private void updateCollisionDistance() {
		ArrayList <clsCollidingObject> eObjectList; 
		
		for(int index=mnRange.length-1; index > 1; index--){
			eObjectList = moDetectedObj.get(mnRange[index]);

			for( clsCollidingObject oCollision : eObjectList ) {
				if(oCollision.moCollider.getShape() instanceof Circle) {
					//FIXME (langr) - hack to get the actual distance. only valid for circle!!!
					Double2D oCurrentPosition = moHostEntity.getPose().getPosition();
					Double2D oObjectPosition = oCollision.moCollider.getPosition();
					Double2D oDiffer = oCurrentPosition.subtract(oObjectPosition);
					
					if( oDiffer.length() > mnRange[index]+oCollision.moCollider.getShape().getMaxXDistanceFromCenter() ) {
						oCollision.mrColPoint.mrLength = mnRange[index]; 
					}
					else {
						double rDistance = oDiffer.length() - oCollision.moCollider.getShape().getMaxXDistanceFromCenter();
						oCollision.mrColPoint.mrLength = rDistance;
					}
				}
			}
		}
	}

	private void sortOutDuplicatedObjects(){
		ArrayList <clsCollidingObject> eObjectList; 
		
		for(int index=mnRange.length-1; index > 1; index--){
			eObjectList = moDetectedObj.get(mnRange[index]);
			removeListEntries(eObjectList, moDetectedObj.get(mnRange[index-1])); 
			moDetectedObj.put(mnRange[index],eObjectList);
		}
	}
	
	private void sortOutNoCollisionObjects(){
		ArrayList <clsCollidingObject> eObjectList; 
		
		for(int index=mnRange.length-1; index > 0; index--){
			eObjectList = moDetectedObj.get(mnRange[index]);
			removeNoCollisionListEntries(eObjectList); 
			moDetectedObj.put(mnRange[index],eObjectList);
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - 
	 * This method is for comparing two objects of type ArrayList with each other and
	 * remove objects from list 1 which are contained in list 2 too. In this particular 
	 * case all objects which are contained in poObjectToRemoveList and in poObjectList 
	 * are removed from poObjectList. Remind that the ArrayList class already provides 
	 * a method removeAll() for such a task. However this does not work in this case 
	 * as the objects of type clsCollidingObjects are build independent from each other.
	 * hence, even they may contain the same objects of type PhysicalObject2D, these 
	 * accordances will not be shown in the lists themselves.       
	 *
	 * @author zeilinger
	 * 20.08.2009, 11:09:25
	 *
	 * @param poObjectList
	 * @param poObjectToRemoveList
	 */
	private void removeListEntries(ArrayList <clsCollidingObject> poObjectList, 
														ArrayList <clsCollidingObject> poObjectToRemoveList){

		ArrayList <PhysicalObject2D> oPhysicalObjectList = getPhysicalObjectList(poObjectToRemoveList); 
		Iterator <clsCollidingObject> itr = poObjectList.iterator(); 
				
		while (itr.hasNext()) {
			PhysicalObject2D collider = itr.next().moCollider; 
		   
			if (oPhysicalObjectList.contains(collider)) {
		    	itr.remove();
			}
		}
	}
	
	private void removeNoCollisionListEntries(ArrayList <clsCollidingObject> poObjectList){
	   Iterator <clsCollidingObject> itr = poObjectList.iterator(); 
	
	   while (itr.hasNext()) {
			PhysicalObject2D collider = itr.next().moCollider; 
			if (collider instanceof clsEntitySensorEngine) {
					itr.remove();
			}
	   }
	}
	
	private ArrayList<PhysicalObject2D> getPhysicalObjectList(ArrayList<clsCollidingObject> poObjectToRemoveList){
		ArrayList<PhysicalObject2D> oRetVal = new ArrayList<PhysicalObject2D>();
		
		for(clsCollidingObject oCollider : poObjectToRemoveList){
			oRetVal.add(oCollider.moCollider); 
		}
		
		return oRetVal; 
	}
	
	private void updateSensorDataAtRanges(){
		
		for(Map.Entry<Double, ArrayList<clsSensorExt>> oEntry : moRegisteredSensorAtRange.entrySet()){
			double nRange = oEntry.getKey(); 
			
			for(clsSensorExt oSensorExt : oEntry.getValue()){
				ArrayList<clsCollidingObject> a= new ArrayList<clsCollidingObject>(moDetectedObj.get(nRange));
				oSensorExt.updateSensorData(nRange,a);
			}
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

