/**
 * CHANGELOG
 *
 * 28.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import memorymgmt.enums.eCondition;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;
import base.tools.ElementNotFoundException;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 28.05.2013, 12:49:58
 * 
 */
public class clsIC_InitContinuedGoalDrive extends clsInitCodelet {

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 28.05.2013 12:50:24
     *
     * @param poCodeletHandler
     */
    public clsIC_InitContinuedGoalDrive(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:34
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
        //clsWordPresentationMeshGoal oPreviousGoal = clsCommonCodeletTools.getPreviousCorrespondingGoalFromShortTermMemory(moShortTermMemory, moGoal);
        
//        //Transfer previous stati in general
//        if (oPreviousGoal.checkIfConditionExists(eCondition.SET_INTERNAL_INFO)==true) {
//            this.moGoal.setCondition(eCondition.SET_INTERNAL_INFO);
//        }
//        if (oPreviousGoal.checkIfConditionExists(eCondition.SET_FOCUS_MOVEMENT)==true) {
//            this.moGoal.setCondition(eCondition.SET_FOCUS_MOVEMENT);
//        }
//        if (oPreviousGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
//            this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
//        }
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:34
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_DRIVE_SOURCE, eCondition.IS_UNPROCESSED_GOAL));
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:34
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:34
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        this.moCodeletDescription = "Default initial anaylsis of continued goal from the last turn if it is a drive goal.";
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 12:50:34
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {
        
        
    }

}
