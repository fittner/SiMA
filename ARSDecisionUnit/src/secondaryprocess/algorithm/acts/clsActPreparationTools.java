/**
 * CHANGELOG
 *
 * 18.10.2012 wendt - File created
 *
 */
package secondaryprocess.algorithm.acts;

import java.util.ArrayList;

import memorymgmt.enums.eCondition;
import memorymgmt.shorttermmemory.clsShortTermMemory;

import org.apache.log4j.Logger;

import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshMentalSituation;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 18.10.2012, 10:09:56
 * 
 */
public class clsActPreparationTools {
	
	private static final double mrMomentActivationThreshold = 0.5;
	private static final double mrMomentConfidenceThreshold = 0.0;
	private static final double mrDefaultConfidenceIncreasement = 0.5;
	private static final double mrActConfidenceThreshold = 0.5;
	private static final double mrApplicableMomentConfidenceThreshold = 0.5;
	/** This is the default start timeout value for images in acts. Der Timeout 
	 * rechnet herunter wenn das �Moment� oder �Expectation� kein 1.0-Match (oder andere Schwellenwert) 
	 * haben und er ist da damit der Agent in keinem Deadlock verf�llt. Wenn der Timeout nur=3 Bewegungen lang ist, 
	 * bricht er den Akt ab, f�gt ein Condition �GOAL_NOT_REACHABLE� dazu und sortiert den Akt f�r ca 7 Runden 
	 * (L�nge des Working Memorys) aus.  @since 15.05.2014 09:50:38 */
	private static final int mnMovementTimeoutStartValue = 20;
	/** DOCUMENT (wendt) - insert description; @since 15.05.2014 09:52:12 */
	private static final int mnMovementTimeoutEstalishValue = 10;
	
	private static final double P_ACTMATCHACTIVATIONTHRESHOLD = 0.5;
	
	//private static Logger log = Logger.getLogger("pa._v38.decisionpreparation");
	private static Logger log = Logger.getLogger("Acts");

	public static ArrayList<eCondition> initActInGoal(clsWordPresentationMesh oIntention) {
	    ArrayList<eCondition> result = new ArrayList<eCondition>();
	    
	   // clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(moGoal.getSupportiveDataStructure());
        
        double rCurrentImageMatch = 0.0;
        
        //If the act has to start with the first image:
        if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true) {
            //Cases:
            //1. If the first image has match 1.0 and there is no first act ||
            //2. If the this act is the same as from the previous goal -> start this act as normal
            //else set GOAL_CONDITION_BAD
            clsWordPresentationMesh oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
            rCurrentImageMatch = clsActTools.getPIMatch(oFirstImage);
        } else {
            //Get best match from an intention
            clsWordPresentationMesh oBestMatchEvent = clsActTools.getHighestPIMatchFromSubImages(oIntention);
            rCurrentImageMatch = clsActTools.getPIMatch(oBestMatchEvent);
        }
        
        //If goal is obstable solving, put condition on goal
        if (clsActTools.checkIfConditionExists(oIntention, eCondition.OBSTACLE_SOLVING)==true) {
            result.add(eCondition.OBSTACLE_SOLVING);
        }
        
        if (rCurrentImageMatch >= 0.0 && rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
            //Set this option if there is an PImatch, but it is still too low
            result.add(eCondition.ACT_MATCH_TOO_LOW);
        } else if (rCurrentImageMatch < 0.0) {
            //Set this option, if the image has been indirectly inactivated
            result.add(eCondition.INSUFFICIENT_PIMATCH_INFO);
        }
        
