/**
 * I5_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:37:21
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
 * 18.05.2010, 14:37:21
 * 
 */
public interface I5_3_send {
	public void send_I5_3(ArrayList<clsSecondaryInformation> poDriveList_old,
						  ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>> poDriveList);
}
