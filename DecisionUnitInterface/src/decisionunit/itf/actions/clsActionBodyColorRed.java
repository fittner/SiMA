/**
 * clsBodyColorRed.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 14:24:00
 */
package decisionunit.itf.actions;

/**
 * Set Body color: Red
 * Parameters
 * 	prRed = Red component of the bubble's color (0=default, +/- for variation)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorRed implements itfActionCommand {

	private double mrRed;

	public clsActionBodyColorRed(double prRed) {
		mrRed=prRed;
	}
	
	public String getLog() {
		return "<BodyColorRed>" + mrRed + "</BodyColorRed>"; 
	}

	public double getRed() {
		return mrRed;
	}
	public void setRed(double prRed) {
		mrRed=prRed;
	}
}
