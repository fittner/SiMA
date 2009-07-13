/**
 * @author Benny D�nz
 * 05.07.2009, 12:06:53
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;
import java.util.HashMap;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.actuators.actionProxies.*;
import decisionunit.itf.actions.*;
import bw.body.io.sensors.external.clsSensorVision;
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

	private ArrayList<Class> moMutEx = new ArrayList<Class>();

	private clsMobile moEntity;
	private eSensorExtType moRangeSensor;

	private double mrMassScalingFactor;
	
	public clsExecutorPickUp(clsMobile poEntity,eSensorExtType poRangeSensor, double prMassScalingFactor) {
		moEntity=poEntity;
		moRangeSensor=poRangeSensor;
		mrMassScalingFactor=prMassScalingFactor;
		
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
	}
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExPickUp();
	}
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

		//Is something in range
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		HashMap oSearch = ((clsSensorVision) oBody.getExternalIO().moSensorExternal.get(moRangeSensor)).getViewObj();
		itfAPCarryable oEntity = (itfAPCarryable) findSingleEntityInRange(oSearch,itfAPCarryable.class) ;

		//nothing there = waste energy
		if (oEntity==null) return 0;
		if (oEntity.getCarryableEntity() ==null) return 0;

		//Calculate stamina from mass/maxmass relation
		return  (mrMassScalingFactor * oEntity.getCarryableEntity().getMass()); 
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
		HashMap oSearch = ((clsSensorVision) oBody.getExternalIO().moSensorExternal.get(moRangeSensor)).getViewObj();
		itfAPCarryable oEntity = (itfAPCarryable) findSingleEntityInRange(oSearch,itfAPCarryable.class) ;
		if (oEntity==null) return false;

		//Try to pick it up
		try {
			oMEntity.getInventory().setCarriedEntity(oEntity.getCarryableEntity());
		} catch(Throwable e) {
			return false;			
		}
		
        oEntity.setCarriedBindingState(eBindingState.CARRIED);
		return true;
	}
}
