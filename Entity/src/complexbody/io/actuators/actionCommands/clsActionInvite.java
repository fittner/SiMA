/**
 * clsActionSleep.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:35:24
 */
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;


public class clsActionInvite extends clsInternalActionCommand {

	private eInternalActionIntensity meIntensity;

	public clsActionInvite(eInternalActionIntensity peIntensity) {
		meIntensity=peIntensity;
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description 
	 *
	 * @since 27.07.2013 12:22:30
	 *
	 */
	public clsActionInvite() {
		// TODO (hinterleitner) - Auto-generated constructor stub
	}

	public eInternalActionIntensity getIntensity() {
		return meIntensity;
	}
	public void setIntensity(eInternalActionIntensity peIntensity) {
		meIntensity=peIntensity;
	}
}

