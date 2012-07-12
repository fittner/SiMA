/**
 * CHANGELOG
 *
 * 04.07.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 04.07.2012, 10:41:19
 * 
 */
public class clsMentalSituationTools {
	
	private final static clsWordPresentationMesh moNullObjectWPM = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	
//	public clsMentalSituationTools() {
//		//Create Null-object
//		setNullObjectWPM(clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(eContentType.NULLOBJECT.toString(), eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>()));
//	}
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMesh getNullObjectWPM() {
		return moNullObjectWPM;
	}
	
//	/**
//	 * @since 05.07.2012 22:04:13
//	 * 
//	 * @param moNullObjectWPM the moNullObjectWPM to set
//	 */
//	private static void setNullObjectWPM(clsWordPresentationMesh moNullObjectWPM) {
//		clsMentalSituationTools.moNullObjectWPM = moNullObjectWPM;
//	}
	
	public static clsWordPresentationMesh createMentalSituation() {
		//Create identifiyer. All goals must have the content type "MENTALSITUATION"
		clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.MENTALSITUATION);
		
		//Create the basic goal structure
		clsWordPresentationMesh oRetVal = new clsWordPresentationMesh(oDataStructureIdentifier, new ArrayList<clsAssociation>(), "MENTALSITUATION");	//Here the current step could be used

		return oRetVal;
	}
	
	public static void setGoal(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getGoal(poMentalSituation);
		clsWordPresentationMesh oExistingDataSupportiveStructure = null;
		
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASGOAL, false);
			oExistingDataSupportiveStructure = clsGoalTools.getSupportiveDataStructure(poDataStructure);
		} else {
			oExistingDataStructure = poDataStructure;
			oExistingDataSupportiveStructure = clsGoalTools.getSupportiveDataStructure(oExistingDataStructure);
		}
		
		if (oExistingDataSupportiveStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==false) {
			setSupportiveDataStructure(poMentalSituation, oExistingDataSupportiveStructure);
		}
		
		
		
	}
	
	public static clsWordPresentationMesh getGoal(clsWordPresentationMesh poMentalSituation) {
		clsWordPresentationMesh oRetVal = getNullObjectWPM();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poMentalSituation.findDataStructure(ePredicate.HASGOAL, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	public static void setAction(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getAction(poMentalSituation);
				
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTION, false);
		} else {
			oExistingDataStructure = poDataStructure;
		}
		
	}
	
	public static clsWordPresentationMesh getAction(clsWordPresentationMesh poMentalSituation) {
		clsWordPresentationMesh oRetVal = getNullObjectWPM();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poMentalSituation.findDataStructure(ePredicate.HASACTION, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	public static void setSupportiveDataStructure(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poMentalSituation);
				
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
		} else {
			oExistingDataStructure = poDataStructure;
		}
		
	}
	
	public static clsWordPresentationMesh getSupportiveDataStructure(clsWordPresentationMesh poMentalSituation) {
		clsWordPresentationMesh oRetVal = getNullObjectWPM();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poMentalSituation.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTURE, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	
}