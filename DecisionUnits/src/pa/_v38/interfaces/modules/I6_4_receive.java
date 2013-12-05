/**
 * I5_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:28
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * Analogous to 6.5, this interface transports the perceptions in the form of word presentations, thing presentations, and affects from F21 to F20.
 * 
 * @author deutsch
 * 11.08.2009, 14:37:28
 * 
 */
public interface I6_4_receive {
	public void receive_I6_4(clsWordPresentationMesh poPerception);
}
