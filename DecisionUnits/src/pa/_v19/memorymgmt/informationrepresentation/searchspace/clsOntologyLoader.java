/**
 * clsSearchSpaceMainMemory.java: DecisionUnits - pa.informationrepresentation.ARSi10.searchspace
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 */
package pa._v19.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.model.Slot;

import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.memorymgmt.datatypes.clsAffect;
import pa._v19.memorymgmt.datatypes.clsAssociation;
import pa._v19.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsAssociationTime;
import pa._v19.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v19.memorymgmt.datatypes.clsDataStructurePA;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v19.memorymgmt.datatypes.clsTemplateImage;
import pa._v19.memorymgmt.datatypes.clsThingPresentation;
import pa._v19.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;
import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.memorymgmt.informationrepresentation.enums.eDataStructureMatch;
import pa._v19.tools.clsPair;
import pa._v19.tools.clsTripple;

/**
 *
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 * 
 */
public class clsOntologyLoader {
	static int mrMaxStackDepth = 1000;  
	static int DS_ID = 0; 
	
	public static void loadOntology(HashMap<String, clsDataStructurePA> poDataStructureTable, String poSourceName){
		// HZ This if-statement is defined for testing reasons as the Search Space should be set for the 
		// JUnit tests (e.g. tssInformationRepresentationManagementARSi10) in order to be independent from changes in the ontology. Of cause an unchangeable test-ontology 
		// is a possiblity to introduce a permanent solution.
		 
		if(poSourceName != ""){
			initOntology(poDataStructureTable, poSourceName); 
		}
		else{
			System.out.println("The search space will be set manually"); 
		}
	}
	
	private static void initOntology(HashMap<String, clsDataStructurePA> poDataStructureList, String poSourceName){
		// HZ: Sorry for the "Object" parameter in ArrayList => however, this is a protege problem
		Collection <?>oErrorList = new ArrayList<Object>(); 
		Project oOntologyPrj = Project.loadProjectFromFile("../" + poSourceName, oErrorList);
	    KnowledgeBase oFrameKB = oOntologyPrj.getKnowledgeBase();
	  
	    // HZ: Optimize the initialization process => Builder
		for(eDataType oDataType : initValues())	{
			for(Instance oDataElement : oFrameKB.getCls(oDataType.name()).getInstances()){
				initDataStructure(null, oDataElement, new clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>>(oFrameKB, poDataStructureList)); 
			}
		}
	}
	
