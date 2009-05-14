package decisionunit.itf.actions;

import enums.eActionMoveDirection;

public class clsActionMove implements itfActionCommand {
	private eActionMoveDirection meDirection;
	private double mnSpeed;

	public clsActionMove() {
		meDirection=eActionMoveDirection.MOVE_FORWARD;
		mnSpeed=4;
	}
	public clsActionMove(eActionMoveDirection peDirection, double pnSpeed) {
		meDirection=peDirection;
		mnSpeed=pnSpeed;
	}
	
	public double getSpeed() {
		return mnSpeed;
	}
	
	public void setSpeed(double pnSpeed) {
		mnSpeed=pnSpeed;
	}
	
	public eActionMoveDirection getDirection() {
		return meDirection;
	}
	
	public void setSpeed(eActionMoveDirection peDirection) {
		meDirection=peDirection;
	}
}
