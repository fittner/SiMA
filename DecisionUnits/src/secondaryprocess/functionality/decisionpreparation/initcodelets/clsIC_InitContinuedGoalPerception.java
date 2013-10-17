/**
 * CHANGELOG
 *
 * 28.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 28.05.2013, 12:49:15
 * 
 */
public class clsIC_InitContinuedGoalPerception extends clsInitCodelet {

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 28.05.2013 12:50:43
     *
     * @param poCodeletHandler
     */
    public clsIC_InitContinuedGoalPerception(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:46
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
        //clsWordPresentationMeshGoal oPreviousGoal = clsCommonCodeletTools.getPreviousCorrespondingGoalFromShortTermMemory(moShortTermMemory, moGoal);
        
//        if (oPreviousGoal.checkIfConditionExists(eCondition.SET_FOCUS_MOVEMENT)==true) {
//            this.moGoal.setCondition(eCondition.SET_FOCUS_MOVEMENT);
//        }
//        if (oPreviousGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
//            this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
//        } else {
//            if (oPreviousGoal.checkIfConditionExists(eCondition.COMPOSED_CODELET)==true) {
//                this.moGoal.setCondition(eCondition.COMPOSED_CODELET);
//            }
//            if (oPreviousGoal.checkIfConditionExists(eCondition.GOTO_GOAL_IN_PERCEPTION)==true) {
//                this.moGoal.setCondition(eCondition.GOTO_GOAL_IN_PERCEPTION);
//            }
//        }
        
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:46
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_CONTINUED_GOAL, eCondition.IS_PERCEPTIONAL_SOURCE));
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:46
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:46
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        this.moCodeletDescription = "Default initial anaylsis of continued goal from the last turn if it is a goal from the perception.";
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:46
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {
        
        
    }

}
