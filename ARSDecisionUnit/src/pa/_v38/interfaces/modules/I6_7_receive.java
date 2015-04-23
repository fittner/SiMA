/**
 * CHANGELOG
 *
 * 11.05.2013 hinterleitner - File created
 *
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * The results of the first reality check performed by module F51 is forwarded to F26.
 * 
 * @author deutsch
 * 11.08.2009, 14:43:06
 * 
 */
public interface I6_7_receive {
	public void receive_I6_7(ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList, clsWordPresentationMesh moWordingToContext2);
}
