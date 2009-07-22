/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.itfStepUpdateInternalState;
import bw.utils.config.clsBWProperties;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsBodyColor implements itfStepUpdateInternalState {

	public clsBodyColor(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		// nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do ...
	}	


	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void stepUpdateInternalState() {
    	
    }
}
