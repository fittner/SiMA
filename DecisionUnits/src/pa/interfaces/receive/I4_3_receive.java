/**
 * I4_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:06:31
 */
package pa.interfaces.receive;

import java.util.List;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:06:31
 * 
 */
public interface I4_3_receive extends I_BaseInterface {
	public void receive_I4_3(List<clsPrimaryInformation> poPIs);
}
