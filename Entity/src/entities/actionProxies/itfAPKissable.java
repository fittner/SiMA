/**
 * itfAPKissable.java: BW - bw.body.io.actuators.actionProxies
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:56:41
 */
package entities.actionProxies;

import du.enums.eActionKissIntensity;

/**
 * Proxy-Interface for action Kiss
 * Method tryKiss: will be called before kissing 
 * Method kiss: kiss 
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:38:56
 * 
 */
public interface itfAPKissable {

	/*
	 * tries to kiss the entity using a given intensity
	 * returns 0 if OK, otherwise != 0 (no effect other than kiss will not be performed)
	 */
	boolean tryKiss(eActionKissIntensity peIntensity);
	
	/*
	 * Inform the entity it has been kissed 
	 * Will only be executed if tryKiss returns 0 as result
	 */
	void kiss(eActionKissIntensity peIntensity);
		
}