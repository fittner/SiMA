/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
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
 * 18.05.2010, 13:40:52
 * 
 */
@Deprecated
public interface I1_3_send {
	public void send_I1_3(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate);
}
