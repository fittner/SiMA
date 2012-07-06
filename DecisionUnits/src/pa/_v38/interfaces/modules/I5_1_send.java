/**
 * I2_19_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;


/**
 * Remembered drive content (emotions + quota of affects) are forwarded from F57 to F54
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 * 
 */
public interface I5_1_send {
	public void send_I5_1(ArrayList<clsDriveMesh> poData);
}