/**
 * CHANGELOG
 *
 * 28.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.algorithm.goals.GoalArrangementTools;
import secondaryprocess.algorithm.goals.clsGoalAlgorithmTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 28.05.2013, 23:26:46
 * 
 */
public class clsIC_GetContinuedGoal extends clsInitCodelet{

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 28.05.2013 23:27:06
     *
     * @param poCodeletHandler
     */
    public clsIC_GetContinuedGoal(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);

    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 23:27:12
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
        
        //Get Goallist from F51
        //this.moReachableGoalList;
        
        //Continued goal comes from the STM

        
        //Get the current incoming goal, which correpsonds to the last goal
        clsWordPresentationMeshSelectableGoal oResult = getContinuedGoalFromPreviousGoal(this.moGoal, this.moCodeletHandler.getGoalListFromF51());
        if (oResult.isNullObject()==false) {
            //Goal type is the only condition set
            
            //Set new continued goal
            oResult.setCondition(eCondition.IS_CONTINUED_GOAL);
            
            //Set condition is unprocessed, in order to process the continued goal
            oResult.setCondition(eCondition.IS_UNPROCESSED_GOAL);
        }
        
        this.moGoal = oResult;        
        
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
    private clsWordPresentationMeshSelectableGoal getContinuedGoalFromPreviousGoal(clsWordPresentationMeshSelectableGoal poPreviousGoal, ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList) {
        clsWordPresentationMeshSelectableGoal oResult = clsGoalManipulationTools.getNullObjectWPMSelectiveGoal();
        
        //Check if goal exists in the goal list
        ArrayList<clsWordPresentationMeshSelectableGoal> oEquivalentGoalList = clsGoalManipulationTools.getEquivalentGoalFromGoalList(poGoalList, poPreviousGoal);
        
        //If the goal could not be found
        if (oEquivalentGoalList.isEmpty()==true) {
            //--- COPY PREVIOUS GOAL ---//
            clsWordPresentationMeshSelectableGoal oNewGoalFromPrevious = clsGoalManipulationTools.copyGoalWithoutTaskStatusAndAction(poPreviousGoal);
            
            oResult = oNewGoalFromPrevious;  

        } else {
            //Assign the right spatially nearest goal from the previous goal if the goal is from the perception
            //eCondition oPreviousGoalType = poPreviousGoal.getc.getGoalType();
            
            if (poPreviousGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)==true) {
                oResult = GoalArrangementTools.getSpatiallyNearestGoalFromPerception(oEquivalentGoalList, poPreviousGoal);
            } else {
                oResult = oEquivalentGoalList.get(0);   //drive or memory is always present
            }
            
            //Remove all conditions, in order not to use the init conditions on continued goals
            oResult.removeAllConditions();
            
        }
        
        //This method sets the condition for the goal type from reading the goal.
        try {
            clsGoalAlgorithmTools.setConditionFromGoalType(oResult);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        
        return oResult;
    }
    

    /* (non-Javadoc)
     *
     * @since 28.05.2013 23:27:12
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        //Get the short
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_CONTINUED_GOAL));
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 23:27:12
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 23:27:12
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        this.moCodeletDescription = "Get the last memory from the STM and put it as input for this codelet. This codelet searches the list of incoming goals and picks the continued goal from there if available, else the previous goal is the next continued goal";
        
    }

    /* (non-Javadoc)
     *
     * @since 28.05.2013 23:27:12
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {
        // TODO (wendt) - Auto-generated method stub
        
    }

}
