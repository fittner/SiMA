/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 */
package pa._v19.interfaces.send;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 * 
 */
@Deprecated
public interface I2_8_send {
	public void send_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation);
}
