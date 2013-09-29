/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package system.functionality.decisionpreparation.actioncodeletes;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import system.algorithm.planning.clsTEMPPlannerAW;
import system.datamanipulation.clsActionTools;
import system.functionality.decisionpreparation.clsCodelet;
import system.functionality.decisionpreparation.clsCodeletHandler;

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
	public clsActionCodelet(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		
		 moExternalActionPlanner = clsTEMPPlannerAW.getPlanner();
		
	}
	
	protected void generateAction(eAction poAction) {
		moAction = clsActionTools.createAction(poAction);
	}
	
	protected void setActionAssociationInGoal() {
	    this.moGoal.setAssociatedAction(this.moAction);
		
		//Get the supportive data structure
		//clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(this.moGoal);
				
		//Associate this structure with the action
		//clsActionTools.setSupportiveDataStructure(this.moAction, oSupportiveDataStructure);
	}

	
}
