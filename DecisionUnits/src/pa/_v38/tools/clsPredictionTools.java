/**
 * CHANGELOG
 *
 * 15.09.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * This class contains all functions, which are used with the data type clsPrediction and are used in more than one module
 * 
 * @author wendt
 * 15.09.2011, 09:44:49
 * 
 */
public class clsPredictionTools {
	
	
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
	public static int countSubStructuresToActEnd(clsSecondaryDataStructureContainer poMoment, ArrayList<clsDataStructureContainer> poActivatedInputList) {
		int nRetVal = 0;
		int nNumberOfPassedMoments = 0;
		//Get the first temporal next structure
		ArrayList<clsSecondaryDataStructure> oNextStructureList = new ArrayList<clsSecondaryDataStructure>();
		
		
		//Get only the first length
		//TODO AW: Adapt to multiple expectations
		clsSecondaryDataStructureContainer oCurrentMoment = poMoment;
		do 
		{
			//Get all "HASNEXT" Association structures
			oNextStructureList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oCurrentMoment, ePredicate.HASNEXT.toString(), false);
			//Get the first expectation
			if (oNextStructureList.isEmpty()==false) {
				clsSecondaryDataStructure oNextDS = oNextStructureList.get(0);
				//Get the whole data structure container
				oCurrentMoment = (clsSecondaryDataStructureContainer) clsDataStructureTools.getContainerFromList(poActivatedInputList, oNextDS);
				
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
		double rReduceIntensity = -rDriveIntensity * prReduceFactorForDrives * (1 - rTemporalProgress * rConfirmationProgress);
		//7. Convert this intensity to an affect value
		eAffectLevel oReduceIntensity = eAffectLevel.elementAt((int)rReduceIntensity);
		//8. Do anything if the reduceintensity is significant
		if (oReduceIntensity.mnAffectLevel!=0) {
			//9. Copy the goal
			try {
				clsWordPresentation oReduceGoal = (clsWordPresentation) ((clsWordPresentation)poGoalContainer.getMoDataStructure()).clone();
				//10. Replace the old intensity with the new one
				String oNewContent = clsAffectTools.replaceAffectIntensity(oReduceGoal.getMoContent(), oReduceIntensity);
				//String oNewContent = oReduceGoal.getMoContent().replace(oDriveType + ":" + oDriveIntensityAsAffect.toString(), oDriveType + ":" + oReduceIntensity.toString());
				oReduceGoal.setMoContent(oNewContent);
				//11. Get the root object
				clsSecondaryDataStructureContainer oIntention = poIntention;
				//12. Create an association between the intention and the WP
				clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("REALITYAFFECT", oIntention.getMoDataStructure(), oReduceGoal, ePredicate.HASREALTYAFFECT.toString(), 1.0);
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
		return clsDataStructureTools.getAttributeOfSecondaryPresentation(poIntention, ePredicate.HASREALTYAFFECT.toString());
		
	}
	
	
}
