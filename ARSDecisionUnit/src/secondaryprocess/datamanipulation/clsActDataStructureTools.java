/**
 * CHANGELOG
 *
 * 23.05.2012 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePredicate;

import org.slf4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationSecondary;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 23.05.2012, 14:09:07
 * 
 */
public class clsActDataStructureTools {
    
    private static Logger log = clsLogger.getLog("Acts");
	
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
		clsWordPresentationMesh oResult = new clsWordPresentationMesh(oDataStructureIdentifier, new ArrayList<clsAssociation>(), poIntention.getContent());
		
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
			if ((oIntention!=null) && (oIntention.getDS_ID()==poImage.getDS_ID())) {
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
		
		clsWordPresentationMesh oPrelRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASINTENTION, 2, false);
		
		if (oPrelRetVal!=null) {
			oRetVal = oPrelRetVal;
		}
		
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

		clsDataStructurePA oDS = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASINTENTION, 2, false);
		if (oDS!=null) {
			oRetVal = (clsAssociationSecondary) oDS;
		}
		
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
     * Set intention
     * 
     * (wendt)
     *
     * @since 23.05.2012 15:35:45
     *
     * @param poAct
     * @param poNewMoment
     */
    public static void setContext(clsWordPresentationMesh poAct, clsWordPresentationMesh poNewContext) {
        //Get intention
        clsWordPresentationMesh Intention = clsActDataStructureTools.getIntention(poAct);
        
        clsMeshTools.setNonUniquePredicateWPM(Intention, ePredicate.HASCONTEXT, poNewContext, false);
        
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
    public static clsWordPresentationMesh getContext(clsWordPresentationMesh poAct) {
        clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
        
        //Get intention
        clsWordPresentationMesh Intention = clsActDataStructureTools.getIntention(poAct);
        
        clsWordPresentationMesh oPrelRetVal = (clsWordPresentationMesh) clsMeshTools.searchFirstDataStructureOverAssociationWPM(Intention, ePredicate.HASCONTEXT, 2, false);
        
        if (oPrelRetVal!=null) {
            oRetVal = oPrelRetVal;
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
	public static clsWordPresentationMesh getMoment(clsWordPresentationMesh poPrediction) {
		clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
		
		clsDataStructurePA oDS = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASMOMENT, 2, false);
		if (oDS!=null) {
			oRetVal = (clsWordPresentationMesh) oDS;
		}
		
		return oRetVal;
	}
	
	
    public static clsWordPresentationMesh getGoalObject(clsWordPresentationMesh poGoal) {
        clsWordPresentationMesh oGoalnObject = null;
        
        if(poGoal != null && !poGoal.isNullObject()) {
            oGoalnObject = clsMeshTools.getUniquePredicateWPM(poGoal, ePredicate.HASGOALOBJECT);
        }
        
        return oGoalnObject;
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
		
		clsDataStructurePA oDS = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASMOMENT, 2, true);
		if (oDS!=null) {
			oRetVal = (clsAssociationSecondary) oDS;
		}
		
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
		
		clsDataStructurePA oDS = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASEXPECTATION, 2, false);
		if (oDS!=null) {
			oRetVal = (clsWordPresentationMesh) oDS;
		}
		
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
		
		clsDataStructurePA oDS = clsMeshTools.searchFirstDataStructureOverAssociationWPM(poPrediction, ePredicate.HASEXPECTATION, 2, true);
		if (oDS!=null) {
			oRetVal = (clsAssociationSecondary) oDS;
		}
		
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
	 * Get the image with the highest PI match from the sub images of an act within an act data structure. 
	 * Multiple structures can be added if there is more than one PI match with the same value
	 * 
	 * (wendt)
	 *
	 * @since 19.07.2012 13:04:02
	 *
	 * @param poAct
	 * @param prMomentActivationThreshold 
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getMomentWithHighestPIMatch(clsWordPresentationMesh poAct, double prMomentActivationThreshold) {
		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
		
		clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(poAct);
		
		double rMaxPIMatch = 0.0;
		
		ArrayList<clsWordPresentationMesh> oSubImageList = clsActTools.getAllSubImages(oIntention);
		
		for (clsWordPresentationMesh oSubImage : oSubImageList) {
			double rCurrentPIMatch = clsActTools.getPIMatch(oSubImage);
//			if (oSubImage.getContent().equals("A12_EAT_CAKE_L01_I01")||oSubImage.getContent().equals("A12_EAT_CAKE_L01_I02"))
//			{
//                oResult.add(oSubImage);  
//                return oResult;
//			}
//			else
			{
    			if (rCurrentPIMatch>rMaxPIMatch && rCurrentPIMatch>=prMomentActivationThreshold) {
    				rMaxPIMatch = rCurrentPIMatch;
    				oResult.clear();
    				oResult.add(oSubImage);
    			} else if (rCurrentPIMatch==rMaxPIMatch && rCurrentPIMatch>=prMomentActivationThreshold) {
    				oResult.add(oSubImage);
    			}
			}
		}
		
		return oResult;
		
	}
	
	
}
