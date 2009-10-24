/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:18:09
 */
package pa.interfaces;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:18:09
 * 
 */
public interface I2_8 {
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation);
}
