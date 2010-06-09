package du.itf.actions;

/**
 * Pick up command
 * No parameters, so anything carryable will be picked up 
 * 
 * @author Benny D�nz
 * 03.07.2009, 22:39:05
 * 
 */

public class clsActionPickUp extends clsActionCommand {

	@Override
	public String getLog() {
		return "<PickUp></PickUp>"; 
	}
	
}