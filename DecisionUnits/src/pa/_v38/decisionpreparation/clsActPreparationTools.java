/**
 * CHANGELOG
 *
 * 18.10.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActDataStructureTools;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 18.10.2012, 10:09:56
 * 
 */
public class clsActPreparationTools {
	
	private static final double mrMomentActivationThreshold = 1.0;
	private static final double mrMomentConfidenceThreshold = 0.3;
	private static final double mrDefaultConfidenceIncreasement = 0.5;
	private static final double mrActConfidenceThreshold = 0.5;

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
	public static ArrayList<eCondition> performBasicActAnalysis(clsWordPresentationMesh poAct, clsShortTermMemory poSTM) {
		ArrayList<eCondition> oResult = new ArrayList<eCondition>();
		
		//Get previous act
		clsWordPresentationMesh oPreviousGoal = clsMentalSituationTools.getGoal(poSTM.findPreviousSingleMemory());
		
		//Find the moment in the act
		analyzeMomentInAct(poAct, oPreviousGoal);
		
		//Current found moment
		clsWordPresentationMesh oCurrentMoment = clsActDataStructureTools.getMoment(poAct);

		
		//Process the found moment
		boolean bMomentIsLastImage = clsActTools.isLastImage(oCurrentMoment);
		if (oCurrentMoment.isNullObject()==false) {
			if (bMomentIsLastImage==false) {
				analyzeExpectationInAct(poAct);
			}
		}
		
		clsWordPresentationMesh oCurrentExpectation = clsActDataStructureTools.getExpectation(poAct);
		
		
		//Current Intention
		clsWordPresentationMesh oCurrentIntention = clsActDataStructureTools.getIntention(poAct);
		
		analyzeIntentionInAct(clsGoalTools.getSupportiveDataStructure(oPreviousGoal), oCurrentMoment, oCurrentIntention);
		
//		//Set all Progress settings to the act
//		//If the act is new, then new progress settings shall be added, else, they shall be updated
//		addTotalProgress(oIntentionMomentExpectationList, poInput, mnConfirmationParts);
//		addAffectReduceValues(oIntentionMomentExpectationList, mrReduceFactorForDrives);

		//Get confidences
		double rCurrentMomentConfidence = clsActTools.getMomentConfidenceLevel(oCurrentMoment);
		double rCurrentIntentionActConfidence = clsActTools.getActConfidenceLevel(oCurrentIntention);
		
		//Return the recommendation
		//If the moment match > Threshold and there is an expectation
		if (rCurrentMomentConfidence>=mrMomentConfidenceThreshold && rCurrentIntentionActConfidence >= mrActConfidenceThreshold && oCurrentExpectation.isNullObject()==false) {
			oResult.add(eCondition.SET_FOLLOW_ACT);
		//If the moment is the last image
		} else if (bMomentIsLastImage==true) {
			oResult.add(eCondition.GOAL_COMPLETED);
		//If there is some error and the images does not match very good
		} else {
			oResult.add(eCondition.GOAL_NOT_REACHABLE);
		}
		
		oResult.add(eCondition.SET_BASIC_ACT_ANALYSIS);
		
		return oResult;
	}
	
	/**
	 * Extract and set the current moment in an act
	 * 
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 14:01:31
	 *
	 * @param poCurrentAct
	 * @param poPreviousAct
	 */
	private static void analyzeMomentInAct(clsWordPresentationMesh poCurrentAct, clsWordPresentationMesh poPreviousAct) {
		//boolean bResult = false;
		
		// --- CASES --- //
		//1. No previous Image exists
		//1.1. If 1 Image match has match = 1.0 -> set moment confidence = Match, set act confidence = 0.5
		//1.2. If 1 Image match has match < 1.0 -> set moment confidence = Match, set act confidence = Momentconfidence * 0.5 
		//1.3. If >1 Images have the highest match -> set moment confidence = Number of Matches * Match * 0.5
		//1.3.1. If >1 images are found, prefer the first image
		
		//2. Previous Image exist, compare this PI-Match with the previous PI-Match
		//2.1. If the previous image = this image -> set this image as the moment
		//2.2. If the this image is the expectation of the previous image -> increase confidence +0.5 * Match, set this image as new moment
		//2.3. If no image has enough match, keep the old moment for x turns as a delay timer
		
		//3. If moment is the last image in the act, set "GOAL_REACHED"
		
		// --- PREPARE IMAGES --- //
		//Get the image from an act with the highest PI-match according to the primary process and consider the moment activation threshold
		ArrayList<clsWordPresentationMesh> oCurrentMomentCandidateList = clsActDataStructureTools.getMomentWithHighestPIMatch(poCurrentAct, mrMomentActivationThreshold);
		
		//Get the previous moment image
		clsWordPresentationMesh oPreviousMoment = clsActDataStructureTools.getMoment(poPreviousAct);
		clsWordPresentationMesh oPreviousExpectation = clsActDataStructureTools.getExpectation(poPreviousAct);
		
		clsWordPresentationMesh oCurrentMoment = calculateMoment(oCurrentMomentCandidateList, oPreviousMoment, oPreviousExpectation);

		//Get previous act
		//clsWordPresentationMesh oPreviousMoment = clsActDataStructureTools.getMoment(poPreviousAct);
		
		//Verify the temporal order
		//A correct moment is either same moment as now or a moment, which is connected somehow with the last moment.
		if (oCurrentMoment.isNullObject()==false) {
			clsActDataStructureTools.setMoment(poCurrentAct, oCurrentMoment);
			//bResult=true;
		}
			
		//return bResult;
	}
	
