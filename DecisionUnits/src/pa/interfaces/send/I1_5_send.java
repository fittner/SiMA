/**
 * I1_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:02:10
 */
package pa.interfaces.send;

import java.util.List;

import pa.datatypes.clsPrimaryInformation;
import pa.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:02:10
 * 
 */
public interface I1_5_send {
	public void send_I1_5(List<clsPrimaryInformation> poData_old,
						  List<clsDriveMesh> poData);
}
