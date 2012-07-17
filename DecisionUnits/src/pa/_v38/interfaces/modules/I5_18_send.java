/**
 * I1_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:10:42
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;

/**
 * Transports (unchanged or adapted) drive contents which passed the defense mechanisms from F6 to the conversion module F8.
 * 
 * @author deutsch
 * 18.05.2010, 14:10:42
 * 
 */
public interface I5_18_send {
	public void send_I5_18(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> poDriveList);
}
