package du.itf.actions;

/**
 * Attack/Bite command
 * Parameters
 * 	prForce = The force used to attack the entity   (Default ~4)
 * 
 * @author IH
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionInnerSpeech extends clsActionCommand {

	private double Context;

	public clsActionInnerSpeech(double prForce) {
		Context=prForce;
	}
	
	@Override
	public String getLog() {
		return "<Speak>" + Context + "</Speak>"; 
	}

	public double getForce() {
		return Context;
	}
	public void setForce(double prForce) {
		Context=prForce;
	}
}
