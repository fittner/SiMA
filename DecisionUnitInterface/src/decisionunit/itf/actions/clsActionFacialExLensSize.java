/**
 * clsActionFacialExLensSize.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:57:29
 */
package decisionunit.itf.actions;

import enums.eLensSize;

/**
 * Facial expression command: Lens size
 * Parameters
 * 	peSize = new lens size to set
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExLensSize extends clsActionCommand {

	private eLensSize meSize;

	public clsActionFacialExLensSize(eLensSize peSize) {
		meSize=peSize;
	}
	
	@Override
	public String getLog() {
		return "<FacialExLensSize>" + meSize.toString() + "</FacialExLensSize>"; 
	}

	public eLensSize getSize() {
		return meSize;
	}
	public void setSize(eLensSize peSize) {
		meSize=peSize;
	}
}