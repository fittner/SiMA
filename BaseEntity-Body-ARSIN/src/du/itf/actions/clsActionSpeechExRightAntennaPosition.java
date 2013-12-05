/**
 * clsActionFacialExRightAntennaPosition.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:59:53
 */
package du.itf.actions;

import du.enums.eAntennaPositions;

/**
 * Facial expression command: Right antenna position
 * Parameters
 * 	pePosition = new position for the right antenna
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionSpeechExRightAntennaPosition extends clsActionCommand {

	private eAntennaPositions mePosition;

	public clsActionSpeechExRightAntennaPosition(eAntennaPositions pePosition) {
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