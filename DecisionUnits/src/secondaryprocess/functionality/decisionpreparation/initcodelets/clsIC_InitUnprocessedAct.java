/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.initcodelets;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
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
    
    private final double P_ACTMATCHACTIVATIONTHRESHOLD = 1.0;

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
        
        //Check if the previous act is the same as this one. Only then, the act match can be too low
        //boolean bSameAct = clsActPreparationTools.checkIfPreviousActIsEqualToCurrentAct(poContinuedGoal, poGoal);
        //eAction oPreviousAction = eAction.valueOf(clsActionTools.getAction(this.moShortTermMemory.findPreviousSingleMemory().getPlanGoal().getAssociatedPlanAction())); //clsMentalSituationTools.getAction(this.moShortTermMemory.findPreviousSingleMemory())));
        double rCurrentImageMatch = 0.0;
        
        //If the act has to start with the first image:
        if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true) {
            //Cases:
            //1. If the first image has match 1.0 and there is no first act ||
            //2. If the this act is the same as from the previous goal -> start this act as normal
            //else set GOAL_CONDITION_BAD
            clsWordPresentationMesh oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
            rCurrentImageMatch = clsActTools.getPIMatch(oFirstImage);
            
        } else {
            //Get best match from an intention
            clsWordPresentationMesh oBestMatchEvent = clsActTools.getHighestPIMatchFromSubImages(oIntention);
            rCurrentImageMatch = clsActTools.getPIMatch(oBestMatchEvent);
        }
        
        //if (bSameAct==true && rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
        //if (oPreviousAction.equals(eAction.SEND_TO_PHANTASY)==false && rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
        if (rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
            moGoal.setCondition(eCondition.ACT_MATCH_TOO_LOW);
        } else {
            //Set the need to perform a basic act recognition analysis
            moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
        }
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:39:49
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_UNPROCESSED_GOAL, eCondition.IS_MEMORY_SOURCE));
        
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
        this.moGoal.removeCondition(eCondition.IS_UNPROCESSED_GOAL);
        
    }

}
