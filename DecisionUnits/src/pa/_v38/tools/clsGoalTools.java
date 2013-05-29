/**
 * CHANGELOG
 *
 * 26.03.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eActivationType;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.modules.clsModuleBaseKB;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.03.2012, 20:45:57
 * 
 */
public class clsGoalTools {

	private final static clsWordPresentationMeshGoal moNullObjectWPM=null;// new clsWordPresentationMesh(new clsTriple<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	private final static double P_GOALTHRESHOLD = 0.1;
	private static Logger log = Logger.getLogger("pa._v38.tools.clsGoalTools");
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMeshGoal getNullObjectWPM() {
		if (moNullObjectWPM==null) {
		    return new clsWordPresentationMeshGoal(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.NULLOBJECT), new ArrayList<clsAssociation>(), eContentType.NULLOBJECT.toString());
		} else {
		    return moNullObjectWPM;
		}
	    
	}
	
	
	/**
	 * Create a new goal
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 12:55:00
	 *
	 * @param poGoalContent
	 * @param poGoalType
	 * @param poAffectLevel
	 * @param poGoalObject
	 * @param poSupportiveDataStructure
	 * @return
	 */
	public static clsWordPresentationMeshGoal createGoal(String poGoalContent, eGoalType poGoalType, double prQuotaOfAffect, eAction poPreferredAction, ArrayList<clsWordPresentationMeshFeeling> oFeelingsList, clsWordPresentationMesh poGoalObject, clsWordPresentationMesh poSupportiveDataStructure) {
		
		//Generate goalidentifier
		String oGoalID = clsGoalTools.generateGoalContentIdentifier(poGoalContent, poGoalObject, poGoalType);
		
		//--- Create goal ---//
		//Create identifiyer. All goals must have the content type "GOAL"
		clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.GOAL);
		//Create the basic goal structure
		clsWordPresentationMeshGoal oRetVal = new clsWordPresentationMeshGoal(oDataStructureIdentifier, new ArrayList<clsAssociation>(), oGoalID);
		
		//--- Create Affectlevel as importance number ---//
		oRetVal.setImportance(prQuotaOfAffect);
		//clsMeshTools.setUniquePredicateWP(oRetVal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECTLEVEL, eContentType.AFFECTLEVEL, String.valueOf(clsImportanceTools.convertAffectLevelToImportance(poAffectLevel)), true);
		
		//--- Create Goal object ---//
		//Add Goalobject to the mesh
		oRetVal.setGoalObject(poGoalObject);
		//clsMeshTools.createAssociationSecondary(oRetVal, 1, poGoalObject, 0, 1.0, eContentType.DRIVEOBJECTASSOCIATION, ePredicate.HASDRIVEOBJECT, false);	
		
		//--- Add Feelings --- //
		oRetVal.addFeelings(oFeelingsList);
		//clsGoalTools.addFeelings(oRetVal, oFeelingsList);
		
		//Add Supportive Data Structure to goal if it is not null
		oRetVal.setSupportiveDataStructure(poSupportiveDataStructure);
//		if (poSupportiveDataStructure == null) {
//			try {
//				throw new Exception("No nulls allowed");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else if (poSupportiveDataStructure.isNullObject()==false) {
//			clsMeshTools.createAssociationSecondary(oRetVal, 1, poSupportiveDataStructure, 0, 1.0, eContentType.SUPPORTDSASSOCIATION, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
//		}
		
		//--- Add preferred action to the goal --- //
		if (poPreferredAction.equals(eAction.NULLOBJECT)==false) {
			oRetVal.setAssociatedAimAction(clsActionTools.createAction(poPreferredAction));
		    //clsWordPresentationMesh oPreferredActionMesh = clsActionTools.createAction(poPreferredAction);
			//clsMeshTools.createAssociationSecondary(oRetVal, 1, oPreferredActionMesh, 0, 1.0, eContentType.PREFERREDACTION, ePredicate.HASPREFERREDACTION, false);
		}
		
		//--- Add goal type to mesh ---//
		oRetVal.setGoalType(poGoalType);
		//clsMeshTools.setUniquePredicateWP(oRetVal, eContentType.GOALTYPEASSOCIATION, ePredicate.HASGOALTYPE, eContentType.GOALTYPE, poGoalType.toString(), true);
		
		
		//--- Add goal name to mesh ---//
		oRetVal.setGoalName(poGoalContent);
		//clsMeshTools.setUniquePredicateWP(oRetVal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASGOALNAME, eContentType.GOALNAME, poGoalContent, true);
		
		return oRetVal;
	}
	
	
	/**
	 * Copy a goal without task status
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 17:26:16
	 *
	 * @param poGoal
	 * @return
	 */
	public static clsWordPresentationMeshGoal copyGoalWithoutTaskStatusAndAction(clsWordPresentationMeshGoal poGoal) {
		clsWordPresentationMeshGoal oResult = null;
		
		try {
			oResult = (clsWordPresentationMeshGoal) poGoal.clone();
			
			//Remove all task status from the goal
			oResult.removeAllAssociatedAction();
			oResult.removeAllConditions();
			//clsGoalTools.removeAllConditions(oResult);
			//clsGoalTools.removeAllAssociatedAction(oResult);
			
		} catch (CloneNotSupportedException e) {
			System.out.println("previous goal could not be cloned");
			e.printStackTrace();
		}
		
		return oResult;
		
	}
	
	/**
	 * Compare if 2 goals are exactly ident
	 * 
	 * Definition: Same Goal name, goal object, goal type and the same supportive datastructure name.
	 * The Goal identifier is created at the creation of a goal
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 16:13:30
	 *
	 * @param poGoalA
	 * @param poGoalB
	 * @return
	 */
	public static boolean compareGoals(clsWordPresentationMeshGoal poGoalA, clsWordPresentationMeshGoal poGoalB) {
		boolean bResult = false;
		
		if (poGoalA.getMoContent().equals(poGoalB.getMoContent())==true && poGoalA.getSupportiveDataStructure().getMoContent()==poGoalB.getSupportiveDataStructure().getMoContent()) {	//No ID comparison as in some structures the ID would be -1
			bResult=true;
		}
		
		return bResult;
	}
	
