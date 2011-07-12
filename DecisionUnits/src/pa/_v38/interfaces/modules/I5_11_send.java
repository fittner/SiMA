/**
 * I3_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:35:43
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * Superego bans and rules are transported from F7 to F19.
 * 
 * @author deutsch
 * 18.05.2010, 14:35:43
 * 
 */
public interface I5_11_send {
	public void send_I5_11(clsPrimaryDataStructureContainer poEnvironmentalPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories);
}
