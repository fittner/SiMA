/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.tools.clsPair;

import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 * 
 */
public interface I1_3_receive {
	public void receive_I1_3(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> >poHomeostaticDriveDemands);
}
