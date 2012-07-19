/**
 * CHANGELOG
 *
 * 23.05.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.05.2012, 14:09:07
 * 
 */
public class clsActDataStructureTools {
	
	/**
	 * Create a new supportive data structure for acts
	 * 
	 * (wendt)
	 *
	 * @since 18.07.2012 21:15:06
	 *
	 * @param poActContent
	 * @param poIntention
	 * @return
	 */
	public static clsWordPresentationMesh createActDataStructure(clsWordPresentationMesh poIntention) {
		//Create identifiyer. All goals must have the content type "GOAL"
		clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.ACT);
				
		//Create the basic goal structure
		clsWordPresentationMesh oResult = new clsWordPresentationMesh(oDataStructureIdentifier, new ArrayList<clsAssociation>(), poIntention.getMoContent());
		
		setIntention(oResult, poIntention);
		
		return oResult;
	}

	
	/**
	 * Check if the intention exists in the predictions
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 20:50:26
	 *
	 * @param poPrediction
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh checkIfIntentionExistsInActList(ArrayList<clsWordPresentationMesh> poPredictionList, clsWordPresentationMesh poImage) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		//Check if there is an intention
		for (clsWordPresentationMesh oPrediction : poPredictionList) {
			clsWordPresentationMesh oIntention = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociationWPM(oPrediction, ePredicate.HASINTENTION, 2, false); 
			if ((oIntention!=null) && (oIntention.getMoDS_ID()==poImage.getMoDS_ID())) {
				oRetVal = oPrediction;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Check if the moment exists in the predictions
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 20:50:26
	 *
	 * @param poPrediction
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getIntention(clsWordPresentationMesh poPrediction) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		oRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASINTENTION, 2, false);
		
		return oRetVal;
	}
	
	/**
	 * Get the association of the moment from a prediction
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 14:38:09
	 *
	 * @param poPrediction
	 * @return
	 */
	public static clsAssociationSecondary getIntentionAssociation(clsWordPresentationMesh poPrediction) {
		clsAssociationSecondary oRetVal = null;
		
		oRetVal = (clsAssociationSecondary) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASINTENTION, 2, false);
		
		return oRetVal;
	}
	
	/**
	 * Set intention
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 15:35:45
	 *
	 * @param poAct
	 * @param poNewMoment
	 */
	public static void setIntention(clsWordPresentationMesh poAct, clsWordPresentationMesh poNewIntention) {
		//Get association
		clsAssociationSecondary oAss = getIntentionAssociation(poAct); 
		if (oAss!=null) {
			//Association exists, replace data structure
			oAss.setLeafElement(poNewIntention);
		} else {
			//Add new Association
			clsMeshTools.createAssociationSecondary(poAct, 1, poNewIntention, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASINTENTION, false);
		}
	}
	
	/**
	 * Check if the moment exists in the predictions
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 20:50:26
	 *
	 * @param poPrediction
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getMoment(clsWordPresentationMesh poPrediction) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		oRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASMOMENT, 2, false);
		
		return oRetVal;
	}
	
	/**
	 * Get the association of the moment from a prediction
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 14:38:09
	 *
	 * @param poPrediction
	 * @return
	 */
	public static clsAssociationSecondary getMomentAssociation(clsWordPresentationMesh poPrediction) {
		clsAssociationSecondary oRetVal = null;
		
		oRetVal = (clsAssociationSecondary) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASMOMENT, 2, true);
		
		return oRetVal;
	}
	
	/**
	 * Set moment
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 15:35:45
	 *
	 * @param poAct
	 * @param poNewMoment
	 */
	public static void setMoment(clsWordPresentationMesh poAct, clsWordPresentationMesh poNewMoment) {
		//Get association
		clsAssociationSecondary oAss = getMomentAssociation(poAct); 
		if (oAss!=null) {
			//Association exists, replace data structure
			oAss.setLeafElement(poNewMoment);
		} else {
			//Add new Association
			clsMeshTools.createAssociationSecondary(poAct, 1, poNewMoment, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASMOMENT, false);
		}
	}
	
	/**
	 * Get the expectation
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 20:50:26
	 *
	 * @param poPrediction
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getExpectation(clsWordPresentationMesh poPrediction) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		oRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASEXPECTATION, 2, false);
		
		return oRetVal;
	}
	
	/**
	 * Get the association of the expectation from a prediction
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 14:38:09
	 *
	 * @param poPrediction
	 * @return
	 */
	public static clsAssociationSecondary getExpectationAssociation(clsWordPresentationMesh poPrediction) {
		clsAssociationSecondary oRetVal = null;
		
		oRetVal = (clsAssociationSecondary) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASMOMENT, 2, true);
		
		return oRetVal;
	}
	
	/**
	 * Set expectation
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 15:35:45
	 *
	 * @param poAct
	 * @param poNewMoment
	 */
	public static void setExpectation(clsWordPresentationMesh poAct, clsWordPresentationMesh poNewExpectation) {
		//Get association
		clsAssociationSecondary oAss = getExpectationAssociation(poAct); 
		if (oAss!=null) {
			//Association exists, replace data structure
			oAss.setLeafElement(poNewExpectation);
		} else {
			//Add new Association
			clsMeshTools.createAssociationSecondary(poAct, 1, poNewExpectation, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASEXPECTATION, false);
		}
	}
	
	/**
	 * Get the image with the highest PI match from the sub images of an act within an act data structure
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 13:04:02
	 *
	 * @param poAct
	 * @param prMomentActivationThreshold 
	 * @return
	 */
	public static clsWordPresentationMesh getMomentWithHighestPIMatch(clsWordPresentationMesh poAct, double prMomentActivationThreshold) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(poAct);
				
		//clsWordPresentationMesh oFirstImage = clsActTools.getFirstSituationFromIntention(oIntention);
		
		double rMaxPIMatch = 0.0;
		
		ArrayList<clsWordPresentationMesh> oSubImageList = clsActTools.getAllSubImages(oIntention);
		
		for (clsWordPresentationMesh oSubImage : oSubImageList) {
			double rCurrentPIMatch = clsActTools.getSecondaryMatchValueToPI(oSubImage);
						
			if (rCurrentPIMatch>rMaxPIMatch && rCurrentPIMatch>=prMomentActivationThreshold) {
				rMaxPIMatch = rCurrentPIMatch;
				oResult = oSubImage;
			}
		}
		
		return oResult;
		
	}
	
	
}
