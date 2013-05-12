/**
 * clsBodyColorBlue.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:24:15
 */
package du.itf.actions;

/**
 * Set Body color: Blue
 * Parameters
 * 	prBlue = Blue component of the ARSIN's color (0=default, +/- for variation)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionBodyColorBlue extends clsActionCommand {

	private int mrBlue;

	public clsActionBodyColorBlue(int prBlue) {
		mrBlue=prBlue;
	}
	
	@Override
	public String getLog() {
		return "<BodyColorBlue>" + mrBlue + "</BodyColorBlue>"; 
	}

	public int getBlue() {
		return mrBlue;
	}
	public void setBlue(int prBlue) {
		mrBlue=prBlue;
	}
}