//	/**
//	 * Get the drive object from a goal mesh
//	 * 
//	 * (wendt)
//	 *
//	 * @since 26.03.2012 21:22:03
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static clsWordPresentationMesh getGoalObject(clsWordPresentationMesh poGoal) {
//		clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
//		
//		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASDRIVEOBJECT, true);
//		
//		if (oFoundStructures.isEmpty()==false) {
//			//The drive object is always a WPM
//			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
//		}
//		
//		return oRetVal;
//	}
//	
//	   /**
//     * Get the Feelings from a goal
//     * 
//     * (wendt)
//     *
//     * @since 26.03.2012 21:25:11
//     *
//     * @param poGoal
//     * @return
//     */
    public static ArrayList<clsWordPresentationMeshFeeling> getFeelingsFromImage(clsWordPresentationMesh poImage) {
        ArrayList<clsWordPresentationMeshFeeling> oRetVal = new ArrayList<clsWordPresentationMeshFeeling>();
    
        ArrayList<clsWordPresentationMesh> oFeelings = clsMeshTools.getNonUniquePredicateWPM(poImage, ePredicate.HASFEELING);
        
        for (clsWordPresentationMesh oF : oFeelings) {
            oRetVal.add((clsWordPresentationMeshFeeling) oF);
        }
    
        return oRetVal;
    }
//    
//    public static void addFeeling(clsWordPresentationMesh poGoal, clsWordPresentationMeshFeeling poFeeling) {
//        clsMeshTools.setNonUniquePredicateWPM(poGoal, ePredicate.HASFEELING, poFeeling, true);
//    }
//    
//    public static void addFeelings(clsWordPresentationMesh poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeeling) {
//        for (clsWordPresentationMeshFeeling oF : poFeeling) {
//            addFeeling(poGoal, oF);
//        }
//    }
//	
//	/**
//	 * Get the affectlevel from a goal
//	 * 
//	 * (wendt)
//	 *
//	 * @since 26.03.2012 21:25:11
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static int getAffectLevel(clsWordPresentationMesh poGoal) {
//		int oRetVal = 0;
//	
//		clsWordPresentation oAffectLevelWP = clsMeshTools.getUniquePredicateWP(poGoal, ePredicate.HASAFFECTLEVEL);
//		
//		if (oAffectLevelWP==null) {
//			oRetVal = 0;
//		} else {
//			oRetVal = clsImportanceTools.getImportance(oAffectLevelWP);
//		}
//	
//		return oRetVal;
//	}
//	
//	public static void setAffectLevel(clsWordPresentationMesh poGoal, int pnImportance) {
//		clsMeshTools.setUniquePredicateWP(poGoal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECTLEVEL, eContentType.AFFECTLEVEL, String.valueOf(pnImportance), true);
//	}
//	
//	public static int getEffortLevel(clsWordPresentationMesh poGoal) {
//		int oRetVal = 0;
//	
//		clsWordPresentation oEffortLevelWP = clsMeshTools.getUniquePredicateWP(poGoal, ePredicate.HASEFFORTLEVEL);
//		
//		if (oEffortLevelWP==null) {
//			oRetVal = 0;
//		} else {
//			oRetVal = clsImportanceTools.getImportance(oEffortLevelWP);
//		}
//		
//		return oRetVal;
//	}
//	
//	public static void setEffortLevel(clsWordPresentationMesh poGoal, int pnImportance) {
//		clsMeshTools.setUniquePredicateWP(poGoal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASEFFORTLEVEL, eContentType.EFFORTLEVEL, String.valueOf(pnImportance), true);
//	}
//	
//	
//	public static String getGoalContentIdentifier(clsWordPresentationMesh poGoal) {
//		return poGoal.getMoContent();
//	}
	
	public static String generateGoalContentIdentifier(String poGoalName, clsWordPresentationMesh poGoalObject, eGoalType poGoalType) {
		String oResult = "";
		
//		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPosition = clsEntityTools.getPosition(poGoalObject);
//		String oPhiPos = "null";
//		String oRadPos = "null";
		String oPositionToAdd = "";
		
//		if (oPosition.b!=null) {
//			oPhiPos = oPosition.b.toString();
//		}
//		if (oPosition.c!=null) {
//			oRadPos = oPosition.c.toString();
//		}
//		
//		if (oPosition.b!=null || oPosition.c!=null) {
//			oPositionToAdd = "(" + oPhiPos + ":" + oRadPos + ")";
//		}
			
		
		 oResult += poGoalName + ":" + poGoalObject.getMoContent() + oPositionToAdd + ":" + poGoalType.toString();
		 
		 return oResult;
	}
	
	
	
//	/**
//	 * Set task status or replace if it already exists
//	 * 
//	 * (wendt)
//	 *
//	 * @since 17.07.2012 22:00:32
//	 *
//	 * @param poGoal
//	 * @param poTask
//	 */
//	public static void setCondition(clsWordPresentationMesh poGoal, eCondition poTask) {
//		//Get the current one
//		//clsWordPresentation oFoundStructure = clsGoalTools.getDecisionTaskDataStructure(poGoal);
//		
//		//Replace or create new
//		//if (oFoundStructure==null) {
//		clsMeshTools.setNonUniquePredicateWP(poGoal, ePredicate.HASCONDITION, eContentType.CONDITION, poTask.toString(), false);
//		//} else {
//		//	oFoundStructure.setMoContent(poTask.toString());
//		//}
//		
//	}
//	
//	
//	/**
//	 * Get the current decision task
//	 * 
//	 * (wendt)
//	 *
//	 * @since 16.07.2012 16:42:03
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static ArrayList<eCondition> getCondition(clsWordPresentationMesh poGoal) {
//		ArrayList<eCondition> oResult = new ArrayList<eCondition>();
//		
//		ArrayList<clsWordPresentation> oFoundTaskStatusList = clsGoalTools.getConditionDataStructure(poGoal);
//				
//		for (clsWordPresentation oTaskStatus : oFoundTaskStatusList) {
//			oResult.add(eCondition.valueOf(((clsWordPresentation) oTaskStatus).getMoContent()));
//		}
//	
//		
//		return oResult;
//	}
//	
//	/**
//	 * Check if a certain taskstatus exists
//	 * 
//	 * (wendt)
//	 *
//	 * @since 23.07.2012 20:27:12
//	 *
//	 * @param poGoal
//	 * @param poTask
//	 * @return
//	 */
//	public static boolean checkIfConditionExists(clsWordPresentationMesh poGoal, eCondition poTask) {
//		boolean bResult = false;
//		
//		ArrayList<eCondition> oResult = clsGoalTools.getCondition(poGoal);
//		if (oResult.contains(poTask)) {
//			bResult=true;
//		}
//		
//		return bResult;
//	}
//	
//	/**
//	 * Remove a certain task status if it exists in the data structure
//	 * 
//	 * (wendt)
//	 *
//	 * @since 19.07.2012 11:25:29
//	 *
//	 * @param poGoal
//	 * @param poTask
//	 */
//	public static void removeCondition(clsWordPresentationMesh poGoal, eCondition poTask) {
//		ArrayList<clsWordPresentation> oFoundStructureList = clsGoalTools.getConditionDataStructure(poGoal);
//		
//		for (clsWordPresentation oTaskStatus : oFoundStructureList) {
//			if (oTaskStatus.getMoContent().equals(poTask.toString())) {
//				clsMeshTools.removeAssociationInObject(poGoal, oTaskStatus);
//			}
//		}
//	}
//	
//	/**
//	 * Removes all task status in the goal
//	 * 
//	 * (wendt)
//	 *
//	 * @since 23.07.2012 17:24:37
//	 *
//	 * @param poGoal
//	 */
//	public static void removeAllConditions(clsWordPresentationMesh poGoal) {
//		clsMeshTools.removeAssociationInObject(poGoal, ePredicate.HASCONDITION);
//	}
//	
//	/**
//	 * Get the goal name
//	 * 
//	 * "" if there is no goal name
//	 * 
//	 * (wendt)
//	 *
//	 * @since 16.07.2012 16:54:50
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	private static ArrayList<clsWordPresentation> getConditionDataStructure(clsWordPresentationMesh poGoal) {
//		return clsMeshTools.getNonUniquePredicateWP(poGoal, ePredicate.HASCONDITION);
//	}
//	
//	
//	/**
//	 * Set goal name
//	 * 
//	 * (wendt)
//	 *
//	 * @since 23.07.2012 16:04:54
//	 *
//	 * @param poGoal
//	 * @param poName
//	 */
//	public static void setGoalName(clsWordPresentationMesh poGoal, String poName) {
//		clsMeshTools.setUniquePredicateWP(poGoal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASGOALNAME, eContentType.GOALNAME, poName, true);
//	}
//	
//	/**
//	 * Get the goal content
//	 * 
//	 * (wendt)
//	 *
//	 * @since 26.03.2012 21:29:45
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static String getGoalName(clsWordPresentationMesh poGoal) {
//		String oResult = "";
//		
//		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poGoal, ePredicate.HASGOALNAME);
//		
//		if (oWP!=null) {
//			oResult = oWP.getMoContent();
//		}
//		
//		return oResult;
//	} 
//	
//	/**
//	 * Get the supportive data structures from a goal mesh
//	 * 
//	 * (wendt)
//	 *
//	 * @since 26.03.2012 21:22:03
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static clsWordPresentationMesh getSupportiveDataStructure(clsWordPresentationMesh poGoal) {
//		clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
//		
//		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTURE, true);
//		
//		if (oFoundStructures.isEmpty()==false) {
//			//The drive object is always a WPM
//			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
//		}
//		
//		return oRetVal;
//	}
//	
//	/**
//	 * Set the supportive data structure
//	 * 
//	 * (wendt)
//	 *
//	 * @since 16.07.2012 22:17:34
//	 *
//	 * @param poGoal
//	 * @param poDataStructure
//	 */
//	public static void setSupportiveDataStructure(clsWordPresentationMesh poGoal, clsWordPresentationMesh poDataStructure) {
//		clsWordPresentationMesh oExistingDataStructure = getSupportiveDataStructure(poGoal);
//		
//		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT)==true) {
//			clsMeshTools.createAssociationSecondary(poGoal, 1, poDataStructure, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);	//Set ass only in one direction
//		} else {
//			//Get the association
//			clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poGoal, ePredicate.HASSUPPORTIVEDATASTRUCTURE, 0, true);
//			oAss.setLeafElement(poDataStructure);
//		}
//		
//	}
//	
//	/**
//	 * If there is no supportive datastructure, a data structure can be created from a single entity
//	 * 
//	 * (wendt)
//	 *
//	 * @since 10.07.2012 11:02:49
//	 *
//	 * @param poGoal: Goal
//	 * @param poEntity: Entity, which shall be added to an image
//	 */
//	public static void createSupportiveDataStructureFromEntity(clsWordPresentationMesh poGoal, clsWordPresentationMesh poEntity, eContentType poContentType) {
//		//Create Image from entity
//		clsWordPresentationMesh oImageFromEntity = clsMeshTools.createImageFromEntity(poEntity, poContentType);
//		
//		clsGoalTools.setSupportiveDataStructure(poGoal, oImageFromEntity);
//	}
//	
//	/**
//	 * Create the supportive datastructure from the drive object
//	 * 
//	 * (wendt)
//	 *
//	 * @since 16.07.2012 13:27:09
//	 *
//	 * @param poGoal
//	 * @param poContentType
//	 */
//	public static void createSupportiveDataStructureFromGoalObject(clsWordPresentationMesh poGoal, eContentType poContentType) {
//		try {
//			clsWordPresentationMesh oGoalObject = clsGoalTools.getGoalObject(poGoal);
//			
//			createSupportiveDataStructureFromEntity(poGoal, oGoalObject, poContentType);
//			
//		} catch (NullPointerException e) {
//			System.out.println("Error: The goal does not have a valid goal object");
//			e.printStackTrace();
//		}
//	}
//	
//	
////	/**
////	 * Set supportive data structure for actions
////	 * 
////	 * (wendt)
////	 *
////	 * @since 16.07.2012 22:24:32
////	 *
////	 * @param poGoal
////	 * @param poDataStructure
////	 */
////	public static void setSupportiveDataStructureForAction(clsWordPresentationMesh poGoal, clsWordPresentationMesh poDataStructure) {
////		clsWordPresentationMesh oExistingDataStructure = clsGoalTools.getSupportiveDataStructureForAction(poGoal);
////		
////		if (oExistingDataStructure.getMoContentType().equals(eContentType.NULLOBJECT)==true) {
////			clsMeshTools.createAssociationSecondary(poGoal, 1, poDataStructure, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, false);
////		} else {
////			//Get the association
////			clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poGoal, ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, 0, true);
////			oAss.setLeafElement(poDataStructure);
////		}
////		
////	}
//	
//	/**
//	 * Get the supportive data structure for actions
//	 * 
//	 * (wendt)
//	 *
//	 * @since 16.07.2012 22:21:26
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static clsWordPresentationMesh getSupportiveDataStructureForAction(clsWordPresentationMesh poGoal) {
//		clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
//		
//		ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, true);
//		
//		if (oFoundStructures.isEmpty()==false) {
//			//The drive object is always a WPM
//			oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
//		}
//		
//		return oRetVal;
//	}
//	
//	/**
//	 * Get the goal type of a certain goal
//	 * 
//	 * (wendt)
//	 *
//	 * @since 24.06.2012 09:21:09
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static eGoalType getGoalType(clsWordPresentationMesh poGoal) {
//		eGoalType oRetVal = eGoalType.NULLOBJECT;
//
//		clsDataStructurePA oWP = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poGoal, ePredicate.HASGOALTYPE, 2, false);
//		if (oWP!=null) {
//			String oContent = ((clsWordPresentation)clsMeshTools.searchFirstDataStructureOverAssociationWPM(poGoal, ePredicate.HASGOALTYPE, 2, false)).getMoContent();
//			oRetVal = eGoalType.valueOf(oContent);
//		}
//		
//		
//		return oRetVal;
//	}
//	
//	/**
//	 * In the action codelets, actions are associated with the goals. In that way a new action can be attached to a goal and extracted
//	 * 
//	 * (wendt)
//	 *
//	 * @since 26.09.2012 12:17:57
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static clsWordPresentationMesh getAssociatedAction(clsWordPresentationMesh poGoal) {
//		return clsMeshTools.getUniquePredicateWPM(poGoal, ePredicate.HASASSOCIATEDACTION);
//		
//	} 
//	
//	/**
//	 * Set associated action
//	 * 
//	 * (wendt)
//	 *
//	 * @since 26.09.2012 12:20:16
//	 *
//	 * @param poGoal
//	 * @param poAssociatedAction
//	 */
//	public static void setAssociatedAction(clsWordPresentationMesh poGoal, clsWordPresentationMesh poAssociatedAction) {
//		clsMeshTools.setNonUniquePredicateWPM(poGoal, ePredicate.HASASSOCIATEDACTION, poAssociatedAction, false);
//		
//	}
//	
//	public static void removeAllAssociatedAction(clsWordPresentationMesh poGoal) {
//		clsMeshTools.removeAssociationInObject(poGoal, ePredicate.HASASSOCIATEDACTION);
//	}
//	
//	/**
//	 * Get the content type of the support data structure type of the goal
//	 * 
//	 * (wendt)
//	 *
//	 * @since 19.06.2012 22:07:50
//	 *
//	 * @param poGoal
//	 * @return
//	 */
//	public static eContentType getSupportiveDataStructureType(clsWordPresentationMesh poGoal) {
//		eContentType oRetVal = null;
//	
//		if (clsGoalTools.getSupportiveDataStructure(poGoal)!=null) {
//			oRetVal = clsGoalTools.getSupportiveDataStructure(poGoal).getMoContentType();
//		}
//		
//		
//		return oRetVal;
//	}
	
	/**
	 * Extract all possible goals from an perception
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 15:42:20
	 *
	 * @param poImage
	 * @return
	 */
	public static ArrayList<clsWordPresentationMeshGoal> extractPossibleGoalsFromPerception(clsWordPresentationMesh poImage) {
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		//Get all possibly reachable drivegoals
		oRetVal = clsImportanceTools.getDriveGoalsFromWPM(poImage, eGoalType.PERCEPTIONALDRIVE, true);	//Only in one image
		
		return oRetVal;
	}
	
	/**
	 * Extract all possible goals from an act
	 * 
	 * (wendt)
	 *
	 * @since 24.07.2012 22:48:20
	 *
	 * @param poAct
	 * @return
	 */
	public static ArrayList<clsWordPresentationMeshGoal> extractPossibleGoalsFromAct(clsWordPresentationMesh poAct) {
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(poAct);
		
		//Get all possibly reachable drivegoals from the intention
		oRetVal.addAll(clsImportanceTools.getDriveGoalsFromWPM(oIntention, eGoalType.MEMORYDRIVE, poAct, true));	//Only in one image
		
		//Get from all subimages too
//		for (clsWordPresentationMesh oSubImage : clsActTools.getAllSubImages(oIntention)) {
//			oRetVal.addAll(clsImportanceTools.getDriveGoalsFromWPM(oSubImage, eGoalType.MEMORYDRIVE, poAct, true));	//Only in one image
//		}
		//No sub images are used as goals
		
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
	public static ArrayList<clsWordPresentationMeshGoal> sortAndEnhanceGoals(
			ArrayList<clsWordPresentationMeshGoal> poSortedPossibleGoalList,
			ArrayList<clsWordPresentationMeshGoal> poSortedFilterList, 
			//int pnNumberOfGoalsToPass,
			double pnAffectLevelThreshold) {
		
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		
		
		//1. Go through the list of drives, which are used as filter
		for (int i=0; i<poSortedFilterList.size();i++) {
		    ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> oPreliminarySortList = new ArrayList<clsPair<Double, clsWordPresentationMeshGoal>>();
			clsWordPresentationMeshGoal oDriveGoal = poSortedFilterList.get(i);
			
			ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> oPreliminaryGoalList = new ArrayList<clsPair<Double, clsWordPresentationMeshGoal>>();
			
			//Extract all remembered goals from the image, which match the drive goal
			oPreliminaryGoalList.addAll(clsGoalTools.filterDriveGoalsFromImageGoals(oDriveGoal, poSortedPossibleGoalList, pnAffectLevelThreshold));
			log.trace("for drivegoal " + oDriveGoal.getGoalContentIdentifier() + " the following reachable goals were extracted: " + oPreliminaryGoalList);
			
			//Some goals are important although they are not in the perception. Therefore, the drive goals will be passed
			if (oPreliminaryGoalList.isEmpty()==true && oDriveGoal.getImportance()>=P_GOALTHRESHOLD) {
				//There is no current affect level
				//This sort order shall have the last priority
			    
			    //Add as a continous goal
			    //FIXME put this somewhere else
			    //oDriveGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
			    //oDriveGoal.setCondition(eCondition.IS_DRIVE_SOURCE);
			    //oDriveGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
			    
				double rCurrentPISortOrder = 0;
				double rTotalCurrentAffectLevel = Math.abs(0 * 10 + rCurrentPISortOrder);
				oPreliminaryGoalList.add(new clsPair<Double, clsWordPresentationMeshGoal>(rTotalCurrentAffectLevel, oDriveGoal));
			}
			
			for (clsPair<Double, clsWordPresentationMeshGoal> oPair : oPreliminaryGoalList) {
				int nIndex = 0;
				//Increase index if the list is not empty
				while((oPreliminarySortList.isEmpty()==false) && 
						(nIndex<oPreliminarySortList.size()) &&
						(oPreliminarySortList.get(nIndex).a > oPair.a)) {
					nIndex++;
				}
				
				oPreliminarySortList.add(nIndex, oPair);
			}
			
			for (clsPair<Double, clsWordPresentationMeshGoal> oPair : oPreliminarySortList) {
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
	private static ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> filterDriveGoalsFromImageGoals(clsWordPresentationMeshGoal poDriveGoal, ArrayList<clsWordPresentationMeshGoal> poSortedPossibleGoalList, double prAffectLevelThreshold) {
		ArrayList<clsPair<Double, clsWordPresentationMeshGoal>> oRetVal = new ArrayList<clsPair<Double, clsWordPresentationMeshGoal>>();
		
		//boolean bGoalObjectFound = false;
		
		//Find those potential goals, which could fulfill the goal from the drive
		for (clsWordPresentationMeshGoal oPossibleGoal : poSortedPossibleGoalList) {
			
			//Get the level of affect for the object in the image of the potential goals
		    double rImportanceOfFeelings = clsImportanceTools.getConsequencesOfFeelingsOnGoalAsImportance(oPossibleGoal);
			double rCurrentAffectLevel = oPossibleGoal.getImportance() + oPossibleGoal.getEffortLevel() + rImportanceOfFeelings;
			
			if (rCurrentAffectLevel>=prAffectLevelThreshold) {
				//This is the sort order for the goal and it has to be fulfilled at any time
				
				//If the content is equal
				if (poDriveGoal.getGoalName().equals(oPossibleGoal.getGoalName()) && poDriveGoal.getGoalObject().getMoDS_ID()==oPossibleGoal.getGoalObject().getMoDS_ID()) {
					double rCurrentPISortOrder = 0.01;		//Initialize as the drive content is the same => +1
					
					//Compare drive objects
					//FIXME AW: The goal objects are always true!!!! This should be corrected
					if (poDriveGoal.getGoalObject().getMoDS_ID()==oPossibleGoal.getGoalObject().getMoDS_ID()) {
						rCurrentPISortOrder += 0.01;	//same drive object => +1
						//bGoalObjectFound=true;
					}
					
					//Check if it exists in perception
					//if (clsGoalTools.getSupportiveDataStructureType(oPossibleGoal) == eContentType.PI) {
					//	nCurrentPISortOrder++;	//Object exists in the perception => +1 because it is nearer
					//}

					//Sort goals
					double rTotalCurrentAffectLevel = Math.abs(rCurrentAffectLevel * 10 + rCurrentPISortOrder);
					oRetVal.add(new clsPair<Double, clsWordPresentationMeshGoal>(rTotalCurrentAffectLevel, oPossibleGoal));
				}
			} 				
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
	
	/**
	 * Get a list of all goals in a list which are equivalent to the input goal, i. e. same name, object and 
	 * type and the same supportive datastructure name. This function does not care about positions in the images
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 16:21:26
	 *
	 * @param poGoalList
	 * @param poCompareGoal
	 * @return
	 */
	public static ArrayList<clsWordPresentationMeshGoal> getEquivalentGoalFromGoalList(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsWordPresentationMeshGoal poCompareGoal) {
		ArrayList<clsWordPresentationMeshGoal> oResult = new ArrayList<clsWordPresentationMeshGoal>();
		
		for (clsWordPresentationMeshGoal poGoalFromList : poGoalList) {
			//Check if it is the same goal
			if (clsGoalTools.compareGoals(poGoalFromList, poCompareGoal)==true) {
				oResult.add(poGoalFromList);
			}
		}
		
		return oResult;
	}
	
	/**
	 * This function shall be used only on a compare goal from perception and a list of goals only from perception. The function compares
	 * the compare goal with the goals from the goal list and returns the goal from the goal list with the shortest distance to the compare goal
	 * This function shall actually find the previous goal in the new goal list. This is a first implementation of object constance.
	 * 
	 * (wendt)
	 *
	 * @since 09.09.2012 20:51:26
	 *
	 * @param poGoalList
	 * @param poCompareGoal
	 * @return
	 */
	public static clsWordPresentationMeshGoal getSpatiallyNearestGoalFromPerception(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsWordPresentationMeshGoal poCompareGoal) {
		clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
		
		eGoalType oCompareGoalType = poCompareGoal.getGoalType();
		
		if (oCompareGoalType.equals(eGoalType.PERCEPTIONALDRIVE)==false && oCompareGoalType.equals(eGoalType.PERCEPTIONALEMOTION)==false) {
			try {
				throw new Exception("Only Goal type from perception is allowed in this function");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//double rBestDistance = -1;
		
		ArrayList<clsWordPresentationMeshGoal> oZeroDistanceList = sortSpatiallyGoalList(poGoalList, poCompareGoal);
		
		//If the size of the list is >1 then there are several possibilites. Therefore correct the comparegoal with the movement
		if (oZeroDistanceList.size()>1) {
//			if (poLastAction.equals(eAction.TURN_LEFT)) {
//				
//			} else if (poLastAction.equals(eAction.TURN_RIGHT)) {
//				
//			} else if (poLastAction.equals(eAction.MOVE_FORWARD)) {
//				
//			}
			
			//TODO someone: Implement the cases above. At a movement, the goal search shall be corrected.
			
			oResult = oZeroDistanceList.get(0);
			
		} else if (oZeroDistanceList.size()==1){
			oResult = oZeroDistanceList.get(0);
		}
		
		return oResult;
	}
	
	private static ArrayList<clsWordPresentationMeshGoal> sortSpatiallyGoalList(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsWordPresentationMeshGoal poCompareGoal) {
		ArrayList<clsWordPresentationMeshGoal> oResultList = new ArrayList<clsWordPresentationMeshGoal>();
		clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
		
		double rBestDistance = -1;
		
		for (clsWordPresentationMeshGoal oGoal : poGoalList) {
			eGoalType oGoalTypeFromListGoal = oGoal.getGoalType();
			
			if (oGoalTypeFromListGoal.equals(eGoalType.PERCEPTIONALDRIVE) || oGoalTypeFromListGoal.equals(eGoalType.PERCEPTIONALEMOTION)) {
				double rCurrentDistance = clsSecondarySpatialTools.getDistance(oGoal.getGoalObject(), poCompareGoal.getGoalObject());
				
				if ((rBestDistance==-1 && rCurrentDistance>=0) || (rBestDistance>=0 && rCurrentDistance<rBestDistance)) {
					rBestDistance=rCurrentDistance;
					oResult = oGoal;
					
					//If the position is exact the same, then break
					if (rCurrentDistance==0) {
						oResultList.add(oGoal);
					}
				}
			}
		}
		
		if (oResultList.isEmpty()==true) {
			oResultList.add(oResult);
		}
		
		return oResultList;
	}
	

	/**
	 * Compare a goal with a list of goals and if the same supportive data structures are used, then return them. 
	 * 
	 * (wendt)
	 *
	 * @since 14.10.2012 12:20:41
	 *
	 * @param poGoalList: List of goals
	 * @param poCompareGoal: Compare goal, which supportive data structure shall be found in the list
	 * @param pbStopAtFirstMatch: Stop at the first match
	 * @param pbIncludeCurrentGoal: If another instance of the current goal is found, include it in the list
	 * @param pbCompareByInstance: Compare the supportive data structure by instance comparison, else by content
	 * @return
	 */
	public static ArrayList<clsWordPresentationMeshGoal> getOtherGoalsWithSameSupportiveDataStructure(ArrayList<clsWordPresentationMeshGoal> poGoalList, clsWordPresentationMeshGoal poCompareGoal, boolean pbStopAtFirstMatch, boolean pbIncludeCurrentGoal, boolean pbCompareByInstance) {
		ArrayList<clsWordPresentationMeshGoal> oResult = new ArrayList<clsWordPresentationMeshGoal>();
		
		for (clsWordPresentationMeshGoal poGoalFromList : poGoalList) {
			if (pbIncludeCurrentGoal==true) {
				if (pbCompareByInstance==true) {
					if (poGoalFromList.getSupportiveDataStructureType()==poCompareGoal.getSupportiveDataStructureType()) {
						oResult.add(poGoalFromList);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else {
					if (poGoalFromList.getSupportiveDataStructure().getMoContent()==poCompareGoal.getSupportiveDataStructure().getMoContent()) {
						oResult.add(poGoalFromList);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				}
			} else {
				if (pbCompareByInstance==true) {
					if (poGoalFromList.getGoalContentIdentifier()!=poCompareGoal.getGoalContentIdentifier() &&
					        poGoalFromList.getSupportiveDataStructureType()==poCompareGoal.getSupportiveDataStructureType()) {
						oResult.add(poGoalFromList);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else {
					if (poGoalFromList.getGoalContentIdentifier()!=poCompareGoal.getGoalContentIdentifier() &&
					        poGoalFromList.getSupportiveDataStructure().getMoContent()==poCompareGoal.getSupportiveDataStructure().getMoContent()) {
						oResult.add(poGoalFromList);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				}
			}
		}
		return oResult;
	}
	
	/**
	 * Search the whole STM and get all of the same goals.
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 16:35:26
	 *
	 * @param poGoal
	 * @param poSTM
	 * @return
	 */
	public static ArrayList<clsWordPresentationMeshGoal> getAllSameGoalsFromSTM(clsWordPresentationMeshGoal poGoal, clsShortTermMemory poSTM) {
		ArrayList<clsWordPresentationMeshGoal> oResult = new ArrayList<clsWordPresentationMeshGoal>();
		
		ArrayList<clsWordPresentationMesh> oMentalSituationList = poSTM.getAllWPMFromSTM();
		for (clsWordPresentationMesh oMS : oMentalSituationList) {
			clsWordPresentationMeshGoal oGoalFromSTM = clsMentalSituationTools.getGoal(oMS);
			
			if (clsGoalTools.compareGoals(poGoal, oGoalFromSTM)==true) {
				oResult.add(oGoalFromSTM);
			}
		}
		
		return oResult;
	}
	
	/**
	 * Create a goal from a drivemesh
	 * 
	 * (wendt)
	 *
	 * @since 17.05.2013 15:33:16
	 *
	 * @param poDM
	 * @return
	 * @throws Exception 
	 */
	public static clsWordPresentationMeshGoal createGoalFromDriveMesh(clsDriveMesh poDM, clsModuleBaseKB poMemoryAccess) throws Exception {
	    clsWordPresentationMeshGoal oRetVal = clsGoalTools.getNullObjectWPM();
	    
	    if (poDM.getDriveComponent()==null) {
            //Break as there is an error
            throw new Exception("There is no drive component of the DM: " + poDM);
        }
        
        //Convert drive to affect
	    //clsWordPresentation oAffect = clsGoalTools.convertDriveMeshToWP(oPair);
        
        //Get the drive content
        String oDriveContent = poDM.getDriveIdentifier(); //clsImportanceTools.getDriveType(oAffect.getMoContent());
        
        //Get the affect level
        double rImportance = poDM.getQuotaOfAffect();
        //eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(oAffect.getMoContent());
        
        //Get the preferred action name
        String oActionString = poDM.getActualDriveAim().getMoContent();
        eAction oAction = eAction.NULLOBJECT;
        try {
            oAction =  eAction.getAction(oActionString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Convert the object to a WPM
        clsWordPresentationMesh oDriveObject = null;
        clsAssociationWordPresentation oWPforObject = poMemoryAccess.getLongTermMemory().getSecondaryDataStructure(poDM.getActualDriveObject(), 1.0);
        if (oWPforObject!=null) {
            if (oWPforObject.getLeafElement() instanceof clsWordPresentationMesh) {
                oDriveObject = (clsWordPresentationMesh) oWPforObject.getLeafElement();
                oDriveObject.getExternalAssociatedContent().add(oWPforObject);
            }
        }
        
        if ((oDriveContent.equals("")==true) || (oDriveObject==null)) {
            throw new Exception("Drive object is null or drive content is nothing of DM " + poDM.toString());
        }
	    
        oRetVal = clsGoalTools.createGoal(oDriveContent, eGoalType.DRIVESOURCE, rImportance, oAction, new ArrayList<clsWordPresentationMeshFeeling>(), oDriveObject, clsMeshTools.getNullObjectWPM());
        
	    return oRetVal;
	}
	
	/**
	 * Create  a list of goals from drive meshes
	 * 
	 * (wendt)
	 *
	 * @since 17.05.2013 15:42:40
	 *
	 * @param poDMList
	 * @param poMemoryAccess
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<clsWordPresentationMeshGoal> createGoalFromDriveMesh(ArrayList<clsDriveMesh> poDMList, clsModuleBaseKB poMemoryAccess) throws Exception {
	    ArrayList<clsWordPresentationMeshGoal> oResult = new ArrayList<clsWordPresentationMeshGoal>();
	    
	    for (clsDriveMesh poDMElement : poDMList) {
	        oResult.add(clsGoalTools.createGoalFromDriveMesh(poDMElement, poMemoryAccess));
	    }
	    
	    return oResult;
	}
	
	/**
	 * Convert a drivemesh to a goal. This function is used within the perception to create a goal, which is 
	 * then added to the entities.
	 * 
	 * (wendt)
	 *
	 * @since 18.05.2013 21:05:26
	 *
	 * @param poDM
	 * @return
	 */
	public static clsWordPresentationMeshGoal convertDriveMeshPerceptionToGoal(clsDriveMesh poDM) {
	    //clsWordPresentationMeshGoal oResult = clsGoalTools.moNullObjectWPM;
	    
	    //Create the drive string from Drive component, orifice and organ
	    String oGoalName = poDM.getDriveIdentifier(); //oDriveComponent.toString() + oOrgan.toString();
      
	    //     Consider influence of multiple drive-satisfaction on decision making (via affect-level)
	    double rImportance = clsImportanceTools.convertDMIntensityToImportance(poDM.getQuotaOfAffect(), poDM.getActualDriveObject().getCriterionActivationValue(eActivationType.EMBODIMENT_ACTIVATION));
      
	    //eAffectLevel oAffectContent = eAffectLevel.convertActivationAndQoAToAffectLevel(poDM.getQuotaOfAffect(), poDM.getActualDriveObject().getCriterionActivationValue(eActivationType.EMBODIMENT_ACTIVATION));
	    
	    clsWordPresentationMeshGoal oResult = new clsWordPresentationMeshGoal(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.AFFECT), new ArrayList<clsAssociation>(), oGoalName);
	    oResult.setImportance(rImportance);
	    
	    return oResult;
	}
	
//	/**
//	 * Convert a DM into an Affect, which is then converted into a word presentation affect
//	 * (wendt)
//	 *
//	 * @since 30.01.2012 16:30:20
//	 *
//	 * @param poDM
//	 * @return
//	 */
//	public static clsWordPresentation convertDriveMeshToWP(clsDriveMesh poDM) {
//		clsWordPresentation oRetVal = null;
//		
//		//Create the drive string from Drive component, orifice and organ
//		String poGoalName = poDM.getDriveIdentifier(); //oDriveComponent.toString() + oOrgan.toString();
//		
//		// Consider influence of multiple drive-satisfaction on decision making (via affect-level)
//		eAffectLevel oAffectContent = eAffectLevel.convertActivationAndQoAToAffectLevel(poDM.getQuotaOfAffect(), poDM.getActualDriveObject().getCriterionActivationValue(eActivationType.EMBODIMENT_ACTIVATION));
//		
//		//Construct WP
//		String oWPContent = poGoalName + ":" + oAffectContent.toString();
//		
//		//Create the new WP for that drive
//		clsWordPresentation oResWP = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<eContentType, Object>(eContentType.AFFECT, oWPContent));
//		
//		oRetVal = oResWP;
//		
//		return oRetVal;
//	}
	
	public static clsWordPresentationMeshFeeling convertEmotionToFeeling(clsEmotion poEmotion) {
	    //clsWordPresentationMeshFeeling oResult = (clsWordPresentationMeshFeeling) clsMeshTools.getNullObjectWPM();
	    
	    double oAffectContent = poEmotion.getMrEmotionIntensity();
	    eEmotionType oFeelingContent = poEmotion.getMoContent();
	    
	    //Generate feeling
	    clsWordPresentationMeshFeeling oResult = new clsWordPresentationMeshFeeling(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.FEELING), new ArrayList<clsAssociation>(), oFeelingContent.toString());
	    
	    //Set Affect
	    oResult.setIntensity(oAffectContent);
	    //clsMeshTools.setUniquePredicateWP(oResult, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECTLEVEL, eContentType.AFFECTLEVEL, oAffectContent.toString(), false);
	    
	    return oResult;
	}
	
	
	

}
	