/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import config.clsProperties;
import du.enums.eBodyActionType;
import du.enums.eFacialExpression;
import du.itf.actions.clsInternalActionSweat;

import bw.body.attributes.clsAttributes;
import bw.body.brainsocket.clsBrainSocket;
import bw.body.interBodyWorldSystems.clsInterBodyWorldSystem;
import bw.body.internalSystems.clsInternalEnergyConsumption;
import bw.body.internalSystems.clsInternalSystem;
import bw.body.intraBodySystems.clsIntraBodySystem;
import bw.body.io.clsExternalIO;
import bw.body.io.clsInternalIO;

import bw.body.io.actuators.clsInternalActionProcessor;
import bw.body.io.actuators.actionExecutors.clsExecutorInternalSweat;

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
	
	public static final String P_INTERNALACTIONEX = "internalactionexecutor";
	
	protected clsBrainSocket moBrain;
    protected clsExternalIO  moExternalIO;
    protected clsInternalIO  moInternalIO;
    
    private clsInternalSystem moInternalSystem;
    private clsIntraBodySystem moIntraBodySystem;
    private clsInterBodyWorldSystem moInterBodyWorldSystem;
    
    private HashMap<eBodyActionType, Integer> moBodyActionList;
    private eFacialExpression moFacialExpression = eFacialExpression.NONE;;
    
    private clsInternalActionProcessor moInternalActionProcessor; 
    private clsEntity moEntity;
       




	public clsComplexBody(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp, poEntity);
		moEntity=poEntity;
		moInternalActionProcessor = new clsInternalActionProcessor(poPrefix,poProp,poEntity);
		
		applyProperties(poPrefix, poProp, poEntity);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		String pre = clsProperties.addDot(poPrefix);

		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(pre+P_EXTERNALIO, poProp, this, poEntity);
		moInternalIO 	= new clsInternalIO(pre+P_INTERNALIO, poProp, this);
		moBrain 		= new clsBrainSocket(pre+P_BRAINSOCKET, poProp, moExternalIO.moSensorEngine.getMeRegisteredSensors(), moInternalIO.moSensorInternal, moExternalIO.getActionProcessor(), this.getInternalActionProcessor());
		
		moBodyActionList = new HashMap<eBodyActionType, Integer>();
		moFacialExpression = eFacialExpression.NONE;
		
		applyInternalActionProperties(poPrefix, poProp);
	}
	
	private void applyInternalActionProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		//TODO AddInternalActions
		
		moInternalActionProcessor.addCommand(clsInternalActionSweat.class, 
			new clsExecutorInternalSweat(poPrefix+"." + P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,poProp, moEntity));

//		//Register actionexecutors
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_DROP)==1) moProcessor.addCommand(clsActionMove.class, new clsExecutorMove(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_MOVE,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TURN)==1) moProcessor.addCommand(clsActionTurn.class, new clsExecutorTurn(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TURN,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EAT)==1) moProcessor.addCommand(clsActionEat.class, new clsExecutorEat(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EAT,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INNERSPEECH)==1) moProcessor.addCommand(clsActionInnerSpeech.class, new clsExecutorMoveToArea(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INNERSPEECH,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKBITE)==1) moProcessor.addCommand(clsActionAttackBite.class, new clsExecutorAttackBite(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKBITE,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_PICKUP)==1) moProcessor.addCommand(clsActionPickUp.class, new clsExecutorPickUp(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_PICKUP,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_DROP)==1) moProcessor.addCommand(clsActionDrop.class, new clsExecutorDrop(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_DROP,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FROMINVENTORY)==1) moProcessor.addCommand(clsActionFromInventory.class, new clsExecutorFromInventory(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FROMINVENTORY,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TOINVENTORY)==1) moProcessor.addCommand(clsActionToInventory.class, new clsExecutorToInventory(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TOINVENTORY,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_CULTIVATE)==1) moProcessor.addCommand(clsActionCultivate.class, new clsExecutorCultivate(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_CULTIVATE,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_KISS)==1) moProcessor.addCommand(clsActionKiss.class, new clsExecutorKiss(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_KISS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING)==1) moProcessor.addCommand(clsActionAttackLightning.class, new clsExecutorAttackLightning(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_MOVETOAREA)==1) moProcessor.addCommand(clsActionMoveToEatableArea.class, new clsExecutorMoveToArea(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_MOVETOAREA,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT)==1) moProcessor.addCommand(clsActionExcrement.class, new clsExecutorExcrement(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT,poProp,(clsMobile) moEntity));
//
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR)==1) moProcessor.addCommand(clsActionBodyColor.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORRED)==1) moProcessor.addCommand(clsActionBodyColorRed.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORGREEN)==1) moProcessor.addCommand(clsActionBodyColorGreen.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORBLUE)==1) moProcessor.addCommand(clsActionBodyColorBlue.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSHAPE)==1) moProcessor.addCommand(clsActionFacialExLensShape.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSIZE)==1) moProcessor.addCommand(clsActionFacialExLensSize.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_EYESIZE)==1) moProcessor.addCommand(clsActionFacialExEyeSize.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LEFTANT)==1) moProcessor.addCommand(clsActionFacialExLeftAntennaPosition.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_RIGHTANT)==1) moProcessor.addCommand(clsActionFacialExRightAntennaPosition.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//
//		//TODO: Add itfAPSleep - objects to inform when sleeping!		
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_SLEEP)==1) {
//			ArrayList<itfAPSleep> oNotifyListLight = new ArrayList<itfAPSleep>();
//			ArrayList<itfAPSleep> oNotifyListDeep = new ArrayList<itfAPSleep>();
//			//oNotifyListLight.add(xxxxx);
//			//oNotifyListDeep.add(xxxxx);			
//			moProcessor.addCommand(clsActionSleep.class, new clsExecutorSleep(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT,poProp,(clsMobile) moEntity,oNotifyListLight,oNotifyListDeep));
//		}		
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

		oProp.putAll( clsExecutorInternalSweat.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL) );
				
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
	
	public clsInternalActionProcessor getInternalActionProcessor() {
		return moInternalActionProcessor;
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
		stepUpdateInternalBodyActions();
	}
	
	/**
	 * iterates through the bodily actions and reduces it by one step
	 *
	 * @since 29.10.2012 20:02:43
	 *
	 */
	private void stepUpdateInternalBodyActions() {
		
	
		if(moBodyActionList!= null && !moBodyActionList.isEmpty())
		{
			Set<Entry<eBodyActionType, Integer>> set = moBodyActionList.entrySet();
			Iterator<Entry<eBodyActionType, Integer>> i = set.iterator();
			 
			while(i.hasNext()){
			      
			      Map.Entry<eBodyActionType, Integer>  entry = (Map.Entry<eBodyActionType, Integer> )i.next();
			      eBodyActionType oBodyAction = (eBodyActionType) entry.getKey();
			      int iDuration =  (Integer) entry.getValue();
			      
			      iDuration = iDuration-1;
			      if(iDuration <= 0)
			      {
			    	  //remove it
			    	  moBodyActionList.remove(oBodyAction);
			      }else{
			    	  //update it
			    	  moBodyActionList.put(oBodyAction, iDuration);
			      }
			    }
		}
	}

	@Override
	public void stepProcessing(){
		moBrain.stepProcessing();
	}	

	@Override
	public void stepExecution() {
		moInternalActionProcessor.dispatch();
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
