/**
 * I4_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:06:31
 */
package pa._v19.interfaces.send;

import java.util.ArrayList;

import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:06:31
 * 
 */
@Deprecated
public interface I4_3_send {
	public void send_I4_3(ArrayList<clsPrimaryInformation> poPIs_old,
						  ArrayList<clsPrimaryDataStructureContainer> poPIs);
}
