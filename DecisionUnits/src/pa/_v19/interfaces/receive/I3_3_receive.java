/**
 * I3_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:44:02
 */
package pa._v19.interfaces.receive;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.interfaces.I_BaseInterface;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:44:02
 * 
 */
@Deprecated
public interface I3_3_receive extends I_BaseInterface {
	public void receive_I3_3(ArrayList<clsAct> poRuleList);
}