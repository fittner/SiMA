/**
 * I7_7_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:18:45
 */
package modules.interfaces;

import java.util.ArrayList;
import java.util.List;

import memorymgmt.enums.PsychicSpreadingActivationMode;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * Word presentations originating in F27 are reduced to thing presentations in F47. These are forwarded together with their attached quota of affects to F46.
 * 
 * @author deutsch
 * 03.03.2011, 16:18:45
 * 
 */
public interface I5_19_send {
	public void send_I5_19(ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode psychicSpreadingActivationMode,
            clsWordPresentationMesh moWordingToContext2, List<clsEmotion> poCurrentEmotions);
}
