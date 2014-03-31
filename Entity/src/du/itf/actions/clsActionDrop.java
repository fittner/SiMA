package du.itf.actions;

/**
 * Drop command
 * No parameters, so whatever is beeing carried will be dropped 
 * 
 * @author Benny Doenz
 * 03.07.2009, 22:39:05
 * 
 */

public class clsActionDrop  extends clsActionCommand {

	@Override
	public String getLog() {
		return "<Drop></Drop>"; 
	}
	
}
