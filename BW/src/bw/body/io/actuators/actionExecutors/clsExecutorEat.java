/**
 * @author Benny D�nz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;

import ARSsim.physics2D.physicalObject.clsCollidingObject;
import bw.body.clsComplexBody;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.io.sensors.ext.clsSensorVision;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyParts;
import bw.utils.tools.clsFood;
import bw.body.io.actuators.actionProxies.*;
import bw.body.itfget.itfGetBody;
import decisionunit.itf.actions.*;
import enums.eSensorExtType;

/**
 * Action Executor for eating
 * Proxy itfAPEatable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prBiteSize = Size of bite taken when eating (default = weight 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsExecutorEat extends clsActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 			
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private double mrBiteSize;
	private clsEntity moEntity;
	private eSensorExtType moRangeSensor;

	public clsExecutorEat(clsEntity poEntity,eSensorExtType poRangeSensor, double prBiteSize) {
		moEntity=poEntity;
		moRangeSensor=poRangeSensor;
		mrBiteSize=prBiteSize;
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_EAT;
	}
	@Override
	protected void setName() {
		moName="Eat executor";	
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
		return srStaminaDemand;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		
		//Is something in range
		ArrayList<clsCollidingObject>  oSearch = ((clsSensorVision) oBody.getExternalIO().moSensorExternal.get(moRangeSensor)).getSensorData();
		itfAPEatable oEatenEntity = (itfAPEatable) findSingleEntityInRange(oSearch,itfAPEatable.class) ;
		
		if (oEatenEntity==null) {
			//Nothing in range then send fast Messenger
			clsFastMessengerSystem oFastMessengerSystem = oBody.getInternalSystem().getFastMessengerSystem();
			oFastMessengerSystem.addMessage(mePartId, eBodyParts.BRAIN, 1);
			return false;
		} 

		//Check if eating is ok
		double rDamage = oEatenEntity.tryEat();
		if (rDamage>0) {
			oBody.getInternalSystem().getHealthSystem().hurt(rDamage);
			return false;
		}
		
		//Eat!
        clsFood oReturnedFood =oEatenEntity.Eat(mrBiteSize);
        if(oReturnedFood != null) {                
        	oBody.getInterBodyWorldSystem().getConsumeFood().digest(oReturnedFood);
        }
		
		return true;
	}	

}
