/**
 * I2_14_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:21:20
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:21:20
 * 
 */
public interface I2_14_receive extends I_BaseInterface {
	public void receive_I2_14(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP);
}
