/**
 * CHANGELOG
 *
 * 17.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import base.datatypes.clsWordPresentationMesh;
import base.tools.ElementNotFoundException;
import memorymgmt.enums.eCondition;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 17.10.2013, 10:56:24
 * 
 */
public class clsCC_EXECUTE_STATIC_ACTION extends clsConsequenceCodelet {

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 17.10.2013 10:56:42
     *
     * @param poCodeletHandler
     */
    public clsCC_EXECUTE_STATIC_ACTION(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);
    }

    /* (non-Javadoc)
     *
     * @since 17.10.2013 10:56:45
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
        moGoal.setCondition(eCondition.SET_DECISION_PHASE_COMPLETE);
        
      //Remove conditions for the movement
        try {
            
            if (this.moGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)==true) {
                
                clsWordPresentationMesh oAct = this.moGoal.getSupportiveDataStructure();
                clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(oAct);
                
                if (oMoment.isNullObject()==false) {
                    int nTimeOutValue = clsActTools.getMovementTimeoutValue(oMoment);
                    
                    if (nTimeOutValue>0) {
                        nTimeOutValue--;
                        clsActTools.setMovementTimeoutValue(oMoment, nTimeOutValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        
    }

    /* (non-Javadoc)
     *
     * @since 17.10.2013 10:56:45
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_EAT, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_BITE, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_BEAT, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_PICKUP, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_DEPOSIT, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_REPRESS, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_SLEEP, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_RELAX, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_SPEAK_EAT, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_DIVIDE, eCondition.IS_CONTINUED_PLANGOAL));
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.EXECUTED_DROP, eCondition.IS_CONTINUED_PLANGOAL));
    }

    /* (non-Javadoc)
     *
     * @since 17.10.2013 10:56:45
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 17.10.2013 10:56:45
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 17.10.2013 10:56:45
     * 
     * @see secondaryprocess.functionality.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {        
        this.moGoal.removeCondition(eCondition.EXECUTED_EAT);
        this.moGoal.removeCondition(eCondition.EXECUTED_BITE);
        this.moGoal.removeCondition(eCondition.EXECUTED_BEAT);
        this.moGoal.removeCondition(eCondition.EXECUTED_PICKUP);
        this.moGoal.removeCondition(eCondition.EXECUTED_DIVIDE);
        this.moGoal.removeCondition(eCondition.EXECUTED_DROP);
        this.moGoal.removeCondition(eCondition.EXECUTED_DEPOSIT);
        this.moGoal.removeCondition(eCondition.EXECUTED_REPRESS);
        this.moGoal.removeCondition(eCondition.EXECUTED_SLEEP);
        this.moGoal.removeCondition(eCondition.EXECUTED_RELAX);
        this.moGoal.removeCondition(eCondition.EXECUTED_SPEAK_EAT);
        this.moGoal.removeCondition(eCondition.GOTO_GOAL_IN_PERCEPTION);
        
        //Remove all needs
        this.moGoal.removeCondition(eCondition.NEED_PERFORM_RECOMMENDED_ACTION);
        this.moGoal.removeCondition(eCondition.NEED_MOVEMENT);
        this.moGoal.removeCondition(eCondition.NEED_FOCUS_MOVEMENT);
        this.moGoal.removeCondition(eCondition.NEED_GOAL_FOCUS);
        //this.moGoal.removeCondition(eCondition.NEED_INTERNAL_INFO);
        this.moGoal.removeCondition(eCondition.NEED_SEARCH_INFO);
        
        //Remove focus
        this.moGoal.removeCondition(eCondition.SET_FOCUS_ON);
        this.moGoal.removeCondition(eCondition.SET_FOCUS_MOVEMENT);
        
        //If memory source, remove conditions
        this.moGoal.removeCondition(eCondition.SET_BASIC_ACT_ANALYSIS);
        this.moGoal.removeCondition(eCondition.SET_FOLLOW_ACT);
    }

}
