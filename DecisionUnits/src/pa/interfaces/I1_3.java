/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 */
package pa.interfaces;

import java.util.ArrayList;

import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsThingPresentationMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 * 
 */
public interface I1_3 {
	public void receive_I1_3(ArrayList<clsPair<clsThingPresentationMesh, clsAffectCandidate>> poDriveCandidate);
}
