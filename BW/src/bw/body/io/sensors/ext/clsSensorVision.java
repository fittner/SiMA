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

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;
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
		
   public clsSensorVision(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsSensorEngine poSensorEngine, clsEntity poEntity) {
		super(poPrefix, poProp, poBaseIO, poSensorEngine, poEntity);
		applyProperties(poPrefix, poProp);
	}
	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, Math.PI );
		oProp.setProperty(pre+P_SENSOR_RANGE, 60.0 );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		Double nFieldOfView = poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		Double nRange = poProp.getPropertyDouble(pre+P_SENSOR_RANGE);
		Double nOffset_X = poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X);
		Double nOffset_Y = poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y);
			

		//HZ -- initialise sensor engine - defines the maximum sensor range
		assignSensorData((clsSensorExt)this,new Double2D(nOffset_X, nOffset_Y), 
						  nRange, nFieldOfView);			
	}
		
//	public ArrayList<PhysicalObject2D> getSensorData(){
//		/*has to be implemented - return SensorData to Decision Unit*/
//		return null; 
//	}

	@Override
	public void updateSensorData(Double pnAreaRange, 
										ArrayList<PhysicalObject2D> peDetectedObjInAreaList, 
										HashMap<Integer, Double2D> peCollisionPointList) {
		// TODO (zeilinger) - Auto-generated method stub
	
		//System.out.println("Range " + pnRange + "  " + peObj.size());
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList);
    }
	
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
										ArrayList<PhysicalObject2D> peDetectedObjInAreaList, 
										HashMap<Integer, Double2D> peCollisionPointList){
		
		calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList); 
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
