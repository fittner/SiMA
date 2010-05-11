package du.itf.actions;

import du.enums.eActionMoveDirection;

/**
 * Move command
 * Parameter Speed = speed of movement (default is 4)
 * Parameter Direction = direction in which to move
 * 
 * @author Benny Dönz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionMove extends clsActionCommand {
	private eActionMoveDirection meDirection;
	private double mrSpeed;

	//Move forward at average speed
	public clsActionMove() {
		meDirection=eActionMoveDirection.MOVE_FORWARD;
		mrSpeed=4;
	}
	
	public clsActionMove(eActionMoveDirection peDirection, double prSpeed) {
		meDirection=peDirection;
		mrSpeed=prSpeed;
	}
	
	public double getSpeed() {
		return mrSpeed;
	}
	
	public void setSpeed(double prSpeed) {
		mrSpeed=prSpeed;
	}
	
	public eActionMoveDirection getDirection() {
		return meDirection;
	}
	
	public void setDirection(eActionMoveDirection peDirection) {
		meDirection=peDirection;
	}

	@Override
	public String getLog() {
		return "<Move>" + meDirection.toString() + "@" + mrSpeed  + "</Move>"; 
	}
	

}
