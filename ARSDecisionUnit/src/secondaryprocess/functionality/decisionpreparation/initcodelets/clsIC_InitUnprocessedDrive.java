/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
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
 * 23.05.2013, 22:38:24
 * 
 */
public class clsIC_InitUnprocessedDrive extends clsInitCodelet {

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 23.05.2013 22:40:14
     *
     * @param poCodeletHandler
     */
    public clsIC_InitUnprocessedDrive(clsCodeletHandler poCodeletHandler) {
        super(poCodeletHandler);
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:40:17
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
     */
    @Override
    protected void processGoal() {
        //moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
        //moGoal.setCondition(eCondition.IS_NEW_GOAL);
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:40:17
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_NEW_GOAL, eCondition.IS_DRIVE_SOURCE));
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:40:17
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
     */
    @Override
    protected void setPostConditions() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:40:17
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
     */
    @Override
    protected void setDescription() {
        // TODO (wendt) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:40:17
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
     */
    @Override
    protected void removeTriggerCondition() throws ElementNotFoundException {
        //this.moGoal.removeCondition(eCondition.IS_UNPROCESSED_GOAL);
        
    }

}
