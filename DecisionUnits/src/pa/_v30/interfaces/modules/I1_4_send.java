/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.tools.clsPair;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 *
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 * 
 */
public interface I1_4_send {
	public void send_I1_4(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate);
}
