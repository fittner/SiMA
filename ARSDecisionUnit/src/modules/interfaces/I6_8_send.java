/**
 * I7_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:52:56
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * Three action goals are passed on to F52. Word and thing presentations representing the result of module F26 are distributed to F52.
 * 
 * @author deutsch
 * 18.05.2010, 14:52:56
 * 
 */
public interface I6_8_send {
	public void send_I6_8(ArrayList<clsWordPresentationMeshPossibleGoal> poDecidedGoalList_OUT, clsWordPresentationMesh moWordingToContext2);
}
