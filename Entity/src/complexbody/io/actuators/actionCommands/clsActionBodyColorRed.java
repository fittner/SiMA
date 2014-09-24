/**
 * clsBodyColorRed.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:24:00
 */
package complexbody.io.actuators.actionCommands;

/**
 * Set Body color: Red
 * Parameters
 * 	prRed = Red component of the ARSIN's color (0=default, +/- for variation)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorRed extends clsActionCommand {

	private int mrRed;

	public clsActionBodyColorRed(int prRed) {
		mrRed=prRed;
	}
	
	@Override
	public String getLog() {
		return "<BodyColorRed>" + mrRed + "</BodyColorRed>"; 
	}

	public int getRed() {
		return mrRed;
	}
	public void setRed(int prRed) {
		mrRed=prRed;
	}
}
