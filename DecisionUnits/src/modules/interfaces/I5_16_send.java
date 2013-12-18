/**
 * I5_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:37:14
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;

/**
 * Analogous to I5.17, I5.16 transports quota of affects which have formerly been attached to thing presentations representing perceived contents. They are forwarded from F19 to F20.
 * 
 * @author gelbard
 * 18.05.2010, 14:37:14
 * 
 */
public interface I5_16_send {
	public void send_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions);
}
