/**
 * I6_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:53:21
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.interfaces.I_BaseInterface;

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
