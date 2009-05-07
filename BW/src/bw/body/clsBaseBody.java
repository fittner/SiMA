/**
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 */
public abstract class clsBaseBody implements itfStepSensing, itfStepUpdateInternalState, itfStepProcessing, itfStepExecution {
	protected clsConfigMap moConfig;
	
	public clsBaseBody(clsConfigMap poConfig){
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);
	}
	
	protected clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		return oDefault;
	}
	

}
