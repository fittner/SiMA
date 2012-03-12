/**
 * clsExecutorKiss.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 28.08.2009, 18:02:30
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.body.io.actuators.actionProxies.*;
import bw.body.itfget.itfGetBody;
import du.enums.eActionKissIntensity;
import du.enums.eSensorExtType;
import du.itf.actions.*;

/**
 * Action Executor for Kissing
 * Proxy itfAPKissable
 * Parameters: 
 *  poRangeSensor = Visionsensor to use
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorKiss extends clsActionExecutor{

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*(1..3=LOW...STRONG) ; 			
	static double srStaminaScalingFactor = 0.001f;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;

	public static final String P_RANGESENSOR = "rangesensor";


	public clsExecutorKiss(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionAttackBite.class);
		moMutEx.add(clsActionAttackLightning.class);
		moMutEx.add(clsActionCultivate.class);
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_KISS;
	}
	@Override
	protected void setName() {
		moName="Kissing executor";	
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
		clsActionKiss oCommand =(clsActionKiss) poCommand;
		double rIntensity=1;
		if (oCommand.getIntensity()==eActionKissIntensity.MIDDLE) rIntensity=2;
		if (oCommand.getIntensity()==eActionKissIntensity.STRONG) rIntensity=4;
		return srStaminaScalingFactor* (srStaminaBase*rIntensity) ;
	}


	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionKiss oCommand =(clsActionKiss) poCommand; 

		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		//Is something in range
		itfAPKissable oKissedEntity = (itfAPKissable) findSingleEntityInRange(moEntity, oBody, moRangeSensor ,itfAPKissable.class) ;

		if (oKissedEntity==null) {
			//Nothing in range then nothing happens
			return false;
		} 

		//Try
		if (oKissedEntity.tryKiss(oCommand.getIntensity())==false) return false; 
		
		//Kiss!
		double rIntensity=1;
		if (oCommand.getIntensity()==eActionKissIntensity.MIDDLE) rIntensity=2;
		if (oCommand.getIntensity()==eActionKissIntensity.STRONG) rIntensity=4;

		oKissedEntity.kiss(oCommand.getIntensity());
		oBody.getInterBodyWorldSystem().getEffectKiss().kiss(mePartId, rIntensity);
		
		return true;
	}	
	
}
