/**
 * CHANGELOG
 *
 * 26.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.actioncodeletes;

import memorymgmt.enums.eAction;
import memorymgmt.enums.eCondition;
import base.datatypes.clsWordPresentationMesh;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 11:55:49
 * 
 */
public class clsAC_FOCUS_ON extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:57:56
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsAC_FOCUS_ON(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:57:58
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {		
		this.generateAction(eAction.FOCUS_ON);

		//Get the supportive data structure
		clsWordPresentationMesh oSupportiveDataStructure = this.moGoal.getSupportiveDataStructure();
		
		//Associate this structure with the action
		clsActionTools.setSupportiveDataStructureForAction(this.moAction, oSupportiveDataStructure);
		
		
		setActionAssociationInGoal();
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:57:58
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		//eCondition.NEED_GOAL_FOCUS
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.NEED_GOAL_FOCUS));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 26.09.2012 11:57:58
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:21:43
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
		//Update goal status - remove the conditions to execute this codelet
	    try {
            //this.moGoal.removeCondition(eCondition.NEED_GOAL_FOCUS);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:06:31
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Focus on an entity.";
		
	}

}
