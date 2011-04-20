/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:03
 */
package pa.interfaces.send._v19;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsTripple;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:18:03
 * 
 */
@Deprecated
public interface I2_7_send {
	public void send_I2_7(ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output);
}
