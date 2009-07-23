/**
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.util.clsPolarcoordinate;
import bw.body.io.clsBaseIO;
//import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.sensors.itfSensorUpdate;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;


/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 */
public abstract class clsSensorExt extends bw.body.io.sensors.external.clsSensorExt implements itfSensorUpdate {//clsSensorActuatorBaseExt implements itfSensorUpdate {

	protected clsSensorData moSensorData; 
	private clsSensorEngine moSensorEngine;  
	private clsEntity moHostEntity;
	

	/**
	 * @param poBaseIO
	 */
	public clsSensorExt(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO,
								clsSensorEngine poSensorEngine, clsEntity poEntity) {
		super(poPrefix, poProp);
		moSensorEngine = poSensorEngine;
		moHostEntity = poEntity; 
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
	}		
	
	public abstract void updateSensorData(Double pnRange, 
										  ArrayList<PhysicalObject2D> peDetectedObj,
										  HashMap<Integer, Double2D> peCollisionPoints);


	public void assignSensorData(clsSensorExt poSensor,Double2D poSensorPosition,
										Double mnRange, Double mnAngle){
		moSensorData = new clsSensorData(poSensor, poSensorPosition, mnRange, mnAngle); 
		moSensorEngine.registerSensor(this);
	}
	
	public clsSensorData getSensorDataObj(){
		return moSensorData; 
	}
	
	
	/* The methods calculateObjInFieldOfView, processObjInAreaList, getPolarCoordinates, and
	 * evaluateIfObjInFieldOfView are sensor specific methods which need not to be  
	 * */
	
	public void calculateObjInFieldOfView(Double pnAreaRange, 
										   ArrayList<PhysicalObject2D> peObjInAreaList,
										   HashMap<Integer, Double2D> peCollisionPointList){
 
	    ArrayList<PhysicalObject2D> eObjInAreaListTemp = new ArrayList <PhysicalObject2D>(); 
		HashMap<Integer, Double2D> eCollisionPointListTemp = new HashMap<Integer, Double2D>(); 
							
			if(peObjInAreaList.size()>0 && moSensorData.getSensorAngle()<2*Math.PI){
				processObjInAreaList(eObjInAreaListTemp, eCollisionPointListTemp, 
									 peObjInAreaList, peCollisionPointList); 
			}
			else {
				eObjInAreaListTemp = peObjInAreaList; 
				eCollisionPointListTemp = peCollisionPointList;
			}
		
		moSensorData.setMeDetectedObjectList(pnAreaRange, eObjInAreaListTemp); 
		moSensorData.setMeCollisionPointList(pnAreaRange, eCollisionPointListTemp); 
	}
	
	
	
	private void processObjInAreaList(ArrayList<PhysicalObject2D> peObjInAreaListTemp,HashMap<Integer, Double2D> peCollisionPointListTemp, 
								  	 ArrayList<PhysicalObject2D> peObjInAreaList, HashMap<Integer, Double2D> peCollisionPointList){
		
		Iterator <PhysicalObject2D> itr = peObjInAreaList.iterator(); 
		while(itr.hasNext()){
			PhysicalObject2D oPhysicalObject = itr.next(); 
			clsPolarcoordinate oRel = getPolarCoordinates(peCollisionPointList, oPhysicalObject.getIndex());
			evaluateIfObjInFieldOfView(oRel, oPhysicalObject, peObjInAreaListTemp, peCollisionPointListTemp); 
		}
	}
	
	
	
	private clsPolarcoordinate getPolarCoordinates (HashMap<Integer, Double2D> peCollisionPointList, 
														Integer nPhysicalObjectIndex){
		
		return moSensorData.getRelativeCollisionPosition(peCollisionPointList
						   .get(nPhysicalObjectIndex));
	}
	
	
	
	private void evaluateIfObjInFieldOfView(clsPolarcoordinate poRel, PhysicalObject2D poPhysicalObject,
										ArrayList<PhysicalObject2D> peObjInAreaList,
										HashMap<Integer,Double2D> peCollisionPointList){
		
		if(moSensorData.checkIfObjectInView(poRel.moAzimuth.radians, 
				moHostEntity.getPose().getAngle().radians, moSensorData.getSensorAngle())){
	
				peObjInAreaList.add(poPhysicalObject); 
				peCollisionPointList.put(poPhysicalObject.getIndex(),
						                 peCollisionPointList.get(poPhysicalObject.getIndex())); 
		}
	}
}
