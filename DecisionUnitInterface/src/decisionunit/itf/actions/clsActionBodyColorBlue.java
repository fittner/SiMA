/**
 * clsBodyColorBlue.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 14:24:15
 */
package decisionunit.itf.actions;

/**
 * Set Body color: Blue
 * Parameters
 * 	prBlue = Blue component of the bubble's color (0=default, +/- for variation)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorBlue implements itfActionCommand {

	private double mrBlue;

	public clsActionBodyColorBlue(double prBlue) {
		mrBlue=prBlue;
	}
	
	public String getLog() {
		return "<BodyColorBlue>" + mrBlue + "</BodyColorBlue>"; 
	}

	public double getBlue() {
		return mrBlue;
	}
	public void setBlue(double prBlue) {
		mrBlue=prBlue;
	}
}