	/**
	 * Get the current moment and consider previous moments
	 * 
	 * (wendt)
	 * 
	 * // --- CASES --- //
	 * //1. No previous Image exists
	 * //1.1. If 1 Image match has match = 1.0 -> set moment confidence = Match, set act confidence = 0.5		
	 * //1.2. If 1 Image match has match < 1.0 -> set moment confidence = Match, set act confidence = Momentconfidence * 0.5
	 * //1.3. If >1 Images have the highest match -> set moment confidence = Number of Matches * Match * 0.5
	 * //1.3.1. If >1 images are found, prefer the first image
	 * 
	 * //2. Previous Image exist, compare this PI-Match with the previous PI-Match
	 * //2.1. If the previous image = this image -> set this image as the moment
	 * //2.2. If the this image is the expectation of the previous image -> increase confidence +0.5 * Match, set this image as new moment
	 * //2.3. If no image has enough match, keep the old moment for x turns as a delay timer
	 * 
	 * //3. If moment is the last image in the act, set "GOAL_REACHED"
	 *
	 *
	 *
	 * @since 18.10.2012 11:05:54
	 *
	 * @param oPossibleMomentList
	 * @param oPreviousMoment
	 * @param oPreviousExpectation
	 * @return
	 */
	private static clsWordPresentationMesh calculateMoment(ArrayList<clsWordPresentationMesh> oPossibleMomentList, clsWordPresentationMesh oPreviousMoment, clsWordPresentationMesh oPreviousExpectation) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		//Check if there is only one image with the highest match
		if (oPreviousMoment.isNullObject()==true) {
			//Check if there are more than one possible moment
			if (oPossibleMomentList.size()>=1) {
				for (clsWordPresentationMesh oMoment : oPossibleMomentList) {
					double rMomentConfidence = clsActTools.getPIMatch(oMoment) * 1/oPossibleMomentList.size();
					
					clsActTools.setMomentConfidenceLevel(oMoment, rMomentConfidence);
				}
				
				//TODO AW: Implement the last case. If the matches are the same, select the first image. In this case just take the first image from the list
				oResult = oPossibleMomentList.get(0);
								
			} else {
				//The list is empty, do nothing
			}
			
			
		} else {
			//Check if there are more than one possible moment
			if (oPossibleMomentList.size()>=1) {
				double rBestConfidence = 0.0;
				for (clsWordPresentationMesh oMoment : oPossibleMomentList) {
					double rMomentConfidence = clsActTools.getPIMatch(oMoment) * 1/oPossibleMomentList.size();
					
					//Add from confirmation of the acts or moments
					double rAdditionalMomentConfidence = clsActPreparationTools.getAdditionalMomentConfidenceByPreviousAct(oMoment, oPreviousMoment, oPreviousExpectation);
					
					double rNewMomentConfidence = rMomentConfidence + rAdditionalMomentConfidence;
					if (rNewMomentConfidence>1.0) {
						rNewMomentConfidence=1.0;
					}
					//Set the new confidence
					clsActTools.setMomentConfidenceLevel(oMoment, rNewMomentConfidence);
					
					if (rNewMomentConfidence>rBestConfidence) {
						rBestConfidence = rNewMomentConfidence;
						oResult = oMoment;
					}
				}
			} else {
				//The list is empty, use the previous moment if the timeout is OK
				
				//Get the timeout value 
				int nTimeOut = clsActTools.getMovementTimeoutValue(oPreviousMoment);
				
				if (nTimeOut>=0) {
					oResult = oPreviousMoment;
				}
				
				//if timeout value = 0 set GOAL_NOT_REACHABLE
			}
		}
		
