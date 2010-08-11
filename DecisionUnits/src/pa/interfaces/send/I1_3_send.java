/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 * 
 */
public interface I1_3_send {
	public void send_I1_3(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate_old,
						 ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsAssociationDriveMesh>, clsPair<clsThingPresentationMesh, clsAssociationDriveMesh>>> poDriveCandidate);
}
