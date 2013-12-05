/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:59:39
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;

/**
 * The homeostatic drive are transfered from F65 to F48
 * 
 * @author deutsch
 * 11.08.2009, 13:59:39
 * 
 */
public interface I3_4_receive {
	public void receive_I3_4(ArrayList <clsDriveMesh> poDriveComponents);
}
