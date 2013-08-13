/**
 * clsActionFacialExEyeSize.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:57:40
 */
package du.itf.actions;

import du.enums.eInternalActionIntensity;

/**
 * Facial expression command: Eye size
 * Parameters
 * 	peSize = new eye size to set
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionSpeechExEyeSize extends clsActionCommand {

	private eInternalActionIntensity meSize;

	public clsActionSpeechExEyeSize(eInternalActionIntensity heavy) {
		meSize=heavy;
	}
	
	@Override
	public String getLog() {
		return "<FacialExEyeSize>" + meSize.toString() + "</FacialExEyeSize>"; 
	}


}

