/**
 * CHANGELOG
 *
 * 23.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.actioncodeletes;

import pa._v38.decisionpreparation.clsActionCodelet;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsPhantasyTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.09.2012, 13:22:21
 * 
 */
public class clsACSendToPhantasy extends clsActionCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 13:23:20
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsACSendToPhantasy(clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//Generate this action
		this.generateAction(eAction.SEND_TO_PHANTASY);
		
		//Set supportive datastructure from the goal
		if (clsGoalTools.getSupportiveDataStructure(this.moGoal).isNullObject()==true) {
			//Create a supportive data structure
			clsGoalTools.createSupportiveDataStructureFromGoalObject(this.moGoal, eContentType.PHI);
		}
		
		//Set phantasyflag
		try {
			clsPhantasyTools.setPhantasyFlagTrue(clsGoalTools.getSupportiveDataStructure(this.moGoal));
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get the supportive data structure
		clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
		
		//Associate this structure with the action
		clsActionTools.setSupportiveDataStructure(this.moAction, oSupportiveDataStructure);
		
		//Associate the action with the goal
		setActionAssociationInGoal();
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		//NEED_INTERNAL_INFO
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.NEED_INTERNAL_INFO));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 23.09.2012 13:23:23
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setName()
	 */
	@Override
	protected void setName() {
		this.moCodeletName = "SEND_TO_PHANTASY";
		
	}

}
