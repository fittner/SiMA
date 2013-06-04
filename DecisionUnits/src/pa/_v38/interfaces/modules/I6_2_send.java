/**
 * I5_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:37:34
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;

/**
 * Differentiated experienced moods (anxiety, worriedness, prickle) are transported from F20 to F26, F52, and F29.
 * 
 * @author deutsch
 * 18.05.2010, 14:37:34
 * 
 */
public interface I6_2_send {
	public void send_I6_2(ArrayList<clsWordPresentationMeshFeeling> moSecondaryDataStructureContainer_Output);
}
