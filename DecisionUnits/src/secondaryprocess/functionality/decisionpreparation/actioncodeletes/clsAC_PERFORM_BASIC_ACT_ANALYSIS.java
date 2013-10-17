/**
 * CHANGELOG
 *
 * 26.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.actioncodeletes;

import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 12:02:42
 * 
 */
public class clsAC_PERFORM_BASIC_ACT_ANALYSIS extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 12:02:57
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsAC_PERFORM_BASIC_ACT_ANALYSIS(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 12:03:00
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		
		this.generateAction(eAction.PERFORM_BASIC_ACT_ANALYSIS);
		
		//Associate the action with the goal
		setActionAssociationInGoal();
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 12:03:00
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.NEED_BASIC_ACT_ANALYSIS, eCondition.SET_INTERNAL_INFO));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 12:03:00
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:18:40
	 * 
	 * @see pa._v38.decisionpreparation.clsActionCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Update goal status - remove the conditions to execute this codelet
	    try {
            //this.moGoal.removeCondition(eCondition.NEED_BASIC_ACT_ANALYSIS);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:06:54
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Perform the internal action to trigger basic act analysis in F51.";
		
	}

}
