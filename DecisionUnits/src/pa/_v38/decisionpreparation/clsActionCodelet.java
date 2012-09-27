/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.planning.clsTEMPPlannerAW;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsGoalTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:05:38
 * 
 */
public abstract class clsActionCodelet extends clsCodelet {

	protected clsWordPresentationMesh moAction;
	protected clsTEMPPlannerAW moExternalActionPlanner;
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 23.09.2012 11:55:29
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */
	public clsActionCodelet(clsWordPresentationMesh poEnvironmentalImage,
			clsShortTermMemory poShortTermMemory,
			clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);
		
		 moExternalActionPlanner = clsTEMPPlannerAW.getPlanner();
		
	}
	
	protected void generateAction(eAction poAction) {
		moAction = clsActionTools.createAction(poAction);
	}
	
	protected void setActionAssociationInGoal() {
		clsGoalTools.setAssociatedAction(this.moGoal, this.moAction);
		
		//Get the supportive data structure
		//clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
				
		//Associate this structure with the action
		//clsActionTools.setSupportiveDataStructure(this.moAction, oSupportiveDataStructure);
	}

	
}
