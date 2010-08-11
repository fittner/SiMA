/**
 * I2_9.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:04:46
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:04:46
 * 
 */
public interface I2_9_receive extends I_BaseInterface {
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation_old,
			  ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation);
}
