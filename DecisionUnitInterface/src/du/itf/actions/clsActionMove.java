package du.itf.actions;

import du.enums.eActionMoveDirection;

/**
 * Move command
 * Parameter Speed = speed of movement (default is 4)
 * Parameter Direction = direction in which to move
 * 
 * @author Benny Doenz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionMove extends clsActionCommand {
	private eActionMoveDirection meDirection;
	private double mrSpeed;
	private int mrStamina;

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

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 14.12.2012 22:13:34
	 *
	 * @return
	 */
	public int getStaminaDemand() {
		return mrStamina;
	}
	

}
