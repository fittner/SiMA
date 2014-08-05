/**
 * clsActionFacialExLeftAntennaPosition.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:59:41
 */
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eAntennaPositions;

/**
 * Facial expression command: Left antenna position
 * Parameters
 * 	pePosition = new position for the left antenna
 * 
 * @author Benny Doenz
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