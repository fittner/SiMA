/**
 * I2_20_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:19:48
 */
package pa.interfaces.send._v30;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:19:48
 * 
 */
public interface I2_20_send extends I_BaseInterface {
	public void send_I2_20(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP);
}
