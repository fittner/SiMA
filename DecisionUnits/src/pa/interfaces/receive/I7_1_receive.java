/**
 * I7_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:52:56
 */
package pa.interfaces.receive;

import java.util.HashMap;

import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I_BaseInterface;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:52:56
 * 
 */
public interface I7_1_receive extends I_BaseInterface {
	public void receive_I7_1(HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateResult_old, 
			  				HashMap<String, clsPair<clsSecondaryDataStructureContainer,Double>> poTemplateResult);
}
