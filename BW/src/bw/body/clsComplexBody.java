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
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;
import bw.body.itfget.itfGetInternalEnergyConsumption;
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
public class clsComplexBody extends clsBaseBody implements itfGetInternalEnergyConsumption {
	
    private clsInternalSystem moInternalSystem;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
       
	/**
	 * CTOR
	 */
	public clsComplexBody(clsEntity poEntity, clsConfigMap poConfig)  {
		super(poEntity, getFinalConfig(poConfig));	
		applyConfig();		
		
		moInternalSystem = new clsInternalSystem((clsConfigMap) moConfig.get(eBodyParts.INTSYS));
		moIntraBodySystem = new clsIntraBodySystem(moInternalSystem, (clsConfigMap) moConfig.get(eBodyParts.INTRA));
		moInterBodyWorldSystem = new clsInterBodyWorldSystem(moInternalSystem, (clsConfigMap) moConfig.get(eBodyParts.INTER));
		
		moExternalIO = new clsExternalIO(this, poEntity, (clsConfigMap)moConfig.get(eBodyParts.EXTERNAL_IO));
		moInternalIO = new clsInternalIO(this, (clsConfigMap)moConfig.get(eBodyParts.INTERNAL_IO));
		   
	  	moBrain = new clsBrainSocket(moExternalIO.moSensorExternal, moInternalIO.moSensorInternal, (clsConfigMap) poConfig.get(eBodyParts.BRAIN));		
		
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

		oDefault.add(eBodyParts.INTSYS, null);
		oDefault.add(eBodyParts.INTRA, null);
		oDefault.add(eBodyParts.INTER, null);


		return oDefault;
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
		super.stepSensing();
	}
	
	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 25.02.2009, 16:02:00
	 *
	 */
	public clsBrainActionContainer stepProcessing(){
		return super.stepProcessing();
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:02:05
	 * 
	 * @see bw.body.itfStep#stepExecution()
	 */
	public void stepExecution(clsBrainActionContainer poActionList) {
		super.stepExecution(poActionList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 18:15:55
	 * 
	 * @see bw.body.itfInternalEnergyConsumption#getInternalEnergyConsumption()
	 */
	@Override
	public clsInternalEnergyConsumption getInternalEnergyConsumption() {
		return moInternalSystem.getInternalEnergyConsumption();
	}

}
