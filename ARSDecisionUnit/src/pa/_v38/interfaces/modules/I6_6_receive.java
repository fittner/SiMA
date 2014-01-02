/**
 * I2_12.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:59
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
/**
 * The list - the package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importance - is forwarded by the interface I6.6 to F51.
 * 
 * @author deutsch
 * 11.08.2009, 14:42:59
 * 
 */
public interface I6_6_receive {
	public 
    
    void receive_I6_6(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList,
            clsWordPresentationMesh poContextToWording);
}
