/**
 * I3_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 * 
 */
public interface I5_13_send {
	public void send_I5_13(ArrayList<String> poForbiddenDrive, ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData);
}
