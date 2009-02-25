/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import sim.physics2D.util.Double2D;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyParts;

/**
 * use this sensor to get the current forces affecting the owner
 * CurrentVelocity - directing to vector er
 * AngularVelocity - directing to vector ephi (polar coordinates)
 * 
 * @author langr
 * 
 */
public class clsSensorAcceleration extends clsSensorExt{

	private clsEntity moEntity;
	
	/**
	 * constructor takes the entity stored as a local reference 
	 */
	public clsSensorAcceleration(clsEntity poEntity, clsBaseIO poBaseIO) {
		super(poBaseIO);
		setEntity(poEntity);
		// TODO Auto-generated constructor stub
	}

	private Double2D moCurrentVelocity;
	/**
	 * @return the moCurrentVelocity - directing to vector er
	 */
	public Double2D getCurrentVelocity() {
		return moCurrentVelocity;
	}

	private double moCurrentAngularVelocity; 
	
	/**
	 * @return the AccelerationVector - directing to vector ephi (polar coordinates)
	 */
	public double getCurrentAngularVelocity() {
		return moCurrentAngularVelocity;
	}
	
	/**
	 * @param moEntity the moEntity to set
	 */
	public void setEntity(clsEntity poEntity) {
		moEntity = poEntity;
	}

	/**
	 * @param mnAcceleration the mnAcceleration to set
	 */
	public void setAcceleration(int poAcceleration) {
	}
	
	/* (non-Javadoc)
	 * Updates the sensor data values by fetching the info from the physics engine entity 
	 */
	public void updateSensorData() {

		//TODO: RL --> entity braucht die Mobile object 2D
		// moCurrentVelocity = moEntity.getVelocity();
		// moCurrentAngularVelocity = moEntity.getAngularVelocity();
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
