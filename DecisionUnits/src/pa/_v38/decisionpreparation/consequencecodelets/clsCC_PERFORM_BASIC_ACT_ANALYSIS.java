/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import pa._v38.decisionpreparation.clsCodeletHandler;
import pa._v38.decisionpreparation.clsConditionGroup;
import pa._v38.decisionpreparation.clsConsequenceCodelet;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMentalSituationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 15:38:41
 * 
 */
public class clsCC_PERFORM_BASIC_ACT_ANALYSIS extends clsConsequenceCodelet {

	private static final double mrMomentActivationThreshold = 1.0;
	
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
		clsWordPresentationMesh oPreviousGoal = clsMentalSituationTools.getGoal(this.moShortTermMemory.findPreviousSingleMemory());
		
		//Perform basic act analysis as the act is completed
		
		//--- MERGE CONTINUED GOAL WITH INCOMING ACTS ---//
		ArrayList<clsWordPresentationMesh> oGoalWithSameAct = clsGoalTools.getOtherGoalsWithSameSupportiveDataStructure(this.moReachableGoalList, this.moGoal);
		if (oGoalWithSameAct.isEmpty()==false) {
			//Get the act
			clsWordPresentationMesh oNewAct = clsGoalTools.getSupportiveDataStructure(oGoalWithSameAct.get(0));
			clsWordPresentationMesh oCurrentAct = clsGoalTools.getSupportiveDataStructure(this.moGoal);
		
			//Add the PI matches to the images of the act
			transferAllPIMatches(clsActDataStructureTools.getIntention(oNewAct), clsActDataStructureTools.getIntention(oCurrentAct));
		
		}

		
		//-----------------------------------------------//
		
		ArrayList<eCondition> oTaskStatusList = performBasicActAnalysis(clsGoalTools.getSupportiveDataStructure(this.moGoal), clsGoalTools.getSupportiveDataStructure(oPreviousGoal));
		
		//Check if act analysis failed and remove all status if this is the case
		if (oTaskStatusList.contains(eCondition.GOAL_NOT_REACHABLE)==true) {
			clsGoalTools.removeAllConditions(this.moGoal);
			clsGoalTools.setCondition(this.moGoal, eCondition.GOAL_NOT_REACHABLE);
			clsGoalTools.setCondition(this.moGoal, eCondition.IS_MEMORY_SOURCE);
		} else {
			for (eCondition oTaskStatus : oTaskStatusList) {
				clsGoalTools.setCondition(this.moGoal, oTaskStatus);
			}
		}
		
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 25.07.2012 20:00:34
	 *
	 * @param poSourceIntention
	 * @param poTargetIntention
	 */
	private void transferAllPIMatches(clsWordPresentationMesh poSourceIntention, clsWordPresentationMesh poTargetIntention) {
		
		for (clsWordPresentationMesh oS : clsActTools.getAllSubImages(poSourceIntention)) {
			for (clsWordPresentationMesh oT : clsActTools.getAllSubImages(poTargetIntention)) {
				if (oS.getMoDS_ID()==oT.getMoDS_ID()) {
					copyPIMatches(oS, oT);
					
					break;
				}
			}
		}
	}
	
	private void copyPIMatches(clsWordPresentationMesh poSourceImage, clsWordPresentationMesh poTargetImage) {
		//Get PI-Match
		double rPIMatch = clsActTools.getPIMatchFlag(poSourceImage);
		
		//Set PI-Match
		clsActTools.setPIMatch(poTargetImage, rPIMatch);
	}
	
	/**
	 * Perform basic act analysis, i. e. extract moment and expectation from an intention
	 * 
	 * (wendt)
	 *
	 * @since 24.07.2012 22:56:46
	 *
	 * @param poAct
	 * @param poPreviousAct
	 * @return
	 */
	private ArrayList<eCondition> performBasicActAnalysis(clsWordPresentationMesh poAct, clsWordPresentationMesh poPreviousAct) {
		ArrayList<eCondition> oResult = new ArrayList<eCondition>();
		
		//Find the moment in the act
		boolean bMomentExists = analyzeMomentInAct(poAct, poPreviousAct);
		
		boolean bExpectationExists=false;
		if (bMomentExists==true) {
			bExpectationExists = analyzeExpectationInAct(poAct);
		}
		//Find the expectation in act
		

//		//Set all Progress settings to the act
//		//If the act is new, then new progress settings shall be added, else, they shall be updated
//		addTotalProgress(oIntentionMomentExpectationList, poInput, mnConfirmationParts);
//		addAffectReduceValues(oIntentionMomentExpectationList, mrReduceFactorForDrives);
//		

		//Return the recommended action here
		if (bMomentExists==true && bExpectationExists==true) {
			oResult.add(eCondition.SET_BASIC_ACT_ANALYSIS);
		} else {
			oResult.add(eCondition.GOAL_NOT_REACHABLE);
		}

		
		return oResult;
	}
	
