/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v30.I2_20_receive;
import pa.interfaces.receive._v30.I2_5_receive;
import pa.interfaces.receive._v30.I7_7_receive;
import pa.interfaces.send._v30.I2_20_send;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;

import config.clsBWProperties;

/**
 * DOCUMENT (HINTERLEITNER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 * 
 */
public class E46_FusionWithMemoryTraces extends clsModuleBase implements
					I2_5_receive, I7_7_receive, I2_20_send, itfKnowledgeBaseAccess {
	public static final String P_MODULENUMBER = "46";
	
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler; 
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern; 
	
	//HZ Not used up to now 16.03.2011
	//private ArrayList<clsPrimaryDataStructureContainer> moGrantedPerception_IN; 
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_IN; 
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_OUT; 
	
	/**
	 * DOCUMENT (HINTERLEITNER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:16:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E46_FusionWithMemoryTraces(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList) throws Exception {
		super(poPrefix, poProp, poModuleList);
		applyProperties(poPrefix, poProp);	
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
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (HINTERLEITNER) - Auto-generated method stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		moEnvironmentalPerception_OUT = new ArrayList<clsPrimaryDataStructureContainer>(); 
		moSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 
		
		//HZ: The method getEnvironmentalRepresentation retrieves memory matches for
		//	environmental input; objects are identified and exchanged by their 
		//	stored correspondance.
		retrieveImages(); 

//		clsPrimaryDataStructureContainer oEnvironmentalInput = getEnvironmentalRepresentation(oEntry.a); 		
//		
//			if(oEntry.a != null && oEntry.b != null){
//				ArrayList<clsDriveMesh> oDriveContent = getAssociatedContent(oEnvironmentalInput, oEntry.b);
//				oRetVal.add(new clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>(oEnvironmentalInput, oEntry.b, oDriveContent));
//			}
//			else{
//				oRetVal.add(new clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>(oEnvironmentalInput, null, new ArrayList<clsDriveMesh>()));
//			}
//		}
		}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 14:27:30
	 *
	 */
	private void retrieveImages() {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult_Image = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		createSearchPattern(eDataType.UNDEFINED, moEnvironmentalPerception_IN); 
		accessKnowledgeBase(oSearchResult_Image);
		createImage(oSearchResult_Image); 	
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:06:52
	 *
	 */
	private void createImage(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			
			if(oEntry.size() > 0){
				int nEntryIndex = poSearchResult.indexOf(oEntry); 
				clsPrimaryDataStructureContainer oNewImage;
				clsPrimaryDataStructureContainer oPerceptionEntry =  moEnvironmentalPerception_IN.get(nEntryIndex); 
					
				oNewImage = (clsPrimaryDataStructureContainer)getBestMatch(oEntry); 
				oNewImage.setMoAssociatedDataStructures(oPerceptionEntry.getMoAssociatedDataStructures()); 
				mergePerceptionAndKnowledge(oNewImage, oPerceptionEntry);
				
				moEnvironmentalPerception_OUT.add(oNewImage); 
			}
		}
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
	private void mergePerceptionAndKnowledge(clsPrimaryDataStructureContainer poNewImage,
												   clsPrimaryDataStructureContainer poPerceptionEntry) {

		if(poNewImage.getMoDataStructure() instanceof clsThingPresentationMesh 
			&& poPerceptionEntry.getMoDataStructure() instanceof clsThingPresentationMesh){

			ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
 								new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
			ArrayList<clsDataStructurePA> oUnknownData = new ArrayList<clsDataStructurePA>(); 
			
		 	extractUnknownData(oUnknownData, poPerceptionEntry, poNewImage); 
		 	createSearchPattern(eDataType.UNDEFINED, oUnknownData);
 			accessKnowledgeBase(oSearchResult);
 			addAssociations(oSearchResult, poNewImage); 
		 }
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:27:04
	 *
	 * @param oUnknownData
	 * @param poPerceptionEntry
	 * @param poNewImage 
	 */
	private void extractUnknownData(ArrayList<clsDataStructurePA> poUnknownData,
			clsPrimaryDataStructureContainer poPerceptionEntry, 
			clsPrimaryDataStructureContainer poNewImage) {
		
		for(clsAssociation oEntry : ((clsThingPresentationMesh)poPerceptionEntry.getMoDataStructure()).moAssociatedContent){
	 		
	 		if( !((clsThingPresentationMesh)poNewImage.getMoDataStructure()).contain(oEntry.getMoAssociationElementB())){
	 			poUnknownData.add(oEntry.getMoAssociationElementB()); 
	 		}
	 	}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:25:24
	 *
	 * @param oSearchResult
	 * @param poNewImage
	 */
	private void addAssociations(
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
			clsPrimaryDataStructureContainer poNewImage) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				clsDataStructureContainer oBestMatch = getBestMatch(oEntry); 
				clsAssociation oAssociation = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(
															-1, 
															eDataType.ASSOCIATIONATTRIBUTE, 
															eDataType.ASSOCIATIONATTRIBUTE.name()  ), 
															(clsPrimaryDataStructure)poNewImage.getMoDataStructure(), 
															(clsPrimaryDataStructure)oBestMatch.getMoDataStructure()); 
				
				poNewImage.getMoAssociatedDataStructures().add(oAssociation); 
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:08:28
	 *
	 * @param oEntry
	 * @return
	 */
	private clsDataStructureContainer getBestMatch(
			ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry) {
		
		return oEntry.get(0).b;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_20(new ArrayList<clsPrimaryDataStructureContainer>());
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v30.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
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
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.send._v30.I2_20_send#receive_I2_20(java.util.ArrayList)
	 */
	@Override
	public void send_I2_20(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_20_receive)moModuleList.get(37)).receive_I2_20(moEnvironmentalPerception_OUT);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v30.I7_7_receive#receive_I7_7(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_7(
		ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		//HZ Not used up to now 16.03.2011 
		//moGrantedPerception_IN = poGrantedPerception; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v30.I2_5_receive#receive_I2_5(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_5(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception) {
		
		moEnvironmentalPerception_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poEnvironmentalPerception); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:05:28
	 *
	 */
	private <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList) {
		moSearchPattern.clear();
		
		for (E oEntry : poList){
			if(oEntry instanceof clsDataStructurePA){
				addToSearchPattern(poDataType, (clsDataStructurePA)oEntry);
			}
			else if (oEntry instanceof clsPrimaryDataStructureContainer){
				addToSearchPattern(poDataType, ((clsPrimaryDataStructureContainer)oEntry).getMoDataStructure());
			}
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.03.2011, 22:56:55
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType, clsDataStructurePA poEntry) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(oReturnType.nBinaryValue, poEntry)); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.03.2011, 22:34:44
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(pa.tools.clsPair)
	 */
	@Override
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(moSearchPattern));
	}
}
