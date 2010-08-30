/**
 * I7_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:52:56
 */
package pa.interfaces.send;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsSecondaryInformation;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:52:56
 * 
 */
public interface I7_1_send {
	public void send_I7_1(HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateResult_old, 
			ArrayList<clsSecondaryDataStructureContainer> poTemplateResult);
}
