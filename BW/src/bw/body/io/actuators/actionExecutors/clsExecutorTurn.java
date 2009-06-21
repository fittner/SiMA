/**
 * @author Benny Dönz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

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
 * @author Benny Dönz
 * 13.05.2009, 21:45:05
 * 
 */
public class clsExecutorTurn extends clsActionExecutor{

	static float srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Angle) ; 			
	static float srStaminaScalingFactor = 0.001f;   

	private ArrayList<Class> moMutEx = new ArrayList<Class>();
	private clsEntity moEntity;
	
	public clsExecutorTurn(clsEntity poEntity) {
		moEntity=poEntity;
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExTurn();
	}
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TURN;
	}
	protected void setName() {
		moName="Turn executor";
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
		clsActionTurn oCommand =(clsActionTurn) poCommand;
		return srStaminaScalingFactor* (float) Math.pow(srStaminaBase,Math.abs( oCommand.getAngle())) ;
	}

	public boolean execute(itfActionCommand poCommand) {
		clsActionTurn oCommand = (clsActionTurn) poCommand;
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT) ((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI*(-1.0)));
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT) ((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI));
    	return true;
	}	
}
