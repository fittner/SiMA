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

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsSensorAcceleration extends clsSensorExt{

	
	/**
	 * constructor takes the entity stored as a local reference 
	 */
	public clsSensorAcceleration(clsEntity poEntity) {
		super();
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
	
	private clsEntity moEntity;

	/**
	 * @param moEntity the moEntity to set
	 */
	public void setEntity(clsEntity poEntity) {
		this.moEntity = poEntity;
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

		moCurrentVelocity = moEntity.getVelocity();
		moCurrentAngularVelocity = moEntity.getAngularVelocity();
	}
	
}
