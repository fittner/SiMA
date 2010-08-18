/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 * 
 */
public interface I2_8_send {
	public void send_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation_old,
						  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation);
}
