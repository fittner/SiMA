/**
 * I1_10_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:48:36
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
 * 03.03.2011, 15:48:36
 * 
 */
public interface I1_10_receive  extends I_BaseInterface {
	public void receive_I1_10(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands);
}