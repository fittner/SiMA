/**
 * CHANGELOG
 *
 * 22.05.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
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
public class clsExtractionTools {
	
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
	public static clsSecondaryDataStructure searchFirstDataStructureOverAssociation(clsWordPresentationMesh poInput, ePredicate poPredicate) {
		clsSecondaryDataStructure oRetVal = null;
		
		ArrayList<clsSecondaryDataStructure> oList = searchDataStructureOverAssociation(poInput, poPredicate, true);
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
	public static ArrayList<clsSecondaryDataStructure> searchDataStructureOverAssociation(clsWordPresentationMesh poInput, ePredicate poPredicate, boolean pbStopAtFirstMatch) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		//Go through outer associations
		oRetVal.addAll(searchAssociationList(poInput.getExternalAssociatedContent(), poPredicate, pbStopAtFirstMatch));
		
		//Go through inner associations
		if (oRetVal.isEmpty()==false) {
			oRetVal.addAll(searchAssociationList(poInput.getAssociatedContent(), poPredicate, pbStopAtFirstMatch));
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
	private static ArrayList<clsSecondaryDataStructure> searchAssociationList(ArrayList<clsAssociation> poInputList, ePredicate poPredicate, boolean pbStopAtFirstMatch) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		for (clsAssociation oAss : poInputList) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate.toString())) {
					if (oAss.getLeafElement().equals(poInputList)==false) {
						oRetVal.add((clsSecondaryDataStructure)oAss.getLeafElement());
					} else {
						oRetVal.add((clsSecondaryDataStructure)oAss.getRootElement());
					}
					
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}		
			}
		}
		
		return oRetVal;
	}
	

}