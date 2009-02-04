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

	
	private Double2D moCurrentVelocity;
	/**
	 * @return the moCurrentVelocity
	 */
	public Double2D getCurrentVelocity() {
		return moCurrentVelocity;
	}

	/**
	 * @return the moCurrentAngularVelocity
	 */
	public double getMoCurrentAngularVelocity() {
		return moCurrentAngularVelocity;
	}

	private double moCurrentAngularVelocity; 
	
	/**
	 * @return the AccelerationVector
	 */
	public double getCurrentAngularVelocity() {
		return 1;
	}

	/**
	 * @param mnAcceleration the mnAcceleration to set
	 */
	public void setAcceleration(int poAcceleration) {
	}
	
	/* (non-Javadoc)
	 * Updates the sensor data values by fetching the info from the physics engine entity 
	 */
	public void updateSensorData(clsEntity poEntity) {

		moCurrentVelocity = poEntity.getVelocity();
		moCurrentAngularVelocity = poEntity.getAngularVelocity();
	}
	
}
