package complexbody.io.actuators.actionCommands;

/**
 * Pick up command
 * No parameters, so anything carryable will be picked up 
 * 
 * @author Benny Doenz
 * 03.07.2009, 22:39:05
 * 
 */

public class clsActionPickUp extends clsActionCommand {

	@Override
	public String getLog() {
		return "<PickUp></PickUp>"; 
	}
	
}
