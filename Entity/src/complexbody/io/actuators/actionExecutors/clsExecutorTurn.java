/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;
import sim.physics2D.util.Angle;
import singeltons.eImages;

import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsActionTurn;
import complexbody.io.sensors.datatypes.enums.eActionTurnDirection;

import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsMobile;
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

	static double srStaminaBase = 1.0f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Angle) ; 			
	static double srStaminaScalingFactor = 0.0001f;   

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;
	
	public clsExecutorTurn(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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
		mePartId = entities.enums.eBodyParts.ACTIONEX_TURN;
	}
	@Override
	protected void setName() {
		moName="Turn executor";
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
		clsActionTurn oCommand = (clsActionTurn) poCommand;
        clsAction oAction = new clsAction(1);
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT){
    	    moEntity.setOverlayImage(eImages.Overlay_Action_TurnLeft);
    		//moEntity.setOverlayImage(eImages.Overlay_Action_OuterSpeech_Eat);
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/180*Math.PI*(-1.0)));
            oAction.setActionName("TURN_LEFT");
    	}
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT){
            moEntity.setOverlayImage(eImages.Overlay_Action_TurnRight);
    		//moEntity.setOverlayImage(eImages.Overlay_Action_OuterSpeech_Eat);
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/180*Math.PI));
    		oAction.setActionName("TURN_RIGHT");
    	}
    	
		//Attach action to entity


        moEntity.addAction(oAction);
    	return true;
	}	
}
