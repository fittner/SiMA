/**
 * I7_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:53:02
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:53:02
 * 
 */
public interface I7_2_receive extends I_BaseInterface {
	public void receive_I7_2(int pnData, ArrayList<clsSecondaryDataStructureContainer> poGoal_Output);
}
