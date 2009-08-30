/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import sim.physics2D.util.Angle;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import decisionunit.itf.actions.*;
import enums.eActionTurnDirection;
/**
 * Action Executor for turning
 * Parameters:
 *    none 
 *
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 */
public class clsExecutorTurn extends clsActionExecutor{

	static double srStaminaBase = 1.1f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Angle) ; 			
	static double srStaminaScalingFactor = 0.001f;   

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;
	
	public clsExecutorTurn(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();

		return oProp;
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TURN;
	}
	@Override
	protected void setName() {
		moName="Turn executor";
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
		clsActionTurn oCommand =(clsActionTurn) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,Math.abs( oCommand.getAngle())) ;
	}

	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsActionTurn oCommand = (clsActionTurn) poCommand;
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT) ((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI*(-1.0)));
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT) ((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI));
    	return true;
	}	
}
