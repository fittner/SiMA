/**
 * I2_9.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:04:46
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:04:46
 * 
 */
public interface I2_9_send {
	public void send_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation_old,
						  ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation);
}
