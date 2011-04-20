/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsDriveDemand;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.tools.clsPair;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 * 
 */
@Deprecated
public interface I1_3_receive extends I_BaseInterface {
	public void receive_I1_3(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate);
}
