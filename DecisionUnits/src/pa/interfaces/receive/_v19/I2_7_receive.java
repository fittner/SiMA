/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 */
package pa.interfaces.receive._v19;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 * 
 */
public interface I2_7_receive extends I_BaseInterface {
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output);
}