/**
 * I7_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:53:12
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.loader.plan.clsPlanAction;
import pa.memorymgmt.datatypes.clsWordPresentation;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:53:12
 * 
 */
public interface I7_4_send {
	public void send_I7_4(ArrayList<clsPlanAction> poActionCommands_old, ArrayList<clsWordPresentation> poActionCommands);
}
