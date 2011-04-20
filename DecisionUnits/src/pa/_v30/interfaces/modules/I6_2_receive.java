/**
 * I6_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:53:21
 */
package pa._v30.interfaces.modules;

import java.util.ArrayList;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.memorymgmt.datatypes.clsAct;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:53:21
 * 
 */
public interface I6_2_receive extends I_BaseInterface {
	public void receive_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput);
}
