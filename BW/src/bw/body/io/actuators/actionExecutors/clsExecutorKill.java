/**
 * @author Benny Dönz
 * 21.06.2009, 13:13:07
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;
import java.util.HashMap;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.sensors.external.clsSensorVision;
import bw.entities.clsEntity;
import bw.utils.datatypes.clsMutableFloat;
import bw.utils.enums.partclass.clsPartBrain;
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
 * @author Benny Dönz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorKill extends clsActionExecutor{

	static float srStaminaBase = 4f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Force) ; 			
	static float srStaminaScalingFactor = 0.01f;  

	private ArrayList<Class> moMutEx = new ArrayList<Class>();

	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;
	
	private float mrForceScalingFactor;

	public clsExecutorKill(clsEntity poEntity,eSensorExtType poRangeSensor, float prForceScalingFactor) {
		moEntity=poEntity;
		moRangeSensor=poRangeSensor;
		mrForceScalingFactor=prForceScalingFactor;
		
		moMutEx.add(clsActionEat.class);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExKill();
	}
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_KILL;
	}
	protected void setName() {
		moName="Kill executor";	
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
		clsActionKill oCommand =(clsActionKill) poCommand;
		return srStaminaScalingFactor* (float) Math.pow(srStaminaBase,oCommand.getForce()) ;
	}

	/*
	 * Executor 
	 */
	public boolean execute(itfActionCommand poCommand) {
		clsActionKill oCommand =(clsActionKill) poCommand; 
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();

		//Is something in range
		HashMap oSearch = ((clsSensorVision) oBody.getExternalIO().moSensorExternal.get(moRangeSensor)).getViewObj();
		itfAPKillable oKilledEntity = (itfAPKillable) findSingleEntityInRange(oSearch,itfAPKillable.class) ;

		if (oKilledEntity==null) {
			//Nothing in range then send fast Messenger
			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
			oFastMessengerSystem.addMessage(moBodyPart, new clsPartBrain(), 1);
			return false;
		} 

		//Check if killing is ok
		float rDamage = oKilledEntity.tryKill(oCommand.getForce());
		if (rDamage>0) {
			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
			return false;
		}

		//Kill!
		oKilledEntity.kill(oCommand.getForce());
		
		return true;
	}	

}