		return oResult;
	}
	
	/**
	 * Add confidence to the moments, which are related to the previous moments or expectations
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 12:25:33
	 *
	 * @param oCurrentMoment
	 * @param oPreviousMoment
	 * @param oPreviousExpectation
	 * @return
	 */
	private static double getAdditionalMomentConfidenceByPreviousAct(clsWordPresentationMesh oCurrentMoment, clsWordPresentationMesh oPreviousMoment, clsWordPresentationMesh oPreviousExpectation) {
		double rResult = 0.0;
		
		if (oCurrentMoment.getMoDS_ID() == oPreviousMoment.getMoDS_ID()) {
			rResult += mrDefaultConfidenceIncreasement*0.2;
		} else if (oCurrentMoment.getMoDS_ID() == oPreviousExpectation.getMoDS_ID()) {
			rResult += mrDefaultConfidenceIncreasement*0.4;
		}
		
		return rResult;
	}
	
	/**
	 * If an expectation is confirmed, i. e. the current moment is the expectation of the previous step, then the act can be consided as confirmed. Therefore, additional confidence
	 * is added to the act.
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 12:27:28
	 *
	 * @param oCurrentMoment
	 * @param oPreviousExpectation
	 * @return
	 */
	private static double getAdditionalActConfidenceByPreviousAct(clsWordPresentationMesh oCurrentMoment, clsWordPresentationMesh oPreviousExpectation) {
		double rResult = 0.0;
		
		double rMomentConfidence = clsActTools.getMomentConfidenceLevel(oCurrentMoment); 
		
		if (oCurrentMoment.getMoDS_ID() == oPreviousExpectation.getMoDS_ID()) {
			rResult += mrDefaultConfidenceIncreasement*rMomentConfidence;
		}
		
		return rResult;
	}
	
	/**
	 * Analyze how the act confidence changes
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 15:58:41
	 *
	 * @param poPreviousExpectation
	 * @param poCurrentMoment
	 * @param poIntention
	 */
	private static void analyzeIntentionInAct(clsWordPresentationMesh poPreviousAct, clsWordPresentationMesh poCurrentMoment, clsWordPresentationMesh poIntention) {
		
		clsWordPresentationMesh oPreviousMoment = clsActDataStructureTools.getMoment(poPreviousAct);
		clsWordPresentationMesh oPreviousExpectation = clsActDataStructureTools.getExpectation(poPreviousAct);
		
		double rConfidence = clsActTools.getActConfidenceLevel(poIntention);
		
		if (oPreviousMoment.isNullObject()==true) {
			rConfidence = mrDefaultConfidenceIncreasement;
		} else {
			rConfidence += clsActPreparationTools.getAdditionalActConfidenceByPreviousAct(poCurrentMoment, oPreviousExpectation);
		}
		
		if (rConfidence>1.0) {
			rConfidence=1.0;
		}
		
		clsActTools.setActConfidenceLevel(poIntention, rConfidence);
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
	private static boolean analyzeExpectationInAct(clsWordPresentationMesh poCurrentAct) {
		boolean bResult = false;
		
		//Extract the expectation
		clsWordPresentationMesh oCurrentExpectation = clsActTools.getNextImage(clsActDataStructureTools.getMoment(poCurrentAct));
		
		//Set the expectation
		if (oCurrentExpectation.isNullObject()==false) {
			clsActDataStructureTools.setExpectation(poCurrentAct, oCurrentExpectation);
			bResult=true;
		}
		
		return bResult;
		
	}
	
	private static void performComparableActAnalysis(ArrayList<clsWordPresentationMesh> poGoalList) {

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
	private static boolean checkNeedForActAnalysis(clsWordPresentationMesh poCurrentAct, clsWordPresentationMesh poPreviousAct) {
		boolean bResult = false;
		
		//Check if the moment match has changed
		clsWordPresentationMesh oCurrentMoment = clsActDataStructureTools.getMoment(poCurrentAct);
		double oCurrentMatch = clsActTools.getPIMatch(oCurrentMoment);
		
		clsWordPresentationMesh oPreviousMoment = clsActDataStructureTools.getMoment(poPreviousAct);
		double oPreviousMatch = clsActTools.getPIMatch(oPreviousMoment);

		if (oCurrentMoment.getMoDS_ID()!=oPreviousMoment.getMoDS_ID() || oCurrentMatch!=oPreviousMatch) {
			bResult=true;
		}
		
		return bResult;
	}
	
	/**
	 * Check if the previous act from a goal is the same as this act
	 * 
	 * (wendt)
	 *
	 * @since 23.10.2012 15:50:38
	 *
	 * @param poPreviousGoal
	 * @param poCurrentGoal
	 * @return
	 */
	public static boolean checkIfPreviousActIsEqualToCurrentAct(clsWordPresentationMesh poPreviousGoal, clsWordPresentationMesh poCurrentGoal) {
		boolean bResult = false;
		
		clsWordPresentationMesh oPreviousIntention = clsActDataStructureTools.getIntention(clsGoalTools.getSupportiveDataStructure(poPreviousGoal));
		clsWordPresentationMesh oCurrentIntention = clsActDataStructureTools.getIntention(clsGoalTools.getSupportiveDataStructure(poCurrentGoal));
		
		
//		double rCurrentFirstImageMatch = clsActTools.getPIMatch(clsActTools.getFirstImageFromIntention(poIntention));
//		
//		//Get the first image and its match to the PI
//		ArrayList<clsWordPresentationMesh> oEventImageList = clsActTools.getAllSubImages(oIntention);
//		double rCurrentFirstImageMatch = 0.0;
//		if (oEventImageList.isEmpty()==false) {
//			clsWordPresentationMesh oFirstImage = clsActTools.getFirstImage(oEventImageList.get(0));
//			rCurrentFirstImageMatch = clsActTools.getPIMatch(oFirstImage);
//		}
//		
//		
//		
//		
		
		if (oPreviousIntention.getMoDS_ID()!=oCurrentIntention.getMoDS_ID()) {
			bResult=true;
		}
		
		return bResult;
	}
	
}
