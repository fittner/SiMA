/**
 * I2_15_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:35:06
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:35:06
 * 
 */
public interface I2_15_receive extends I_BaseInterface {
	public void receive_I2_15(ArrayList<clsDriveMesh> poDriveCandidate);
}

