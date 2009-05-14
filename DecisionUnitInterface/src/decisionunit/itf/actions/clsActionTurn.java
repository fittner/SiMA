package decisionunit.itf.actions;

import enums.eActionTurnDirection;

public class clsActionTurn implements itfActionCommand {

	private eActionTurnDirection meDirection;
	private double mnAngle; //in degrees, will automatically be transformed to rad 

	public clsActionTurn(eActionTurnDirection peDirection) {
		meDirection=peDirection;
		mnAngle=2;
	}
	public clsActionTurn(eActionTurnDirection peDirection, double pnAngle) {
		meDirection=peDirection;
		mnAngle=pnAngle;
	}
	
	public double getAngle() {
		return mnAngle;
	}
	
	public void setAngle(double pnAngle) {
		mnAngle=pnAngle;
	}
	
	public eActionTurnDirection getDirection() {
		return meDirection;
	}
	
	public void setSpeed(eActionTurnDirection peDirection) {
		meDirection=peDirection;
	}
	

}
