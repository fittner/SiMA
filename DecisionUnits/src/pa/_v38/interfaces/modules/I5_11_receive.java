/**
 * I3_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:35:43
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.tools.clsPair;



/**
 * Superego bans and rules are transported from F7 to F19.
 * 
 * @author deutsch
 * 11.08.2009, 14:35:43
 * 
 */
public interface I5_11_receive {
	public void receive_I5_11(ArrayList<clsPair<eContentType, String>> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh);
}
