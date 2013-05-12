package du.itf.actions;

import du.enums.eActionTurnDirection;

/**
 * Turn Vision command for focused vision
 * Parameter Angle = angle in degrees to turn (default=2)
 * Parameter Direction = direction in which to turn 
 * 
 * @author Muchitsch
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionTurnVision extends clsActionCommand {

	private eActionTurnDirection meDirection;
	private double mnAngle; //in degrees, will automatically be transformed to rad 
	final private double mnMaxAngle = 90;

	public clsActionTurnVision(eActionTurnDirection peDirection, double pnAngle) {
		meDirection=peDirection;
		setAngle(pnAngle);
	}
	
	public double getAngle() {
		return mnAngle;
	}
	
	public void setAngle(double pnAngle) {
		// you cant go over max angle
		if(pnAngle > mnMaxAngle)
			pnAngle = mnMaxAngle;
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
		return "<TurnVision>" + meDirection.toString() + "@" + mnAngle + "</TurnVision>"; 
	}

}
