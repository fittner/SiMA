/**
 * CHANGELOG
 *
 * 26.03.2012 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import general.datamanipulation.ImportanceComparatorWPM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import memorymgmt.enums.eAction;
import memorymgmt.enums.eActivationType;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eGoalType;
import memorymgmt.enums.ePredicate;
import memorymgmt.shorttermmemory.clsShortTermMemory;

import org.apache.log4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBaseKB;
import secondaryprocess.algorithm.goals.GoalArrangementTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.03.2012, 20:45:57
 * 
 */
public class clsGoalManipulationTools {

	private final static clsWordPresentationMeshPossibleGoal moNullObjectWPM=null;// new clsWordPresentationMesh(new clsTriple<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	private final static double P_GOALTHRESHOLD = 0.1;
	private static Logger log = Logger.getLogger("pa._v38.tools.clsGoalTools");
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMeshPossibleGoal getNullObjectWPMSelectiveGoal() {
		if (moNullObjectWPM==null) {
		    return new clsWordPresentationMeshPossibleGoal(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.NULLOBJECT), new ArrayList<clsAssociation>(), eContentType.NULLOBJECT.toString(), null, "", eGoalType.NULLOBJECT, 0);
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
	 * @param poGoalName
	 * @param poGoalType
	 * @param poAffectLevel
	 * @param poGoalObject
	 * @param poSupportiveDataStructure
	 * @return
	 */
	public static clsWordPresentationMeshPossibleGoal createSelectableGoal(String poGoalName, eGoalType poGoalType, double driveDemandImportance, clsWordPresentationMesh poGoalObject) {
		
		//Generate goalidentifier
		String oGoalID = clsGoalManipulationTools.generateGoalContentIdentifier(poGoalName, poGoalObject, poGoalType);
		
		//--- Create goal ---//
		//Create identifiyer. All goals must have the content type "GOAL"
		clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.GOAL);
	
		//Create the basic goal structure
        clsWordPresentationMeshPossibleGoal oRetVal = new clsWordPresentationMeshPossibleGoal(oDataStructureIdentifier, new ArrayList<clsAssociation>(), oGoalID, poGoalObject, poGoalName, poGoalType, driveDemandImportance);
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT - Alternative goal creation method that does not set a drive fullfillment importance
	 *
	 * @author Kollmann
	 * @since 10.09.2014 15:02:21
	 *
	 * @param poGoalName
	 * @param poGoalType
	 * @param poGoalObject
	 * @return
	 */
	public static clsWordPresentationMeshPossibleGoal createSelectableGoal(String poGoalName, eGoalType poGoalType, clsWordPresentationMesh poGoalObject) {
        
        //Generate goalidentifier
        String oGoalID = clsGoalManipulationTools.generateGoalContentIdentifier(poGoalName, poGoalObject, poGoalType);
        
        //--- Create goal ---//
        //Create identifiyer. All goals must have the content type "GOAL"
        clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.GOAL);
    
        //Create the basic goal structure
        clsWordPresentationMeshPossibleGoal oRetVal = new clsWordPresentationMeshPossibleGoal(oDataStructureIdentifier, new ArrayList<clsAssociation>(), oGoalID, poGoalObject, poGoalName, poGoalType);
        
        return oRetVal;
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
    public static clsWordPresentationMeshAimOfDrive createAimOfDrive(String poGoalContent, eGoalType poGoalType, double prQuotaOfAffect, eAction poPreferredAction, clsWordPresentationMesh poGoalObject, clsWordPresentationMesh poSupportiveDataStructure) {
        
        //Generate goalidentifier
        String oGoalID = clsGoalManipulationTools.generateGoalContentIdentifier(poGoalContent, poGoalObject, poGoalType);
        
        //--- Create goal ---//
        //Create identifiyer. All goals must have the content type "GOAL"
        clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.GOAL);
        
        //--- Create Affectlevel as importance number ---//
//        oRetVal.setQuotaOfAffectAsImportance(prQuotaOfAffect);
        //clsMeshTools.setUniquePredicateWP(oRetVal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECTLEVEL, eContentType.AFFECTLEVEL, String.valueOf(clsImportanceTools.convertAffectLevelToImportance(poAffectLevel)), true);
        
        //--- Create Goal object ---//
//        //Add Goalobject to the mesh
//        oRetVal.setGoalObject(poGoalObject);
        //clsMeshTools.createAssociationSecondary(oRetVal, 1, poGoalObject, 0, 1.0, eContentType.DRIVEOBJECTASSOCIATION, ePredicate.HASDRIVEOBJECT, false); 
        

//      if (poSupportiveDataStructure == null) {
//          try {
//              throw new Exception("No nulls allowed");
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
//      } else if (poSupportiveDataStructure.isNullObject()==false) {
//          clsMeshTools.createAssociationSecondary(oRetVal, 1, poSupportiveDataStructure, 0, 1.0, eContentType.SUPPORTDSASSOCIATION, ePredicate.HASSUPPORTIVEDATASTRUCTURE, false);
//      }
        
        //--- Add preferred action to the goal --- //
        clsWordPresentationMesh driveAimAction = clsMeshTools.getNullObjectWPM();
        if (poPreferredAction.equals(eAction.NULLOBJECT)==false) {
            driveAimAction = clsActionTools.createAction(poPreferredAction);
            //clsWordPresentationMesh oPreferredActionMesh = clsActionTools.createAction(poPreferredAction);
            //clsMeshTools.createAssociationSecondary(oRetVal, 1, oPreferredActionMesh, 0, 1.0, eContentType.PREFERREDACTION, ePredicate.HASPREFERREDACTION, false);
        }
        
        //--- Add goal type to mesh ---//
//        oRetVal.setGoalType(poGoalType);
        //clsMeshTools.setUniquePredicateWP(oRetVal, eContentType.GOALTYPEASSOCIATION, ePredicate.HASGOALTYPE, eContentType.GOALTYPE, poGoalType.toString(), true);
        
        
        //--- Add goal name to mesh ---//
//        oRetVal.setGoalName(poGoalContent);
        //clsMeshTools.setUniquePredicateWP(oRetVal, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASGOALNAME, eContentType.GOALNAME, poGoalContent, true);
        
        //Create the basic goal structure
        clsWordPresentationMeshAimOfDrive oRetVal = new clsWordPresentationMeshAimOfDrive(oDataStructureIdentifier, new ArrayList<clsAssociation>(), oGoalID, poGoalObject, poGoalContent, poGoalType, prQuotaOfAffect, driveAimAction);
        
        //Add Supportive Data Structure to goal if it is not null
        oRetVal.setSupportiveDataStructure(poSupportiveDataStructure);
        
        return oRetVal;
    }
    
