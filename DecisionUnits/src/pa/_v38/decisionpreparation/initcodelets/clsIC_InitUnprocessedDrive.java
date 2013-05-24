/**
 * CHANGELOG
 *
 * 23.05.2013 wendt - File created
 *
 */
package pa._v38.decisionpreparation.initcodelets;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsInitCodelet;
import pa._v38.memorymgmt.enums.eCondition;

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
        moGoal.setCondition(eCondition.NEED_INTERNAL_INFO);
    }

    /* (non-Javadoc)
     *
     * @since 23.05.2013 22:40:17
     * 
     * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
     */
    @Override
    protected void setPreconditions() {
        this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_UNPROCESSED_GOAL, eCondition.IS_DRIVE_SOURCE));
        
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
    protected void removeTriggerCondition() {
        // TODO (wendt) - Auto-generated method stub
        
    }

}
