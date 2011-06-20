/**
 * I2_16_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:32:07
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
 * 03.03.2011, 16:32:07
 * 
 */
public interface I2_16_send  {
	public void send_I2_16(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation);
}
