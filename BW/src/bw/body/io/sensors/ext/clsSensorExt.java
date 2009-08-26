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
import java.util.Iterator;

import config.clsBWProperties;

import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
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
			  							   ArrayList<clsCollidingObject> peDetectedObjInAreaList);

	
	/* The methods calculateObjInFieldOfView, processObjInAreaList, getPolarCoordinates, and
	 * evaluateIfObjInFieldOfView are sensor specific methods which need not to be  
	 * */
	
	public void calculateObjInFieldOfView(Double pnAreaRange, 
										   ArrayList<clsCollidingObject> peDetectedObjectList){
 			//if(peDetectedObjectList.size()>0){
				updateDetectedObjAndCollisionPointList(peDetectedObjectList);	
				moSensorData.setMeDetectedObjectList(pnAreaRange, peDetectedObjectList); 
			//}
	}
	
	private void updateDetectedObjAndCollisionPointList(ArrayList<clsCollidingObject> peDetectedObjectList){
		if(moSensorData.mnFieldOfView<2*Math.PI){
			processObjInAreaList(peDetectedObjectList); 
		}
	}
		
	private void processObjInAreaList(ArrayList<clsCollidingObject> peDetectedObjectList){
		Iterator <clsCollidingObject> itr = peDetectedObjectList.iterator(); 
		
		while(itr.hasNext()){
			clsCollidingObject oCollidingObject = itr.next(); 
			//TODO ()
	     	if(!evaluateIfObjInFieldOfView(oCollidingObject)){ //.moCollider, oCollidingObject.moCollider.getPosition())){
	     		itr.remove(); 
	     	}
		}
	}
	
//	private clsPolarcoordinate getPolarCoordinates (Double2D poCollisionPoint){
//		return moSensorData.getRelativeCollisionPosition(poCollisionPoint);
//	}
	
	private boolean evaluateIfObjInFieldOfView(clsCollidingObject poCollidingObject){
		double nEntityOrientation =  moSensorEngine.getMeSensorAreas().firstEntry().getValue().getOrientation().radians;
		if(moSensorData.checkIfObjectInView(poCollidingObject.mrColPoint.moAzimuth.radians, nEntityOrientation, 
											moSensorData.mnFieldOfView)){	
				return true;  
		}
		return false; 
	}
	
	public abstract void setDetectedObjectsList(Double pnAreaRange,ArrayList<clsCollidingObject> peObjInAreaList);  
}
