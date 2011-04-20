/**
 * I4_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:27:40
 */
package pa.interfaces.receive._v19;

import java.util.List;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:27:40
 * 
 */
@Deprecated
public interface I4_1_receive extends I_BaseInterface {
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects);
}
