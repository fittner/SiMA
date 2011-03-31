/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import bfg.tools.clsMutableDouble;
import config.clsBWProperties;
import du.enums.pa.eContext;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v30.I2_14_receive;
import pa.interfaces.receive._v30.I2_8_receive;
import pa.interfaces.send._v30.I2_8_send;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.storage.clsBlockedContentStorage;
import pa.tools.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class E35_EmersionOfRepressedContent extends clsModuleBase implements I2_14_receive, I2_8_send, itfKnowledgeBaseAccess {
	public static final String P_MODULENUMBER = "35";
	public static String P_CONTEXT_SENSTITIVITY = "CONTEXT_SENSITIVITY"; 
	
	private clsBlockedContentStorage moBlockedContentStorage;
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler; 
	private clsMemory moMemory;
	
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
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsMemory poMemory,
			clsBlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList);
		
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		moMemory = poMemory;
		moBlockedContentStorage = poBlockedContentStorage;

		applyProperties(poPrefix, poProp);
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
		
		moAttachedRepressed_Output = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		ArrayList<clsPrimaryDataStructureContainer> oContainerList = new ArrayList<clsPrimaryDataStructureContainer>(); 
 		
		oContainerList = moEnvironmentalTP_Input; 
		assignDriveMeshes(oContainerList); 
		adaptCathegories(oContainerList);
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
	
		
		search(eDataType.DM, poContainerList, oSearchResult); 
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
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				ArrayList <clsAssociation> oAssociationList = oEntry.get(0).b.getMoAssociatedDataStructures(); 
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
		
		eContext oContext = eContext.valueOf(poContextPrim.getKey().getMoDataStructure().getMoContentType());
		
		for(clsAssociation oAssociation : poContainer.getMoAssociatedDataStructures()){
			//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
			// do not have a data structure ID => in a later version when E16 will be placed in front 
			// of E15, the patterns already have an ID. 
			clsDataStructurePA oData = oAssociation.getLeafElement();
			
			if(oData instanceof clsDriveMesh){
				if(eContext.valueOf(oData.getMoContentType()).equals(oContext)){
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
		return moMemory.moCurrentContextStorage.getContextRatiosPrimCONVERTED(mrContextSensitivity);
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
		
		for(clsPrimaryDataStructureContainer oInput : poCathegorizedInputContainer){
				clsDriveMesh oRep = moMemory.moRepressedContentsStore.getBestMatchCONVERTED(oInput);
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
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#createSearchPattern(pa.memorymgmt.enums.eDataType, java.lang.Object, java.util.ArrayList)
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
		((I2_8_receive)moModuleList.get(45)).receive_I2_8(moAttachedRepressed_Output);
		((I2_8_receive)moModuleList.get(18)).receive_I2_8(moAttachedRepressed_Output);
		
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

}
