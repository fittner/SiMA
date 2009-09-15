/**
 * clsBodyColorRed.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny D�nz
 * 28.08.2009, 14:24:00
 */
package decisionunit.itf.actions;

/**
 * Set Body color: Red
 * Parameters
 * 	prRed = Red component of the bubble's color (0=default, +/- for variation)
 * 
 * @author Benny D�nz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorRed implements itfActionCommand {

	private int mrRed;

	public clsActionBodyColorRed(int prRed) {
		mrRed=prRed;
	}
	
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
