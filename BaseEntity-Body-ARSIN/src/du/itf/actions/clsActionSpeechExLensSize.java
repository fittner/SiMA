/**
 * clsActionFacialExLensSize.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:57:29
 */
package du.itf.actions;

import du.enums.eLensSize;

/**
 * Facial expression command: Lens size
 * Parameters
 * 	peSize = new lens size to set
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionSpeechExLensSize extends clsActionCommand {

	private eLensSize meSize;

	public clsActionSpeechExLensSize(eLensSize peSize) {
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