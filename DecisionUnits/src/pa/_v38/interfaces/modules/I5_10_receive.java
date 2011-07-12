/**
 * I2_9.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:04:46
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;


import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * Thing presentations of the perception (enriched with data from memory and feed back thing presentations) and their attached quota of affects are forwarded from F18 to F7.
 * 
 * @author deutsch
 * 11.08.2009, 14:04:46
 * 
 */
public interface I5_10_receive {
	public void receive_I5_10(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories);
}
