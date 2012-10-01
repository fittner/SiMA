/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.decisionpreparation.actioncodeletes.clsACExecuteExternalAction;
import pa._v38.decisionpreparation.actioncodeletes.clsACFlee;
import pa._v38.decisionpreparation.actioncodeletes.clsACFocusMovement;
import pa._v38.decisionpreparation.actioncodeletes.clsACFocusOn;
import pa._v38.decisionpreparation.actioncodeletes.clsACPerformBasicActAnalysis;
import pa._v38.decisionpreparation.actioncodeletes.clsACSendToPhantasy;
import pa._v38.decisionpreparation.decisioncodelets.clsDCActionFocusMovement;
import pa._v38.decisionpreparation.decisioncodelets.clsDCActionFocusOn;
import pa._v38.decisionpreparation.decisioncodelets.clsDCActionMovement;
import pa._v38.decisionpreparation.decisioncodelets.clsDCActionPerformBasicActAnalysis;
import pa._v38.decisionpreparation.decisioncodelets.clsDCActionSendToPhantasy;
import pa._v38.decisionpreparation.decisioncodelets.clsDCCheckFocusExists;
import pa._v38.decisionpreparation.decisioncodelets.clsDCContinousAnalysis;
import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.modules.clsModuleBase;
import pa._v38.storage.clsEnvironmentalImageMemory;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:06:06
 * 
 */
public class clsCodeletHandler {
	
	private clsWordPresentationMesh moEnvironmentalImage;	//Current environmental image
	private clsEnvironmentalImageMemory moEnvironmentalImageMemory;
	private clsShortTermMemory moShortTermMemory;	//Current STM, in order to get the previous actions
	
	private ArrayList<clsWordPresentationMesh> moGoalListFromF51 = null;
	
	private ArrayList<clsActionCodelet> moActionCodeletList = new ArrayList<clsActionCodelet>();
	private ArrayList<clsDecisionCodelet> moDecisionCodeletList = new ArrayList<clsDecisionCodelet>();
	
	
	public clsCodeletHandler(clsEnvironmentalImageMemory poEnvironmentalImageMemory, clsShortTermMemory poShortTermMemory) {
		//Get the init references
		moEnvironmentalImageMemory=poEnvironmentalImageMemory;
		
		this.moEnvironmentalImage=moEnvironmentalImageMemory.getEnvironmentalImage();
		this.moShortTermMemory=poShortTermMemory;
		
		init();
	}


	/**
	 * @since 23.09.2012 12:10:38
	 * 
	 * @return the moCodeletList
	 */
	public ArrayList<clsActionCodelet> getActionCodeletList() {
		return moActionCodeletList;
	}
	
	public ArrayList<clsDecisionCodelet> getDecisionCodeletList() {
		return moDecisionCodeletList;
	}


	/**
	 * @since 23.09.2012 12:10:38
	 * 
	 * @param moCodeletList the moCodeletList to set
	 */
	public void setActionCodeletList(ArrayList<clsActionCodelet> poActionCodeletList) {
		this.moActionCodeletList = poActionCodeletList;
	}
	
	public void setDecisionCodeletList(ArrayList<clsDecisionCodelet> poDecisionCodeletList) {
		this.moDecisionCodeletList = poDecisionCodeletList;
	}
	
	public void addToCodeletList(clsCodelet poCodelet) {
		if (poCodelet instanceof clsActionCodelet) {
			this.moActionCodeletList.add((clsActionCodelet) poCodelet);
		} else if (poCodelet instanceof clsDecisionCodelet) {
			this.moDecisionCodeletList.add((clsDecisionCodelet) poCodelet);
		}
		
	}
	
	public void initF51(ArrayList<clsWordPresentationMesh> poGoalListFromF51) {
		moGoalListFromF51 = poGoalListFromF51;
	}
	
	public void initF52() {
		
	}
	
	public void init() {
		this.registerCodelets();
	}
	
	private void registerCodelets() {
		//Decision codelets
		clsDCActionFocusMovement oDCActionFocusMovement = new clsDCActionFocusMovement(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51,  this);
		clsLogger.jlog.debug("Codelet registered: " + oDCActionFocusMovement.toString());
		
		clsDCActionFocusOn oDCActionFocusOn = new clsDCActionFocusOn(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51,  this);
		clsLogger.jlog.debug("Codelet registered: " + oDCActionFocusOn.toString());
		
		clsDCActionMovement oDCActionMovement = new clsDCActionMovement(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51,  this);
		clsLogger.jlog.debug("Codelet registered: " + oDCActionMovement.toString());
		
		clsDCActionPerformBasicActAnalysis oDCActionPerformBasicActAnalysis = new clsDCActionPerformBasicActAnalysis(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51,  this);
		clsLogger.jlog.debug("Codelet registered: " + oDCActionPerformBasicActAnalysis.toString());
		
		clsDCActionSendToPhantasy oDCActionSendToPhantasy = new clsDCActionSendToPhantasy(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51,  this);
		clsLogger.jlog.debug("Codelet registered: " + oDCActionSendToPhantasy.toString());
		
		clsDCCheckFocusExists oCheckFocus = new clsDCCheckFocusExists(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51,  this);
		clsLogger.jlog.debug("Codelet registered: " + oCheckFocus.toString());
		
		clsDCContinousAnalysis oContinousAnalysis = new clsDCContinousAnalysis(this.moEnvironmentalImage, this.moShortTermMemory, this.moGoalListFromF51, this);
		clsLogger.jlog.debug("Codelet registered: " + oContinousAnalysis.toString());
		
		
		
		
		//Action codelets
		clsACExecuteExternalAction oACExecuteExternalAction = new clsACExecuteExternalAction(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACExecuteExternalAction.toString());
		//System.out.println("Codelet registered: " + oACExecuteExternalAction.toString());
		
		clsACFlee oACFlee = new clsACFlee(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACFlee.toString());
		//System.out.println("Codelet registered: " + oACFlee.toString());
		
		clsACFocusMovement oACFocusMovement = new clsACFocusMovement(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACFocusMovement.toString());
		//System.out.println("Codelet registered: " + oACFocusMovement.toString());
		
		clsACFocusOn oACFocuOn = new clsACFocusOn(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACFocuOn.toString());
		//System.out.println("Codelet registered: " + oACFocuOn.toString());
		
		clsACPerformBasicActAnalysis oACPerformBasicActAnalysis = new clsACPerformBasicActAnalysis(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACPerformBasicActAnalysis.toString());
		//System.out.println("Codelet registered: " + oACPerformBasicActAnalysis.toString());
		
		clsACSendToPhantasy oACSendToPhantasy = new clsACSendToPhantasy(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACSendToPhantasy.toString());
		//System.out.println("Codelet registered: " + oACSendToPhantasy.toString());
	}
	
