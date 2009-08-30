/**
 * @author Benny D�nz
 * 05.07.2009, 12:06:53
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import decisionunit.itf.actions.*;
import bw.entities.clsMobile;
import bw.utils.enums.eBindingState;
import enums.eSensorExtType;
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

	public clsExecutorPickUp(String poPrefix, clsBWProperties poProp, clsMobile poEntity) {
		moEntity=poEntity;

		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
		
		applyProperties(poPrefix,poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
		oProp.setProperty(pre+P_MASSSCALINGFACTOR, 0.01f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
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
	public ArrayList<Class<?>> getMutualExclusions(itfActionCommand poCommand) {
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
	public boolean execute(itfActionCommand poCommand) {
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
		} catch(Throwable e) {
			return false;			
		}
		
        oEntity.setCarriedBindingState(eBindingState.CARRIED);
        
		return true;
	}
}
