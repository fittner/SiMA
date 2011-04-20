/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 */
package pa.interfaces.receive._v30;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 13:40:52
 * 
 */
public interface I1_3_receive extends I_BaseInterface {
	public void receive_I1_3(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> >poHomeostaticDriveDemands);
}
