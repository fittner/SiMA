/**
 * clsExecutorKiss.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny D�nz
 * 28.08.2009, 18:02:30
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.body.io.actuators.actionProxies.*;
import bw.body.itfget.itfGetBody;
import decisionunit.itf.actions.*;
import enums.eActionKissIntensity;
import enums.eSensorExtType;

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


	public clsExecutorKiss(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionKill.class);
		moMutEx.add(clsActionAttack.class);
		moMutEx.add(clsActionCultivate.class);
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
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
	public ArrayList<Class<?>> getMutualExclusions(itfActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(itfActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(itfActionCommand poCommand) {
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
	public boolean execute(itfActionCommand poCommand) {
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