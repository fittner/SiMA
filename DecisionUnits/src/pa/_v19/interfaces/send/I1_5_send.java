/**
 * I1_5.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:02:10
 */
package pa._v19.interfaces.send;

import java.util.List;

import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:02:10
 * 
 */
@Deprecated
public interface I1_5_send {
	public void send_I1_5(List<clsPrimaryInformation> poData_old,
						  List<clsDriveMesh> poData);
}
