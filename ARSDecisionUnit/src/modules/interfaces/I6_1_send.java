/**
 * I2_11.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:42:23
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;

/**
 * The perception contents consisting of word presentations, thing presentations, and affects are sent from F21 to F26 and F23 for further processing.
 * 
 * @author deutsch
 * 18.05.2010, 14:42:23
 * 
 */
public interface I6_1_send {
	public void send_I6_1(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);
}
