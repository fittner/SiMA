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
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eTaskStatus;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 06.07.2012, 13:47:41
 * 
 */
public class clsActionTools {
	
	private final static clsWordPresentationMesh moNullObjectWPM = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMesh getNullObjectWPM() {
		return moNullObjectWPM;
	}
	
	public static clsWordPresentationMesh createAction(String poAction) {
		return clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ACTION, poAction), new ArrayList<clsAssociation>());
	}
	
//	public static clsWordPresentationMesh getSupportiveDataStructureFromHashCode(clsWordPresentationMesh poAction, clsShortTermMemory poSTM) {
//		clsWordPresentationMesh oRetVal = getNullObjectWPM();
//			
//		//Get the Hashcode
//		clsWordPresentationMesh oHashCodeMesh = getSupportiveDataStructure(poAction);
//		if (oHashCodeMesh.getMoContent().equals(eContentType.NULLOBJECT.toString())==false) {
//			int nHashCode = Integer.valueOf(getSupportiveDataStructure(poAction).getMoContent());
//			
//			clsWordPresentationMesh oThisSituation = poSTM.findCurrentSingleMemory();
//			ArrayList<clsPair<eContentType, String>> oFilter = new ArrayList<clsPair<eContentType, String>>();
//			oFilter.add(new clsPair<eContentType, String>(eContentType.NOTHING,""));
//			ArrayList<clsDataStructurePA> oCompleteMentalSituation = clsMeshTools.getDataStructureInWPM(oThisSituation, eDataType.WPM, oFilter, false, 5);
//			
//			for (clsDataStructurePA oWPM : oCompleteMentalSituation) {
//				if (oWPM.hashCode()==nHashCode) {
//					oRetVal = (clsWordPresentationMesh) oWPM;
//					break;
//				}
//			}
//		}
//				
//		return oRetVal;
//	}
	
//	public static void setSupportiveDataStructureHashCode(clsWordPresentationMesh poAction, clsWordPresentationMesh poDataStructure) {
//		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poAction);
//		
//		clsWordPresentationMesh oWPMRef = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.SUPPORTIVEDATASTRUCTURE, String.valueOf(poDataStructure.hashCode())), new ArrayList<clsAssociation>());
//		
//		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT.toString())==true) {
//			clsMeshTools.createAssociationSecondary(poAction, 1, oWPMRef, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
//		} else {
//			oExistingDataStructure = oWPMRef;
//		}
//		
//	}
	
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
		clsAssociationSecondary oExistingDataStructure = getSupportiveDataStructureAssociation(poAction);
				
		if (oExistingDataStructure==null) {
			clsMeshTools.createAssociationSecondary(poAction, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, false);
		} else {
			oExistingDataStructure.setLeafElement(poDataStructure);
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
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poAction.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
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
	public static clsAssociationSecondary getSupportiveDataStructureAssociation(clsWordPresentationMesh poAction) {
		clsAssociationSecondary oRetVal = null;
		clsDataStructurePA oAss = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poAction, ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, 0, true);
		
		if (oAss!=null) {
			oRetVal = (clsAssociationSecondary) oAss;
		}
		
		return oRetVal;
	}
	
	/**
	 * Set phantasyflag
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:30:09
	 *
	 * @param poAction
	 */
	public static void setPhantasyFlag(clsWordPresentationMesh poAction) {
		clsMeshTools.setUniquePredicateWP(poAction, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPHANTASYFLAG, eContentType.PHANTASYFLAG, eContent.TRUE.toString());
	}
	
	/**
	 * Get the phantasyflag if any is set
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:29:51
	 *
	 * @param poAction
	 * @return
	 */
	public static boolean getPhantasyFlag(clsWordPresentationMesh poAction) {
		boolean oResult = false;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poAction, ePredicate.HASPHANTASYFLAG);
		
		if (oWP!=null) {
			if (oWP.getMoContent().equals(eContent.TRUE.toString())) {
				oResult = true;
			}
		}
		
		return oResult;
	}
	
	/**
	 * Get Preconditions
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 20:59:12
	 *
	 * @param poAction
	 * @return
	 */
	public static ArrayList<eTaskStatus> getPreconditions(clsWordPresentationMesh poAction) {
		ArrayList<eTaskStatus> oResult = new ArrayList<eTaskStatus>();
		
		ArrayList<clsWordPresentation> oWPList = clsMeshTools.getNonUniquePredicateWP(poAction, ePredicate.HASPRECONDITION);
		
		for (clsWordPresentation oWP : oWPList) {
			oResult.add(eTaskStatus.valueOf(oWP.getMoContent()));
		}
		
		return oResult;
	}
	
	/**
	 * Check if a certain action has a certain precondition
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 21:02:37
	 *
	 * @param poAction
	 * @param poDecisionTask
	 * @return
	 */
	public static boolean checkIfPreconditionExists(clsWordPresentationMesh poAction, ArrayList<eTaskStatus> poTaskStatusList) {
		boolean bResult = true;
		
		if (poTaskStatusList.isEmpty()==true) {
			bResult=false;
		} else {
			ArrayList<eTaskStatus> oPreconditionList = clsActionTools.getPreconditions(poAction);
		
			//Check if ALL preconditions can be matched
			for (eTaskStatus oTaskStatus : poTaskStatusList) {
				if (oPreconditionList.contains(oTaskStatus)==false) {
					bResult = false;
					break;
				}
			}
		}
		
		
		return bResult;
	}
	
}
