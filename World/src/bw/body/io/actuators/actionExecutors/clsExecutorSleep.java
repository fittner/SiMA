/**
 * clsExecutorSleep.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 15.09.2009, 08:30:35
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.factories.eImages;
import bw.body.io.actuators.actionProxies.*;
import du.enums.eActionSleepIntensity;
import du.itf.actions.*;

/**
 * Action Executor for Sleeping
 * Proxy itfAPSleep
 * Parameters: 
 *  None
 * 
 * @author Benny D�nz
 * 15.09.2009, 08:32:13
 * 
 */
public class clsExecutorSleep extends clsActionExecutor{
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	//private clsEntity moEntity;
	private ArrayList<itfAPSleep> moNotifyLight;
	private ArrayList<itfAPSleep> moNotifyDeep;
	private clsEntity moEntity;

	public clsExecutorSleep(String poPrefix, clsProperties poProp, clsEntity poEntity, ArrayList<itfAPSleep> poNotifyLight, ArrayList<itfAPSleep> poNotifyDeep ) {
		super(poPrefix, poProp);
		
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
		moEntity=poEntity;
		
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

		moEntity.setOverlayImage(eImages.Overlay_Action_Sleep);
		
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
