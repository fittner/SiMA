/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionmaking;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eGoalType;
import secondaryprocess.functionality.decisionpreparation.clsDecisionEngine;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 22:11:28
 * 
 */
public class GoalProcessingFunctionality {
    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    
    public static void initStatusOfSelectedGoals(clsDecisionEngine decisionEngine, ArrayList<clsWordPresentationMeshSelectableGoal> reducedSelectableGoalList) {
        for (clsWordPresentationMeshSelectableGoal g: reducedSelectableGoalList) {
        
            //Special case if goal is generated
            if (g.getGoalSource().equals(eGoalType.DRIVESOURCE) && g.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)==false) {
                try {
                    decisionEngine.initIncomingGoal(g);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
            
            //Set continued goal condition
            decisionEngine.declareGoalAsContinued(g);
        }
    }
}
