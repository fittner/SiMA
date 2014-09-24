/**
 * clsActionKiss.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:35:06
 */
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eActionKissIntensity;

/**
 * Kiss command
 * Parameters
 * 	peIntensity = Intensity of the kiss (low, middle, strong)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionKiss extends clsActionCommand {

	private eActionKissIntensity meIntensity;

	public clsActionKiss(eActionKissIntensity peIntensity) {
		meIntensity=peIntensity;
	}
	
	@Override
	public String getLog() {
		return "<Kiss>" + meIntensity.toString() + "</Kiss>"; 
	}

	public eActionKissIntensity getIntensity() {
		return meIntensity;
	}
	public void setIntensity(eActionKissIntensity peIntensity) {
		meIntensity=peIntensity;
	}
}
