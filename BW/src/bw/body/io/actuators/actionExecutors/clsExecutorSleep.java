/**
 * clsExecutorSleep.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 15.09.2009, 08:30:35
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.body.io.actuators.actionProxies.*;
import decisionunit.itf.actions.*;
import enums.eActionSleepIntensity;

/**
 * Action Executor for Sleeping
 * Proxy itfAPSleep
 * Parameters: 
 *  None
 * 
 * @author Benny Dï¿½nz
 * 15.09.2009, 08:32:13
 * 
 */
public class clsExecutorSleep extends clsActionExecutor{
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	//private clsEntity moEntity;
	private ArrayList<itfAPSleep> moNotifyLight;
	private ArrayList<itfAPSleep> moNotifyDeep;

	public clsExecutorSleep(String poPrefix, clsBWProperties poProp, clsEntity poEntity, ArrayList<itfAPSleep> poNotifyLight, ArrayList<itfAPSleep> poNotifyDeep ) {
		//moEntity=poEntity;
		moNotifyLight=poNotifyLight;
		moNotifyDeep=poNotifyDeep;
		
		moMutEx.add(clsActionAttackBite.class);
		moMutEx.add(clsActionAttackLightning.class);
		moMutEx.add(clsActionCultivate.class);
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionEat.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionKiss.class);
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionMoveToEatableArea.class);
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionToInventory.class);
		moMutEx.add(clsActionTurn.class);
		
		applyProperties(poPrefix,poProp);
	}


	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	}
	

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_SLEEP;
	}
	@Override
	protected void setName() {
		moName="Sleep executor";	
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
		return 0 ;
	}


	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionSleep oCommand =(clsActionSleep) poCommand; 

		ArrayList<itfAPSleep> oNotify = null;
		
		if (oCommand.getIntensity()==eActionSleepIntensity.LIGHT) oNotify=moNotifyLight;
		if (oCommand.getIntensity()==eActionSleepIntensity.DEEP) oNotify=moNotifyDeep;

		if (oNotify==null) return false;
		
		for(int i=0; i<oNotify.size(); i++){
			itfAPSleep poObject = oNotify.get(i);
			poObject.sleep();
		}
		
		return true;
	}	
	

}
