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
import bw.utils.config.clsBWProperties;

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
	
	public clsBaseBody(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
		
		moBrain = null;
		moExternalIO = null;
		moInternalIO = null;		
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
	
	public void stepProcessing(){
		moBrain.stepProcessing();
	}	

	public void stepExecution() {
		moExternalIO.stepExecution();
		moInternalIO.stepExecution();
	}	
}
