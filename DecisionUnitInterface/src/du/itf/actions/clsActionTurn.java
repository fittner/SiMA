package du.itf.actions;

import du.enums.eActionTurnDirection;

/**
 * Turn command
 * Parameter Angle = angle in degrees to turn (default=2)
 * Parameter Direction = direction in which to turn 
 * 
 * @author Benny Doenz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionTurn extends clsActionCommand {

	private eActionTurnDirection meDirection;
	private double mnAngle; //in degrees, will automatically be transformed to rad 

	//Default Angle per Cycle is 2 degrees
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
	
	@Override
	public String getLog() {
		return "<Turn>" + meDirection.toString() + "@" + mnAngle + "</Turn>"; 
	}

}
