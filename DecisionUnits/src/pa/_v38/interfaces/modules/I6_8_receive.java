/**
 * I7_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:52:56
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;


import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * Three action goals are passed on to F52. Word and thing presentations representing the result of module F26 are distributed to F52.
 * 
 * @author deutsch
 * 11.08.2009, 14:52:56
 * 
 */
public interface I6_8_receive {
	public void receive_I6_8(ArrayList<clsSecondaryDataStructureContainer> poTemplateResult, ArrayList<clsPrediction> poExtractedPrediction);
}
