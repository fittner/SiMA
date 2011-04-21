/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Map;
import java.util.Map.Entry;
import bfg.tools.clsMutableDouble;
import config.clsBWProperties;
import du.enums.pa.eContext;
import pa._v30.tools.clsPair;
import pa._v30.tools.toHtml;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I2_14_receive;
import pa._v30.interfaces.modules.I2_8_receive;
import pa._v30.interfaces.modules.I2_8_send;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.memorymgmt.enums.eDataType;
import pa._v30.storage.clsBlockedContentStorage;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class E35_EmersionOfRepressedContent extends clsModuleBaseKB implements I2_14_receive, I2_8_send {
	public static final String P_MODULENUMBER = "35";
	public static String P_CONTEXT_SENSTITIVITY = "CONTEXT_SENSITIVITY"; 
	
	private clsBlockedContentStorage moBlockedContentStorage;
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP_Input; 
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output; 
	private double mrContextSensitivity = 0.8;
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:24:23
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E35_EmersionOfRepressedContent(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsBlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		moBlockedContentStorage = poBlockedContentStorage;

		applyProperties(poPrefix, poProp);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += toHtml.valueToHTML("moBlockedContentStorage", moBlockedContentStorage);
		html += toHtml.listToHTML("moEnvironmentalTP_Input", moEnvironmentalTP_Input);
		html += toHtml.listToHTML("moAttachedRepressed_Output", moAttachedRepressed_Output);
		html += toHtml.valueToHTML("mrContextSensitivity", mrContextSensitivity);
		html += toHtml.valueToHTML("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
		return html;
	}	

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_CONTEXT_SENSTITIVITY, 0.8);
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		mrContextSensitivity = poProp.getPropertyDouble(pre+P_CONTEXT_SENSTITIVITY);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//HZ 16.08.2010: this part is programmed to map the functionalities of ARSi09 to the new datastructure model. However, 
		//it is for sure that the functionalities change in further model revisions. These changes include that 
		//	- modules of E15 (in case it still exists) will presumably not obey of a memory access (this has to be
		//	  included here in order to match ARSi09 functionalities)
		//	- The cathegorization has to be done on the base of memories that are actually retrieved in E16. This will 
		//	  be changed in the next model revision; 
		//	- The current context has to be read out of a buffer (like a working memory) that has to be introduced. As some 
		//	  model changes will turn up, the current implementation uses the old hack that bases on ARSi09 data structures. 
		//    in the class clsRepressedContentStorage methods are introduced that return the context in terms of new 
		//	  data structures. However, after the new functionalities are introduced, old and new data structures have to 
		//    be clearly separated from each other and the use of clsRepressedContentStorage has to be avoided. 
		
		//FIXME: AW: The output consists of 3 equal pairs of DM and Containers. Why is the same Ref used 3 times?
		moAttachedRepressed_Output = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		ArrayList<clsPrimaryDataStructureContainer> oContainerList = new ArrayList<clsPrimaryDataStructureContainer>(); 
 		
		oContainerList = moEnvironmentalTP_Input; 
		/* Add DM for to object and include them in the TPM through associations. The TPM then contains 
		 * attributeassociations and drivemeshassociations
		 */
		assignDriveMeshes(oContainerList);
		/* The context of a certain drive or object is loaded, in the case of CAKE, it is NOURISH. If the drive 
		 * NOURISH is found in the objects the categories (anal, oral...) is multiplied with a category factor <= 1
		 * In case of a 100% match, the factor is 1.0.
		 */
		adaptCathegories(oContainerList);
		/* DM from the Repressed Content are added to the objects in the oContainerList
		 */
		matchRepressedContent(oContainerList); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 09:55:48
	 *
	 * @return
	 */
	private void assignDriveMeshes(ArrayList<clsPrimaryDataStructureContainer> poContainerList) {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
	
		/*Search for associated Drive Meshes with the parameter Datatype=DM, poContainerlist with the objects and
		*their associations and put the result in oSearchResult, which consists of the original objects from 
		*poContainerlist and for each object all assigned DM. The DM also have a weight, which is always set=1
		*/
		search(eDataType.DM, poContainerList, oSearchResult); 
		/*This function takes the associated DM from oSeachResult and adds them to the corresponding
		 * poContainerList in the associated Objects (not in the TPM)
		 */
		addAssociations(poContainerList, oSearchResult);  
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.03.2011, 17:03:40
	 * @param oSearchPattern 
	 *
	 * @param poContainer
	 * @param oSearchResult
	 */
	private void addAssociations(ArrayList<clsPrimaryDataStructureContainer> poContainerList, ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		//oEntry: Data structure with a double association weigth and an object e. g. CAKE with its associated DM.
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				//get associated DM from a the object e. g. CAKE
				ArrayList <clsAssociation> oAssociationList = oEntry.get(0).b.getMoAssociatedDataStructures();
				//Add associated DM to the input list. Now the list moAssociatedDataStructures contains DM and ATTRIBUTES
				poContainerList.get(poSearchResult.indexOf(oEntry)).getMoAssociatedDataStructures().addAll(oAssociationList); 
				
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 15:09:42
	 *
	 * @param oContainer
	 */
	private void adaptCathegories(ArrayList<clsPrimaryDataStructureContainer> poContainerList) {
		
		HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> oContextResult = getContext(); 
		
		for(clsPrimaryDataStructureContainer oContainer : poContainerList){
			for( Map.Entry<clsPrimaryDataStructureContainer, clsMutableDouble> oContextPrim : oContextResult.entrySet() ) {
				calculateCath(oContainer, oContextPrim); 
			}
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 26.08.2010, 12:06:22
	 *
	 * @param oContainer
	 * @param oContextPrim
	 */
	private void calculateCath(clsPrimaryDataStructureContainer poContainer,
							   Entry<clsPrimaryDataStructureContainer, clsMutableDouble> poContextPrim) {
		
		//Get the drive of the object. In the case of the CAKE, NOURISH
		eContext oContext = eContext.valueOf(poContextPrim.getKey().getMoDataStructure().getMoContentType());
		
		/*For each Associated Content for each object in the clsPrimaryDataStructureContainer, get the associated
		 * content in oAssociation (Format clsAssociation)
		 * 
		 */
		for(clsAssociation oAssociation : poContainer.getMoAssociatedDataStructures()){
			//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
			// do not have a data structure ID => in a later version when E16 will be placed in front 
			// of E15, the patterns already have an ID. 
			
			/* In the case of the CAKE, get the Leaf-Element, i. e. the Thing presentation, which describes
			 * one property 
			 */
			clsDataStructurePA oData = oAssociation.getLeafElement();
			
			//If the oData is not a Thing Presentation, but a DM ...
			if(oData instanceof clsDriveMesh){
				//If the Drive is equal to the drive in the context (e. g. NOURISH) then....
				if(eContext.valueOf(oData.getMoContentType()).equals(oContext)){
					/*setCathegories has the following Parameters:
					 * The DM with their categories anal, oral, phallic and genital and the context value
					 * For the CAKE with the drive NOURISH, the context value is = 1.0 as the thing can be 
					 * eaten. The categories are multiplied with the context value.
					 * This function reduces not 100%-Matching context with the context factor. If the 
					 * context matches to 100%, then the context factor is = 1.0
					 */
					setCathegories((clsDriveMesh)oData, poContextPrim.getValue().doubleValue()); 
				}
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 26.08.2010, 12:02:45
	 *
	 * @return
	 */
	private HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> getContext() {
		//return moMemory.moCurrentContextStorage.getContextRatiosPrimCONVERTED(mrContextSensitivity);
		return new HashMap<clsPrimaryDataStructureContainer, clsMutableDouble>();
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 18:13:00
	 *
	 * @param oDM
	 * @param doubleValue
	 */
	private void setCathegories(clsDriveMesh poDM, double prContextValue) {
		poDM.setAnal(poDM.getAnal() * prContextValue); 
		poDM.setGenital(poDM.getGenital() * prContextValue);
		poDM.setOral(poDM.getOral() * prContextValue); 
		poDM.setPhallic(poDM.getPhallic() * prContextValue);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 17:15:39
	 *
	 * @param oContainer
	 * @return
	 */
	private void matchRepressedContent(ArrayList<clsPrimaryDataStructureContainer> poCathegorizedInputContainer) {
		
		//For each object (e. g. CAKE) with adapted categories...
		//oInput is a clsPrimaryDataStructureContainer
		for(clsPrimaryDataStructureContainer oInput : poCathegorizedInputContainer){
				/* A DM is loaded, which matches a drive, which is Repressed.
				 * In the storage of Repressed Content, DM are stored. If the ContentType of the DM attached to
				 * an object is exactly matched to a content type of a DM in the repressed Content Store, 
				 * the categories are compared. For each matching DM, the equality of the values in the categories
				 * are compared and a number <= 1.0 is generated. The DM with the highest category match is returned
				 * In the case of CAKE, there exists 2 DM: BITE (associated with DEATH) and NOURISH. In the Repressed Content Store, there
				 * exists 2 Repressed Content DM: BITE (PUNCH) and NOURISH (GREEDY), both with negative mrPleasure
				 * The match of the BITE DM is 0.5 and the match of the NOURISH DM is 0.9. Therefore, the DM of 
				 * NOURISH is selected
				 */
				//FIXME: mrPleasure = -0.3. This is not allowed. Add mrUnpleasure instead
//				clsDriveMesh oRep = moMemory.moRepressedContentsStore.getBestMatchCONVERTED(oInput);
//				moAttachedRepressed_Output.add(new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oInput, oRep));
// TD 2011/04/20: removed above two line due to removal of rolands clsMemory. has to be reimplemented by other means
// TODO (Wendt): reimplement method matchRepressedContent
			clsDriveMesh oRep = moBlockedContentStorage.getBestMatchCONVERTED(oInput);
			moAttachedRepressed_Output.add(new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oInput, oRep));
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_8(moAttachedRepressed_Output);
			
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:52:35
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (wendt) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:52:35
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (wendt) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 08:36:59
	 *
	 * @param undefined
	 * @param poDS
	 * @param oSearchResult
	 */
	@Override
	public <E> void search(
			eDataType poDataType,
			ArrayList<E> poPattern,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		createSearchPattern(poDataType, poPattern, oSearchPattern);
		accessKnowledgeBase(poSearchResult, oSearchPattern); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.03.2011, 19:04:29
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#createSearchPattern(pa._v30.memorymgmt.enums.eDataType, java.lang.Object, java.util.ArrayList)
	 */
	@Override
	public <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList,
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		for (E oEntry : poList){
				if(oEntry instanceof clsDataStructurePA){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, (clsDataStructurePA)oEntry));
				}
				else if (oEntry instanceof clsPrimaryDataStructureContainer){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, ((clsPrimaryDataStructureContainer)oEntry).getMoDataStructure()));
				}
			}
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.03.2011, 22:34:44
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(pa.tools.clsPair)
	 */
	@Override
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult,
									ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:24:31
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:25:17
	 * 
	 * @see pa.interfaces.send._v30.I2_8_send#send_I2_8(java.util.ArrayList)
	 */
	@Override
	public void send_I2_8(
			ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I2_8_receive)moModuleList.get(45)).receive_I2_8(poMergedPrimaryInformation);
		((I2_8_receive)moModuleList.get(18)).receive_I2_8(poMergedPrimaryInformation);
		
		putInterfaceData(I2_8_send.class, poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:25:17
	 * 
	 * @see pa.interfaces.receive._v30.I2_14_receive#receive_I2_14(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_14(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		moEnvironmentalTP_Input = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poEnvironmentalTP);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This module shares the same task as the second part of the tasks of {E36}. It is responsible for changing repressed contents such that they are more likely to pass the defense mechanisms. This is done by searching for fitting incoming thing presentations. If one is found, the repressed content is attached to it. All incoming thing presentations are forwarded to next modules, some of them with additional information attached.";
	}	
}
