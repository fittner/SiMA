/**
 * @author Benny Dönz
 * 05.07.2009, 12:07:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;
import java.util.HashMap;

import statictools.clsSingletonUniqueIdGenerator;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import decisionunit.itf.actions.*;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.utils.enums.eBindingState;

/**
 * Action Executor for moving the carried object to the inventory
 * 
 * @author Benny Dönz
 * 05.07.2009, 12:07:22
 * 
 */
public class clsExecutorToInventory extends clsActionExecutor {

	private clsMobile moEntity;

	private ArrayList<Class> moMutEx = new ArrayList<Class>();

	public clsExecutorToInventory(clsMobile poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionPickUp.class);
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExToInventory();
	}
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TOINVENTORY;
	}
	protected void setName() {
		moName="Move to inventory executor";	
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
	public double getEnergyDemand(itfActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	public double getStaminaDemand(itfActionCommand poCommand) {
		return 0;
	}

	/*
	 * Executor 
	 */
	public boolean execute(itfActionCommand poCommand) {
		itfAPCarryable oEntity;
		
		//move carried to inventory
		try {
			oEntity= (itfAPCarryable) moEntity.getInventory().moveCarriedToInventory();
		} catch(Throwable e) {
			return false;			
		}
		if (oEntity==null) return false;
		
		oEntity.setCarriedBindingState(eBindingState.INVENTORY);
		return true;
	}
}
