/**
 * I7_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:52:56
 */
package modules.interfaces;

import java.util.ArrayList;





import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * Three action goals are passed on to F52. Word and thing presentations representing the result of module F26 are distributed to F52.
 * 
 * @author deutsch
 * 11.08.2009, 14:52:56
 * 
 */
public interface I6_8_receive {
	public void receive_I6_8(ArrayList<clsWordPresentationMeshPossibleGoal> poDecidedGoalList_OUT);
}
