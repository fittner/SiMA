/**
 * I2_11.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 * 
 */
@Deprecated
public interface I2_11_receive extends I_BaseInterface {
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception);
}
