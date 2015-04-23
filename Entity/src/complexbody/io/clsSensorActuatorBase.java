/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io;

import properties.clsProperties;
import utils.clsUniqueIdGenerator;
import entities.enums.eBodyParts;

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
	
	private static final int mnUniqueId = clsUniqueIdGenerator.getUniqueId();
	protected eBodyParts mePartId;
	protected String moName;
	private clsBaseIO moBaseIO; // reference
	
	protected double mrBaseEnergyConsumption;
	
	public clsSensorActuatorBase(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO) {
		setBodyPartId();
		setName();
		moBaseIO = poBaseIO;
		
		applyProperties(poPrefix, poProp);
	}		

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0);

		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mrBaseEnergyConsumption = poProp.getPropertyDouble(pre+P_BASEENERGYCONSUMPTION);
		
		registerEnergyConsumption(mrBaseEnergyConsumption);
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
	
	/*
	 * If these two methods of an object which does not have body (e.g. Base) are called, moBaseIO is null and therefore an exception is thrown
	 * 
	 * @author horvath
	 * 
	 */
	protected void registerEnergyConsumption(double prValue) {
		if(moBaseIO != null){
			moBaseIO.registerEnergyConsumption(getBodyPartId(), prValue);
		}
	}
	protected void registerEnergyConsumptionOnce(double prValue) {
		if(moBaseIO != null){
			moBaseIO.registerEnergyConsumptionOnce(getBodyPartId(), prValue);
		}
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
