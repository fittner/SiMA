/**
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import physics2D.physicalObject.clsCollidingObject;
import physics2D.physicalObject.clsMobileObject2D;
import physics2D.physicalObject.clsStationaryObject2D;

import config.clsProperties;
import du.enums.eSensorExtType;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.sensors.itfSensorUpdate;
import bw.entities.base.clsEntity;
import bw.utils.sensors.clsSensorDataCalculation;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 09:28:39
 * 
 */

public abstract class clsSensorExt extends clsSensorActuatorBaseExt implements itfSensorUpdate {//extends bw.body.io.sensors.external.clsSensorExt implements itfSensorUpdate {//clsSensorActuatorBaseExt implements itfSensorUpdate {

	public static final String P_SENSOR_FIELD_OF_VIEW = "sensor_field_of_view";
	public static final String P_SENSOR_OFFSET_X = "sensor_offset_X";
	public static final String P_SENSOR_OFFSET_Y = "sensor_offset_Y";
	
	public clsSensorData moSensorData; 
	protected clsSensorEngine moSensorEngine;  
	
	/**
	 * @param poBaseIO
	 */
	public clsSensorExt(String poPrefix, clsProperties poProp,  
						clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
		
		moSensorEngine = ((clsExternalIO)poBaseIO).moSensorEngine; 			
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = clsSensorActuatorBaseExt.getDefaultProperties(pre);
		
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, 2*Math.PI);
		oProp.setProperty(pre+P_SENSOR_OFFSET_X, 0.0);
		oProp.setProperty(pre+P_SENSOR_OFFSET_Y, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	}		
	
	public void assignSensorData(Double2D poSensorOffset,Double pnRange, Double pnAngle){
		moSensorData = new clsSensorData(poSensorOffset,pnRange, pnAngle); 
	}
	
	public clsSensorData getSensorDataObj(){
		return moSensorData; 
	}
	
	public abstract void updateSensorData(Double pnAreaRange, 
			  							   ArrayList<clsCollidingObject> peDetectedObjInAreaList);

	//FIXME (horvath): - this function checks whether an object is registered employing a boolean variable in clsEntity - because 
	// 					 unregistering of objects does not work reliably
	public void calculateRegisteredObjects(ArrayList<clsCollidingObject> peDetectedObjectList){
		clsEntity oEntity;
		PhysicalObject2D oObject; 
		Iterator <clsCollidingObject> itr = peDetectedObjectList.iterator(); 
		
		while(itr.hasNext()){
			// get entity
			oEntity = null;
			oObject = itr.next().moCollider;
			if (oObject instanceof clsMobileObject2D) {
				oEntity = (clsEntity) ((clsMobileObject2D) oObject).getEntity();
			} else if (oObject instanceof clsStationaryObject2D) {
				oEntity = (clsEntity) ((clsStationaryObject2D) oObject).getEntity();
			}
			
			if(oEntity != null){
				// if an object is not registered -> remove it from the visible objects list
				if(!oEntity.isRegistered()){
					itr.remove();
				}
			}
			
		}
	}
	
	/* The methods calculateObjInFieldOfView, processObjInAreaList, getPolarCoordinates, and
	 * evaluateIfObjInFieldOfView are sensor specific methods which need not to be  
	 * */
	
	public void calculateObjInFieldOfView(Double pnAreaRange, ArrayList<clsCollidingObject> peDetectedObjectList){
 				updateDetectedObjAndCollisionPointList(peDetectedObjectList);	
				moSensorData.setMeDetectedObjectList(pnAreaRange, peDetectedObjectList); 
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
			eSensorExtType oSensorType =null;
			for (Map.Entry<eSensorExtType, clsSensorExt> oEntry :moSensorEngine.getMeRegisteredSensors().entrySet()){
				if(oEntry.getValue().equals(this)){
					oSensorType = oEntry.getKey();
				}
				
			}
			if(!evaluateIfObjInFieldOfView(oCollidingObject) && !(oSensorType == eSensorExtType.VISION_SELF)){ 
	       		itr.remove(); 
	     	}
		}
	}
	
	private boolean evaluateIfObjInFieldOfView(clsCollidingObject poCollidingObject){
		boolean oRetVal = false; 
		double nEntityOrientation =  moSensorEngine.getMeSensorAreas().firstEntry().getValue().getOrientation().radians;
		
		if(clsSensorDataCalculation.checkIfObjectInView(poCollidingObject, nEntityOrientation, 
																	moSensorData.mnFieldOfView)){	
				poCollidingObject.meColPos = clsSensorDataCalculation.
													getRelativePositionOfCollidingObject(
													poCollidingObject, nEntityOrientation, 
													moSensorData.mnFieldOfView);  
				oRetVal = true;  
		}
		
		return oRetVal; 
	}
	
	public abstract void setDetectedObjectsList(Double pnAreaRange,ArrayList<clsCollidingObject> peObjInAreaList);  
}
