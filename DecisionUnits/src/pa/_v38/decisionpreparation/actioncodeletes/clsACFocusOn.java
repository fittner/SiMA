/**
 * CHANGELOG
 *
 * 26.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.actioncodeletes;

import pa._v38.decisionpreparation.clsActionCodelet;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 11:55:49
 * 
 */
public class clsACFocusOn extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 11:57:56
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsACFocusOn(clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
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
		
		//Update goal status - remove the conditions to execute this codelet
		clsGoalTools.removeTaskStatus(this.moGoal, eCondition.NEED_GOAL_FOCUS);
		
		//Get the supportive data structure
		clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
		
		//Associate this structure with the action
		clsActionTools.setSupportiveDataStructure(this.moAction, oSupportiveDataStructure);
		
		
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
	 * @since 26.09.2012 11:57:58
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = this.getClass().getName();
		
	}

}
