/**
 * clsActionSleep.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:35:24
 */
package du.itf.actions;

import du.enums.eInternalActionIntensity;


public class clsInternalActionSweat extends clsInternalActionCommand {

	private eInternalActionIntensity meIntensity;

	public clsInternalActionSweat(eInternalActionIntensity peIntensity) {
		meIntensity=peIntensity;
	}

	public eInternalActionIntensity getIntensity() {
		return meIntensity;
	}
	public void setIntensity(eInternalActionIntensity peIntensity) {
		meIntensity=peIntensity;
	}
}
