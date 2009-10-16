/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:59:39
 */
package pa.interfaces;

import java.util.ArrayList;

import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:59:39
 * 
 */
public interface I1_4 {
	public void receive_I1_4(ArrayList<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>> poDriveCandidate);
}
