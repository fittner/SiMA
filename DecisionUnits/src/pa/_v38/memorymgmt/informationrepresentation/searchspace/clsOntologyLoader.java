/**
 * clsSearchSpaceMainMemory.java: DecisionUnits - pa.informationrepresentation.ARSi10.searchspace
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 */
package pa._v38.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.model.Slot;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationEmotion;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.informationrepresentation.enums.eDataStructureMatch;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 08:21:13
 * 
 */
public class clsOntologyLoader {
	static int mrMaxStackDepth = 1000;  
	static int DS_ID = 0; 
	
	public static void loadOntology(HashMap<String, clsDataStructurePA> poDataStructureTable, String poSourceName){
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
	
	private static void initOntology(HashMap<String, clsDataStructurePA> poDataStructureList, String poSourceName){
		//FIXME HZ: Sorry for the "Object" parameter in ArrayList => however, this is a protege problem
		Collection <?>oErrorList = new ArrayList<Object>(); 
		Project oOntologyPrj = Project.loadProjectFromFile("../" + poSourceName, oErrorList);
		System.out.println("Reading ontology: "+"../" + poSourceName);
		
	    KnowledgeBase oFrameKB = oOntologyPrj.getKnowledgeBase();
	  
	    //FIXME HZ: Optimize the initialization process => Builder
		for(eDataType oDataType : initValues())	{
			for(Instance oDataElement : oFrameKB.getCls(oDataType.name()).getInstances()){
				initDataStructure(null, oDataElement, new clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>>(oFrameKB, poDataStructureList)); 
			}
		}
		
		
	}
	
	public static eDataType[] initValues(){
		//As AssociationDM is independent from most other structures, they have to be initialized first, else the prinstance will not work correctly
		eDataType [] oRetVal = {eDataType.ASSOCIATIONDM,	
								eDataType.ASSOCIATIONWP, 
								eDataType.ASSOCIATIONATTRIBUTE,
								eDataType.ASSOCIATIONPRI,
								eDataType.ASSOCIATIONSEC,
								eDataType.ASSOCIATIONEMOTION,
								eDataType.AFFECT, 
								eDataType.DM,
								eDataType.TP,
								eDataType.TPM,
								eDataType.WP,
								eDataType.WPM,
								eDataType.EMOTION,
								eDataType.ASSOCIATIONTEMP};
		
		/*eDataType [] oRetVal = {eDataType.ASSOCIATIONWP, 
				eDataType.ASSOCIATIONDM, 
				eDataType.ASSOCIATIONATTRIBUTE,
				eDataType.ASSOCIATIONPRI,
				eDataType.ASSOCIATIONSEC,
				eDataType.ACT,
				eDataType.AFFECT, 
				eDataType.DM,
				eDataType.TI,
				eDataType.TP,
				eDataType.TPM,
				eDataType.WP};*/
		
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
	private static void initDataStructure(Instance poRootElement, Instance poElement,
								clsPair<KnowledgeBase, HashMap<String, clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementDataType = getElementDataType(poElement); 
		
		if(retrieveDataStructure(poElement.getName(), poDataContainer.b)== null){
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
	private static void createDataStructure(eDataType poDataType, Instance poElement, Instance poRootElement,
							clsPair<KnowledgeBase, HashMap<String, clsDataStructurePA>> poDataContainer){
		switch(poDataType){
			case ACT: 	createACT(poRootElement, poElement, poDataContainer); 					break; 
			case AFFECT:  		createAFFECT(poRootElement, poElement, poDataContainer); 		break; 
			case ASSOCIATIONDM:	createAssociation(poRootElement, poElement, poDataContainer); 	break; 
			case ASSOCIATIONEMOTION:	createAssociation(poRootElement, poElement, poDataContainer); 	break; 
			case ASSOCIATIONWP:  createAssociation(poRootElement, poElement, poDataContainer); 			break; 
			case ASSOCIATIONTEMP:  createAssociation(poRootElement, poElement, poDataContainer); 		break;
			case ASSOCIATIONATTRIBUTE:  createAssociation(poRootElement, poElement, poDataContainer); 	break;
			case ASSOCIATIONPRI:	createAssociation(poRootElement, poElement, poDataContainer); 	break;
			case ASSOCIATIONSEC:	createAssociationSec(poRootElement, poElement, poDataContainer); 	break;
			case DM:	 createDM(poRootElement, poElement, poDataContainer);	break; 
			case TI:	 createTI(poRootElement, poElement, poDataContainer);	break; 
			case TPM:	 createTPM(poRootElement, poElement, poDataContainer);	break; 
			case TP:	 createTP(poRootElement, poElement, poDataContainer);	break; 
			case WP:	 createWP(poRootElement, poElement, poDataContainer);	break; 
			case WPM: 	 createWPM(poRootElement, poElement, poDataContainer);	break;
			case EMOTION:createEmotion(poRootElement, poElement, poDataContainer);	break;
			
			//Special case in order to be able to create images
			//FIXME AW: This is not a real datatype and should not be here.
			case PRIINSTANCE: createPRIINSTANCE(poRootElement, poElement, poDataContainer); break;
				
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
	private static void createWP(Instance poRootElement, Instance poElement,
		clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.WP; 
		int oID = DS_ID++;  
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
								
		clsWordPresentation oDataStructure = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>(oID ,oElementType,oElementValueType),oElementValue);
		//HZ Word Presentation does not obey of any associations
		//TODO HZ: Define other attributes!! 
		poDataContainer.b.put(poElement.getName(), oDataStructure);
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @author wendt
	 *
	 * @param poRootElement
	 * @param poElement
	 * @param poDataContainer
	 */
	private static void createWPM(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
			
			eDataType oElementType = eDataType.WPM; 
			int oID = DS_ID++;  
			eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
			String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
									
			clsWordPresentationMesh oDataStructure = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(oID ,oElementType,oElementValueType), new ArrayList<clsAssociation>(), oElementValue);
			//The presentation does obey associations
			//TODO AW: define how to add associations. At the moment it is not necessary to add intrinsic values 
			poDataContainer.b.put(poElement.getName(), oDataStructure);
		}

	/**
	 * DOCUMENT (zeilinger) - insert description
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
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		float oElementValue = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		float oElementMinVal = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_min"));
		float oElementMaxVal = (Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_max"));
	
		clsAffect oDataStructure = new clsAffect(new clsTriple<Integer, eDataType, eContentType>(oID,oElementType,oElementValueType),oElementValue);
		//HZ AFFECT does not obey of any associations
				 		
		oDataStructure.setMinVal(oElementMinVal);
		oDataStructure.setMaxVal(oElementMaxVal);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
	}
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 13.07.2012, 21:24:42
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createDM(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		eDataType oElementType = eDataType.DM;
		int oID = DS_ID++;   
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		float rQuotaOfAffect = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("quotaOfAffect")));
		eDriveComponent oDriveComponent = eDriveComponent.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("drive_component")));
		ePartialDrive oPartialDrive = ePartialDrive.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("partial_drive")));
		
		clsDriveMesh oDataStructure = new clsDriveMesh(new clsTriple<Integer, eDataType, eContentType>(oID,oElementType,oElementValueType),
																								new ArrayList <clsAssociation>(), rQuotaOfAffect,
																								oElementValue, oDriveComponent, oPartialDrive);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
		
		ArrayList <clsAssociation> oAssociationList = loadClassAssociations(poElement, oDataStructure, poDataContainer); 
				
		oAssociationList.addAll(loadInstanceAssociations(poElement, poDataContainer));
		
		oDataStructure.addInternalAssociations(oAssociationList);
		

		 
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 3.07.2012, 21:24:42
	 *
	 * @param poDataElements
	 * @param poFrameKB
	 * @param poDataStructurePA
	 */
	private static void createEmotion(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		eDataType oElementType = eDataType.EMOTION;
		int oID = DS_ID++;   
		// TODO ...
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		float rEmotionIntensity = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("emotionIntensity"))); 
		float rSourcePleasure = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("sourcePleasure")));
		float rSourceUnpleasure = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("sourceUnpleasure")));
		float rSourceLibid = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("sourceLibid")));
		float rSourceAggr = ((Float)poElement.getOwnSlotValue(poDataContainer.a.getSlot("sourceAggr")));
				
		eEmotionType oEmotionType = eEmotionType.getEmotionType(oElementValue);
		
		clsEmotion oDataStructure = new clsEmotion(new clsTriple<Integer, eDataType, eContentType>(oID,oElementType,oElementValueType),
																								rEmotionIntensity,
																								oEmotionType, rSourcePleasure,  rSourceUnpleasure,  rSourceLibid,  rSourceAggr);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
		
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, poDataContainer);
		
		for(clsAssociation element : oAssociationList){
			if(element instanceof clsAssociationEmotion) { 
				oDataStructure.assignDataStructure(element);
			}
		}
		
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
	private static void createTP(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.TP;
		int oID = DS_ID++;   
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		Object oElementValue = poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
	
		clsThingPresentation oDataStructure = new clsThingPresentation(new clsTriple<Integer, eDataType, eContentType>(oID,oElementType,oElementValueType),oElementValue);
		loadInstanceAssociations(poElement, poDataContainer);
		//HZ TP does not obey of any associations 		
		//TODO HZ: Define other attributes!! 
		poDataContainer.b.put(poElement.getName(), oDataStructure);
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
	private static void createTPM(Instance poRootElement, Instance poElement,
						clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		eDataType oElementType = eDataType.TPM;
		int oID = DS_ID++; 
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		
		clsThingPresentationMesh oDataStructure = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(oID,oElementType,oElementValueType),
																														 new ArrayList<clsAssociation>(),
																														 oElementValue);
		poDataContainer.b.put(poElement.getName(), oDataStructure);
		
		ArrayList <clsAssociation> oAssociationList = loadInstanceAssociations(poElement, poDataContainer); 
		oAssociationList.addAll(loadClassAssociations(poElement, oDataStructure, poDataContainer));
		
		for(clsAssociation element : oAssociationList){
			//Go through all associations of that structure. If the association is an association attribute, then add it to the internal associations
			//Added by AW: If the association is of type associationTemp, then also add it to the internal associations. If associationTemp are used, then
			//it means that it is an image.
			if(element instanceof clsAssociationAttribute || element instanceof clsAssociationTime) { 
				oDataStructure.assignDataStructure(element);
			}
		}
	}
	
	private static void createPRIINSTANCE(Instance poRootElement, Instance poElement,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		//Get the instance of the container structure
		Instance oInstanceOfType = (Instance)poElement.getOwnSlotValue(poDataContainer.a.getSlot("instance_type"));
		//If the data structure does not exist, create it
		initDataStructure(null, oInstanceOfType, poDataContainer);
		//Get the element of the data structure
		clsDataStructurePA oDS = (clsDataStructurePA)retrieveDataStructure(oInstanceOfType.getName(),poDataContainer.b);
		
		//make a deepcopy of the data structure in order to get new association ids
		
		clsDataStructurePA oNewInstanceDS = null;
		if (oDS != null) {
			if (oDS instanceof clsThingPresentationMesh) {
				//TODO AW: Only TPM is covered here
				clsThingPresentationMesh oNewInstanceTPMDS = null;
				try {
					oNewInstanceTPMDS = (clsThingPresentationMesh) ((clsThingPresentationMesh) oDS).clone();
				} catch (CloneNotSupportedException e) {
					System.out.print("Error in clsOntologyLoader.java in createPRIINSTANCE: oDS could not be cloned");
					e.printStackTrace();
				}
				//IMPORTANT NOTE: InstanceIDs should only be set outside of the memory management, but as association to drives has
				//to be instanciated, instanceIDs are set here. It is only set for this data structure
				//Get new instanceID
				int nInstanceID = oNewInstanceTPMDS.hashCode();
				oNewInstanceTPMDS.setMoDSInstance_ID(nInstanceID);
				//Make this clsDataStructurePA to a TPM
				oNewInstanceDS = oNewInstanceTPMDS;
				
				poDataContainer.b.put(poElement.getName(), oNewInstanceDS);	//Use the containername as identifier
				
				//TODO: IMPORTANT NOTE TO DOCUMENTv: In associationAttribute, the rootelement must be the first element
				
				//As drive meshes are no intrinsic data structures and are bound to the type, they have to be cloned as well 
				//and the cloned associations have to be assigned the new PRIInstances
				
				//Get associated drive meshes
				ArrayList<clsAssociationDriveMesh> oDMAssList = new ArrayList<clsAssociationDriveMesh>();
				Collection<clsDataStructurePA> oValueList = poDataContainer.b.values();	//Get all values from the hashtable
				//FIXME AW: To get all values from the hashtable is no very nice solution, in order to extract the fitting associations for an element. 
				//A memory-PhD should make this part more efficient
				
				for (clsDataStructurePA oStructure : oValueList) {
					if ((oStructure instanceof clsAssociationDriveMesh)) {
						clsAssociationDriveMesh oOriginalAssDM = (clsAssociationDriveMesh) oStructure;
						//If the rootelement of a DM-Ass is the original data structure (e. g. CAKE)
						if (oOriginalAssDM.getRootElement() == oDS) {
							//For each found AssociationDriveMesh for that structure, create a clone and change the root element
							try {
								clsAssociationDriveMesh oNewAssDM = (clsAssociationDriveMesh)oOriginalAssDM.clone();
								//In this case, the Root element is B. Therefore, set be
								oNewAssDM.setMoAssociationElementB(oNewInstanceDS);
								oDMAssList.add(oNewAssDM);
							} catch (CloneNotSupportedException e) {
								System.out.print("Error in clsOntologyLoader.java in createPRIINSTANCE: oNewAssDM could not be cloned");
								e.printStackTrace();
							}
						}
					}
				}
				
				int i=0;
				for (clsAssociationDriveMesh oAssDM : oDMAssList) {
					//It would be better to create a real name as hash key.
					String oName = poElement.getName() + ":DM:NO" + i;
					poDataContainer.b.put(oName, oAssDM);	//Add the new association
					i++;
				}
			}
		}
		
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
	private static void createACT(Instance poRootElement, Instance poElement,
							clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		eDataType oElType = eDataType.ACT;
		int oID = DS_ID++;  
		eContentType oElValType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		String oElVal = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		Collection <?> oPreCon = getSlotValues("precondition", poElement);
		Collection <?> oAction = getSlotValues("action", poElement);
		Collection <?> oConseq = getSlotValues("consequence", poElement);
		clsWordPresentation oDS = null;
		clsAct oAct = new clsAct(new clsTriple<Integer, eDataType, eContentType>(oID,oElType,oElValType),
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
	 * DOCUMENT (zeilinger) - insert description
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
		eContentType oElementValueType = eContentType.valueOf((String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value_type")));
		String oElementValue = (String)poElement.getOwnSlotValue(poDataContainer.a.getSlot("value"));
		
		clsTemplateImage oDataStructure = new clsTemplateImage(new clsTriple<Integer, eDataType, eContentType>(oID,oElementType,oElementValueType),
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
	 * DOCUMENT (zeilinger) - insert description
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

		eContentType eAssContentType = getElementContentType(poAssociation); 
	    
	    if(poRootElement == null){
	    	 Instance oIns_a = (Instance)getSlotValues("element", poAssociation).toArray()[0]; 
	    	 Instance oIns_b = (Instance)getSlotValues("element", poAssociation).toArray()[1]; 
	    	 
	    	 initDataStructure(null, oIns_a, poDataContainer);
	    	 initDataStructure(null, oIns_b, poDataContainer);
	    	 clsDataStructurePA oDS_a = retrieveDataStructure(oIns_a.getName(), poDataContainer.b);
	    	 clsDataStructurePA oDS_b = retrieveDataStructure(oIns_b.getName(), poDataContainer.b);
	    	 oDataStructure = getNewAssociation(eAssContentType, eAssociationType, poAssociation, oDS_a, oDS_b);  
	    }
	    else{
	    	 for(Object oElement : getSlotValues("element", poAssociation)){
	    		 String oRootName = poRootElement.getName(); 
	    		 String oElementName = ((Instance) oElement).getName(); 
	    		 
	    		  if(!(oElementName.equals(oRootName))){
	    			 initDataStructure(poRootElement, (Instance)oElement, poDataContainer);
	    			 clsDataStructurePA oDS_a = retrieveDataStructure(oRootName, poDataContainer.b);
	    			 clsDataStructurePA oDS_b = retrieveDataStructure(oElementName, poDataContainer.b);
	    			 oDataStructure = getNewAssociation(eAssContentType, eAssociationType, poAssociation, oDS_a, oDS_b);
	    		 }
	    	 }
	    }
	    
    	//TODO HZ: Define other attributes!!
	    poDataContainer.b.put(oAssName, oDataStructure);
	}
	
	/**
	 * Create the AssociationSecondary. It is almost the same as the normal association, but with
	 * an additional field "predicate".
	 *
	 * AW
	 * @since 19.07.2011 19:27:02
	 *
	 * @param poRootElement
	 * @param poAssociation
	 * @param poDataContainer
	 */
	private static void createAssociationSec(Instance poRootElement, Instance poAssociation,
			clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {

		//HZ Checks if the tree depth does not exceed a certain limit => avoids
		// endless loops
		checkStackDepth(); 

		clsAssociation oDataStructure = null;
		String oAssName = poAssociation.getName(); 
		eDataType eAssociationType = getElementDataType(poAssociation); 
		eContentType eAssContentType = getElementContentType(poAssociation); 

		if(poRootElement == null){
			Instance oIns_a = (Instance)getSlotValues("element", poAssociation).toArray()[0]; 
			Instance oIns_b = (Instance)getSlotValues("element", poAssociation).toArray()[1]; 
			
			//Extract predicate
			ePredicate oPredicate = ePredicate.valueOf((String) getSlotValue("predicate", poAssociation));
			
			initDataStructure(null, oIns_a, poDataContainer);
			initDataStructure(null, oIns_b, poDataContainer);
			clsDataStructurePA oDS_a = retrieveDataStructure(oIns_a.getName(), poDataContainer.b);
			clsDataStructurePA oDS_b = retrieveDataStructure(oIns_b.getName(), poDataContainer.b);
			oDataStructure = getNewAssociation(eAssContentType, eAssociationType, poAssociation, oDS_a, oDS_b, oPredicate);
		}
		else {
			//Extract predicate
			ePredicate oPredicate = ePredicate.valueOf((String)getSlotValue("predicate", poAssociation));
			for(Object oElement : getSlotValues("element", poAssociation)){
				String oRootName = poRootElement.getName(); 
				String oElementName = ((Instance) oElement).getName(); 

				if(!(oElementName.equals(oRootName))){
					initDataStructure(poRootElement, (Instance)oElement, poDataContainer);
					clsDataStructurePA oDS_a = retrieveDataStructure(oRootName, poDataContainer.b);
					clsDataStructurePA oDS_b = retrieveDataStructure(oElementName, poDataContainer.b);
					oDataStructure = getNewAssociation(eAssContentType, eAssociationType, poAssociation, oDS_a, oDS_b, oPredicate);
				}
			}
		}

		//TODO HZ: Define other attributes!!
		poDataContainer.b.put(oAssName, oDataStructure);
	}

	

	private static void checkStackDepth() {
		/*long depth = Thread.currentThread().getStackTrace().length;
		
		if (depth > mrMaxStackDepth) {
			throw new StackOverflowError("max. call stack for clsAssociation<TYPE>.clone reached. either increase threshold constant or check code for an infinite clone loop. ("+depth+">"+mrMaxStackDepth+")");
		}*/
	}

	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.07.2011 19:37:33
	 *
	 * @param peElementType
	 * @param poAssociation
	 * @param poElementA
	 * @param poElementB
	 * @return
	 */
	private static clsAssociation getNewAssociation(eContentType peContentType, eDataType peElementType, Instance poAssociation, clsDataStructurePA poElementA, clsDataStructurePA poElementB) {
		//Specialize the create association function, by not applying an attribute
		return getNewAssociation(peContentType, peElementType, poAssociation, poElementA, poElementB, ePredicate.NONE);
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
	private static clsAssociation getNewAssociation(eContentType peContentType, eDataType peElementType, Instance poAssociation, clsDataStructurePA poElementA, clsDataStructurePA poElementB, ePredicate oPredicate) {
		
		//Use oPredicate if an AssociationSecondary is used, else the predicate is not used in the function.
		
		int oID = DS_ID++;  
		clsPair<clsDataStructurePA, clsDataStructurePA> oAssociationElements = null; 
		
		switch(peElementType){
			case ASSOCIATIONATTRIBUTE:
				return new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
						(clsPrimaryDataStructure)poElementA,(clsPrimaryDataStructure)poElementB); 
			case ASSOCIATIONTEMP:
				return new clsAssociationTime(new clsTriple<Integer, eDataType, eContentType >(oID,peElementType,peContentType),
						(clsThingPresentationMesh)poElementA,(clsThingPresentationMesh)poElementB); 
			
			case ASSOCIATIONDM:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.DM);
				return new clsAssociationDriveMesh(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
												   (clsDriveMesh)oAssociationElements.a, 
												   (clsThingPresentationMesh)oAssociationElements.b); 
			case ASSOCIATIONEMOTION:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.EMOTION);
				return new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
												   (clsEmotion)oAssociationElements.a, 
												   (clsThingPresentationMesh)oAssociationElements.b); 
			case ASSOCIATIONPRI:
				return new clsAssociationPrimary(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
						(clsThingPresentationMesh)poElementA,(clsThingPresentationMesh)poElementB);
			
			case ASSOCIATIONWP:
				if ((poElementA instanceof clsWordPresentationMesh) || (poElementB instanceof clsWordPresentationMesh)) {
					oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.WPM);
					return new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
							   (clsWordPresentationMesh)oAssociationElements.a, 
							   (clsDataStructurePA)oAssociationElements.b);
				} else {
					oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.WP);
					return new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
							   (clsWordPresentation)oAssociationElements.a, 
							   (clsDataStructurePA)oAssociationElements.b);
				}
	
			//Special case, where the String "Predicate" is added
			case ASSOCIATIONSEC:
				oAssociationElements = evaluateElementOrder(poElementA, poElementB, eDataType.WPM);	//In association secondary, the same conditions as in WP are used. This association has a direction
				return new clsAssociationSecondary(new clsTriple<Integer, eDataType, eContentType>(oID,peElementType,peContentType),
						   (clsWordPresentationMesh)oAssociationElements.a, 
						   (clsSecondaryDataStructure)oAssociationElements.b, oPredicate);
				
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
	private static ArrayList<clsAssociation> loadInstanceAssociations(Instance poDataElement,  
												clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
		
		Collection <?> oInstanceAssociations = getSlotValues("instance_association", poDataElement);
		ArrayList <clsAssociation> oAssociationList = new ArrayList<clsAssociation>(); 
		clsAssociation oAssociation = null; 
		
		for(Object element : oInstanceAssociations){
				oAssociation = loadAssociation(poDataElement, (Instance) element, poDataContainer); 
				//Below the necessity of the association is defined. Entities are defined by their associations to different 
				//types of data structures. Depending on their definition in the ontology (class_association or instance association)
				//the necessity is defined as true (mandatory) or false (optional). E.g. an entity arsin has always the shape "circle"
				//but can differ in its color => shape is defined as class association while color is defined as instance association. Here,
				//the instance associations are defined => moAssociationImperative is set to false => optional.
				oAssociation.setMrImperativeFactor(eDataStructureMatch.OPTIONALMATCH.getMatchFactor()); 
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
					oAssociationElement = poDataContainer.a.createInstance(oAssociationName, poDataContainer.a.getCls(eDataType.ASSOCIATIONDM.name()));
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
				//the necessity is defined as true (mandatory) or false (optional). E.g. an entity arsin has always the shape "circle"
				//but can differ in its color => shape is defined as class association while color is defined as instance association. Here,
				//the class associations are defined => moAssociationImperative is set to true => mandatory. 
				oAssociation.setMrImperativeFactor(eDataStructureMatch.MANDATORYMATCH.getMatchFactor()); 
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
	private static clsAssociation loadAssociation(Instance poRootElement, Instance poAssociation, 
												clsPair<KnowledgeBase, HashMap<String,clsDataStructurePA>> poDataContainer) {
 
		initDataStructure(poRootElement, poAssociation, poDataContainer);
		return (clsAssociation)retrieveDataStructure(poAssociation.getName(),poDataContainer.b);
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
	private static clsDataStructurePA retrieveDataStructure(String poElNa, HashMap<String,clsDataStructurePA> poDataStructureTable) {
			return poDataStructureTable.get(poElNa); 
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
	
		try{
			oReturnValue = eDataType.valueOf(oElementType);
		}catch (IllegalArgumentException e){System.out.println(" Element of type " + oElementType + " not handled");}
		
		return oReturnValue; 
 	}
	
	private static eContentType getElementContentType(Instance poElement) {
		String oElementType = (String)getSlotValue("type", poElement); 
		eContentType oReturnValue = null; 
	
		try{
			oReturnValue = eContentType.valueOf(oElementType);
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

