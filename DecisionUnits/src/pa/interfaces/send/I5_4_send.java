/**
 * I5_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:37:28
 */
package pa.interfaces.send;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:37:28
 * 
 */
public interface I5_4_send {
	public void send_I5_4(ArrayList<clsSecondaryInformation> poPerception_old,
						  ArrayList<clsSecondaryDataStructureContainer> poPerception);
}
