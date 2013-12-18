/**
 * I8_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:53:33
 */
package modules.interfaces;

import java.util.ArrayList;

import du.itf.actions.clsActionCommand;

/**
 * Connection of a neurosymbolization module with its body module. F31->F32
 * 
 * @author deutsch
 * 18.05.2010, 14:53:33
 * 
 */
public interface I1_5_send {
	public void send_I1_5(ArrayList<clsActionCommand> poActionCommandList);
}
