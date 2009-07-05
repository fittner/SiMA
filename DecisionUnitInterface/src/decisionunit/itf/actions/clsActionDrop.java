package decisionunit.itf.actions;

/**
 * Drop command
 * No parameters, so whatever is beeing carried will be dropped 
 * 
 * @author Benny Dönz
 * 03.07.2009, 22:39:05
 * 
 */

public class clsActionDrop  implements itfActionCommand {

	public String getLog() {
		return "<Drop></Drop>"; 
	}
	
}