    /**
     * Convert a drivemesh to a goal. This function is used within the perception to create a goal, which is 
     * then added to the entities.
     * 
     * (wendt)
     *
     * @since 18.05.2013 21:05:26
     *
     * ALGORITHM
     * 
     * @param poDM
     * @return
     */
    public static clsWordPresentationMeshPossibleGoal convertDriveMeshPerceptionToGoal(clsDriveMesh poDM, clsWordPresentationMesh goalObject, eGoalType goalType) {
        //clsWordPresentationMeshGoal oResult = clsGoalTools.moNullObjectWPM;
        
        //Create the drive string from Drive component, orifice and organ
        String oGoalName = poDM.getDriveIdentifier(); //oDriveComponent.toString() + oOrgan.toString();
      
        //     Consider influence of multiple drive-satisfaction on decision making (via affect-level)
        
        //FIXME SSCH: 
        //double rImportance = clsImportanceTools.convertDMIntensityToImportance(poDM.getQuotaOfAffect(), poDM.getActualDriveObject().getCriterionActivationValue(eActivationType.EMBODIMENT_ACTIVATION), 0.1);
        double rImportance = clsImportanceTools.convertDMIntensityToImportance(poDM.getQuotaOfAffect(), 0, 0.1);
        //getGoalObject
        
        
        //eAffectLevel oAffectContent = eAffectLevel.convertActivationAndQoAToAffectLevel(poDM.getQuotaOfAffect(), poDM.getActualDriveObject().getCriterionActivationValue(eActivationType.EMBODIMENT_ACTIVATION));
        
        //String poGoalContent, eGoalType poGoalType, double driveDemandImportance, ArrayList<clsWordPresentationMeshFeeling> oFeelingsList, clsWordPresentationMesh poGoalObject, clsWordPresentationMesh poSupportiveDataStructure
        

        
        clsWordPresentationMeshPossibleGoal oResult = createSelectableGoal(oGoalName, goalType, rImportance, goalObject); //new clsWordPresentationMeshAimOfDrive(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.AFFECT), new ArrayList<clsAssociation>(), oGoalName);
        //oResult.setQuotaOfAffectAsImportance(rImportance);
        
        return oResult;
    }
	
