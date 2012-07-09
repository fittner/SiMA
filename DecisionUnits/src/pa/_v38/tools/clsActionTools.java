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
import pa._v38.storage.clsShortTermMemory;

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
	
	public static clsWordPresentationMesh createAction(String poAction) {
		return clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(eContentType.ACTION.toString(), poAction), new ArrayList<clsAssociation>());
	}
	
	public static clsWordPresentationMesh getSupportiveDataStructureFromHashCode(clsWordPresentationMesh poAction, clsShortTermMemory poSTM) {
		clsWordPresentationMesh oRetVal = getNullObjectWPM();
		
		//Get the Hashcode
		if (getSupportiveDataStructure(poAction).getMoContent().equals(eContentType.NULLOBJECT.toString())==false) {
			int nHashCode = Integer.valueOf(getSupportiveDataStructure(poAction).getMoContent());
			
			ArrayList<clsWordPresentationMesh> oCompleteMentalSituation = clsMeshTools.getAllWPMImages(poSTM.findCurrentSingleMemory(), 5);
			
			for (clsWordPresentationMesh oWPM : oCompleteMentalSituation) {
				if (oWPM.hashCode()==nHashCode) {
					oRetVal = oWPM;
					break;
				}
			}
		}
				
		return oRetVal;
	}
	
	public static void setSupportiveDataStructureHashCode(clsWordPresentationMesh poAction, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poDataStructure);
		
		clsWordPresentationMesh oWPMRef = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(eContentType.SUPPORTIVEDATASTRUCTURE.toString(), String.valueOf(poDataStructure.hashCode())), new ArrayList<clsAssociation>());
		
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
			clsMeshTools.createAssociationSecondary(poAction, 1, oWPMRef, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
		} else {
			oExistingDataStructure = oWPMRef;
		}
		
	}
	
	/**
	 * Set the supportive data structure of an action
	 * 
	 * (wendt)
	 *
	 * @since 09.07.2012 15:56:12
	 *
	 * @param poMentalSituation
	 * @param poDataStructure
	 */
	public static void setSupportiveDataStructure(clsWordPresentationMesh poAction, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poAction);
				
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
			clsMeshTools.createAssociationSecondary(poAction, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
		} else {
			oExistingDataStructure = poDataStructure;
		}
		
	}
	
	/**
	 * Get the supportive data structure of an action
	 * 
	 * (wendt)
	 *
	 * @since 09.07.2012 15:55:48
	 *
	 * @param poAction
	 * @return
	 */
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
