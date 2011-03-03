/**
 * I2_17_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:36:55
 */
package pa.interfaces.receive._v30;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:36:55
 * 
 */
public interface I2_17_receive extends I_BaseInterface {
	public void receive_I2_17(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate);
}
