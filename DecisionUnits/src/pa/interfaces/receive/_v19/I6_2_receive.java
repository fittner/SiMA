/**
 * I6_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:53:21
 */
package pa.interfaces.receive._v19;

import java.util.ArrayList;

import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsAct;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:53:21
 * 
 */
@Deprecated
public interface I6_2_receive extends I_BaseInterface {
	public void receive_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput);
}
