/**
 * I1_7.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:42:01
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:42:01
 * 
 */
public interface I1_7_send {
	public void send_I1_7(ArrayList<clsSecondaryInformation> poDriveList_old,
						  ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>> poDriveList);
}
