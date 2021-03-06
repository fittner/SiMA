/**
 * clsActionBodyColor.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:24:57
 */
package complexbody.io.actuators.actionCommands;

/**
 * Set Body color: Red+Green+Blue
 * Parameters
 * 	prRed = Red component of the ARSIN's color (0=default, +/- for variation)
 *  prGreen = Green component of the ARSIN color (0=default, +/- for variation)
 *  prBlue = Blue component of the ARSIN color (0=default, +/- for variation)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColor extends clsActionCommand {

	private int mrRed;
	private int mrGreen;
	private int mrBlue;

	public clsActionBodyColor(int prRed,int prGreen,int prBlue) {
		mrRed=prRed;
		mrGreen=prGreen;
		mrBlue=prBlue;
	}
	
	@Override
	public String getLog() {
		return "<BodyColor>" + mrRed + '/' +  mrGreen + '/' +  mrBlue + "</BodyColor>"; 
	}

	public int getRed() {
		return mrRed;
	}
	public void setRed(int prRed) {
		mrRed=prRed;
	}

	public int getGreen() {
		return mrGreen;
	}
	public void setGreen(int prGreen) {
		mrGreen=prGreen;
	}
	
	public int getBlue() {
		return mrBlue;
	}
	public void setBlue(int prBlue) {
		mrBlue=prBlue;
	}
}
