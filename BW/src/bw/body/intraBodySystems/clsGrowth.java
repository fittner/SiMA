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

	private clsConfigMap moConfig;
	
	public clsGrowth(clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}
	
	private void applyConfig() {

		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}	
	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void stepUpdateInternalState() {
    	
    }
}
