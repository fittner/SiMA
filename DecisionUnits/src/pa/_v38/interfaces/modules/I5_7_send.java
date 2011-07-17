/**
 * I2_14_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:21:37
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;


import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * Thing presentations and their quota of affects are transported from F37 to F35 and F57.
 * 
 * @author deutsch
 * 03.03.2011, 16:21:37
 * 
 */
public interface I5_7_send {
	public void send_I5_7(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories);
}
