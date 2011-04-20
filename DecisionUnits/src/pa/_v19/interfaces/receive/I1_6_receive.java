/**
 * I1_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:10:42
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:10:42
 * 
 */
@Deprecated
public interface I1_6_receive extends I_BaseInterface {
	public void receive_I1_6(ArrayList<clsDriveMesh> poDriveList);
}
