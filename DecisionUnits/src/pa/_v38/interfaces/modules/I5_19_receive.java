/**
 * I7_7_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:18:25
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.PsychicSpreadingActivationMode;

/**
 * Word presentations originating in F27 are reduced to thing presentations in F47. These are forwarded together with their attached quota of affects to F46.
 * 
 * @author deutsch
 * 03.03.2011, 16:18:25
 * 
 */
public interface I5_19_receive {
	public void receive_I5_19(ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode mode);
}
