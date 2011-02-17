/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.io.actuators.actionExecutors;

import java.util.ArrayList;
import nao.body.io.actuators.clsActionExecutor;
import du.itf.actions.*;
/**
 * Action Executor for turning
 * Parameters:
 *    none 
 *
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 */
public class clsNAOExecutorSendMessage extends clsActionExecutor{

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	
	public clsNAOExecutorSendMessage() {
		super();
	}

	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
//		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TURN;
	}
	@Override
	protected void setName() {
//		moName="Turn executor";
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx;
	}
	
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionTurn oCommand = (clsActionTurn) poCommand;
//    	if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT) ((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI*(-1.0)));
//    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT) ((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI));
    	return true;
	}	
}
