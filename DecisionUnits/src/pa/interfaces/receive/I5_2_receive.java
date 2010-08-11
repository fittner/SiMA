/**
 * I5_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:14
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsAffectTension;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:37:14
 * 
 */
public interface I5_2_receive extends I_BaseInterface {
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects_old,
							ArrayList<clsAssociationDriveMesh> poDeniedAffects);
}
