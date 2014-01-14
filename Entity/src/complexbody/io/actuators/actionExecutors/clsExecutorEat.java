/**
 * @author Benny D�nz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import complexbody.internalSystems.clsFastMessengerSystem;
import complexbody.io.actuators.clsActionExecutor;

import singeltons.eImages;

import body.clsComplexBody;
import body.itfget.itfGetBody;
import body.utils.clsFood;
import du.enums.eSensorExtType;
import du.itf.actions.*;
import entities.abstractEntities.clsEntity;
import entities.actionProxies.*;
import entities.enums.eBodyParts;

/**
 * Action Executor for eating
 * Proxy itfAPEatable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prBiteSize = Size of bite taken when eating (default = weight 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorEat extends clsActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 			
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private double mrBiteSizeMax;
	private double mrLibidinousEatFactor;
	private double mrAggressivEatFactor;
	private eSensorExtType moRangeSensor;
	private clsEntity moEntity;

	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_BITESIZE_MAX = "bitesizemax";
	public static final String P_LIBIDINOUS_EAT_FACTOR = "libidinouseatfactor";
	public static final String P_AGGRESSIV_EAT_FACTOR = "aggressiveatfactor";
	

	public clsExecutorEat(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);

		applyProperties(poPrefix,poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		oProp.setProperty(pre+P_BITESIZE_MAX, 5f); //0.3f
		//how strong the libidinous oral mucosa is stimulated by the action eat
		oProp.setProperty(pre+P_LIBIDINOUS_EAT_FACTOR, 0.2);
		//how strong the aggressiv oral mucosa is stimulated by the action eat
		oProp.setProperty(pre+P_AGGRESSIV_EAT_FACTOR, 0.2);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
		mrBiteSizeMax=poProp.getPropertyFloat(pre+P_BITESIZE_MAX);
		mrLibidinousEatFactor=poProp.getPropertyFloat(pre+P_LIBIDINOUS_EAT_FACTOR);
		mrAggressivEatFactor=poProp.getPropertyFloat(pre+P_AGGRESSIV_EAT_FACTOR);
	
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_EAT;
	}
	@Override
	protected void setName() {
		moName="Eat executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		return srStaminaDemand;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsActionEat oEatCommand = (clsActionEat) poCommand;
		//Executing Eat has 3 parts: 
		//1) the bodily part of getting a piece of food from the eaten entity and presenting this to the stomach
		//2) the stimulation of the erogenous zone (the mouth, the orifice where food is passed and which leads to the stomach)
		//3) activation of the memorytrace of the action eat which was planned and now executed
		
		
		//1) take food from Object and put it in stomach
		//Is something in range
		clsEntity oEatenEntity = (clsEntity) findEntityInRange(moEntity, oBody, moRangeSensor ,itfAPEatable.class) ;
		
		if (oEatenEntity==null) {
			moEntity.setOverlayImage(eImages.Overlay_Action_EatNothing);
			//Nothing in range then send fast Messenger, = "bites in hins tounge"
			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
			oFastMessengerSystem.addMessage(mePartId, eBodyParts.BRAIN, 1);
			return false;
		} 
		else {
			//setting a overlay image, normal eating
			moEntity.setOverlayImage(eImages.Overlay_Action_Eat);
		}
			

		//Check if eating is ok
		double rDamage = ((itfAPEatable)oEatenEntity).tryEat();
		if (rDamage>0) {
			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
			return false;
		}
		
		//Eat!
        clsFood oReturnedFood =((itfAPEatable)oEatenEntity).Eat(mrBiteSizeMax* oEatCommand.getMrBiteSize());
        if(oReturnedFood != null) {                
        	oBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood);
        }
        
        //FIXME (horvath) - "unregister" eaten entity
        if(oReturnedFood.getWeight() <= 0){
        	oEatenEntity.setRegistered(false);
        }
        
        //2) the stimulation of the erogenous zone
        
        //normalize weight to 0...1 referencing on bit size max
        double rNormalizedWeight = oReturnedFood.getWeight()/mrBiteSizeMax;
       
        double rAgressivStimulus;
        double rLibidinousStimulus;
        rAgressivStimulus = oReturnedFood.getMrAggressivStimulationFactor()*rNormalizedWeight*mrAggressivEatFactor;
        
      	rLibidinousStimulus = oReturnedFood.getMrLibidinousStimulationFactor()*rNormalizedWeight*mrLibidinousEatFactor;




        oBody.getIntraBodySystem().getErogenousZonesSystem().StimulateOralAggressivMucosa(rAgressivStimulus);
        oBody.getIntraBodySystem().getErogenousZonesSystem().StimulateOralLibidinousMucosa(rLibidinousStimulus);
        

		//3) attach eat to the self 
        
        clsAction oAction = new clsAction(1);
        oAction.setActionName("EAT");
        oAction.attachEntity(oEatenEntity);
        moEntity.addAction(oAction);
		
		return true;
	}	

}
