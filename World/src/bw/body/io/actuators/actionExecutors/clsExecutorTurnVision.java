/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;

import sim.physics2D.util.Angle;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.factories.eImages;
import du.enums.eActionTurnDirection;
import du.itf.actions.*;
/**
 * Action Executor for turning
 * Parameters:
 *    none 
 *
 * @author Muchitsch
 * 13.05.2009, 21:45:05
 * 
 */
public class clsExecutorTurnVision extends clsActionExecutor{

	static double srStaminaBase = 1.1f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Angle) ; 			
	static double srStaminaScalingFactor = 0.001f;   

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;
	
	public clsExecutorTurnVision(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
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
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TURNVISION;
	}
	@Override
	protected void setName() {
		moName="TurnVision executor";
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
		clsActionTurn oCommand =(clsActionTurn) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,Math.abs( oCommand.getAngle())) ;
	}

	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionTurnVision oCommand = (clsActionTurnVision) poCommand;
		double mnAngle = oCommand.getAngle();
		
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT){
    		moEntity.setOverlayImage(eImages.Overlay_Action_TurnLeft);
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI*(-1.0)));
    	}
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT){
    		moEntity.setOverlayImage(eImages.Overlay_Action_TurnRight);
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI));
    	}
    	return true;
	}	
}
