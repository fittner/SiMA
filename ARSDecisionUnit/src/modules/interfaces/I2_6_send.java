/**
 * I2_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:51
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;

/**
 * Memory traces representing perceived environment and body information are forwarded to F46.
 * 
 * @author deutsch
 * 18.05.2010, 14:16:51
 * 
 */
public interface I2_6_send {
	public void send_I2_6(ArrayList<clsThingPresentationMesh> poEnvironmentalTP, ArrayList<clsDriveMesh> poDrives);
}
