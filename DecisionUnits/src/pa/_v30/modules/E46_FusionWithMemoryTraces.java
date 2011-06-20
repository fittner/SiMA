/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I2_20_receive;
import pa._v30.interfaces.modules.I2_20_send;
import pa._v30.interfaces.modules.I2_5_receive;
import pa._v30.interfaces.modules.I7_7_receive;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsTemplateImage;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.enums.eDataType;

import config.clsBWProperties;

/**
 *
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 * 
 */
public class E46_FusionWithMemoryTraces extends clsModuleBaseKB implements
					I2_5_receive, I7_7_receive, I2_20_send {
	public static final String P_MODULENUMBER = "46";
		
	//HZ Not used up to now 16.03.2011
	//private ArrayList<clsPrimaryDataStructureContainer> moGrantedPerception_IN; 
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_IN; 
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_OUT; 
	
	//New Output
	private clsTemplateImage moPerceivedImage_OUT;
	@SuppressWarnings("unused")
	private ArrayList<clsTemplateImage> moTemplateImages_OUT;

	
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:16:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E46_FusionWithMemoryTraces(String poPrefix, clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
								clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		applyProperties(poPrefix, poProp);	
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);		
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);		
		
		return text;
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
		//moEnvironmentalPerception_OUT = new ArrayList<clsPrimaryDataStructureContainer>(); 
		moEnvironmentalPerception_OUT = retrieveImages(moEnvironmentalPerception_IN);
		
		
		//****** New Data structures Don't delete AW 20110424 *********
		//AW: 2011-04-18, new Data structures
		//Convert Output to a template image
		/*
		moPerceivedImage_OUT = tempConvertInput(moEnvironmentalPerception_OUT);
		//Load indirect template images
		moTemplateImages_OUT = retrieveIndirectTI(moPerceivedImage_OUT);
		
		*/
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
		

	}
	
	/**
	 * retrieves memory matches for environmental input; objects are identified and exchanged by their 
	 * stored correspondance.
	 *
	 * @author zeilinger
	 * 14.03.2011, 14:27:30
	 *
	 */
	private ArrayList<clsPrimaryDataStructureContainer> retrieveImages(ArrayList<clsPrimaryDataStructureContainer> oPerceivedImage_IN) {
		ArrayList<clsPrimaryDataStructureContainer> oRetVal;
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		search(eDataType.TP, oPerceivedImage_IN, oSearchResult ); 
		oRetVal = createImage(oSearchResult);	
		
		return oRetVal;
	}

	
	/**
	 *
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:06:52
	 *
	 */
	private ArrayList<clsPrimaryDataStructureContainer> createImage(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
		
				int nEntryIndex = poSearchResult.indexOf(oEntry); 
				clsPrimaryDataStructureContainer oNewImage;
				clsPrimaryDataStructureContainer oPerceptionEntry =  moEnvironmentalPerception_IN.get(nEntryIndex); 
				
				if(oEntry.size() > 0){
					oNewImage = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry); 
					oNewImage.setMoAssociatedDataStructures(oPerceptionEntry.getMoAssociatedDataStructures()); 
					mergePerceptionAndKnowledge(oNewImage, oPerceptionEntry);
					
					oRetVal.add(oNewImage);
				}
			}
		return oRetVal;
	}
	
	
	
	private ArrayList<clsTemplateImage> retrieveIndirectTI(clsTemplateImage oInput) {
		ArrayList<clsTemplateImage> oRetVal = new ArrayList<clsTemplateImage>();
		
		//Spread activation from input
		
		return oRetVal;
	}
	
	private clsTemplateImage tempConvertInput(ArrayList<clsPrimaryDataStructureContainer> oInput) {
		return clsDataStructureConverter.convertMultiplePDSCtoTI (oInput);
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 17.08.2010, 20:39:09
	 *
	 * @param oSearchResult
	 * @param poEnvironmentalInput
	 */
	private void mergePerceptionAndKnowledge(clsPrimaryDataStructureContainer poNewImage,
												   clsPrimaryDataStructureContainer poPerceptionEntry) {

		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<clsDataStructurePA> oAssociatedElements = new ArrayList<clsDataStructurePA>(); 
		
		if(poNewImage.getMoDataStructure() instanceof clsThingPresentationMesh 
			&& poPerceptionEntry.getMoDataStructure() instanceof clsThingPresentationMesh){

			extractUnknownData(oAssociatedElements, poPerceptionEntry, poNewImage); 
			search(eDataType.UNDEFINED, oAssociatedElements, oSearchResult); 
		 	addAssociations(oSearchResult, poNewImage); 
		 }
	}

	/**
	 *
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
		
		for(clsAssociation oEntry : ((clsThingPresentationMesh)poPerceptionEntry.getMoDataStructure()).getMoAssociatedContent()){
	 		
	 		if( !((clsThingPresentationMesh)poNewImage.getMoDataStructure()).contain(oEntry.getMoAssociationElementB())){
	 			poUnknownData.add(oEntry.getMoAssociationElementB()); 
	 		}
	 	}
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:25:24
	 *
	 * @param oSearchResult
	 * @param poNewImage
	 */
	private void addAssociations(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
						clsPrimaryDataStructureContainer poNewImage) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				clsPrimaryDataStructureContainer oBestMatch = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry); 
				clsAssociation oAssociation = new clsAssociationAttribute(new clsTripple<Integer, eDataType, String>(
							-1, eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.name()), 
							(clsPrimaryDataStructure)poNewImage.getMoDataStructure(), 
							(clsPrimaryDataStructure)oBestMatch.getMoDataStructure());
				poNewImage.getMoAssociatedDataStructures().add(oAssociation);
			}
		}
	}

	/**
	 *
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:08:28
	 *
	 * @param oEntry
	 * @return
	 */
	private clsDataStructureContainer extractBestMatch(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry) {
		
		clsDataStructureContainer oBestMatch = oEntry.get(0).b;; 
		
		return oBestMatch; 
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
		send_I2_20(moEnvironmentalPerception_OUT);
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
		((I2_20_receive)moModuleList.get(37)).receive_I2_20(poEnvironmentalTP);
		putInterfaceData(I2_20_send.class, poEnvironmentalTP);
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
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The thing presentations which represent the perception are associated with previously experienced and stored memory traces. This has two impacts: more information is added to the current perception and the perception is completed with previously stored information. Thus, if only parts of a well known object are visible, the other parts are added from memory. Next to information stored in memory, memory traces processed in secondary processes from the last step can be used.";
	}		
}
