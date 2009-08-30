/**
 * clsActionFacialExLensSize.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:57:29
 */
package decisionunit.itf.actions;

import enums.eActionFacialExLensSize;

/**
 * Facial expression command: Lens size
 * Parameters
 * 	peSize = new lens size to set
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExLensSize implements itfActionCommand {

	private eActionFacialExLensSize meSize;

	public clsActionFacialExLensSize(eActionFacialExLensSize peSize) {
		meSize=peSize;
	}
	
	public String getLog() {
		return "<FacialExLensSize>" + meSize.toString() + "</FacialExLensSize>"; 
	}

	public eActionFacialExLensSize getSize() {
		return meSize;
	}
	public void setSize(eActionFacialExLensSize peSize) {
		meSize=peSize;
	}
}