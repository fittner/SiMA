/**
 * clsExecutorSpeech.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author MW
 * 25.02.2013, 12:57:01
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.base.clsEntity;
import bw.entities.base.clsSpeech;
import bw.factories.clsRegisterEntity;
import bw.body.itfget.itfGetBody;
import du.itf.actions.*;


/**
 * DOCUMENT (MW) - Speech Action 
 * 
 * @author MW
 * 25.02.2013, 12:57:01
 * 
 */
public class clsExecutorSpeech extends clsActionExecutor{

	static double mrpleasure = 0.5; 	 //0.5f;		//Lustgewinn beim Aussprechen
	static double srStaminaDemand = 0.5; //0.5f;		//Stamina demand 

	static double srStaminaBase = 2f;					//Stamina demand=srStaminaScalingFactor*srStaminaBase; 			
	static double srStaminaScalingFactor = 0.01f;  
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorSpeech(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionAttackBite.class);
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionKiss.class);
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);

	}


	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_SPEECH;
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
		return srStaminaScalingFactor*srStaminaBase;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionSpeech oCommand =(clsActionSpeech) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Get Speech
		clsSpeech oSpeech = oBody.getInterBodyWorldSystem().getCreateSpeech().getSpeech(oCommand.getData(), 1);
		if (oSpeech==null) return false; 
		
		//Drop it in Mason
		clsRegisterEntity.registerEntity(oSpeech);
		oSpeech.setRegistered(true);
		
		oSpeech.addSensor();
		
		return true;
	}		
}