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

import config.clsBWProperties;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.util.clsPolarcoordinate;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.body.io.sensors.itfSensorUpdate;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 */
public abstract class clsSensorExt extends bw.body.io.sensors.external.clsSensorExt implements itfSensorUpdate {//clsSensorActuatorBaseExt implements itfSensorUpdate {

	public static final String P_SENSOR_FIELD_OF_VIEW = "sensor_field_of_view";
	public static final String P_SENSOR_OFFSET_X = "sensor_offset_X";
	public static final String P_SENSOR_OFFSET_Y = "sensor_offset_Y";
	
	public clsSensorData moSensorData; 
	private clsSensorEngine moSensorEngine;  
	
	/**
	 * @param poBaseIO
	 */
	public clsSensorExt(String poPrefix, clsBWProperties poProp,  
						clsBaseIO poBaseIO) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
		moSensorEngine = ((clsExternalIO)poBaseIO).moSensorEngine; 			
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, 2*Math.PI);
		oProp.setProperty(pre+P_SENSOR_OFFSET_X, 0.0);
		oProp.setProperty(pre+P_SENSOR_OFFSET_Y, 0.0);
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
	}		
	
	public void assignSensorData(Double2D poSensorOffset,Double pnRange, Double pnAngle){
		moSensorData = new clsSensorData(poSensorOffset,pnRange, pnAngle); 
	}
	
	public clsSensorData getSensorDataObj(){
		return moSensorData; 
	}
	
	public abstract void updateSensorData(Double pnAreaRange, 
			  ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			  HashMap<Integer, Double2D> peCollisionPointList);

	
	/* The methods calculateObjInFieldOfView, processObjInAreaList, getPolarCoordinates, and
	 * evaluateIfObjInFieldOfView are sensor specific methods which need not to be  
	 * */
	
	public void calculateObjInFieldOfView(Double pnAreaRange, 
										   ArrayList<PhysicalObject2D> peDetectedObjectList,
										   HashMap<Integer, Double2D> peCollisionPointList){
 
			if(peDetectedObjectList.size()>0){
				updateDetectedObjAndCollisionPointList(peDetectedObjectList, peCollisionPointList);	
				moSensorData.setMeDetectedObjectList(pnAreaRange, clsTemporaryDetectedObjHolder.meDetectedObjectList); 
				moSensorData.setMeCollisionPointList(pnAreaRange, clsTemporaryDetectedObjHolder.meCollisionPointList); 
			}
	}
	
	public void updateDetectedObjAndCollisionPointList(ArrayList<PhysicalObject2D> peDetectedObjectList,
													   HashMap<Integer, Double2D> peCollisionPointList){
		if(moSensorData.mnFieldOfView<2*Math.PI){
			clsTemporaryDetectedObjHolder.clearList(); 
			processObjInAreaList(peDetectedObjectList, peCollisionPointList); 
		}
		else{
			clsTemporaryDetectedObjHolder.meDetectedObjectList = peDetectedObjectList; 
			clsTemporaryDetectedObjHolder.meCollisionPointList = peCollisionPointList; 
		}
	}
	
	
	private void processObjInAreaList(ArrayList<PhysicalObject2D> peDetectedObjectList, HashMap<Integer, Double2D> peCollisionPointList){
		
		Iterator <PhysicalObject2D> itr = peDetectedObjectList.iterator(); 
		while(itr.hasNext()){
			PhysicalObject2D oPhysicalObject = itr.next(); 
			int nPhysicalObjIndex = oPhysicalObject.getIndex(); 
			Double2D oCollisionPoint = peCollisionPointList.get(nPhysicalObjIndex); 
			clsPolarcoordinate oRel = getPolarCoordinates(oCollisionPoint);
	
			evaluateIfObjInFieldOfView(oRel.moAzimuth.radians, oPhysicalObject, oCollisionPoint); 
		}
	}
	
	private clsPolarcoordinate getPolarCoordinates (Double2D poCollisionPoint){
		return moSensorData.getRelativeCollisionPosition(poCollisionPoint);
	}
	
	private void evaluateIfObjInFieldOfView(double poRelativeCoordinatesRad, PhysicalObject2D poPhysicalObject,
											Double2D poCollisionPoint){
		double nOrientation = moSensorEngine.getMeSensorAreas().firstEntry()
												.getValue().getOrientation().radians;
		if(moSensorData.checkIfObjectInView(poRelativeCoordinatesRad, nOrientation, 
											moSensorData.mnFieldOfView)){
	
				clsTemporaryDetectedObjHolder.meDetectedObjectList.add(poPhysicalObject); 
				clsTemporaryDetectedObjHolder.meCollisionPointList.put(poPhysicalObject.getIndex(),poCollisionPoint); 
		}
	}
	
	public abstract void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peObjInAreaList, 
			HashMap<Integer, Double2D> peCollisionPointList);
	
	private abstract static class clsTemporaryDetectedObjHolder{
		public static ArrayList<PhysicalObject2D> meDetectedObjectList;
		public static HashMap<Integer,Double2D> meCollisionPointList; 
		
		public static void clearList(){
			meDetectedObjectList.clear();
			meCollisionPointList.clear(); 
		}
	}
}
