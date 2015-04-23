/**
 * I2_11.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;

/**
 * The perception contents consisting of word presentations, thing presentations, and affects are sent from F21 to F26 and F23 for further processing.
 * 
 * @author deutsch
 * 11.08.2009, 14:42:23
 * 
 */
public interface I6_1_receive {
	public void receive_I6_1(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);
}
