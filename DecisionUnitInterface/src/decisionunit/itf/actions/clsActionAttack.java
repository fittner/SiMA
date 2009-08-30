/**
 * clsActionAttack.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 14:24:26
 */
package decisionunit.itf.actions;

/**
 * Attack command
 * Parameters
 * 	prForce = Strength of the attack   (Default ~4)
 *  poOpponent = ID of the Opponent as provided by the vision sensor
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionAttack implements itfActionCommand {

	private double mrForce;
	private String moOpponentID;

	public clsActionAttack(double prForce, String poOpponentID ) {
		mrForce=prForce;
		moOpponentID=poOpponentID;
	}
	
	public String getLog() {
		return "<Attack>" + moOpponentID + '@' + mrForce + "</Attack>"; 
	}

	public double getForce() {
		return mrForce;
	}
	public void setForce(double prForce) {
		mrForce=prForce;
	}
	
	public String getOpponentID() {
		return moOpponentID;
	}
	public void setOpponentID(String poOpponentID) {
		moOpponentID=poOpponentID;
	}
}
