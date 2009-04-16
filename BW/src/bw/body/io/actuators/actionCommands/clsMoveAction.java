/**
 * @author Benny Dönz
 * 15.04.2009, 16:30:58
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionCommands;

import bw.utils.enums.*;
import bw.body.io.actuators.clsActionCommand;
import bw.entities.clsEntity;
import bw.entities.clsMobile;

/**
 * TODO Temporary movement command derived from clsActuatorMove 
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:30:58
 * 
 */
public class clsMoveAction extends clsActionCommand{
	private eMoveActionDirection meDirection;
	private double mnSpeed;

	public clsMoveAction() {
		meDirection=eMoveActionDirection.MOVE_FORWARD;
		mnSpeed=4;
	}
	public clsMoveAction(eMoveActionDirection peDirection, double pnSpeed) {
		meDirection=peDirection;
		mnSpeed=pnSpeed;
	}
	
	public double getSpeed() {
		return mnSpeed;
	}
	
	public void setSpeed(double pnSpeed) {
		mnSpeed=pnSpeed;
	}
	
	public eMoveActionDirection getDirection() {
		return meDirection;
	}
	
	public void setSpeed(eMoveActionDirection peDirection) {
		meDirection=peDirection;
	}
	
	public boolean execute(clsEntity poEntity) {
    	switch( meDirection )
    	{
    	case MOVE_FORWARD:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.moveForward(mnSpeed);
    	case MOVE_BACKWARD:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.backup();
    	}
    	return true;
	}	

}
