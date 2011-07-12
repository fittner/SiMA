/**
 * itfInspectorDrives.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 12:09:10
 */
package pa._v38.interfaces;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;

/**
 * Displays the provided drives in an inspector tab.
 * 
 * @author deutsch
 * 23.04.2011, 12:09:10
 * 
 */
@Deprecated
public interface itfInspectorDrives {
	public ArrayList<clsDriveMesh> getDriveList();
}
