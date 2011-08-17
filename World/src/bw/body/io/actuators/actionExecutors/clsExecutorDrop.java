/**
 * @author Benny D�nz
 * 05.07.2009, 12:07:04
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
 * Action Executor for dropping objects
 * 
 * @author Benny D�nz
 * 05.07.2009, 12:07:04
 * 
 */
public class clsExecutorDrop  extends clsActionExecutor{

	private clsMobile moEntity;

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	public clsExecutorDrop(String poPrefix, clsProperties poProp, clsMobile poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionFromInventory.class);
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
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_DROP;
	}
	@Override
	protected void setName() {
		moName="Drop executor";	
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
		if (!(moEntity instanceof clsMobile)) return false;
		clsMobile oMEntity = (clsMobile) moEntity;
		
		if (oMEntity.getInventory().getCarriedEntity()==null) return false;
		
		itfAPCarryable oEntity;

		//try to drop
		try {
			//decrease entity holders
			oMEntity.getInventory().getCarriedEntity().decHolders();
			
			oEntity= (itfAPCarryable)oMEntity.getInventory().getCarriedEntity(); 
			oMEntity.getInventory().setCarriedEntity(null);
		} catch(Throwable e) {
			return false;			
		}
		
		oEntity.setCarriedBindingState(eBindingState.NONE);
		 
		return true;
	}
}
