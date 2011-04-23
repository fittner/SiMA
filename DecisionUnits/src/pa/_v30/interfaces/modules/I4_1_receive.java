/**
 * I4_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:27:40
 */
package pa._v30.interfaces.modules;

import java.util.List;


import pa._v30.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:27:40
 * 
 */
public interface I4_1_receive {
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v30.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects);
}
