/**
 * clsExecutorCultivate.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 28.08.2009, 16:19:40
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
 * Action Executor for Cultivation
 * Proxy itfAPCultivatable
 * Parameters: 
 *  poRangeSensor = Visionsensor to use
 *  
 * @author Benny Dï¿½nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorCultivate extends clsActionExecutor{

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Amount) ; 			
	static double srStaminaScalingFactor = 0.001f;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;

	public static final String P_RANGESENSOR = "rangesensor";

	public clsExecutorCultivate(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionAttackBite.class);
		moMutEx.add(clsActionAttackLightning.class);
		moMutEx.add(clsActionKiss.class);
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.MANIPULATE_AREA.toString());
		
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
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_CULTIVATE;
	}
	@Override
	protected void setName() {
		moName="Cultivation executor";	
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
		clsActionCultivate oCommand =(clsActionCultivate) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,oCommand.getAmount()) ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionCultivate oCommand =(clsActionCultivate) poCommand; 

		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		//Is something in range
		itfAPCultivatable oCultivatedEntity = (itfAPCultivatable) findSingleEntityInRange(moEntity, oBody, moRangeSensor ,itfAPCultivatable.class) ;

		if (oCultivatedEntity==null) {
			//Nothing in range then nothing happens
			return false;
		} 

		//Cultivate!
		oCultivatedEntity.cultivate(oCommand.getAmount());
		
		return true;
	}	
	
}
