/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 */
package pa.interfaces.send._v30;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 * 
 */
public interface I1_3_send {
	public void send_I1_3(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> >poHomeostaticDriveDemands);
}
