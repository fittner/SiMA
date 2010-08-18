/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bfg.tools.clsMutableDouble;

import config.clsBWProperties;
import du.enums.eEntityType;
import du.enums.pa.eContext;
import pa.clsInterfaceHandler;
import pa.datatypes.clsDriveContentCategories;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I2_5_receive;
import pa.interfaces.receive.I2_6_receive;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class S_ManagementOfRepressedContents_1 extends clsModuleBase implements I2_5_receive, itfKnowledgeBaseAccess {

	public ArrayList<clsPrimaryInformation> moEnvironmentalTP_Input_old;
	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moAttachedRepressed_Output_old;
	
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
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP_old, ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		moEnvironmentalTP_Input_old = (ArrayList<clsPrimaryInformation>)deepCopy( poEnvironmentalTP_old );
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
		
 		ArrayList<clsPrimaryDataStructureContainer> oContainer = assignDriveMeshes(); 
		adaptCathegories(oContainer);
		moAttachedRepressed_Output = matchRepressedContent(oContainer); 
		
		process_oldDT(); 
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
		HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult; 
		
		moSearchPattern.clear(); 
		for(clsPrimaryDataStructureContainer oContainer : moEnvironmentalTP_Input){
			addToSearchPattern(eDataType.DM, oContainer.moDataStructure); 
		}
		
		oSearchResult = accessKnowledgeBase(); 
		
		for(clsPair<Integer, clsDataStructurePA> oEntry: moSearchPattern){
			//HZ: 16.08.2010 Actually the first element is taken out of the list.
			//The associated drive meshes are linked to the search pattern; the search pattern is not 
			//exchanged. 
			clsDataStructurePA oDataStructure = oEntry.b; 
			ArrayList<clsAssociation> oAssociatedDataStructures = new ArrayList<clsAssociation>(); 
			
			if(oSearchResult.containsKey(moSearchPattern.indexOf(oEntry))){
				oAssociatedDataStructures = oSearchResult.get(moSearchPattern.indexOf(oEntry)).get(0).b.moAssociatedDataStructures; 
			}
			
			oRetVal.add(new clsPrimaryDataStructureContainer(oDataStructure, oAssociatedDataStructures));
		}
		
		return oRetVal;
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
		
		HashMap<clsPrimaryDataStructureContainer, clsMutableDouble> oContextResult = 
							moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrimCONVERTED(mrContextSensitivity);
		
		for(clsPrimaryDataStructureContainer oContainer : oContainerList){
			for( Map.Entry<clsPrimaryDataStructureContainer, clsMutableDouble> oContextPrim : oContextResult.entrySet() ) {
				eContext oContext = eContext.valueOf(oContextPrim.getKey().moDataStructure.moContentType);
								
				for(clsAssociation oAssociation : oContainer.moAssociatedDataStructures){
					//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
					// do not have a data structure ID => in a later version when E16 will be placed in front 
					// of E15, the patterns already have an ID. 
					clsDriveMesh oDM = ((clsAssociationDriveMesh)oAssociation).getDM();  
						
					if(eContext.valueOf(oDM.moContentType).equals(oContext)){
						setCathegories(oDM, oContextPrim.getValue().doubleValue()); 
					}
				}
			}
		}
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
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> matchRepressedContent(
			ArrayList<clsPrimaryDataStructureContainer> poCathegorizedInputContainer) {
		
		ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> oRetVal = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		
		for(clsPrimaryDataStructureContainer oInput : poCathegorizedInputContainer){
				clsDriveMesh oRep = moEnclosingContainer.moMemory.moRepressedContentsStore.getBestMatchCONVERTED(oInput);
				oRetVal.add(new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oInput, oRep));
		}
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 * This method is used while adapting the model from the old datatypes (pa.datatypes) to the
	 * new ones (pa.memorymgmt.datatypes) The method has to be deleted afterwards.
	 * @author zeilinger
	 * 13.08.2010, 09:56:48
	 * @deprecated
	 */
	private void process_oldDT() {
		cathegorize_old( moEnvironmentalTP_Input_old );
		moAttachedRepressed_Output_old = matchWithRepressedContent_old(moEnvironmentalTP_Input_old);
	}

	/**
	 * returns the corresponding driveContentCategories in the current context
	 *
	 * @author langr
	 * 17.10.2009, 18:52:32
	 *
	 * @param moEnvironmentalTP_Input2
	 * @return
	 * @deprecated
	 */
	private void cathegorize_old(
			ArrayList<clsPrimaryInformation> poEnvironmentalTP) {

		HashMap<eEntityType, clsPrimaryInformation> oSemanticWeb = this.moEnclosingContainer.moMemory.moObjectSemanticsStorage.moObjectSemantics;
		
		//get current contexts and attach drvContCat to TP of incoming PrimInfo
		HashMap<clsPrimaryInformation, clsMutableDouble> oContextResult = moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(mrContextSensitivity); 
		for(clsPrimaryInformation oInfo : poEnvironmentalTP) {
			for( Map.Entry<clsPrimaryInformation, clsMutableDouble> oContextPrim : oContextResult.entrySet() ) {
				eContext oContext = (eContext)oContextPrim.getKey().moTP.moContent;
				if( oSemanticWeb.containsKey(oInfo.moTP.moContent) ) {
					clsDriveContentCategories oCath = oSemanticWeb.get(oInfo.moTP.moContent).moTP.moDriveContentCategory.get(oContext);
					if(oCath != null) { //cathegory does not exist here
					clsDriveContentCategories oCathegory = new clsDriveContentCategories( oCath );
					oCathegory.adaptToContextRatio(oContextPrim.getValue().doubleValue());	//lower the category-ratio according to the match of the context
					oInfo.moTP.moDriveContentCategory.put(oContext, oCathegory);
					}

				}
			}
		}
		return;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 17.10.2009, 18:54:27
	 *
	 * @param poCategorizedInput
	 * @return
	 * @deprecated
	 */
	private ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> matchWithRepressedContent_old(
			ArrayList<clsPrimaryInformation> poCategorizedInput) {
		
		ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> oRetVal = new ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>>();

		for(clsPrimaryInformation oInput : poCategorizedInput) {
			clsPrimaryInformation oRep = moEnclosingContainer.moMemory.moRepressedContentsStore.getBestMatch(oInput.moTP.moDriveContentCategory);
			oRetVal.add(new clsPair<clsPrimaryInformation, clsPrimaryInformation>(oInput, oRep));
		}
		
		return oRetVal;
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
		((I2_6_receive)moEnclosingContainer).receive_I2_6(moAttachedRepressed_Output_old, moAttachedRepressed_Output);
		
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
		// TODO (deutsch) - Auto-generated method stub
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
		// TODO (deutsch) - Auto-generated method stub
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
	public HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> accessKnowledgeBase() {
		
		return moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}

}
