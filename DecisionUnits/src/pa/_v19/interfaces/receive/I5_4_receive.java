/**
 * I5_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:28
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:37:28
 * 
 */
@Deprecated
public interface I5_4_receive extends I_BaseInterface {
	public void receive_I5_4(ArrayList<clsSecondaryDataStructureContainer> poPerception);
}
