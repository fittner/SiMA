/**
 * clsActionBodyColor.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 14:24:57
 */
package decisionunit.itf.actions;

/**
 * Set Body color: Red+Green+Blue
 * Parameters
 * 	prRed = Red component of the bubble's color (0=default, +/- for variation)
 *  prGreen = Green component of the bubble's color (0=default, +/- for variation)
 *  prBlue = Blue component of the bubble's color (0=default, +/- for variation)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColor implements itfActionCommand {

	private double mrRed;
	private double mrGreen;
	private double mrBlue;

	public clsActionBodyColor(double prRed,double prGreen,double prBlue) {
		mrRed=prRed;
		mrGreen=prGreen;
		mrBlue=prBlue;
	}
	
	public String getLog() {
		return "<BodyColor>" + mrRed + '/' +  mrGreen + '/' +  mrBlue + "</BodyColor>"; 
	}

	public double getRed() {
		return mrRed;
	}
	public void setRed(double prRed) {
		mrRed=prRed;
	}

	public double getGreen() {
		return mrGreen;
	}
	public void setGreen(double prGreen) {
		mrGreen=prGreen;
	}
	
	public double getBlue() {
		return mrBlue;
	}
	public void setBlue(double prBlue) {
		mrBlue=prBlue;
	}
}
