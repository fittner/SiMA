/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package pa.interfaces.receive._v30;

import java.util.List;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I2_19_receive extends I_BaseInterface {
	public void receive_I2_19(List<clsDriveMesh> poData);
}