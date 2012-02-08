/**
 * I7_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:52:56
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.tools.clsTriple;

/**
 * Three action goals are passed on to F52. Word and thing presentations representing the result of module F26 are distributed to F52.
 * 
 * @author deutsch
 * 18.05.2010, 14:52:56
 * 
 */
public interface I6_8_send {
	public void send_I6_8(ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> poTemplateResult, clsWordPresentationMesh poEnvironmentalPerception, ArrayList<clsPrediction> poExtractedPrediction, ArrayList<clsWordPresentationMesh> poAssociatedMemories);
}
