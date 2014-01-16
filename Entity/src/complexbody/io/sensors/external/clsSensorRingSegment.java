/**
 * @author zeilinger
 * 14.07.2009, 08:01:45
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.sensors.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import complexbody.io.clsBaseIO;
import complexbody.io.clsExternalIO;
import complexbody.io.sensors.external.clsSensorExt;

import physics2D.physicalObject.clsCollidingObject;
import properties.clsProperties;

import entities.enums.eBodyParts;
import sim.physics2D.shape.Circle;
import sim.physics2D.util.Double2D;

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
	
   private ArrayList<clsCollidingObject> moSensorDataDeliveredToDU = new ArrayList<clsCollidingObject>();
   private double mrMinDistance;
   private double mrMaxDistance;
   private double mrOffsetX;
	
   public clsSensorRingSegment(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, Math.PI);
		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 0);
		oProp.setProperty(pre+P_SENSOR_MAX_DISTANCE, -1);
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
		return moSensorDataDeliveredToDU; 
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
	private void calculateObjWithinDistance(ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		
		for( Iterator<clsCollidingObject> it = peDetectedObjInAreaList.iterator(); it.hasNext(); ) {
			clsCollidingObject oSensorEntry = it.next();
			//get colliding objects position relative to THIS object
			if(!oSensorEntry.moEntity.equals(moSensorEngine.getHostEntity())){
				//self will be removed because mrLength == mrMaxDistance
				if(oSensorEntry.moCollider.getShape() instanceof Circle && (oSensorEntry.mrColPoint.mrLength < mrMinDistance || oSensorEntry.mrColPoint.mrLength >= mrMaxDistance) ) {
					it.remove();
				}
			}
			else{
				//System.out.println(oSensorEntry);
			}
		}
	}

	/*has to be implemented - return SensorData to Decision Unit,
	 * Actual, only the detected physical objects summarized in 
	 * one ArrayList are returned. 
	 * TODO: implement the sensor specific computation  
     */	
	public void computeDataDeliveredToDU(){
		moSensorDataDeliveredToDU.clear(); 
		HashMap<Double, ArrayList<clsCollidingObject>> oDetectedObjectList = moSensorData.getMeDetectedObject(); 
			
		for(Map.Entry<Double, ArrayList<clsCollidingObject>> oEntry : oDetectedObjectList.entrySet()){
			moSensorDataDeliveredToDU.addAll(oEntry.getValue()); 
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
