/**
 * CHANGELOG
 *
 * 22.05.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.ListIterator;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * This class contains methods for data search and extraction in meshes
 * 
 * (wendt) 
 * 
 * @author wendt
 * 22.05.2012, 13:01:07
 * 
 */
public class clsMeshTools {
	
	/**
	 * Get the first data structure for a certain association within a data structure
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:25:20
	 *
	 * @param poInput
	 * @param poPredicate
	 * @return
	 */
	public static clsDataStructurePA searchFirstDataStructureOverAssociation(clsWordPresentationMesh poInput, ePredicate poPredicate, boolean pbGetWholeAssociation) { 
		clsDataStructurePA oRetVal = null;
		
		ArrayList<clsDataStructurePA> oList = searchDataStructureOverAssociation(poInput, poPredicate, pbGetWholeAssociation, true);
		if (oList.isEmpty()==false) {
			oRetVal = oList.get(0);
		}
		
		return oRetVal;
	
	}
	
	/**
	 * Search a through a WPM for a certain type of associations based on the predicate. Optinal, only the first
	 * match is searched
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:20:22
	 *
	 * @param poInput
	 * @param poPredicate
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> searchDataStructureOverAssociation(clsWordPresentationMesh poInput, ePredicate poPredicate, boolean pbGetWholeAssociation, boolean pbStopAtFirstMatch) {
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through outer associations
		oRetVal.addAll(searchAssociationList(poInput.getExternalAssociatedContent(), poInput, poPredicate, pbGetWholeAssociation, pbStopAtFirstMatch));
		
		//Go through inner associations
		if (oRetVal.isEmpty()==false) {
			oRetVal.addAll(searchAssociationList(poInput.getAssociatedContent(), poInput, poPredicate, pbGetWholeAssociation, pbStopAtFirstMatch));
		}
			
		return oRetVal;
	}
	
	/**
	 * Search any association list for a certain secondary association and return the other 
	 * element of the association. Stop at first match is optional.
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:21:33
	 *
	 * @param poInputList
	 * @param poPredicate
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsDataStructurePA> searchAssociationList(ArrayList<clsAssociation> poInputList, clsDataStructurePA poThisDataStructure, ePredicate poPredicate, boolean pbGetWholeAssociation, boolean pbStopAtFirstMatch) {
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		for (clsAssociation oAss : poInputList) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate.toString())) {
					if (pbGetWholeAssociation==true) {
						oRetVal.add(oAss);
					} else {
						oRetVal.add(oAss.getTheOtherElement(poThisDataStructure));
					}
					
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}		
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Create a new association secondary between 2 existing objects and add the association to the objects association lists (depending on add state)
	 * 
	 * (wendt)
	 *
	 * @since 25.01.2012 16:09:04
	 *
	 * @param <E> WPM or WP
	 * @param poElementOrigin Always a WPM
	 * @param nOriginAddAssociationState 0: Do not add, 1: Add to internal associations, 2: Add to external associations
	 * @param poElementTarget WPM or WP
	 * @param nTargetAddAssociationState 0: Do not add, 1: Add to internal associations, 2: Add to external associations
	 * @param prWeight
	 * @param poContenType
	 * @param poPredicate
	 * @param pbSwapDirectionAB
	 */
	public static <E extends clsSecondaryDataStructure> void createAssociationSecondary(clsWordPresentationMesh poElementOrigin, int nOriginAddAssociationState, E poElementTarget, int nTargetAddAssociationState, double prWeight, String poContentType, String poPredicate, boolean pbSwapDirectionAB) {
		//Create association
		clsAssociationSecondary oNewAss;
		if (pbSwapDirectionAB==false) {
			oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(poContentType, poElementOrigin, poElementTarget, poPredicate, prWeight);
		} else {
			oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(poContentType, poElementTarget, poElementOrigin, poPredicate, prWeight);
		}
		
		//Process the original Element 
		if (nOriginAddAssociationState==1) {
			poElementOrigin.getAssociatedContent().add(oNewAss);
		} else if (nOriginAddAssociationState==2) {
			poElementOrigin.getExternalAssociatedContent().add(oNewAss);
		}
		//If Associationstate=0, then do nothing
		
		//Add association to the target structure if it is a WPM
		if ((poElementTarget instanceof clsWordPresentationMesh) && (nOriginAddAssociationState!=0)) {
			if (nTargetAddAssociationState==1) {
				((clsWordPresentationMesh)poElementTarget).getAssociatedContent().add(oNewAss);
			} else if (nTargetAddAssociationState==2) {
				((clsWordPresentationMesh)poElementTarget).getExternalAssociatedContent().add(oNewAss);
			}
		}
	}
	
