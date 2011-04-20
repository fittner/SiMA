/**
 * I2_18_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:36:22
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;
import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:36:22
 * 
 */
public interface I2_18_send extends I_BaseInterface {
	public void send_I2_18(ArrayList<clsDriveMesh> poDrives);
}

