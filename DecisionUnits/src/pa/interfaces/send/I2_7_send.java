/**
 * I2_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:03
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:18:03
 * 
 */
public interface I2_7_send {
	public void send_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output);
}
