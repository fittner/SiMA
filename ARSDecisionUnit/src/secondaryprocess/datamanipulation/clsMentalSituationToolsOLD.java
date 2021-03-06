/**
 * CHANGELOG
 *
 * 04.07.2012 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePredicate;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 04.07.2012, 10:41:19
 * 
 */
public class clsMentalSituationToolsOLD {
	
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
	
	public static void setGoal(clsWordPresentationMesh poMentalSituation, clsWordPresentationMeshGoal poDataStructure) {
		clsWordPresentationMeshGoal oExistingDataStructure = getGoal(poMentalSituation);
		clsWordPresentationMesh oExistingDataSupportiveStructure = null;
		
		if (oExistingDataStructure.getContentType().equals(eContentType.NULLOBJECT)==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASGOAL, false);
			oExistingDataSupportiveStructure = poDataStructure.getSupportiveDataStructure();
		} else {
			oExistingDataStructure = poDataStructure;
			oExistingDataSupportiveStructure = oExistingDataStructure.getSupportiveDataStructure();
		}
		
		if (oExistingDataSupportiveStructure.getContentType().equals(eContentType.NULLOBJECT)==false) {
			setSupportiveDataStructure(poMentalSituation, oExistingDataSupportiveStructure);
		}
		
		
		
	}
	
	public static clsWordPresentationMeshPossibleGoal getGoal(clsWordPresentationMesh poMentalSituation) {
	    clsWordPresentationMeshPossibleGoal oRetVal = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poMentalSituation.findDataStructure(ePredicate.HASGOAL, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMeshPossibleGoal) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	public static ArrayList<clsWordPresentationMesh> getExcludedGoal(clsWordPresentationMesh poMentalSituation) {		
		return clsMeshTools.getNonUniquePredicateWPM(poMentalSituation, ePredicate.HASEXCLUDEDGOAL);
	}
	
	public static void setExcludedGoal(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poGoal) {
		clsMeshTools.setNonUniquePredicateWPM(poMentalSituation, ePredicate.HASEXCLUDEDGOAL, poGoal, true);
		//FIM HASEXCLUDEDGOAL
	}
	
	
	
	public static void setAction(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getAction(poMentalSituation);
				
		if (oExistingDataStructure.getContentType().equals(eContentType.NULLOBJECT)==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTION, false);
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
	
	/**
	 * Removes all actions
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 17:20:48
	 *
	 * @param poMentalSituation
	 */
	public static void removeAction(clsWordPresentationMesh poMentalSituation) {
		clsMeshTools.removeAssociationInObject(poMentalSituation, ePredicate.HASACTION);
	}
	
	public static void setSupportiveDataStructure(clsWordPresentationMesh poMentalSituation, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poMentalSituation);
				
		if (oExistingDataStructure.getContentType().equals(eContentType.NULLOBJECT)==true) {
			clsMeshTools.createAssociationSecondary(poMentalSituation, 1, poDataStructure, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
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
