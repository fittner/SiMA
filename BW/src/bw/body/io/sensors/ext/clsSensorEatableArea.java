/**
 * @author zeilinger
 * 18.07.2009, 17:07:05
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

import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.utils.enums.eBodyParts;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 17:07:05
 * 
 */

public class clsSensorEatableArea extends clsSensorExt{

   private ArrayList<PhysicalObject2D> meSensorDataDeliveredToDU = new ArrayList<PhysicalObject2D>(); 	

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 18.07.2009, 17:08:12
	 *
	 * @param poBaseIO
	 * @param poConfig
	 * @param poSensorEngine
	 */


	public clsSensorEatableArea(String poPrefix, clsBWProperties poProp,clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre) );
		
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
	
	public ArrayList<PhysicalObject2D> getSensorData(){
		return meSensorDataDeliveredToDU; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:07:41
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.07.2009, 14:59:07
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			HashMap<Integer, Double2D> peCollisionPointList) {
		// TODO (zeilinger) - Auto-generated method stub
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList);
		computeDataDeliveredToDU(); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 10:50:37
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			HashMap<Integer, Double2D> peCollisionPointList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
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
	 * 18.07.2009, 17:07:41
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION_EATABLE_AREA;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:07:41
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Vision Eatable Area";
		
	}
}
