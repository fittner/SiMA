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
import bw.body.internalSystems.clsFlesh;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;


/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsComplexBody extends clsBaseBody implements itfGetInternalEnergyConsumption {
	public static final String P_INTERNAL = "internal";
	public static final String P_INTRABODY = "intrabody";
	public static final String P_BODYWORLD = "bodyworld";
	
    private clsInternalSystem moInternalSystem;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
       
    public clsComplexBody(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
		
		moExternalIO = new clsExternalIO(this, poEntity, (clsConfigMap)moConfig.get(eConfigEntries.EXTERNAL_IO));
		moInternalIO = new clsInternalIO(this, (clsConfigMap)moConfig.get(eConfigEntries.INTERNAL_IO) );
		moBrain = new clsBrainSocket(moExternalIO.moSensorExternal, moInternalIO.moSensorInternal, moExternalIO.getActionProcessor(), (clsConfigMap) moConfig.get(eConfigEntries.BRAIN));		
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsInternalSystem.getDefaultProperties(pre+P_INTERNAL) );
		oProp.putAll( clsIntraBodySystem.getDefaultProperties(pre+P_INTRABODY) );
		oProp.putAll( clsInterBodyWorldSystem.getDefaultProperties(pre+P_BODYWORLD) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		moInternalSystem = new clsInternalSystem(pre+P_INTERNAL, poProp);
		moIntraBodySystem = new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem);
		moInterBodyWorldSystem = new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem);
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
	@Override
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
	@Override
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
	@Override
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
	public clsInternalEnergyConsumption getInternalEnergyConsumption() {
		return moInternalSystem.getInternalEnergyConsumption();
	}

}
