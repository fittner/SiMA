/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 09:58:07
 * 
 */
public class clsDecisionPreparationTools {

	public static clsWordPresentationMesh preprocessGoals(clsShortTermMemory poSTM, ArrayList<clsWordPresentationMesh> poGoalList) {
		
		//Get previous memory
		clsWordPresentationMesh oPreviousMentalSituation = poSTM.findPreviousSingleMemory();
		
		//--- EXTRACT PREVIOUS GOAL INFO ---//
		//Get the goal
		clsWordPresentationMesh oPreviousGoal = clsMentalSituationTools.getGoal(oPreviousMentalSituation);
		clsLogger.jlog.debug("Previous goal: " + oPreviousGoal);
		//Get the previous action
		clsWordPresentationMesh oPreviousAction = clsMentalSituationTools.getAction(oPreviousMentalSituation);
		clsLogger.jlog.debug("Previous action " + oPreviousAction);
		
		
		
		clsWordPresentationMesh oResult = clsDecisionPreparationTools.initContinuedGoal(oPreviousGoal, poGoalList);
		
		clsDecisionPreparationTools.setDefaultConditionForGoalList(oResult, poGoalList);
		
		clsDecisionPreparationTools.updateGoalConditionWithPreviousAction(oResult, poSTM);
		
		return oResult;
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
	 * @return
	 */
	private static clsWordPresentationMesh initContinuedGoal(clsWordPresentationMesh poPreviousGoal, ArrayList<clsWordPresentationMesh> poGoalList) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		//Check if goal exists in the goal list
		ArrayList<clsWordPresentationMesh> oEquivalentGoalList = clsGoalTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
		
		if (oEquivalentGoalList.isEmpty()==true) {

			
			//----------------------------------//
			
			//--- Remove the temporal data from the last turn ---//
			if (clsGoalTools.getGoalType(poPreviousGoal).equals(eGoalType.MEMORYDRIVE)==true) {
				//--- COPY PREVIOUS GOAL ---//
				clsWordPresentationMesh oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
				
				//Remove all PI-matches from the images
				clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(oNewGoalFromPrevious);
				clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(oSupportiveDataStructure);
				clsActTools.removePIMatchFromWPMAndSubImages(oIntention);
				clsGoalTools.setTaskStatus(oNewGoalFromPrevious, eCondition.IS_NEW_CONTINUED_GOAL);
			
				//Add to goallist
				poGoalList.add(oNewGoalFromPrevious);
				
				oResult = oNewGoalFromPrevious;
			} else if (clsGoalTools.getGoalType(poPreviousGoal).equals(eGoalType.DRIVESOURCE)==true) {			
				clsWordPresentationMesh oNewGoalFromPrevious = clsGoalTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
				clsGoalTools.setTaskStatus(oNewGoalFromPrevious, eCondition.IS_DRIVE_SOURCE);
				clsGoalTools.setTaskStatus(oNewGoalFromPrevious, eCondition.IS_NEW_CONTINUED_GOAL);
				poGoalList.add(oNewGoalFromPrevious);
				
				oResult = oNewGoalFromPrevious;
			}
			


		} else {
			//Assign the right spatially nearest goal from the previous goal if the goal is from the perception
			eGoalType oPreviousGoalType = clsGoalTools.getGoalType(poPreviousGoal);
			
			if (oPreviousGoalType.equals(eGoalType.PERCEPTIONALDRIVE)==true || oPreviousGoalType.equals(eGoalType.PERCEPTIONALEMOTION)==true) {
				oResult = clsGoalTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
			} else {
				oResult = oEquivalentGoalList.get(0);
			}
			
			clsGoalTools.setTaskStatus(oResult, eCondition.IS_NEW_CONTINUED_GOAL);
			
		}
		
		return oResult;
	}
	
	
	/**
	 * Set the default conditions for all goals in the list except the continued goal.
	 * 
	 * (wendt)
	 *
	 * @since 27.09.2012 10:22:03
	 *
	 * @param poContinuedGoal
	 * @param poGoalList
	 */
	private static void setDefaultConditionForGoalList(clsWordPresentationMesh poContinuedGoal, ArrayList<clsWordPresentationMesh> poGoalList) {
		
		for (clsWordPresentationMesh oGoal : poGoalList) {
			if (poContinuedGoal!=oGoal) {
				setDefaultGoalCondition(oGoal);
			}
		}
	}
	

	/**
	 * For a certain goal, depending on the goal type, default start conditions are added. It means that if some continued goal erroneously gets here, the conditions below are added
	 * 
	 * (wendt)
	 *
	 * @since 27.09.2012 10:20:38
	 *
	 * @param poGoal
	 */
	private static void setDefaultGoalCondition(clsWordPresentationMesh poGoal) {
		//All other goals will have a "NEED_FOCUS" or "NEED_INTERNAL_INFO" status
		if (clsGoalTools.getGoalType(poGoal).equals(eGoalType.PERCEPTIONALDRIVE) || clsGoalTools.getGoalType(poGoal).equals(eGoalType.PERCEPTIONALEMOTION)) {
			//Set the NEED_FOCUS for all focus images
			clsGoalTools.setTaskStatus(poGoal, eCondition.NEED_GOAL_FOCUS);
			clsGoalTools.setTaskStatus(poGoal, eCondition.IS_PERCEPTIONAL_SOURCE);
		} else if (clsGoalTools.getGoalType(poGoal).equals(eGoalType.DRIVESOURCE)) {
			//Set the NEED_INTERNAL_INFO, in order to trigger phantasy to activate memories
			clsGoalTools.setTaskStatus(poGoal, eCondition.NEED_INTERNAL_INFO);
			clsGoalTools.setTaskStatus(poGoal, eCondition.IS_DRIVE_SOURCE);
		} else if (clsGoalTools.getGoalType(poGoal).equals(eGoalType.MEMORYEMOTION) || clsGoalTools.getGoalType(poGoal).equals(eGoalType.MEMORYDRIVE)) {
			//Set the need to perform a basic act recognition analysis
			clsGoalTools.setTaskStatus(poGoal, eCondition.NEED_INTERNAL_INFO);
			clsGoalTools.setTaskStatus(poGoal, eCondition.IS_MEMORY_SOURCE);
		}
	}
	
	private static void updateGoalConditionWithPreviousAction(clsWordPresentationMesh poContinuedGoal, clsShortTermMemory poSTM) {
		
	}
}
