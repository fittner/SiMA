/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;
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
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output_old,
			  ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer,ArrayList<clsPrimaryDataStructureContainer>>> poPerceptPlusMemories_Output);
}
