/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import bw.body.brainsocket.clsBrainSocket;
import bw.body.interBodyWorldSystems.clsInterBodyWorldSystem;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;

/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsAgentBody extends clsBaseBody {
	private clsBrainSocket moBrain;
    private clsInternalSystem moInternalSystem;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
    private clsExternalIO moExternalIO;
    private clsInternalIO moInternalIO;
    
	/**
	 * CTOR
	 */
	public clsAgentBody(clsEntity poEntity, clsConfigMap poConfig)  {
		super(poConfig);
		
	   moInternalSystem = new clsInternalSystem((clsConfigMap) poConfig.get(eBodyParts.INTSYS));
	   moIntraBodySystem = new clsIntraBodySystem(moInternalSystem, (clsConfigMap) poConfig.get(eBodyParts.INTRA));
	   moInterBodyWorldSystem = new clsInterBodyWorldSystem(moInternalSystem, (clsConfigMap) poConfig.get(eBodyParts.INTER));
	   
	   moExternalIO = new clsExternalIO(poEntity, this, (clsConfigMap) poConfig.get(eBodyParts.EXTERNAL_IO));
	   moInternalIO = new clsInternalIO(this, (clsConfigMap) poConfig.get(eBodyParts.INTERNAL_IO));
	   
  	   moBrain = new clsBrainSocket(moExternalIO.moSensorExternal, moInternalIO.moSensorInternal, (clsConfigMap) poConfig.get(eBodyParts.BRAIN));
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.05.2009, 17:12:09
	 * 
	 * @see bw.body.clsBaseBody#getDefaultConfig()
	 */
	@Override
	protected clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = super.getDefaultConfig();
		
		oDefault.add(eBodyParts.INTSYS, null);
		oDefault.add(eBodyParts.INTRA, null);
		oDefault.add(eBodyParts.INTER, null);
		oDefault.add(eBodyParts.EXTERNAL_IO, null);
		oDefault.add(eBodyParts.INTERNAL_IO, null);
		oDefault.add(eBodyParts.BRAIN, null);
		
		return oDefault;
	}	
	
	/**
	 * @return the moBrain
	 */
	public clsBrainSocket getBrain() {
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
	public clsBrainActionContainer stepProcessing(){
		return moBrain.stepProcessing();
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:02:05
	 * 
	 * @see bw.body.itfStep#stepExecution()
	 */
	public void stepExecution(clsBrainActionContainer poActionList) {
		moExternalIO.stepExecution(poActionList);
		moInternalIO.stepExecution(poActionList);
	}





}
