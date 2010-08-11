/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 * 
 */
public interface I1_3_receive extends I_BaseInterface {
	public void receive_I1_3(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate_old,
			 ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsAssociationDriveMesh>, clsPair<clsThingPresentationMesh, clsAssociationDriveMesh>>> poDriveCandidate);
}
