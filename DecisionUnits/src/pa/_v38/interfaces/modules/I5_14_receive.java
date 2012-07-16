/**
 * I3_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:35:43
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;



/**
 * Superego bans and rules are transported from F55 to F19.
 * 
 * @author deutsch
 * 11.08.2009, 14:35:43
 * 
 */
public interface I5_14_receive {
	public void receive_I5_14(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> poData);
}
