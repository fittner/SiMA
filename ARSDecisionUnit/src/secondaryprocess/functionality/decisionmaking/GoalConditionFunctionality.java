/**
 * CHANGELOG
 *
 * 17.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionmaking;

import java.util.ArrayList;

import memorymgmt.enums.eCondition;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import secondaryprocess.datamanipulation.SortAndFilterTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 17.10.2013, 14:23:07
 * 
 */
public class GoalConditionFunctionality {
    
    
    /**
     * Set conditions for all goals
     *
     * @author wendt
     * @since 17.10.2013 14:37:58
     *
     * @param allGoals
     */
    public static void setCommonConditionsToGoals(ArrayList<clsWordPresentationMeshPossibleGoal> allGoals) {
        for (clsWordPresentationMeshPossibleGoal goal : allGoals) {
        	if( goal.getSupportiveDataStructure().getContent().contains("FLEE"))
        	{
        		int i=0;
        		i++;
        		if (i >2)
        		{ i=0;;
        		
        		}
        	}
            setCommonConditionsToGoals(goal, allGoals);
        }
    }
    
    /**
     * If one supportive data structure has a certain condition bound to its goal, all other goals with the same supportivbe data structures recieve the same
     *
     * @author wendt
     * @since 17.10.2013 14:34:12
     *
     * @param goal
     * @param allGoals
     */
    public static void setCommonConditionsToGoals(clsWordPresentationMeshPossibleGoal goal, ArrayList<clsWordPresentationMeshPossibleGoal> allGoals) {
        //Get conditions
        ArrayList<eCondition> conditions = goal.getCondition();
        
        ArrayList<eCondition> checkCondition = new ArrayList<eCondition>();
        
        if (conditions.contains(eCondition.GOAL_COMPLETED)==true) {
            checkCondition.add(eCondition.GOAL_COMPLETED);
        }
        
        if (conditions.contains(eCondition.GOAL_NOT_REACHABLE)==true) {
            checkCondition.add(eCondition.GOAL_NOT_REACHABLE);
        }
        
        //Get all goals with same supportive datastructures
        ArrayList<clsWordPresentationMeshPossibleGoal> sameGoals = SortAndFilterTools.getAllGoalsWithSameSupportiveDataStructure(goal, allGoals);
        
        for (eCondition check : checkCondition) {
            for (clsWordPresentationMeshPossibleGoal sameGoal: sameGoals) {
                sameGoal.setCondition(check);
            }
        }
    }
}
