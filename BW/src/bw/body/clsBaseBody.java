/**
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.brainsocket.clsBrainSocket;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 */
public abstract class clsBaseBody implements itfStepSensing, itfStepUpdateInternalState, itfStepProcessing, itfStepExecution {
	protected clsBrainSocket moBrain;
    protected clsExternalIO moExternalIO;
    protected clsInternalIO moInternalIO;
	
	protected clsConfigMap moConfig;
	
	public clsBaseBody(clsEntity poEntity, clsConfigMap poConfig){
		moConfig = getFinalConfig(poConfig);
		applyConfig();
		
		moBrain = null;
		moExternalIO = null;
		moInternalIO = null;
		
	}
	
	private void applyConfig() {
		//TODO add code ...
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();


		return oDefault;
	}	
	
	/**
	 * @return the moExternalIO
	 */
	public clsExternalIO getExternalIO() {
		return moExternalIO;
	}



	/**
	 * @return the moInternalIO
	 */
	public clsInternalIO getInternalIO() {
		return moInternalIO;
	}
	/**
	 * @return the moBrain
	 */
	public clsBrainSocket getBrain() {
		return moBrain;
	}

	public void stepSensing() {
		moExternalIO.stepSensing();
		moInternalIO.stepSensing();
	}
	
	public clsBrainActionContainer stepProcessing(){
		return moBrain.stepProcessing();
	}	

	public void stepExecution(clsBrainActionContainer poActionList) {
		moExternalIO.stepExecution(poActionList);
		moInternalIO.stepExecution(poActionList);
	}	
}
