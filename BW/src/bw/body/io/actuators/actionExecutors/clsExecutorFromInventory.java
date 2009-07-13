/**
 * @author Benny D�nz
 * 05.07.2009, 12:07:31
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;

import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import decisionunit.itf.actions.*;
import bw.entities.clsMobile;
import bw.utils.enums.eBindingState;

/**
 * Action Executor for moving objects from the inventory and set them as carried
 * 
 * @author Benny D�nz
 * 05.07.2009, 12:07:31
 * 
 */
public class clsExecutorFromInventory extends clsActionExecutor{

	private clsMobile moEntity;

	private ArrayList<Class> moMutEx = new ArrayList<Class>();

	public clsExecutorFromInventory(clsMobile poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionToInventory.class);
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExFromInventory();
	}
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_FROMINVENTORY;
	}
	@Override
	protected void setName() {
		moName="Get from inventory executor";	
	}


	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class> getMutualExclusions(itfActionCommand poCommand) {
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
		return 0;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsActionFromInventory oCommand =(clsActionFromInventory) poCommand; 
		itfAPCarryable oEntity;
		
		//set as carried
		try {
			oEntity = (itfAPCarryable) moEntity.getInventory().getInventoryItem(oCommand.getIndex());
			moEntity.getInventory().setCarriedEntity(oEntity.getCarryableEntity() );
		} catch(Throwable e) {
			return false;			
		}
	
		oEntity.setCarriedBindingState(eBindingState.CARRIED);
		return true;
	}
}
