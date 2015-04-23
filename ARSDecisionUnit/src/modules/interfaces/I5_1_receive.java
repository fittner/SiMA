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
 * Remembered drive content (emotions + quota of affects) are forwarded from F57 to F54
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_1_receive {
	public void receive_I5_1(ArrayList <clsDriveMesh> poData);
}