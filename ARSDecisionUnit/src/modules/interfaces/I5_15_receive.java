/**
 * I2_10.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:36:32
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;

/**
 * Transports (unchanged or adapted) perceptions which passed the defense mechanisms from F19 to the conversion module F21.
 * 
 * @author deutsch
 * 11.08.2009, 14:36:32
 * 
 */
public interface I5_15_receive {
	public void receive_I5_15(clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsEmotion> poEmotions);
}
