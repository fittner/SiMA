/**
 * @author zeilinger
 * 14.07.2009, 08:01:45
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
import sim.physics2D.shape.Circle;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.utils.enums.eBodyParts;
import bw.body.io.sensors.ext.clsSensorExt;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 14.07.2009, 08:01:45
 * 
 */
public class clsSensorRingSegment extends clsSensorExt {

	public static final String P_SENSOR_MIN_DISTANCE = "sensor_min_distance";
	public static final String P_SENSOR_MAX_DISTANCE = "sensor_max_distance";
	
   private ArrayList<clsCollidingObject> meSensorDataDeliveredToDU = new ArrayList<clsCollidingObject>();
   private double mrMinDistance;
   private double mrMaxDistance;
   private double mrOffsetX;
	
   public clsSensorRingSegment(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, Math.PI);
		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 0);
		oProp.setProperty(pre+P_SENSOR_MAX_DISTANCE, -1);
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
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
		
		assignCenterOfField(nFieldOfView, mrMinDistance, mrMaxDistance);
	}
	
	private void assignCenterOfField(double prFieldOfView, double prMin, double prMax) {
		//NOTE: this is a rough estimation!
		
		double epsilon=0.000000001;
		
		if (prFieldOfView >= (Math.PI - epsilon)) {
			mrOffsetX = 0;
		} else {
			double einheitsoffset = ((2*Math.PI - prFieldOfView) / (2*Math.PI) ) * 0.5;
			double maxoffset = einheitsoffset * prMax;
			double minoffset = einheitsoffset * prMin;
			mrOffsetX = (maxoffset+minoffset) / 2;
		}
	}
	
	public double getOffsetX() {
		//value is in "Roboterkoordinaten" not "worldkoordinaten"
		return mrOffsetX;
	}
	
	/*has to be implemented - return SensorData to Decision Unit,
	 * Actual, only the detected physical objects summarized in 
	 * one ArrayList are returned. 
	 * TODO: imply the sensor specific computation  
     */	
	public ArrayList<clsCollidingObject> getSensorData(){
		return meSensorDataDeliveredToDU; 
	}

	@Override
	public void updateSensorData(Double pnAreaRange, 
										ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList);
		computeDataDeliveredToDU(); 
    }
	
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
								ArrayList<clsCollidingObject> peDetectedObjInAreaList){
		calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList);
		calculateObjWithinDistance(peDetectedObjInAreaList);
		// FIXME (horvath)
		calculateRegisteredObjects(peDetectedObjInAreaList);
	}
	
	/**
	 * Removes vision entries that are below the min-distance or above the max-distance of the vision sensor 
	 * Purpose: Realization of vision-sensors valid from e.g. range 45 to 55 (different of the 3 ranges)
	 *
	 * @author langr
	 * 07.09.2009, 14:25:38
	 *
	 * @param peDetectedObjInAreaList
	 */
	private void calculateObjWithinDistance(
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		
		for( Iterator<clsCollidingObject> it = peDetectedObjInAreaList.iterator(); it.hasNext(); ) {
			clsCollidingObject oSensorEntry = it.next();
			//get colliding objects position relative to THIS object
			if(oSensorEntry.moCollider.getShape() instanceof Circle && (oSensorEntry.mrColPoint.mrLength < mrMinDistance || oSensorEntry.mrColPoint.mrLength >= mrMaxDistance) ) {
				it.remove();
			}
		}
	}

	/*has to be implemented - return SensorData to Decision Unit,
	 * Actual, only the detected physical objects summarized in 
	 * one ArrayList are returned. 
	 * TODO: implement the sensor specific computation  
     */	
	public void computeDataDeliveredToDU(){
		meSensorDataDeliveredToDU.clear(); 
		HashMap<Double, ArrayList<clsCollidingObject>> eDetectedObjectList = moSensorData.getMeDetectedObject(); 
		
		Iterator <Double>itrRange = eDetectedObjectList.keySet().iterator(); 
		while(itrRange.hasNext())
		{
			Double oKey = itrRange.next();
			Iterator <clsCollidingObject> itr = eDetectedObjectList.get(oKey).iterator(); 
			while(itr.hasNext()){
				meSensorDataDeliveredToDU.add(itr.next()); 
			}
		}
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.07.2009, 12:48:42
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.util.ArrayList)
	 * Integrate Sensor Engine - new SensorExt
	 */
	
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION;
	}
	
	@Override
	protected void setName() {
		moName = "ext. Sensor Vision";
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.07.2009, 23:26:09
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO (zeilinge) - Auto-generated method stub
	}

	
}
