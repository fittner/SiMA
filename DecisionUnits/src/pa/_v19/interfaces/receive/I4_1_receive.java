/**
 * I4_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:27:40
 */
package pa._v19.interfaces.receive;

import java.util.List;

import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:27:40
 * 
 */
@Deprecated
public interface I4_1_receive extends I_BaseInterface {
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects);
}
