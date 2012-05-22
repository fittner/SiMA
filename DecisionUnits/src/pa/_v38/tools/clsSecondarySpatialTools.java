/**
 * CHANGELOG
 *
 * 19.02.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDistanceRelation;
import pa._v38.memorymgmt.enums.ePositionRelation;
import pa._v38.memorymgmt.enums.eXPosition;
import pa._v38.memorymgmt.enums.eYPosition;

/**
 * This function creates relational relationships between all object pairs (m with n) in the image. Only
 * image depth 1 (pnLevel) is considered here
 * 
 * (wendt)
 * 
 * @author wendt
 * 19.02.2012, 15:14:59
 * 
 */
public class clsSecondarySpatialTools {
	
	/**
	 * Create relations between all objects within an image
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 17:23:41
	 *
	 * @param poImage
	 */
	public static void createRelationalObjectMesh(clsWordPresentationMesh poImage) {
		
		//1. Go through all the internal structures of the image, only level 1 is considered here
		ArrayList<clsWordPresentationMesh> oSubObjects = clsDataStructureTools.getAllSubWPMInWPMImage(poImage);
		
		//2. Go through all pair of objects within the image
		if (oSubObjects.size()>1) {
			for (int i=0; i<oSubObjects.size()-1; i++ ) {
				for (int j=i+1; j<oSubObjects.size(); j++) {
					//Create all relations between the objects
					clsPair<eDistanceRelation, ePositionRelation> oRelationPair = createSpatialRelation(oSubObjects.get(i), oSubObjects.get(j));
					
					//Add new associations to both images
					clsDataStructureTools.createAssociationSecondary(oSubObjects.get(i), 2, oSubObjects.get(j), 2, 1.0, eContentType.DISTANCERELATION.toString(), oRelationPair.a.toString(), false);
					clsDataStructureTools.createAssociationSecondary(oSubObjects.get(i), 2, oSubObjects.get(j), 2, 1.0, eContentType.POSITIONRELATION.toString(), oRelationPair.b.toString(), false);					
				}
			}
		}
	}
	
