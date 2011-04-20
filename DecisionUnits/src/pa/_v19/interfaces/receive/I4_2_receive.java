/**
 * I4_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:27:51
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:27:51
 * 
 */
@Deprecated
public interface I4_2_receive extends I_BaseInterface {
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects);
}
