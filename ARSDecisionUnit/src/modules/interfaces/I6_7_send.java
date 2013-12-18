/**
 * I2_13.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:43:06
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * The results of the first reality check performed by module F51 is forwarded to F26.
 * 
 * @author deutsch
 * 18.05.2010, 14:43:06
 * 
 */
public interface I6_7_send {
	public void send_I6_7(ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList);
}
