/**
 * clsActionFacialExLeftAntennaPosition.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:59:41
 */
package decisionunit.itf.actions;

import enums.eAntennaPositions;

/**
 * Facial expression command: Left antenna position
 * Parameters
 * 	pePosition = new position for the left antenna
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExLeftAntennaPosition extends clsActionCommand {

	private eAntennaPositions mePosition;

	public clsActionFacialExLeftAntennaPosition(eAntennaPositions pePosition) {
		mePosition=pePosition;
	}
	
	@Override
	public String getLog() {
		return "<FacialExLeftAntennaPosition>" + mePosition.toString() + "</FacialExLeftAntennaPosition>"; 
	}

	public eAntennaPositions getPosition() {
		return mePosition;
	}
	public void setPosition(eAntennaPositions pePosition) {
		mePosition=pePosition;
	}
}