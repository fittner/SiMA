/**
 * @author Benny D�nz
 * 21.06.2009, 13:13:07
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;
import java.util.HashMap;

import sim.physics2D.physicalObject.PhysicalObject2D;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.sensors.external.clsSensorVision;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyParts;
import bw.body.io.actuators.actionProxies.*;
import bw.body.itfget.itfGetBody;
import decisionunit.itf.actions.*;
import enums.eSensorExtType;

/**
 * Action Executor for killing
 * Proxy itfAPKillable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prForceScalingFactor = Scales the force applied to the force felt by the entity to be killed (default = 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorKill extends clsActionExecutor{

	static double srStaminaBase = 4f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Force) ; 			
	static double srStaminaScalingFactor = 0; //0.001f;  

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;
	
	private double mrForceScalingFactor;

	public clsExecutorKill(clsEntity poEntity,eSensorExtType poRangeSensor, double prForceScalingFactor) {
		moEntity=poEntity;
		moRangeSensor=poRangeSensor;
		mrForceScalingFactor=prForceScalingFactor;
		
		moMutEx.add(clsActionEat.class);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_KILL;
	}
	@Override
	protected void setName() {
		moName="Kill executor";	
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
		clsActionKill oCommand =(clsActionKill) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,oCommand.getForce()) ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsActionKill oCommand =(clsActionKill) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Is something in range
		HashMap<Integer, PhysicalObject2D> oSearch = ((clsSensorVision) oBody.getExternalIO().moSensorExternal.get(moRangeSensor)).getViewObj();
		itfAPKillable oKilledEntity = (itfAPKillable) findSingleEntityInRange(oSearch,itfAPKillable.class) ;

		if (oKilledEntity==null) {
			//Nothing in range then send fast Messenger
			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
			oFastMessengerSystem.addMessage(mePartId, eBodyParts.BRAIN, 1);
			return false;
		} 

		//Check if killing is ok
		double rDamage = oKilledEntity.tryKill(oCommand.getForce()*mrForceScalingFactor);
		if (rDamage>0) {
			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
			return false;
		}

		//Kill!
		oKilledEntity.kill(oCommand.getForce());
		
		return true;
	}	

}
