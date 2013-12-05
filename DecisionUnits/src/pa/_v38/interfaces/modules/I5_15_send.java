/**
 * I2_10.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:36:32
 */
package pa._v38.interfaces.modules;




import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * Transports (unchanged or adapted) perceptions which passed the defense mechanisms from F19 to the conversion module F21.
 * 
 * @author deutsch
 * 18.05.2010, 14:36:32
 * 
 */
public interface I5_15_send {
	public void send_I5_15(clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsEmotion> poEmotions);
}
