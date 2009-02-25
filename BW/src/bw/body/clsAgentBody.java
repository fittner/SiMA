/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;


import java.util.ArrayList;

import bw.body.brain.clsBrain;
import bw.body.interBodyWorldSystems.clsInterBodyWorldSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;
import bw.body.motionplatform.clsBrainAction;
import bw.entities.clsEntity;

/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsAgentBody implements itfStepSensing, itfStepUpdateInternalState, itfStepProcessing, itfStepExecution {
	private clsBrain moBrain;
    private clsInternalSystem moInternalSystem;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
    private clsExternalIO moExternalIO;
    private clsInternalIO moInternalIO;
    
	/**
	 * CTOR
	 */
	public clsAgentBody(clsEntity poEntity) {
  	   moBrain = new clsBrain();
  	   
	   moInternalSystem = new clsInternalSystem();
	   moIntraBodySystem = new clsIntraBodySystem(moInternalSystem);
	   moInterBodyWorldSystem = new clsInterBodyWorldSystem(moInternalSystem);
	   
	   moExternalIO = new clsExternalIO(poEntity, this);
	   moInternalIO = new clsInternalIO(this);
	}
	
	
	
	/**
	 * @return the moBrain
	 */
	public clsBrain getBrain() {
		return moBrain;
	}



	/**
	 * @return the moInternalStates
	 */
	public clsInternalSystem getInternalSystem() {
		return moInternalSystem;
	}



	/**
	 * @return the moIntraBodySystem
	 */
	public clsIntraBodySystem getIntraBodySystem() {
		return moIntraBodySystem;
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
	 * @return the moInterBodyWorldSystem
	 */
	public clsInterBodyWorldSystem getInterBodyWorldSystem() {
		return moInterBodyWorldSystem;
	}


	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 25.02.2009, 16:01:54
	 *
	 */
	public void stepUpdateInternalState() {
		moInternalSystem.stepUpdateInternalState(); //call first!
		moIntraBodySystem.stepUpdateInternalState();
		moInterBodyWorldSystem.stepUpdateInternalState();
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:01:56
	 * 
	 * @see bw.body.itfStep#stepSensing()
	 */
	public void stepSensing() {
		moExternalIO.stepSensing();
		moInternalIO.stepSensing();
	}
	
	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 25.02.2009, 16:02:00
	 *
	 */
	public void stepProcessing(){
		moBrain.stepProcessing();
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:02:05
	 * 
	 * @see bw.body.itfStep#stepExecution()
	 */
	public void stepExecution(ArrayList<clsBrainAction> poActionList) {
		moExternalIO.stepExecution(poActionList);
		moInternalIO.stepExecution(poActionList);
	}

}
