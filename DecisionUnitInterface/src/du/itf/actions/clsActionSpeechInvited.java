/**
 * clsActionSleep.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:35:24
 */
package du.itf.actions;

import du.enums.eInternalActionIntensity;


public class clsActionSpeechInvited extends clsInternalActionCommand {

	private eInternalActionIntensity meIntensity;

	public clsActionSpeechInvited(eInternalActionIntensity peIntensity) {
		meIntensity=peIntensity;
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description 
	 *
	 * @since 27.07.2013 12:22:30
	 *
	 */
	public clsActionSpeechInvited() {
		// TODO (hinterleitner) - Auto-generated constructor stub
	}

	public eInternalActionIntensity getIntensity() {
		return meIntensity;
	}
	public void setIntensity(eInternalActionIntensity peIntensity) {
		meIntensity=peIntensity;
	}
}
