/**
 * I7_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:53:02
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:53:02
 * 
 */
@Deprecated
public interface I7_2_receive extends I_BaseInterface {
	public void receive_I7_2(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output);
}
