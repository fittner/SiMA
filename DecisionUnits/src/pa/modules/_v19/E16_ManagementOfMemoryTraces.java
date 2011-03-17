/**
 * E16_ManagmentOfMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 */
package pa.modules._v19;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v19.I2_6_receive;
import pa.interfaces.receive._v19.I2_7_receive;
import pa.interfaces.send._v19.I2_7_send;
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
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 * 
 */
public class E16_ManagementOfMemoryTraces extends clsModuleBase implements I2_6_receive, I2_7_send, itfKnowledgeBaseAccess {

	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moPerceptPlusRepressed_Input;
	public ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>> moPerceptPlusMemories_Output;
	public clsDataStructureContainer moRetrievedAssociatedDataStructures; 
	public ArrayList<clsPair<Double, clsDataStructureContainer>> moRetrievedEnvironmentalMatches; 
	/**
	 * 
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
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		
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
		//process_oldDT(); 
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
			ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();  
			ArrayList<clsDriveMesh> oRetVal = new ArrayList<clsDriveMesh>();
			
			moSearchPattern.clear(); 
			addToSearchPattern(eDataType.DM, oEnvironmentalInput.getMoDataStructure());
			//It is for sure that the search retrieves a result as the search parameter is 
			//an already found object => hence the retrieved ArrayList is not empty. 
			//In addition the search retrieves exactly one result as the search parameter 
			//obeys of a data-structure identification number. 
			//=> Hence the container can be read out directly below; 
			accessKnowledgeBase(oSearchResult); 
			
			try{
				moRetrievedAssociatedDataStructures = oSearchResult.get(0).get(0).b;
							
				for(clsAssociation oAssociation : moRetrievedAssociatedDataStructures.getMoAssociatedDataStructures()){
								clsDriveMesh oFoundDM = ((clsAssociationDriveMesh)oAssociation).getDM(); 
								
								if(oRepressedContent.getMoContentType().equals(oFoundDM.getMoContentType())){
									oRetVal.add(oFoundDM); 
								}
				}
			
			}catch(java.lang.IndexOutOfBoundsException e){/*HZ: Must be refactored*/}
										
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
		moRetrievedEnvironmentalMatches = searchDS(poEnvInput.getMoDataStructure()); 
		
		//Only the best match is used for the further processing; this explains the 
		//get(0) in the statement below.
		if(moRetrievedEnvironmentalMatches.size() > 0){
			oCon = (clsPrimaryDataStructureContainer)moRetrievedEnvironmentalMatches.get(0).b; 
			oCon.setMoAssociatedDataStructures(poEnvInput.getMoAssociatedDataStructures()); 
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

		ArrayList<clsPair<Double, clsDataStructureContainer>> oRetVal = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		moSearchPattern.clear(); 
		addToSearchPattern(eDataType.UNDEFINED, poDS);
		//The search pattern exists out of one entry => the returned hashmap contains only 
		//ONE entry that has the key 0. 
		accessKnowledgeBase(oSearchResult); 
		
		if(oSearchResult.size() > 0){
			oRetVal = oSearchResult.get(0); 
		}
			
		return oRetVal; 
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
		
		if(poStoredContainer.getMoDataStructure()instanceof clsThingPresentationMesh && poEnvironmentalInput.getMoDataStructure() instanceof clsThingPresentationMesh){

		 	for(clsAssociation oAAEnvironmentIn : ((clsThingPresentationMesh)poEnvironmentalInput.getMoDataStructure()).moAssociatedContent){
				
		 		if( !((clsThingPresentationMesh)poStoredContainer.getMoDataStructure()).contain(oAAEnvironmentIn.getMoAssociationElementB()) ){
					ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResult = searchDS(oAAEnvironmentIn.getMoAssociationElementB());
					
					if( oSearchResult.size() > 0){
						clsAssociation oAssociation = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(
																								 -1, 
																								 eDataType.ASSOCIATIONATTRIBUTE, 
																								 eDataType.ASSOCIATIONATTRIBUTE.name()  ), 
																								 (clsPrimaryDataStructure)poStoredContainer.getMoDataStructure(), 
																								 (clsPrimaryDataStructure)oSearchResult.get(0).b.getMoDataStructure()); 
						
						poStoredContainer.getMoAssociatedDataStructures().add(oAssociation);  
					}
				}
		 	}
		}
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
		send_I2_7(moPerceptPlusMemories_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:56:27
	 * 
	 * @see pa.interfaces.send.I2_7_send#send_I2_7(java.util.ArrayList)
	 */
	@Override
	public void send_I2_7(ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output) {
		((I2_7_receive)moEnclosingContainer).receive_I2_7(moPerceptPlusMemories_Output);
		
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
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		poSearchResult.addAll(moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern));
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
