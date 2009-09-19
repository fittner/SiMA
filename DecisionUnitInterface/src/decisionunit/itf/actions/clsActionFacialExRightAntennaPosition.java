/**
 * clsActionFacialExRightAntennaPosition.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:59:53
 */
package decisionunit.itf.actions;

import enums.eAntennaPositions;

/**
 * Facial expression command: Right antenna position
 * Parameters
 * 	pePosition = new position for the right antenna
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExRightAntennaPosition extends clsActionCommand {

	private eAntennaPositions mePosition;

	public clsActionFacialExRightAntennaPosition(eAntennaPositions pePosition) {
		mePosition=pePosition;
	}
	
	@Override
	public String getLog() {
		return "<FacialExRightAntennaPosition>" + mePosition.toString() + "</FacialExRightAntennaPosition>"; 
	}

	public eAntennaPositions getPosition() {
		return mePosition;
	}
	public void setPosition(eAntennaPositions pePosition) {
		mePosition=pePosition;
	}
}