    public static void copyConditions(clsWordPresentationMeshPossibleGoal source, clsWordPresentationMeshPossibleGoal target) {
        for (eCondition c : source.getCondition()) {
            target.setCondition(c);
        }
        
    }
	
	/**
	 * Copy a goal without actions and importance, but with current task status
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 17:26:16
	 *
	 * @param poGoal
	 * @return
	 */
	public static clsWordPresentationMeshPossibleGoal copyBareGoal(clsWordPresentationMeshPossibleGoal poGoal) {
	    clsWordPresentationMeshPossibleGoal oResult = null;
		try {   
			oResult = (clsWordPresentationMeshPossibleGoal) poGoal.clone();
			
			//Remove all task status from the goal
			oResult.removeAllAssociatedAction();
			oResult.removeAllConditions();
			
			//Reset all evaluations
			oResult.removeAllImportance();
			
			
		} catch (CloneNotSupportedException e) {
			log.error("previous goal could not be cloned", e);
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
		
		if (poGoalA.getContent().equals(poGoalB.getContent())==true && poGoalA.getSupportiveDataStructure().getContent()==poGoalB.getSupportiveDataStructure().getContent()) {	//No ID comparison as in some structures the ID would be -1
			bResult=true;
		}
		
		return bResult;
	}

    public static ArrayList<clsWordPresentationMeshFeeling> getFeelingsFromImage(clsWordPresentationMesh poImage) {
        ArrayList<clsWordPresentationMeshFeeling> oRetVal = new ArrayList<clsWordPresentationMeshFeeling>();
    
        //First check if feelings are associated directly to the image
        ArrayList<clsWordPresentationMesh> oFeelings = clsMeshTools.getNonUniquePredicateWPM(poImage, ePredicate.HASFEELING);
        
        //next check if feelings are associated to the SELF in the image, if it has one
        clsWordPresentationMesh oSelf = clsMeshTools.getSELF(poImage);
        if(!(oSelf == null || oSelf.isNullObject())) {
            oFeelings.addAll(clsMeshTools.getNonUniquePredicateWPM(oSelf, ePredicate.HASFEELING));
        }
        
        for (clsWordPresentationMesh oF : oFeelings) {
            oRetVal.add((clsWordPresentationMeshFeeling) oF);
        }
    
        return oRetVal;
    }
	
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
			
		
		 oResult += poGoalName + ":" + poGoalObject.getContent() + oPositionToAdd + ":" + poGoalType.toString();
		 
		 return oResult;
	}
	
	
	
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
	public static ArrayList<clsWordPresentationMeshPossibleGoal> extractPossibleGoalsFromPerception(clsWordPresentationMesh poImage) {
		ArrayList<clsWordPresentationMeshPossibleGoal> oRetVal = new ArrayList<clsWordPresentationMeshPossibleGoal>();
		
		//Get all possibly reachable drivegoals
		try {
            oRetVal = clsImportanceTools.getSelectableGoalsFromWPM(poImage, eGoalType.PERCEPTIONALDRIVE, clsMeshTools.getNullObjectWPM(), true);
        } catch (Exception e) {
            log.error("Could not extract goal from perception", e);
        }	//Only in one image
		
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
	public static ArrayList<clsWordPresentationMeshPossibleGoal> extractPossibleGoalsFromAct(clsWordPresentationMesh poAct) {
		ArrayList<clsWordPresentationMeshPossibleGoal> oRetVal = new ArrayList<clsWordPresentationMeshPossibleGoal>();
		
		clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(poAct);
		
		//Get all possibly reachable drivegoals from the intention
		try {
            oRetVal.addAll(clsImportanceTools.getSelectableGoalsFromWPM(oIntention, eGoalType.MEMORYDRIVE, poAct, true));
        } catch (Exception e) {
            log.error("Could not extract goals from act", e);
        }	//Only in one image
		
		//Get all possible feelinggoals from the act
		//FIXME: Activate goal generation from feelings again
		//oRetVal.addAll(GoalGenerationTools.generateSelectableGoalsFromFeelingsWPM(oIntention, poAct));  //Only in one image
		
		
		//Get from all subimages too
//		for (clsWordPresentationMesh oSubImage : clsActTools.getAllSubImages(oIntention)) {
//			oRetVal.addAll(clsImportanceTools.getDriveGoalsFromWPM(oSubImage, eGoalType.MEMORYDRIVE, poAct, true));	//Only in one image
//		}
		//No sub images are used as goals
		
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
	public static ArrayList<clsWordPresentationMeshPossibleGoal> getEquivalentGoalFromGoalList(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList, clsWordPresentationMeshPossibleGoal poCompareGoal) {
		ArrayList<clsWordPresentationMeshPossibleGoal> oResult = new ArrayList<clsWordPresentationMeshPossibleGoal>();
		
		for (clsWordPresentationMeshPossibleGoal poGoalFromList : poGoalList) {
			//Check if it is the same goal
			if (clsGoalManipulationTools.compareGoals(poGoalFromList, poCompareGoal)==true) {
				oResult.add(poGoalFromList);
			}
		}
		
		return oResult;
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
					if (poGoalFromList.getSupportiveDataStructure().getContent()==poCompareGoal.getSupportiveDataStructure().getContent()) {
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
					        poGoalFromList.getSupportiveDataStructure().getContent()==poCompareGoal.getSupportiveDataStructure().getContent()) {
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
	public static ArrayList<clsWordPresentationMeshGoal> getAllSameGoalsFromSTM(clsWordPresentationMeshGoal poGoal, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM) {
		ArrayList<clsWordPresentationMeshGoal> oResult = new ArrayList<clsWordPresentationMeshGoal>();
		
		ArrayList<clsWordPresentationMeshMentalSituation> oMentalSituationList = poSTM.getAllWPMFromSTM();
		for (clsWordPresentationMeshMentalSituation oMS : oMentalSituationList) {
			clsWordPresentationMeshGoal oGoalFromSTM = oMS.getPlanGoal();
			
			if (clsGoalManipulationTools.compareGoals(poGoal, oGoalFromSTM)==true) {
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
	public static clsWordPresentationMeshAimOfDrive createGoalFromDriveMesh(clsDriveMesh poDM, clsModuleBaseKB poMemoryAccess) throws Exception {
	    //clsWordPresentationMeshAimOfDrive oRetVal = clsGoalTools.getNullObjectWPM();
	    
	    if (poDM.getDriveComponent()==null) {
            //Break as there is an error
            throw new Exception("There is no drive component of the DM: " + poDM);
        }
        
        //Convert drive to affect
	    //clsWordPresentation oAffect = clsGoalTools.convertDriveMeshToWP(oPair);
        
        //Get the drive content
        String oDriveContent = poDM.getDriveIdentifier(); //clsImportanceTools.getDriveType(oAffect.getMoContent());
        
        //String oGoalTypeName = oDriveContent;
        
        //Get the affect level
        //double rImportance = poDM.getQuotaOfAffect();
        double rImportance = clsImportanceTools.convertDMIntensityToImportance(poDM.getQuotaOfAffect(), poDM.getActualDriveObject().getCriterionActivationValue(eActivationType.EMBODIMENT_ACTIVATION), 0);
        
        //eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(oAffect.getMoContent());
        
        //Get the preferred action name
        String oActionString = poDM.getActualDriveAim().getContent();
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
	    
        clsWordPresentationMeshAimOfDrive oRetVal = clsGoalManipulationTools.createAimOfDrive(oDriveContent, eGoalType.DRIVESOURCE, rImportance, oAction, oDriveObject, clsMeshTools.getNullObjectWPM());
        
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
	public static ArrayList<clsWordPresentationMeshAimOfDrive> createGoalFromDriveMesh(ArrayList<clsDriveMesh> poDMList, clsModuleBaseKB poMemoryAccess) throws Exception {
	    ArrayList<clsWordPresentationMeshAimOfDrive> oResult = new ArrayList<clsWordPresentationMeshAimOfDrive>();
	    
	    for (clsDriveMesh poDMElement : poDMList) {
	        oResult.add(clsGoalManipulationTools.createGoalFromDriveMesh(poDMElement, poMemoryAccess));
	    }
	    
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
	    return new clsWordPresentationMeshFeeling(poEmotion);
	}
	
	/**
	 * Sort and filter goal by total importance
	 *
	 * @author wendt
	 * @since 30.09.2013 21:35:24
	 *
	 * @param goalList
	 * @param numberOfElementsToKeep
	 * @return
	 */
	public static <E extends clsWordPresentationMeshGoal> ArrayList<E> sortAndFilterGoalsByTotalImportance(ArrayList<E> goalList, int numberOfElementsToKeep) {
	    ArrayList<E> result = new ArrayList<E>();
	    
	    //Sort the list for importance
	    Collections.sort(goalList, Collections.reverseOrder(new ImportanceComparatorWPM()));
	    HashMap<String, Integer> filter = new HashMap<String, Integer>();
	    for(E goal:goalList)
	    {   String name;
	        name=goal.getSupportiveDataStructure().getContent();
	        int i;
	        if(name != "NULLOBJECT" && name != "ENTITY2IMAGE")
	        {
    	        if(filter.containsKey(name))
    	        {
    	            filter.replace(name,filter.get(name)+1);
    	        }
    	        else
    	        {
    	            filter.put(name,1);
    	        }
    	        if(filter.get(name) < 3)
    	        {
    	            result.add(goal);
    	        }
	        }
	    }
	    
	    //Get the n highest elements
	    if(result.size()==0)
	    {
    	    if (numberOfElementsToKeep>-1 && numberOfElementsToKeep<goalList.size()) {
    //	        filter = selectSuitableReachableGoals
    	        result = new ArrayList<E>(goalList.subList(0, numberOfElementsToKeep));
    	    } else {
    	        result = goalList;
    	    }
	    }
	    
	    return result;
	}
	
	/**
	 * Convert a list of goals to a sortgoallist
	 *
	 * @author wendt
	 * @since 30.09.2013 21:32:23
	 *
	 * @param goalList
	 * @return
	 */
	private static <E extends clsWordPresentationMeshGoal> ArrayList<clsPair<Double, E>> convertGoalListToSortList(ArrayList<E> goalList) {
	    ArrayList<clsPair<Double, E>> result = new ArrayList<clsPair<Double, E>>();
	    
	    for (E g:  goalList) {
	        result.add(new clsPair<Double, E>(g.getTotalImportance(), g));
	    }
	    
	    return result;
	}
	
    /**
     * Map the previous goal with a new goal from the goal list. The new goal is used, but enhanced with info from the previous step. 
     * 
     * (wendt)
     *
     * @since 27.09.2012 10:22:34
     *
     * @param poPreviousGoal
     * @param poGoalList
     * @return the previous continued goal or the continued goal from the incoming goallist
     */
    public static clsWordPresentationMeshPossibleGoal getContinuedGoalFromPreviousGoal(clsWordPresentationMeshPossibleGoal poPreviousGoal, ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
        clsWordPresentationMeshPossibleGoal oResult = clsWordPresentationMeshPossibleGoal.getNullObject();
        
        //Check if goal exists in the goal list
        ArrayList<clsWordPresentationMeshPossibleGoal> oEquivalentGoalList = clsGoalManipulationTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
        
        //If the goal could not be found
        if (oEquivalentGoalList.isEmpty()==true) {
            //--- COPY PREVIOUS GOAL ---//
            if (poPreviousGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==false) {
                clsWordPresentationMeshPossibleGoal oNewGoalFromPrevious = clsGoalManipulationTools.copyBareGoal(poPreviousGoal);
                
                //Clear act if memory source
                if (poPreviousGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
                    //Remove PI-Matches as they do not match anymore
                    clsActTools.removePIMatchFromWPMAndSubImages(oNewGoalFromPrevious.getSupportiveDataStructure());
                }
                
                oResult = oNewGoalFromPrevious;  
            }
        } else {
            //Assign the right spatially nearest goal from the previous goal if the goal is from the perception
            //eCondition oPreviousGoalType = poPreviousGoal.getc.getGoalType();
            
            if (poPreviousGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
                oResult = GoalArrangementTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
            } else {
                oResult = oEquivalentGoalList.get(0);   //drive or memory is always present
            }
        }
        
        //Copy all conditions from previous goal
        clsGoalManipulationTools.copyConditions(poPreviousGoal, oResult);
        
        return oResult;
    }
	

	
	
	
	

}
	