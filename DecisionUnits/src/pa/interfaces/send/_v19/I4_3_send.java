/**
 * I4_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:06:31
 */
package pa.interfaces.send._v19;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

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
