/**
 * I3_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:35:43
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;

/**
 * Selected drive content is forwarded from F55 to F6
 * 
 * @author deutsch
 * 18.05.2010, 14:35:43
 * 
 */
public interface I5_5_send {
	public void send_I5_5(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> poData);
}
