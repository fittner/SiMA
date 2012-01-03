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
		clsAssociationTime oAssTemp = (clsAssociationTime)clsDataStructureGenerator.generateASSOCIATIONTIME(oContentType, poSuperStructure, poSubStructure, prWeight);
		poSuperStructure.getMoAssociatedContent().add(oAssTemp);
		poSubStructure.getExternalMoAssociatedContent().add(oAssTemp);
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
		ArrayList<clsSecondaryDataStructure> oWPList = getAttributeOfSecondaryPresentation(poContainer, poPredicate);
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
	public static ArrayList<clsSecondaryDataStructure> getAttributeOfSecondaryPresentation(clsSecondaryDataStructureContainer poContainer, String poPredicate) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		//A container can only have ONE classification
		//TODO AW: Check if more than one classification may be necessary
		
		for (clsAssociation oAss : poContainer.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate)) {
					oRetVal.add((clsSecondaryDataStructure)oAss.getLeafElement());
				}
			}
		}
		
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
			(clsTemplateImage)oImage.getMoDataStructure(), 
			(clsPrimaryDataStructure)oC.getMoDataStructure());
			
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
	public static clsThingPresentationMesh findDataStructureInstanceInMesh(clsThingPresentationMesh poTargetImageMesh, clsThingPresentationMesh poFindDataStructure, int pnLevel) {
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
		
		ArrayList<clsThingPresentationMesh> oAllTPMInMesh = getAllTPMImages(poTargetImageMesh, pnLevel);
		
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
			for (clsAssociation oImageDS : ((clsWordPresentationMesh)poTargetImage.getMoDataStructure()).getMoAssociatedContent()) {
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
			for (clsAssociation oSecondaryIntrinsicAssociation : ((clsWordPresentationMesh)poImage.getSecondaryComponent().getMoDataStructure()).getMoAssociatedContent()) {
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
		
		addTPMToImage(poInput, oConstructedImage);
		
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
	 * @param poInput
	 * @param poMesh
	 */
	public static void addTPMToImage(ArrayList<clsThingPresentationMesh> oAddList, clsThingPresentationMesh oImage) {
		//Modify the image by adding additional compontents
		
		for (clsThingPresentationMesh oC : oAddList) {
			clsAssociationTime oAssTime = new clsAssociationTime(new clsTriple<Integer, eDataType, String> 
			(-1, eDataType.ASSOCIATIONTEMP, eDataType.ASSOCIATIONTEMP.toString()), 
			oImage, oC);
			
			//Add Timeassociation
			oImage.assignDataStructure(oAssTime);
			
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
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 09:51:50
	 *
	 * @param poMesh
	 * @param pnLevel  - Level of search depth. If = 1, only direct matches are used
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> getDataStructureInMesh(clsThingPresentationMesh poMesh, eDataType poDataType, String poContentType, String poContent, boolean pbStopAtFirstMatch, int pnLevel) {
		ArrayList<clsThingPresentationMesh> oAddedElements = new ArrayList<clsThingPresentationMesh>();
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through each TPM (Node) and search for the defined structures. This is a recursive function
		searchDataStructureInMesh(poMesh, oAddedElements, oRetVal, poDataType, poContentType, poContent, pbStopAtFirstMatch, pnLevel);
		
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
	private static void searchDataStructureInMesh(clsThingPresentationMesh poMesh, ArrayList<clsThingPresentationMesh> poAddedElements, ArrayList<clsDataStructurePA> poRetVal, eDataType poDataType, String poContentType, String poContent, boolean pbStopAtFirstMatch, int pnLevel) {
		//ArrayList<clsThingPresentationMesh> oRetVal = poAddedElements;
		
		//Add the structure itself to the list of passed elements
		poAddedElements.add(poMesh);
		//Check this data structure for filter options and add the result to the result list if filter fits
		if (poDataType.equals(eDataType.TPM)==true) {
			//Check if this mesh matches the content and content type filter. If yes, then add the result
			boolean bMatchFound = FilterTPM(poMesh, poContentType, poContent);
			if (bMatchFound==true) {
				poRetVal.add(poMesh);
			}
		} else if (poDataType.equals(eDataType.TP)==true) {
			ArrayList<clsAssociationAttribute> oFoundTPAssList = getTPAssociations(poMesh, poContentType, poContent, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundTPAssList);
		} else if (poDataType.equals(eDataType.DM)==true) {
			ArrayList<clsAssociationDriveMesh> oFoundDMAssList = FilterDMList(poMesh.getExternalMoAssociatedContent(), poContentType, poContent, pbStopAtFirstMatch);
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
						searchDataStructureInMesh((clsThingPresentationMesh) oAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentType, poContent, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oAss.getRootElement())==false && oAss.getRootElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInMesh((clsThingPresentationMesh) oAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentType, poContent, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
			
			//Add external associations
			if ((pnLevel>1) || (pnLevel==-1)) {
				for (clsAssociation oExtAss : poMesh.getExternalMoAssociatedContent()) {
					if (poAddedElements.contains(oExtAss.getLeafElement())==false && oExtAss.getLeafElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInMesh((clsThingPresentationMesh) oExtAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentType, poContent, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oExtAss.getRootElement())==false && oExtAss.getRootElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInMesh((clsThingPresentationMesh) oExtAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentType, poContent, pbStopAtFirstMatch, pnLevel-1);
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
		
			if (poContentType!=null && poContent!=null) {
				if ((poContentType.equals(poMesh.getMoContentType())==true) &&
						(poContent.equals(poMesh.getMoContent())==true)) {
					oRetVal = true;
				}
			} else if (poContentType!=null && poContent==null) {
				if (poContentType.equals(poMesh.getMoContentType())==true) {
					oRetVal = true;
				}
			} else if (poContentType==null && poContent!=null) {
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
	 * If poContent = null, then it is not a criteria
	 * If poContentType = null, then it is not a criteria
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
	private static ArrayList<clsAssociationAttribute> getTPAssociations(clsThingPresentationMesh poTPM, String poContentType, String poContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationAttribute> oRetVal = new ArrayList<clsAssociationAttribute>();

		//Go through all external
		oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), poContentType, poContent, pbStopAtFirstMatch));
		if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
			//Go through the external list
			oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), poContentType, poContent, pbStopAtFirstMatch));
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
				if (poContentType!=null && poContent!=null) {
					if ((poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) &&
							(poContent.equals(((clsThingPresentation)oAss.getLeafElement()).getMoContent().toString())==true)) {
						oRetVal.add((clsAssociationAttribute) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType!=null && poContent==null) {
					if (poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) {
						oRetVal.add((clsAssociationAttribute) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType==null && poContent!=null) {
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
			//Check if dm has the corrent content and content type
			if (oAss instanceof clsAssociationDriveMesh) {
				if (poContentType!=null && poContent!=null) {
					if ((poContentType.equals(((clsAssociationDriveMesh)oAss).getLeafElement().getMoContentType())==true) &&
							(poContent.equals(((clsDriveMesh)oAss.getLeafElement()).getMoContent().toString())==true)) {
						oRetVal.add((clsAssociationDriveMesh) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType!=null && poContent==null) {
					if (poContentType.equals(((clsAssociationDriveMesh)oAss).getLeafElement().getMoContentType())==true) {
						oRetVal.add((clsAssociationDriveMesh) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType==null && poContent!=null) {
					if (poContent.equals(((clsDriveMesh)oAss.getLeafElement()).getMoContent().toString())==true) {
						oRetVal.add((clsAssociationDriveMesh) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else {
					oRetVal.add((clsAssociationDriveMesh) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
		
		return oRetVal;
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
		oFoundImages.addAll(getDataStructureInMesh(poPerceptionalMesh, eDataType.TPM, eContentType.PI.toString(), null, true, pnLevel));
		
		//Add all RI. 
		oFoundImages.addAll(getDataStructureInMesh(poPerceptionalMesh, eDataType.TPM, eContentType.RI.toString(), null, false, pnLevel));
		
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
		oFoundImages.addAll(getDataStructureInMesh(poPerceptionalMesh, eDataType.TPM, null, null, false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	public static ArrayList<clsAssociationDriveMesh> getAllDMInMesh(clsThingPresentationMesh poPerceptionalMesh) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		//This is an unconverted clsAssociationDriveMesh
		ArrayList<clsDataStructurePA> oFoundList = clsDataStructureTools.getDataStructureInMesh(poPerceptionalMesh, eDataType.DM, null, null, false, 1);
		
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
	
	public static void deleteObjectInMesh(clsThingPresentationMesh poPerceptionalMesh, clsDataStructurePA poDeleteObject) {
		//In this function the whole tree shall be deleted, of which a structure is connected. Start with the focused element and
		//delete the "delete-Element" as well as the whole tree connected with it.
		
		//Todo AW: Implement this function!!!!!!!!!
		
		
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
		
		return oNewExtAssociationList;
	}
	
	
	
}