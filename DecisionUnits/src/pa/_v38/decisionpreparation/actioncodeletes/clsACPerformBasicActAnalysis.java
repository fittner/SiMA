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

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 12:02:42
 * 
 */
public class clsACPerformBasicActAnalysis extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 26.09.2012 12:02:57
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsACPerformBasicActAnalysis(
			clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
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
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.NEED_BASIC_ACT_ANALYSIS, eCondition.NEED_INTERNAL_INFO_SET));
		
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
	 * @since 26.09.2012 12:03:00
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = "PERFORM_BASIC_ACT_ANALYSIS";
		
	}

}
