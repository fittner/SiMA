/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 */
package pa.interfaces.send._v19;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

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
