package decisionunit.itf.actions;

import enums.eTurnActionDirection;

public class clsActionTurn implements itfActionCommand {

	private eTurnActionDirection meDirection;
	private double mnAngle; //in degrees, will automatically be transformed to rad 

	public clsActionTurn(eTurnActionDirection peDirection) {
		meDirection=peDirection;
		mnAngle=2;
	}
	public clsActionTurn(eTurnActionDirection peDirection, double pnAngle) {
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
	

}
