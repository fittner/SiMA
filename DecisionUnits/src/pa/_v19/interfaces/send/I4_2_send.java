/**
 * I4_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:27:51
 */
package pa._v19.interfaces.send;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:27:51
 * 
 */
@Deprecated
public interface I4_2_send {
	public void send_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects);
}
