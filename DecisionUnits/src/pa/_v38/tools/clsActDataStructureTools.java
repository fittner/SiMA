/**
 * CHANGELOG
 *
 * 23.05.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
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
	 * Create a prediction from an intention
	 * 
	 * (wendt)
	 * @since 22.05.2012 12:32:00
	 *
	 * @param poIntention
	 * @return
	 */
	public static clsWordPresentationMesh createActDataStructure(clsWordPresentationMesh poIntention) {
		//If yes, then create a prediction
		//Create an act structure for that act
		clsWordPresentationMesh oPrediction = clsDataStructureGenerator.generateWPM(new clsPair<String, Object>(eContentType.PREDICTION.toString(), poIntention.getMoContent()), new ArrayList<clsAssociation>());
		//Create the association to the intention from the prediction but not the other way around. In that way the mesh is independent of the meta structure
		clsMeshTools.createAssociationSecondary(oPrediction, 1, poIntention, 0, 1.0, eContentType.INTENTION.toString(), ePredicate.HASINTENTION.toString(), false);
	
		return oPrediction;
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
		clsWordPresentationMesh oRetVal = null;
		
		//Check if there is an intention
		for (clsWordPresentationMesh oPrediction : poPredictionList) {
			clsWordPresentationMesh oIntention = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociation(oPrediction, ePredicate.HASINTENTION, false); 
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
		clsWordPresentationMesh oRetVal = null;
		
		oRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociation(poPrediction, ePredicate.HASINTENTION, false);
		
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
		
		oRetVal = (clsAssociationSecondary) clsMeshTools.searchFirstDataStructureOverAssociation(poPrediction, ePredicate.HASINTENTION, false);
		
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
			clsMeshTools.createAssociationSecondary(poAct, 1, poNewIntention, 0, 1.0, eContentType.ASSOCIATIONSECONDARY.toString(), ePredicate.HASINTENTION.toString(), false);
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
		clsWordPresentationMesh oRetVal = null;
		
		oRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociation(poPrediction, ePredicate.HASMOMENT, false);
		
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
		
		oRetVal = (clsAssociationSecondary) clsMeshTools.searchFirstDataStructureOverAssociation(poPrediction, ePredicate.HASMOMENT, true);
		
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
			clsMeshTools.createAssociationSecondary(poAct, 1, poNewMoment, 0, 1.0, eContentType.ASSOCIATIONSECONDARY.toString(), ePredicate.HASMOMENT.toString(), false);
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
		clsWordPresentationMesh oRetVal = null;
		
		oRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociation(poPrediction, ePredicate.HASEXPECTATION, false);
		
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
		
		oRetVal = (clsAssociationSecondary) clsMeshTools.searchFirstDataStructureOverAssociation(poPrediction, ePredicate.HASMOMENT, true);
		
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
			clsMeshTools.createAssociationSecondary(poAct, 1, poNewExpectation, 0, 1.0, eContentType.ASSOCIATIONSECONDARY.toString(), ePredicate.HASEXPECTATION.toString(), false);
		}
	}
	
	
}
