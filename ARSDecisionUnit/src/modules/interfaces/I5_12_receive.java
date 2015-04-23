/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMesh;


/**
 * Drive content/Drives F55 to F7
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_12_receive {
	public void receive_I5_12(ArrayList<clsDriveMesh> poDrives, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2);
}