	public static eDataType[] initValues(){
		eDataType [] oRetVal = {eDataType.ASSOCIATIONWP, 
								eDataType.ASSOCIATIONDM, 
								eDataType.ACT,
								eDataType.AFFECT, 
								eDataType.DM,
								eDataType.TI,
								eDataType.TP,
								eDataType.TPM,
								eDataType.WP};
		return oRetVal;
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 26.06.2010, 20:33:15
	 *
	 * @param elementTypeB
	 * @param elementBName
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void initDataStructure(Instance poRootElement, Instance poElement,
								clsPair<KnowledgeBase, HashMap<String, clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementDataType = getElementDataType(poElement); 
		
		if(retrieveDataStructure(poElement.getName(), poDataContainer.b)== null){
			 createDataStructure(oElementDataType, poElement, poRootElement, poDataContainer);
		}
	}
	
	/**
	 * This method loads the different data structures from the ontology and maps it to objects of the
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
	private static void createDataStructure(eDataType poDataType, Instance poElement, Instance poRootElement,
							clsPair<KnowledgeBase, HashMap<String, clsDataStructurePA>> poDataContainer){
		switch(poDataType){
			case ACT: 	createACT(poRootElement, poElement, poDataContainer); 					break; 
			case AFFECT:  		createAFFECT(poRootElement, poElement, poDataContainer); 		break; 
			case ASSOCIATIONDM:	createAssociation(poRootElement, poElement, poDataContainer); 	break; 
			case ASSOCIATIONWP:  createAssociation(poRootElement, poElement, poDataContainer); 			break; 
			case ASSOCIATIONTEMP:  createAssociation(poRootElement, poElement, poDataContainer); 		break;
			case ASSOCIATIONATTRIBUTE:  createAssociation(poRootElement, poElement, poDataContainer); 	break; 
			case DM:	 createDM(poRootElement, poElement, poDataContainer);	break; 
			case TI:	 createTI(poRootElement, poElement, poDataContainer);	break; 
			case TPM:	 createTPM(poRootElement, poElement, poDataContainer);	break; 
			case TP:	 createTP(poRootElement, poElement, poDataContainer);	break; 
			case WP:	 createWP(poRootElement, poElement, poDataContainer);	break; 
				
			default: throw new NoSuchFieldError(" datatype " + poDataType +" is not handled"); 
		}
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 24.06.2010, 15:43:06
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createWP(Instance poRootElement, Instance poElement,
		clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.WP; 
		int oID = DS_ID++;  
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
								
		clsWordPresentation oDataStructure = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(oID ,oElementType,oElementValueType),oElementValue);
		//HZ Word Presentation does not obey of any associations
		// HZ: Define other attributes!! 
		poDataContainer.b.put(poElement.getName(), oDataStructure);
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 15.08.2010, 14:38:23
	 *
	 * @param poRootElement
	 * @param poElement
	 * @param poDataContainer
	 */
	private static void createAFFECT(
			Instance poRootElement,
			Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.AFFECT; 
		int oID = DS_ID++;   
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		float oElementValue = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		float oElementMinVal = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_min"));
		float oElementMaxVal = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_max"));
	
		clsAffect oDataStructure = new clsAffect(new clsTripple<Integer, eDataType, String>(oID,oElementType,oElementValueType),oElementValue);
		//HZ AFFECT does not obey of any associations
				 		
		oDataStructure.setMinVal(oElementMinVal);
		oDataStructure.setMaxVal(oElementMaxVal);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
	}
	/**
	 *
	 *
	 * @author zeilinger
	 * 23.06.2010, 21:24:42
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createDM(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		eDataType oElementType = eDataType.DM;
		int oID = DS_ID++;   
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		float rPleasure = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("pleasure")));
		float rDriveCathegoryAnal = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:anal")));
		float rDriveCathegoryOral = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:oral"));
		float rDriveCathegoryGenital = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:genital"));
		float rDriveCathegoryPhalic = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("cathegory:phalic"));
		double [] oDriveCathegory = {rDriveCathegoryAnal, rDriveCathegoryOral, rDriveCathegoryGenital, rDriveCathegoryPhalic}; 
				
		clsDriveMesh oDataStructure = new clsDriveMesh(new clsTripple<Integer, eDataType, String>(oID,oElementType,oElementValueType),
																								rPleasure,
																								oDriveCathegory, 
																								new ArrayList<clsAssociation>(), 
																								oElementValue);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
		
		ArrayList <clsAssociation> oAssociationList = loadClassAssociations(poElement, oDataStructure, poDataContainer); 
				
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationAttribute){oDataStructure.assignDataStructure(element);}
		}
		// HZ: Define other attributes!! 
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:12:04
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createTP(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.TP;
		int oID = DS_ID++;   
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		Object oElementValue = poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
	
		clsThingPresentation oDataStructure = new clsThingPresentation(new clsTripple<Integer, eDataType, String>(oID,oElementType,oElementValueType),oElementValue);
		loadInstanceAssociations(poElement, poDataContainer);
		//HZ TP does not obey of any associations 		
		// HZ: Define other attributes!! 
		poDataContainer.b.put(poElement.getName(), oDataStructure);
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:12:01
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createTPM(Instance poRootElement, Instance poElement,
						clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.TPM;
		int oID = DS_ID++; 
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		
		clsThingPresentationMesh oDataStructure = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(oID,oElementType,oElementValueType),
																														 new ArrayList<clsAssociation>(),
																														 oElementValue);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
		
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, poDataContainer); 
		oAssociationList.addAll(loadClassAssociations(poElement, oDataStructure, poDataContainer));
		
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationAttribute){ oDataStructure.assignDataStructure(element);}
		}
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:11:56
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createACT(Instance poRootElement, Instance poElement,
							clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		eDataType oElType = eDataType.ACT;
		int oID = DS_ID++;  
		String oElValType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		String oElVal = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		Collection <?> oPreCon = getSlotValues("precondition", poElement);
		Collection <?> oAction = getSlotValues("action", poElement);
		Collection <?> oConseq = getSlotValues("consequence", poElement);
		clsWordPresentation oDS = null;
		clsAct oAct = new clsAct(new clsTripple<Integer, eDataType, String>(oID,oElType,oElValType),
																					  new ArrayList<clsSecondaryDataStructure>(), 
																					  oElVal);
		
		poDataContainer.b.put(poElement.getName(), oAct);
		
		oAct.setMoContent(oAct.getMoContent() + "|PRECONDITION|") ; 
		
		for(Object oElement : oPreCon){
			initDataStructure(null, (Instance)oElement, poDataContainer);
			oDS = (clsWordPresentation)retrieveDataStructure(((Instance)oElement).getName(), poDataContainer.b); 
			oAct.getMoAssociatedContent().add(oDS);
			oAct.setMoContent(oAct.getMoContent() + oDS.getMoContentType() + ":" + oDS.getMoContent() + "|");
		}
		
		oAct.setMoContent(oAct.getMoContent() + "|ACTION|"); 
		
		for(Object oElement : oAction){
			initDataStructure(null, (Instance)oElement, poDataContainer);
			oDS = (clsWordPresentation)retrieveDataStructure(((Instance)oElement).getName(), poDataContainer.b); 
			oAct.getMoAssociatedContent().add(oDS);
			oAct.setMoContent(oAct.getMoContent() + oDS.getMoContentType() + ":" + oDS.getMoContent() + "|");
		}
		
		oAct.setMoContent(oAct.getMoContent() + "|CONSEQUENCE|"); 
		
		for(Object oElement : oConseq){
			initDataStructure(null, (Instance)oElement, poDataContainer);
			oDS = (clsWordPresentation)retrieveDataStructure(((Instance)oElement).getName(), poDataContainer.b); 
			oAct.getMoAssociatedContent().add(oDS);
			oAct.setMoContent(oAct.getMoContent() + oDS.getMoContentType() + ":" + oDS.getMoContent() + "|"); 
		}
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 21.06.2010, 17:02:33
	 *
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createTI(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.TI;
		int oID = DS_ID++;   
		String oElementValueType = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type"));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		
		clsTemplateImage oDataStructure = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(oID,oElementType,oElementValueType),
															   new ArrayList<clsAssociation>(), 
															   oElementValue);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
		
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, poDataContainer); 
		oAssociationList.addAll(loadClassAssociations(poElement, oDataStructure, poDataContainer));
		
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationTime){oDataStructure.assignDataStructure(element);}
		}
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:11:58
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createAssociation(Instance poRootElement, Instance poAssociation,
									clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		//HZ Checks if the tree depth does not exceed a certain limit => avoids
		// endless loops
		checkStackDepth(); 
		
		clsAssociation oDataStructure = null;
		String oAssName = poAssociation.getName(); 
	    eDataType eAssociationType = getElementDataType(poAssociation); 
	    
	    if(poRootElement == null){
	    	 Instance oIns_a = (Instance)getSlotValues("element", poAssociation).toArray()[0]; 
	    	 Instance oIns_b = (Instance)getSlotValues("element", poAssociation).toArray()[1]; 
	    	 
	    	 initDataStructure(null, oIns_a, poDataContainer);
	    	 initDataStructure(null, oIns_b, poDataContainer);
	    	 clsDataStructurePA oDS_a = retrieveDataStructure(oIns_a.getName(), poDataContainer.b);
	    	 clsDataStructurePA oDS_b = retrieveDataStructure(oIns_b.getName(), poDataContainer.b);
	    	 oDataStructure = getNewAssociation(eAssociationType, poAssociation, oDS_a, oDS_b);  
	    }
	    else{
	    	 for(Object oElement : getSlotValues("element", poAssociation)){
	    		 String oRootName = poRootElement.getName(); 
	    		 String oElementName = ((Instance) oElement).getName(); 
	    		 
	    		  if(!(oElementName.equals(oRootName))){
	    			 initDataStructure(poRootElement, (Instance)oElement, poDataContainer);
	    			 clsDataStructurePA oDS_a = retrieveDataStructure(oRootName, poDataContainer.b);
	    			 clsDataStructurePA oDS_b = retrieveDataStructure(oElementName, poDataContainer.b);
	    			 oDataStructure = getNewAssociation(eAssociationType, poAssociation, oDS_a, oDS_b);
	    		 }
	    	 }
	    }
	    
    	// HZ: Define other attributes!!
	    poDataContainer.b.put(oAssName, oDataStructure);
	}
	

	private static void checkStackDepth() {
		/*long depth = Thread.currentThread().getStackTrace().length;
		
		if (depth > mrMaxStackDepth) {
			throw new StackOverflowError("max. call stack for clsAssociation<TYPE>.clone reached. either increase threshold constant or check code for an infinite clone loop. ("+depth+">"+mrMaxStackDepth+")");
		}*/
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 23.06.2010, 08:02:09
	 *
	 * @param elementType
	 * @param poDataElements
	 * @return
	 */
	private static clsAssociation getNewAssociation(eDataType peElementType, Instance poAssociation, clsDataStructurePA poElementA, clsDataStructurePA poElementB) {
		
		int oID = DS_ID++;  
		String oElementValueType = peElementType.toString();
		clsPair<clsDataStructurePA, clsDataStructurePA> oAssociationElements = null; 
		
		switch(peElementType){
			case ASSOCIATIONATTRIBUTE:
				return new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(oID,peElementType,oElementValueType),
						(clsPrimaryDataStructure)poElementA,(clsPrimaryDataStructure)poElementB); 
			case ASSOCIATIONTEMP:
				return new clsAssociationTime(new clsTripple<Integer, eDataType, String>(oID,peElementType,oElementValueType),
						(clsPrimaryDataStructure)poElementA,(clsPrimaryDataStructure)poElementB); 
			
			case ASSOCIATIONDM:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.DM);
				return new clsAssociationDriveMesh(new clsTripple<Integer, eDataType, String>(oID,peElementType,oElementValueType),
												   (clsDriveMesh)oAssociationElements.a, 
												   (clsPrimaryDataStructure)oAssociationElements.b); 
			
			case ASSOCIATIONWP:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.WP);
				return new clsAssociationWordPresentation(new clsTripple<Integer, eDataType, String>(oID,peElementType,oElementValueType),
						   (clsWordPresentation)oAssociationElements.a, 
						   (clsDataStructurePA)oAssociationElements.b); 
		}
		throw new NoSuchFieldError(" association of unknown type: " + peElementType.toString());
	}

	/**
	 *
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
		
		if(poDataElementA.getMoDataStructureType() == poDataType){ 
				return new clsPair<clsDataStructurePA, clsDataStructurePA>(poDataElementA, poDataElementB);
		}
		else if (poDataElementB.getMoDataStructureType() == poDataType) {
				return new clsPair<clsDataStructurePA, clsDataStructurePA>(poDataElementB, poDataElementA);
		}
		else {
			throw new NoSuchFieldError("The required datatype "+ poDataType +" does not match the data structures"); 
		}
	}

	/**
	 *
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
	private static ArrayList<clsAssociation> loadInstanceAssociations(Instance poDataElement,  
												clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", poDataElement);
		ArrayList <clsAssociation> oAssociationList = new ArrayList<clsAssociation>(); 
		clsAssociation oAssociation = null; 
		
		for(Object element : oInstanceAssociations){
				oAssociation = loadAssociation(poDataElement, (Instance) element, poDataContainer); 
				//Below the necessity of the association is defined. Entities are defined by their associations to different 
				//types of data structures. Depending on their definition in the ontology (class_association or instance association)
				//the necessity is defined as true (mandatory) or false (optional). E.g. an entity bubble has always the shape "circle"
				//but can differ in its color => shape is defined as class association while color is defined as instance association. Here,
				//the instance associations are defined => moAssociationImperative is set to false => optional.
				oAssociation.setMrImperativeFactor(eDataStructureMatch.OPTIONALMATCH.getMatchFactor()); 
				oAssociationList.add(oAssociation);
		}
		return oAssociationList; 
	}
	
	/**
	 *
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
							clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		Collection <?> oClassAssociations = getSlotValues("class_association", poDataElementA);
		ArrayList <clsAssociation> oAssociationList = new ArrayList<clsAssociation>();
		Instance oAssociationElement = null; 
		clsAssociation oAssociation = null; 
	
		for(Object element : oClassAssociations){
				Instance oDataElementB = (Instance)element; 
				String oAssociationName = "CA:" + poDataElementA.getName() + ":"+ ((Instance)oDataElementB).getName();
				String oDataElementTypeA = poDataElementA.getOwnSlotValue(poDataContainer.a.getSlot("type")).toString();
				//never used String oDataElementTypeB = oDataElementB.getOwnSlotValue(poDataContainer.a.getSlot("type")).toString(); 
								
				if(poDataContainer.a.getInstance(oAssociationName)!=null)break; 
				
				if(oDataElementTypeA.equals("TPM") ){
					oAssociationElement = poDataContainer.a.createInstance(oAssociationName, poDataContainer.a.getCls(eDataType.ASSOCIATIONATTRIBUTE.name()));
				}
				else if(oDataElementTypeA.equals("TI")){
					oAssociationElement = poDataContainer.a.createInstance(oAssociationName, poDataContainer.a.getCls(eDataType.ASSOCIATIONTEMP.name()));
				}
				else if(oDataElementTypeA.equals("DM")){
					oAssociationElement = poDataContainer.a.createInstance(oAssociationName, poDataContainer.a.getCls(eDataType.ASSOCIATIONATTRIBUTE.name()));
				}
				else {throw new NoSuchFieldError("class-association type not verifiable");}
//				else if(oDataElementTypeB.equals("WP")){
//					oAssociationElement = poFrameKB.createInstance(oAssociationName, poFrameKB.getCls("ASSOCIATIONWP"));
					//HZ - be careful - the word presentation association is not added to
					//the root element => it is added as data structure to poDataStructurePA
//				}
				//HZ drive mesh associations are not handled up to now 
				createClassAssociation(oAssociationElement, poDataElementA, oDataElementB, poDataContainer.a);
				oAssociation = loadAssociation(null, oAssociationElement, poDataContainer); 
				//Below the necessity of the association is defined. Entities are defined by their associations to different 
				//types of data structures. Depending on their definition in the ontology (class_association or instance association)
				//the necessity is defined as true (mandatory) or false (optional). E.g. an entity bubble has always the shape "circle"
				//but can differ in its color => shape is defined as class association while color is defined as instance association. Here,
				//the class associations are defined => moAssociationImperative is set to true => mandatory. 
				oAssociation.setMrImperativeFactor(eDataStructureMatch.MANDATORYMATCH.getMatchFactor()); 
				oAssociationList.add(oAssociation);
		}
		return oAssociationList;
	}

	/**
	 *
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
	 *
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
	private static clsAssociation loadAssociation(Instance poRootElement, Instance poAssociation, 
												clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
 
		initDataStructure(poRootElement, poAssociation, poDataContainer);
		return (clsAssociation)retrieveDataStructure(poAssociation.getName(),poDataContainer.b);
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 22.06.2010, 15:46:17
	 * @param poDataStructurePA 
	 * @param string 
	 * @param dataType 
	 *
	 * @return
	 */
	private static clsDataStructurePA retrieveDataStructure(String poElNa, HashMap<String,clsDataStructurePA> poDataStructureTable) {
			return poDataStructureTable.get(poElNa); 
	}

	/**
	 *
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

