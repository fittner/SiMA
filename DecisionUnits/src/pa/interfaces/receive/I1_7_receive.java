/**
 * I1_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:01
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:42:01
 * 
 */
public interface I1_7_receive extends I_BaseInterface {
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList);
}