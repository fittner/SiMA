package complexbody.io.actuators.actionCommands;

/**
 *  Beat
 *  Parameters
 * 	prForce = The force used to attack the entity   (Default ~4)
 * 
 * @author herret
 * 17.07.2013, 15:31:13
 * 
 */
public class clsActionBeat extends clsActionCommand {

	private double mrForce;

	public clsActionBeat(double prForce) {
		mrForce=prForce;
	}
	
	@Override
	public String getLog() {
		return "<Beat>" + mrForce + "</Beat>"; 
	}

	public double getForce() {
		return mrForce;
	}
	public void setForce(double prForce) {
		mrForce=prForce;
	}
}
