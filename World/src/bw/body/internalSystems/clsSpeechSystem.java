/**
 * clsSpeechSystem.java: BW - bw.body.internalSystem
 * 
 * @author MW
 * 25.02.2013, 12:57:01
 */
package bw.body.internalSystems;

import config.clsProperties;
import bw.body.itfStepUpdateInternalState;

/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 25.02.2013, 13:30:54
 * 
 */
public class clsSpeechSystem implements itfStepUpdateInternalState {
    
	
	public clsSpeechSystem(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
		
	}

	@Override
	public void stepUpdateInternalState() {
		// TODO MW 
		
	}		
	
	
}
