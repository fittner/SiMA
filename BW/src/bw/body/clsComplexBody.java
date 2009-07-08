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
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;

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
		
		moInternalSystem = new clsInternalSystem((clsConfigMap) moConfig.get(eConfigEntries.INTSYS));
		moIntraBodySystem = new clsIntraBodySystem(moInternalSystem, (clsConfigMap) moConfig.get(eConfigEntries.INTRA));
		moInterBodyWorldSystem = new clsInterBodyWorldSystem(moInternalSystem, (clsConfigMap) moConfig.get(eConfigEntries.INTER));
		
		moExternalIO = new clsExternalIO(this, poEntity, (clsConfigMap)moConfig.get(eConfigEntries.EXTERNAL_IO));
		
		moInternalIO = new clsInternalIO(this, (clsConfigMap)moConfig.get(eConfigEntries.INTERNAL_IO) );
		   
		moBrain = new clsBrainSocket(moExternalIO.moSensorExternal, moInternalIO.moSensorInternal, moExternalIO.getActionProcessor(), (clsConfigMap) moConfig.get(eConfigEntries.BRAIN));		
		
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

		oDefault.add(eConfigEntries.INTSYS, null);
		oDefault.add(eConfigEntries.INTRA, null);
		oDefault.add(eConfigEntries.INTER, null);

		oDefault.add(eConfigEntries.EXTERNAL_IO, null);
		oDefault.add(eConfigEntries.INTERNAL_IO, null);
		oDefault.add(eConfigEntries.BRAIN, null);

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
	public void stepProcessing(){
		 super.stepProcessing();
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:02:05
	 * 
	 * @see bw.body.itfStep#stepExecution()
	 */
	public void stepExecution() {
		super.stepExecution();
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
