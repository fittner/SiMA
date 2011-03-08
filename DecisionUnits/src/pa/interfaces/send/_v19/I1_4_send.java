/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
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
 * 18.05.2010, 13:59:39
 * 
 */
public interface I1_4_send {
	public void send_I1_4(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate);
}
