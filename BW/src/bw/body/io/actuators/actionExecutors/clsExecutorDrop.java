/**
 * @author Benny Dönz
 * 05.07.2009, 12:07:04
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;
import java.util.HashMap;

import sim.physics2D.constraint.PinJoint;
import statictools.clsSingletonUniqueIdGenerator;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.itfget.itfGetBody;
import decisionunit.itf.actions.*;
import enums.eSensorExtType;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.enums.eBindingState;

/**
 * Action Executor for dropping objects
 * 
 * @author Benny Dönz
 * 05.07.2009, 12:07:04
 * 
 */
public class clsExecutorDrop  extends clsActionExecutor{

	private clsMobile moEntity;

	private ArrayList<Class> moMutEx = new ArrayList<Class>();

	public clsExecutorDrop(clsMobile poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExDrop();
	}
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_DROP;
	}
	protected void setName() {
		moName="Drop executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	public ArrayList<Class> getMutualExclusions(itfActionCommand poCommand) {
		return moMutEx; 
	}

	/*
	 * Energy and stamina demand 
	 */
	public float getEnergyDemand(itfActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	public float getStaminaDemand(itfActionCommand poCommand) {
		return 0;
	}
	
	/*
	 * Executor 
	 */
	public boolean execute(itfActionCommand poCommand) {
		if (!(moEntity instanceof clsMobile)) return false;
		clsMobile oMEntity = (clsMobile) moEntity;
		
		if (oMEntity.getInventory().getCarriedEntity()==null) return false;
		
		itfAPCarryable oEntity;

		//try to drop
		try {
			oEntity= (itfAPCarryable)oMEntity.getInventory().getCarriedEntity(); 
			oMEntity.getInventory().setCarriedEntity(null);
		} catch(Throwable e) {
			return false;			
		}
		
		oEntity.setCarriedBindingState(eBindingState.NONE);
		return true;
	}
}
