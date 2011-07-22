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
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;

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
	public static <E extends clsDataStructureContainer> ArrayList<E> createInstanceFromTypeList(ArrayList<E> poInput) {
		ArrayList<E> oRetVal = poInput; //TD 2011/07/20 - removed deepCopy. this has to be decided by the one who calls this method and done there.
		
		//Set Unique IDs for all root elements
		for (E oElement : oRetVal) {
			createInstanceFromType(oElement);
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
	public static <E extends clsDataStructureContainer> void createInstanceFromType(E poElement) {
		int oInstanceID;	//
		//Check if the root (DS) element already have an unique ID
		if (poElement.getMoDataStructure().getMoDSInstance_ID() == 0) {
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
	
	public static clsPrimaryDataStructureContainer extractPrimaryContainer(clsSecondaryDataStructureContainer poInput, ArrayList<clsDataStructureContainer> poSourceList) {
		clsPrimaryDataStructureContainer oRetVal = null;
		
		//Go through the container and search for associationWP
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
}
