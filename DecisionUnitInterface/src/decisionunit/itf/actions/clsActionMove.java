package decisionunit.itf.actions;

import enums.eMoveActionDirection;

public class clsActionMove implements itfActionCommand {
	private eMoveActionDirection meDirection;
	private double mnSpeed;

	public clsActionMove() {
		meDirection=eMoveActionDirection.MOVE_FORWARD;
		mnSpeed=4;
	}
	public clsActionMove(eMoveActionDirection peDirection, double pnSpeed) {
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
}
