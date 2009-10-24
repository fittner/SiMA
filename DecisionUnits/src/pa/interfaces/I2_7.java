/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 */
package pa.interfaces;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 * 
 */
public interface I2_7 {
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output);
}
