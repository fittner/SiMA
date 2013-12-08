/**
 * I7_7_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:18:45
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.PsychicSpreadingActivationMode;

/**
 * Word presentations originating in F27 are reduced to thing presentations in F47. These are forwarded together with their attached quota of affects to F46.
 * 
 * @author deutsch
 * 03.03.2011, 16:18:45
 * 
 */
public interface I5_19_send {
	public void send_I5_19(ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode psychicSpreadingActivationMode);
}
