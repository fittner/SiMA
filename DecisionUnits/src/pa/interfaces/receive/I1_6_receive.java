/**
 * I1_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:10:42
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:10:42
 * 
 */
public interface I1_6_receive extends I_BaseInterface {
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList);
}
