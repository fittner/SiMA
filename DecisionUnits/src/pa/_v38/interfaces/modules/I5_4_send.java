/**
 * I2_19_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.tools.clsPair;

/**
 * Drives (emotions + quota of affects) are forwarded from F56 to F55
 * 
 * @author deutsch
 * 03.03.2011, 15:34:29
 * 
 */
public interface I5_4_send {
	public void send_I5_4(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives);
}