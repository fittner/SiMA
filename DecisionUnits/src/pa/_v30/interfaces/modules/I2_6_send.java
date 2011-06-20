/**
 * I2_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:17:57
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
 * 18.05.2010, 14:17:57
 * 
 */
public interface I2_6_send {
	//first element: perceived clsPrimaryInformation
	//second element (optional): attached repressed content 
	public void send_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed);
}
