/**
 * clsExecutorAttack.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 28.08.2009, 18:30:17
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
import enums.eSensorExtType;

/**
 * Action Executor for attacking
 * Proxy itfAPKillable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prForceScalingFactor = Scales the force applied to the force felt by the attacked entity (default = 1)
 * 
 * @author Benny Dï¿½nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorAttack extends clsActionExecutor{

	static double srStaminaBase = 1f;			//Stamina demand =srStaminaBase*Force*srStaminaScalingFactor; 			
	static double srStaminaScalingFactor = 0.05;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;
	
	private double mrForceScalingFactor;

	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_FORCECALINGFACTOR = "forcescalingfactor";

	public clsExecutorAttack(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionKill.class);
		moMutEx.add(clsActionKiss.class);
		moMutEx.add(clsActionCultivate.class);

		applyProperties(poPrefix,poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.VISION.toString());
		oProp.setProperty(pre+P_FORCECALINGFACTOR, 1f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
		mrForceScalingFactor=poProp.getPropertyFloat(pre+P_FORCECALINGFACTOR);
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_ATTACK;
	}
	@Override
	protected void setName() {
		moName="Attack executor";	
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
		clsActionAttack oCommand =(clsActionAttack) poCommand;
		return srStaminaScalingFactor* srStaminaBase*oCommand.getForce() ;
	}


	/*
	 * Executor 
	 */
	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsActionAttack oCommand =(clsActionAttack) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Is something in range
		itfAPKillable oAttackEntity = (itfAPKillable) findNamedEntityInRange(oCommand.getOpponentID() , oBody, moRangeSensor ,itfAPKillable.class) ;

		if (oAttackEntity==null) {
			//Nothing in range
			return false;
		} 

		//Attack!
		oAttackEntity.attack(oCommand.getForce()*mrForceScalingFactor);
		
		return true;
	}	
	
}