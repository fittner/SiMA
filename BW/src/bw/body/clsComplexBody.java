/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import config.clsBWProperties;
import bw.body.attributes.clsAttributes;
import bw.body.brainsocket.clsBrainSocket;
import bw.body.interBodyWorldSystems.clsInterBodyWorldSystem;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.entities.clsEntity;


/**
 * The agent body is the basic container for each entity the body needs: 
 * int./ext. I/O's,  flesh, int. states, biosys., brain 
 * 
 * 
 * @author langr
 * 
 */
public class clsComplexBody extends clsBaseBody implements 
							itfGetInternalEnergyConsumption, itfGetBrain,
							itfGetInternalIO, itfGetExternalIO {
	public static final String P_INTERNAL 		= "internal";
	public static final String P_INTRABODY 		= "intrabody";
	public static final String P_BODYWORLD 		= "bodyworld";
	public static final String P_EXTERNALIO 	= "externalio";
	public static final String P_INTERNALIO 	= "internalio";
	public static final String P_BRAINSOCKET 	= "brainsocket";
	
	protected clsBrainSocket moBrain;
    protected clsExternalIO  moExternalIO;
    protected clsInternalIO  moInternalIO;
    
    private clsInternalSystem moInternalSystem;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
       
    public clsComplexBody(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp, poEntity);
		applyProperties(poPrefix, poProp, poEntity);
	}
    
	private void applyProperties(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		String pre = clsBWProperties.addDot(poPrefix);

		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(pre+P_EXTERNALIO, poProp, this, poEntity);
		moInternalIO 	= new clsInternalIO(pre+P_INTERNALIO, poProp, this);
		moBrain 		= new clsBrainSocket(pre+P_BRAINSOCKET, poProp, moExternalIO.moSensorExternal, moInternalIO.moSensorInternal, moExternalIO.getActionProcessor());
	}	    

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsExternalIO.getDefaultProperties(pre+P_EXTERNALIO) );
		oProp.putAll( clsInternalIO.getDefaultProperties(pre+P_INTERNALIO) );
		oProp.putAll( clsBrainSocket.getDefaultProperties(pre+P_BRAINSOCKET) );
		oProp.putAll( clsInternalSystem.getDefaultProperties(pre+P_INTERNAL) );
		oProp.putAll( clsIntraBodySystem.getDefaultProperties(pre+P_INTRABODY) );
		oProp.putAll( clsInterBodyWorldSystem.getDefaultProperties(pre+P_BODYWORLD) );
		oProp.putAll( clsAttributes.getDefaultProperties(pre+P_ATTRIBUTES) );
				
		return oProp;
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
	
	public void stepUpdateInternalState() {
		moInternalSystem.stepUpdateInternalState(); //call first!
		moIntraBodySystem.stepUpdateInternalState();
		moInterBodyWorldSystem.stepUpdateInternalState();
	}
	
	public void stepProcessing(){
		moBrain.stepProcessing();
	}	

	public void stepExecution() {
		moExternalIO.stepExecution();
		moInternalIO.stepExecution();
	}
	
}