	/**
	 * Create spatial relations between 2 objects. For each object pair 2 relations are added: 
	 * 1) a distance relation and 2) a position relation.
	 * 
	 * If one position is null, i. e. is generalized, then it receives a general relation, which means that all positions
	 * can be taken
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 15:22:18
	 *
	 * @param poObject1
	 * @param poObject2
	 */
	private static clsPair<eDistanceRelation, ePositionRelation> createSpatialRelation(clsWordPresentationMesh poObject1, clsWordPresentationMesh poObject2) {
		clsPair<eDistanceRelation, ePositionRelation> oRetVal = null;
		
		//1. Get position for object1
		clsTriple<clsWordPresentationMesh, eXPosition, eYPosition> oObjectPosition1 = getPosition(poObject1);
		//2. Get position for object2
		clsTriple<clsWordPresentationMesh, eXPosition, eYPosition> oObjectPosition2 = getPosition(poObject2);
		
		//If some values are null, then the positions are generalized and this shall be considered
		if (oObjectPosition1.b!=null && oObjectPosition1.c!=null && oObjectPosition2.b!=null && oObjectPosition2.c!=null) {
			//3. Get the vector between the objects
			clsPair<Double, Double> oRelationVector = clsPrimarySpatialTools.getRelationVector((double)oObjectPosition1.b.mnPos, (double)oObjectPosition2.b.mnPos, (double)oObjectPosition1.c.mnPos, (double)oObjectPosition2.c.mnPos);
			
			//4. Get the distance between the objects
			double rDistance = clsPrimarySpatialTools.getDistance(oRelationVector);
			
			//5. Convert distance to a relation
			eDistanceRelation oDistanceRelation = eDistanceRelation.getDistanceRelation(rDistance);
			
			//6. Convert vector to position relation
			ePositionRelation oPositionRelation = ePositionRelation.getPositionRelation(oRelationVector);
			
			//7. return
			oRetVal = new clsPair<eDistanceRelation, ePositionRelation>(oDistanceRelation, oPositionRelation);
		} else if ((oObjectPosition1.b==null || oObjectPosition2.b==null) && (oObjectPosition1.c!=null && oObjectPosition2.c!=null)) {
			//3. Get the vector between the objects
			double rDistanceScalar = clsPrimarySpatialTools.get1DRelationVector((double)oObjectPosition1.c.mnPos, (double)oObjectPosition2.c.mnPos);
			//5. Convert distance to a relation
			eDistanceRelation oDistanceRelation = eDistanceRelation.getDistanceRelation(rDistanceScalar);
			
			ePositionRelation oPositionRelation = ePositionRelation.GENERAL;
			
			oRetVal = new clsPair<eDistanceRelation, ePositionRelation>(oDistanceRelation, oPositionRelation);
		} else if ((oObjectPosition1.b!=null && oObjectPosition2.b!=null) && (oObjectPosition1.c==null || oObjectPosition2.c==null)) {
			//3. Get the vector between the objects
			double rPositionScalar = clsPrimarySpatialTools.get1DRelationVector((double)oObjectPosition1.b.mnPos, (double)oObjectPosition2.b.mnPos);
			//5. Convert distance to a relation
			ePositionRelation oPositionRelation = ePositionRelation.getPositionRelation(new clsPair<Double, Double>(rPositionScalar, (double)0.0));
			
			eDistanceRelation oDistanceRelation = eDistanceRelation.GENERAL;
			
			oRetVal = new clsPair<eDistanceRelation, ePositionRelation>(oDistanceRelation, oPositionRelation);
		} else {
			oRetVal = new clsPair<eDistanceRelation, ePositionRelation>(eDistanceRelation.GENERAL, ePositionRelation.GENERAL);
		}
		
//		if (oObjectPosition1.b==null || oObjectPosition1.c==null || oObjectPosition2.b==null || oObjectPosition2.c==null) {
//			try {
//				throw new Exception("Some values in the Positions are null. ElementA: " + oObjectPosition1.toString() + ", ElementB: " + oObjectPosition2.toString());
//			} catch (Exception e) {
//				// TODO (wendt) - Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		
		return oRetVal;
	}
	
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
	private static clsTriple<clsWordPresentationMesh, eXPosition, eYPosition> getPosition(clsWordPresentationMesh poDS) {
		clsTriple<clsWordPresentationMesh, eXPosition, eYPosition> oRetVal = null;
		
		//Search for xy compontents
		eXPosition X = null;	//default error value
		eYPosition Y = null;
		//ArrayList<clsAssociation> oDSAssList = poImageContainer.getMoAssociatedDataStructures(poDS);
		for (clsAssociation oAss : poDS.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				if (oAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE.toString())) {
					//Get content of the association
					String oContent = (String) ((clsWordPresentation)oAss.getLeafElement()).getMoContent();
					if (Y==null) {
						Y = eYPosition.elementAt(oContent);
					}
					//Special case if EATABLE is used
					//FIXME AW: EATABLE is center
					if (((clsWordPresentation)oAss.getLeafElement()).getMoContent().equals("EATABLE")==true) {
						if (X==null) {
							X = eXPosition.CENTER;
						}
					}
					
				} else if (oAss.getLeafElement().getMoContentType().equals(eContentType.POSITION.toString())) {
					String oContent = (String) ((clsWordPresentation)oAss.getLeafElement()).getMoContent();
					//Get the X-Part
					if (X==null) {
						X = eXPosition.elementAt(oContent);
					}
				}
			}
		
		}
		
		oRetVal = new clsTriple<clsWordPresentationMesh, eXPosition, eYPosition>(poDS, X, Y);
				
		return oRetVal;
	}
	
//	/**
//	 * Get the distance between 2 objects. 
//	 * All positions have to be defined for each object
//	 * 
//	 * (wendt)
//	 *
//	 * @since 08.11.2011 11:17:23
//	 *
//	 * @param poPIElement
//	 * @param poRIElement
//	 * @return
//	 */
//	public static double getDistance(clsTriple<clsWordPresentationMesh, eXPosition, eYPosition> poElementA, clsTriple<clsWordPresentationMesh, eXPosition, eYPosition> poElementB) {
//		
//		if (poElementA.b==null || poElementA.c==null || poElementB.b==null || poElementB.c==null) {
//			try {
//				throw new Exception("Some values in the Positions are null. ElementA: " + poElementA.toString() + ", ElementB: " + poElementB.toString());
//			} catch (Exception e) {
//				// TODO (wendt) - Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		//The outcome of specialize positions is always != null
//		return clsPrimarySpatialTools.getDistance((double)poElementB.b.mnPos, (double)poElementB.c.mnPos, (double)poElementA.b.mnPos, (double)poElementA.c.mnPos);
//	}
	
	
	
}
