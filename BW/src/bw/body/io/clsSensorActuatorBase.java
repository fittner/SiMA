/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import config.clsBWProperties;
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
	private clsBaseIO moBaseIO; // reference
	
	public clsSensorActuatorBase(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO) {
		setBodyPartId();
		setName();
		moBaseIO = poBaseIO;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
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
		if(!moBaseIO.equals(null)){
			moBaseIO.registerEnergyConsumption(getBodyPartId(), prValue);
		}else{
			throw new NullPointerException();			
		}
	}
	protected void registerEnergyConsumptionOnce(double prValue) {
		if(!moBaseIO.equals(null)){
			moBaseIO.registerEnergyConsumptionOnce(getBodyPartId(), prValue);
		}else{
			throw new NullPointerException();			
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
