/**
 * CHANGELOG
 *
 * 15.09.2011 wendt - File created
 *
 */
package pa._v38.tools.datastructures;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * This class contains all functions, which are used in the processing of acts and are used in more than one module
 * 
 * @author wendt
 * 15.09.2011, 09:44:49
 * 
 */
public class clsActTools {

	
	// ============== ACT CATEGORIZATION START ==============================//
	
	public static ArrayList<clsWordPresentationMesh> processMemories(ArrayList<clsWordPresentationMesh> poSingleList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsWordPresentationMesh oImage : poSingleList) {
			//Tasks in the memory processing
			
			//1. Add all PI-Matches as WP to each image
			clsActTools.setPIMatchToWPM(oImage); //The adding is done hier at the first place as in this module only images are processed and not entities.
			
			//2. Delete all primary process external connections
			clsMeshTools.removeAllExternalAssociationsTPM(clsMeshTools.getPrimaryDataStructureOfWPM(oImage));
			
		}
		
		//3. Organize all loose images in acts
		oRetVal = organizeImagesInActs(poSingleList);
		
		return oRetVal;
	}
	
	/**
	 * Create a list of categorized memories. Each act is given a single entry of the type prediction. The prediction is
	 * a WPM, which contains Moment, Intention and Expectation
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 11:39:49
	 *
	 * @param poSingleList
	 * @return
	 */
	private static ArrayList<clsWordPresentationMesh> organizeImagesInActs(ArrayList<clsWordPresentationMesh> poSingleList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsWordPresentationMesh oRI : poSingleList) {
			//1. Extract the super structure of the RI
			//Either the intention of the oRI will be returned or the structure itself if it is an intention
			clsWordPresentationMesh oSuperStructure = clsMeshTools.getSuperStructure(oRI);
			
			//2. Check if the super structure exists in any prediction
			clsWordPresentationMesh oExistentPrediction = clsActDataStructureTools.checkIfIntentionExistsInActList(oRetVal, oSuperStructure);
	
			//3.a If act exists, then check if the match of the current moment, if exists, is lower than this image
			if (oExistentPrediction.isNullObject()==false) {
				//Merge meshes
			    clsMeshTools.mergeMesh(clsActDataStructureTools.getIntention(oExistentPrediction), oRI);
			} else {
				//Create prediction
				clsWordPresentationMesh oPrediction = clsActDataStructureTools.createActDataStructure(oSuperStructure);
				
				oRetVal.add(oPrediction);
			}
			
						
		}
		
		return oRetVal;
	}
	
	/**
	 * Check if the current WPM is an intention.
	 *  
	 * (wendt)
	 *
	 * @since 31.08.2011 13:27:30
	 *
	 * @param poInputList
	 * @return
	 */
	public static boolean isIntention(clsWordPresentationMesh poInput) {
		boolean bRetVal = false;
		
		//Get all external associations of each memory
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			//If this container is a leaf element of an associationsecondary with the predicate "hasSuper" and is the object of that association
			if (oAss instanceof clsAssociationSecondary) {
				//An intention is recognized if the image is the Leaf element of a Hierarchical association (hasSuper)
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASSUPER) && (oAss.getLeafElement().equals(poInput))) {
					//One intention has been found
					bRetVal=true;
					//One result was found, then break
					break;
				}
			}
		}
		return bRetVal;
	}
	
	/**
	 * Check if this structure is a part of an act, i. e. if it is a situation. Check if the WPM has a "hasSuper" and a "hasNext"
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 12:41:59
	 *
	 * @param poInput
	 * @return
	 */
	public static boolean isEvent(clsWordPresentationMesh poInput) {
		boolean bRetVal = false;
		boolean bHasSuper = false;
		boolean bHasNext = false;
		
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			//If this container is a leaf element of an associationsecondary with the predicate "hasSuper" and is the object of that association
			if (oAss instanceof clsAssociationSecondary) {
				//An intention is recognized if the image is the Leaf element of a Hierarchical association (hasSuper)
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASSUPER)) {
					//The structure is a super or has a super
					bHasSuper=true;
					if (bHasNext==true) {
						//One result was found, then break
						bRetVal=true;
						break;
					}
				} else if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASNEXT)) {
					bHasNext=true;
					if (bHasSuper==true) {
						bRetVal=true;
						break;
					}
				}
			}
		}
		
		return bRetVal;
	}
	
	/**
	 * Get the PI-Match of a certain WPM
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 22:14:21
	 *
	 * @param poImage
	 * @return
	 */
	public static double getSecondaryMatchValueToPIFromPrimaryPart(clsWordPresentationMesh poImage) {
		double rRetVal = 0.0;
		
		//Get the primary datastructure
		clsThingPresentationMesh oTPMPart = clsMeshTools.getPrimaryDataStructureOfWPM(poImage);
		if (oTPMPart!=null) {
			rRetVal = getPrimaryMatchValueToPI(oTPMPart);
		}
		
		return rRetVal;
	}
	
	/**
	 * Get association of a primary data structure container with PI. If the answer is 0.0, then no association to the PI was found
	 * (wendt)
	 *
	 * @since 04.08.2011 13:59:56
	 *
	 * @param poImageContainer
	 * @return 
	 */
	public static double getPrimaryMatchValueToPI(clsThingPresentationMesh poImage) {
		double rRetVal = 0.0;
		
		for (clsAssociation oAss : poImage.getExternalMoAssociatedContent()) {
			if (oAss instanceof clsAssociationPrimary) {
				if (oAss.getTheOtherElement(poImage).getMoContentType().equals(eContentType.PI)) {
					rRetVal = oAss.getMrWeight();
					break;
				}	
			}
		}
		
		return rRetVal;
	}
	
	/**
	 * Get the PI match from the PP image and add it to the SP image
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 16:46:54
	 *
	 * @param poImage
	 */
	public static void setPIMatchToWPM(clsWordPresentationMesh poImage) {
		//Get the PI-match
		double rPIMatchValue = getSecondaryMatchValueToPIFromPrimaryPart(poImage);
		
		if (rPIMatchValue>0.0) {
			//Add new WP to image
			clsMeshTools.setUniquePredicateWP(poImage, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPIMATCH, eContentType.PIMATCH, String.valueOf(rPIMatchValue), false);
		}
	}
	
	/**
	 * Get the PI match of an image in the secondary process. This function only gets the secondary process match, which is set in a WP and is not taking the match from 
	 * the primary process.
	 * 
	 * (wendt)
	 *
	 * @since 16.10.2012 21:14:41
	 *
	 * @param poImage
	 * @return
	 */
	public static double getPIMatch(clsWordPresentationMesh poImage) {
		double rResult = 0.0;
		
		clsWordPresentation oPIMatch = clsMeshTools.getUniquePredicateWP(poImage, ePredicate.HASPIMATCH);
		
		if (oPIMatch!=null) {
			rResult = Double.valueOf(oPIMatch.getMoContent());
		}
		
		return rResult;
	}
	
	/**
	 * Remove PI match from an image and its sub images
	 * 
	 * (wendt)
	 *
	 * @since 22.07.2012 22:56:24
	 *
	 * @param poImage
	 */
	public static void removePIMatchFromWPMAndSubImages(clsWordPresentationMesh poImage) {
		ArrayList<clsWordPresentationMesh> MIA = clsMeshTools.getAllWPMImages(poImage, 2);
		
		for (clsWordPresentationMesh oWPM : MIA) {
			clsMeshTools.removeAllNonUniquePredicateSecondaryDataStructure(oWPM, ePredicate.HASPIMATCH);
		}
		
	}
	
	/**
	 * Get the next image in the sequence
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 16:15:26
	 *
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getNextImage(clsWordPresentationMesh poImage) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		//Return the whole association, with the leaf elements of the associations of the image
		ArrayList<clsDataStructurePA> oDSList = clsMeshTools.searchDataStructureOverAssociation(poImage, ePredicate.HASNEXT, 2, true, false);
		
		for (clsDataStructurePA oDS : oDSList) {
			clsAssociationSecondary oAss = (clsAssociationSecondary) oDS;
			if (oAss.getRootElement().getMoDS_ID()==poImage.getMoDS_ID()) {
				oRetVal = (clsWordPresentationMesh) oAss.getLeafElement();
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the previous image in the sequence
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 16:16:05
	 *
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getPreviousImage(clsWordPresentationMesh poImage) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		//Return the whole association, with the leaf elements of the associations of the image
		ArrayList<clsDataStructurePA> oDSList = clsMeshTools.searchDataStructureOverAssociation(poImage, ePredicate.HASNEXT, 1, true, false);
				
		for (clsDataStructurePA oDS : oDSList) {
			clsAssociationSecondary oAss = (clsAssociationSecondary) oDS;
			if (oAss.getLeafElement().getMoDS_ID()==poImage.getMoDS_ID()) {
				oRetVal = (clsWordPresentationMesh) oAss.getRootElement();
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Check if there is a previous image in the sequence
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 16:16:28
	 *
	 * @param poImage
	 * @return
	 */
	public static boolean hasPreviousImage(clsWordPresentationMesh poImage) {
		boolean oRetVal = false;
		
		clsWordPresentationMesh oPreviousImage = getPreviousImage(poImage);
		//clsWordPresentationMesh oNextImage = getNextImage(poImage);
		
		if (oPreviousImage.isNullObject()==false) {
			oRetVal = true;
		}
		
		return oRetVal;
	}
	
	/**
	 * Check if there is a next image in the sequence
	 * 
	 * (wendt)
	 * 
	 * @since 24.05.2012 16:16:52
	 *
	 * @param poImage
	 * @return
	 */
	public static boolean hasNextImage(clsWordPresentationMesh poImage) {
		boolean oRetVal = false;
		
		clsWordPresentationMesh oNextImage = getNextImage(poImage);
		
		if (oNextImage.isNullObject()==false) {
			oRetVal = true;
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the first image in the sequence
	 * 
	 * (wendt)
	 *
	 * @since 18.07.2012 21:11:14
	 *
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getFirstImage(clsWordPresentationMesh poEventImage) {
		clsWordPresentationMesh oRetVal = poEventImage;
				
		while (hasPreviousImage(oRetVal)==true) {
			oRetVal = getPreviousImage(oRetVal);
		}
		
		return oRetVal;
	}
	
	/**
	 * Getthe first image from an act by having the intention
	 * 
	 * (wendt)
	 *
	 * @since 23.10.2012 15:46:41
	 *
	 * @param poIntention
	 * @return
	 */
	public static clsWordPresentationMesh getFirstImageFromIntention(clsWordPresentationMesh poIntention) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		ArrayList<clsWordPresentationMesh> oEventImageList = clsActTools.getAllSubImages(poIntention);
		if (oEventImageList.isEmpty()==false) {
			oResult = clsActTools.getFirstImage(oEventImageList.get(0));
		}
		
		return oResult;
	}
	
	/**
	 * Get the last image in the sequence
	 * 
	 * (wendt)
	 *
	 * @since 18.07.2012 21:11:34
	 *
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getLastImage(clsWordPresentationMesh poEventImage) {
		clsWordPresentationMesh oRetVal = poEventImage;
		
		while (hasNextImage(oRetVal)==true) {
			oRetVal = getNextImage(oRetVal);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the position of an image in an act
	 * 
	 * (wendt)
	 *
	 * @since 23.10.2012 21:24:44
	 *
	 * @param poEventImage
	 * @return
	 */
	public static int getEventPositionInAct(clsWordPresentationMesh poEventImage) {
		int nResult = 0;
		
		//Get the first image
		clsWordPresentationMesh oFirstImage = clsActTools.getFirstImage(poEventImage);
		
		while (hasNextImage(oFirstImage)==true) {
			if (oFirstImage==poEventImage) {
				break;
			}
			
			nResult++;
			oFirstImage = getNextImage(oFirstImage);
		}
		
		return nResult;
	}
	
	/**
	 * Get the number of events in the act
	 * 
	 * (wendt)
	 *
	 * @since 23.10.2012 21:34:02
	 *
	 * @param poIntention
	 * @return
	 */
	public static int getEventImageCount(clsWordPresentationMesh poIntention) {
		return clsActTools.getAllSubImages(poIntention).size();
	}
	
	/**
	 * Check if an event is the last image of an act
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 15:44:04
	 *
	 * @param poEventImage
	 * @return
	 */
	public static boolean isLastImage(clsWordPresentationMesh poEventImage) {
		boolean bResult = false;
		
		clsWordPresentationMesh oLastImage = getLastImage(poEventImage);
		if (oLastImage.getMoDS_ID()==poEventImage.getMoDS_ID()) {
			bResult = true;
		}
		
		return bResult;
	}
	
	/**
	 * Get all subimages from an intention
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 13:32:16
	 *
	 * @param poIntention
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getAllSubImages(clsWordPresentationMesh poIntention) {
		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
		
		ArrayList<clsDataStructurePA> oFoundList = clsMeshTools.searchDataStructureOverAssociation(poIntention, ePredicate.HASSUPER, 1, false, false);
		
		for (clsDataStructurePA oDS : oFoundList) {
			oResult.add((clsWordPresentationMesh)oDS);
		}
		
		return oResult;
	}
	
	/**
	 * Set PIMatch
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:30:09
	 *
	 * @param poAction
	 */
	public static void setPIMatch(clsWordPresentationMesh poAction, double prPIMatch) {
		clsMeshTools.setUniquePredicateWP(poAction, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPIMATCH, eContentType.PIMATCH, String.valueOf(prPIMatch), false);
	}
	
	/**
	 * Get the PIMatch
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:29:51
	 *
	 * @param poAction
	 * @return
	 */
	public static double getPIMatchFlag(clsWordPresentationMesh poImage) {
		double rResult = 0.0;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poImage, ePredicate.HASPIMATCH);
		
		if (oWP!=null) {
			rResult = Double.valueOf(oWP.getMoContent());
		}
		
		return rResult;
	}
	
	/**
	 * Get the subimage with the highest PIMatch from the intention
	 * 
	 * (wendt)
	 *
	 * @since 12.10.2012 22:51:08
	 *
	 * @param poIntention
	 * @return
	 */
	public static clsWordPresentationMesh getHighestPIMatchFromSubImages(clsWordPresentationMesh poIntention) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		ArrayList<clsWordPresentationMesh> oSubImageList = clsActTools.getAllSubImages(poIntention);
		
		double rBestPIMatch = 0.0;
		for (clsWordPresentationMesh oSubImage : oSubImageList) {
			//Get PIMatch
			double rCurrentPIMatch = clsActTools.getPIMatchFlag(oSubImage);
			
			if (rBestPIMatch<rCurrentPIMatch) {
				rBestPIMatch = rCurrentPIMatch;
				oResult = oSubImage;
			}
		}
		
		return oResult;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 25.07.2012 20:00:34
	 *
	 * @param poSourceIntention
	 * @param poTargetIntention
	 */
	public static void transferAllPIMatches(clsWordPresentationMesh poSourceIntention, clsWordPresentationMesh poTargetIntention) {
		
		for (clsWordPresentationMesh oS : clsActTools.getAllSubImages(poSourceIntention)) {
			for (clsWordPresentationMesh oT : clsActTools.getAllSubImages(poTargetIntention)) {
				if (oS.getMoDS_ID()==oT.getMoDS_ID()) {
					copyPIMatches(oS, oT);
					
					break;
				}
			}
		}
	}
	
	/**
	 * Copy a PI match from one image to another
	 * 
	 * (wendt)
	 *
	 * @since 14.10.2012 12:30:22
	 *
	 * @param poSourceImage
	 * @param poTargetImage
	 */
	private static void copyPIMatches(clsWordPresentationMesh poSourceImage, clsWordPresentationMesh poTargetImage) {
		//Get PI-Match
		double rPIMatch = clsActTools.getPIMatchFlag(poSourceImage);
		
		//Set PI-Match
		clsActTools.setPIMatch(poTargetImage, rPIMatch);
	}
	
	/**
	 * Get the recommended action from an image
	 * 
	 * (wendt)
	 *
	 * @since 16.10.2012 22:12:37
	 *
	 * @param poImage
	 * @return
	 */
	public static eAction getRecommendedAction(clsWordPresentationMesh poImage) {
		return eAction.valueOf(clsMeshTools.getUniquePredicateWPM(poImage, ePredicate.HASACTION).getMoContent());
	}
	
	/**
	 * Set confidence level
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 10:27:41
	 *
	 * @param poIntention
	 * @param prValue
	 */
	public static void setMomentConfidenceLevel(clsWordPresentationMesh poMoment, double prValue) {
		clsMeshTools.setUniquePredicateWP(poMoment, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASMOMENTCONFIDENCE, eContentType.MOMENTCONFIDENCE, String.valueOf(prValue), false);
	}
	
	/**
	 * Get confidence level
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 10:27:53
	 *
	 * @param poIntention
	 * @return
	 */
	public static double getMomentConfidenceLevel(clsWordPresentationMesh poMoment) {
		double rResult = 0.0;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poMoment, ePredicate.HASMOMENTCONFIDENCE);
		
		if (oWP!=null) {
			rResult = Double.valueOf(oWP.getMoContent());
		}
		
		return rResult;
	}
	
	/**
	 * Set confidence level
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 10:27:41
	 *
	 * @param poIntention
	 * @param prValue
	 */
	public static void setActConfidenceLevel(clsWordPresentationMesh poIntention, double prValue) {
		clsMeshTools.setUniquePredicateWP(poIntention, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTCONFIDENCE, eContentType.ACTCONFIDENCE, String.valueOf(prValue), false);
	}
	
	/**
	 * Get confidence level
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 10:27:53
	 *
	 * @param poIntention
	 * @return
	 */
	public static double getActConfidenceLevel(clsWordPresentationMesh poIntention) {
		double rResult = 0.0;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poIntention, ePredicate.HASACTCONFIDENCE);
		
		if (oWP!=null) {
			rResult = Double.valueOf(oWP.getMoContent());
		}
		
		return rResult;
	}
	
	/**
	 * Set timeout for a certain moment.
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 10:27:41
	 *
	 * @param poIntention
	 * @param prValue
	 */
	public static void setMovementTimeoutValue(clsWordPresentationMesh poMoment, int pnNumberOfTimeoutMovements) {
		clsMeshTools.setUniquePredicateWP(poMoment, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASMOVEMENTTIMEOUT, eContentType.MOVEMENTTIMEOUT, String.valueOf(pnNumberOfTimeoutMovements), false);
	}
	
	/**
	 * Get the timeout for a certain moment
	 * 
	 * (wendt)
	 *
	 * @since 18.10.2012 10:27:53
	 *
	 * @param poIntention
	 * @return
	 */
	public static int getMovementTimeoutValue(clsWordPresentationMesh poMoment) {
		int nResult = 0;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poMoment, ePredicate.HASMOVEMENTTIMEOUT);
		
		if (oWP!=null) {
			nResult = Integer.valueOf(oWP.getMoContent());
		}
		
		return nResult;
	}
	
	/**
	 * Get the default movement timeout value for this moment
	 * 
	 * (wendt)
	 *
	 * @since 14.02.2013 15:15:18
	 *
	 * @param poMoment
	 * @return
	 */
	public static int getIndividualMovementTimeoutValue(clsWordPresentationMesh poMoment) {
		int nResult = 0;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poMoment, ePredicate.HASINDIVIDUALMOVEMENTTIMEOUT);
		
		if (oWP!=null) {
			nResult = Integer.valueOf(oWP.getMoContent());
		}
		
		return nResult;
	}
	
	/**
	 * Set task status or replace if it already exists
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 22:00:32
	 *
	 * @param poGoal
	 * @param poTask
	 */
	public static void setCondition(clsWordPresentationMesh poIntention, eCondition poTask) {
		//Get the current one
		//clsWordPresentation oFoundStructure = clsGoalTools.getDecisionTaskDataStructure(poGoal);
		
		//Replace or create new
		//if (oFoundStructure==null) {
		clsMeshTools.setNonUniquePredicateWP(poIntention, ePredicate.HASCONDITION, eContentType.CONDITION, poTask.toString(), false);
		//} else {
		//	oFoundStructure.setMoContent(poTask.toString());
		//}
		
	}
	
	
	/**
	 * Get the current decision task
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 16:42:03
	 *
	 * @param poGoal
	 * @return
	 */
	public static ArrayList<eCondition> getCondition(clsWordPresentationMesh poIntention) {
		ArrayList<eCondition> oResult = new ArrayList<eCondition>();
		
		ArrayList<clsWordPresentation> oFoundTaskStatusList = clsMeshTools.getNonUniquePredicateWP(poIntention, ePredicate.HASCONDITION);
				
		for (clsWordPresentation oTaskStatus : oFoundTaskStatusList) {
			oResult.add(eCondition.valueOf(((clsWordPresentation) oTaskStatus).getMoContent()));
		}
		
		return oResult;
	}
	
	/**
	 * Check if a certain taskstatus exists
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 20:27:12
	 *
	 * @param poGoal
	 * @param poTask
	 * @return
	 */
	public static boolean checkIfConditionExists(clsWordPresentationMesh poGoal, eCondition poTask) {
		boolean bResult = false;
		
		ArrayList<eCondition> oResult = clsActTools.getCondition(poGoal);
		if (oResult.contains(poTask)) {
			bResult=true;
		}
		
		return bResult;
	}
	
	/**
	 * Find a certain instance of an event within an act by comparing similar images with the same ID
	 * 
	 * This function is used to find the current moment in an act where the input is the current moment of the previous act (previous step)
	 * 
	 * If an instance of the same act would be searched for as input, then the same instance would be returned again. 
	 * 
	 * Precondition: Break at first match as only one image with an ID is allowed within an act
	 * 
	 * (wendt)
	 *
	 * @since 23.10.2012 20:46:00
	 *
	 * @param poIntention: Intention of the act
	 * @param poEvent: Other event.
	 * @return
	 */
	public static clsWordPresentationMesh getEventFromIntentionByImageID(clsWordPresentationMesh poIntention, clsWordPresentationMesh poEvent) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		ArrayList<clsWordPresentationMesh> oEventList = clsActTools.getAllSubImages(poIntention);
		
		for (clsWordPresentationMesh oActEvent : oEventList) {
			if (oActEvent.getMoDS_ID()==poEvent.getMoDS_ID()) {
				oResult = oActEvent;
				break;
			}
		}
		
		return oResult;
	}
	
		
	// ============== ACT CATEGORIZATION START ==============================//
	
	// ============== PROGRESS FUNCTIONS START ==============================//
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 09.09.2011 21:57:13
//	 *
//	 * @param poContainer
//	 * @param poPredicate
//	 * @param poContentType
//	 * @param poContent
//	 * @param pbReplace
//	 */
//	public static void setTemporalProgressFactor(clsSecondaryDataStructureContainer poContainer, double prContent) {
//		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASTEMPORALPROGRESSFACTOR.toString(), "PROGRESSFACTOR", String.valueOf(prContent));
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 09.09.2011 21:57:15
//	 *
//	 * @param poContainer
//	 * @param poPredicate
//	 * @return
//	 */
//	public static double getTemporalProgressFactor(clsSecondaryDataStructureContainer poContainer) {
//		double oRetVal = 0;
//		
//		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASTEMPORALPROGRESSFACTOR.toString());
//		
//		if (oWPList.isEmpty()==false) {
//			oRetVal = Double.valueOf(oWPList.get(0).getMoContent());
//		}
//			
//		return oRetVal;
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 09.09.2011 22:27:25
//	 *
//	 * @param poContainer
//	 * @param poContent
//	 */
//	public static void setTemporalProgress(clsSecondaryDataStructureContainer poContainer, double prContent) {
//		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASTEMPORALPROGRESS.toString(), "PROGRESS", String.valueOf(prContent));
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 10.09.2011 17:23:41
//	 *
//	 * @param poContainer
//	 * @return
//	 */
//	public static double getTemporalProgress(clsSecondaryDataStructureContainer poContainer) {
//		double oRetVal = 0.0;
//		
//		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASTEMPORALPROGRESS.toString());
//		
//		if (oWPList.isEmpty()==false) {
//			oRetVal = Double.valueOf(oWPList.get(0).getMoContent());
//		}
//			
//		return oRetVal;
//	}
//	
//	// ============== PROGRESS FUNCTIONS END ==============================//
//	
//	// ============== SECONDARY GENERAL INFORMATION EXTRACTION FUNCTIONS START ==============================//
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 09.09.2011 21:57:11
//	 *
//	 * @param poIntention
//	 * @return
//	 */
//	public static int countAllSubStructures(clsSecondaryDataStructureContainer poIntention) {
//		int nRetVal = 0;
//		
//		ArrayList<clsSecondaryDataStructure> oSubImages = clsDataStructureTools.getDSFromSecondaryAssInContainer(poIntention, ePredicate.ISA.toString(), true);
//		nRetVal = oSubImages.size();
//		
//		return nRetVal;
//	}
//	
//	/**
//	 * From a certain image in an act, get the number of structures, until the end of the act is reached. 
//	 * This function is used in F51
//	 * (wendt)
//	 *
//	 * @since 11.09.2011 14:15:11
//	 *
//	 * @param poMoment
//	 * @param poActivatedInputList
//	 * @return
//	 */
//	public static int countSubStructuresToActEnd(clsSecondaryDataStructureContainer poMoment, ArrayList<clsDataStructureContainerPair> poActivatedInputList) {
//		int nRetVal = 0;
//		int nNumberOfPassedMoments = 0;
//		//Get the first temporal next structure
//		ArrayList<clsSecondaryDataStructure> oNextStructureList = new ArrayList<clsSecondaryDataStructure>();
//		
//		
//		//Get only the first length
//		//TODO AW: Adapt to multiple expectations
//		clsSecondaryDataStructureContainer oCurrentMoment = poMoment;
//		//clsDataStructureContainerPair oCurrentMoment = poMoment;
//		do 
//		{
//			//Get all "HASNEXT" Association structures
//			oNextStructureList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentMoment, ePredicate.HASNEXT.toString(), false);
//			//Get the first expectation
//			if (oNextStructureList.isEmpty()==false) {
//				clsSecondaryDataStructure oNextDS = oNextStructureList.get(0);
//				//Get the whole data structure container
//				clsDataStructureContainerPair oCPair = clsDataStructureTools.getContainerFromList(poActivatedInputList, oNextDS);
//				oCurrentMoment = oCPair.getSecondaryComponent();
//				
//				//Increment the number of images
//				nNumberOfPassedMoments++;
//			}
//		} while (oNextStructureList.isEmpty()==false);
//		
//		
//		nRetVal = nNumberOfPassedMoments;
//		return nRetVal;
//	}
	
	// ============== SECONDARY GENERAL INFORMATION EXTRACTION FUNCTIONS END ==============================//
	
	// ============== CONFIRMATION FUNCTIONS START ==============================//
	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 10.09.2011 16:36:44
