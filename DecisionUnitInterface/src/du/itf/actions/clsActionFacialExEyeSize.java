/**
 * clsActionFacialExEyeSize.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:57:40
 */
package du.itf.actions;

import du.enums.eEyeSize;

/**
 * Facial expression command: Eye size
 * Parameters
 * 	peSize = new eye size to set
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExEyeSize extends clsActionCommand {

	private eEyeSize meSize;

	public clsActionFacialExEyeSize(eEyeSize peSize) {
		meSize=peSize;
	}
	
	@Override
	public String getLog() {
		return "<FacialExEyeSize>" + meSize.toString() + "</FacialExEyeSize>"; 
	}

	public eEyeSize getSize() {
		return meSize;
	}
	public void setSize(eEyeSize peSize) {
		meSize=peSize;
	}
}

