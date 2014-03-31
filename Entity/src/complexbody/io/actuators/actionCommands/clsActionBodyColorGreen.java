/**
 * clsBodyColorGreen.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:24:08
 */
package complexbody.io.actuators.actionCommands;

/**
 * Set Body color: Green
 * Parameters
 * 	prGreen = Green component of the ARSIN's color (0=default, +/- for variation)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorGreen extends clsActionCommand {

	private int mrGreen;

	public clsActionBodyColorGreen(int prGreen) {
		mrGreen=prGreen;
	}
	
	@Override
	public String getLog() {
		return "<BodyColorGreen>" + mrGreen + "</BodyColorGreen>"; 
	}

	public int getGreen() {
		return mrGreen;
	}
	public void setGreen(int prGreen) {
		mrGreen=prGreen;
	}
}
