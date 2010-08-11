/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:09
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:18:09
 * 
 */
public interface I2_8_receive extends I_BaseInterface {
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> poMergedPrimaryInformation);
}
