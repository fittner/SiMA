/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshPossibleGoal;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 18:05:27
 * 
 */
public class clsCommonCodeletTools {
	/**
	 * Get previous plan goal
	 *
	 * @author wendt
	 * @since 04.10.2013 12:04:08
	 *
	 * @param poMem
	 * @return
	 */
	public static clsWordPresentationMeshGoal getPreviousPlanGoalFromShortTermMemory(clsShortTermMemory<clsWordPresentationMeshMentalSituation> poMem) {
		return poMem.findPreviousSingleMemory().getPlanGoal(); //clsMentalSituationTools.getGoal(poMem.findPreviousSingleMemory());
			
	}
	
	/**
	 * Get previous selectable goals
	 *
	 * @author wendt
	 * @since 04.10.2013 12:11:00
	 *
	 * @param poMem
	 * @param currentGoal
	 * @return
	 */
	public static clsWordPresentationMeshPossibleGoal getPreviousCorrespondingGoalFromShortTermMemory(clsShortTermMemory<clsWordPresentationMeshMentalSituation> poMem, clsWordPresentationMeshPossibleGoal currentGoal) {
	    ArrayList<clsWordPresentationMeshPossibleGoal> selectableGoalList = poMem.findPreviousSingleMemory().getSelectableGoals();
	    
	    clsWordPresentationMeshPossibleGoal result = clsWordPresentationMeshPossibleGoal.getNullObject();
	    
	    for (clsWordPresentationMeshPossibleGoal goal : selectableGoalList) {
	        if(goal.isEquivalentDataStructure(currentGoal)) {
	            result = goal;
	        }
	    }
	    
	    return result;
	            
	}
	
}
