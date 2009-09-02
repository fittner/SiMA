/**
 * clsActionSleep.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny D�nz
 * 28.08.2009, 13:35:24
 */
package decisionunit.itf.actions;

import enums.eActionSleepIntensity;

/**
 * Sleep command
 * Parameters
 * 	peIntensity = Intensity of the sleep (light=switch off first phalanx of consumers, deep=switch off all non-life-supporting consumers)
 * 
 * @author Benny D�nz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionSleep implements itfActionCommand {

	private eActionSleepIntensity meIntensity;

	public clsActionSleep(eActionSleepIntensity peIntensity) {
		meIntensity=peIntensity;
	}
	
	public String getLog() {
		return "<Kiss>" + meIntensity.toString() + "</Kiss>"; 
	}

	public eActionSleepIntensity getIntensity() {
		return meIntensity;
	}
	public void setIntensity(eActionSleepIntensity peIntensity) {
		meIntensity=peIntensity;
	}
}