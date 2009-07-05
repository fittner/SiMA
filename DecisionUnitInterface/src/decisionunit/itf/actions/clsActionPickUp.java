package decisionunit.itf.actions;

/**
 * Pick up command
 * No parameters, so anything carryable will be picked up 
 * 
 * @author Benny Dönz
 * 03.07.2009, 22:39:05
 * 
 */

public class clsActionPickUp implements itfActionCommand {

	public String getLog() {
		return "<PickUp></PickUp>"; 
	}
	
}
