/**
 * @author Benny Dönz
 * 13.05.2009, 21:44:55
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import decisionunit.itf.actions.*;

/**
 * TODO (Benny Dönz) - insert description 
 * 
 * @author Benny Dönz
 * 13.05.2009, 21:44:55
 * 
 */
public class clsExecutorMove extends clsActionExecutor{

	public String getName() {
		return "Move executor";
	}
	
	public boolean execute(itfActionCommand poCommand, clsEntity poEntity) {
		clsActionMove oCommand =(clsActionMove) poCommand; 
    	switch(oCommand.getDirection() )
    	{
    	case MOVE_FORWARD:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.moveForward(10*oCommand.getSpeed());
    	case MOVE_BACKWARD:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.backup();
    	}
    	return true;
	}	
}
