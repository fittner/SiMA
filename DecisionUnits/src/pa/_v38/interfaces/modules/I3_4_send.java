/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;

/**
 * Pair of opposites in form of thing presentations and the tensions at the various drive source are transmitted from F4 to F48.
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 * 
 */
public interface I3_4_send {
	public void send_I3_4(ArrayList<clsPair<clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand>>> poDriveCandidates);
}
