/**
 * I2_12.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:42:59
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * The list - the package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importance - is forwarded by the interface I6.6 to F51.
 * 
 * @author deutsch
 * 18.05.2010, 14:42:59
 * 
 */
public interface I6_6_send {
	public void send_I6_6(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMeshPossibleGoal> poSelectableGoalList, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);
}
