/**
 * CHANGELOG
 *
 * 06.07.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 06.07.2012, 13:47:41
 * 
 */
public class clsActionTools {
	
	private final static clsWordPresentationMesh moNullObjectWPM = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(eContentType.NULLOBJECT.toString(), eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMesh getNullObjectWPM() {
		return moNullObjectWPM;
	}
	
	public static void setSupportiveDataStructure(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poMentalSituation);
				
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
		} else {
			oExistingDataStructure = poDataStructure;
		}
		
	}
	
	public static clsWordPresentationMesh getSupportiveDataStructure(clsWordPresentationMesh poAction) {
		clsWordPresentationMesh oRetVal = getNullObjectWPM();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poAction.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTURE, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
}
