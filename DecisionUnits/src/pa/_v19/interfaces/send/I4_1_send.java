/**
 * I4_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:27:40
 */
package pa._v19.interfaces.send;

import java.util.List;

import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:27:40
 * 
 */
@Deprecated
public interface I4_1_send {
	public void send_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects);
}
