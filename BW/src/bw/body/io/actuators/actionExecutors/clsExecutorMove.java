/**
 * @author Benny Dönz
 * 13.05.2009, 21:44:55
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import java.util.ArrayList;

import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import decisionunit.itf.actions.*;

/**
 * Action Executor for movement
 * Parameters:
 *    prSpeedScalingFactor = Relation of Speed to Energy. For Average Speed of "4", default ist 10 
 * 
 * @author Benny Dönz
 * 13.05.2009, 21:44:55
 * 
 */
public class clsExecutorMove extends clsActionExecutor{

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Speed) ; 			
	static double srStaminaScalingFactor = 0.01f;  
	
	private ArrayList<Class> moMutEx = new ArrayList<Class>();

	private clsEntity moEntity;
	private double mrSpeedScalingFactor;
	
	public clsExecutorMove(clsEntity poEntity,double prSpeedScalingFactor) {
		moEntity=poEntity;
		mrSpeedScalingFactor=prSpeedScalingFactor;
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	protected void setBodyPart() {
		moBodyPart = new bw.utils.enums.partclass.clsPartActionExMove();
	}
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_MOVE;
	}
	protected void setName() {
		moName="Move executor";
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
	public double getEnergyDemand(itfActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	public double getStaminaDemand(itfActionCommand poCommand) {
		clsActionMove oCommand =(clsActionMove) poCommand;
		return srStaminaScalingFactor* (float) Math.pow(srStaminaBase,oCommand.getSpeed()) ;
	}
	
	/*
	 * Executor 
	 */
	public boolean execute(itfActionCommand poCommand) {
		clsActionMove oCommand =(clsActionMove) poCommand; 
    	switch(oCommand.getDirection() )
    	{
    	case MOVE_FORWARD:
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.moveForward(mrSpeedScalingFactor*oCommand.getSpeed());
    	case MOVE_BACKWARD:
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.backup();
    	}
    	return true;
	}	
}
