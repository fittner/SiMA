/**
 * I2_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:17:57
 */
package pa.interfaces;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:17:57
 * 
 */
public interface I2_6 {
	//first element: perceived clsPrimaryInformation
	//second element (optional): attached repressed content 
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed);
}
