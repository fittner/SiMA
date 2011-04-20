/**
 * I2_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:17:57
 */
package pa._v30.interfaces.receive;

import java.util.ArrayList;

import pa._v30.tools.clsPair;
import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:17:57
 * 
 */
public interface I2_6_receive extends I_BaseInterface {
	//first element: perceived clsPrimaryInformation
	//second element (optional): attached repressed content 
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed);
}