        return result;
	}
	
	public static ArrayList<eCondition> checkProcessFirstImageStartCondition(clsWordPresentationMesh oIntention, clsWordPresentationMesh moment) {
	    ArrayList<eCondition> result = new ArrayList<eCondition>();
	    
	    double rCurrentImageMatch = 0.0;
	    
	    if (clsActTools.checkIfConditionExists(oIntention, eCondition.START_WITH_FIRST_IMAGE)==true && moment.isNullObject()==true) {
            //Cases:
            //1. If the first image has match 1.0 and there is no first act ||
            //2. If the this act is the same as from the previous goal -> start this act as normal
            //else set GOAL_CONDITION_BAD
            clsWordPresentationMesh oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
            rCurrentImageMatch = clsActTools.getPIMatch(oFirstImage);
            
            if (rCurrentImageMatch < P_ACTMATCHACTIVATIONTHRESHOLD) {
                //Set this option if there is an PImatch, but it is still too low
                result.add(eCondition.ACT_MATCH_TOO_LOW);
            }
        }
	    
	    return result;
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
	public static ArrayList<eCondition> performBasicActAnalysis(clsWordPresentationMesh poAct, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poSTM) {
		ArrayList<eCondition> oResult = new ArrayList<eCondition>();
		
		log.debug("Perform basic act analysis on: " + poAct.toString());
		
		//Get previous act
		clsWordPresentationMeshGoal oPreviousGoal = poSTM.findPreviousSingleMemory().getPlanGoal(); //clsMentalSituationTools.getGoal(poSTM.findPreviousSingleMemory());
		clsWordPresentationMesh oPreviousAct = oPreviousGoal.getSupportiveDataStructure();
		
		//Find the moment in the act
		analyzeMomentInAct(poAct, oPreviousAct);
		
		//Current found moment
		clsWordPresentationMesh oCurrentMoment = clsActDataStructureTools.getMoment(poAct);
		log.debug("Extracted moment: " + oCurrentMoment.toString());
		
		//Process the found moment
		boolean bMomentIsLastImage = clsActTools.isLastImage(oCurrentMoment);
		if (oCurrentMoment.isNullObject()==false) {
			if (bMomentIsLastImage==false) {
				analyzeExpectationInAct(poAct);
			}
		}
		
		clsWordPresentationMesh oCurrentExpectation = clsActDataStructureTools.getExpectation(poAct);
		log.debug("Extracted expectation: " + oCurrentExpectation.toString() + "\n");
		
		//Current Intention
		clsWordPresentationMesh oCurrentIntention = clsActDataStructureTools.getIntention(poAct);
		analyzeIntentionInAct(oPreviousGoal.getSupportiveDataStructure(), oCurrentMoment, oCurrentIntention);
		
//		//Set all Progress settings to the act
//		//If the act is new, then new progress settings shall be added, else, they shall be updated
//		addTotalProgress(oIntentionMomentExpectationList, poInput, mnConfirmationParts);
//		addAffectReduceValues(oIntentionMomentExpectationList, mrReduceFactorForDrives);

		//Get confidences
		double rCurrentMomentConfidence = clsActTools.getMomentConfidenceLevel(oCurrentMoment);
		double rCurrentIntentionActConfidence = clsActTools.getActConfidenceLevel(oCurrentIntention);
		
		//Return the recommendation
		//If the moment match > Threshold and there is an expectation
		//If the moment is the last image
		    
		if (rCurrentMomentConfidence>=mrMomentConfidenceThreshold && rCurrentIntentionActConfidence >= mrActConfidenceThreshold && oCurrentExpectation.isNullObject()==false) {
		    if (bMomentIsLastImage==true) {
	            if (clsActTools.checkIfConditionExists(oCurrentIntention, eCondition.CYCLIC_ACT)==true) {
	                //Goal completed but the last action shall be done again
	                oResult.add(eCondition.RESET_GOAL);
	            } else {
	                //Goal completed, close goal
	                oResult.add(eCondition.GOAL_COMPLETED);
	            }
	            oResult.add(eCondition.ACT_FINISHED);
	        } else {
	            //If not the last image
	            oResult.add(eCondition.SET_FOLLOW_ACT);
	        }
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
		//Get previous expectation
		clsWordPresentationMesh oPreviousExpectation = clsActDataStructureTools.getExpectation(poPreviousAct);
		
		//when start_with_first_image ersten vorziehen
		
		enhanceListWithPreviousMoment(oCurrentMomentCandidateList, poCurrentAct, oPreviousMoment);
		
		clsWordPresentationMesh oCurrentMoment = calculateMoment(oCurrentMomentCandidateList, oPreviousMoment, oPreviousExpectation);
		
		//A correct moment is either same moment as now or a moment, which is connected somehow with the last moment.
		if (oCurrentMoment.isNullObject()==false) {
			//Set the moment in the act
			clsActDataStructureTools.setMoment(poCurrentAct, oCurrentMoment);
		}
			
	}
	
	private static void enhanceListWithPreviousMoment(ArrayList<clsWordPresentationMesh> poCurrentMomentCandidateList, clsWordPresentationMesh poCurrentAct, clsWordPresentationMesh poPreviousMoment) {
		//Enhance currentmomentcandidatelist with the current moment of the previous act
		if (poPreviousMoment.isNullObject()==false) {
			//poCurrentMomentCandidateList.clear();
			
			clsWordPresentationMesh oCurrentIntention = clsActDataStructureTools.getIntention(poCurrentAct);
			clsWordPresentationMesh oProposedMomentFromPreviousAct = clsActTools.getEventFromIntentionByImageID(oCurrentIntention, poPreviousMoment);
			
			if (poCurrentMomentCandidateList.contains(oProposedMomentFromPreviousAct)==false) {
				poCurrentMomentCandidateList.add(oProposedMomentFromPreviousAct);
			}
			
		}
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
		
		log.debug("Calculate moment");
		
		//Check if there is only one image with the highest match
		if (oPreviousMoment.isNullObject()==true) {
		    log.trace("Previous moment does not exist");
			//Check if there are more than one possible moment
			if (oPossibleMomentList.size()>=1) {
			    //log.trace("At least More than one moment is proposed");
				int nBestImagePosition = clsActTools.getEventPositionInAct(oPossibleMomentList.get(0));
				oResult = oPossibleMomentList.get(0);
				
				//getfirstimage
				
				for (clsWordPresentationMesh oMoment : oPossibleMomentList) {
					double rMomentConfidence = clsActTools.getPIMatch(oMoment) * ((1-mrApplicableMomentConfidenceThreshold)*(1/oPossibleMomentList.size()) + mrApplicableMomentConfidenceThreshold);
					
					//if start with first image && isfirstimage do()
					
					clsActTools.setMomentConfidenceLevel(oMoment, rMomentConfidence);
					int nImagePosition = clsActTools.getEventPositionInAct(oMoment);
					
					log.trace("=== Momentcandidate: " + oMoment.getContent() + ", confidence: " + rMomentConfidence + "Image position: " + nImagePosition + "===");
					
					if (nBestImagePosition > nImagePosition) {
						oResult = oMoment;
						nBestImagePosition = nImagePosition;
					}
					
				}
			} else {
				//The list is empty, do nothing
			}
			
			
		} else {  //Previous moment exists
		    log.trace("Previous moment exists");
			
			//Check if there are more than one possible moment
			if (oPossibleMomentList.size()>=1) {
			    //log.trace("More than one moment is proposed");
				double rBestConfidence = 0.0;
				log.trace("Analyze cases: ");
				for (clsWordPresentationMesh oMoment : oPossibleMomentList) {	    
				    //Get the confidence of this moment
				    
				    //TODO Find a way to calculate the moment confidence if the important moment has not been activated in the primary process
				    //START TODO ========================================
				    double rMomentConfidence = 0;
				    if (clsActTools.getPIMatch(oMoment)==0.0) {
				        rMomentConfidence = 1.0 * 1/oPossibleMomentList.size();
				    } else {
				        rMomentConfidence = clsActTools.getPIMatch(oMoment) * 1/oPossibleMomentList.size();
				    }
				    
				    //STOP TODO =========================================
				    
				    
				    log.trace("=== Momentcandidate: " + oMoment.getContent() + ", confidence: " + rMomentConfidence + "===");
					
					//Add from confirmation of the acts or moments
					double rAdditionalMomentConfidence = clsActPreparationTools.getAdditionalMomentConfidenceByPreviousAct(oMoment, oPreviousMoment, oPreviousExpectation);
					
					double rNewMomentConfidence = rMomentConfidence + rAdditionalMomentConfidence;
					log.trace("New moment confidence: " + rNewMomentConfidence + "(addconfidence=" + rAdditionalMomentConfidence + ")");
					if (rNewMomentConfidence>1.0) {
						rNewMomentConfidence=1.0;
					}
					//log.trace("======");
					//Set the new confidence
					clsActTools.setMomentConfidenceLevel(oMoment, rNewMomentConfidence);
					
					//Take the best confidence value as moment
					if (rNewMomentConfidence>rBestConfidence) {
						rBestConfidence = rNewMomentConfidence;
						oResult = oMoment;
					//If the values are equal, take the one, which was the moment the last time.
					} else if (rNewMomentConfidence==rBestConfidence) {
						if (oMoment.getDS_ID()==oPreviousMoment.getDS_ID()) {
							rBestConfidence = rNewMomentConfidence;
							oResult = oMoment;
						}
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
		
		//If the PI match of the moment is over the recognition threshold, then set a new timeout value, else not.
		double oPIMatch = clsActTools.getPIMatch(oResult);

		if (oPIMatch >= mrMomentActivationThreshold) {
			//Check if the moment already has a default movement timeout value
			int nIndividualMovementTimeout = clsActTools.getIndividualMovementTimeoutValue(oResult);
			
			if (nIndividualMovementTimeout>0) {
				clsActTools.setMovementTimeoutValue(oResult, nIndividualMovementTimeout);
			} else {
				//Set the movement timeout from this modeule (default value)
				clsActTools.setMovementTimeoutValue(oResult, mnMovementTimeoutStartValue);
			}
		}
		
		return oResult;
	}
	
	/**
	 * Add confidence to the moments, which are related to the previous moments or expectations
	 * 
	 * This is a method, which holds the parameters of how an act is recognized or which event shall be the moment
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
	/**
	 * DOCUMENT - insert description
	 *
	 * @author wendt
	 * @since 15.05.2014 09:52:10
	 *
	 * @param poPossibleMoment
	 * @param poPreviousMoment
	 * @param poPreviousExpectation
	 * @return
	 */
	private static double getAdditionalMomentConfidenceByPreviousAct(clsWordPresentationMesh poPossibleMoment, clsWordPresentationMesh poPreviousMoment, clsWordPresentationMesh poPreviousExpectation) {
		double rResult = 0.0;
		
		double rReinforceFactor = 0.0;
		
		// --- GET ALL CONDITIONS AS BOOLEANS --- //
		//1. The possible moment is the PreviousPastMoment
		boolean bPossibleMomentIsPreviousPastMoment = false;
		clsWordPresentationMesh oPreviousPastMoment = clsActTools.getPreviousImage(poPreviousMoment);
		if (poPossibleMoment.getDS_ID() == oPreviousPastMoment.getDS_ID()) {
			bPossibleMomentIsPreviousPastMoment = true;
		}
		
		//2. The possible moment is the PreviousMoment
		boolean bPossibleMomentIsPreviousMoment = false;
		if (poPossibleMoment.getDS_ID() == poPreviousMoment.getDS_ID()) {
			bPossibleMomentIsPreviousMoment = true;
		}
		
		//3. The possible moment is the PreviousExpectation
		boolean bPossibleMomentIsPreviousExpectation = false;
		if (poPossibleMoment.getDS_ID() == poPreviousExpectation.getDS_ID()) {
			bPossibleMomentIsPreviousExpectation = true;
		}
		
		//4. The PI Match of the possible moment is above or equal 0.5
		boolean bPossibleMomentPIMatchEqual10 = false;
		if (clsActTools.getPIMatch(poPossibleMoment) >= 0.5) {
			bPossibleMomentPIMatchEqual10 = true;
		}
		
		//5. The PI-Match of the previous event is above or equal 0.5
		boolean bPreviousEventPIMatchEquals10 = false;
		clsWordPresentationMesh oPreviousEvent = clsActTools.getPreviousImage(poPossibleMoment);
		if (clsActTools.getPIMatch(oPreviousEvent) >= 0.5) {
			bPreviousEventPIMatchEquals10 = true;
		}
		
		//6. The PI-Match of the next event is above or equal 0.5
		boolean bNextEventPIMatchEquals10 = false;
		clsWordPresentationMesh oNextEvent = clsActTools.getNextImage(poPossibleMoment);
		if (clsActTools.getPIMatch(oNextEvent) >= 0.5) {
			bNextEventPIMatchEquals10 = true;
		}
				
		//7. The timeout of the previous moment is within the  
		boolean bPreviousMomentTimeoutWithinEstablishZone = false;
		int nPreviousMomentTimeoutValue = clsActTools.getMovementTimeoutValue(poPreviousMoment);
		if (nPreviousMomentTimeoutValue>= mnMovementTimeoutStartValue - mnMovementTimeoutEstalishValue) {
			bPreviousMomentTimeoutWithinEstablishZone = true;
		}
		
		//8. Previous moment is nullobject
		boolean bPreviousMomentIsNullObject = poPreviousMoment.isNullObject();
		
		// --- SET ALL CASES --- //
		if (bPossibleMomentIsPreviousPastMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==true && bNextEventPIMatchEquals10==false) {
			//Almost sure that the previous moment was an error
			rReinforceFactor += 0.8;
			log.debug("Case: Almost sure that the previous moment was an error");
		} else if (bPossibleMomentIsPreviousPastMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==true && bNextEventPIMatchEquals10==true) {
			//Conflict: Both images have the highest PI-Match, prefer the expectation
			rReinforceFactor += 0.2;
			log.debug("Conflict: Both the possible moment and the next possible expectation have the highest PI-Match, prefer the possible expectation as new moment");
	    } else if (bPossibleMomentIsPreviousMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==true && bNextEventPIMatchEquals10==true) {
	        //The next image is the expectation and it has full match as well as this moment, but as the previous moment is with the establish zone, the previous moment is preferred
	        rReinforceFactor += 0.5;
	        log.debug("The next image is the expectation and it has full match as well as this moment, but as the previous moment is with the establish zone, the previous moment is preferred");
	    } else if (bPossibleMomentIsPreviousMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==false && bNextEventPIMatchEquals10==true) {
            //The next image is the expectation and it has full match as well as this moment, but as the previous moment is not with the establish zone, the possible expectation should be preferred
            rReinforceFactor += -0.5;
            log.debug("The next image is the expectation and it has full match as well as this moment, but as the previous moment is not with the establish zone, the possible expectation should be preferred");
		} else if (bPossibleMomentIsPreviousMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==true && bNextEventPIMatchEquals10==false) {
			//The expectation match is lower than the previous moment match
			rReinforceFactor += 0.6;
			log.debug("The expectation match is lower than the previous moment match");
		} else if (bPossibleMomentIsPreviousExpectation==true && bPossibleMomentPIMatchEqual10==false && bPreviousMomentTimeoutWithinEstablishZone==true && bPreviousEventPIMatchEquals10==true) {
			//The possible moment is here the expectation, but its PImatch is low and it is within the establish zone, i. e. it may be erroneously put as an expectation. Prefer the past moment as the new moment
			rReinforceFactor += 0.3;
			log.debug("The possible moment is here the expectation, but its PImatch is low and it is within the establish zone, i. e. it may be erroneously put as an expectation. Prefer the past moment as the new moment");
		} else if (bPossibleMomentIsPreviousExpectation==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==true) {
            //This is a perfect match of the expectation inside of the establish zone of the previous moment. Change the moment to the expectation
            rReinforceFactor += 0.9;
            log.debug("This is a perfect match of the expectation inside of the establish zone of the previous moment. Change the moment to the expectation"); 
	    } else if (bPossibleMomentIsPreviousExpectation==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==false) {
			//This is a perfect match of the expectation outside of the establish zone of the previous moment. Change the moment to the expectation
			rReinforceFactor += 1.0;
			log.debug("This is a perfect match of the expectation outside of the establish zone of the previous moment. Change the moment to the expectation");
		} else if (bPossibleMomentIsPreviousMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==false && bPreviousEventPIMatchEquals10==false) {
			//This is a confirmation of the previous moment as there is no certain match of the expectation
			rReinforceFactor += 1.0;
			log.debug("This is a confirmation of the previous moment as there is no certain match of the expectation");
		} else if (bPossibleMomentIsPreviousMoment==true && bPossibleMomentPIMatchEqual10==false && bPreviousMomentTimeoutWithinEstablishZone==true && bNextEventPIMatchEquals10==false) {
			//This is a confirmation of the previous moment as there is no certain match of the expectation
			rReinforceFactor += 0.8;
			log.debug("This is a confirmation of the previous moment as there is no certain match of the expectation");
		} else if (bPossibleMomentIsPreviousMoment==true && bPossibleMomentPIMatchEqual10==true && bPreviousMomentTimeoutWithinEstablishZone==false && bNextEventPIMatchEquals10==false) {
			//This is a confirmation of the previous moment as there is no certain match of the expectation
			rReinforceFactor += 0.3;
			log.debug("This is a confirmation of the previous moment as there is no certain match of the expectation");
		} else if (bPreviousMomentIsNullObject==false) {
			//Reduce all other image in the act, which is not covered by the cases above
			rReinforceFactor += -0.5;
			log.debug("Reduce all other image in the act, which is not covered by the cases above");
		}
		
		rResult = mrDefaultConfidenceIncreasement*rReinforceFactor;
		
		log.debug("Added reinforcementfactor=" + rResult + " (Reinforcementfactor from cases= " + rReinforceFactor + ", default reinforcementfactorincreasement= " + mrDefaultConfidenceIncreasement);
		
		
		
//		//int nTimeOutValue =  clsActTools.getMovementTimeoutValue(oCurrentMoment);
//		
//		if (oCurrentMoment.getMoDS_ID() == oPreviousMoment.getMoDS_ID()) {
//			rResult += mrDefaultConfidenceIncreasement*0.5;
//		} else if (oCurrentMoment.getMoDS_ID() == oPreviousExpectation.getMoDS_ID() && rPIMatchCurrentMoment<1.0 && nTimeOutValue < mnMovementTimeoutValue - 5) {
//			rResult += mrDefaultConfidenceIncreasement*1.0;
//		} else if (oCurrentMoment.getMoDS_ID() == oPreviousExpectation.getMoDS_ID() && rPIMatchCurrentMoment==1.0 && nTimeOutValue > mnMovementTimeoutValue - 5) {
//			rResult += mrDefaultConfidenceIncreasement*0.3;
//		} else if (oPreviousMoment.isNullObject()==false) {
//			rResult += mrDefaultConfidenceIncreasement*(-0.5);
//		}
		
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
		
		if (oCurrentMoment.getDS_ID() == oPreviousExpectation.getDS_ID()) {
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

		if (oCurrentMoment.getDS_ID()!=oPreviousMoment.getDS_ID() || oCurrentMatch!=oPreviousMatch) {
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
	public static boolean checkIfPreviousActIsEqualToCurrentAct(clsWordPresentationMeshGoal poPreviousGoal, clsWordPresentationMesh poCurrentGoal) {
		boolean bResult = false;
		
		clsWordPresentationMesh oPreviousIntention = clsActDataStructureTools.getIntention(poPreviousGoal.getSupportiveDataStructure());
		clsWordPresentationMesh oCurrentIntention = clsActDataStructureTools.getIntention(poPreviousGoal.getSupportiveDataStructure());
		
		
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
		
		if (oPreviousIntention.getDS_ID()!=oCurrentIntention.getDS_ID()) {
			bResult=true;
		}
		
		return bResult;
	}
	
}
