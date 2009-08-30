/**
 * clsExecutorMoveToEatableArea.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 29.08.2009, 09:58:37
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
 * Action Executor for moving objects from one area to another
 * Parameters: none
 *   mrForceScalingFactor = Relation of Force to energy  (Default=1)
 * 	 poRangeSource = Range to search for the object
 *   poRangeDest = Range to move the object to
 * 
 * @author Benny Dï¿½nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorMoveToArea extends clsActionExecutor{

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Speed) ; 			
	static double srStaminaScalingFactor = 0.01f;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSource;
	private eSensorExtType moRangeDest;
	
	private double mrForceScalingFactor;

	public static final String P_RANGESOURCE = "rangesource";
	public static final String P_RANGEDEST = "rangedest";
	public static final String P_FORCECALINGFACTOR = "forcescalingfactor";

	public clsExecutorMoveToArea(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionCultivate.class);
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);

		applyProperties(poPrefix,poProp);
	}

	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_RANGESOURCE, eSensorExtType.VISION.toString());
		oProp.setProperty(pre+P_RANGEDEST, eSensorExtType.VISION.toString());
		oProp.setProperty(pre+P_FORCECALINGFACTOR, 1f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		moRangeSource=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESOURCE));
		moRangeDest=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGEDEST));
		mrForceScalingFactor=poProp.getPropertyFloat(pre+P_FORCECALINGFACTOR);
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_MOVETOAREA;
	}
	@Override
	protected void setName() {
		moName="Move to area executor";	
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
		clsActionMoveToEatableArea oCommand =(clsActionMoveToEatableArea) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,oCommand.getForce()) ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsActionMoveToEatableArea oCommand =(clsActionMoveToEatableArea) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Is something in range
		itfAPCarryable oEntity = (itfAPCarryable) findSingleEntityInRange(moEntity , oBody, moRangeSource  ,itfAPCarryable.class) ;
		
		return true;
	}	
	
}