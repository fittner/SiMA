/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:03
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 *
 * 
 * @author deutsch
 * 18.05.2010, 14:18:03
 * 
 */
public interface I2_7_send {
	public void send_I2_7(ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output);
}
