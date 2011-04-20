/**
 * I2_15_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:36:06
 */
package pa._v30.interfaces.send;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:36:06
 * 
 */
public interface I2_15_send extends I_BaseInterface {
	public void send_I2_15(ArrayList<clsDriveMesh> poDriveList);
}
