/**
 * I1_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:02:10
 */
package pa._v19.interfaces.receive;

import java.util.List;

import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:02:10
 * 
 */
@Deprecated
public interface I1_5_receive extends I_BaseInterface {
	public void receive_I1_5(List<clsDriveMesh> poData);
}
