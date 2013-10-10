/**
 * I7_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 27.04.2010, 10:42:05
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;



/**
 * Reality checked action plans are passed on from F53 to F29.
 * 
 * @author deutsch
 * 27.04.2010, 10:42:05
 * 
 */
public interface I6_10_receive {
	public void receive_I6_10(ArrayList<clsWordPresentationMeshSelectableGoal> poSelectableGoals);
}
