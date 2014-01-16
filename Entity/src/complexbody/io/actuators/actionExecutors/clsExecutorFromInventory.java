/**
 * @author Benny D�nz
 * 05.07.2009, 12:07:31
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;

import complexbody.io.actuators.clsActionExecutor;

import du.itf.actions.*;
import entities.abstractEntities.clsMobile;
import entities.actionProxies.*;
import entities.enums.eBindingState;

/**
 * Action Executor for moving objects from the inventory and set them as carried
 * 
 * @author Benny D�nz
 * 05.07.2009, 12:07:31
 * 
 */
public class clsExecutorFromInventory extends clsActionExecutor{

	private clsMobile moEntity;

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	public clsExecutorFromInventory(String poPrefix, clsProperties poProp, clsMobile poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionToInventory.class);
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
		mePartId = entities.enums.eBodyParts.ACTIONEX_FROMINVENTORY;
	}
	@Override
	protected void setName() {
		moName="Get from inventory executor";	
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
