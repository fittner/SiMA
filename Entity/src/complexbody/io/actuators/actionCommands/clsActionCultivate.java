/**
 * clsActionCultivate.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:34:34
 */
package complexbody.io.actuators.actionCommands;

/**
 * Cultivate command
 * Parameters
 * 	prAmount = amount of cultivation (Default ~1) 
 *             0 = no cultivation (unnecessary)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionCultivate extends clsActionCommand {

	private double mrAmount;

	public clsActionCultivate(double prAmount) {
		mrAmount=prAmount;
	}
	
	@Override
	public String getLog() {
		return "<Cultivate>" + mrAmount + "</Cultivate>"; 
	}

	public double getAmount() {
		return mrAmount;
	}
	public void setAmount(double prAmount) {
		mrAmount=prAmount;
	}
}
