/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsPrimaryDataStructureContainer;



/**
 * Read access to blocked content (from F36).
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 * 
 */
public interface D2_4_receive {
	public void receive_D2_4(clsPrimaryDataStructureContainer poData, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories);
}
