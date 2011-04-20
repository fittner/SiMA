/**
 * I2_10.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:36:32
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:36:32
 * 
 */
@Deprecated
public interface I2_10_receive extends I_BaseInterface {
	public void receive_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception);
}
