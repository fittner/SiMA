/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import java.util.ArrayList;

import memorymgmt.enums.eCondition;
import base.datatypes.clsWordPresentationMesh;
import base.tools.ElementNotFoundException;
import secondaryprocess.algorithm.acts.clsActPreparationTools;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.05.2013, 22:39:06
 * 
 */
public class clsIC_InitUnprocessedAct extends clsInitCodelet {
    
    

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 23.05.2013 22:39:46
     *
     * @param poCodeletHandler
     */
    public clsIC_InitUnprocessedAct(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:39:49
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
     // --- Check the conditions in the intention --- //
        //Get the intention
        clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(moGoal.getSupportiveDataStructure());
           
        ArrayList<eCondition> conditionList = clsActPreparationTools.initActInGoal(oIntention);
        
        for (eCondition c: conditionList) {
            this.moGoal.setCondition(c);
        }
        
//        double rCurrentImageMatch = 0.0;
//        
//        //If the act has to start with the first image:
//        if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true) {
//            //Cases:
//            //1. If the first image has match 1.0 and there is no first act ||
//            //2. If the this act is the same as from the previous goal -> start this act as normal
//            //else set GOAL_CONDITION_BAD
//            clsWordPresentationMesh oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
//            rCurrentImageMatch = clsActTools.getPIMatch(oFirstImage);
//            
//        } else {
//            //Get best match from an intention
//            clsWordPresentationMesh oBestMatchEvent = clsActTools.getHighestPIMatchFromSubImages(oIntention);
//            rCurrentImageMatch = clsActTools.getPIMatch(oBestMatchEvent);
//        }
//        
//        if (rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
//            moGoal.setCondition(eCondition.ACT_MATCH_TOO_LOW);
//        } 
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:39:49
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_NEW_GOAL, eCondition.IS_MEMORY_SOURCE));
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:39:49
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:39:49
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:39:49
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {
        
    }

}
