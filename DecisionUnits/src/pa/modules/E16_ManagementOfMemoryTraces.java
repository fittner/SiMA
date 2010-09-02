/**
 * E16_ManagmentOfMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import bfg.tools.clsMutableDouble;

import config.clsBWProperties;
import du.enums.eEntityType;
import du.enums.pa.eRepressedContentType;
import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I2_6_receive;
import pa.interfaces.receive.I2_7_receive;
import pa.interfaces.send.I2_7_send;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 * 
 */
public class E16_ManagementOfMemoryTraces extends clsModuleBase implements I2_6_receive, I2_7_send, itfKnowledgeBaseAccess {

	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moPerceptPlusRepressed_Input_old;
	public ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> moPerceptPlusMemories_Output_old;
	
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moPerceptPlusRepressed_Input;
	public ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>> moPerceptPlusMemories_Output;
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:31:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E16_ManagementOfMemoryTraces(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		moPerceptPlusMemories_Output_old = new ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>>();
		moPerceptPlusMemories_Output = new ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>>(); 
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
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
	 * 11.08.2009, 14:31:53
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		
		moPerceptPlusRepressed_Input_old = (ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>)deepCopy(poPerceptPlusRepressed_old);
		moPerceptPlusRepressed_Input = (ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>>)deepCopy(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moPerceptPlusMemories_Output = getOutput(); 
		process_oldDT(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 16:49:37
	 *
	 * @return
	 */
	private ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>> getOutput() {
		
		ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>> oRetVal 
							= new ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>>(); 
		
		for(clsPair<clsPrimaryDataStructureContainer, clsDriveMesh> oEntry : moPerceptPlusRepressed_Input){
			//HZ: The method getEnvironmentalRepresentation retrieves memory matches for
			//	environmental input; objects are identified and exchanged by their 
			//	stored correspondance. oEnvironmentalInput receives them and adds 
			//	former drive meshes as associated data structures to the container. 
			clsPrimaryDataStructureContainer oEnvironmentalInput = getEnvironmentalRepresentation(oEntry.a); 		
			
			if(oEntry.a != null && oEntry.b != null){
				ArrayList<clsDriveMesh> oDriveContent = getAssociatedContent(oEnvironmentalInput, oEntry.b);
				oRetVal.add(new clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>(oEnvironmentalInput, oEntry.b, oDriveContent));
			}
			else{
				oRetVal.add(new clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>(oEnvironmentalInput, null, new ArrayList<clsDriveMesh>()));
			}
		}
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 18:20:26
	 * @param b 
	 * @param oEnvironmentalInput 
	 * @return 
	 *
	 */
	private ArrayList<clsDriveMesh> getAssociatedContent(clsPrimaryDataStructureContainer oEnvironmentalInput,
													    clsDriveMesh oRepressedContent) {
			
			//FIXME HZ 18.08.2010: This part is quite a hack in the old version and it is actually 
			//not possible to verify how the aware content can be implemented in a more
			//"dynamic" form. This has to be discussed and corrected when it is time to do so. 
			//
			//HZ's interpretation:
			//From E15 a repressedContent is received that has to be associated to a new 
			//object in order to increase the possibility for passing the defense mechanism.
			//Hence the associated drives are retrieved from the memory (done in this method)
			//and are compared with the repressed content. In case the ContentType of a drive mesh
			//matches the repressed content; a drive mesh that has the chance to get "aware" is found; 
			//In e17 this aware content will be connected to the object 
			//Open questions:
			// 	- is this interpretation right?
			//	- if so, is "aware content" the right name for these drive meshes
			//by the way, DO NOT BLAME the programmer; BLAME the orderer
			 
			ArrayList<clsDriveMesh> oRetVal = new ArrayList<clsDriveMesh>();
			clsDataStructureContainer oSearchResult = null; 
			moSearchPattern.clear(); 
			addToSearchPattern(eDataType.DM, oEnvironmentalInput.moDataStructure);
			//It is for sure that the search retrieves a result as the search parameter is 
			//an already found object => hence the retrieved ArrayList is not empty. 
			//In addition the search retrieves exactly one result as the search parameter 
			//obeys of a data-structure identification number. 
			//=> Hence the container can be read out directly below; 
			oSearchResult = accessKnowledgeBase().get(0).get(0).b;
			
			if(oSearchResult.moAssociatedDataStructures != null){
				for(clsAssociation oAssociation : oSearchResult.moAssociatedDataStructures){
						clsDriveMesh oFoundDM = ((clsAssociationDriveMesh)oAssociation).getDM(); 
						
						if(oRepressedContent.moContentType.equals(oFoundDM.moContentType)){
							oRetVal.add(oFoundDM); 
						}
				}
			}
							
			return oRetVal; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 18:20:23
	 * @param a 
	 * @return 
	 *
	 */
	private clsPrimaryDataStructureContainer getEnvironmentalRepresentation(clsPrimaryDataStructureContainer poEnvironmentalInput) {
		
		clsPrimaryDataStructureContainer oSearchResult = searchContainer(poEnvironmentalInput);
		mergeEnvironmentalInputWithMemory(oSearchResult, poEnvironmentalInput); 
		
		return oSearchResult; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 23:30:58
	 *
	 * @param poEnvironmentalInput
	 * @return
	 */
	private clsPrimaryDataStructureContainer searchContainer(clsPrimaryDataStructureContainer poEnvInput) {
		
		clsPrimaryDataStructureContainer oCon = poEnvInput; 
		ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResult = searchDS(poEnvInput.moDataStructure); 
		
		if(oSearchResult!=null){
			//Only the best match is used for the further processing; this explains the 
			//get(0) in the statement below. 
			oCon = (clsPrimaryDataStructureContainer)oSearchResult.get(0).b; 
			oCon.moAssociatedDataStructures = poEnvInput.moAssociatedDataStructures; 
		}
		//In case their does not exist a search result, the Environmental INPUT VALUE is assigned to the container.  
		return oCon;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 20:22:16
	 * @param poEnvironmentalInput 
	 *
	 * @return
	 */
	private ArrayList<clsPair<Double, clsDataStructureContainer>>  searchDS(clsDataStructurePA poDS) {
		moSearchPattern.clear(); 
		addToSearchPattern(eDataType.UNDEFINED, poDS);
		//The search pattern exists out of one entry => the returned hashmap contains only 
		//ONE entry that has the key 0. 
		return accessKnowledgeBase().get(0); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 20:39:09
	 *
	 * @param oSearchResult
	 * @param poEnvironmentalInput
	 */
	private void mergeEnvironmentalInputWithMemory(clsPrimaryDataStructureContainer poStoredContainer,
												   clsPrimaryDataStructureContainer poEnvironmentalInput) {
		
		if(poStoredContainer.moDataStructure instanceof clsThingPresentationMesh && poEnvironmentalInput.moDataStructure instanceof clsThingPresentationMesh){

		 	for(clsAssociation oAAEnvironmentIn : ((clsThingPresentationMesh)poEnvironmentalInput.moDataStructure).moAssociatedContent){
				
		 		if( !((clsThingPresentationMesh)poStoredContainer.moDataStructure).contain(oAAEnvironmentIn.moAssociationElementB) ){
					ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResult = searchDS(oAAEnvironmentIn.moAssociationElementB);
					
					if( oSearchResult != null ){
						clsAssociation oAssociation = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(
																								 -1, 
																								 eDataType.ASSOCIATIONATTRIBUTE, 
																								 eDataType.ASSOCIATIONATTRIBUTE.name()  ), 
																								 (clsPrimaryDataStructure)poStoredContainer.moDataStructure, 
																								 (clsPrimaryDataStructure)oSearchResult.get(0).b.moDataStructure); 
						
						poStoredContainer.moAssociatedDataStructures.add(oAssociation);  
					}
				}
		 	}
		}
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
		moPerceptPlusMemories_Output_old = getOutput_old(moPerceptPlusRepressed_Input_old); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 14:23:35
	 *
	 * @param moPerceptPlusRepressed_Input2
	 * @return
	 */
	private ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> getOutput_old(
			         ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_Input) {
		
		ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> oRetVal
								= new ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>>(); 
		
		for(clsPair<clsPrimaryInformation, clsPrimaryInformation>element : poPerceptPlusRepressed_Input){
			oRetVal.add(changeRepressedContent(element)); 
		}
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 14:48:40
	 *
	 * @param element
	 * @return
	 */
	private clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>> changeRepressedContent(
					clsPair<clsPrimaryInformation, clsPrimaryInformation> poInputElement) {
		
		ArrayList<clsPrimaryInformation> oAwareContentList = new ArrayList<clsPrimaryInformation>(); 
		
		if(poInputElement.a != null && poInputElement.b != null){
			oAwareContentList = getAwareContentList(poInputElement); 
			return new clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>(poInputElement.a, poInputElement.b, oAwareContentList); 
		}
		return new clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>(poInputElement.a, poInputElement.b, oAwareContentList); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 15:18:01
	 *
	 * @param poInputElement
	 * @return
	 */
	
	//HashMap<clsPrimaryInformation, clsMutableDouble> oContextResult = moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(mrContextSensitivity);
	
	private ArrayList<clsPrimaryInformation> getAwareContentList(clsPair<clsPrimaryInformation, clsPrimaryInformation> poInputElement) {

		HashMap<clsPrimaryInformation, clsMutableDouble> oCurrentContextMap = this.moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(1); 
		ArrayList <clsPrimaryInformation> oAwareContent = new ArrayList<clsPrimaryInformation>(); 
		eEntityType oEntityType = eEntityType.valueOf(poInputElement.a.moTP.moContent.toString()); 
		eRepressedContentType oRepressedContentType = eRepressedContentType.valueOf(poInputElement.b.moTP.moContent.toString()); 
		
		for(clsPrimaryInformation oContextName : oCurrentContextMap.keySet()){
			String oCurrentContextType = oContextName.moTP.moContent.toString();
			
			oAwareContent.add(this.moEnclosingContainer.moMemory.moAwareContentsStore.getMappedContent(new clsTripple<String, String, String>(oEntityType.toString(), 
							         oCurrentContextType.toString(),oRepressedContentType.toString()))); 
		}
		
		return oAwareContent; 
	 }
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_7(moPerceptPlusMemories_Output_old, moPerceptPlusMemories_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:56:27
	 * 
	 * @see pa.interfaces.send.I2_7_send#send_I2_7(java.util.ArrayList)
	 */
	@Override
	public void send_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output_old,
			  ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output) {
		((I2_7_receive)moEnclosingContainer).receive_I2_7(moPerceptPlusMemories_Output_old, moPerceptPlusMemories_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:39
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
	 * 12.07.2010, 10:46:39
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 12.08.2010, 20:57:42
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> accessKnowledgeBase() {
		return moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.08.2010, 10:15:41
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType, clsDataStructurePA poSearchPattern) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(oReturnType.nBinaryValue, poSearchPattern));
	}
}
