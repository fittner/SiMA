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
import pa._v38.decisionpreparation.decisioncodelets.clsDCCheckFocusExists;
import pa._v38.decisionpreparation.decisioncodelets.clsDCDriveContinousAnalysis;
import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.storage.clsEnvironmentalImageMemory;
import pa._v38.storage.clsShortTermMemory;

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
	
	private ArrayList<clsCodelet> moCodeletList = new ArrayList<clsCodelet>();
	
	
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
	public ArrayList<clsCodelet> getCodeletList() {
		return moCodeletList;
	}


	/**
	 * @since 23.09.2012 12:10:38
	 * 
	 * @param moCodeletList the moCodeletList to set
	 */
	public void setCodeletList(ArrayList<clsCodelet> moCodeletList) {
		this.moCodeletList = moCodeletList;
	}
	
	public void addToCodeletList(clsCodelet poCodelet) {
		this.moCodeletList.add(poCodelet);
	}
	
	public void init() {
		this.registerCodelets();
	}
	
	private void registerCodelets() {
		//Decision codelets
		clsDCCheckFocusExists oCheckFocus = new clsDCCheckFocusExists(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oCheckFocus.toString());
		System.out.println("Codelet registered: " + oCheckFocus.toString());
		
		clsDCDriveContinousAnalysis oContinousAnalysis = new clsDCDriveContinousAnalysis(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oContinousAnalysis.toString());
		System.out.println("Codelet registered: " + oContinousAnalysis.toString());
		
		//Action codelets
		clsACExecuteExternalAction oACExecuteExternalAction = new clsACExecuteExternalAction(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACExecuteExternalAction.toString());
		System.out.println("Codelet registered: " + oACExecuteExternalAction.toString());
		
		clsACFlee oACFlee = new clsACFlee(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACFlee.toString());
		System.out.println("Codelet registered: " + oACFlee.toString());
		
		clsACFocusMovement oACFocusMovement = new clsACFocusMovement(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACFocusMovement.toString());
		System.out.println("Codelet registered: " + oACFocusMovement.toString());
		
		clsACFocusOn oACFocuOn = new clsACFocusOn(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACFocuOn.toString());
		System.out.println("Codelet registered: " + oACFocuOn.toString());
		
		clsACPerformBasicActAnalysis oACPerformBasicActAnalysis = new clsACPerformBasicActAnalysis(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACPerformBasicActAnalysis.toString());
		System.out.println("Codelet registered: " + oACPerformBasicActAnalysis.toString());
		
		clsACSendToPhantasy oACSendToPhantasy = new clsACSendToPhantasy(this.moEnvironmentalImage, this.moShortTermMemory, this);
		clsLogger.jlog.debug("Codelet registered: " + oACSendToPhantasy.toString());
		System.out.println("Codelet registered: " + oACSendToPhantasy.toString());
	}
	
	public ArrayList<clsCodelet> getMatchingCodelets(clsWordPresentationMesh poGoal) {
		ArrayList<clsCodelet> oResult = new ArrayList<clsCodelet>();
		
		for (clsCodelet oCodelet : this.moCodeletList) {
			
			if (oCodelet.checkMatchingPreconditions(poGoal)==true) {
				oResult.add(oCodelet);
			}
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
	
	public void executeMatchingCodelets(clsWordPresentationMesh poGoal) {
		ArrayList<clsCodelet> oCList = this.getMatchingCodelets(poGoal);
		this.executeCodeletListOnGoal(oCList, poGoal);
	}
	
	
}
