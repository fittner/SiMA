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

	private float mrForce;

	public clsActionKill(float prForce) {
		mrForce=prForce;
	}
	
	public String getLog() {
		return "<Kill>" + mrForce + "</Kill>"; 
	}

	public float getForce() {
		return mrForce;
	}
	public void setForce(float prForce) {
		mrForce=prForce;
	}
}
