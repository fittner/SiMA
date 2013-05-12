/**
 * CHANGELOG
 *
 * 19.02.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDistanceRelation;
import pa._v38.memorymgmt.enums.ePositionRelation;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;

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
		ArrayList<clsWordPresentationMesh> oSubObjects = clsMeshTools.getAllSubWPMInWPMImage(poImage);
		
		//2. Go through all pair of objects within the image
		if (oSubObjects.size()>1) {
			for (int i=0; i<oSubObjects.size()-1; i++ ) {
				for (int j=i+1; j<oSubObjects.size(); j++) {
					//Create all relations between the objects
					clsPair<eDistanceRelation, ePositionRelation> oRelationPair = createSpatialRelation(oSubObjects.get(i), oSubObjects.get(j));
					
					//Add new associations to both images
					clsMeshTools.createAssociationSecondary(oSubObjects.get(i), 2, oSubObjects.get(j), 2, 1.0, eContentType.DISTANCERELATION, ePredicate.valueOf(oRelationPair.a.toString()), false);
					clsMeshTools.createAssociationSecondary(oSubObjects.get(i), 2, oSubObjects.get(j), 2, 1.0, eContentType.POSITIONRELATION, ePredicate.valueOf(oRelationPair.b.toString()), false);					
				}
			}
		}
	}
	
	/**
	 * Get the position for all entities in an image
	 * 
	 * (wendt)
	 *
	 * @since 07.07.2012 10:44:29
	 *
	 * @param poImage
	 * @return
	 */
	public static ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> getEntityPositionsInImage(clsWordPresentationMesh poImage) {
		ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oResult = new ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>>();
		
		for (clsAssociation poHasPartAssociation : poImage.getMoInternalAssociatedContent()) {
			
			clsWordPresentationMesh oEntity = (clsWordPresentationMesh) poHasPartAssociation.getLeafElement();
			
			clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPosition = clsEntityTools.getPosition(oEntity);
			
			oResult.add(oPosition);
		}
		
		return oResult;
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
		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oObjectPosition1 = clsEntityTools.getPosition(poObject1);
		//2. Get position for object2
		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oObjectPosition2 = clsEntityTools.getPosition(poObject2);
		
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
	
	/**
	 * Extract all entities from a certain sector in an image and set an importance value for that entity
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 07.07.2012 10:53:17
	 *
	 * @param poPerceivedImage
	 * @param poDistance
	 * @param poPosition
	 * @param pnImportance
	 * @return
	 */
	public static ArrayList<clsPair<Integer, clsWordPresentationMesh>> extractEntitiesInArea(clsWordPresentationMesh poPerceivedImage, eRadius poDistance, ePhiPosition poPosition, int pnImportance) {
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oResult = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		
		//Get all positions of all entities in the image
		ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oEntityPositions = clsSecondarySpatialTools.getEntityPositionsInImage(poPerceivedImage);
		
		for (clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oEntity : oEntityPositions) {
			try {
				if (oEntity.b==null || oEntity.c==null) {
					throw new NullPointerException("The positions of this entity are not completely defined. Distance or position is missing.");
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			
			if (oEntity.b.equals(poPosition) && oEntity.c.equals(poDistance)) {
				oResult.add(new clsPair<Integer, clsWordPresentationMesh>(pnImportance, oEntity.a));	//Adds the entity from the perceived image together with importance
			}
		}
		
		return oResult;
	}
	
	/**
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 20:15:08
	 *
	 * @param poSourceImage
	 * @param poEntitiesToFindImage
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getAlreadyExistingEntitiesInImage(clsWordPresentationMesh poSourceImage, clsWordPresentationMesh poEntitiesToFindImage, boolean poReturnSourceEntities) {
		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
		
		ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oSourceImageEntityList = getEntityPositionsInImage(poSourceImage);
		
		ArrayList<clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius>> oEntitiesToFindImageList = getEntityPositionsInImage(poEntitiesToFindImage);
		
		for (clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oFindEntity : oEntitiesToFindImageList) {
			for (clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oSourceEntity : oSourceImageEntityList) {
				if (compareEntitiesInImagesIdent(oFindEntity, oSourceEntity)==true) {
					if (poReturnSourceEntities==true) {
						oResult.add(oSourceEntity.a);
					} else {
						oResult.add(oFindEntity.a);
					}
					
					break;
				}
			}
		}
		
		return oResult;
	}
	
	/**
	 * Compare 2 entities, which are originating from images with each other. The ID and position is compared. If they are equal, then 
	 * it is the same entity
	 * 
	 * (wendt)
	 * 
	 * @since 17.07.2012 20:12:35
	 *
	 * @param poEntityA
	 * @param poEntityB
	 * @return
	 */
	private static boolean compareEntitiesInImagesIdent(clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> poEntityA, clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> poEntityB) {
		boolean bResult = false;
		
		if (poEntityA.a.getMoDS_ID()==poEntityB.a.getMoDS_ID() &&
				poEntityA.b == poEntityB.b &&
				poEntityA.c == poEntityB.c) {
			bResult = true;
		}
		
		
		return bResult;
	}
	

	
	/**
	 * Get the distance between 2 WPM entities
	 * 
	 * (wendt)
	 *
	 * @since 09.09.2012 20:26:55
	 *
	 * @param poElementA
	 * @param poElementB
	 * @return
	 */
	public static double getDistance(clsWordPresentationMesh poElementA, clsWordPresentationMesh poElementB) {
		double rResult = -1;
		
		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oElementPositionA = clsEntityTools.getPosition(poElementA);
		clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oElementPositionB = clsEntityTools.getPosition(poElementB);
		
		rResult = clsPrimarySpatialTools.getDistance(oElementPositionA, oElementPositionB);
		
		return rResult;
	}
	
	
}
