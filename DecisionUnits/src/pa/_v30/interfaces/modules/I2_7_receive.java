/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.tools.clsTripple;
import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

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
