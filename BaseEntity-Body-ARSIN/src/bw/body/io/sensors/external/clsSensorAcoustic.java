/**
 * @author zeilinger
 * 18.07.2009, 17:06:30
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

import sim.physics2D.shape.Circle;
import sim.physics2D.util.Double2D;


import ARSsim.physics2D.physicalObject.clsCollidingObject;

import config.clsProperties;
import du.enums.eEntityType;
import bfg.utils.enums.eSide;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.entities.base.clsSpeech;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author Owner
 * 04.03.2013, 22:58:40
 * 
 */
public class clsSensorAcoustic extends clsSensorExt{
	public static final String P_SENSOR_MIN_DISTANCE = "sensor_min_distance";
	public static final String P_SENSOR_MAX_DISTANCE = "sensor_max_distance";
	
	private ArrayList<clsCollidingObject> moSensorDataDeliveredToDU = new ArrayList<clsCollidingObject>();
	private double mrMinDistance;
	private double mrMaxDistance;
   
	public clsSensorAcoustic(String poPrefix, clsProperties poProp,clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);

		applyProperties(poPrefix, poProp);
		String pre = clsProperties.addDot(poPrefix);
		
		mrMinDistance = poProp.getPropertyDouble(pre+P_SENSOR_MIN_DISTANCE);
		mrMaxDistance = poProp.getPropertyDouble(pre+P_SENSOR_MAX_DISTANCE);
	}


	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);

		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, 2*Math.PI);
		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 0);
		oProp.setProperty(pre+P_SENSOR_MAX_DISTANCE, 60);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		mrMinDistance = poProp.getPropertyDouble(pre+P_SENSOR_MIN_DISTANCE);
		mrMaxDistance = poProp.getPropertyDouble(pre+P_SENSOR_MAX_DISTANCE);
		if(mrMaxDistance < 0) {
			mrMaxDistance = nRange;
		}
		if (mrMinDistance > mrMaxDistance) {
			throw new java.lang.IllegalArgumentException("min > max");
		}
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
										 poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
	
		//HZ -- initialize sensor engine - defines the maximum sensor range
		assignSensorData(oOffset, nRange, nFieldOfView);		

	}	


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_ACOUSTIC;	
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName="ext. Sensor Acoustic";
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	public void computeDataDeliveredToDU(){
		moSensorDataDeliveredToDU.clear(); 
		HashMap<Double, ArrayList<clsCollidingObject>> oDetectedObjectList = moSensorData.getMeDetectedObject(); 
			
		for(Map.Entry<Double, ArrayList<clsCollidingObject>> oEntry : oDetectedObjectList.entrySet()){
			for (clsCollidingObject oCollidingObject : oEntry.getValue()){
				if (oCollidingObject.moEntity.isEntityType(eEntityType.SPEECH) && ((clsSpeech) oCollidingObject.moEntity).getAbstractSpeech().getProceedings().equals("S3"))
					moSensorDataDeliveredToDU.add(oCollidingObject); 
			}
		}
	}
	
	private void calculateObjWithinDistance(ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		
		for( Iterator<clsCollidingObject> it = peDetectedObjInAreaList.iterator(); it.hasNext(); ) {
			clsCollidingObject oSensorEntry = it.next();
			//get colliding objects position relative to THIS object
			if(oSensorEntry.moCollider.getShape() instanceof Circle && (oSensorEntry.mrColPoint.mrLength < mrMinDistance || oSensorEntry.mrColPoint.mrLength >= mrMaxDistance) ) {
				it.remove();
			}
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.07.2009, 14:59:59
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {

		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList);
		computeDataDeliveredToDU(); 
	}


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 11:02:59
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
			
		    // necessary for DU because it sets collision points
			//TODO: Commented out from LH (2013-10-29) because this statement corrupts the values of all other sensors
			//setColPos(peDetectedObjInAreaList);
			
			calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList);	
			calculateObjWithinDistance(peDetectedObjInAreaList);
			// FIXME (horvath)
			calculateRegisteredObjects(peDetectedObjInAreaList);
	}
	
	private void setColPos(ArrayList<clsCollidingObject> peDetectedObjectList){
		Iterator <clsCollidingObject> itr = peDetectedObjectList.iterator(); 
		
		while(itr.hasNext()){
			clsCollidingObject oCollidingObject = itr.next(); 
			//TODO ()

			oCollidingObject.meColPos = eSide.CENTER;
		}
	}
	
	public ArrayList<clsCollidingObject> getSensorData() {
		return moSensorDataDeliveredToDU; 
	}

}
