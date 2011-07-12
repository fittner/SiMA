/**
 * I2_14_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:21:20
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;


import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * Thing presentations and their quota of affects are transported from F37 to F35 and F57.
 * 
 * @author deutsch
 * 03.03.2011, 16:21:20
 * 
 */
public interface I5_7_receive {
	public void receive_I5_7(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories);
}

