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
import pa.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.enums.eDataStructureMatch;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 * 
 */
public class clsOntologyLoader {
	public static void loadOntology(Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureTable, String poSourceName){
		//TODO HZ: Configure the different ontologyTypes
		initDataStructureList(poDataStructureTable);
		//FIXME HZ This if-statement is defined for testing reasons as the Search Space should be set for the 
		// JUnit tests (e.g. tssInformationRepresentationManagementARSi10) in order to be independent from changes in the ontology. Of cause an unchangeable test-ontology 
		// is a possiblity to introduce a permanent solution.
		if(poSourceName != ""){
			initOntology(poDataStructureTable, poSourceName); 
		}
		else{
			System.out.println("The search space will be set manually"); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.06.2010, 13:46:11
	 *
	 * @param poDataStructureList
	 */
	private static void initDataStructureList(Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureTable) {
		for(eDataType element : eDataType.values()) {
			poDataStructureTable.put(element,new ArrayList<clsDataStructurePA>());
		}
	}
	
	private static void initOntology(Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureList, String poSourceName){
		//FIXME HZ: Sorry for the "Object" parameter in ArrayList => however, this is a protege problem
		Collection <?>oErrorList = new ArrayList<Object>(); 
		Project oOntologyPrj = Project.loadProjectFromFile(poSourceName, oErrorList);
	    KnowledgeBase oFrameKB = oOntologyPrj.getKnowledgeBase();
		    
	  //FIXME HZ: Optimize the initialization process => Builder
		for(eDataType oDataType : initValues())	{
			for(Instance oDataElement : oFrameKB.getCls(oDataType.name()).getInstances()){
				initDataStructure(null, oDataElement, new clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>>(oFrameKB, poDataStructureList)); 
			}
		}
	}
	
	public static eDataType[] initValues(){
		eDataType [] oRetVal = {eDataType.ASSOCIATIONWP, 
								eDataType.ASSOCIATIONDM, 
								eDataType.ACT,
								eDataType.DM,
								eDataType.TI,
								eDataType.TP,
								eDataType.TPM,
								eDataType.WP};
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 26.06.2010, 20:33:15
	 *
	 * @param elementTypeB
	 * @param elementBName
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void initDataStructure(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {
		
		eDataType oElementDataType = getElementDataType(poElement); 
		if(retrieveDataStructure(oElementDataType, poElement.getName(),poDataContainer.b)== null){
			 createDataStructure(oElementDataType, poElement, poRootElement, poDataContainer);
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
	private static void createDataStructure(eDataType poDataType, Instance poElement,clsDataStructurePA poRootElement,
							clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer){
		switch(poDataType){
			case ACT:
				createACT(poRootElement, poElement, poDataContainer);
				break;
			case ASSOCIATIONDM:
				createAssociation(poRootElement, poElement, poDataContainer);
				break;
			case ASSOCIATIONWP:
				createAssociation(poRootElement, poElement, poDataContainer);
				break;
			case ASSOCIATIONTEMP:
				createAssociation(poRootElement, poElement, poDataContainer);
				break;
			case ASSOCIATIONATTRIBUTE:
				createAssociation(poRootElement, poElement, poDataContainer);
				break;
			case DM:
				createDM(poRootElement, poElement, poDataContainer);
				break; 
			case TI:
				createTI(poRootElement, poElement, poDataContainer);
				break; 
			case TPM:
				createTPM(poRootElement, poElement, poDataContainer);
				break; 
			case TP:
				createTP(poRootElement, poElement, poDataContainer);
				break; 
			case WP:
				createWP(poRootElement, poElement, poDataContainer);
				break;
			default: throw new NoSuchFieldError(" datatype " + poDataType +" is not handled"); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.06.2010, 15:43:06
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createWP(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {
		
		String oElementName = poElement.getName();
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
								
		clsWordPresentation oDataStructure = new clsWordPresentation(new clsTripple<String, eDataType, String>(oElementName,eDataType.WP,oElementValueType),oElementName);
		//HZ Word Presentation does not obey of any associations
		//TODO HZ: Define other attributes!! 
		poDataContainer.b.get(eDataType.WP).add(oDataStructure);
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
	private static void createDM(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {

		String oElementName = poElement.getName(); 
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		double oDriveCathegoryAnal = (Double)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:anal"));
		double oDriveCathegoryOral = (Double)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:oral"));
		double oDriveCathegoryGenital = (Double)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:genital"));
		double oDriveCathegoryPhalic = (Double)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:phalic"));
		double [] oDriveCathegory = {oDriveCathegoryAnal, oDriveCathegoryOral, oDriveCathegoryGenital, oDriveCathegoryPhalic}; 
				
		clsDriveMesh oDataStructure = new clsDriveMesh(new clsTripple<String, eDataType, String>(oElementName,eDataType.DM,oElementValueType),oDriveCathegory, new ArrayList<clsAssociation>());
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, oDataStructure, poDataContainer); 
				
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationAttribute){oDataStructure.assignDataStructure(element);}
		}
		//TODO HZ: Define other attributes!! 
		poDataContainer.b.get(eDataType.DM).add(oDataStructure);
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
	private static void createTP(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {
		
		String oElementName = poElement.getName(); 
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		Object oElementValue = poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
	
		clsThingPresentation oDataStructure = new clsThingPresentation(new clsTripple<String, eDataType, String>(oElementName,eDataType.TP,oElementValueType),oElementValue);
		loadInstanceAssociations(poElement, oDataStructure, poDataContainer);
		//HZ TP does not obey of any associations 		
		//TODO HZ: Define other attributes!! 
		poDataContainer.b.get(eDataType.TP).add(oDataStructure);
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
	private static void createTPM(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {
		
		String oElementName = poElement.getName(); 
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
				
		clsThingPresentationMesh oDataStructure = new clsThingPresentationMesh(new clsTripple<String, eDataType, String>(oElementName,eDataType.TPM,oElementValueType),new ArrayList<clsAssociation>());
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, oDataStructure, poDataContainer); 
		oAssociationList.addAll(loadClassAssociations(poElement, oDataStructure, poDataContainer));
		
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationAttribute){ oDataStructure.assignDataStructure(element);}
		}
		poDataContainer.b.get(eDataType.TPM).add(oDataStructure);
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
	private static void createAssociation(clsDataStructurePA poRootElement, Instance poAssociation,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {

		System.out.println("Association " + poAssociation.getName());
		clsAssociation oDataStructure = null; 
	    eDataType eAssociationType = getElementDataType(poAssociation); 
	    
	    if(poRootElement == null){
	    	 for(Object oElement : getSlotValues("element", poAssociation) ){
	    		 initDataStructure(null, (Instance)oElement, poDataContainer);
	    	 }
	    	 Instance oElementA = (Instance)getSlotValues("element", poAssociation).toArray()[0]; 
	    	 Instance oElementB = (Instance)getSlotValues("element", poAssociation).toArray()[1];
	    	 clsDataStructurePA oDataStructureA = retrieveDataStructure(getElementDataType((Instance)oElementA), oElementA.getName(), poDataContainer.b);
	    	 clsDataStructurePA oDataStructureB = retrieveDataStructure(getElementDataType((Instance)oElementB), oElementB.getName(), poDataContainer.b);
    		 oDataStructure = getNewAssociation(eAssociationType, poAssociation, oDataStructureA, oDataStructureB);  
	    }
	    else{
	    	 for(Object oElement : getSlotValues("element", poAssociation)){
	    		 String oElementName = ((Instance)oElement).getName();  
	    		 if(!(oElementName.equals(poRootElement.moDataStructureID))){
	    			 initDataStructure(poRootElement, (Instance)oElement, poDataContainer);
	    			 clsDataStructurePA oDataStructureB = retrieveDataStructure(getElementDataType((Instance)oElement), ((Instance)oElement).getName(), poDataContainer.b);
	    			 oDataStructure = getNewAssociation(eAssociationType, poAssociation, poRootElement, oDataStructureB);
	    		 }
	    	 }
	    }
	    
    	//TODO HZ: Define other attributes!!
	    poDataContainer.b.get(eAssociationType).add(oDataStructure);
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
	private static clsAssociation getNewAssociation(eDataType peElementType, Instance poAssociation, clsDataStructurePA poElementA, clsDataStructurePA poElementB) {
		
		String oElementName = poAssociation.getName(); 
		String oElementValueType = peElementType.toString();
		clsPair<clsDataStructurePA, clsDataStructurePA> oAssociationElements; 
		
		switch(peElementType){
			case ASSOCIATIONATTRIBUTE:
				return new clsAssociationAttribute(new clsTripple<String, eDataType, String>(oElementName,peElementType,oElementValueType),
						(clsPrimaryDataStructure)poElementA,(clsPrimaryDataStructure)poElementB); 
			case ASSOCIATIONTEMP:
				return new clsAssociationTime(new clsTripple<String, eDataType, String>(oElementName,peElementType,oElementValueType),
						(clsPrimaryDataStructure)poElementA,(clsPrimaryDataStructure)poElementB); 
			
			case ASSOCIATIONDM:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.DM);
				return new clsAssociationDriveMesh(new clsTripple<String, eDataType, String>(oElementName,peElementType,oElementValueType),
												   (clsDriveMesh)oAssociationElements.a, 
												   (clsPrimaryDataStructure)oAssociationElements.b); 
			
			case ASSOCIATIONWP:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.WP);
				return new clsAssociationWordPresentation(new clsTripple<String, eDataType, String>(oElementName,peElementType,oElementValueType),
						   (clsWordPresentation)oAssociationElements.a, 
						   (clsDataStructurePA)oAssociationElements.b); 
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
		
		if(poDataElementA.moDataStructureType==poDataType) 
				return new clsPair<clsDataStructurePA, clsDataStructurePA>(poDataElementA, poDataElementB);  
		else if (poDataElementB.moDataStructureType==poDataType) 
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
	private static void createACT(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {

		String oElementName = poElement.getName(); 
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		clsAct oDataStructure = new clsAct(new clsTripple<String, eDataType, String>(oElementName,eDataType.ACT,oElementValueType),new ArrayList<clsAssociation>());
    	ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, oDataStructure, poDataContainer);  
				
		for(clsAssociation element : oAssociationList){
			oDataStructure.assignDataStructure(element);
		}
		poDataContainer.b.get(eDataType.ACT).add(oDataStructure); 
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
	private static void createTI(clsDataStructurePA poRootElement, Instance poElement,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {
		
		String oElementName = poElement.getName(); 
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		clsTemplateImage oDataStructure = new clsTemplateImage(new clsTripple<String, eDataType, String>(oElementName,eDataType.TI,oElementValueType),new ArrayList<clsAssociation>());
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, oDataStructure, poDataContainer);  
		
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationTime){oDataStructure.assignDataStructure(element);}
		}
		poDataContainer.b.get(eDataType.TI).add(oDataStructure); 
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
	private static ArrayList<clsAssociation> loadInstanceAssociations(Instance poDataElement, clsDataStructurePA poDataStructure, 
				clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {
		
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", poDataElement);
		ArrayList <clsAssociation> oAssociationList = new ArrayList<clsAssociation>(); 
		clsAssociation oAssociation = null; 
		
		for(Object element : oInstanceAssociations){
				oAssociation = loadAssociation((Instance) element, poDataStructure,poDataContainer); 
				//Below the necessity of the association is defined. Entities are defined by their associations to different 
				//types of data structures. Depending on their definition in the ontology (class_association or instance association)
				//the necessity is defined as true (mandatory) or false (optional). E.g. an entity bubble has always the shape "circle"
				//but can differ in its color => shape is defined as class association while color is defined as instance association. Here,
				//the instance associations are defined => moAssociationImperative is set to false => optional.
				oAssociation.mrImperativeFactor = eDataStructureMatch.OPTIONALMATCH.getMatchFactor(); 
				oAssociationList.add(oAssociation);
		}
		return oAssociationList; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 22:24:53
	 *
	 * @param dataElement
	 * @param dataStructure
	 * @param poFrameKB
	 * @param poDataStructurePA
	 * @return
	 */
	private static ArrayList<clsAssociation> loadClassAssociations(Instance poDataElementA, clsDataStructurePA poDataStructureA,
			clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {

		Collection <?> oClassAssociations = getSlotValues("class_association", poDataElementA);
		ArrayList <clsAssociation> oAssociationList = new ArrayList<clsAssociation>();
		Instance oAssociationElement = null; 
		clsAssociation oAssociation = null; 
	
		for(Object element : oClassAssociations){
				Instance oDataElementB = (Instance)element; 
				String oAssociationName = "CA:" + poDataElementA.getName() + ":"+ ((Instance)oDataElementB).getName();
				String oDataElementTypeA = poDataElementA.getOwnSlotValue(poDataContainer.a.getSlot("type")).toString();
				String oDataElementTypeB = oDataElementB.getOwnSlotValue(poDataContainer.a.getSlot("type")).toString(); 
								
				if(poDataContainer.a.getInstance(oAssociationName)!=null)break; 
				
				if(oDataElementTypeA.equals("TPM") ){
					oAssociationElement = poDataContainer.a.createInstance(oAssociationName, poDataContainer.a.getCls(eDataType.ASSOCIATIONATTRIBUTE.name()));
				}
				else if(oDataElementTypeA.equals("TI")){
					oAssociationElement = poDataContainer.a.createInstance(oAssociationName, poDataContainer.a.getCls(eDataType.ASSOCIATIONTEMP.name()));
				}
				else {throw new NoSuchFieldError("class-association type not verifiable");}
//				else if(oDataElementTypeB.equals("WP")){
//					oAssociationElement = poFrameKB.createInstance(oAssociationName, poFrameKB.getCls("ASSOCIATIONWP"));
					//HZ - be careful - the word presentation association is not added to
					//the root element => it is added as data structure to poDataStructurePA
//				}
				//HZ drive mesh associations are not handled up to now 
				createClassAssociation(oAssociationElement, poDataElementA, oDataElementB, poDataContainer.a);
				oAssociation = loadAssociation(oAssociationElement, poDataStructureA, poDataContainer); 
				//Below the necessity of the association is defined. Entities are defined by their associations to different 
				//types of data structures. Depending on their definition in the ontology (class_association or instance association)
				//the necessity is defined as true (mandatory) or false (optional). E.g. an entity bubble has always the shape "circle"
				//but can differ in its color => shape is defined as class association while color is defined as instance association. Here,
				//the class associations are defined => moAssociationImperative is set to true => mandatory. 
				oAssociation.mrImperativeFactor = eDataStructureMatch.MANDATORYMATCH.getMatchFactor(); 
				oAssociationList.add(oAssociation);
		}
		return oAssociationList;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 26.06.2010, 11:25:25
	 *
	 * @param associationElement
	 * @param poDataElementA
	 * @param poFrameKB 
	 * @param dataElementB
	 */
	private static void createClassAssociation(Instance poAssociationElement,
			Instance poDataElementA, Instance poDataElementB, KnowledgeBase poFrameKB) {
	    poAssociationElement.addOwnSlotValue(poFrameKB.getSlot("element"), poDataElementA);
		poAssociationElement.addOwnSlotValue(poFrameKB.getSlot("element"), poDataElementB);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.06.2010, 06:35:52
	 *
	 * @param association
	 * @param poDataStructure
	 * @param poFrameKB
	 * @param poDataStructurePA
	 * @return
	 */
	private static clsAssociation loadAssociation(Instance poAssociation, clsDataStructurePA poDataStructure, clsPair<KnowledgeBase, Hashtable<eDataType, List<clsDataStructurePA>>> poDataContainer) {

		eDataType eElementType = getElementDataType(poAssociation); 
		initDataStructure(poDataStructure, poAssociation, poDataContainer);
		
		return (clsAssociation)retrieveDataStructure(eElementType, poAssociation.getName(),poDataContainer.b);
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
	private static clsDataStructurePA retrieveDataStructure(eDataType poDataType, 
									String poElementName, Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureTable) {
		if(!poDataStructureTable.containsKey(poDataType)){throw new NoSuchFieldError("datatype " + poDataType + "is not found in the data structure table");}
		for(clsDataStructurePA element : poDataStructureTable.get(poDataType)){
			if(element.moDataStructureID.equals(poElementName)) return element; 
		}
		//TODO HZ: Introduce a better return value => null should not be returned
		return null;
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
		eDataType oReturnValue = null; 
		//TODO HZ: This solution is not very nice as every string of the slot-value "type" is mapped to a enum of eDataType
		//		   by the us of if statements.
		//         A more generic and from my point of view nicer solution would be to write eDataType.valueOf(oElementType)
		//		   in order to get the data type. However therefore it has to be guaranteed that types in the onotlogy
		//		   have the same name es in eDataType. Up to now this cannot be guaranteed but should be changed in the 
		//		   future. 
		//FIXME HZ: This has to be definitely changed to a more generic form - see above
//HZ 03.08.2010 - old version 		
// 		if(oElementType.equals("thing_presentation")) return eDataType.TP; 
// 		else if(oElementType.equals("thing_presentation_mesh")) return eDataType.TPM;
// 		else if(oElementType.equals("word_presentation")) return eDataType.WP;
// 		else if(oElementType.equals("association_attributes")) return eDataType.ASSOCIATIONATTRIBUTE;
// 		else if(oElementType.equals("association_drive")) return eDataType.ASSOCIATIONDM;
// 		else if(oElementType.equals("association_temporal")) return eDataType.ASSOCIATIONTEMP;
// 		else if(oElementType.equals("association_wp")) return eDataType.ASSOCIATIONWP;
// 		else if(oElementType.equals("drive_mesh")) return eDataType.DM;
// 		else if(oElementType.equals("template_image")) return eDataType.TI;
// 		else if(oElementType.equals("act")) return eDataType.ACT;
		
		//HZ 03.08.2010 - new version - more generic one
		try{
			oReturnValue = eDataType.valueOf(oElementType);
		}catch (IllegalArgumentException e){System.out.println(" Element of type " + oElementType + " not handled");}
		
		return oReturnValue; 
 	}
	
	private static Object getSlotValue(String poSlotName, Instance poElement){
		for(Slot oOwnSlot: poElement.getOwnSlots()){
			if(oOwnSlot.getName().equals(poSlotName)){
				return poElement.getOwnSlotValue(oOwnSlot);
			}
		}
		
		throw new NoSuchFieldError(" there is no slot with the name " + poSlotName + " found "); 
	}
	
	private static Collection <?> getSlotValues(String poSlotName, Instance poElement){
		for(Slot oOwnSlot: poElement.getOwnSlots()){
			if(oOwnSlot.getName().equals(poSlotName)){
				return poElement.getOwnSlotValues(oOwnSlot);
			}
		}
		
		throw new NoSuchFieldError(" there is no slot with the name " + poSlotName + " found ");
	}
}

