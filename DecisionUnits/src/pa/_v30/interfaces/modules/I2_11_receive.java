/**
 * I2_11.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 * 
 */
public interface I2_11_receive extends I_BaseInterface {
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception);
}
