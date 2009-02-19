/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import sim.physics2D.util.Double2D;
import bw.clsEntity;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;

/**
 * TODO (langr) - insert description 
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
	 * @return the moCurrentVelocity
	 */
	public Double2D getCurrentVelocity() {
		return moCurrentVelocity;
	}

	private double moCurrentAngularVelocity; 
	
	/**
	 * @return the AccelerationVector
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
