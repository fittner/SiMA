/**
 * I2_19_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 */
package pa.interfaces.send._v30;

import java.util.List;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 * 
 */
public interface I2_19_send extends I_BaseInterface {
	public void send_I2_19(List<clsDriveMesh> poData);
}