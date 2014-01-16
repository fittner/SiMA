/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.intraBodySystems;

import properties.clsProperties;
import body.itfStepUpdateInternalState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsGrowth implements itfStepUpdateInternalState {

	public clsGrowth(String poPrefix, clsProperties poProp) {
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		// String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		// nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

		//nothing to do ...
	}	
	
    /**
     * DOCUMENT (deutsch) - insert description
     *
     */
    @Override
	public void stepUpdateInternalState() {
    	
    }
}
