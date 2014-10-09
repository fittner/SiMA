/**
 * I2_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:51
 */
package modules.interfaces;

import java.util.ArrayList;
import memorymgmt.enums.PsychicSpreadingActivationMode;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * Memory traces representing perceived environment and body information are forwarded to F46.
 * 
 * @author deutsch
 * 11.08.2009, 14:16:51
 * 
 */
public interface I2_6_receive {
	public void receive_I2_6(ArrayList<clsThingPresentationMesh> poEnvironmentalTP, ArrayList<clsDriveMesh> poDrives,
	        ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode poMode, clsWordPresentationMesh moWordingToContext2);
}
