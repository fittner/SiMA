/**
 * clsSensorAcceleration.java: BW - bw.body.io.sensors.ext
 * 
 * @author zeilinger
 * 30.07.2009, 10:51:00
 */
package bw.body.io.sensors.external;

import java.util.ArrayList;

import config.clsProperties;

import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.entities.base.clsEntity;
import bw.entities.base.clsMobile;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * use this sensor to get the current forces affecting the owner
 * CurrentVelocity - directing to vector er
 * AngularVelocity - directing to vector ephi (polar coordinates)
 * 
 * @author zeilinger
 * 30.07.2009, 10:51:00
 * 
 */
public class clsSensorAcceleration extends clsSensorExt{

	public static final String P_START_VELOCITY_X = "start_velocity_x";
	public static final String P_START_VELOCITY_Y = "start_velocity_y";
	
	private clsMobile moEntity;	
	
	private Double2D oldVelocity;
	private Double2D curVelocity;
	private double oldAngularVelocity;
	private double curAngularVelocity;
	
	private Double2D curAcceleration;
	private double curAngularAcceleration;
	
	private static double stepsPerSecond = 10.0; // TODO: use BW-wide definition
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 30.07.2009, 10:51:29
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poBaseIO
	 */
	public clsSensorAcceleration(String poPrefix, clsProperties poProp,
			clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		clsEntity oEntity = ((clsExternalIO)poBaseIO).moEntity; 
		
		if (! (oEntity instanceof clsMobile))
			throw new UnsupportedOperationException("Only mobile entitys may have acceleration Sensors!");
		moEntity = (clsMobile)oEntity;
		
		applyProperties(poPrefix, poProp); 
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre));
		oProp.setProperty(pre+P_START_VELOCITY_X, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_Y, 0.0);
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		curVelocity = new Double2D(poProp.getPropertyDouble(pre+P_START_VELOCITY_X),
									poProp.getPropertyDouble(pre+P_START_VELOCITY_Y));
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
										 poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
	
		//HZ -- initialise sensor engine - defines the maximum sensor range
		assignSensorData(oOffset, nRange, nFieldOfView);		
	}	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 10:51:32
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		oldVelocity = curVelocity;
		curVelocity = moEntity.getVelocity();
		oldAngularVelocity = curAngularVelocity;
		curAngularVelocity = moEntity.getAngularVelocity();
		
		curAcceleration = curVelocity.subtract(oldVelocity).scalarMult(stepsPerSecond);
		curAngularAcceleration = (curAngularVelocity - oldAngularVelocity) * stepsPerSecond;
	}
	
	public Double2D getCurAcceleration() { return curAcceleration; }
	public double getCurAngularAcceleration() { return curAngularAcceleration; }
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 10:51:32
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<clsCollidingObject> peObjInAreaList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 10:51:32
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<clsCollidingObject> peDetectedObjInAreaList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 10:51:32
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_ACCELERATION;		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.07.2009, 10:51:32
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Acceleration";
	}
}
