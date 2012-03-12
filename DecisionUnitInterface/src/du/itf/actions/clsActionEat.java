package du.itf.actions;

/**
 * Eat command
 * No parameters, so anything eatable will be eaten... 
 * 
 * @author Benny Doenz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionEat extends clsActionCommand {

	@Override
	public String getLog() {
		return "<Eat></Eat>"; 
	}
	
}
