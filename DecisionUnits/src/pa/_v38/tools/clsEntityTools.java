/**
 * CHANGELOG
 *
 * 07.07.2012 wendt - File created
 *
 */
package pa._v38.tools;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.eRadius;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 07.07.2012, 07:44:13
 * 
 */
public class clsEntityTools {
	
	
	
	/**
	 * Get the position coordinates in X, Y integers from word presentations of a data structure
	 * (wendt)
	 *
	 * @since 01.10.2011 09:50:49
	 *
	 * @param poDS
	 * @param poImageContainer
	 * @return
	 */
	public static clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> getPosition(clsWordPresentationMesh poDS) {
		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oRetVal = new clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>(poDS, null, null);
		
		//Search for xy compontents
		ePhiPosition X = null;	//default error value
		eRadius Y = null;

		for (clsAssociation oAss : poDS.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASDISTANCE.toString())) {
					//Get content of the association
					String oContent = (String) ((clsWordPresentation)oAss.getLeafElement()).getMoContent();
					if (Y==null) {
						Y = eRadius.elementAt(oContent);
					}
					//Special case if EATABLE is used
					//FIXME AW: EATABLE is center
					if (((clsWordPresentation)oAss.getLeafElement()).getMoContent().equals("EATABLE")==true) {
						if (X==null) {
							X = ePhiPosition.CENTER;
						}
					}
				
				} else if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASPOSITION.toString())) {
					String oContent = (String) ((clsWordPresentation)oAss.getLeafElement()).getMoContent();
					//Get the X-Part
					if (X==null) {
						X = ePhiPosition.elementAt(oContent);
					}
				}
			}
		
		}
		
		oRetVal.b = X;
		oRetVal.c = Y;
				
		return oRetVal;
	}
}
