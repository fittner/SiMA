/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;


/**
 * Drives (emotions + quota of affects) are forwarded from F56 to F55 
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_4_receive {
	public void receive_I5_4(ArrayList<clsDriveMeshOLD> poDrives);
}