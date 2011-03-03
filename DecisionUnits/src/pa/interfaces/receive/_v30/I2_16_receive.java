/**
 * I2_16_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:31:47
 */
package pa.interfaces.receive._v30;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:31:47
 * 
 */
public interface I2_16_receive extends I_BaseInterface {
	public void receive_I2_16(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation);
}