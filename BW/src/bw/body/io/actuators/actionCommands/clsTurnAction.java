/**
 * @author Benny Dönz
 * 15.04.2009, 16:31:21
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionCommands;

import sim.physics2D.util.Angle;
import bw.utils.enums.*;
import bw.body.io.actuators.clsActionCommand;
import bw.entities.clsEntity;
import bw.entities.clsMobile;

/**
 * TODO Temporary turn command derived from clsActuatorMove 
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:31:21
 * 
 */
public class clsTurnAction extends clsActionCommand {
	private eTurnActionDirection meDirection;
	private double mnAngle; //in degrees, will automatically be transformed to rad 

	public clsTurnAction(eTurnActionDirection peDirection) {
		meDirection=peDirection;
		mnAngle=2;
	}
	public clsTurnAction(eTurnActionDirection peDirection, double pnAngle) {
		meDirection=peDirection;
		mnAngle=pnAngle;
	}
	
	public double getAngle() {
		return mnAngle;
	}
	
	public void setAngle(double pnAngle) {
		mnAngle=pnAngle;
	}
	
	public eTurnActionDirection getDirection() {
		return meDirection;
	}
	
	public void setSpeed(eTurnActionDirection peDirection) {
		meDirection=peDirection;
	}
	
	public boolean execute(clsEntity poEntity) {
    	switch( meDirection )
    	{
    	case TURN_LEFT:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(mnAngle/360*Math.PI*(-1)));
    	case TURN_RIGHT:
    		((clsMobile)poEntity).getMobileObject2D().moMotionPlatform.faceTowardsRelative(new Angle(mnAngle/360*Math.PI/200));
    	}
    	return true;
	}	

}
