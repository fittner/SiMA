/**
 * I7_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 27.04.2010, 10:42:05
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * Reality checked action plans are passed on from F53 to F29.
 * 
 * @author deutsch
 * 27.04.2010, 10:42:05
 * 
 */
public interface I6_10_send {
	public void send_I6_10(ArrayList<clsWordPresentationMeshPossibleGoal> poSelectableGoals, clsWordPresentationMesh moWordingToContext2);
}
