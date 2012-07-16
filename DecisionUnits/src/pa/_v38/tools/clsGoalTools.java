/**
 * CHANGELOG
 *
 * 26.03.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.03.2012, 20:45:57
 * 
 */
public class clsGoalTools {

	private final static clsWordPresentationMesh moNullObjectWPM = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMesh getNullObjectWPM() {
		return moNullObjectWPM;
	}
	
	
	public static clsWordPresentationMesh createGoal(String poGoalContent, eGoalType poGoalType, eAffectLevel poAffectLevel, clsWordPresentationMesh poGoalObject, clsWordPresentationMesh poSupportiveDataStructure) {
		//Create identifiyer. All goals must have the content type "GOAL"
		clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.GOAL);
		
		//Create the basic goal structure
		clsWordPresentationMesh oRetVal = new clsWordPresentationMesh(oDataStructureIdentifier, new ArrayList<clsAssociation>(), poGoalContent);
		
		//Create a WP for the affectlevel
		clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier2 = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WP, eContentType.AFFECTLEVEL);
		clsWordPresentation oAffectLevelWP = new clsWordPresentation(oDataStructureIdentifier2, poAffectLevel.toString());
		
		//Add WP to the mesh
		clsMeshTools.createAssociationSecondary(oRetVal, 1, oAffectLevelWP, 0, 1.0, eContentType.AFFECTLEVEL, ePredicate.HASAFFECTLEVEL, false);
		
		//Add Goalobject to the mesh
		if (poGoalObject != null) {
			clsMeshTools.createAssociationSecondary(oRetVal, 1, poGoalObject, 0, 1.0, eContentType.DRIVEOBJECTASSOCIATION, ePredicate.HASDRIVEOBJECT, false);	
		}
		
		//Add Supportive Data Structure to goal
		if (poSupportiveDataStructure != null) {
			clsMeshTools.createAssociationSecondary(oRetVal, 1, poSupportiveDataStructure, 0, 1.0, eContentType.SUPPORTDSASSOCIATION, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
		}
		
		//Add goal type to mesh
		clsMeshTools.setWP(oRetVal, eContentType.GOALTYPEASSOCIATION, ePredicate.HASGOALTYPE, eContentType.GOALTYPE, poGoalType.toString());
		
		return oRetVal;
	}
	
	/**
	 * Get the drive object from a goal mesh
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:22:03
	 *
	 * @param poGoal
	 * @return
	 */
	public static clsWordPresentationMesh getGoalObject(clsWordPresentationMesh poGoal) {
		clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASDRIVEOBJECT, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the affectlevel from a goal
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:25:11
	 *
	 * @param poGoal
	 * @return
	 */
	public static eAffectLevel getAffectLevel(clsWordPresentationMesh poGoal) {
		eAffectLevel oRetVal = null;
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASAFFECTLEVEL, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = eAffectLevel.valueOf(oFoundStructures.get(0).getMoContent());
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the goal content
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:29:45
	 *
	 * @param poGoal
	 * @return
	 */
	public static String getGoalContent(clsWordPresentationMesh poGoal) {
		String oRetVal = poGoal.getMoContent();
		
		return oRetVal;
	}
	
	/**
	 * Get the supportive data structures from a goal mesh
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:22:03
	 *
	 * @param poGoal
	 * @return
	 */
	public static clsWordPresentationMesh getSupportiveDataStructure(clsWordPresentationMesh poGoal) {
		clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
		
		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTURE, true);
		
		if (oFoundStructures.isEmpty()==false) {
			//The drive object is always a WPM
			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
		}
		
		return oRetVal;
	}
	
	public static void setSupportiveDataStructure(clsWordPresentationMesh poGoal, clsWordPresentationMesh poDataStructure) {
		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poGoal);
		
		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT)==true) {
			clsMeshTools.createAssociationSecondary(poGoal, 1, poDataStructure, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
		} else {
			//Get the association
			clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poGoal, ePredicate.HASSUPPORTIVEDATASTRUCTURE, 0, true);
			oAss.setLeafElement(poDataStructure);
		}
		
	}
	
	/**
	 * If there is no supportive datastructure, a data structure can be created from a single entity
	 * 
	 * (wendt)
	 *
	 * @since 10.07.2012 11:02:49
	 *
	 * @param poGoal: Goal
	 * @param poEntity: Entity, which shall be added to an image
	 */
	public static void createSupportiveDataStructureFromEntity(clsWordPresentationMesh poGoal, clsWordPresentationMesh poEntity) {
		//Create Image from entity
		clsWordPresentationMesh oImageFromEntity = clsMeshTools.createImageFromEntity(poEntity);
		
		clsGoalTools.setSupportiveDataStructure(poGoal, oImageFromEntity);
	}
	
	/**
	 * Get the goal type of a certain goal
	 * 
	 * (wendt)
	 *
	 * @since 24.06.2012 09:21:09
	 *
	 * @param poGoal
	 * @return
	 */
	public static eGoalType getGoalType(clsWordPresentationMesh poGoal) {
		eGoalType oRetVal = null;
		try {
			oRetVal = eGoalType.valueOf(((clsWordPresentation)clsMeshTools.searchFirstDataStructureOverAssociationWPM(poGoal, ePredicate.HASGOALTYPE, 2, false)).getMoContent());
		} catch (Exception e) {
			System.err.println("No valid goaltype was found.");
			e.printStackTrace();
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the content type of the support data structure type of the goal
	 * 
	 * (wendt)
	 *
	 * @since 19.06.2012 22:07:50
	 *
	 * @param poGoal
	 * @return
	 */
	public static eContentType getSupportDataStructureType(clsWordPresentationMesh poGoal) {
		eContentType oRetVal = null;
	
		if (clsGoalTools.getSupportiveDataStructure(poGoal)!=null) {
			oRetVal = clsGoalTools.getSupportiveDataStructure(poGoal).getMoContentType();
		}
		
		
		return oRetVal;
	}
	
	/**
	 * Extract all possible goals from an image, sorted
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 15:42:20
	 *
	 * @param poImage
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> extractPossibleGoals(clsWordPresentationMesh poImage) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		//Get all possibly reachable drivegoals
		oRetVal = clsImportanceTools.getWPMDriveGoals(poImage, true);
				
		return oRetVal;
	}
	
	/**
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 18:04:40
	 *
	 * @param poSortedPossibleGoalList
	 * @param poSortedFilterList
	 * @param pnNumberOfGoalsToPass
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> sortAndEnhanceGoals(
			ArrayList<clsWordPresentationMesh> poSortedPossibleGoalList,
			ArrayList<clsWordPresentationMesh> poSortedFilterList, 
			//int pnNumberOfGoalsToPass,
			int pnAffectLevelThreshold) {
		
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oPreliminarySortList = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		
		//1. Go through the list of drives, which are used as filter
		for (int i=0; i<poSortedFilterList.size();i++) {
			clsWordPresentationMesh oDriveGoal = poSortedFilterList.get(i);
			
			ArrayList<clsPair<Integer, clsWordPresentationMesh>> oPreliminaryGoalList = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
			
			//Extract all remembered goals from the image, which match the drive goal
			oPreliminaryGoalList.addAll(clsGoalTools.filterDriveGoalsFromImageGoals(oDriveGoal, poSortedPossibleGoalList, pnAffectLevelThreshold));
			
			for (clsPair<Integer, clsWordPresentationMesh> oPair : oPreliminaryGoalList) {
				int nIndex = 0;
				//Increase index if the list is not empty
				while((oPreliminarySortList.isEmpty()==false) && 
						(nIndex<oRetVal.size()) &&
						(oPreliminarySortList.get(nIndex).a > oPair.a)) {
					nIndex++;
				}
				
				oPreliminarySortList.add(nIndex, oPair);
			}
			
			for (clsPair<Integer, clsWordPresentationMesh> oPair : oPreliminaryGoalList) {
				oRetVal.add(oPair.b);
			}
		}	
		
		return oRetVal;
	}
	
	/**
	 * Filter drive goals from image goals image
	 * 
	 * No other goals are extracted. Emotions, which form goals are separately extracted
	 * 
	 * Parameter SortOrder
	 * 0: Lowest order 4: Highest
	 * 
	 * 0: No Drivegoal present in the image
	 * +1: Drive is the same but the drive goal is different
	 * +1: Drivegoal is the same
	 * +1: The goal can be found in the perception
	 * 
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 25.05.2012 18:10:06
	 *
	 * @param poDriveGoal
	 * @param poSortedPossibleGoalList
	 * @param pnAffectLevelThreshold
	 * @return
	 */
	private static ArrayList<clsPair<Integer, clsWordPresentationMesh>> filterDriveGoalsFromImageGoals(clsWordPresentationMesh poDriveGoal, ArrayList<clsWordPresentationMesh> poSortedPossibleGoalList, int pnAffectLevelThreshold) {
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oRetVal = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		boolean bGoalObjectFound = false;
		
		//Find those potential goals, which could fulfill the goal from the drive
		for (clsWordPresentationMesh oPossibleGoal : poSortedPossibleGoalList) {
			
			//Get the level of affect for the object in the image of the potential goals
			int nCurrentAffectLevel = clsGoalTools.getAffectLevel(oPossibleGoal).mnAffectLevel;
			
			if (nCurrentAffectLevel>=pnAffectLevelThreshold) {
				//This is the sort order for the goal and it has to be fulfilled at any time
				
				//If the content is equal
				if (clsGoalTools.getGoalContent(poDriveGoal).equals(clsGoalTools.getGoalContent(oPossibleGoal))) {
					int nCurrentPISortOrder = 1;		//Initialize as the drive content is the same => +1
					
					//Compare drive objects
					if (clsGoalTools.getGoalObject(poDriveGoal).getMoContent().equals(clsGoalTools.getGoalObject(oPossibleGoal).getMoContent())) {
						nCurrentPISortOrder++;	//same drive object => +1
						bGoalObjectFound=true;
					}
					
					//Check if it exists in perception
					if (clsGoalTools.getSupportDataStructureType(oPossibleGoal) == eContentType.PI) {
						nCurrentPISortOrder++;	//Object exists in the perception => +1 because it is nearer
					}

					//Sort goals
					int nTotalCurrentAffectLevel = Math.abs(nCurrentAffectLevel * 10 + nCurrentPISortOrder);
					oRetVal.add(new clsPair<Integer, clsWordPresentationMesh>(nTotalCurrentAffectLevel, oPossibleGoal));
				}
			} 				
		}
		
		//Some goals are important although they are not in the perception. Therefore, the drive goals will be passed
		if (bGoalObjectFound==false && clsGoalTools.getAffectLevel(poDriveGoal).equals(eAffectLevel.LOWPOSITIVE)) {
			//There is no current affect level
			//This sort order shall have the last priority
			
			int nCurrentPISortOrder = 0;
			int nTotalCurrentAffectLevel = Math.abs(0 * 10 + nCurrentPISortOrder);
			oRetVal.add(new clsPair<Integer, clsWordPresentationMesh>(nTotalCurrentAffectLevel, poDriveGoal));
		}
		
		return oRetVal;
	}
	

	
	/**
	 * Filter goals according to the number of elements given by the input
	 * 
	 * (wendt)
	 *
	 * @since 25.05.2012 20:14:21
	 *
	 * @param poInput
	 * @param pnNumberOfGoalsToPass
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> filterGoals(ArrayList<clsWordPresentationMesh> poInput, int pnNumberOfGoalsToPass) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		//Add all goals to this list
		for (clsWordPresentationMesh oReachableGoal : poInput) {
			if (oRetVal.size()<pnNumberOfGoalsToPass) {
				oRetVal.add(oReachableGoal);
			} else {
				break;
			}
		}

		return oRetVal;
	}
}
