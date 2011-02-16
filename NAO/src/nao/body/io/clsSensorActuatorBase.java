/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.io;


import statictools.clsSingletonUniqueIdGenerator;
import nao.utils.enums.eBodyParts;

/**
 * Base class for all sensors and actuators, contains methods and definitions for the extending classes
 * Sensors an actuators must set the uniqueID (type eBodyParts) and any self chosen name for the part.
 * 
 * @author muchitsch
 *  
 * Changes:
 *   BD (21.6.) Moved moBaseIO and registerEnergyconsumption to clsSensorActuatorBaseInt/-Ext
 */
public abstract class clsSensorActuatorBase {
	public static final String P_BASEENERGYCONSUMPTION = "baseenergyconsumption";
	
	private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();
	protected eBodyParts mePartId;
	protected String moName;
	private clsBaseIO moBaseIO; // reference
	
	protected double mrBaseEnergyConsumption;
	
	public clsSensorActuatorBase(clsBaseIO poBaseIO) {
		setBodyPartId();
		setName();
		moBaseIO = poBaseIO;
		
		
	}		

		
	protected abstract void setBodyPartId();
	protected abstract void setName();
	
	public String getName() {
		return moName;
	}

	/**
	 * @return the mnUniqueId
	 */
	public int getUniqueId() {
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
