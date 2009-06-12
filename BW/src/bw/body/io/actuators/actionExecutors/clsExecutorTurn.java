/**
 * @author Benny Dönz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import sim.physics2D.util.Angle;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import decisionunit.itf.actions.*;
/**
 * TODO (Benny Dönz) - insert description 
 * 
 * @author Benny Dönz
 * 13.05.2009, 21:45:05
 * 
 */
public class clsExecutorTurn extends clsActionExecutor{

	public String getName() {
		return "Turn executor";
	}
	
	public boolean execute(itfActionCommand poCommand, clsEntity poEntity) {
		clsActionTurn oCommand = (clsActionTurn) poCommand;
    	switch( oCommand.getDirection() )
    	{
    	case TURN_LEFT:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI*(-1)));
    	case TURN_RIGHT:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(oCommand.getAngle()/360*Math.PI/200));
    	}
    	return true;
	}	
}
