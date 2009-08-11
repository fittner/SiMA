/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import config.clsBWProperties;
import sim.physics2D.util.Double2D;
import bw.utils.enums.eBodyParts;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.entities.clsMobile;

/**
 * use this sensor to get the current forces affecting the owner
 * CurrentVelocity - directing to vector er
 * AngularVelocity - directing to vector ephi (polar coordinates)
 * 
 * @author langr
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
	
	public clsSensorAcceleration(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsEntity poEntity) {
		super(poPrefix, poProp);
		if (! (poEntity instanceof clsMobile))
			throw new UnsupportedOperationException("Only mobile entitys may have acceleration Sensors!");
		moEntity = (clsMobile)poEntity;
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_START_VELOCITY_X, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_Y, 0.0);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		curVelocity = new Double2D(poProp.getPropertyDouble(pre+P_START_VELOCITY_X),
									poProp.getPropertyDouble(pre+P_START_VELOCITY_Y));
	}		
	

	public Double2D getCurAcceleration() { return curAcceleration; }
	public double getCurAngularAcceleration() { return curAngularAcceleration; }
	
	
	/* (non-Javadoc)
	 * Updates the sensor data values by fetching the info from the physics engine entity 
	 */
	public void updateSensorData() {
		oldVelocity = curVelocity;
		curVelocity = moEntity.getVelocity();
		oldAngularVelocity = curAngularVelocity;
		curAngularVelocity = moEntity.getAngularVelocity();
		
		curAcceleration = curVelocity.subtract(oldVelocity).scalarMult(stepsPerSecond);
		curAngularAcceleration = (curAngularVelocity - oldAngularVelocity) * stepsPerSecond;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_ACCELERATION;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Acceleration";
		
	}	
}
