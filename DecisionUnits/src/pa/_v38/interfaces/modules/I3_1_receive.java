/**
 * I1_10_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:48:36
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.tools.clsPair;

/**
 * The total amount of libido tension as well as the pair of opposites are transmitted from F41 to F43.
 * 
 * @author deutsch
 * 03.03.2011, 15:48:36
 * 
 */
public interface I3_1_receive  {
	public void receive_I3_1(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands);
}