	/**
	 * Get the primary component of a WPM
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 22:01:39
	 *
	 * @param poInput
	 * @return
	 */
	public static clsThingPresentationMesh getPrimaryDataStructureOfWPM(clsWordPresentationMesh poInput) {
		clsThingPresentationMesh oRetVal = null;
		
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationWordPresentation && oAss.getRootElement() instanceof clsThingPresentationMesh) {
				//Add the TPM to the output
				oRetVal = (clsThingPresentationMesh) oAss.getRootElement();
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Set any word presentation to a certain WPM
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 17:04:04
	 *
	 * @param poWPM
	 * @param poAssContentType
	 * @param poAssPredicate
	 * @param poWPContentType
	 * @param poWPContent
	 */
	public static void setWP(clsWordPresentationMesh poWPM, eContentType poAssContentType, ePredicate poAssPredicate, eContentType poWPContentType, String poWPContent) {
		//Get association if exists
		clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociation(poWPM, poAssPredicate, true);
		
		if (oAss==null) {
			//Create new WP
			clsWordPresentation oNewPresentation = clsDataStructureGenerator.generateWP(new clsPair<String, Object>(poWPContentType.toString(), poWPContent));
			
			//Create and add association
			clsMeshTools.createAssociationSecondary(poWPM, 2, oNewPresentation, 0, 1.0, poAssContentType.toString(), poAssPredicate.toString(), false);
			
		} else {
			((clsSecondaryDataStructure)oAss.getTheOtherElement(poWPM)).setMoContent(poWPContent);
		}
		
	}
	
	/**
	 * Remove all associations, which connect 2 images or 2 entities, which are not in the same image. 
	 * Only entities within the same image can use the associationtemporary. All other shall use association primary 
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 17:24:03
	 *
	 * @param poMesh
	 */
	public static void removeAllExternalAssociations(clsThingPresentationMesh poMesh) {
		
		ListIterator<clsAssociation> liList = poMesh.getExternalMoAssociatedContent().listIterator();
		while (liList.hasNext()) {
			clsAssociation oAss = liList.next();
			if (oAss instanceof clsAssociationPrimary) {
				liList.remove();
			}
		}
	}
	
	/**
	 * Add associations to both elements, if they only exist in one of the elements.
	 * This is especially useful at the image associations
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 11:43:23
	 *
	 * @param poInput
	 */
	public static void setInverseAssociations(clsWordPresentationMesh poInput) {
		//Go through the associations and search for the other elements in the external associations
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				clsSecondaryDataStructure oOtherElement = (clsSecondaryDataStructure) oAss.getTheOtherElement(poInput);
				//At the other element, add this association to its external associations if it does not already exist
				if ((oOtherElement instanceof clsWordPresentationMesh) && 
						(clsDataStructureTools.findPADataStructureInArrayList(((clsWordPresentationMesh) oOtherElement).getExternalAssociatedContent(), oAss)==null)) {
					((clsWordPresentationMesh)oOtherElement).getExternalAssociatedContent().add(oAss);
				}
			}
		}
		
		
		
		
	}
}