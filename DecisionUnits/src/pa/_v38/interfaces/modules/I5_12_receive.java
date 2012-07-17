/**
 * I2_19_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:34:03
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsEmotion;


/**
 * Drive content/Drives F55 to F7
 * 
 * @author deutsch
 * 03.03.2011, 15:34:04
 * 
 */
public interface I5_12_receive {
	public void receive_I5_12(ArrayList<clsDriveMeshOLD> poDrives, ArrayList<clsEmotion> poEmotions);
}