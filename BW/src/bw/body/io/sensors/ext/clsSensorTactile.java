/**
 * @author zeilinger
 * 27.07.2009, 11:16:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;
import java.util.HashMap;

import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 27.07.2009, 11:16:25
 * 
 */
public class clsSensorTactile extends clsSensorExt {

	public clsSensorTactile(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsSensorEngine poSensorEngine, clsEntity poEntity) {
		super(poPrefix, poProp, poBaseIO, poSensorEngine, poEntity);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_SENSOR_RANGE, 0.0 );
				
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
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 11:16:44
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			HashMap<Integer, Double2D> peCollisionPointList) {
		calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 11:16:44
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			HashMap<Integer, Double2D> peCollisionPointList) {
		
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 11:16:44
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
	 * 27.07.2009, 11:16:44
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Acoustic";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 11:16:44
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub
		
	}

}
