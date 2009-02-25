/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;



import bw.factories.clsSingletonUniqueIdGenerator;
import bw.utils.enums.eBodyParts;



/**
 * Base class for all sensors and actuators, contains methods and definitions for the extending classes
 * Sensors an actuators must set the uniqueID (type eBodyParts) and any self chosen name for the part.
 * 
 * @author muchitsch
 * 
 */
public abstract class clsSensorActuatorBase {
	private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();
	protected eBodyParts mePartId;
	protected String moName;
	
	private clsBaseIO moBaseIO;
	
	public clsSensorActuatorBase(clsBaseIO poBaseIO) {
		moBaseIO = poBaseIO;
		setBodyPartId();
		setName();
	}

	protected abstract void setBodyPartId();
	protected abstract void setName();
	
	protected void registerEnergyConsumption(float prValue) {
		moBaseIO.registerEnergyConsumption(mnUniqueId, prValue);
	}

	public String getName() {
		return moName;
	}


	/**
	 * @return the mnUniqueId
	 */
	public long getUniqueId() {
		return mnUniqueId;
	}

	/**
	 * @return the mePartId
	 */
	public eBodyParts getBodyPartId() {
		return mePartId;
	}	
	
	/**
	 * needed for access from actuator-classes to the PhysicalObject2D via the clsEntity
	 * 
	 * @author langr
	 * 25.02.2009, 17:45:01
	 * 
	 * @return the moBaseIO
	 */
	public clsBaseIO getBaseIO() {
		return moBaseIO;
	}
}
