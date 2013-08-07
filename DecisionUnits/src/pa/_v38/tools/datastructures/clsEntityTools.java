/**
 * CHANGELOG
 *
 * 07.07.2012 wendt - File created
 *
 */
package pa._v38.tools.datastructures;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.tools.clsTriple;

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
		
		clsWordPresentation oDistance = clsMeshTools.getUniquePredicateWP(poDS, ePredicate.HASDISTANCE);
		clsWordPresentation oPosition = clsMeshTools.getUniquePredicateWP(poDS, ePredicate.HASPOSITION);
		
		if (oDistance!=null && oDistance.getMoContent().equals("EATABLE")==true) {
			X = ePhiPosition.CENTER;
			Y = eRadius.NEAR;
		} else {
			if (oDistance!=null) {
				Y = eRadius.elementAt(oDistance.getMoContent());
			}
			if (oPosition!=null) {
				X = ePhiPosition.elementAt(oPosition.getMoContent());
			}
		}

//		for (clsAssociation oAss : poDS.getExternalAssociatedContent()) {
//			if (oAss instanceof clsAssociationSecondary) {
//				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASDISTANCE)) {
//					//Get content of the association
//					String oContent = (String) ((clsWordPresentation)oAss.getLeafElement()).getMoContent();
//					if (Y==null) {
//						Y = eRadius.elementAt(oContent);
//					}
//					//Special case if EATABLE is used
//					//FIXME AW: EATABLE is center
//					if (((clsWordPresentation)oAss.getLeafElement()).getMoContent().equals("EATABLE")==true) {
//						if (X==null) {
//							X = ePhiPosition.CENTER;
//						}
//					}
//				
//				} else if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.HASPOSITION)) {
//					String oContent = (String) ((clsWordPresentation)oAss.getLeafElement()).getMoContent();
//					//Get the X-Part
//					if (X==null) {
//						X = ePhiPosition.elementAt(oContent);
//					}
//				}
//			}
//		
//		}
		
		oRetVal.b = X;
		oRetVal.c = Y;
				
		return oRetVal;
	}
	
	/**
	 * Remove the position of an entity
	 * 
	 * (wendt)
	 *
	 * @since 09.10.2012 18:14:03
	 *
	 * @param poEntity
	 */
	public static void removePosition(clsWordPresentationMesh poEntity) {
		clsMeshTools.removeAllNonUniquePredicateSecondaryDataStructure(poEntity, ePredicate.HASDISTANCE);	
		clsMeshTools.removeAllNonUniquePredicateSecondaryDataStructure(poEntity, ePredicate.HASPOSITION);
		
		clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(poEntity);
		removePosition(oTPM);
		
	}
	
	/**
	 * Remove the position of an entity
	 * 
	 * (wendt)
	 *
	 * @since 10.10.2012 12:07:41
	 *
	 * @param poEntity
	 */
	public static void removePosition(clsThingPresentationMesh poEntity) {
		clsMeshTools.removeUniqueTP(poEntity, eContentType.DISTANCE);
		clsMeshTools.removeUniqueTP(poEntity, eContentType.POSITION);
	}
}
