/**
 * I1_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;
/**
 * The homeostatic drive are transfered from F65 to F48
 * 
 * @author deutsch
 * 18.05.2010, 13:59:39
 * 
 */
public interface I3_4_send {
	public void send_I3_4(ArrayList <clsDriveMesh> poDriveComponents);
}
