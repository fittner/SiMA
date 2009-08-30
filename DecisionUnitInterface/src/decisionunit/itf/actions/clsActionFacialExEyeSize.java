/**
 * clsActionFacialExEyeSize.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny D�nz
 * 28.08.2009, 13:57:40
 */
package decisionunit.itf.actions;

import enums.eActionFacialExEyeSize;

/**
 * Facial expression command: Eye size
 * Parameters
 * 	peSize = new eye size to set
 * 
 * @author Benny D�nz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExEyeSize implements itfActionCommand {

	private eActionFacialExEyeSize meSize;

	public clsActionFacialExEyeSize(eActionFacialExEyeSize peSize) {
		meSize=peSize;
	}
	
	public String getLog() {
		return "<FacialExEyeSize>" + meSize.toString() + "</FacialExEyeSize>"; 
	}

	public eActionFacialExEyeSize getSize() {
		return meSize;
	}
	public void setSize(eActionFacialExEyeSize peSize) {
		meSize=peSize;
	}
}

