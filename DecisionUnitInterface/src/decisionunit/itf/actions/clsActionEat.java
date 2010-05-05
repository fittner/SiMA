package decisionunit.itf.actions;

/**
 * Eat command
 * No parameters, so anything eatable will be eaten... 
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionEat extends clsActionCommand {

	@Override
	public String getLog() {
		return "<Eat></Eat>"; 
	}
	
}
