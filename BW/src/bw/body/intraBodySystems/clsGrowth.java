/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.itfStepUpdateInternalState;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsGrowth implements itfStepUpdateInternalState {

	// private clsConfigMap moConfig;	// EH - make warning free
	
	public clsGrowth(clsConfigMap poConfig) {
		// moConfig = getFinalConfig(poConfig); // EH - make warning free
		applyConfig();
	}
	
	private void applyConfig() {

		//TODO add ...

	}
	
	/* // EH - make warning free
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	*/
	
	/* // EH - make warning free
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}	
	*/
	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void stepUpdateInternalState() {
    	
    }
}
