/**
 * @author
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.itfAPEatable;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import bw.factories.eImages;
import bw.utils.enums.eBodyParts;
import bw.utils.tools.clsFood;
import du.enums.eSensorExtType;
import du.itf.actions.*;

/**
 * Action Executor for inner speech
 * 
 * Parameters:
 * 
 * @author 

 * 
 */
public class clsExecutorInnerSpeech extends clsActionExecutor{

	static double mrpleasure = 0.5; //0.5f;		//Lustgewinn beim Aussprechen
	static double srStaminaDemand = 0.5; //0.5f;		//Stamina demand 
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private double mrBiteSize;
	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;
	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_BIZESIZE = "bitesize";
	public static final String P_ACTIONEX = "actionexecutor";
  


	public clsExecutorInnerSpeech(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
	
		applyProperties(poPrefix,poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		oProp.setProperty(pre+P_BIZESIZE, 3f); //0.3f
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
		mrBiteSize=poProp.getPropertyFloat(pre+P_BIZESIZE);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_INNERSPEECH;
	}
	@Override
	protected void setName() {
		moName="Speech executor";	
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
		
		
		
		//Is something in range
		clsEntity oEatenEntity = (clsEntity) findSingleEntityInRange(moEntity, oBody, moRangeSensor ,itfAPEatable.class) ;
		
		if (oEatenEntity==null) {
			moEntity.setOverlayImage(eImages.Overlay_Action_InnerSpeech);
			//Nothing in range then send fast Messenger, = "bites in hins tounge"
			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
			oFastMessengerSystem.addMessage(mePartId, eBodyParts.BRAIN, 1);
			return false;
		} 
		else {
			//setting a overlay image, normal eating
			moEntity.setOverlayImage(eImages.Overlay_Action_InnerSpeech);
			
		}
			

		//Check if eating is ok
		double rDamage = ((itfAPEatable)oEatenEntity).tryEat();
		if (rDamage>0) {
			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
			return false;
		}
		
		//Eat!
        clsFood oReturnedFood =((itfAPEatable)oEatenEntity).Eat(mrBiteSize);
        if(oReturnedFood != null) {                
        	oBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood);
        }
        
        //FIXME (horvath) - "unregister" eaten entity
        if(oReturnedFood.getWeight() <= 0){
        	oEatenEntity.setRegistered(false);
        }
		
		return true;
	}	

}