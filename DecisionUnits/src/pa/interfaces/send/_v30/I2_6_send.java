/**
 * I2_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:17:57
 */
package pa.interfaces.send._v30;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:17:57
 * 
 */
public interface I2_6_send {
	//first element: perceived clsPrimaryInformation
	//second element (optional): attached repressed content 
	public void send_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed);
}
