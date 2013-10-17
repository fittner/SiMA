/**
 * CHANGELOG
 *
 * 15.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.algorithm.goals.GoalAlgorithmTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 15.10.2013, 14:42:57
 * 
 */
public class clsIC_ApplyPreviousActionOnPlanGoal extends clsInitCodelet {

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 15.10.2013 14:43:12
     *
     * @param poCodeletHandler
     */
    public clsIC_ApplyPreviousActionOnPlanGoal(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);
    }

    /* (non-Javadoc)
     *
     * @since 15.10.2013 14:43:14
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
        //Append the previous action as precondition to trigger consequences
        GoalAlgorithmTools.appendPreviousActionsAsPreconditions(this.moGoal, this.moShortTermMemory);
    }

    /* (non-Javadoc)
     *
     * @since 15.10.2013 14:43:14
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_CONTINUED_PLANGOAL));
        
    }

    /* (non-Javadoc)
     *
     * @since 15.10.2013 14:43:14
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 15.10.2013 14:43:14
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 15.10.2013 14:43:14
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {
        // TODO (wendt) - Auto-generated method stub
        
    }

}
