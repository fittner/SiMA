/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:59:39
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;

/**
 * Pair of opposites in form of thing presentations and the tensions at the various drive source are transmitted from F4 to F48.
 * Pair.A is agressive
 * Pair.B is lib Drive component
 * 
 * @author deutsch
 * 11.08.2009, 13:59:39
 * 
 */
public interface I3_4_receive {
	public void receive_I3_4(ArrayList <clsDriveMesh> poDriveComponents);
}
