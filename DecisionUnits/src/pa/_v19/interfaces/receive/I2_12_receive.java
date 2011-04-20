/**
 * I2_12.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:59
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:42:59
 * 
 */
@Deprecated
public interface I2_12_receive extends I_BaseInterface {
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
							  ArrayList<clsSecondaryDataStructureContainer> poDriveList);
}
