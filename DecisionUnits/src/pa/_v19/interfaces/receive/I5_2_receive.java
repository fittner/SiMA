/**
 * I5_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:14
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:37:14
 * 
 */
@Deprecated
public interface I5_2_receive extends I_BaseInterface {
	public void receive_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects);
}
