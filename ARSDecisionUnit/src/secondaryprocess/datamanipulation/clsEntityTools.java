/**
 * CHANGELOG
 *
 * 07.07.2012 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.ePredicate;
import memorymgmt.enums.eRadius;

import org.slf4j.Logger;

import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsTriple;

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
    
    private static final Logger log = clsLogger.getLog("Entitytools");
    
	public static clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> getPosition(clsWordPresentationMesh poDS) {
		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oRetVal = new clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>(poDS, null, null);
		
		//Search for xy compontents
		ePhiPosition X = null;	//default error value
		eRadius Y = null;
		
		clsWordPresentation oDistance = clsMeshTools.getUniquePredicateWP(poDS, ePredicate.HASDISTANCE);
		clsWordPresentation oPosition = clsMeshTools.getUniquePredicateWP(poDS, ePredicate.HASPOSITION);
		
		if (oDistance!=null && oDistance.getContent().equals("EATABLE")==true) {
			X = ePhiPosition.CENTER;
			Y = eRadius.NEAR;
		} else {
			if (oDistance!=null) {
				Y = eRadius.elementAt(oDistance.getContent());
			}
			if (oPosition!=null) {
				X = ePhiPosition.elementAt(oPosition.getContent());
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
	 * Add position and distance for word presentation meshes
	 *
	 * @author wendt
	 * @since 03.12.2013 12:54:08
	 *
	 * @param poEntity
	 * @param poRadius
	 * @param poPosition
	 * @throws Exception
	 */
	public static void addPosition(clsWordPresentationMesh poEntity, eRadius poRadius, ePhiPosition poPosition) throws Exception {
	        removeDistanceAndPosition(poEntity);
	        
	        try {
	            clsMeshTools.setNonUniquePredicateWP(poEntity, ePredicate.HASDISTANCE, eContentType.DISTANCE, poRadius.toString(), false);
	            clsMeshTools.setNonUniquePredicateWP(poEntity, ePredicate.HASPOSITION, eContentType.POSITION, poPosition.toString(), false);
	        } catch (Exception e) {
	           log.error("Error at setting position or distance", e);
	           throw e;
	        }
	        
	        clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(poEntity);
	        addPosition(oTPM, poRadius, poPosition);
	        
	    }
	
	/**
	 * Add position and distance for thing presentation meshes
	 *
	 * @author wendt
	 * @since 03.12.2013 12:54:27
	 *
	 * @param poEntity
	 * @param poRadius
	 * @param poPosition
	 * @throws Exception
	 */
	public static void addPosition(clsThingPresentationMesh poEntity, eRadius poRadius, ePhiPosition poPosition) throws Exception {
	    removeDistanceAndPosition(poEntity);
	    
	    try {
            clsMeshTools.setUniqueTP(poEntity, eContentType.DISTANCE, poRadius.toString());
            clsMeshTools.setUniqueTP(poEntity, eContentType.POSITION, poPosition.toString());
        } catch (Exception e) {
           log.error("Error at setting position or distance", e);
           throw e;
        }
	    
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
	public static void removeDistanceAndPosition(clsWordPresentationMesh poEntity) {
		clsMeshTools.removeAllNonUniquePredicateSecondaryDataStructure(poEntity, ePredicate.HASDISTANCE);	
		clsMeshTools.removeAllNonUniquePredicateSecondaryDataStructure(poEntity, ePredicate.HASPOSITION);
		
		clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(poEntity);
		removeDistanceAndPosition(oTPM);
		
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
	public static void removeDistanceAndPosition(clsThingPresentationMesh poEntity) {
		clsMeshTools.removeUniqueTP(poEntity, eContentType.DISTANCE);
		clsMeshTools.removeUniqueTP(poEntity, eContentType.POSITION);
	}
}
