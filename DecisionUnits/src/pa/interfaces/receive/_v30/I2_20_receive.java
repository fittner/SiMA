/**
 * I2_20_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:19:32
 */
package pa.interfaces.receive._v30;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

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
