/**
 * I7_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:53:08
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * The various imaginated action plans are distributed from F52 to F47, F29, and F53.
 * 
 * @author deutsch
 * 18.05.2010, 14:53:08
 * 
 */
public interface I6_9_send {
	public void send_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainer> poAssociatedMemories, clsDataStructureContainerPair poEnvironmentalPerception);
}
