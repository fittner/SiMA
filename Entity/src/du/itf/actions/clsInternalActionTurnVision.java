/**
 * CHANGELOG
 *
 * 13.08.2013 muchitsch - File created
 *
 */
package du.itf.actions;

import du.enums.eActionTurnDirection;



public class clsInternalActionTurnVision extends clsInternalActionCommand {

	private eActionTurnDirection meVisionDirection;
	private double mnAngle;

	public clsInternalActionTurnVision(eActionTurnDirection peVisionDirection, double pnAngle) {
		meVisionDirection=peVisionDirection;
		mnAngle = pnAngle;
	}

	public eActionTurnDirection getVisionDirection() {
		return meVisionDirection;
	}
	public void setVisionDirection(eActionTurnDirection peVisionDirection) {
		meVisionDirection=peVisionDirection;
	}
	
	public double getAngle() {
		return mnAngle;
	}

	public void setAngle(double pnAngle) {
		this.mnAngle = pnAngle;
	}
}
