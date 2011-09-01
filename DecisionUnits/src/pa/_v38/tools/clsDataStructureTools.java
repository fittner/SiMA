/**
 * CHANGELOG
 *
 * 20.07.2011 deutsch - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;

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
	 * (wendt)
	 *
	 * @since 22.07.2011 10:01:54
	 *
	 * @param poContainerA
	 * @param poContainerB
	 * @param prWeight
	 */
	public static void createAssociationPrimary(clsDataStructureContainer poContainerA, clsDataStructureContainer poContainerB, double prWeight) {
		String oContentType = "PIASSOCIATION";
		clsAssociationPrimary oAssPri = (clsAssociationPrimary)clsDataStructureGenerator.generateASSOCIATIONPRI(oContentType, poContainerA.getMoDataStructure(), poContainerB.getMoDataStructure(), prWeight);
		poContainerA.getMoAssociatedDataStructures().add(oAssPri);
		poContainerB.getMoAssociatedDataStructures().add(oAssPri);
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
	 * For a secondary data structure in a container, get the primary data structure in a container if it is available in the input list
	 * (wendt)
	 *
	 * @since 01.08.2011 16:14:31
	 *
	 * @param poInput
	 * @param poSourceList
	 * @return
	 */
	public static clsPrimaryDataStructureContainer extractPrimaryContainer(clsSecondaryDataStructureContainer poInput, ArrayList<clsDataStructureContainer> poSourceList) {
		clsPrimaryDataStructureContainer oRetVal = null;
		
		//Go through the container and search for associationWP
		if (poInput!=null) {
			for (clsAssociation oAss : poInput.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationWordPresentation) {
					//Check if the primary data structure is a part of the root or the leaf element
					if (oAss.getLeafElement() instanceof clsPrimaryDataStructure) {
						oRetVal = (clsPrimaryDataStructureContainer) getContainerFromList(poSourceList, oAss.getLeafElement());
						break;
					} else if (oAss.getRootElement() instanceof clsPrimaryDataStructure) {
						oRetVal = (clsPrimaryDataStructureContainer) getContainerFromList(poSourceList, oAss.getRootElement());
						break;
					}
				}
			}
	}
		
		return oRetVal;
	}
	
	/**
	 * Extract a primarydatastructurecontainer from a secondarydatastructurecontainer
	 * (wendt)
	 *
	 * @since 22.07.2011 18:26:09
	 *
	 * @param poSourceList
	 * @param poDS
	 * @return
	 */
	public static clsDataStructureContainer getContainerFromList(ArrayList<clsDataStructureContainer> poSourceList, clsDataStructurePA poDS) {
		clsDataStructureContainer oRetVal = null;
		
		for (clsDataStructureContainer oContainer : poSourceList) {
			if (oContainer.getMoDataStructure().getMoDS_ID() == poDS.getMoDS_ID()) {
				oRetVal = oContainer;
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
	public static void setClassification(clsSecondaryDataStructureContainer poContainer, String poClassification) {
		//Check if such an association already exists
		clsWordPresentation oWP = getClassification(poContainer);
		//if (oWP)
		//String oClassification = getClassification(poContainer);
				
		if (oWP==null) {
			//Add new association
			//Cases: "" != MOMENT and EXPECTATION != MOMENT
			clsWordPresentation oClass = clsDataStructureGenerator.generateWP(new clsPair<String, Object>("CLASSIFICATION", poClassification));
			clsAssociationSecondary oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("ASSOCIATIONSECONDARY", poContainer.getMoDataStructure(), oClass, moPredicateClassification, 1.0);
			poContainer.getMoAssociatedDataStructures().add(oNewAss);
		} else if (oWP.getMoContent().equals(poClassification) == false) {
			//Replace content of the current classification
			oWP.setMoContent(poClassification);
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
	public static clsWordPresentation getClassification(clsSecondaryDataStructureContainer poContainer) {
		clsWordPresentation oRetVal = null;
		
		//A container can only have ONE classification
		//TODO AW: Check if more than one classification may be necessary
		
		for (clsAssociation oAss : poContainer.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(moPredicateClassification)) {
					oRetVal = ((clsWordPresentation)oAss.getLeafElement());
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
}
