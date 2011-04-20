/**
 * I5_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:07
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:37:07
 * 
 */
@Deprecated
public interface I5_1_receive extends I_BaseInterface {
	public void receive_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList);
}
