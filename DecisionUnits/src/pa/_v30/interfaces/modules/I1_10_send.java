/**
 * I1_10_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:48:48
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;
import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:48:48
 * 
 */
public interface I1_10_send  extends I_BaseInterface {
	public void send_I1_10(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands);
}