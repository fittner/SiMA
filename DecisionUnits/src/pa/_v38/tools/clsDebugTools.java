/**
 * CHANGELOG
 *
 * 25.02.2013 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.ListIterator;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.02.2013, 16:35:21
 * 
 */
public class clsDebugTools {
	public static void correctErronerusAssociations(clsWordPresentationMesh poSource) {
		//Get all associations of the source element
		ArrayList<clsAssociationSecondary> oSourceAssList = getSecondaryAssociations(poSource);
		//Get all other elements
		ArrayList<clsWordPresentationMesh> oOtherWPMList = new ArrayList<clsWordPresentationMesh>();
		for (clsAssociationSecondary oSourceAss : oSourceAssList) {
			clsSecondaryDataStructure oOtherDS = (clsSecondaryDataStructure) oSourceAss.getTheOtherElement(poSource);
			if (oOtherDS instanceof clsWordPresentationMesh) {
				oOtherWPMList.add((clsWordPresentationMesh) oOtherDS);
			}
		}
		
		//For each of those elements, get their associations
		for (clsAssociationSecondary oSourceAss : oSourceAssList) {
			for (clsWordPresentationMesh oOtherWPM : oOtherWPMList) {
				ArrayList<clsAssociationSecondary> oOtherWPMAssList = getSecondaryAssociations(oOtherWPM);
				ListIterator<clsAssociationSecondary> oList = oOtherWPMAssList.listIterator();
				//ArrayList<clsPair<clsAssociation, clsAssociation>> oReplaceList = new ArrayList<clsPair<clsAssociation, clsAssociation>>();
				clsAssociation oFoundAss = null;
				while (oList.hasNext()) {
					clsAssociationSecondary oOtherSecondaryAss = oList.next();
					if (oSourceAss!=oOtherSecondaryAss && 
							oSourceAss.getRootElement()==oOtherSecondaryAss.getRootElement() &&
							oSourceAss.getLeafElement()==oOtherSecondaryAss.getLeafElement() &&
							oSourceAss.getMoPredicate()==oOtherSecondaryAss.getMoPredicate()) {
						
						oFoundAss = oOtherSecondaryAss;
						break;
					}
				}
				
				if (oFoundAss!=null) {
					oOtherWPM.getExternalAssociatedContent().remove(oFoundAss);
					oOtherWPM.getExternalAssociatedContent().add(oSourceAss);
				}
			}
		}
		
		
		//If the association elements are the same but the association instance is different exchange the instance in the target
		
	}
	
	private static ArrayList<clsAssociationSecondary> getSecondaryAssociations(clsWordPresentationMesh poMesh) {
		ArrayList<clsAssociationSecondary> oResult = new ArrayList<clsAssociationSecondary>();
		
		//Check associations. If associations are moved, the all have to be the same
		for (clsAssociation oAss : poMesh.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				oResult.add((clsAssociationSecondary) oAss);
			}
		}
		
		return oResult;
	}
	
	
}
