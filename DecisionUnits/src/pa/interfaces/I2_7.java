/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 */
package pa.interfaces;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:18:03
 * 
 */
public interface I2_7 {
	public void receive_I2_7(ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output);
}
