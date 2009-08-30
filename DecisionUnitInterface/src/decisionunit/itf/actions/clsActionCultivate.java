/**
 * clsActionCultivate.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:34:34
 */
package decisionunit.itf.actions;

/**
 * Cultivate command
 * Parameters
 * 	prAmount = amount of cultivation (Default ~1) 
 *             0 = no cultivation (unnecessary)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionCultivate implements itfActionCommand {

	private double mrAmount;

	public clsActionCultivate(double prAmount) {
		mrAmount=prAmount;
	}
	
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
