/**
 * @author Benny D�nz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;
import bw.body.clsComplexBody;
import bw.body.io.actuators.clsInternalActionExecutor;
import bw.entities.base.clsEntity;
import bw.body.itfget.itfGetBody;
import du.enums.eSpeechExpression;
import du.itf.actions.*;

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
public class clsExecutorSpeechInvite extends clsInternalActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 			
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private double mranticFactor;
	
	public static final String P_ANTICIPATION_FACTOR = "anticipationfactor";
	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_BIZESIZE = "bitesize";

	public clsExecutorSpeechInvite(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);

		applyProperties(poPrefix,poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsInternalActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_ANTICIPATION_FACTOR, 10f);
		//oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		//oProp.setProperty(pre+P_BIZESIZE, 3f); //0.3f
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		//mranticFactor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_ANTICIPATION_FACTOR));
		mranticFactor=poProp.getPropertyFloat(pre+P_ANTICIPATION_FACTOR);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_SPEECHEXPRESSIONS;
	}
	@Override
	protected void setName() {
		moName="Speech Inv executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsInternalActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsInternalActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsInternalActionCommand poCommand) {
		return srStaminaDemand;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsInternalActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		moEntity.setSpeechExpressionOverlayImage(eSpeechExpression.INVITED);
		//setFacialExpressionOverlayImage
		//oBody.setSpeechExpression(eSpeechExpression.INVITED);
		
		
		
//		//Is something in range
//		clsEntity oEatenEntity = (clsEntity) findSingleEntityInRange(moEntity, oBody, moRangeSensor ,itfAPEatable.class) ;
//		
//		if (oEatenEntity==null) {
//			moEntity.setOverlayImage(eImages.Overlay_Action_EatNothing);
//			//Nothing in range then send fast Messenger, = "bites in hins tounge"
//			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
//			oFastMessengerSystem.addMessage(mePartId, eBodyParts.BRAIN, 1);
//			return false;
//		} 
//		else {
//			//setting a overlay image, normal eating
//			moEntity.setOverlayImage(eImages.Overlay_Action_Eat);
//		}
//			
//
//		//Check if eating is ok
//		double rDamage = ((itfAPEatable)oEatenEntity).tryEat();
//		if (rDamage>0) {
//			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
//			return false;
//		}
//		
//		//Eat!
//        clsFood oReturnedFood =((itfAPEatable)oEatenEntity).Eat(mrBiteSize);
//        if(oReturnedFood != null) {                
//        	oBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood);
//        }
//        
//        //FIXME (horvath) - "unregister" eaten entity
//        if(oReturnedFood.getWeight() <= 0){
//        	oEatenEntity.setRegistered(false);
//        }
//		
		return true;
	}	

}
