/**
 * CHANGELOG
 *
 * 20.07.2011 deutsch - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalStructureComposition;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 20.07.2011, 13:58:07
 * 
 */
public class clsDataStructureTools {
	
	private static int mnMaxLevel = 10;
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 20.07.2011 13:58:43
	 *
	 * @param poSearchStructure
	 * @param poSearchInImage
	 * @return
	 */
	private static String moPredicateClassification = "ISCLASSIFEDAS";
	
	public static clsDataStructurePA getDataStructureFromImage(clsDataStructurePA poSearchStructure, clsPrimaryDataStructureContainer poSearchInImage) {
		clsDataStructurePA oRetVal = null;

		if (poSearchStructure.getMoDS_ID() == poSearchInImage.getMoDataStructure().getMoDS_ID()) {
			oRetVal = poSearchInImage.getMoDataStructure();
		} else {
			clsTemplateImage oDS = (clsTemplateImage)poSearchInImage.getMoDataStructure();
			for (clsAssociation oSubDS : oDS.getMoAssociatedContent()) {
				if (oSubDS.getLeafElement().getMoDS_ID() == poSearchStructure.getMoDS_ID()) {
					oRetVal = oSubDS.getLeafElement();
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Creates a new AssociationPri between 2 containers and adds this association to the associated data structures.
	 * In this way, the match weight between a perceived image and a memory is stored
	 * 
	 * ONLY TPM ARE ALLOWED TO HAVE AN ASSOCIATION PRIMARY IN ITS EXTERNAL ASSOCIATIONS
	 * 
	 * (wendt)
	 *
	 * @since 22.07.2011 10:01:54
	 *
	 * @param poContainerA
	 * @param poContainerB
	 * @param prWeight
	 */
	public static void createAssociationPrimary(clsThingPresentationMesh poStructureA, clsThingPresentationMesh poStructureB, double prWeight) {
		String oContentType = eContentType.PIASSOCIATION.toString();
		clsAssociationPrimary oAssPri = (clsAssociationPrimary)clsDataStructureGenerator.generateASSOCIATIONPRI(oContentType, poStructureA, poStructureB, prWeight);
		poStructureA.getExternalMoAssociatedContent().add(oAssPri);
		poStructureB.getExternalMoAssociatedContent().add(oAssPri);
	}
	
	/**
	 * Creates a new AssociationTemp between 2 structures and adds this association to the associated data structures.
	 * 
	 * ONLY TPM ARE ALLOWED TO HAVE AN ASSOCIATION TEMP IN ITS EXTERNAL ASSOCIATIONS
	 * 
	 * (wendt)
	 *
	 * @since 22.07.2011 10:01:54
	 *
	 * @param poContainerA
	 * @param poContainerB
	 * @param prWeight
	 */
	public static void createAssociationTemporary(clsThingPresentationMesh poSuperStructure, clsThingPresentationMesh poSubStructure, double prWeight) {
		String oContentType = eContentType.PARTOFASSOCIATION.toString();
		//Create association
		clsAssociationTime oAssTemp = (clsAssociationTime)clsDataStructureGenerator.generateASSOCIATIONTIME(oContentType, poSuperStructure, poSubStructure, prWeight);
		//Add association to the superstructure
		poSuperStructure.getMoAssociatedContent().add(oAssTemp);
		//Add association to the substructure
		poSubStructure.getExternalMoAssociatedContent().add(oAssTemp);
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
	 * For each container, where the associations are not bound, the hash-code from the data structure was taken as id and
	 * all associations in the associated data structures root elements were set with the instance ID of the container
	 * data structures.
	 * 
	 * This function shall be executed as soon as more TPMs are used in one container and every time something is loaded 
	 * from the memory
	 * 
	 * @since 06.07.2011 15:03:52
	 *
	 * @param <E>
	 * @param poInput
	 * @return
	 **/
	public static <E extends clsDataStructureContainer> ArrayList<E> createInstanceFromTypeList(ArrayList<E> poInput, boolean pbResetInstanceID) {
		ArrayList<E> oRetVal = poInput; //TD 2011/07/20 - removed deepCopy. this has to be decided by the one who calls this method and done there.
		
		//Set Unique IDs for all root elements
		for (E oElement : oRetVal) {
			createInstanceFromType(oElement, pbResetInstanceID);
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 22.07.2011 10:59:59
	 *
	 * @param <E>
	 * @param poElement
	 */
	public static <E extends clsDataStructureContainer> void createInstanceFromType(E poElement, boolean pbResetInstanceID) {
		int oInstanceID;	//
		//Check if the root (DS) element already have an unique ID
		if ((poElement.getMoDataStructure().getMoDSInstance_ID() == 0) || (pbResetInstanceID==true)) {
			oInstanceID = poElement.getMoDataStructure().hashCode();
			poElement.getMoDataStructure().setMoDSInstance_ID(oInstanceID);
		} else {
			oInstanceID = poElement.getMoDataStructure().getMoDSInstance_ID();
		}
		
		//Go through all associations in the container and complete the ones, which are missing or different from the root or leaf elements
		for (clsAssociation oAssStructure : poElement.getMoAssociatedDataStructures()) {
			//Change ID only if the association root element is the same type (ID) as the data structure
			if ((oAssStructure.getRootElement().getMoDSInstance_ID()!=oInstanceID) && (poElement.getMoDataStructure().getMoDS_ID()==oAssStructure.getRootElement().getMoDS_ID())) {
				oAssStructure.getRootElement().setMoDSInstance_ID(oInstanceID);
			} else if ((oAssStructure.getLeafElement().getMoDSInstance_ID()!=oInstanceID) && (poElement.getMoDataStructure().getMoDS_ID()==oAssStructure.getLeafElement().getMoDS_ID())) {
				oAssStructure.getLeafElement().setMoDSInstance_ID(oInstanceID);
			}
		}
	}
	
	/**
	 * For a secondary data structure in a container, get the fitting primary data structure in a container if it is available in the input list
	 * (wendt)
	 *
	 * @since 01.08.2011 16:14:31
	 *
	 * @param poInput
	 * @param poSourceList
	 * @return
	 */
	public static clsDataStructureContainerPair searchContainerPairList(clsSecondaryDataStructureContainer poInput, ArrayList<clsDataStructureContainerPair> poSourceList) {
		clsDataStructureContainerPair oRetVal = null;
		
		//Go through the container and search for associationWP
		if (poInput!=null) {
			for (clsAssociation oAss : poInput.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationWordPresentation) {
					//Check if the primary data structure is a part of the root or the leaf element
					if (oAss.getLeafElement() instanceof clsPrimaryDataStructure) {
						oRetVal = getContainerFromList(poSourceList, oAss.getLeafElement());
						break;
					} else if (oAss.getRootElement() instanceof clsPrimaryDataStructure) {
						oRetVal = getContainerFromList(poSourceList, oAss.getRootElement());
						break;
					}
				}
			}
	}
		return oRetVal;
	}
	
	/**
	 * Extract a container pair a data structure
	 * (wendt)
	 *
	 * @since 22.07.2011 18:26:09
	 *
	 * @param poSourceList
	 * @param poDS
	 * @return
	 */
	public static clsDataStructureContainerPair getContainerFromList(ArrayList<clsDataStructureContainerPair> poSourceList, clsDataStructurePA poDS) {
		clsDataStructureContainerPair oRetVal = null;
		
		for (clsDataStructureContainerPair oContainerPair : poSourceList) {
			if (oContainerPair.getSecondaryComponent().getMoDataStructure().getMoDS_ID() == poDS.getMoDS_ID()) {
				oRetVal = oContainerPair;
				break;
			} else if (oContainerPair.getPrimaryComponent()!=null && oContainerPair.getPrimaryComponent().getMoDataStructure().getMoDS_ID() == poDS.getMoDS_ID()) {
				oRetVal = oContainerPair;
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get a list of the remote data structure from all associationsecondary of a certain predicate for a secondary container
	 * (wendt)
	 *
	 * @since 19.08.2011 22:06:00
	 *
	 * @param poSourceContainer
	 * @param poPredicate
	 * @param pbInverseDirection
	 * @return
	 */
	public static ArrayList<clsSecondaryDataStructure> getDSFromSecondaryAssInContainer(clsSecondaryDataStructureContainer poSourceContainer, String poPredicate, boolean pbInverseDirection) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		//pbInverseDirection: If FALSE, then the direction is to get the leafelement, if TRUE, then the rootelement is search for
		
		//Get the datastructure on the associationsecondary, which is not this structure
		for (clsAssociation oSecAss : poSourceContainer.getMoAssociatedDataStructures()) {
			if (oSecAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oSecAss).getMoPredicate() == poPredicate) {
					//Get the purposed container of the expectation
					//Normal case
					if (pbInverseDirection==false) {
						if ((oSecAss.getLeafElement().getMoDS_ID() != poSourceContainer.getMoDataStructure().getMoDS_ID()) && 
								(oSecAss.getRootElement().getMoDS_ID() == poSourceContainer.getMoDataStructure().getMoDS_ID())) {
							oRetVal.add((clsSecondaryDataStructure) oSecAss.getLeafElement());
						}
					} else {
					//Inverse case
						if ((oSecAss.getRootElement().getMoDS_ID() != poSourceContainer.getMoDataStructure().getMoDS_ID()) && 
								(oSecAss.getLeafElement().getMoDS_ID() == poSourceContainer.getMoDataStructure().getMoDS_ID())) {
							oRetVal.add((clsSecondaryDataStructure) oSecAss.getRootElement());
						}
					}
				}
			}
		}
		
		return oRetVal;
	}
	
//	/**
//	 * Get the primary association with the PI
//	 *(wendt)
//	 *
//	 * @since 19.08.2011 22:05:34
//	 *
//	 * @param poContainer
//	 * @return
//	 */
//	public static clsAssociationPrimary getAssociationPrimaryWithPI(clsPrimaryDataStructureContainer poContainer) {
//		//Precondition: There is only one match with the PI in one container
//		clsAssociationPrimary oRetVal = null;
//		
//		for (clsAssociation oAss : poContainer.getMoAssociatedDataStructures()) {
//			if ((oAss instanceof clsAssociationPrimary) && (oAss.getMoContentType().equals("PIASSOCIATION"))) {
//				oRetVal = (clsAssociationPrimary) oAss;
//				break;
//			}
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * Get association of a primary data structure container with PI
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 04.08.2011 13:59:56
	 *
	 * @param poImageContainer
	 * @return
	 */
	public static double getMatchValueToPI(clsPrimaryDataStructureContainer poImageContainer) {
		String oContent = "PERCEPTION";
		double rRetVal = 0.0;
		
		for (clsAssociation oAss : poImageContainer.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationPrimary) {
				if ((oAss.getMoAssociationElementA() instanceof clsTemplateImage) || (oAss.getMoAssociationElementB() instanceof clsTemplateImage)) {
					if ((((clsTemplateImage)oAss.getMoAssociationElementA()).getMoContent() == oContent) || (((clsTemplateImage)oAss.getMoAssociationElementB()).getMoContent() == oContent)) {
						rRetVal = oAss.getMrWeight();
					}
				}
					
			}
		}
		return rRetVal;
	}
	
	/**
	 * Add a classification to the secondary data structure, if it does not already exist
	 * (wendt)
	 *
	 * @since 31.08.2011 11:53:55
	 *
	 * @param poContainer
	 * @param poClassification
	 */
	public static void setAttributeWordPresentation(clsSecondaryDataStructureContainer poContainer, String poPredicate, String poContentType, String poContent) {
		//Check if such an association already exists
		ArrayList<clsSecondaryDataStructure> oWPList = null;	//FIXME AW: Hack due to mesh structure//getAttributeOfSecondaryPresentation(poContainer, poPredicate);
		//if (oWP)
		
		if (oWPList.isEmpty()==true) {
			//Add new association
			//Cases: "" != MOMENT and EXPECTATION != MOMENT
			clsWordPresentation oClass = clsDataStructureGenerator.generateWP(new clsPair<String, Object>(poContentType, poContent));
			clsAssociationSecondary oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("ASSOCIATIONSECONDARY", poContainer.getMoDataStructure(), oClass, poPredicate, 1.0);
			poContainer.getMoAssociatedDataStructures().add(oNewAss);
		} else if (oWPList.get(0).getMoContent().equals(poContent) == false) {
			//Replace content of the current classification
			oWPList.get(0).setMoContent(poContent);
		}
				
	}
	
	
	/**
	 * Check, which classification a secondary data structure has
	 * (wendt)
	 *
	 * @since 31.08.2011 11:54:49
	 *
	 * @param poContainer
	 * @return
	 */
	public static ArrayList<clsWordPresentation> getAttributeOfSecondaryPresentation(clsWordPresentationMesh poContainer, String poPredicate) {
		ArrayList<clsWordPresentation> oRetVal = new ArrayList<clsWordPresentation>();
		
		//A container can only have ONE classification
		//TODO AW: Check if more than one classification may be necessary
		
		for (clsAssociation oAss : poContainer.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate)) {
					oRetVal.add((clsWordPresentation)oAss.getLeafElement());
				}
			}
		}
		
		return oRetVal;
	}
	
	
	/**
	 * TEMP-FUNCTION due to the meshes. Remove this as soon as the SP is completed
	 *
	 * (wendt)
	 *
	 * @since 08.02.2012 09:20:19
	 *
	 * @param poContainer
	 * @param poPredicate
	 * @return
	 */
	public static ArrayList<clsSecondaryDataStructure> getAttributeOfSecondaryPresentation(clsSecondaryDataStructureContainer poContainer, String poPredicate) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		return oRetVal;
	}
	
	/**
	 * Get the position of an object as a pair of Strings, where the position-TP are in some container
	 * (wendt)
	 *
	 * @since 02.09.2011 14:27:26
	 *
	 * @param <E>
	 * @param poObject
	 * @param poPositionContainer
	 * @return
	 */
	public static clsTriple<clsThingPresentationMesh, String, String> getObjectPosition(clsThingPresentationMesh poObject) {
		clsTriple<clsThingPresentationMesh, String, String> oRetVal = null;
		//Get object position only if both x and y are given
		
		ArrayList<String> oDistance = new ArrayList<String>();
		oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR","MANIPULATEABLE","EATABLE"));
		ArrayList<String> oPosition = new ArrayList<String>();
		oPosition.addAll(Arrays.asList("RIGHT","MIDDLE_RIGHT","CENTER","MIDDLE_LEFT","LEFT"));
		
		ArrayList<clsAssociation> oAllAss = poObject.getExternalMoAssociatedContent();
		
		boolean bDistanceFound = false;
		boolean bPositionFound = false;
		
		clsTriple<clsThingPresentationMesh, String, String> oPositionPair = new clsTriple<clsThingPresentationMesh, String, String>(poObject, "", "");
		
		for (clsAssociation oSingleAss : oAllAss) {
			if (oSingleAss instanceof clsAssociationAttribute) {
				if (oSingleAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE.toString())) {
					String oContent = (String) ((clsThingPresentation)oSingleAss.getLeafElement()).getMoContent();
					if (bDistanceFound==false) {
						for (String oDist : oDistance) {
							if (oDist.equals(oContent)) {
								oPositionPair.c = oDist;
								bDistanceFound = true;
								break;
							}
						}
					}
				}
							
				if (oSingleAss.getLeafElement().getMoContentType().equals(eContentType.POSITION.toString())) {
					String oContent = (String) ((clsThingPresentation)oSingleAss.getLeafElement()).getMoContent();
					if (bPositionFound==false) {
						for (String oPos : oPosition) {
							if (oPos.equals(oContent)) {
								oPositionPair.b = oPos;
								bPositionFound = true;
								break;
							}
						}
					}
				}
			}
		}
		
		if ((bPositionFound==true) && (bDistanceFound==true)) {
			oRetVal = oPositionPair;
		}
		
		return oRetVal;
	}
	
//	/**
//	 * Get all positions of all objects in an image
//	 * 
//	 * (wendt)
//	 *
//	 * @since 02.09.2011 14:30:53
//	 *
//	 * @param poImage
//	 * @return
//	 */
//	public static ArrayList<clsTriple<clsThingPresentationMesh, String, String>> getImageContentPositions(clsThingPresentationMesh poImage) {
//		ArrayList<clsTriple<clsThingPresentationMesh, String, String>> oRetVal = new ArrayList<clsTriple<clsThingPresentationMesh, String, String>>();
//		
//		for (clsAssociation oAss : poImage.getMoAssociatedContent()) {
//			clsTriple<clsThingPresentationMesh, String, String> oPosPair = getObjectPosition((clsThingPresentationMesh)oAss.getLeafElement());
//			if (oPosPair!=null) {
//				oRetVal.add(oPosPair);
//			}
//		}
//		
//		return oRetVal;
//	}
	