	public ArrayList<clsCodelet> getMatchingActionCodelets(clsWordPresentationMesh poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
//		for (clsActionCodelet oCodelet : this.moActionCodeletList) {
//			
//			if (oCodelet.checkMatchingPreconditions(poGoal)==true) {
//				oResult.add(oCodelet);
//			}
//		}
		
		ArrayList<clsActionCodelet> oPrelResult = sortAndFilterRatedStructures(this.moActionCodeletList, poGoal);
		oResult.addAll(oPrelResult);
		
		return oResult;
	}
	
	public ArrayList<clsCodelet> getMatchingDecisionCodelets(clsWordPresentationMesh poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
//		for (clsDecisionCodelet oCodelet : this.moDecisionCodeletList) {
//			
//			if (oCodelet.checkMatchingPreconditions(poGoal)>=1.0) {
//				clsImportanceTools.sortAndFilterRatedStructures(poInput, -1);
//				
//				oResult.add(oCodelet);
//			}
//		}
		
		ArrayList<clsDecisionCodelet> oPrelResult = sortAndFilterRatedStructures(this.moDecisionCodeletList, poGoal);
		oResult.addAll(oPrelResult);
		
		
		return oResult;
	}
	
	private static <E extends clsCodelet> ArrayList<E> sortAndFilterRatedStructures(ArrayList<E> poInput, clsWordPresentationMesh poGoal) {
		ArrayList<E> oResult = new ArrayList<E>();
		
		ArrayList<clsPair<Double, E>> oUnsortList = new ArrayList<clsPair<Double, E>>();
		ArrayList<clsPair<Double, E>> oSortList = new ArrayList<clsPair<Double, E>>();
		
		for (E oCodelet : poInput) {
			double rMatch = oCodelet.checkMatchingPreconditions(poGoal);
			if (oCodelet.checkMatchingPreconditions(poGoal)>=1.0) {
				
				oUnsortList.add(new clsPair<Double, E>(rMatch, oCodelet));
			}
		}
		
		oSortList = clsImportanceTools.sortAndFilterRatedStructuresDouble(oUnsortList, -1);
		
		for (clsPair<Double, E> oSortPair : oSortList) {
			oResult.add(oSortPair.b);
		}
		
		return oResult;
		
	}
	
//	private ArrayList<clsWordPresentationMesh> sortMostSpecializedAction(ArrayList<clsWordPresentationMesh> poActionList, clsWordPresentationMesh poGoal) {
//		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
//		
//		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oOpenToSortList = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
//		
//		//Go through all actions and get the number of successful preconditions 
//		for (clsWordPresentationMesh poAction : poActionList) {
//			ArrayList<eCondition> oPreconditionForActionList = clsActionTools.getPreconditions(poAction);
//			int nScore = 0;
//			
//			for (eCondition oPreconditionForAction : oPreconditionForActionList) {
//				if (poPreconditionStatusList.contains(oPreconditionForAction)==true) {
//					nScore++;
//				}
//			}
//			
//			oOpenToSortList.add(new clsPair<Integer, clsWordPresentationMesh>(nScore, poAction));			
//		}
//		
//		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oSortedActionList =  clsImportanceTools.sortAndFilterRatedStructures(oOpenToSortList, -1);
//		
//		for (clsPair<Integer, clsWordPresentationMesh> oAction : oSortedActionList) {
//			oResult.add(oAction.b);
//		}
//		
//		return oResult;
//	}
	
	public void executeCodeletListOnGoal(ArrayList<clsCodelet> oCodeletList, clsWordPresentationMesh poGoal) {
		for (clsCodelet oCodelet : oCodeletList) {
			oCodelet.assignGoal(poGoal);
			oCodelet.startCodelet();
			//clsLogger.jlog.debug("Codelet "  + oCodelet.toString() + " executed");
			oCodelet.clearGoal();
		}
	}
	
	public void executeMatchingCodelets(clsWordPresentationMesh poGoal, clsModuleBase oModulebase) {
		int nModuleNumber = oModulebase.getModuleNumber();
		
		//ArrayList<clsCodelet> oCList = new ArrayList<clsCodelet>();
		
		if (nModuleNumber==52) {
			ArrayList<clsCodelet> oCList = this.getMatchingActionCodelets(poGoal);
			this.executeCodeletListOnGoal(oCList, poGoal);
		} else if (nModuleNumber==51) {
			ArrayList<clsCodelet> oCList = this.getMatchingDecisionCodelets(poGoal);
			this.executeCodeletListOnGoal(oCList, poGoal);
		}
	}
	
}
