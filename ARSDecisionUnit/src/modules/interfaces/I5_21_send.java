/**
 * I2_21_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author muchitsch
 * 03.03.2011, 16:21:37
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMesh;


/**
 * Thing presentations and their quota of affects are transported from F37 to F35 and F57.
 * 
 * @author muchitsch
 * 07.05.2012, 16:21:37
 * 
 */
public interface I5_21_send {
	public void send_I5_21(ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2);
}
