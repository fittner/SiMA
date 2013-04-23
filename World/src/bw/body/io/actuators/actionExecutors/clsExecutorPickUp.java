/**
 * @author Benny D�nz
 * 05.07.2009, 12:06:53
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import du.enums.eSensorExtType;
import du.itf.actions.*;
import bw.entities.clsMobile;
import bw.utils.enums.eBindingState;
import bw.body.itfget.itfGetBody;

/**
 * Action Executor for picking-up objects
 * Proxy itfAPCarryable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 *   prMassScalingFactor = Amount of Stamina needed per Unit of mass to pick something up 
 * 
 * @author Benny D�nz
 * 05.07.2009, 12:06:53
 * 
 */
public class clsExecutorPickUp  extends clsActionExecutor {

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsMobile moEntity;
	private eSensorExtType moRangeSensor;

	private double mrMassScalingFactor;
	
	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_MASSSCALINGFACTOR = "massscalingfactor";

	public clsExecutorPickUp(String poPrefix, clsProperties poProp, clsMobile poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;

		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.MANIPULATE_AREA.toString());
		oProp.setProperty(pre+P_MASSSCALINGFACTOR, 0.01f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
		mrMassScalingFactor=poProp.getPropertyFloat(pre+P_MASSSCALINGFACTOR);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_PICKUP;
	}
	@Override
	protected void setName() {
		moName="Pick-up executor";	
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

		//Is something in range
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		itfAPCarryable oEntity = (itfAPCarryable) findSingleEntityInRange(moEntity, oBody, moRangeSensor,itfAPCarryable.class) ;

		//nothing there = waste energy
		if (oEntity==null) return 0;
		if (oEntity.getCarryableEntity() ==null) return 0;
		//Calculate stamina from mass/maxmass relation
		return  (mrMassScalingFactor * oEntity.getCarryableEntity().getTotalWeight()); 
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		if (!(moEntity instanceof clsMobile)) return false;
		clsMobile oMEntity = (clsMobile) moEntity;
		
		//Is something in range
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		itfAPCarryable oEntity = (itfAPCarryable) findSingleEntityInRange(moEntity, oBody, moRangeSensor,itfAPCarryable.class) ;
		if (oEntity==null) return false;

		//Try to pick it up
		try {			
			//increase entity holders
			oEntity.getCarryableEntity().incHolders();

			oMEntity.getInventory().setCarriedEntity(oEntity.getCarryableEntity());
			oMEntity.getInventory().moveCarriedToInventory(); //hacked by Kivy (putting the carried object in the inventory): for inventory inspector
																//He tries to pickup objects in range again even if he already holds them.
																//so he loops in picking up and dropping the same object			
			oEntity.setCarriedBindingState(eBindingState.INVENTORY); //hacked by Kivy: copied from clsExecutorToInventory
			
		} catch(Throwable e) {
			return false;			
		}
		
        oEntity.setCarriedBindingState(eBindingState.CARRIED);
        
		return true;
	}
}
