/**
 * CHANGELOG
 *
 * 15.09.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
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
	public static ArrayList<clsWordPresentationMesh> categorizeMeshInActStructures(ArrayList<clsWordPresentationMesh> poSingleList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsWordPresentationMesh oRI : poSingleList) {
			//1. Extract the super structure of the RI
			clsWordPresentationMesh oSuperStructure = clsActTools.getSuperStructure(oRI);
			
			//2. Check if the super structure exists in a prediction
			
			//3.a If yes, then check if the match of the current moment, if exists, is lower than this image
			//3.a.1 if yes, then replace the moment with this image
			//3.a.2 if no, do nothing
			
			//3.b If no, create a new prediction and add the intention as an intention and this RI as the current moment
			
			//Check if the oRI is an intention
			//if (getCheckIntentionIntentions(oRI)==true) {
			
			//}
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
	private static boolean checkIfIntention(clsWordPresentationMesh poInput) {
		boolean bRetVal = false;
		
		//Get all external associations of each memory
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			//If this container is a leaf element of an associationsecondary with the predicate "hasSuper" and is the object of that association
			if (oAss instanceof clsAssociationSecondary) {
				//An intention is recognized if the image is the Leaf element of a Hierarchical association (hasSuper)
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASSUPER.toString()) && (oAss.getLeafElement().equals(poInput))) {
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
	private static boolean checkIfEvent(clsWordPresentationMesh poInput) {
		boolean bRetVal = false;
		boolean bHasSuper = false;
		boolean bHasNext = false;
		
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			//If this container is a leaf element of an associationsecondary with the predicate "hasSuper" and is the object of that association
			if (oAss instanceof clsAssociationSecondary) {
				//An intention is recognized if the image is the Leaf element of a Hierarchical association (hasSuper)
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASSUPER.toString())) {
					//The structure is a super or has a super
					bHasSuper=true;
					if (bHasNext==true) {
						//One result was found, then break
						bRetVal=true;
						break;
					}
				} else if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASNEXT.toString())) {
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
	 * Create a prediction from an intention
	 * 
	 * (wendt)
	 * @since 22.05.2012 12:32:00
	 *
	 * @param poIntention
	 * @return
	 */
	private static clsWordPresentationMesh createPrediction(clsWordPresentationMesh poIntention) {
		//If yes, then create a prediction
		//Create an act structure for that act
		clsWordPresentationMesh oPrediction = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(eContentType.PREDICTION.toString(), poIntention.getMoContent()), new ArrayList<clsAssociation>());
		//Create the association to the intention from the prediction but not the other way around. In that way the mesh is independent of the meta structure
		clsDataStructureTools.createAssociationSecondary(oPrediction, 1, poIntention, 0, 1.0, eContentType.INTENTION.toString(), ePredicate.HASINTENTION.toString(), false);
	
		return oPrediction;
	}
	
	/**
	 * Get the super structure of a data structure. If the input is its own super structure, then
	 * return itself.
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:27:40
	 *
	 * @param poInput
	 * @return
	 */
	private static clsWordPresentationMesh getSuperStructure(clsWordPresentationMesh poInput) {
		clsWordPresentationMesh oRetVal = poInput;
		
		clsWordPresentationMesh oSuperStructure = (clsWordPresentationMesh) clsExtractionTools.searchFirstDataStructureOverAssociation(poInput, ePredicate.HASSUPER);
		
		if (oSuperStructure!=null) {
			oRetVal = oSuperStructure;
		}
		
		return oRetVal;
	}
	
	private static boolean checkIfExistsInPrediction(ArrayList<clsWordPresentationMesh> poInput) {
		boolean bRetVal = false;
		
		//for (clsAssociation clsWordPrese)
		
		return bRetVal;
	}
	
	
	
	// ============== ACT CATEGORIZATION START ==============================//
	
	// ============== PROGRESS FUNCTIONS START ==============================//
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 09.09.2011 21:57:13
	 *
	 * @param poContainer
	 * @param poPredicate
	 * @param poContentType
	 * @param poContent
	 * @param pbReplace
	 */
	public static void setTemporalProgressFactor(clsSecondaryDataStructureContainer poContainer, double prContent) {
		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASTEMPORALPROGRESSFACTOR.toString(), "PROGRESSFACTOR", String.valueOf(prContent));
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 09.09.2011 21:57:15
	 *
	 * @param poContainer
	 * @param poPredicate
	 * @return
	 */
	public static double getTemporalProgressFactor(clsSecondaryDataStructureContainer poContainer) {
		double oRetVal = 0;
		
		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASTEMPORALPROGRESSFACTOR.toString());
		
		if (oWPList.isEmpty()==false) {
			oRetVal = Double.valueOf(oWPList.get(0).getMoContent());
		}
			
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 09.09.2011 22:27:25
	 *
	 * @param poContainer
	 * @param poContent
	 */
	public static void setTemporalProgress(clsSecondaryDataStructureContainer poContainer, double prContent) {
		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASTEMPORALPROGRESS.toString(), "PROGRESS", String.valueOf(prContent));
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 10.09.2011 17:23:41
	 *
	 * @param poContainer
	 * @return
	 */
	public static double getTemporalProgress(clsSecondaryDataStructureContainer poContainer) {
		double oRetVal = 0.0;
		
		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASTEMPORALPROGRESS.toString());
		
		if (oWPList.isEmpty()==false) {
			oRetVal = Double.valueOf(oWPList.get(0).getMoContent());
		}
			
		return oRetVal;
	}
	
	// ============== PROGRESS FUNCTIONS END ==============================//
	
	// ============== SECONDARY GENERAL INFORMATION EXTRACTION FUNCTIONS START ==============================//
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 09.09.2011 21:57:11
	 *
	 * @param poIntention
	 * @return
	 */
	public static int countAllSubStructures(clsSecondaryDataStructureContainer poIntention) {
		int nRetVal = 0;
		
		ArrayList<clsSecondaryDataStructure> oSubImages = clsDataStructureTools.getDSFromSecondaryAssInContainer(poIntention, ePredicate.ISA.toString(), true);
		nRetVal = oSubImages.size();
		
		return nRetVal;
	}
	
	/**
	 * From a certain image in an act, get the number of structures, until the end of the act is reached. 
	 * This function is used in F51
	 * (wendt)
	 *
	 * @since 11.09.2011 14:15:11
	 *
	 * @param poMoment
	 * @param poActivatedInputList
	 * @return
	 */
	public static int countSubStructuresToActEnd(clsSecondaryDataStructureContainer poMoment, ArrayList<clsDataStructureContainerPair> poActivatedInputList) {
		int nRetVal = 0;
		int nNumberOfPassedMoments = 0;
		//Get the first temporal next structure
		ArrayList<clsSecondaryDataStructure> oNextStructureList = new ArrayList<clsSecondaryDataStructure>();
		
		
		//Get only the first length
		//TODO AW: Adapt to multiple expectations
		clsSecondaryDataStructureContainer oCurrentMoment = poMoment;
		//clsDataStructureContainerPair oCurrentMoment = poMoment;
		do 
		{
			//Get all "HASNEXT" Association structures
			oNextStructureList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentMoment, ePredicate.HASNEXT.toString(), false);
			//Get the first expectation
			if (oNextStructureList.isEmpty()==false) {
				clsSecondaryDataStructure oNextDS = oNextStructureList.get(0);
				//Get the whole data structure container
				clsDataStructureContainerPair oCPair = clsDataStructureTools.getContainerFromList(poActivatedInputList, oNextDS);
				oCurrentMoment = oCPair.getSecondaryComponent();
				
				//Increment the number of images
				nNumberOfPassedMoments++;
			}
		} while (oNextStructureList.isEmpty()==false);
		
		
		nRetVal = nNumberOfPassedMoments;
		return nRetVal;
	}
	
	// ============== SECONDARY GENERAL INFORMATION EXTRACTION FUNCTIONS END ==============================//
	
	// ============== CONFIRMATION FUNCTIONS START ==============================//
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 10.09.2011 16:36:44
	 *
	 * @param poContainer
	 * @param poContent
	 */
	public static void setConfirmFactor(clsSecondaryDataStructureContainer poContainer, double prContent) {
		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASCONFIRMFACTOR.toString(), "CONFIRMFACTOR", String.valueOf(prContent));
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 10.09.2011 16:37:40
	 *
	 * @param poContainer
	 * @return
	 */
	public static double getConfirmFactor(clsSecondaryDataStructureContainer poContainer) {
		double oRetVal = 0.0;
		
		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASCONFIRMFACTOR.toString());
		
		if (oWPList.isEmpty()==false) {
			oRetVal = Double.valueOf(oWPList.get(0).getMoContent());
		}
			
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 09:44:57
	 *
	 * @param poContainer
	 * @param poContent
	 */
	public static void setConfirmProgress(clsSecondaryDataStructureContainer poContainer, double prContent) {
		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASCONFIRMPROGRESS.toString(), "CONFIRMPROGRESS", String.valueOf(prContent));
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 09:49:24
	 *
	 * @param poContainer
	 * @return
	 */
	public static double getConfirmProgress(clsSecondaryDataStructureContainer poContainer) {
		double oRetVal = 0.0;
		
		ArrayList<clsSecondaryDataStructure> oWP = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASCONFIRMPROGRESS.toString());
		
		if (oWP.isEmpty()==false) {
			oRetVal = Double.valueOf(oWP.get(0).getMoContent());
		}
			
		return oRetVal;
	}

	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 09:56:38
	 *
	 * @param poContainer
	 * @param pbContent
	 */
	public static void setExpectationAlreadyConfirmed(clsSecondaryDataStructureContainer poContainer, boolean pbContent) {
		clsDataStructureTools.setAttributeWordPresentation(poContainer, ePredicate.HASBEENCONFIRMED.toString(), "CONFIRMEDEXPECTATION", String.valueOf(pbContent));
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 09:56:40
	 *
	 * @param poContainer
	 * @return
	 */
	public static boolean getExpectationAlreadyConfirmed(clsSecondaryDataStructureContainer poContainer) {
		boolean oRetVal = false;
		
		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.HASBEENCONFIRMED.toString());
		
		if (oWPList.isEmpty()==false) {
			oRetVal = true;
		}
			
		return oRetVal;
	}
	
	// ============== CONFIRMATION FUNCTIONS END ==============================//
	
	// ============== CORRECTIVE AFFECT START ================================//
	
	/**
	 * Set the affect to the intention which shall reduce each of the goals, if the progress and confirmation are unsure.
	 * (wendt)
	 *
	 * @since 15.09.2011 10:19:22
	 *
	 * @param poIntention
	 * @param poGoalContainer
	 * @param prReduceFactorForDrives
	 * @param rTemporalProgress
	 * @param rConfirmationProgress
	 */
	public static void setReduceAffect(clsSecondaryDataStructureContainer poIntention, clsSecondaryDataStructureContainer poGoalContainer, double prReduceFactorForDrives, double rTemporalProgress, double rConfirmationProgress) {
		//String oDriveType = clsAffectTools.getDriveType(((clsSecondaryDataStructure)poGoalContainer.getMoDataStructure()).getMoContent());
		eAffectLevel oDriveIntensityAsAffect = clsAffectTools.getDriveIntensityAsAffectLevel(((clsSecondaryDataStructure)poGoalContainer.getMoDataStructure()).getMoContent());
		double rDriveIntensity = (double)oDriveIntensityAsAffect.mnAffectLevel;
		//6. Calculate the reduce intensity
		if (rConfirmationProgress>1 || rTemporalProgress > 1) {
			try {
				throw new Exception("Error in F51: rConfirmationProgress: " + rConfirmationProgress + ", rTemporalProgress:" + rTemporalProgress);
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		double rReduceIntensity = -rDriveIntensity * prReduceFactorForDrives * (1 - rTemporalProgress * rConfirmationProgress);
		//7. Convert this intensity to an affect value
		eAffectLevel oReduceIntensity = eAffectLevel.elementAt((int)rReduceIntensity);
		//8. Do anything if the reduceintensity is significant
		if (oReduceIntensity.mnAffectLevel!=0) {
			//9. Copy the goal
			try {
				clsWordPresentation oReduceGoal = (clsWordPresentation) ((clsWordPresentation)poGoalContainer.getMoDataStructure()).clone();
				//10. Replace the old intensity with the new one
				String oNewContent = ""; //clsAffectTools.replaceAffectIntensity(oReduceGoal.getMoContent(), oReduceIntensity);
				//String oNewContent = oReduceGoal.getMoContent().replace(oDriveType + ":" + oDriveIntensityAsAffect.toString(), oDriveType + ":" + oReduceIntensity.toString());
				oReduceGoal.setMoContent(oNewContent);
				//11. Get the root object
				clsSecondaryDataStructureContainer oIntention = poIntention;
				//12. Create an association between the intention and the WP
				clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("REALITYAFFECT", oIntention.getMoDataStructure(), oReduceGoal, ePredicate.HASREALITYAFFECT.toString(), 1.0);
				//13. Add the association to the container
				oIntention.addMoAssociatedDataStructure(oAssSec);
				
			} catch (CloneNotSupportedException e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the reduceaffect value from the intention
	 * (wendt)
	 *
	 * @since 15.09.2011 10:20:17
	 *
	 * @param poIntention
	 * @return
	 */
	public static ArrayList<clsSecondaryDataStructure> getReduceAffect(clsSecondaryDataStructureContainer poIntention) {
		return clsDataStructureTools.getAttributeOfSecondaryPresentation(poIntention, ePredicate.HASREALITYAFFECT.toString());
		
	}
	
	/**
	 * Check at the intention if there shall be a reduce affect calculation. If TRUE, then yes, if FALSE, then not
	 * (wendt)
	 *
	 * @since 22.09.2011 15:35:41
	 *
	 * @param poContainer
	 * @return
	 */
	public static boolean getActivateReduceFactor(clsSecondaryDataStructureContainer poIntentionContainer) {
		boolean oRetVal = false;
		
		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poIntentionContainer, ePredicate.ACTIVATEAFFECTREDUCE.toString());
		
		if (oWPList.isEmpty()==false) {
			oRetVal = true;
		}
			
		return oRetVal;
	}
	
	
}
