/**
 * I4_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:27:51
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;


import pa._v30.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 14:27:51
 * 
 */
public interface I4_2_receive {
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa._v30.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects);
}
