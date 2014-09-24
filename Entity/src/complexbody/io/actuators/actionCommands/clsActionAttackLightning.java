/**
 * clsActionAttack.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:24:26
 */
package complexbody.io.actuators.actionCommands;

/**
 * Attack/Lightning command
 * Parameters
 * 	prForce = Strength of the attack   (Default ~4)
 *  poOpponent = ID of the Opponent as provided by the vision sensor
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionAttackLightning extends clsActionCommand {

	private double mrForce;
	private String moOpponentID;

	public clsActionAttackLightning(double prForce, String poOpponentID ) {
		mrForce=prForce;
		moOpponentID=poOpponentID;
	}
	
	@Override
	public String getLog() {
		return "<AttackLightning>" + moOpponentID + '@' + mrForce + "</AttackLightning>"; 
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
