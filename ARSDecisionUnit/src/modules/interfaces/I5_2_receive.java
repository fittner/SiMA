/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;


/**
 * Remembered drive content with associated primal repressed content are forwarded F49 to F54
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_2_receive {
	public void receive_I5_2(ArrayList<clsDriveMesh> poData);
}