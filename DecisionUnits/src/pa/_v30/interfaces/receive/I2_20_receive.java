/**
 * I2_20_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:19:32
 */
package pa._v30.interfaces.receive;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:19:32
 * 
 */
public interface I2_20_receive extends I_BaseInterface {
	public void receive_I2_20(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP);
}
