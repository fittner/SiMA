/**
 * clsSearchSpaceMainMemory.java: DecisionUnits - pa.informationrepresentation.ARSi10.searchspace
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Hashtable;

import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.model.Slot;

import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsAssociationTime;
import pa.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.enums.eDataTypeInit;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 * 
 */
public class clsOntologyLoader {
		
	public static void loadOntology(Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureList){
		//TODO HZ: Configure the different ontologyTypes
		loadProtegeOntology(poDataStructureList); 
	}
	
	private static void loadProtegeOntology(Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureList){
		//TODO HZ: Make the project file-path configurable
		//FIXME HZ: Sorry for the "Object" parameter in ArrayList => however, this is a protege problem
		Collection <?>oErrorList = new ArrayList<Object>(); 
		Project oOntologyPrj = Project.loadProjectFromFile("S:/BWsimOnt/ARSi10.pprj", oErrorList);
	    KnowledgeBase oFrameKB = oOntologyPrj.getKnowledgeBase();
	    
	    
	  //FIXME HZ: Optimize the initialization process => Builder
		for(eDataTypeInit oDataType : eDataTypeInit.values())	{
			for(Instance oDataElement : oFrameKB.getCls(oDataType.name()).getInstances()){
				createDataStructuresPA(eDataType.valueOf(oDataType.toString()), new clsPair <clsDataStructurePA, Instance>(null, oDataElement), oFrameKB, poDataStructureList); 
			}
		}
}

