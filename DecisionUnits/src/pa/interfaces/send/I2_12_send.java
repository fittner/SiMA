/**
 * I2_12.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:42:59
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:42:59
 * 
 */
public interface I2_12_send {
	public void send_I2_12(ArrayList<clsSecondaryInformation> poFocusedPerception_old,
						   ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
						   ArrayList<clsSecondaryInformation> poDriveList_old,
						   ArrayList<clsSecondaryDataStructureContainer> poDriveList);
}
