package complexbody.io.actuators.actionCommands;

/**
 * Move command
 * Parameter Speed = speed of movement (default is 4)
 * Parameter Direction = direction in which to move
 * 
 * @author Benny Doenz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionUnrealMoveTo extends clsActionCommand {
	
	private String moUnrealAction;
	private String moEntityPosition;
	
	public clsActionUnrealMoveTo(String poUnrealAction, String poEntityPosition) {
		moUnrealAction = poUnrealAction;
		moEntityPosition = poEntityPosition;
		
	}
	
	@Override
	public String getLog() {
		return "<" + moUnrealAction + ">" + moUnrealAction + "@" + moEntityPosition + "</" + moUnrealAction + ">"; 
	}
	

}
