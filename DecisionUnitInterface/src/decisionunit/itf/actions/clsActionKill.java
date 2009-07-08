package decisionunit.itf.actions;

/**
 * Kill command
 * Parameters
 * 	prForce = The force used to kill the entity   (Default ~4)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionKill implements itfActionCommand {

	private double mrForce;

	public clsActionKill(double prForce) {
		mrForce=prForce;
	}
	
	public String getLog() {
		return "<Kill>" + mrForce + "</Kill>"; 
	}

	public double getForce() {
		return mrForce;
	}
	public void setForce(double prForce) {
		mrForce=prForce;
	}
}
