/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;

/**
 * Drives (emotions + quota of affects) are forwarded from F54 to F56
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_3_receive {
	public void receive_I5_3(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives);
}