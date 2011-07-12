/**
 * I2_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:51
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;


import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * Memory traces representing perceived environment and body information are forwarded to F46.
 * 
 * @author deutsch
 * 11.08.2009, 14:16:51
 * 
 */
public interface I2_6_receive {
	public void receive_I2_6(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP);
}