	/**
	 * DOCUMENT (zeilinger) - This method loads the different data structures from the ontology and maps it to objects of the
	 * package type pa.datatypes. The loading process does not start at the atomic datastructures (thing-presentations
	 * or word presetnation) but at rich data structures like thing-presentation-meshes (TPM) and template-images (TI).
	 * As sketched in the figure below, the rich data structures are intialized first and therefore intialize atomic
	 * data structures that are required to form the rich data structure 
	 * => TPM requires associations => associations require two elements (TPM and TPs) => TPs are intialized
	 * The same is true for TIs and ACTs.     
	 * 
	 * NOTE: In case an atomic data structure exist in the ontology but is not required by a rich data 
	 * structure, it is not initialized in the actual version. 
	 * 
	 * @@@ FIGURE
	 * 
	 * @author zeilinger
	 * 21.06.2010, 15:54:23
	 *
	 * @param frameKB
	 * @param poDataStructurePA
	 */
	private static void createDataStructuresPA(eDataType poDataType,clsPair <clsDataStructurePA, Instance> poDataElements, KnowledgeBase poFrameKB, Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {
		switch(poDataType){
			case ACT:
				createACT(poDataElements, poFrameKB, poDataStructurePA);
				break;
			case ASSOCIATION:
				createAssociation(poDataElements, poFrameKB, poDataStructurePA);
				break; 
			case DM:
				createDM(poDataElements, poFrameKB, poDataStructurePA);
				break; 
			case TI:
				createTI(poDataElements, poFrameKB, poDataStructurePA);
				break; 
			case TPM:
				createTPM(poDataElements, poFrameKB, poDataStructurePA);
				break; 
			case TP:
				createTP(poDataElements, poFrameKB, poDataStructurePA);
				break; 
			default: throw new NoSuchFieldError(" datatype " + poDataType +" is not handled"); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.06.2010, 21:24:42
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createDM(
			clsPair<clsDataStructurePA, Instance> poDataElements,
			KnowledgeBase poFrameKB,
			Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:12:04
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createTP(
			clsPair<clsDataStructurePA, Instance> poDataElements,KnowledgeBase poFrameKB,
						Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {
		
		clsThingPresentation oDataStructure = new clsThingPresentation(new ArrayList<clsAssociation>(), 
																		poDataElements.b.getName(),
																		eDataType.TI);
		Instance oDataElement = poDataElements.b; 
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", oDataElement); 
				
				for(Object element : oInstanceAssociations){
					Instance oAssociation = (Instance) element; 
					oDataStructure.assignDataStructure(loadAssociation(oAssociation, oDataStructure,poFrameKB, poDataStructurePA )); 
			}
		
			//TODO HZ: Define other attributes!! 
		poDataStructurePA.get(eDataType.TI).add(oDataStructure);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:12:01
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createTPM(
			clsPair<clsDataStructurePA, Instance> poDataElements,
			KnowledgeBase poFrameKB,
			Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {

		clsThingPresentationMesh oDataStructure = new clsThingPresentationMesh(new ArrayList<clsAssociation>(), 
				new ArrayList<clsAssociation>(), 
				new ArrayList<clsAssociation>(),
				poDataElements.b.getName(),
				eDataType.TPM);
		
		Instance oDataElement = poDataElements.b; 
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", oDataElement);
					
		for(Object element : oInstanceAssociations){
				Instance oAssociation = (Instance) element; 
				oDataStructure.assignDataStructure(loadAssociation(oAssociation, oDataStructure,poFrameKB, poDataStructurePA )); 
		}
			
		poDataStructurePA.get(eDataType.TPM).add(oDataStructure);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.06.2010, 23:14:12
	 *
	 * @param association
	 * @param dataStructure
	 * @param poFrameKB
	 * @param poDataStructurePA
	 * @return
	 */
	private static clsAssociation loadAssociation(Instance poAssociation, clsDataStructurePA poDataStructure, 
							KnowledgeBase poFrameKB, Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {
		
		eDataType eElementType = getElementDataType(poAssociation); 
		if(retrieveDataStructure(eElementType, poAssociation.getName(),poDataStructurePA)== null){
			createDataStructuresPA(eElementType,new clsPair<clsDataStructurePA, Instance>(poDataStructure, poAssociation), poFrameKB, poDataStructurePA);
		}
		return (clsAssociation)retrieveDataStructure(eElementType, poAssociation.getName(),poDataStructurePA);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:11:58
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createAssociation(clsPair<clsDataStructurePA, Instance> poDataElements,
			KnowledgeBase poFrameKB, Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {

		clsAssociation oDataStructure = null; 
	    Instance oDataElement = poDataElements.b; 
				
	    Collection <?> oElements = getSlotValues("instance_association", oDataElement);
	    for(Object element : oElements ){
		    	Instance oElementB = (Instance) element; 
		    	
		    	if(!(oElementB.getName().equals(poDataElements.a.oDataStructureID))){
					 eDataType eElementType = getElementDataType(oElementB);
		    		 createDataStructuresPA(eElementType,new clsPair<clsDataStructurePA, Instance>(null, oElementB), poFrameKB, poDataStructurePA);
		    		 clsAssociation oDataStructureB = (clsAssociation)retrieveDataStructure(eElementType, oElementB.getName(),poDataStructurePA);
		    		 oDataStructure = getNewAssociation(eElementType, poDataElements, oDataStructureB);  
		    	}
		    	
	    }
    	//TODO HZ: Define other attributes!!
		poDataStructurePA.get(eDataType.ASSOCIATION).add(oDataStructure);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.06.2010, 08:02:09
	 *
	 * @param elementType
	 * @param poDataElements
	 * @return
	 */
	private static clsAssociation getNewAssociation(eDataType peElementType, 
							clsPair<clsDataStructurePA, Instance> poDataElements,clsDataStructurePA poDataElementB) {
		
		clsPair<clsDataStructurePA, clsDataStructurePA> oAssociationElements; 
		
		switch(peElementType){
			case ASSCOCIATIONATTRIBUTE:
				//HZ Actually it is defined that there are attribute associations only between physical represetnation
				//	 objects. 
				//evaluate which element has to be introduced as elementA or elementB
				oAssociationElements = evaluateElementOrder(poDataElements.a, poDataElementB, eDataType.TPM); 											
			 	return new clsAssociationAttribute((clsThingPresentationMesh)oAssociationElements.a, 
												   (clsPhysicalRepresentation)oAssociationElements.b, 
												    poDataElements.b.getName(), peElementType); 
			case ASSOCIATIONTEMP:
				oAssociationElements = evaluateElementOrder(poDataElements.a, poDataElementB, eDataType.TI);
				return new clsAssociationTime((clsTemplateImage)oAssociationElements.a, 
						   (clsPhysicalRepresentation)oAssociationElements.b, 
						    poDataElements.b.getName(), peElementType); 
			
			case ASSOCIATIONDM:
				oAssociationElements = evaluateElementOrder(poDataElements.a, poDataElementB, eDataType.TP);
				return new clsAssociationDriveMesh((clsDriveMesh)oAssociationElements.a, 
						   (clsDataStructurePA)oAssociationElements.b, 
						    poDataElements.b.getName(), peElementType); 
			
			case ASSOCIATIONWP:
				oAssociationElements = evaluateElementOrder(poDataElements.a, poDataElementB, eDataType.WP);
			
				return new clsAssociationWordPresentation((clsWordPresentation)oAssociationElements.a, 
						   (clsDataStructurePA)oAssociationElements.b, 
						    poDataElements.b.getName(), peElementType); 
		}
		throw new NoSuchFieldError(" association of unknown type: " + peElementType.toString());
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.06.2010, 16:39:30
	 *
	 * @param poDataElements
	 * @param poDataElementB
	 * @param clsPair
	 * @return
	 */
	private static clsPair<clsDataStructurePA, clsDataStructurePA> evaluateElementOrder(
			clsDataStructurePA poDataElementA,
			clsDataStructurePA poDataElementB,
			eDataType poDataType) {
		
		if(poDataElementA.oDataStructureType==poDataType) 
				return new clsPair<clsDataStructurePA, clsDataStructurePA>(poDataElementA, poDataElementB);  
		else if (poDataElementB.oDataStructureType==poDataType) 
				return new clsPair<clsDataStructurePA, clsDataStructurePA>(poDataElementB, poDataElementA);
		
		throw new NoSuchFieldError("The required datatype "+ poDataType +" does not match the data structures"); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:11:56
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createACT(
			clsPair<clsDataStructurePA, Instance> poDataElements,
			KnowledgeBase poFrameKB,
			Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {

		clsAct oDataStructure = new clsAct(new ArrayList<clsAssociation>(), 
				poDataElements.b.getName(),
				eDataType.ACT);

		Instance oDataElement = poDataElements.b; 
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", oDataElement);
		
		for(Object element : oInstanceAssociations){
			Instance oAssociation = (Instance) element; 
			oDataStructure.assignDataStructure(loadAssociation(oAssociation, oDataStructure,poFrameKB, poDataStructurePA )); 
 
		}
		
		poDataStructurePA.get(eDataType.ACT).add(oDataStructure); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.06.2010, 17:02:33
	 *
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createTI(clsPair<clsDataStructurePA, Instance> poDataElements, KnowledgeBase poFrameKB,Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {
		clsTemplateImage oDataStructure = new clsTemplateImage(new ArrayList<clsAssociation>(), 
													new ArrayList<clsAssociation>(), 
													new ArrayList<clsAssociation>(),
													poDataElements.b.getName(),
													eDataType.TI);
				
		Instance oDataElement = poDataElements.b; 
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", oDataElement);
					
		for(Object element : oInstanceAssociations){
			Instance oAssociation = (Instance) element; 
			oDataStructure.assignDataStructure(loadAssociation(oAssociation, oDataStructure,poFrameKB, poDataStructurePA )); 
		}
			
		poDataStructurePA.get(eDataType.TI).add(oDataStructure); 
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.06.2010, 15:46:17
	 * @param poDataStructurePA 
	 * @param string 
	 * @param dataType 
	 *
	 * @return
	 */
	private static clsDataStructurePA retrieveDataStructure(eDataType dataType, 
									String poElementName, Hashtable<eDataType, List<clsDataStructurePA>> poDataStructurePA) {
		for(clsDataStructurePA element : poDataStructurePA.get(dataType)){
			if(element.oDataStructureID.equals(poElementName)) return element; 
		}
		throw new NoSuchFieldError(" no such datastructure is found");
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.06.2010, 06:53:47
	 *
	 * @param association
	 * @return
	 */
	private static eDataType getElementDataType(Instance poElement) {
		String oElementType = (String)getSlotValue("type", poElement); 
		//TODO HZ: This solution is not very nice as every string of the slot-value "type" is mapped to a enum of eDataType
		//		   by the us of if statements.
		//         A more generic and from my point of view nicer solution would be to write eDataType.valueOf(oElementType)
		//		   in order to get the data type. However therefore it has to be guaranteed that types in the onotlogy
		//		   have the same name es in eDataType. Up to now this cannot be guaranteed but should be changed in the 
		//		   future. 
		//FIXME HZ: This has to be definitely changed to a more generic form - see above
 		if(oElementType.equals("thing_presentation")) return eDataType.TP; 
 		else if(oElementType.equals("thing_presentation_mesh")) return eDataType.TPM;
 		else if(oElementType.equals("word_presentation")) return eDataType.WP;
 		else if(oElementType.equals("association_attributes")) return eDataType.ASSCOCIATIONATTRIBUTE;
 		else if(oElementType.equals("association_drive")) return eDataType.ASSOCIATIONDM;
 		else if(oElementType.equals("association_temp")) return eDataType.ASSOCIATIONTEMP;
 		else if(oElementType.equals("association_wp")) return eDataType.ASSOCIATIONWP;
 		else if(oElementType.equals("drive_mesh")) return eDataType.DM;
 		else if(oElementType.equals("template_image")) return eDataType.TI;
 		else if(oElementType.equals("act")) return eDataType.ACT;
		
 		throw new NoSuchFieldError(" Element of type " + oElementType + " not handled");
	}
	
	private static Object getSlotValue(String poSlotName, Instance poElement){
		for(Slot oOwnSlot: poElement.getOwnSlots()){
			if(oOwnSlot.getName().equals(poSlotName)){
				return poElement. getOwnSlotValue(oOwnSlot);
			}
		}
		
		throw new NoSuchFieldError(" there is no slot with the name " + poSlotName + " found "); 
	}
	
	private static Collection <?> getSlotValues(String poSlotName, Instance poElement){
		for(Slot oOwnSlot: poElement.getOwnSlots()){
			if(oOwnSlot.getName().equals(poSlotName)){
				return poElement. getOwnSlotValues(oOwnSlot);
			}
		}
		
		throw new NoSuchFieldError(" there is no slot with the name " + poSlotName + " found ");
	}
}

