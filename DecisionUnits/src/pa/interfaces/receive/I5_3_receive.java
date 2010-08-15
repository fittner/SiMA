/**
 * I5_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:21
 */
package pa.interfaces.receive;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:37:21
 * 
 */
public interface I5_3_receive extends I_BaseInterface {
	public void receive_I5_3(ArrayList<clsSecondaryInformation> poDriveList_old,
				ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>> poDriveList);
}
