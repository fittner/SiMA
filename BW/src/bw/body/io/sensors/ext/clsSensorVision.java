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

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
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
public class clsSensorVision extends clsSensorExt {

   private ArrayList<PhysicalObject2D> meSensorDataDeliveredToDU = new ArrayList<PhysicalObject2D>(); 	
	
   public clsSensorVision(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
										 poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
	
		//HZ -- initialise sensor engine - defines the maximum sensor range
		assignSensorData(oOffset, nRange, nFieldOfView);			
	}
	/*has to be implemented - return SensorData to Decision Unit,
	 * Actual, only the detected physical objects summarized in 
	 * one ArrayList are returned. 
	 * TODO: imply the sensor specific computation  
     */	
	public ArrayList<PhysicalObject2D> getSensorData(){
		return meSensorDataDeliveredToDU; 
	}

	@Override
	public void updateSensorData(Double pnAreaRange, 
										ArrayList<PhysicalObject2D> peDetectedObjInAreaList, 
										HashMap<Integer, Double2D> peCollisionPointList) {
		// TODO (zeilinger) - Auto-generated method stub
	
		//System.out.println("Range " + pnRange + "  " + peObj.size());
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList);
		computeDataDeliveredToDU(); 
    }
	
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
										ArrayList<PhysicalObject2D> peDetectedObjInAreaList, 
										HashMap<Integer, Double2D> peCollisionPointList){
		
		calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList); 
	}
	
	/*has to be implemented - return SensorData to Decision Unit,
	 * Actual, only the detected physical objects summarized in 
	 * one ArrayList are returned. 
	 * TODO: imply the sensor specific computation  
     */	
	public void computeDataDeliveredToDU(){
		meSensorDataDeliveredToDU.clear(); 
		HashMap<Double, ArrayList<PhysicalObject2D>> eDetectedObjectList = moSensorData.getMeDetectedObject(); 
		for(ArrayList<PhysicalObject2D> element : eDetectedObjectList.values())
		{
			Iterator <PhysicalObject2D> itr = element.iterator(); 
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
