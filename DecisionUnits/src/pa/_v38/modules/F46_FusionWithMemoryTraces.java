/**
 * E46_FusionWithMemoryTraces.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.tools.clsGlobalFunctions;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_6_receive;
import pa._v38.interfaces.modules.I5_6_send;
import pa._v38.interfaces.modules.I2_6_receive;
import pa._v38.interfaces.modules.I5_19_receive;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;

import config.clsBWProperties;

/**
 * DOCUMENT (HINTERLEITNER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:16:45
 * 
 */
public class F46_FusionWithMemoryTraces extends clsModuleBaseKB implements
					I2_6_receive, I5_19_receive, I5_6_send {
	public static final String P_MODULENUMBER = "46";
	
	/* Inputs */
	private clsPrimaryDataStructureContainer moReturnedTPMemory_IN; 
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalPerception_IN;
	
	/* Output */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;

	/* Module-Parameters */
	
	/**
	 * DOCUMENT (HINTERLEITNER) - Association of TPMs (TP + Emotion, fantasies) with thing presentations raw data (from external perception). In a first step these are attached with a value to get a meaning. Secondly the fantasies are added from the TPMs to the thing presentations
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:16:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F46_FusionWithMemoryTraces(String poPrefix, clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
								clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		applyProperties(poPrefix, poProp);	
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);		
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
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		/**
		 * DOCUMENT (WENDT) - insert description
		 *
		 * @since 20110625
		 *
		 * ${tags}
		 * 
		 * 
		 */
		
		//Variables
		clsPrimaryDataStructureContainer oEnvPerceptionNoDM;
		ArrayList<clsPrimaryDataStructureContainer> oContainerWithTypes;
		ArrayList<clsPrimaryDataStructureContainer> oNewInstancesFromType;
		
		/* Construction of perceived images*/
		/* Assign objects from storage to perception */
		
		oContainerWithTypes = retrieveImages(moEnvironmentalPerception_IN);
		oNewInstancesFromType = createInstanceFromType(oContainerWithTypes);
		oEnvPerceptionNoDM = clsDataStructureConverter.convertTPMContToTICont(oNewInstancesFromType);
		/* Assign drive meshes and adapt categories */
		moEnvironmentalPerception_OUT = oEnvPerceptionNoDM;	//The output is a perceived image
		
		/* Perception - Activation of associated memories */
		moAssociatedMemories_OUT = retrieveActivatedMemories(moEnvironmentalPerception_OUT, moReturnedTPMemory_IN);
		

		ArrayList<clsPrimaryDataStructureContainer> oContainerList = new ArrayList<clsPrimaryDataStructureContainer>(); 
 		//oContainerList = clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN); 
		
		assignDriveMeshes(oContainerList);
		/* The context of a certain drive or object is loaded, in the case of CAKE, it is NOURISH. If the drive 
		 * NOURISH is found in the objects the categories (anal, oral...) is multiplied with a category factor <= 1
		 * In case of a 100% match, the factor is 1.0.
		 */
		
	    addValues(oContainerList);	
		//****** New Data structures Don't delete AW 20110424 *********
		//UNIT-Tests for the converters
		//clsPrimaryDataStructureContainer oTest = clsDataStructureConverter.convertTPMContToTICont(moEnvironmentalPerception_OUT);
		//ArrayList<clsPrimaryDataStructureContainer> oTest2 = clsDataStructureConverter.convertTIContToTPMCont(oTest);
		/* Post-Processing */
		//Function: Update association weights after activation (first step to individual mind) 
		
		attachFantasies(oContainerList);
		//Fantasiertes wird an die TP (Sachvorstellungen) angehängt
		
	}
	
	

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 28.06.2011 14:32:48
	 *
	 * @param oContainerList
	 */
	private void attachFantasies(
			ArrayList<clsPrimaryDataStructureContainer> oContainerList) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 28.06.2011 14:32:45
	 *
	 * @param oContainerList
	 */
	private void addValues(
			ArrayList<clsPrimaryDataStructureContainer> oContainerList) {
		// TODO (hinterleitner) - Auto-generated method stub
		
	}
	
	/**
	 * DOCUMENT (hinterleitner) - insert description
	 *
	 * @since 28.06.2011 14:18:54
	 *
	 * @param oContainerList
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (HINTERLEITNER) - Auto-generated method stub
		}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}
	
	/**
	 * DOCUMENT (zeilinger) - retrieves memory matches for environmental input; objects are identified and exchanged by their 
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
	
	private ArrayList<clsPrimaryDataStructureContainer> retrieveActivatedMemories(clsPrimaryDataStructureContainer oPerceptionInput, 
			clsPrimaryDataStructureContainer oReturnedMemory) {
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		boolean blUsePerception = true;
		
		//Associated memories
		//Decide which image will be the input for spread activation
		if (oReturnedMemory!=null) {
			if (clsGlobalFunctions.calculateAbsoluteAffect(oPerceptionInput) < clsGlobalFunctions.calculateAbsoluteAffect(oReturnedMemory)) {
				blUsePerception = false;
			}
		}
			
		if (blUsePerception==true) {
			//Use perceived image as input of spread activation
			//TODO: Dummy for spread activation
			clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object> oContent = new clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object>("DummyPerceived", new ArrayList<clsPhysicalRepresentation>(), "DummyPerceived");
			oRetVal.add(new clsPrimaryDataStructureContainer(clsDataStructureGenerator.generateTI(oContent), new ArrayList<clsAssociation>()));
		} else {
			//Use action-plan image as input of spread activation
			//TODO: Dummy for spread activation
			clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object> oContent = new clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object>("DummyMemory", new ArrayList<clsPhysicalRepresentation>(), "DummyMemory");
			oRetVal.add(new clsPrimaryDataStructureContainer(clsDataStructureGenerator.generateTI(oContent), new ArrayList<clsAssociation>()));
		}
		
		return oRetVal;
	}

	
	/**
	 * DOCUMENT (zeilinger) - insert description
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
	
	
	//AW 2011-05-19 added new function
	/*private ArrayList<clsTemplateImage> retrieveIndirectTI(clsTemplateImage oInput) {
		ArrayList<clsTemplateImage> oRetVal = new ArrayList<clsTemplateImage>();
		
		//Spread activation from input
		
		return oRetVal;
	}
	
	//AW 2011-05-19 added new function
	private clsTemplateImage tempConvertInput(ArrayList<clsPrimaryDataStructureContainer> oInput) {
		return clsDataStructureConverter.convertMultiplePDSCtoTI (oInput);
	}*/

	
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
		
		for(clsAssociation oEntry : ((clsThingPresentationMesh)poPerceptionEntry.getMoDataStructure()).getMoAssociatedContent()){
	 		
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
	 * DOCUMENT (zeilinger) - insert description
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
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_6(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:16:45
	 * 
	 * @see pa.modules._v38.clsModuleBase#setProcessType()
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
	 * @see pa.modules._v38.clsModuleBase#setPsychicInstances()
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
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
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
	 * @see pa.interfaces.send._v38.I2_20_send#receive_I2_20(java.util.ArrayList)
	 */
	@Override
	public void send_I5_6(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		/* The inputs and outputs can be changed if the following parameters are changed:
		 * clsModuleBase.deepcopy
		 * this function
		 * the receive function in the following module
		 * I5_6_send.java
		 * I5_6_receive.java
		 */
		//Give output to input of F37
		((I5_6_receive)moModuleList.get(37)).receive_I5_6(poEnvironmentalTP, poAssociatedMemories);
		putInterfaceData(I5_6_send.class, poEnvironmentalTP, poAssociatedMemories);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v38.I7_7_receive#receive_I7_7(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_19(clsPrimaryDataStructureContainer poReturnedMemory) {
		moReturnedTPMemory_IN = (clsPrimaryDataStructureContainer)deepCopy(poReturnedMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:20:28
	 * 
	 * @see pa.interfaces.receive._v38.I2_5_receive#receive_I2_5(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_6(
			ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception) {
		
		moEnvironmentalPerception_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poEnvironmentalPerception); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = " Association of TPMs (TP + Emotion, fantasies) with thing presentations raw data (from external perception). In a first step these are attached with a value to get a meaning. Secondly the fantasies are added from the TPMs to the thing presentations";
	}		
}
