/**
 * I0_6_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:30:30
 */
package pa._v30.interfaces.send;

import java.util.ArrayList;

import du.itf.actions.clsActionCommand;

import pa._v30.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:30:30
 * 
 */
public interface I0_6_send  extends I_BaseInterface {
	public void send_I0_6(ArrayList<clsActionCommand> poActionList);
}