//	 *
//	 * @param poContainer
//	 * @param poContent
//	 */
//	public static void setConfirmFactor(clsSecondaryDataStructureContainer poContainer, double prContent) {
//		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASCONFIRMFACTOR.toString(), "CONFIRMFACTOR", String.valueOf(prContent));
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 10.09.2011 16:37:40
//	 *
//	 * @param poContainer
//	 * @return
//	 */
//	public static double getConfirmFactor(clsSecondaryDataStructureContainer poContainer) {
//		double oRetVal = 0.0;
//		
//		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASCONFIRMFACTOR.toString());
//		
//		if (oWPList.isEmpty()==false) {
//			oRetVal = Double.valueOf(oWPList.get(0).getMoContent());
//		}
//			
//		return oRetVal;
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 12.09.2011 09:44:57
//	 *
//	 * @param poContainer
//	 * @param poContent
//	 */
//	public static void setConfirmProgress(clsSecondaryDataStructureContainer poContainer, double prContent) {
//		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASCONFIRMPROGRESS.toString(), "CONFIRMPROGRESS", String.valueOf(prContent));
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 12.09.2011 09:49:24
//	 *
//	 * @param poContainer
//	 * @return
//	 */
//	public static double getConfirmProgress(clsSecondaryDataStructureContainer poContainer) {
//		double oRetVal = 0.0;
//		
//		ArrayList<clsSecondaryDataStructure> oWP = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASCONFIRMPROGRESS.toString());
//		
//		if (oWP.isEmpty()==false) {
//			oRetVal = Double.valueOf(oWP.get(0).getMoContent());
//		}
//			
//		return oRetVal;
//	}
//
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 12.09.2011 09:56:38
//	 *
//	 * @param poContainer
//	 * @param pbContent
//	 */
//	public static void setExpectationAlreadyConfirmed(clsSecondaryDataStructureContainer poContainer, boolean pbContent) {
//		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASBEENCONFIRMED.toString(), "CONFIRMEDEXPECTATION", String.valueOf(pbContent));
//	}
//	
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 12.09.2011 09:56:40
//	 *
//	 * @param poContainer
//	 * @return
//	 */
//	public static boolean getExpectationAlreadyConfirmed(clsSecondaryDataStructureContainer poContainer) {
//		boolean oRetVal = false;
//		
//		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASBEENCONFIRMED.toString());
//		
//		if (oWPList.isEmpty()==false) {
//			oRetVal = true;
//		}
//			
//		return oRetVal;
//	}
	
	// ============== CONFIRMATION FUNCTIONS END ==============================//
	
	// ============== CORRECTIVE AFFECT START ================================//
	
