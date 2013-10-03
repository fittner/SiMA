/**
 * CHANGELOG
 *
 * 02.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eActionType;
import secondaryprocess.algorithm.planning.ActionPlanAlgorithm;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.functionality.decisionpreparation.clsDecisionEngine;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.10.2013, 10:57:45
 * 
 */
public class PlanningFunctionality {
    
    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    
    public static void generatePlanForGoals(clsDecisionEngine decisionEngine, ArrayList<clsWordPresentationMeshSelectableGoal> goalList) {
        
        for (clsWordPresentationMeshSelectableGoal goal : goalList) {
            // --- SET NEW PRECONDITIONS FOR ACTIONS --- //
            //Decision codelets
            decisionEngine.generateDecision(goal);
            log.info("Generated decisions: {}", goal);
            
            //Generate plans based on decision codelets
            decisionEngine.generatePlanFromDecision(goal);
            log.info("Generate plan based on decision: {}", goal);
        }
    }
    
    /**
     * Get action command from plan goal.
     *
     * @author wendt
     * @since 03.10.2013 13:02:07
     *
     * @param planGoal
     * @return
     * @throws Exception
     */
    public static clsWordPresentationMesh getActionCommandFromPlanGoal(clsWordPresentationMeshSelectableGoal planGoal) throws Exception {
        try {
            if (planGoal.isNullObject()==true) {
                throw new Exception("planGoal is Nullobject. The agent always have to do something");
            }
        } catch (Exception e) {
            log.error("Goal is nullobject", e);
            throw new Exception(e.getMessage());
        }
        
        //Extract action from goal
        clsWordPresentationMesh actionCommand = planGoal.getAssociatedPlanAction();
        if (actionCommand.isNullObject()==true) {
            actionCommand = clsActionTools.createAction(eAction.NONE);
            log.trace("The selected action was a null object and therefore the action NONE was selected.");
        }
        
        return actionCommand;
    }
    
    /**
     * Get an action of a certain type, internal or external
     *
     * @author wendt
     * @since 03.10.2013 14:41:43
     *
     * @param action
     * @param actionType
     * @return
     */
    public static clsWordPresentationMesh getActionOfType(clsWordPresentationMesh action, eActionType actionType) {
        clsWordPresentationMesh result = ActionPlanAlgorithm.getActionOfType(action, actionType);
        
        return result;
    }
}
