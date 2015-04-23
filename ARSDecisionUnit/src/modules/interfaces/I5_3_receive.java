/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;


/**
 * Drives (emotions + quota of affects) are forwarded from F54 to F56
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_3_receive {
	public void receive_I5_3(ArrayList<clsDriveMesh> poDrives);
}