/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsActPreparationTools;
import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsConsequenceCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:38:41
 * 
 */
public class clsCC_PERFORM_BASIC_ACT_ANALYSIS extends clsConsequenceCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 15:39:02
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poReachableGoalList
	 * @param poCodeletHandler
	 */
	public clsCC_PERFORM_BASIC_ACT_ANALYSIS(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		// TODO (wendt) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:39:04
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
		//Get Previous goal
		//clsWordPresentationMesh oPreviousGoal = clsMentalSituationTools.getGoal(this.moShortTermMemory.findPreviousSingleMemory());
		//clsWordPresentationMesh oPreviousAction = clsMentalSituationTools.getAction(this.moShortTermMemory.findPreviousSingleMemory());
		
		//Perform basic act analysis as the act is complete
		clsWordPresentationMesh oCurrentAct = this.moGoal.getSupportiveDataStructure();
		//clsWordPresentationMesh oPreviousAct = clsGoalTools.getSupportiveDataStructure(oPreviousGoal);
		
		//This function shall extract the current moment and the expectation
		ArrayList<eCondition> oTaskStatusList = clsActPreparationTools.performBasicActAnalysis(oCurrentAct, this.moShortTermMemory);
		
		//Check if act analysis failed and remove all status if this is the case
		if (oTaskStatusList.contains(eCondition.GOAL_NOT_REACHABLE)==true) {
		    this.moGoal.removeAllConditions();
		    this.moGoal.setCondition(eCondition.GOAL_NOT_REACHABLE);
		    this.moGoal.setCondition(eCondition.IS_MEMORY_SOURCE);
		} else {
			for (eCondition oTaskStatus : oTaskStatusList) {
			    this.moGoal.setCondition(oTaskStatus);
			}
		}
		
	}
	

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:39:04
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.IS_MEMORY_SOURCE, eCondition.EXECUTED_PERFORM_BASIC_ACT_ANALYSIS));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 15:39:04
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:38:01
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
	    try {
            this.moGoal.removeCondition(eCondition.EXECUTED_PERFORM_BASIC_ACT_ANALYSIS);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:11:43
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Performs basic act analysis";
		
	}

}