	/**
	 * Add new data structures or objects to a template image
	 * 
	 * (wendt)
	 *
	 * @since 02.09.2011 22:43:58
	 *
	 * @param oAddList
	 * @param oImage
	 */
	public static void addContainersToImage(ArrayList<clsPrimaryDataStructureContainer> oAddList, clsPrimaryDataStructureContainer oImage) {
		//Modify the image by adding additional compontents
		
		for (clsPrimaryDataStructureContainer oC : oAddList) {
			clsAssociationTime oAssTime = new clsAssociationTime(new clsTriple<Integer, eDataType, String> 
			(-1, eDataType.ASSOCIATIONTEMP, eDataType.ASSOCIATIONTEMP.toString()), 
			(clsThingPresentationMesh)oImage.getMoDataStructure(), 
			(clsThingPresentationMesh)oC.getMoDataStructure());
			
			//Add Timeassociation
			((clsTemplateImage)oImage.getMoDataStructure()).assignDataStructure(oAssTime);
			
			//Add locations
			oImage.getMoAssociatedDataStructures().addAll(oC.getMoAssociatedDataStructures());
		}
		
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.09.2011 10:38:39
	 *
	 * @param poCompareCPair
	 * @param poTargetList
	 * @return
	 */
	public static clsDataStructureContainerPair containerPairExists(clsDataStructureContainerPair poCompareCPair, ArrayList<clsDataStructureContainerPair> poTargetList) {
		clsDataStructureContainerPair oRetVal = null;
		
		for (clsDataStructureContainerPair oCPair : poTargetList) {
			if ((poCompareCPair!=null) && (poCompareCPair.getSecondaryComponent()!=null) && (oCPair.getSecondaryComponent()!=null)) {
				if (poCompareCPair.getSecondaryComponent().getMoDataStructure().getMoDS_ID() == oCPair.getSecondaryComponent().getMoDataStructure().getMoDS_ID()) {
					oRetVal = oCPair;
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Revove all associations with a certain predicate
	 * (wendt)
	 *
	 * @since 13.09.2011 09:59:33
	 *
	 * @param oSContainer
	 * @param oPredicate
	 */
	public static void removeAssociation(clsSecondaryDataStructureContainer oSContainer, ePredicate oPredicate) {
		ListIterator<clsAssociation> liMainList = oSContainer.getMoAssociatedDataStructures().listIterator();
		
		while (liMainList.hasNext()) {
			clsAssociation oAss = liMainList.next();
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(oPredicate.toString())==true) {
					liMainList.remove();
				}
			}
			
		}
	}
	
	/**
	 * Find a certain instance of a TPM in a mesh. The instance ID is compared here
	 * 
	 * (wendt)
	 *
	 * @since 22.09.2011 14:31:10
	 *
	 * @param poTargetImage
	 * @param poFindDataStructure
	 * @return
	 */
	public static clsThingPresentationMesh findDataStructureInstanceInMeshTPM(clsThingPresentationMesh poTargetImageMesh, clsThingPresentationMesh poFindDataStructure, int pnLevel) {
		clsThingPresentationMesh oRetVal = null;
		//Compare IDs of the structures
		//TODO: Extend to instance_IDs
		
		ArrayList<clsThingPresentationMesh> oAllTPMInMesh = getAllTPMImages(poTargetImageMesh, pnLevel);
		
		for (clsThingPresentationMesh oObject : oAllTPMInMesh) {
			if (poFindDataStructure.getMoDSInstance_ID() == oObject.getMoDSInstance_ID()) {
				oRetVal = oObject;
				break;
			}
		}

		return oRetVal;
	}
	
	/**
	 * Find a certain instance of a WPM in a mesh. The instance ID is compared here.
	 * 
	 * ONLY A SINGLE LEVEL IS MADE, I. E. ONLY THE IMMEDIATE ASSOCIATIONS
	 * 
	 * (wendt)
	 *
	 * @since 22.09.2011 14:31:10
	 *
	 * @param poTargetImage
	 * @param poFindDataStructure
	 * @return
	 */
	public static void correctFalseInstancesInAssWPM(clsWordPresentationMesh poMesh) {
		clsWordPresentationMesh oRetVal = null;
		//Compare IDs of the structures

		//Go through the internal structures
		for (clsAssociation oIntAss : poMesh.getAssociatedContent()) {
			//Check if this part is meant
			if (oIntAss.getRootElement() instanceof clsWordPresentationMesh &&
					oIntAss.getRootElement().equals(poMesh)==false && 
					oIntAss.getRootElement().getMoDS_ID()==poMesh.getMoDS_ID() && 
					oIntAss.getRootElement().getMoContentType().equals(poMesh.getMoContentType())) {
				//If all this is true, this association is falsly assigned
				oIntAss.setRootElement(poMesh);
				
			} else if (oIntAss.getLeafElement() instanceof clsWordPresentationMesh &&
					oIntAss.getLeafElement().equals(poMesh)==false && 
					oIntAss.getLeafElement().getMoDS_ID()==poMesh.getMoDS_ID() && 
					oIntAss.getLeafElement().getMoContentType().equals(poMesh.getMoContentType())) {
				
				oIntAss.setLeafElement(poMesh);
				
			} else if (oIntAss.getLeafElement().equals(poMesh)==false && oIntAss.getRootElement().equals(poMesh)==false) {
				try {
					throw new Exception("Error in clsDataStructureTools, correctFalseInstancesInAssWPM: Erroneously assogined association");
				} catch (Exception e) {
					// TODO (wendt) - Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
		//Go through the external structures
		for (clsAssociation oExtAss : poMesh.getExternalAssociatedContent()) {
			//Check if this part is meant
			if (oExtAss.getRootElement() instanceof clsWordPresentationMesh &&
					oExtAss.getRootElement().equals(poMesh)==false && 
					oExtAss.getRootElement().getMoDS_ID()==poMesh.getMoDS_ID() && 
					oExtAss.getRootElement().getMoContentType().equals(poMesh.getMoContentType())) {
				//If all this is true, this association is falsly assigned
				oExtAss.setRootElement(poMesh);
				
			} else if (oExtAss.getLeafElement() instanceof clsWordPresentationMesh &&
					oExtAss.getLeafElement().equals(poMesh)==false && 
					oExtAss.getLeafElement().getMoDS_ID()==poMesh.getMoDS_ID() && 
					oExtAss.getLeafElement().getMoContentType().equals(poMesh.getMoContentType())) {
				
				oExtAss.setLeafElement(poMesh);
				
			} else if (oExtAss.getLeafElement().equals(poMesh)==false && oExtAss.getRootElement().equals(poMesh)==false) {
				try {
					throw new Exception("Error in clsDataStructureTools, correctFalseInstancesInAssWPM: Erroneously assogined association");
				} catch (Exception e) {
					// TODO (wendt) - Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Check the mesh for existing structures, where the input is a TPM. The type ID is compared
	 * 
	 * (wendt)
	 *
	 * @since 22.09.2011 14:31:10
	 *
	 * @param poTargetImage
	 * @param poFindDataStructure
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> findDataStructureTypesInMesh(clsThingPresentationMesh poTargetImageMesh, clsThingPresentationMesh poFindDataStructure, int pnLevel) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		//Compare IDs of the structures
		//TODO: Extend to instance_IDs
		
		ArrayList<clsThingPresentationMesh> oAllTPMInMesh = getAllTPMObjects(poTargetImageMesh, pnLevel);
		
		//Go through all objects on this level in each image
		for (clsThingPresentationMesh oObject : oAllTPMInMesh) {
			
			if (poFindDataStructure.getMoDS_ID() == oObject.getMoDS_ID()) {
				oRetVal.add(oObject);
			}
		}

		return oRetVal;
	}
	
	/**
	 * Check if a selected secondary data structure exists in an image
	 * (wendt)
	 *
	 * @since 22.09.2011 14:31:10
	 *
	 * @param poTargetImage
	 * @param poFindDataStructure
	 * @return
	 */
	public static clsWordPresentationMesh findDataStructureTypesInMesh(clsSecondaryDataStructureContainer poTargetImage, clsSecondaryDataStructure poFindDataStructure) {
		clsWordPresentationMesh oRetVal = null;
		//Compare IDs of the structures
		//TODO: Extend to instance_IDs
		if (poTargetImage.getMoDataStructure() instanceof clsWordPresentationMesh) {
			for (clsAssociation oImageDS : ((clsWordPresentationMesh)poTargetImage.getMoDataStructure()).getAssociatedContent()) {
				if (oImageDS.getRootElement().getMoDS_ID() == poFindDataStructure.getMoDS_ID()) {
					oRetVal = (clsWordPresentationMesh) oImageDS.getRootElement();
					break;
				}
			}
		} else {
			try {
				throw new Exception("Error in clsDataStructureTools, findDataStructureInImage: Only WPM are allowed");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return oRetVal;
	}
	
	public static ArrayList<clsDataStructureContainerPair> getObjectContainerPairsFromImage(clsDataStructureContainerPair poImage) {
		ArrayList<clsDataStructureContainerPair> oRetVal = new ArrayList<clsDataStructureContainerPair>();
		//2. Check if a primary part is available, else, return nothing
		//1. Check if it really is an image
		if (((poImage.getPrimaryComponent()!=null) && (poImage.getPrimaryComponent().getMoDataStructure() instanceof clsTemplateImage) && (poImage.getSecondaryComponent().getMoDataStructure() instanceof clsWordPresentationMesh))) {
			//3. go through all elements in the secondary container WPM and get all associations
			for (clsAssociation oSecondaryIntrinsicAssociation : ((clsWordPresentationMesh)poImage.getSecondaryComponent().getMoDataStructure()).getAssociatedContent()) {
				//4. Get all associations for that WPM
				clsSecondaryDataStructure poSingleWPObject = (clsSecondaryDataStructure) oSecondaryIntrinsicAssociation.getRootElement();
				//5. Get all associated data structure from the WP container
				ArrayList<clsAssociation> poWPAssList = poImage.getSecondaryComponent().getAnyAssociatedDataStructures(poSingleWPObject);
				//6. Search for the WP-Association to the thing presentation part
				clsPrimaryDataStructure poPriObject = null;
				for (clsAssociation oSecondaryAss : poWPAssList) {
					//7. If the association is a ass-WP, then is must be connected with a PP object
					if (oSecondaryAss instanceof clsAssociationWordPresentation) {
						poPriObject = (clsPrimaryDataStructure) oSecondaryAss.getRootElement();
						//There is only one assocition per object 
						break;
					}
				}
				
				//8. Find the whole container for that TPM
				if (poPriObject!=null) {
					ArrayList<clsAssociation> oPriAssList = poImage.getPrimaryComponent().getAnyAssociatedDataStructures(poPriObject);
					//9. Create the new pair with the new objects
					clsDataStructureContainerPair oCPair = new clsDataStructureContainerPair(new clsSecondaryDataStructureContainer(poSingleWPObject, poWPAssList), new clsPrimaryDataStructureContainer(poPriObject, oPriAssList));
					//Add the new data structure
					oRetVal.add(oCPair);
				}	
			}
			
		}
		
		return oRetVal;
		
	}
	
	
	//=================== PERCEIVED IMAGE FUNCTIONS =====================================//
	
	/**
	 * Create a new TPM as Top-Object from a list of TPMs, i. e. an image
	 * 
	 * (wendt)
	 *
	 * @since 29.11.2011 15:04:29
	 *
	 * @param poInput
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	public static clsThingPresentationMesh createTPMImage(ArrayList<clsThingPresentationMesh> poInput, String poContentType, String poContent) {
		clsThingPresentationMesh oRetVal = null;
		
		clsTriple<Integer, eDataType, String> oTPMIdentifier = new clsTriple<Integer, eDataType, String>(-1, eDataType.TPM, poContentType);
		clsThingPresentationMesh oConstructedImage = new clsThingPresentationMesh(oTPMIdentifier, new ArrayList<clsAssociation>(), poContent);
		
		addTPMToTPMImage(oConstructedImage, poInput);
		
		oRetVal = oConstructedImage;
		
		return oRetVal;
	}
	
	/**
	 * Add a list of TPM to another TPM as parts of it to its intrinsic structures
	 * 
	 * (wendt)
	 *
	 * @since 05.12.2011 15:33:14
	 *
	 * @param poInput: List of structures, which shall be added
	 * @param poMesh: The image, which shall receive the structures
	 */
	public static void addTPMToTPMImage(clsThingPresentationMesh oImage, ArrayList<clsThingPresentationMesh> oAddList) {
		//Modify the image by adding additional compontents
		
		for (clsThingPresentationMesh oC : oAddList) {
			createAssociationTemporary(oImage, oC, 1.0);
			//clsAssociationTime oAssTime = new clsAssociationTime(new clsTriple<Integer, eDataType, String> 
			//(-1, eDataType.ASSOCIATIONTEMP, eDataType.ASSOCIATIONTEMP.toString()), 
			//oImage, oC);
			
			//Add Timeassociation
			//oImage.assignDataStructure(oAssTime);
			
			//Add locations
			//oImage.getExternalMoAssociatedContent().addAll(oC.getExternalMoAssociatedContent());
		}
		
	}
	
	/**
	 * Find a certain data structure type in a directed graph. Only the first level is searched for in this function. In the case of a PI, its direct sub elements are searched.
	 * 
	 * The compare criterium is the Type ID and not the instance ID
	 *
	 * !!!!! THIS FUNCTION IS ONLY SPECIALIZED FOR GETTING OBJECTS FROM PERCEIVED IMAGES !!!!!!
	 * 
	 * @since 21.07.2011 09:27:59
	 *
	 * @param clsDataStructurePA
	 * @return
	 */
	public static clsDataStructurePA containsInstanceType(clsDataStructurePA poSearchStructure, clsThingPresentationMesh poMesh) {
		clsDataStructurePA oRetVal = null;
		
		if (poSearchStructure.getMoDS_ID() == poMesh.getMoDS_ID()) {
			oRetVal = poMesh;
		} else {
			for (clsAssociation oAss : poMesh.getMoAssociatedContent()) {
				if (oAss.getLeafElement().getMoDS_ID() == poSearchStructure.getMoDS_ID()) {
					oRetVal = oAss.getLeafElement();
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all TPM structures in a mesh up to a certain level. A list with each TPM structure is returned
	 * (wendt)
	 *
	 * @since 05.01.2012 10:42:02
	 *
	 * @param poMesh: The ThingPresentationMesh, normally the perception
	 * @param poDataType: Filter: Returndatatype: TPM, TP or DM
	 * @param poContentType: Filter ContentType. Set null for no filter
	 * @param poContent: Filter Content. Set null for no filter
	 * @param pbStopAtFirstMatch: Break at first match
	 * @param pnLevel: Level of search depth. If = 1, only the current image is processed. 
	 * If pnLevel = 2, all direct matches to the top image are processed
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> getDataStructureInTPM(clsThingPresentationMesh poMesh, eDataType poDataType, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch, int pnLevel) {
		ArrayList<clsThingPresentationMesh> oAddedElements = new ArrayList<clsThingPresentationMesh>();
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through each TPM (Node) and search for the defined structures. This is a recursive function
		searchDataStructureInTPM(poMesh, oAddedElements, oRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel);
		
		return oRetVal;
	}
	
	/**
	 * Get all WPM structures in a mesh up to a certain level. A list with each WPM structure is returned
	 * (wendt)
	 *
	 * @since 05.01.2012 10:42:02
	 *
	 * @param poMesh: The ThingPresentationMesh, normally the perception
	 * @param poDataType: Filter: Returndatatype: TPM, TP or DM
	 * @param poContentType: Filter ContentType. Set null for no filter
	 * @param poContent: Filter Content. Set null for no filter
	 * @param pbStopAtFirstMatch: Break at first match
	 * @param pnLevel: Level of search depth. If = 1, only the current image is processed. 
	 * If pnLevel = 2, all direct matches to the top image are processed
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> getDataStructureInWPM(clsWordPresentationMesh poMesh, eDataType poDataType, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch, int pnLevel) {
		ArrayList<clsWordPresentationMesh> oAddedElements = new ArrayList<clsWordPresentationMesh>();
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through each TPM (Node) and search for the defined structures. This is a recursive function
		searchDataStructureInWPM(poMesh, oAddedElements, oRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel);
		
		return oRetVal;
	}
	
//	public static ArrayList<clsDataStructurePA> getStructureInMesh(clsThingPresentationMesh poMesh, String poContentType, String poContent, boolean pbStopAtFirstMatch, int pnLevel) {
//		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
//		
//		getAllTPMInMesh(poMesh, oRetVal, poContentType, poContent, pbStopAtFirstMatch, pnLevel);
//		
//		return oRetVal;
//	}
	
	
	/**
	 * Recursively go through all elements in the mesh to get all TPMs, but here, this function is only used if there already exists a list
	 * 
	 * (wendt)
	 *
	 * @since 06.12.2011 11:37:00
	 *
	 * @param poMesh
	 * @return
	 */
	private static void searchDataStructureInTPM(clsThingPresentationMesh poMesh, ArrayList<clsThingPresentationMesh> poAddedElements, ArrayList<clsDataStructurePA> poRetVal, eDataType poDataType, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch, int pnLevel) {
		//ArrayList<clsThingPresentationMesh> oRetVal = poAddedElements;
		
		//Add the structure itself to the list of passed elements
		poAddedElements.add(poMesh);
		//Check this data structure for filter options and add the result to the result list if filter fits
		if (poDataType.equals(eDataType.TPM)==true) {
			//Check if this mesh matches the content and content type filter. If yes, then add the result
			for (clsPair<String, String> oCTC : poContentTypeAndContent) {
				//Check if this mesh has this filter
				boolean bMatchFound = FilterTPM(poMesh, oCTC.a, oCTC.b);
				
				//As soon as positive, break loop
				if (bMatchFound==true) {
					poRetVal.add(poMesh);
					break;
				}
			}

		} else if (poDataType.equals(eDataType.TP)==true) {
			ArrayList<clsAssociationAttribute> oFoundTPAssList = getTPAssociations(poMesh, poContentTypeAndContent, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundTPAssList);
		} else if (poDataType.equals(eDataType.DM)==true) {
			
			ArrayList<clsAssociationDriveMesh> oFoundDMAssList = getDMAssociations(poMesh, poContentTypeAndContent, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundDMAssList);
		} else {
			try {
				throw new Exception("clsDataStructureTools: searchDataStructureInMesh: Only TPM, TP or DM allowed as data types");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (pbStopAtFirstMatch==false || poRetVal.isEmpty()==true) {	//=NOT Stopatfirstmatch=true AND oRetVal is not empty
			
			//Add the substructures of the internal associations
			if ((pnLevel>0) || (pnLevel==-1)) {
				for (clsAssociation oAss : poMesh.getMoAssociatedContent()) {
					if (poAddedElements.contains(oAss.getLeafElement())==false && oAss.getLeafElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oAss.getRootElement())==false && oAss.getRootElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
			
			//Add external associations to other external images
			if ((pnLevel>1) || (pnLevel==-1)) {
				for (clsAssociation oExtAss : poMesh.getExternalMoAssociatedContent()) {
					if (poAddedElements.contains(oExtAss.getLeafElement())==false && oExtAss.getLeafElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oExtAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oExtAss.getRootElement())==false && oExtAss.getRootElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oExtAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
		}
		
	}
	
	/**
	 * Filter a list of TPMs for certain content and content type. NULL counts as nothing and is not used as filter criterium
	 * 
	 * (wendt)
	 *
	 * @since 02.01.2012 21:57:09
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static boolean FilterTPM(clsThingPresentationMesh poMesh, String poContentType, String poContent) {
		//ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		boolean oRetVal = false;
		
			if (poContentType.equals("")==false && poContent.equals("")==false) {
				if ((poContentType.equals(poMesh.getMoContentType())==true) &&
						(poContent.equals(poMesh.getMoContent())==true)) {
					oRetVal = true;
				}
			} else if (poContentType.equals("")==false && poContent.equals("")==true) {
				if (poContentType.equals(poMesh.getMoContentType())==true) {
					oRetVal = true;
				}
			} else if (poContentType.equals("")==true && poContent.equals("")==false) {
				if (poContent.equals(poMesh.getMoContent())==true) {
					oRetVal = true;
				}
			} else {
				oRetVal = true;
			}
		
		return oRetVal;
	}
	
	/**
	 * Filter WPM for certain content and content type
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 20:26:13
	 *
	 * @param poMesh
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static boolean FilterWPM(clsWordPresentationMesh poMesh, String poContentType, String poContent) {

		boolean oRetVal = false;
		
			if (poContentType.equals("")==false && poContent.equals("")==false) {
				if ((poContentType.equals(poMesh.getMoContentType())==true) &&
						(poContent.equals(poMesh.getMoContent())==true)) {
					oRetVal = true;
				}
			} else if (poContentType.equals("")==false && poContent.equals("")==true) {
				if (poContentType.equals(poMesh.getMoContentType())==true) {
					oRetVal = true;
				}
			} else if (poContentType.equals("")==true && poContent.equals("")==false) {
				if (poContent.equals(poMesh.getMoContent())==true) {
					oRetVal = true;
				}
			} else {
				oRetVal = true;
			}
		
		return oRetVal;
	}
	
	/**
	 * Get all properties TP in from a certain mesh perception and associated memories.
	 * 
	 * If poContent = null, then it is not a criteria (String 1)
	 * If poContentType = null, then it is not a criteria (String 2)
	 * 
	 * pnMode
	 * 0: External and internal associations of the TPM
	 * 1: Only external associations of the TPM
	 * 2: Only internal associations of the TPM
	 * 
	 * (wendt)
	 *
	 * @since 06.09.2011 16:36:10
	 *
	 * @param poEnvironmentalPerception
	 * @param poAssociatedMemories
	 * @return
	 */
	private static ArrayList<clsAssociationAttribute> getTPAssociations(clsThingPresentationMesh poTPM, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationAttribute> oRetVal = new ArrayList<clsAssociationAttribute>();

		//Go through all external
		for (clsPair<String, String> oCTC : poContentTypeAndContent) {
			oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
			if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
				//Go through the external list
				oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
				break;
			}
		}
		
		return oRetVal;
	}
	
	private static ArrayList<clsAssociationSecondary> getWPAssociations(clsWordPresentationMesh poWPM, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationSecondary> oRetVal = new ArrayList<clsAssociationSecondary>();

		//Go through all external
		for (clsPair<String, String> oCTC : poContentTypeAndContent) {
			oRetVal.addAll(FilterWPList(poWPM.getAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
			if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
				//Go through the external list
				oRetVal.addAll(FilterWPList(poWPM.getExternalAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all associatied DM in a TPM by using some filter criteria
	 * 
	 * (wendt)
	 *
	 * @since 20.01.2012 11:36:30
	 *
	 * @param poTPM
	 * @param poContentTypeAndContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsAssociationDriveMesh> getDMAssociations(clsThingPresentationMesh poTPM, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();

		//Go through all external
		for (clsPair<String, String> oCTC : poContentTypeAndContent) {
			oRetVal.addAll(FilterDMList(poTPM.getMoAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
			if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
				//Go through the external list
				oRetVal.addAll(FilterDMList(poTPM.getExternalMoAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Filter a List of TP for certain content and contenttype
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:29:31
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static ArrayList<clsAssociationAttribute> FilterTPList(ArrayList<clsAssociation> poAssList, String poContentType, String poContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationAttribute> oRetVal = new ArrayList<clsAssociationAttribute>();
		
		for (clsAssociation oAss: poAssList) {
			//Check if attribute
			if (oAss instanceof clsAssociationAttribute) {
				if (poContentType.equals("")==false && poContent.equals("")==false) {
					if ((poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) &&
							(poContent.equals(((clsThingPresentation)oAss.getLeafElement()).getMoContent().toString())==true)) {
						oRetVal.add((clsAssociationAttribute) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType.equals("")==false && poContent.equals("")==true) {
					if (poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) {
						oRetVal.add((clsAssociationAttribute) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType.equals("")==true && poContent.equals("")==false) {
					if (poContent.equals(((clsThingPresentation)oAss.getLeafElement()).getMoContent().toString())==true) {
						oRetVal.add((clsAssociationAttribute) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else {
					oRetVal.add((clsAssociationAttribute) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Filter a List of WP for certain content and contenttype
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:29:31
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static ArrayList<clsAssociationSecondary> FilterWPList(ArrayList<clsAssociation> poAssList, String poContentType, String poContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationSecondary> oRetVal = new ArrayList<clsAssociationSecondary>();
		
		for (clsAssociation oAss: poAssList) {
			//Check if attribute
			if (oAss instanceof clsAssociationSecondary && oAss.getLeafElement() instanceof clsWordPresentation) {
				if (poContentType.equals("")==false && poContent.equals("")==false) {
					if ((poContentType.equals(((clsAssociationSecondary)oAss).getLeafElement().getMoContentType())==true) &&
							(poContent.equals(((clsWordPresentation)oAss.getLeafElement()).getMoContent().toString())==true)) {
						oRetVal.add((clsAssociationSecondary) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType.equals("")==false && poContent.equals("")==true) {
					if (poContentType.equals(((clsAssociationSecondary)oAss).getLeafElement().getMoContentType())==true) {
						oRetVal.add((clsAssociationSecondary) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType.equals("")==true && poContent.equals("")==false) {
					if (poContent.equals(((clsWordPresentation)oAss.getLeafElement()).getMoContent().toString())==true) {
						oRetVal.add((clsAssociationSecondary) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else {
					oRetVal.add((clsAssociationSecondary) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Filter a List of DM for certain content and contenttype
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:29:31
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static ArrayList<clsAssociationDriveMesh> FilterDMList(ArrayList<clsAssociation> poAssList, String poContentType, String poContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		
		
		
		for (clsAssociation oAss: poAssList) {
			boolean bBreakAssLoop = false;
			//Go through all pairs of contents and content types
			//for (clsPair<String, String> oCTC : poContentTypeAndContent) {
				//Check if dm has the corrent content and content type
				if (oAss instanceof clsAssociationDriveMesh) {
					if (poContentType.equals("")==false && poContent.equals("")==false) {
						if ((poContentType.equals(((clsAssociationDriveMesh)oAss).getLeafElement().getMoContentType())==true) &&
								(poContent.equals(((clsDriveMesh)oAss.getLeafElement()).getMoContent().toString())==true)) {
							oRetVal.add((clsAssociationDriveMesh) oAss);
							if (pbStopAtFirstMatch==true) {
								bBreakAssLoop=true;
							}
							break;
						}
					} else if (poContentType.equals("")==false && poContent.equals("")==true) {
						if (poContentType.equals(((clsAssociationDriveMesh)oAss).getLeafElement().getMoContentType())==true) {
							oRetVal.add((clsAssociationDriveMesh) oAss);
							if (pbStopAtFirstMatch==true) {
								bBreakAssLoop=true;
							}
						}
					} else if (poContentType.equals("")==true && poContent.equals("")==false) {
						if (poContent.equals(((clsDriveMesh)oAss.getLeafElement()).getMoContent().toString())==true) {
							oRetVal.add((clsAssociationDriveMesh) oAss);
							if (pbStopAtFirstMatch==true) {
								bBreakAssLoop=true;
							}
						}
					} else {
						oRetVal.add((clsAssociationDriveMesh) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				//}
			}
			
			if (bBreakAssLoop==true) {
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Recursively go through all elements in the mesh to get all TPMs, but here, this function is only used if there already exists a list
	 * 
	 * (wendt)
	 *
	 * @since 06.12.2011 11:37:00
	 *
	 * @param poMesh
	 * @return
	 */
	private static void searchDataStructureInWPM(clsWordPresentationMesh poMesh, ArrayList<clsWordPresentationMesh> poAddedElements, ArrayList<clsDataStructurePA> poRetVal, eDataType poDataType, ArrayList<clsPair<String, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch, int pnLevel) {
		//ArrayList<clsThingPresentationMesh> oRetVal = poAddedElements;
		
		//Add this element, in order not to search it through 2 times
		poAddedElements.add(poMesh);
		//Add the structure itself to the list of passed elements
		//Check this data structure for filter options and add the result to the result list if filter fits
		if (poDataType.equals(eDataType.WPM)==true) {
			//Check if this mesh matches the content and content type filter. If yes, then add the result
			for (clsPair<String, String> oCTC : poContentTypeAndContent) {
				//Check if this mesh has this filter
				boolean bMatchFound = FilterWPM(poMesh, oCTC.a, oCTC.b);
				
				//As soon as positive, break loop
				if (bMatchFound==true) {
					poRetVal.add(poMesh);
					break;
				}
			}
		} else if (poDataType.equals(eDataType.WP)==true) {
			ArrayList<clsAssociationSecondary> oFoundWPAssList = getWPAssociations(poMesh, poContentTypeAndContent, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundWPAssList);
		} else {
			try {
				throw new Exception("clsDataStructureTools: searchDataStructureInMesh: Only WPM allowed as data types");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (pbStopAtFirstMatch==false || poRetVal.isEmpty()==true) {	//=NOT Stopatfirstmatch=true AND oRetVal is not empty
			
			//Add the substructures of the internal associations
			if ((pnLevel>0) || (pnLevel==-1)) {
				for (clsAssociation oAss : poMesh.getAssociatedContent()) {
					if (poAddedElements.contains(oAss.getLeafElement())==false && oAss.getLeafElement() instanceof clsWordPresentationMesh && oAss.getLeafElement().equals(poMesh)==false) {
						searchDataStructureInWPM((clsWordPresentationMesh) oAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oAss.getRootElement())==false && oAss.getRootElement() instanceof clsWordPresentationMesh && oAss.getRootElement().equals(poMesh)==false) {
						searchDataStructureInWPM((clsWordPresentationMesh) oAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
			
			//Add external associations to other external images
			if ((pnLevel>1) || (pnLevel==-1)) {
				for (clsAssociation oExtAss : poMesh.getExternalAssociatedContent()) {
					//Association WP contains TPM, therefore, here is not searched
					if ((oExtAss instanceof clsAssociationWordPresentation)==false) {
						if (poAddedElements.contains(oExtAss.getLeafElement())==false && oExtAss.getLeafElement() instanceof clsWordPresentationMesh && oExtAss.getLeafElement().equals(poMesh)==false) {
							searchDataStructureInWPM((clsWordPresentationMesh) oExtAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
						} else if (poAddedElements.contains(oExtAss.getRootElement())==false && oExtAss.getRootElement() instanceof clsWordPresentationMesh && oExtAss.getRootElement().equals(poMesh)==false) {
							searchDataStructureInWPM((clsWordPresentationMesh) oExtAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
						}
					}
				}
			}
		}
		
	}
	
//	public static <E extends clsDataStructurePA> ArrayList<E> castArrayList(eDataType poDataType, ArrayList<clsDataStructurePA> poList) {
//		if (poDataType==eDataType.TPM) {
//			for (clsDataStructurePA oE : poList) {
//				
//			}
//		}
//		
//	}
	
//	/**
//	 * Get all properties TP in from a certain mesh perception and associated memories.
//	 * 
//	 * If poContent = null, then it is not a criteria
//	 * If poContentType = null, then it is not a criteria
//	 * 
//	 * pnMode
//	 * 0: External and internal associations of the TPM
//	 * 1: Only external associations of the TPM
//	 * 2: Only internal associations of the TPM
//	 * 
//	 * (wendt)
//	 *
//	 * @since 06.09.2011 16:36:10
//	 *
//	 * @param poEnvironmentalPerception
//	 * @param poAssociatedMemories
//	 * @return
//	 */
//	private static ArrayList<clsAssociationDriveMesh> getDMAssociations(clsThingPresentationMesh poTPM, String poContentType, String poContent, boolean pbStopAtFirstMatch, int pnLevel) {
//		ArrayList<clsAssociationAttribute> oRetVal = new ArrayList<clsAssociationAttribute>();
//
//		//Go through all external
//		oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), poContentType, poContent, pbStopAtFirstMatch));
//		if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
//			//Go through the external list
//			oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), poContentType, poContent, pbStopAtFirstMatch));
//		}
//		
//		return oRetVal;
//	}
	
	
//	/**
//	 * Get all drive meshes in perception and associated memories
//	 * (wendt)
//	 *
//	 * @since 06.09.2011 16:36:10
//	 *
//	 * @param poEnvironmentalPerception
//	 * @param poAssociatedMemories
//	 * @return
//	 */
//	public static ArrayList<clsAssociationDriveMesh> getDriveMeshAssociations(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
//		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
//		
//		//Get all TPM
//		ArrayList<clsThingPresentationMesh> oAllTPM = getTPMInMesh(poPerceptionalMesh, pnLevel);
//		
//		//Go through all TPMs to find all DMs
//		for (clsThingPresentationMesh oTPM : oAllTPM) {
//			for (clsAssociation oExtAss : oTPM.getExternalMoAssociatedContent()) {
//				if (oExtAss instanceof clsAssociationDriveMesh) {
//					oRetVal.add((clsAssociationDriveMesh) oExtAss);
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
	
//	/**
//	 * Filter a list of TPMs for certain content and content type. NULL counts as nothing and is not used as filter criterium
//	 * 
//	 * (wendt)
//	 *
//	 * @since 02.01.2012 21:57:09
//	 *
//	 * @param poAssList
//	 * @param poContentType
//	 * @param poContent
//	 * @param pbStopAtFirstMatch
//	 * @return
//	 */
//	public static ArrayList<clsThingPresentationMesh> FilterTPMList(ArrayList<clsThingPresentationMesh> poAssList, String poContentType, String poContent, boolean pbStopAtFirstMatch) {
//		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
//		
//		for (clsThingPresentationMesh oTPM: poAssList) {
//			if (poContentType!=null && poContent!=null) {
//				if ((poContentType.equals(oTPM.getMoContentType())==true) &&
//						(poContent.equals(oTPM.getMoContent())==true)) {
//					oRetVal.add(oTPM);
//					if (pbStopAtFirstMatch==true) {
//						break;
//					}
//				}
//			} else if (poContentType!=null && poContent==null) {
//				if (poContentType.equals(oTPM.getMoContentType())==true) {
//					oRetVal.add(oTPM);
//					if (pbStopAtFirstMatch==true) {
//						break;
//					}
//				}
//			} else if (poContentType==null && poContent!=null) {
//				if (poContent.equals(oTPM.getMoContent())==true) {
//					oRetVal.add(oTPM);
//					if (pbStopAtFirstMatch==true) {
//						break;
//					}
//				}
//			} else {
//				oRetVal.add(oTPM);
//				if (pbStopAtFirstMatch==true) {
//					break;
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
	
	
//	private static void getAllTPMInMesh(clsThingPresentationMesh poMesh, ArrayList<clsThingPresentationMesh> poAddedElements, String poContentType, String poContent, boolean pbStopAtFirstMatch, int pnLevel) {
//		//ArrayList<clsThingPresentationMesh> oRetVal = poAddedElements;
//		
//		//Add the structure itself
//		poAddedElements.add(poMesh);
//		
//		//Add the substructures of the internal associations
//		if ((pnLevel>0) || (pnLevel==-1)) {
//			for (clsAssociation oAss : poMesh.getMoAssociatedContent()) {
//				if (poAddedElements.contains(oAss.getLeafElement())==false && oAss.getLeafElement() instanceof clsThingPresentationMesh) {
//					getAllTPMInMesh((clsThingPresentationMesh) oAss.getLeafElement(), poAddedElements, pnLevel-1);
//				} else if (poAddedElements.contains(oAss.getRootElement())==false && oAss.getRootElement() instanceof clsThingPresentationMesh) {
//					getAllTPMInMesh((clsThingPresentationMesh) oAss.getRootElement(), poAddedElements, pnLevel-1);
//				}
//			}
//		}
//		
//		//Add external associations
//		if ((pnLevel>1) || (pnLevel==-1)) {
//			for (clsAssociation oExtAss : poMesh.getExternalMoAssociatedContent()) {
//				if (poAddedElements.contains(oExtAss.getLeafElement())==false && oExtAss.getLeafElement() instanceof clsThingPresentationMesh) {
//					getAllTPMInMesh((clsThingPresentationMesh) oExtAss.getLeafElement(), poAddedElements, pnLevel-1);
//				} else if (poAddedElements.contains(oExtAss.getRootElement())==false && oExtAss.getRootElement() instanceof clsThingPresentationMesh) {
//					getAllTPMInMesh((clsThingPresentationMesh) oExtAss.getRootElement(), poAddedElements, pnLevel-1);
//				}
//			}
//		}
//		
//	}
	
	/**
	 * Get all images in a mesh, i. e. contentType = RI or PI
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:30:25
	 *
	 * @param poPerceptionalMesh
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> getAllTPMImages(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Get all TPM in the mesh
		//ArrayList<clsThingPresentationMesh> oAllTPM = getTPMInMesh(poPerceptionalMesh, pnLevel);
		
		//Add PI. There is only one
		ArrayList<clsPair<String, String>> oContentTypeAndContentPairPI = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContentPairPI.add(new clsPair<String, String>(eContentType.PI.toString(), ""));
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypeAndContentPairPI, true, pnLevel));
		
		//Add all RI. 
		ArrayList<clsPair<String, String>> oContentTypeAndContentPairRI = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContentPairRI.add(new clsPair<String, String>(eContentType.RI.toString(), ""));
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypeAndContentPairRI, false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all images in a WPM mesh, i. e. contentType = RI or PI
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:30:25
	 *
	 * @param poPerceptionalMesh
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getAllWPMImages(clsWordPresentationMesh poMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		//Add all RI. 
		ArrayList<clsPair<String, String>> oContentTypeAndContentPairRI = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContentPairRI.add(new clsPair<String, String>(eContentType.RI.toString(), ""));
		oFoundImages.addAll(getDataStructureInWPM(poMesh, eDataType.WPM, oContentTypeAndContentPairRI, false, pnLevel));
		
		for (clsDataStructurePA oWPM : oFoundImages) {
			oRetVal.add((clsWordPresentationMesh) oWPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all memories (images, contentType = Remembered image RI) in a mesh, i. e. contentType = RI
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:30:25
	 *
	 * @param poPerceptionalMesh
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> getAllTPMMemories(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Add all RI. 
		ArrayList<clsPair<String, String>> oContentTypeAndContentPairRI = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContentPairRI.add(new clsPair<String, String>(eContentType.RI.toString(), ""));
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypeAndContentPairRI, false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all TPM with a certain contenttype and content
	 * 
	 * (wendt)
	 *
	 * @since 02.01.2012 21:56:02
	 *
	 * @param poPerceptionalMesh
	 * @param poContentType
	 * @param poContent
	 * @param pbStopAtFirstMatch
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> getAllTPMObjects(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Get all TPM for that level
		ArrayList<clsPair<String, String>> oContentTypeAndContentPair = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContentPair.add(new clsPair<String, String>("", ""));
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypeAndContentPair, false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get a list of all drivemeshes in a certain thing presentation mesh, pnLevel=1 (Only this image)
	 * (wendt)
	 *
	 * @since 15.01.2012 17:52:22
	 *
	 * @param poPerceptionalMesh
	 * @return
	 */
	public static ArrayList<clsAssociationDriveMesh> getAllDMInMesh(clsThingPresentationMesh poPerceptionalMesh) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		//This is an unconverted clsAssociationDriveMesh
		ArrayList<clsPair<String, String>> oContentTypeAndContentPair = new ArrayList<clsPair<String, String>>();
		oContentTypeAndContentPair.add(new clsPair<String, String>("", ""));
		ArrayList<clsDataStructurePA> oFoundList = clsDataStructureTools.getDataStructureInTPM(poPerceptionalMesh, eDataType.DM, oContentTypeAndContentPair, false, 1);
		
		for (clsDataStructurePA oAss : oFoundList) {
			oRetVal.add((clsAssociationDriveMesh) oAss);
		}
		
		return oRetVal;
	}
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 30.01.2012 21:02:43
	 *
	 * @param poPerceptionalMesh
	 * @param poFilterContentTypeAndContent
	 * @return
	 */
	public static ArrayList<clsAssociationDriveMesh> getSelectedDMInImage(clsThingPresentationMesh poPerceptionalMesh, ArrayList<clsPair<String, String>> poFilterContentTypeAndContent) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		
		ArrayList<clsDataStructurePA> oFoundList = clsDataStructureTools.getDataStructureInTPM(poPerceptionalMesh, eDataType.DM, poFilterContentTypeAndContent, false, 2);
						
		for (clsDataStructurePA oAss : oFoundList) {
			oRetVal.add((clsAssociationDriveMesh) oAss);
		}
		
		return oRetVal;
	}
	 	/**
	 * Filter a list of TPMs for certain content and content type. NULL counts as nothing and is not used as filter criterium
	 * 
	 * (wendt)
	 *
	 * @since 02.01.2012 21:57:09
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> FilterTPMList(ArrayList<clsThingPresentationMesh> poMeshList, String poContentType, String poContent, boolean bStopAtFirstMatch) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		for (clsThingPresentationMesh oTPM : poMeshList)
			if (poContentType!=null && poContent!=null) {
				if ((poContentType.equals(oTPM.getMoContentType())==true) &&
						(poContent.equals(oTPM.getMoContent())==true)) {
					oRetVal.add(oTPM);
					if (bStopAtFirstMatch==true) {
						break;
					}
				}
			} else if (poContentType!=null && poContent==null) {
				if (poContentType.equals(oTPM.getMoContentType())==true) {
					oRetVal.add(oTPM);
					if (bStopAtFirstMatch==true) {
						break;
					}
				}
			} else if (poContentType==null && poContent!=null) {
				if (poContent.equals(oTPM.getMoContent())==true) {
					oRetVal.add(oTPM);
					if (bStopAtFirstMatch==true) {
						break;
					}
				}
			} else {
				oRetVal.add(oTPM);
				if (bStopAtFirstMatch==true) {
					break;
				}
			}
		
		return oRetVal;
	}
	
	/**
	 * Delete an object in a mesh. Ony the deleteobject is the input, as the object is deleted as soon as the delete object is 
	 * not connected to any mesh. All meshes, where this object was present are modified.
	 * 
	 * (wendt)
	 *
	 * @since 30.01.2012 21:02:46
	 *
	 * @param poPerceptionalMesh
	 * @param poDeleteObject
	 */
	public static void deleteObjectInMesh(clsThingPresentationMesh poDeleteObject) {
		//In this function the whole tree shall be deleted, of which a structure is connected. Start with the focused element and
		//delete the "delete-Element" as well as the whole tree connected with it.
		
		//Go through all internal associations
		for (clsAssociation oInternalAss : poDeleteObject.getMoAssociatedContent()) {
			//If the root element is this mesh, then...
			if (oInternalAss.getRootElement().equals(poDeleteObject)) {
				//go to the other element, if it is a TPM
				if (oInternalAss.getLeafElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getLeafElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}				
			} else if (oInternalAss.getLeafElement().equals(poDeleteObject)) {
				if (oInternalAss.getRootElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getRootElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}
			}
		}
		
		//Go through all external associations
		for (clsAssociation oInternalAss : poDeleteObject.getExternalMoAssociatedContent()) {
			//If the root element is this mesh, then...
			if (oInternalAss.getRootElement().equals(poDeleteObject)) {
				//go to the other element, if it is a TPM
				if (oInternalAss.getLeafElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getLeafElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}				
			} else if (oInternalAss.getLeafElement().equals(poDeleteObject)) {
				if (oInternalAss.getRootElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getRootElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}
			}
		}
		
		//Now the object is not connected with anything more and can be seen as deleted from the meshes, which it was connected. It does not matter in which mesh it belongs, everything is deleted
	}
	
	/**
	 * Delete a certain association within an object. 
	 * 
	 * The purpose is to remove an association in the target object for each object, which is deleted.
	 * 
	 * (wendt)
	 *
	 * @since 03.02.2012 16:22:47
	 *
	 * @param poSourceTPM: The object which has the association
	 * @param poDeleteObject: The associatited object, which association shall be deleted.
	 */
	public static void deleteAssociationInObject(clsThingPresentationMesh poSourceTPM, clsThingPresentationMesh poDeleteObject) {
		boolean bFound = false;
		clsAssociation oDeleteAss = null;
		
		//Check external associations
		for (clsAssociation oExternalAss : poSourceTPM.getExternalMoAssociatedContent()) {
			if (oExternalAss.getLeafElement().equals(poDeleteObject) || oExternalAss.getRootElement().equals(poDeleteObject)) {
				bFound = true;
				oDeleteAss = oExternalAss;
				break;
			}
		}
		
		//If found, then delete
		if (bFound==true) {
			poSourceTPM.getExternalMoAssociatedContent().remove(oDeleteAss);
		} else {
			//Check internal associations
			for (clsAssociation oExternalAss : poSourceTPM.getMoAssociatedContent()) {
				if (oExternalAss.getLeafElement().equals(poDeleteObject) || oExternalAss.getRootElement().equals(poDeleteObject)) {
					bFound = true;
					oDeleteAss = oExternalAss;
					break;
				}
			}
			
			if (bFound==true) {
				poSourceTPM.getMoAssociatedContent().remove(oDeleteAss);
			}
		}
	}
	
	/**
	 * Move an association from the origin TPM to the target TPM
	 * 
	 * (wendt)
	 *
	 * @since 03.01.2012 20:43:49
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 * @param poAssociation
	 */
	public static void moveAssociation(clsThingPresentationMesh poTargetTPM, clsThingPresentationMesh poOriginTPM, clsAssociation poAssociation) {
		//1. Check if Element A or B has the origin structure
		if ((poAssociation.getRootElement().equals(poOriginTPM)==true) || (poAssociation.getLeafElement().equals(poOriginTPM)==true)) {
			if (poAssociation.getRootElement().equals(poOriginTPM)==true) {
				poAssociation.setRootElement(poTargetTPM);
			} else {
				poAssociation.setLeafElement(poTargetTPM);
			}
			
			if (poOriginTPM.getExternalMoAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetTPM.getExternalMoAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginTPM.getExternalMoAssociatedContent().remove(poAssociation);
			} else if (poOriginTPM.getMoAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetTPM.getMoAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginTPM.getMoAssociatedContent().remove(poAssociation);
			}

		}
	}
	
	/**
	 * Move an association from the origin WPM to the target WPM
	 * 
	 * (wendt)
	 *
	 * @since 03.01.2012 20:43:49
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 * @param poAssociation
	 */
	public static void moveAssociation(clsWordPresentationMesh poTargetWPM, clsWordPresentationMesh poOriginWPM, clsAssociation poAssociation) {
		//1. Check if Element A or B has the origin structure
		if ((poAssociation.getRootElement().equals(poOriginWPM)==true) || (poAssociation.getLeafElement().equals(poOriginWPM)==true)) {
			if (poAssociation.getRootElement().equals(poOriginWPM)==true) {
				poAssociation.setRootElement(poTargetWPM);
			} else {
				poAssociation.setLeafElement(poTargetWPM);
			}
			
			if (poOriginWPM.getExternalAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetWPM.getExternalAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginWPM.getExternalAssociatedContent().remove(poAssociation);
			} else if (poOriginWPM.getAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetWPM.getAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginWPM.getAssociatedContent().remove(poAssociation);
			}

		}
	}
	
	/**
	 * Merge 2 meshes. Only TPM are allowed
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 20:06:35
	 *
	 * @param poSourceTPM
	 * @param poNewMesh
	 */
	public static void mergeMesh(clsThingPresentationMesh poSourceMesh, clsThingPresentationMesh poNewMesh) {
		//Check if both presentations are TPM or WPM. Else nothing is done
		//if (poSourceMesh instanceof clsThingPresentationMesh && poNewMesh instanceof clsThingPresentationMesh) {
		//Get source mesh list
		ArrayList<clsThingPresentationMesh> oSourceTPMList = clsDataStructureTools.getAllTPMImages(poSourceMesh, mnMaxLevel);
			
		//Get the new mesh list
		ArrayList<clsThingPresentationMesh> oNewTPMList = clsDataStructureTools.getAllTPMImages(poNewMesh, mnMaxLevel);
			
		//Go through each mesh in the newMesh
		for (clsThingPresentationMesh oNewTPM : oNewTPMList) {
			//Go through each mesh in the source list
			for (clsThingPresentationMesh oSourceTPM : oSourceTPMList) {
				//If there are IDs with -1, it is not allowed and should be thrown as exception
				if (oSourceTPM.getMoDS_ID()==-1) {
					try {
						throw new Exception("Error: DataStructureTools, mergeMesh: A TPM with ID = -1 was found");
					} catch (Exception e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
				}
				//If the images are equal, then transfer the associations
				if (oSourceTPM.getMoDS_ID() == oNewTPM.getMoDS_ID()) {
					moveAllAssociations(oSourceTPM, oNewTPM);
					break;
				}
			}
		} 
	}
	
	/**
	 * Merge 2 meshes. Only WPM are allowed
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 20:06:35
	 *
	 * @param poSourceTPM
	 * @param poNewMesh
	 */
	public static void mergeMesh(clsWordPresentationMesh poSourceMesh, clsWordPresentationMesh poNewMesh) {
		//Check if both presentations are TPM or WPM. Else nothing is done
		//if (poSourceMesh instanceof clsThingPresentationMesh && poNewMesh instanceof clsThingPresentationMesh) {
		//Get source mesh list
		ArrayList<clsWordPresentationMesh> oSourceWPMList = clsDataStructureTools.getAllWPMImages(poSourceMesh, mnMaxLevel);
			
		//Get the new mesh list
		ArrayList<clsWordPresentationMesh> oNewWPMList = clsDataStructureTools.getAllWPMImages(poNewMesh, mnMaxLevel);
		
		//Create process pairs
		ArrayList<clsPair<clsWordPresentationMesh, clsWordPresentationMesh>> oInstancePairList = new ArrayList<clsPair<clsWordPresentationMesh, clsWordPresentationMesh>>();
		
		//Go through each mesh in the newMesh
		for (int i=0; i<oNewWPMList.size();i++) {
		//for (clsWordPresentationMesh oNewWPM : oNewWPMList) {
		clsWordPresentationMesh oNewWPM = oNewWPMList.get(i);
			//Go through each mesh in the source list
			for (int j=0; j<oSourceWPMList.size();j++) {
			//for (clsWordPresentationMesh oSourceWPM : oSourceWPMList) {
				clsWordPresentationMesh oSourceWPM = oSourceWPMList.get(j);
				//If there are IDs with -1, it is not allowed and should be thrown as exception
				if (oSourceWPM.getMoDS_ID()==-1) {
					try {
						throw new Exception("Error: DataStructureTools, mergeMesh: A TPM with ID = -1 was found");
					} catch (Exception e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
				}
				//If the images are equal, then transfer the associations
				if (oSourceWPM.getMoDS_ID() == oNewWPM.getMoDS_ID()) {
					oInstancePairList.add(new clsPair<clsWordPresentationMesh, clsWordPresentationMesh>(oSourceWPM, oNewWPM));
					break;
				}
			}
		} 
		
		for (clsPair<clsWordPresentationMesh, clsWordPresentationMesh> oInstancePair : oInstancePairList) {
			//Move associations from the new mesh to the source b->a
			moveAllAssociations(oInstancePair.a, oInstancePair.b);
		}
	}
	
	
	/**
	 * Move all associations, which do not already exist from the origin structure to the taregt structure. In that way it is
	 * possible to merge 2 meshes over the same structure.
	 * 
	 * This is the TPM function
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 21:09:18
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 */
	public static void moveAllAssociations(clsThingPresentationMesh poTargetTPM, clsThingPresentationMesh poOriginTPM) {
		//Move all internal associations from origin to target
		for (clsAssociation oOriAss : poOriginTPM.getMoAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetTPM.getMoAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				moveAssociation(poTargetTPM, poOriginTPM, oOriAss);
			}
		}
		
		//Move all external associations from origin to target
		for (clsAssociation oOriAss : poOriginTPM.getExternalMoAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetTPM.getExternalMoAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				moveAssociation(poTargetTPM, poOriginTPM, oOriAss);
			}
		}
	}
	
	/**
	 * Move all associations, which do not already exist from the origin structure to the taregt structure. In that way it is
	 * possible to merge 2 meshes over the same structure
	 * 
	 * This function is only valid for the WPM
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 21:09:18
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 */
	public static void moveAllAssociations(clsWordPresentationMesh poTargetWPM, clsWordPresentationMesh poOriginWPM) {
		//Move all internal associations from origin to target

		//Create a list of associations, which shall be moved
		ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
		
		for (clsAssociation oOriAss : poOriginWPM.getAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetWPM.getAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				oAssList.add(oOriAss);
				//moveAssociation(poTargetWPM, poOriginWPM, oOriAss);
			}
		}
		
		//Move all external associations from origin to target
		for (clsAssociation oOriAss : poOriginWPM.getExternalAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetWPM.getExternalAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				oAssList.add(oOriAss);
				//moveAssociation(poTargetWPM, poOriginWPM, oOriAss);
			}
		}
		
		for (clsAssociation oAss : oAssList) {
			moveAssociation(poTargetWPM, poOriginWPM, oAss);
		}
	}
	
	/**
	 * Check if 2 associations are the same. If yes, give true, else false
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 21:00:36
	 *
	 * @param poAssA
	 * @param poAssB
	 * @return
	 */
	private static boolean checkAssociationExists(clsAssociation poAssA, clsAssociation poAssB) {
		boolean bRetVal = false;
		
		if (poAssA.getMoDS_ID() == poAssB.getMoDS_ID() && 
				poAssA.getRootElement().getMoDS_ID() == poAssB.getRootElement().getMoDS_ID() &&
				poAssA.getLeafElement().getMoDS_ID() == poAssB.getLeafElement().getMoDS_ID() &&
				poAssA.getRootElement().getMoContentType().equals(poAssB.getRootElement().getMoContentType())==true &&
				poAssA.getLeafElement().getMoContentType().equals(poAssB.getLeafElement().getMoContentType())==true) {
					bRetVal = true;
		}
		
		return bRetVal;
	}

	
	/**
	 * Move an association from the origin TPM to the target TPM
	 * 
	 * (wendt)
	 *
	 * @since 03.01.2012 20:43:49
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 * @param poAssociation
	 */
	private static ArrayList<clsAssociation> TEMPmoveAssociation(clsPhysicalStructureComposition poTargetTI, clsThingPresentationMesh poOriginTPM, clsAssociation poAssociation) {
		ArrayList<clsAssociation> oRetVal = new ArrayList<clsAssociation>();
		
		//1. Check if Element A or B has the origin structure
		if ((poAssociation.getRootElement().equals(poOriginTPM)==true) || (poAssociation.getLeafElement().equals(poOriginTPM)==true)) {
			if (poAssociation.getRootElement().equals(poOriginTPM)==true) {
				poAssociation.setRootElement(poTargetTI);
			} else {
				poAssociation.setLeafElement(poTargetTI);
			}
			
			if (poOriginTPM.getExternalMoAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				oRetVal.add(poAssociation);
				//poTargetTI.getExternalMoAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginTPM.getExternalMoAssociatedContent().remove(poAssociation);
			} else if (poOriginTPM.getMoAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetTI.getMoAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginTPM.getMoAssociatedContent().remove(poAssociation);
			}

		}
		return oRetVal;
	}
	
	/**
	 * Transfer all associations from a TPM to a TI
	 * 
	 * (wendt)
	 *
	 * @since 03.02.2012 16:36:51
	 *
	 * @param poTargetTI
	 * @param poOriginTPM
	 * @return
	 */
	public static ArrayList<clsAssociation> TEMPmoveAllAssociations(clsPhysicalStructureComposition poTargetTI, clsThingPresentationMesh poOriginTPM) {
		ArrayList<clsAssociation> oNewInternalList = new ArrayList<clsAssociation>();
		ArrayList<clsAssociation> oNewExtAssociationList = new ArrayList<clsAssociation>();
		
		ArrayList<clsAssociation> oNewReturnAssociationList = new ArrayList<clsAssociation>();
		
		oNewExtAssociationList.addAll(poOriginTPM.getExternalMoAssociatedContent());
		for (clsAssociation oAss1 : oNewExtAssociationList) {
			oNewReturnAssociationList.addAll(TEMPmoveAssociation(poTargetTI, poOriginTPM, oAss1));
		}
		
		oNewInternalList.addAll(poOriginTPM.getMoAssociatedContent());
		for (clsAssociation oAss2 : oNewInternalList) {
			oNewReturnAssociationList.addAll(TEMPmoveAssociation(poTargetTI, poOriginTPM, oAss2));
		}
		
		//Move all internal associations to the container
		for (clsAssociation oAss : poTargetTI.getMoAssociatedContent()) {
			clsThingPresentationMesh oSubTPM = (clsThingPresentationMesh) oAss.getLeafElement();
			
			oNewReturnAssociationList.addAll(oSubTPM.getExternalMoAssociatedContent());
		}
		
		return oNewReturnAssociationList;
	}
	
	/**
	 * Find a PA datastructure in any arraylist and return the object from the arraylist
	 * 
	 * (wendt)
	 *
	 * @since 03.02.2012 16:40:24
	 *
	 * @param poList
	 * @param poSearchDS
	 * @return
	 */
	public static <E extends clsDataStructurePA> E findPADataStructureInArrayList(ArrayList<E> poList, E poSearchDS) {
		E oRetVal = null;
		
		//check invalid IDs
		if (poSearchDS.getMoDS_ID() == -1) {
			try {
				throw new Exception("clsDataStructureTools, findPADataStructureInArrayList: Invalid Input ID");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (E oDS : poList) {
			if (oDS.getMoDS_ID() == -1) {
				try {
					throw new Exception("clsDataStructureTools, findPADataStructureInArrayList: Invalid DatastructurePA ID");
				} catch (Exception e) {
					// TODO (wendt) - Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (oDS.getMoDS_ID() == poSearchDS.getMoDS_ID()) {
				oRetVal = oDS;
			}
		}
		
		return  oRetVal;
	}
	
	public static clsThingPresentationMesh getPrimaryComponentOfWPM(clsWordPresentationMesh poInput) {
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
	 * Return the image, of which the objects belongs to
	 * 
	 * (wendt)
	 *
	 * @since 08.02.2012 13:30:42
	 *
	 * @param poInput
	 * @return
	 */
	public static clsWordPresentationMesh getHigherLevelImage(clsWordPresentationMesh poInput) {
		clsWordPresentationMesh oRetVal = null;
		
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				//If the the predicate is PARTOF, then this object is a part of some image. Give the image back
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(ePredicate.PARTOF.toString())) {
					//The super object, i.e. image is always the root element
					oRetVal = (clsWordPresentationMesh) oAss.getRootElement();
				}
			}
		}
		
		return oRetVal;
	}
	
	
	
}