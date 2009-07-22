/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import sim.physics2D.util.Double2D;
import bw.utils.config.clsBWProperties;
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

	//private clsEntity moEntity;	// EH - make warning free
	
	public clsSensorAcceleration(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		//setEntity(poEntity);		// EH - make warning free
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
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
	/*	// EH - make warning free
	public void setEntity(clsEntity poEntity) {
		moEntity = poEntity;
	}
	*/

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