	/**
	 * Extract and set the current moment in an act
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 14:01:31
	 *
	 * @param poCurrentAct
	 * @param poPreviousAct
	 */
	private boolean analyzeMomentInAct(clsWordPresentationMesh poCurrentAct, clsWordPresentationMesh poPreviousAct) {
		boolean bResult = false;
		boolean bSetCurrentMomentAsMoment = true;
		
		//Get the image from an act with the highest PI-match according to the primary process and consider the moment activation threshold
		clsWordPresentationMesh oCurrentMoment = clsActDataStructureTools.getMomentWithHighestPIMatch(poCurrentAct, this.mrMomentActivationThreshold);

		//Get previous act
		//clsWordPresentationMesh oPreviousMoment = clsActDataStructureTools.getMoment(poPreviousAct);
		
		//Verify the temporal order
		//A correct moment is either same moment as now or a moment, which is connected somehow with the last moment.
		if (oCurrentMoment.getMoContentType().equals(eContentType.NULLOBJECT)==true) {
			//Break here as no moment was found
			bSetCurrentMomentAsMoment=false;
		}
		
		
		
		if (poPreviousAct.getMoContentType().equals(eContentType.NULLOBJECT)==true) {
			//Break as there is nothing to compare with

			
			
		}
		
		
		
		//If there are no expectations, then this moment was the last moment and nothing from this act should be found any more
		//Do nothing
		
		//If the best match was the saved moment in the short time memory, then return it with forced save
		//If the current moment 
		//TODO AW
		
		//else if the best match is an expectation of the previous moment, then it is ok too
		
		if (bSetCurrentMomentAsMoment==true) {
			clsActDataStructureTools.setMoment(poCurrentAct, oCurrentMoment);
			bResult=true;
		}
		
		return bResult;
		
	}
	
	/**
	 * Extract the expectation from an act
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 14:10:08
	 *
	 * @param poCurrentAct
	 */
	private boolean analyzeExpectationInAct(clsWordPresentationMesh poCurrentAct) {
		boolean bResult = false;
		
		//Extract the expectation
		clsWordPresentationMesh oCurrentExpectation = clsActTools.getNextImage(clsActDataStructureTools.getMoment(poCurrentAct));
		
		//Set the expectation
		if (oCurrentExpectation.getMoContentType().equals(eContentType.NULLOBJECT)==false) {
			clsActDataStructureTools.setExpectation(poCurrentAct, oCurrentExpectation);
			bResult=true;
		}
		
		return bResult;
		
	}
	
	private void performComparableActAnalysis(ArrayList<clsWordPresentationMesh> poGoalList) {

	}
	
	/**
	 * Check if a new act analysis has to be made.
	 * Criteria: The moment has changed or the PI match has changed
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 12:15:41
	 *
	 * @param poCurrentAct
	 * @param poPreviousAct
	 * @return
	 */
	private boolean checkNeedForActAnalysis(clsWordPresentationMesh poCurrentAct, clsWordPresentationMesh poPreviousAct) {
		boolean bResult = false;
		
		//Check if the moment match has changed
		clsWordPresentationMesh oCurrentMoment = clsActDataStructureTools.getMoment(poCurrentAct);
		double oCurrentMatch = clsActTools.getSecondaryMatchValueToPI(oCurrentMoment);
		
		clsWordPresentationMesh oPreviousMoment = clsActDataStructureTools.getMoment(poPreviousAct);
		double oPreviousMatch = clsActTools.getSecondaryMatchValueToPI(oPreviousMoment);

		if (oCurrentMoment.getMoDS_ID()!=oPreviousMoment.getMoDS_ID() || oCurrentMatch!=oPreviousMatch) {
			bResult=true;
		}
		
		return bResult;
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
		clsGoalTools.removeCondition(this.moGoal, eCondition.EXECUTED_PERFORM_BASIC_ACT_ANALYSIS);
	}

}
