/**
 * I2_19_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;


/**
 * Remembered drive content with associated primal repressed content are forwarded F49 to F54
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 * 
 */
public interface I5_2_send {
	public void send_I5_2(ArrayList<clsDriveMesh> poData);
}