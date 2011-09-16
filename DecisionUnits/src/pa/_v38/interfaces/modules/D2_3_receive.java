/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;



/**
 * Write access to blocked content (from F36).
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 * 
 */
public interface D2_3_receive {
	public void receive_D2_3(clsPhysicalRepresentation poDS, clsDriveMesh poDM);
}
