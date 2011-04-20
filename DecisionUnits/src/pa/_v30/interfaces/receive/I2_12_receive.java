/**
 * I2_12.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:59
 */
package pa._v30.interfaces.receive;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:42:59
 * 
 */
public interface I2_12_receive extends I_BaseInterface {
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
							  ArrayList<clsSecondaryDataStructureContainer> poDriveList);
}
