/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:09
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.tools.clsPair;

import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 14:18:09
 * 
 */
public interface I2_8_receive {
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation);
}
