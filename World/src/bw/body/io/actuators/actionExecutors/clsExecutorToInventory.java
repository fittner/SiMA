/**
 * @author Benny D�nz
 * 05.07.2009, 12:07:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import du.itf.actions.*;
import bw.entities.clsMobile;
import bw.utils.enums.eBindingState;

/**
 * Action Executor for moving the carried object to the inventory
 * 
 * @author Benny D�nz
 * 05.07.2009, 12:07:22
 * 
 */
public class clsExecutorToInventory extends clsActionExecutor {

	private clsMobile moEntity;

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	public clsExecutorToInventory(String poPrefix, clsProperties poProp, clsMobile poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionPickUp.class);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);

		return oProp;
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TOINVENTORY;
	}
	@Override
	protected void setName() {
		moName="Move to inventory executor";	
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
		return 0;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
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
