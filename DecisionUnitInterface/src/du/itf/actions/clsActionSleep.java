/**
 * clsActionSleep.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:35:24
 */
package du.itf.actions;

import du.enums.eActionSleepIntensity;

/**
 * Sleep command
 * Parameters
 * 	peIntensity = Intensity of the sleep (light=switch off first phalanx of consumers, deep=switch off all non-life-supporting consumers)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionSleep extends clsActionCommand {

	private eActionSleepIntensity meIntensity;

	public clsActionSleep(eActionSleepIntensity peIntensity) {
		meIntensity=peIntensity;
	}
	
	@Override
	public String getLog() {
		return "<Sleep>" + meIntensity.toString() + "</Sleep>"; 
	}

	public eActionSleepIntensity getIntensity() {
		return meIntensity;
	}
	public void setIntensity(eActionSleepIntensity peIntensity) {
		meIntensity=peIntensity;
	}
}
