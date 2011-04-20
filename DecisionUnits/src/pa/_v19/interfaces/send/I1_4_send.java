/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 */
package pa._v19.interfaces.send;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsDriveDemand;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 * 
 */
@Deprecated
public interface I1_4_send {
	public void send_I1_4(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate);
}
