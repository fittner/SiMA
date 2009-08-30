/**
 * clsActionKiss.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:35:06
 */
package decisionunit.itf.actions;

import enums.eActionKissIntensity;

/**
 * Kiss command
 * Parameters
 * 	peIntensity = Intensity of the kiss (low, middle, strong)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionKiss implements itfActionCommand {

	private eActionKissIntensity meIntensity;

	public clsActionKiss(eActionKissIntensity peIntensity) {
		meIntensity=peIntensity;
	}
	
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
