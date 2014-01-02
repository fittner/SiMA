package du.itf.actions;

import du.enums.eActionSpeechDirection;

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

	public clsActionInnerSpeech(eActionSpeechDirection innerSpeech) {
		Context=getDirection();
	}
	
	/**
	 * DOCUMENT (hinterleitner) - insert description 
	 *
	 * @since 21.07.2013 12:52:34
	 *
	 * @param innerSpeech
	 */
	


	@Override
	public String getLog() {
		return "<Speak>" + Context + "</Speak>"; 
	}

	public double getDirection() {
		return Context;
	}
	public void setDirection(double prDirection) {
		Context=prDirection;
	}
}
