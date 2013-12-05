package du.itf.actions;

/**
 * Attack/Bite command
 * Parameters
 * 	prForce = The force used to attack the entity   (Default ~4)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionAttackBite extends clsActionCommand {

	private double mrForce;

	public clsActionAttackBite(double prForce) {
		mrForce=prForce;
	}
	
	@Override
	public String getLog() {
		return "<AttackBite>" + mrForce + "</AttackBite>"; 
	}

	public double getForce() {
		return mrForce;
	}
	public void setForce(double prForce) {
		mrForce=prForce;
	}
}
