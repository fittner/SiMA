/**
 * I1_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:42:01
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * This interface distributes the drive wishes produced by module F8 to the modules F23 and F26.
 * 
 * @author deutsch
 * 18.05.2010, 14:42:01
 * 
 */
public interface I6_3_send {
	public void send_I6_3(ArrayList<clsWordPresentationMesh> poDriveList);
}
