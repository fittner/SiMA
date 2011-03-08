/**
 * I2_11.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 */
package pa.interfaces.receive._v19;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 * 
 */
public interface I2_11_receive extends I_BaseInterface {
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception);
}
