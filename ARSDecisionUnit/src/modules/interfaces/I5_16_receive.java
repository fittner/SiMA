/**
 * I5_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:14
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * Analogous to I5.17, I5.16 transports quota of affects which have formerly been attached to thing presentations representing perceived contents. They are forwarded from F19 to F20.
 * 
 * @author deutsch
 * 11.08.2009, 14:37:14
 * 
 */
public interface I5_16_receive {
	public void receive_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2, clsThingPresentationMesh poPerceptionalMesh);
}
