/**
 * clsBodyColorGreen.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 14:24:08
 */
package decisionunit.itf.actions;

/**
 * Set Body color: Green
 * Parameters
 * 	prGreen = Green component of the bubble's color (0=default, +/- for variation)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorGreen implements itfActionCommand {

	private double mrGreen;

	public clsActionBodyColorGreen(double prGreen) {
		mrGreen=prGreen;
	}
	
	public String getLog() {
		return "<BodyColorGreen>" + mrGreen + "</BodyColorGreen>"; 
	}

	public double getGreen() {
		return mrGreen;
	}
	public void setGreen(double prGreen) {
		mrGreen=prGreen;
	}
}