//	/**
//	 * Set the affect to the intention which shall reduce each of the goals, if the progress and confirmation are unsure.
//	 * (wendt)
//	 *
//	 * @since 15.09.2011 10:19:22
//	 *
//	 * @param poIntention
//	 * @param poGoalContainer
//	 * @param prReduceFactorForDrives
//	 * @param rTemporalProgress
//	 * @param rConfirmationProgress
//	 */
//	public static void setReduceAffect(clsSecondaryDataStructureContainer poIntention, clsSecondaryDataStructureContainer poGoalContainer, double prReduceFactorForDrives, double rTemporalProgress, double rConfirmationProgress) {
//		//String oDriveType = clsAffectTools.getDriveType(((clsSecondaryDataStructure)poGoalContainer.getMoDataStructure()).getMoContent());
//		eAffectLevel oDriveIntensityAsAffect = clsImportanceTools.getDriveIntensityAsAffectLevel(((clsSecondaryDataStructure)poGoalContainer.getMoDataStructure()).getMoContent());
//		double rDriveIntensity = (double)oDriveIntensityAsAffect.mnAffectLevel;
//		//6. Calculate the reduce intensity
//		if (rConfirmationProgress>1 || rTemporalProgress > 1) {
//			try {
//				throw new Exception("Error in F51: rConfirmationProgress: " + rConfirmationProgress + ", rTemporalProgress:" + rTemporalProgress);
//			} catch (Exception e) {
//				// TODO (wendt) - Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		double rReduceIntensity = -rDriveIntensity * prReduceFactorForDrives * (1 - rTemporalProgress * rConfirmationProgress);
//		//7. Convert this intensity to an affect value
//		eAffectLevel oReduceIntensity = eAffectLevel.elementAt((int)rReduceIntensity);
//		//8. Do anything if the reduceintensity is significant
//		if (oReduceIntensity.mnAffectLevel!=0) {
//			//9. Copy the goal
//			try {
//				clsWordPresentation oReduceGoal = (clsWordPresentation) ((clsWordPresentation)poGoalContainer.getMoDataStructure()).clone();
//				//10. Replace the old intensity with the new one
//				String oNewContent = ""; //clsAffectTools.replaceAffectIntensity(oReduceGoal.getMoContent(), oReduceIntensity);
//				//String oNewContent = oReduceGoal.getMoContent().replace(oDriveType + ":" + oDriveIntensityAsAffect.toString(), oDriveType + ":" + oReduceIntensity.toString());
//				oReduceGoal.setMoContent(oNewContent);
//				//11. Get the root object
//				clsSecondaryDataStructureContainer oIntention = poIntention;
//				//12. Create an association between the intention and the WP
//				clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(eContentType.AFFECT, oIntention.getMoDataStructure(), oReduceGoal, ePredicate.HASREALITYAFFECT.toString(), 1.0);
//				//13. Add the association to the container
//				oIntention.addMoAssociatedDataStructure(oAssSec);
//				
//			} catch (CloneNotSupportedException e) {
//				// TODO (wendt) - Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * Get the reduceaffect value from the intention
//	 * (wendt)
//	 *
//	 * @since 15.09.2011 10:20:17
//	 *
//	 * @param poIntention
//	 * @return
//	 */
//	public static ArrayList<clsSecondaryDataStructure> getReduceAffect(clsSecondaryDataStructureContainer poIntention) {
//		return clsDataStructureTools.getAttributeOfSecondaryPresentation(poIntention, ePredicate.HASREALITYAFFECT.toString());
//		
//	}
//	
//	/**
//	 * Check at the intention if there shall be a reduce affect calculation. If TRUE, then yes, if FALSE, then not
//	 * (wendt)
//	 *
//	 * @since 22.09.2011 15:35:41
//	 *
//	 * @param poContainer
//	 * @return
//	 */
//	public static boolean getActivateReduceFactor(clsSecondaryDataStructureContainer poIntentionContainer) {
//		boolean oRetVal = false;
//		
//		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poIntentionContainer, ePredicate.ACTIVATEAFFECTREDUCE.toString());
//		
//		if (oWPList.isEmpty()==false) {
//			oRetVal = true;
//		}
//			
//		return oRetVal;
//	}
//	
	
}
