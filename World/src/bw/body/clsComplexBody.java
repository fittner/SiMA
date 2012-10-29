/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;

import java.util.HashMap;
import config.clsProperties;
import du.enums.eBodyActionType;
import du.enums.eFacialExpression;
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
import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsFood;


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
    
    private HashMap<eBodyActionType, Integer> moBodyActionList;
    private eFacialExpression moFacialExpression;
       




	public clsComplexBody(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp, poEntity);
		applyProperties(poPrefix, poProp, poEntity);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		String pre = clsProperties.addDot(poPrefix);

		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(pre+P_EXTERNALIO, poProp, this, poEntity);
		moInternalIO 	= new clsInternalIO(pre+P_INTERNALIO, poProp, this);
		moBrain 		= new clsBrainSocket(pre+P_BRAINSOCKET, poProp, moExternalIO.moSensorEngine.getMeRegisteredSensors(), moInternalIO.moSensorInternal, moExternalIO.getActionProcessor());
		
		moBodyActionList = new HashMap<eBodyActionType, Integer>();
		moFacialExpression = eFacialExpression.NEUTRAL;
	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
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
	@Override
	public clsInternalEnergyConsumption getInternalEnergyConsumption() {
		return moInternalSystem.getInternalEnergyConsumption();
	}

	/**
	 * @return the moExternalIO
	 */
	@Override
	public clsExternalIO getExternalIO() {
		return moExternalIO;
	}

	/**
	 * @return the moInternalIO
	 */
	@Override
	public clsInternalIO getInternalIO() {
		return moInternalIO;
	}
	/**
	 * @return the moBrain
	 */
	@Override
	public clsBrainSocket getBrain() {
		return moBrain;
	}	
	
	@Override
	public void stepSensing() {
		moExternalIO.stepSensing();
		moInternalIO.stepSensing();
	}
	
	@Override
	public void stepUpdateInternalState() {
		moInternalSystem.stepUpdateInternalState(); //call first!
		moIntraBodySystem.stepUpdateInternalState();
		moInterBodyWorldSystem.stepUpdateInternalState();
	}
	
	@Override
	public void stepProcessing(){
		moBrain.stepProcessing();
	}	

	@Override
	public void stepExecution() {
		moExternalIO.stepExecution();
		moInternalIO.stepExecution();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:35:37
	 * 
	 * @see bw.body.clsBaseBody#setBodyType()
	 */
	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.COMPLEX;		
	}
	
	public boolean isAlive() {
		return moInternalSystem.getHealthSystem().getIsAlive();
	}
	
	/**
	 * Add a bodily reaction with this method, and this method only!
	 * When the reaction is already there, the duration will be 
	 * set to the new duration
	 *
	 * @since 29.10.2012 19:43:24
	 *
	 * @param durationInSteps
	 */
	public void AddBodyAction(eBodyActionType poBodyAction, int durationInSteps){
		
		if(moBodyActionList.containsKey(poBodyAction)){
			//update the duration
			moBodyActionList.put(poBodyAction, durationInSteps);
			//jaja schaut gleich aus, ist es auch, teilung für debug messages gedacht und vielleicht später für fast messenger
		}else{
			//add the new action
			moBodyActionList.put(poBodyAction, durationInSteps);
		}
	}
	
    /**
     * Get the list og bodily reactions to emotions, work with it, now!
     *
     * @since 29.10.2012 19:53:52
     *
     * @return
     */
    public HashMap<eBodyActionType, Integer> getMoBodyActionList() {
		return moBodyActionList;
	}
    
	public eFacialExpression getFacialExpression() {
		return moFacialExpression;
	}

	public void setFacialExpression(eFacialExpression moFacialExpression) {
		this.moFacialExpression = moFacialExpression;
	}
	
	
	
	
	// ATTENTION THE FOLLOWING FUNCTIONS ARE FOR DEBUG USE ONLY!!!
	//************************************************************
	/**
	 * ATTENTION: THE FUNCTION IS FOR DEBUG USE ONLY!!!
	 *Empties the Stomach
	 */
	public void DEBUG_DestroyAllNutritionAndEnergyForModelTesting() {
		this.getInternalSystem().getStomachSystem().DestroyAllEnergyForModelTesting();
	}
	
	/**
	 * ATTENTION: THE FUNCTION IS FOR DEBUG USE ONLY!!!
	 *get the internal health value
	 */
	public double DEBUG_getInternalHealthValue() {
		return this.getInternalSystem().getHealthSystem().getHealth().getContent();
	}
	
	/**
	 * ATTENTION: THE FUNCTION IS FOR DEBUG USE ONLY!!!
	 *removes health
	 */
	public void DEBUG_HurtBody(double rDamage) {
		this.getInternalSystem().getHealthSystem().hurt(rDamage);
	}
	
	/**
	 * ATTENTION: THE FUNCTION IS FOR DEBUG USE ONLY!!!
	 *Add health
	 */
	public void DEBUG_HealBody(double rAmount) {
		this.getInternalSystem().getHealthSystem().heal(rAmount);
	}
	
	/**
	 * ATTENTION: THE FUNCTION IS FOR DEBUG USE ONLY!!!
	 *Makes ARSin eat something
	 */
	public void DEBUG_EatFoodPackage(){

		clsFood oFood = new clsFood();
		try {
			oFood.setWeight(6f);
		} catch (exFoodWeightBelowZero e) {
			e.printStackTrace();
		}
		try {
		oFood.addNutritionFraction(eNutritions.CARBOHYDRATE, new clsMutableDouble(1.0) );
		oFood.addNutritionFraction(eNutritions.FAT, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.MINERAL, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.PROTEIN, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.VITAMIN, new clsMutableDouble(1.0f));
		oFood.addNutritionFraction(eNutritions.WATER, new clsMutableDouble(1.0f));
		} catch (exFoodAlreadyNormalized e) {
			e.printStackTrace();
		}

		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
			e.printStackTrace();
		}
		
        this.getInterBodyWorldSystem().getConsumeFood().digest(oFood);   
	}
	
	public void DEBUG_EatUndigestablePackage(){

		clsFood oFood = new clsFood();
		try {
			oFood.setWeight(6f);
		} catch (exFoodWeightBelowZero e) {
			e.printStackTrace();
		}
		try {
		oFood.addNutritionFraction(eNutritions.UNDIGESTABLE, new clsMutableDouble(1.0f));
		} catch (exFoodAlreadyNormalized e) {
			e.printStackTrace();
		}

		try {
			oFood.finalize();
		} catch (exFoodAlreadyNormalized e) {
			e.printStackTrace();
		}
		
        this.getInterBodyWorldSystem().getConsumeFood().digest(oFood);   
	}
	
}
