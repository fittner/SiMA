/**
 * CHANGELOG
 *
 * 20.07.2011 deutsch - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.ListIterator;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
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
		
	public static clsDataStructurePA getDataStructureFromImage(clsDataStructurePA poSearchStructure, clsPrimaryDataStructureContainer poSearchInImage) {
		clsDataStructurePA oRetVal = null;

		if (poSearchStructure.getMoDS_ID() == poSearchInImage.getMoDataStructure().getMoDS_ID()) {
			oRetVal = poSearchInImage.getMoDataStructure();
		} else {
			clsTemplateImage oDS = (clsTemplateImage)poSearchInImage.getMoDataStructure();
			for (clsAssociation oSubDS : oDS.getMoInternalAssociatedContent()) {
				if (oSubDS.getLeafElement().getMoDS_ID() == poSearchStructure.getMoDS_ID()) {
					oRetVal = oSubDS.getLeafElement();
					break;
				}
			}
		}
		
		return oRetVal;
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
		//clsWordPresentationMesh oRetVal = null;
		//Compare IDs of the structures

		//Go through the internal structures
		for (clsAssociation oIntAss : poMesh.getMoInternalAssociatedContent()) {
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
			for (clsAssociation oImageDS : ((clsWordPresentationMesh)poTargetImage.getMoDataStructure()).getMoInternalAssociatedContent()) {
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
			for (clsAssociation oSecondaryIntrinsicAssociation : ((clsWordPresentationMesh)poImage.getSecondaryComponent().getMoDataStructure()).getMoInternalAssociatedContent()) {
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
			for (clsAssociation oAss : poMesh.getMoInternalAssociatedContent()) {
				if (oAss.getLeafElement().getMoDS_ID() == poSearchStructure.getMoDS_ID()) {
					oRetVal = oAss.getLeafElement();
					break;
				}
			}
		}
		
		return oRetVal;
	}
	

	
//	public static ArrayList<clsDataStructurePA> getStructureInMesh(clsThingPresentationMesh poMesh, String poContentType, String poContent, boolean pbStopAtFirstMatch, int pnLevel) {
//		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
//		
//		getAllTPMInMesh(poMesh, oRetVal, poContentType, poContent, pbStopAtFirstMatch, pnLevel);
//		
//		return oRetVal;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	

	
	
	
		
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
}