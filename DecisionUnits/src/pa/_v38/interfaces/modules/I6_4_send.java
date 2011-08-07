/**
 * I5_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:37:28
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;

/**
 * Analogous to 6.5, this interface transports the perceptions in the form of word presentations, thing presentations, and affects from F21 to F20.
 * 
 * @author deutsch
 * 18.05.2010, 14:37:28
 * 
 */
public interface I6_4_send {
	public void send_I6_4(ArrayList<clsDataStructureContainer> poPerception);
}
