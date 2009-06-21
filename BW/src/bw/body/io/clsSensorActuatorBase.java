/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import statictools.clsSingletonUniqueIdGenerator;
import bw.utils.enums.eBodyParts;

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
	private static final int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();
	protected eBodyParts mePartId;
	protected String moName;
	
	public clsSensorActuatorBase() {
		setBodyPartId();
		setName();
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

}
