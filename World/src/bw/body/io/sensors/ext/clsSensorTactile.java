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

import ARSsim.physics2D.physicalObject.clsCollidingObject;
import config.clsProperties;

import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.utils.enums.eBodyParts;

import sim.physics2D.util.Double2D;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 27.07.2009, 11:16:25
 * 
 */
public class clsSensorTactile extends clsSensorExt {

	public clsSensorTactile(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
    	clsProperties oProp = new clsProperties();
	
		oProp.putAll(clsSensorExt.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
																 poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
	
		assignSensorData(oOffset, nRange, nFieldOfView);					
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
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList); 
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
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		
		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList);
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
		mePartId = eBodyParts.SENSOR_EXT_TACTITLE;
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
		moName = "ext. Sensor Tactile";
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
		// TODO (zeilinger) - Auto-generated method stub
		
	}

}
