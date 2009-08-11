/**
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 */
public abstract class clsBaseBody implements 	itfStepSensing, itfStepUpdateInternalState, 
												itfStepProcessing, itfStepExecution {

	
	public clsBaseBody(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);	
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		// nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do ...
	}	
	
}
