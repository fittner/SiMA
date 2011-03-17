/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import bfg.tools.clsMutableDouble;

import config.clsBWProperties;
import du.enums.pa.eContext;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v19.I2_5_receive;
import pa.interfaces.receive._v19.I2_6_receive;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class S_ManagementOfRepressedContents_1 extends clsModuleBase implements I2_5_receive, itfKnowledgeBaseAccess {

	public ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP_Input; 
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output; 
	
	private double mrContextSensitivity = 0.8;
	
	public static String P_CONTEXT_SENSTITIVITY = "CONTEXT_SENSITIVITY"; 
	
	
	public S_ManagementOfRepressedContents_1(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
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
	 * 07.10.2009, 11:20:02
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		moEnvironmentalTP_Input = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poEnvironmentalTP); 
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
		ArrayList<clsPrimaryDataStructureContainer> oContainer = new ArrayList<clsPrimaryDataStructureContainer>(); 
 		
		oContainer = assignDriveMeshes(); 
		adaptCathegories(oContainer);
		matchRepressedContent(oContainer); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 09:55:48
	 *
	 * @return
	 */
	private ArrayList<clsPrimaryDataStructureContainer> assignDriveMeshes() {
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>(); 
	
		for(clsPrimaryDataStructureContainer oContainer : moEnvironmentalTP_Input){
			ArrayList<clsAssociation> oAssDS = getAssociatedDS(eDataType.DM, oContainer.getMoDataStructure()); 
			oRetVal.add(new clsPrimaryDataStructureContainer(oContainer.getMoDataStructure(), oAssDS));
		}
	
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.08.2010, 08:21:09
	 *
	 * @param poType
	 * @param poDS
	 * @return
	 */
	private ArrayList<clsAssociation> getAssociatedDS(eDataType poType, clsDataStructurePA poDS){
		moSearchPattern.clear(); 
		addToSearchPattern(poType, poDS); 
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		ArrayList<clsAssociation> oAssDS = new ArrayList<clsAssociation>(); 
		
		//FIXME HZ: IndexOutOfBound + NullpointerException should be avoided
		//HZ 23.08.2010 Actually the best match is taken from the search result =>
		//      		get(0) *2 
		accessKnowledgeBase(oSearchResult);
		
		try{
			oAssDS = oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures();
		}catch(java.lang.IndexOutOfBoundsException e){/*HZ: Must be refactored*/}
		
		return oAssDS;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 15:09:42
	 *
	 * @param oContainer
	 */
	private void adaptCathegories(ArrayList<clsPrimaryDataStructureContainer> oContainerList) {
		
		HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> oContextResult = getContext(); 
		
		for(clsPrimaryDataStructureContainer oContainer : oContainerList){
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
	private void calculateCath(clsPrimaryDataStructureContainer poContainer,Entry<clsPrimaryDataStructureContainer, clsMutableDouble> poContextPrim) {
		
		eContext oContext = eContext.valueOf(poContextPrim.getKey().getMoDataStructure().getMoContentType());
		
		for(clsAssociation oAssociation : poContainer.getMoAssociatedDataStructures()){
			//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
			// do not have a data structure ID => in a later version when E16 will be placed in front 
			// of E15, the patterns already have an ID. 
			clsDriveMesh oDM = (clsDriveMesh)((clsAssociationDriveMesh)oAssociation).getLeafElement();  
				
			if(eContext.valueOf(oDM.getMoContentType()).equals(oContext)){
				setCathegories(oDM, poContextPrim.getValue().doubleValue()); 
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
		return moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrimCONVERTED(mrContextSensitivity);
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
				clsDriveMesh oRep = moEnclosingContainer.moMemory.moRepressedContentsStore.getBestMatchCONVERTED(oInput);
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
		((I2_6_receive)moEnclosingContainer).receive_I2_6(moAttachedRepressed_Output);
		
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
		
		throw new java.lang.NoSuchMethodError();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.08.2010, 13:41:53
	 *
	 * @param wp
	 * @param oDriveMesh
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType, clsDataStructurePA poSearchPattern) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(oReturnType.nBinaryValue, poSearchPattern)); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.08.2010, 10:11:28
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		poSearchResult.addAll(moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern));
	}

}
