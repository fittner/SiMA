/**
 * E45_LibidoDischarge.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.storage.clsLibidoBuffer;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.modules.I5_9_receive;
import pa._v38.interfaces.modules.I5_9_send;
import pa._v38.interfaces.modules.I5_8_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.enums.eDataType;

import config.clsBWProperties;

/**
 * F45 communicates with F41 via the libido buffer. Incoming perceptions are compared with memory to determine 
 * whether they qualify for libido discharge and thus for pleasure gain. If so, the value of the libido buffer 
 * is reduced (tension reduction is pleasure gain). The pleasure gain is forwarded to F18 as an additional value 
 * for the composition of the quota of affect. 
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 * 
 */
public class F45_LibidoDischarge extends clsModuleBaseKB implements itfInspectorGenericTimeChart, I5_8_receive, I5_9_send {
	public static final String P_MODULENUMBER = "45";
	
	/** Perceived Image in; @since 14.07.2011 14:02:10 */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	/** Associated memories in; @since 14.07.2011 14:02:10 */
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	/** Perceived Image in, enriched with LIBIDO DMs; @since 14.07.2011 14:02:10 */
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	/** Associated memories out, which are enriched with LIBIDO DM; @since 14.07.2011 14:02:10 */
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	private ArrayList<clsPair<String, Double>> moLibidioDischargeCandidates; //pair of IDENTIFIER and qualification from 0 to 1
	
	/* Module parameters */
	/** DOCUMENT (wendt) - insert description; @since 14.07.2011 14:22:14 */
	private double mrMatchThreshold = 0.6;
	/** DOCUMENT (wendt) - insert description; @since 14.07.2011 14:22:39 */
	private double mrAvailablePsychicEnergy = 1.0;
	
	// Other variables
	private double mrDischargePiece = 0.2; //amount of the sotred libido which is going to be withtracted max. (see formula below)
	private double mrAvailableLibido;
	private double mrLibidoReducedBy;
	private clsLibidoBuffer moLibidoBuffer;	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:30:00
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F45_LibidoDischarge(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
			clsLibidoBuffer poLibidoBuffer,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		moLibidoBuffer = poLibidoBuffer;
		
		applyProperties(poPrefix, poProp);	
		
		//fillLibidioDischargeCandidates();	//FIXME AW: REPLACE
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
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		text += toText.valueToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
		
		text += toText.listToTEXT("moLibidioDischargeCandidates", moLibidioDischargeCandidates);
		text += toText.valueToTEXT("mrDischargePiece", mrDischargePiece);		
		text += toText.valueToTEXT("mrAvailableLibido", mrAvailableLibido);
		text += toText.valueToTEXT("mrReducedLibido", mrLibidoReducedBy);
		text += toText.valueToTEXT("moLibidoBuffer", moLibidoBuffer);
		
		return text;
	}	
	
	//FIXME: REPLACE
	/*private void fillLibidioDischargeCandidates() {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResultDM = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResultObjects = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<clsDataStructurePA> oDriveMeshList = new ArrayList<clsDataStructurePA>(); 
		ArrayList<clsDataStructurePA> oCandidateList = new ArrayList<clsDataStructurePA>();

		clsThingPresentation oDriveContentPattern = clsDataStructureGenerator.generateTP(new clsPair<String, Object>("LIFE", "PLEASURE")); 
		clsDriveMesh oDriveMeshPattern = clsDataStructureGenerator.generateDM(
				new clsTripple<String, ArrayList<clsThingPresentation>, Object>
							("LIBIDO", 
							 new ArrayList<clsThingPresentation>(Arrays.asList(oDriveContentPattern)), 
							 "LIBIDO"));
				
		search(eDataType.DM, new ArrayList<clsDataStructurePA>(Arrays.asList(oDriveMeshPattern)), oSearchResultDM); 
		extractDriveMatches(oSearchResultDM, oDriveMeshList); 
		
		search(eDataType.TPM, oDriveMeshList, oSearchResultObjects); 
		extractCandidateMatches(oSearchResultObjects, oCandidateList);
		
		moLibidioDischargeCandidates = new ArrayList<clsPair<String,Double>>();
		
		for (clsDataStructurePA oCandidate : oCandidateList){
			moLibidioDischargeCandidates.add( new clsPair<String, Double>(
												((clsThingPresentationMesh)oCandidate).getMoContent(),
												((clsDriveMesh)oDriveMeshList.get(oCandidateList.indexOf(oCandidate))).getMrPleasure()));
		}
		
		//FIXME (Zeilinger): bitte irgendwie aus dem protege auslesen
//		moLibidioDischargeCandidates = new ArrayList<clsPair<String,Double>>();
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("CAKE", 1.0) );
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("CARROT", 0.5) );
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("BUBBLE", 0.1) );
	}*/
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.04.2011, 10:41:34
	 *
	 * @param oSearchResultDM
	 * @param oDriveMeshList 
	 */
	/*private void extractDriveMatches(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult, 
									ArrayList<clsDataStructurePA> poDriveMatchList) {
				
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
				for (clsPair<Double, clsDataStructureContainer> oMatch : oEntry){
					poDriveMatchList.add(oMatch.b.getMoDataStructure());
				}
		}
	}*/
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.04.2011, 11:29:37
	 *
	 * @param oSearchResultObjects
	 * @param oCandidateList
	 */
	/*private void extractCandidateMatches(
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResultObjects,
			ArrayList<clsDataStructurePA> poCandidateList) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResultObjects){
			for (clsPair<Double, clsDataStructureContainer> oMatch : oEntry){
				for(clsAssociation oAssociation : oMatch.b.getMoAssociatedDataStructures()){
					poCandidateList.add(oAssociation.getMoAssociationElementB());
				}
			}
		}
		
	}*/
	
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
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		//Get available amount of free libido 
		mrAvailableLibido = moLibidoBuffer.send_D1_4();
		
		//Clone input structure and make modification directly on the output
		moEnvironmentalPerception_OUT = (clsPrimaryDataStructureContainer) moEnvironmentalPerception_IN.clone();
		
		mrLibidoReducedBy = setImageLibido(moEnvironmentalPerception_OUT, 1.0);
		
		//Pass the memories forward. Later, they are enriched with repressed content
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
		
		moLibidoBuffer.receive_D1_3(mrLibidoReducedBy);

		
		//FIXME: Hack remove
		//double rChunk = mrAvailableLibido * mrDischargePiece; //each match can reduce the libido by a maximum of rChunk.
		//FIXME: if more than ten piece fit 100% ... the last pieces will get nothing ...
		
		/*mrLibidoReducedBy = 0;
				
		//moMergedPrimaryInformation_Snd = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		//FIXME (ZEILINGER): das ganze zeug geht noch nicht so ganz .. irgendwie ... plz - wobei. das kann ich morgen auch noch debuggen.
		for (clsPair<String,Double> oCandidate:moLibidioDischargeCandidates) {
			String oSearchPattern = oCandidate.a;
			Double rFactor = oCandidate.b;
			double rReduction = rChunk * rFactor;
			
			//for (clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oData:moMergedPrimaryInformation_Rcv) {
			
			for (clsAssociation oTIAss : ((clsTemplateImage)moEnvironmentalPerception_IN.getMoDataStructure()).getMoAssociatedContent()) {
				//clsPrimaryDataStructureContainer oPDSC = oData.a;
				//clsDataStructurePA oDS = oPDSC.getMoDataStructure(); 
				clsDataStructurePA oDS = oTIAss.getLeafElement();
				
				if (oDS instanceof clsThingPresentationMesh && ((clsThingPresentationMesh)oDS).getMoContent().contains(oSearchPattern)) {
					clsDriveMesh oDrive = createDriveMesh("LIBIDO", "LIBIDO");
					
					double r = rReduction;
					
					//FIXME (Zeilinger): dirty hack!!!! by TD : *5 is to be removed!
					//the problem is that later on, the pleasure value is converted into intervals with steplength 
					//of about 0.4. usually, libido provide pleasure gain of 0.2 max -> only in rare occasions, libido
					//gained has influence on decission making!
					r *= 5;
					
					if (rReduction > 1) {rReduction = 1;}
					if (rReduction < -1) {rReduction = -1;}

					oDrive.setPleasure(r);
					
					mrLibidoReducedBy += rReduction;
					
					//clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oResult = 
					//	new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oPDSC, oDrive);
					//moMergedPrimaryInformation_Snd.add(oResult);
					
					clsTripple<Integer, eDataType, String> oIdentifyer = new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, eDataType.ASSOCIATIONDM.toString());
					clsAssociationDriveMesh oDriveAss = new clsAssociationDriveMesh(oIdentifyer, oDrive, (clsPrimaryDataStructure)oDS);
					
					moEnvironmentalPerception_OUT.getMoAssociatedDataStructures().add(oDriveAss);
				}
			}
		}
		
		moLibidoBuffer.receive_D1_3(mrLibidoReducedBy);*/
	
	}
	
	/*private clsDriveMesh createDriveMesh(String poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<String, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v38.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTripple<String, Object, Object>(poContentType, oContent, poContext)
				);
		
		return oRetVal;
	}*/
	
	private double setImageLibido(clsPrimaryDataStructureContainer poInput, double prLibidoReduceRate) {
		//Search for matches for the input image
		double rReducedValue = 0.0;
		ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResultContainer = new ArrayList<clsPair<Double,clsDataStructureContainer>>();
		
		//Find matching images for the input image
		//FIXME AW: Set Threshold for matching = 0.9
		
		searchContainer(poInput, oSearchResultContainer, "LIBIDOIMAGE");
		
		// Here, spread activation for Libido shall be placed.
		//searchContainer(oPerceptionInput, oSearchResultContainer);
		// 2. In spread activation, only very little psychic energy is available
		// 3. if an object match > 60% in an image is found, the libido-DM in the image is added to the input image 
		
		//Get the match with the highest match
		//FIXME AW: Check if the result is correctly sorted
		clsPrimaryDataStructureContainer oBestCompareContainer = getBestMatch(oSearchResultContainer);
		//Get a list of corresponding objects for the libido DMs in the input image
		ArrayList<clsPair<clsDataStructurePA, clsAssociation>> oLibidoDM = getSpecificAssociatedContent(oBestCompareContainer, poInput, eDataType.DM, "LIBIDO");
		
		rReducedValue = addDriveMeshes(new clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPair<clsDataStructurePA, clsAssociation>>>(poInput, oLibidoDM), prLibidoReduceRate);
		
		return rReducedValue;
	}
	
	private double addDriveMeshes (clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPair<clsDataStructurePA, clsAssociation>>> poAssignment, double prLibidoReduceRate) {
		//For each Pair, assign the drive meshes
		double rTotalReduce = 0.0;
		for (clsPair<clsDataStructurePA, clsAssociation> oAssignmentElement : poAssignment.b) {
			clsDriveMesh oDM = (clsDriveMesh) oAssignmentElement.b.getLeafElement();
			//With this amount the libido puffer shall be reduced
			double rDMReduce = oDM.getMrPleasure() * prLibidoReduceRate;
			//if the total reduction of libido is smaller than the buffer, then the DM can be assigned
			//For perception, the reduce rate shall be 1.0 and for associated content, only 0.5
			if (rTotalReduce + rDMReduce <= mrAvailableLibido) {
				try {
					//Clone the original DM
					clsDriveMesh oNewDriveMesh = (clsDriveMesh) oDM.clone();
					//Set new Pleasurevalue, which depends on the reducevalue
					oNewDriveMesh.setMrPleasure(rDMReduce);
					//Create new identifier
					clsTripple<Integer, eDataType, String> oIdentifyer = new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, eDataType.ASSOCIATIONDM.toString());
					//Create new association drivemesh but with the new root element
					clsAssociationDriveMesh oDriveAss = new clsAssociationDriveMesh(oIdentifyer, oNewDriveMesh, (clsPrimaryDataStructure)oAssignmentElement.a);
					//Add the assocation to the input container
					poAssignment.a.addMoAssociatedDataStructure(oDriveAss);
					rTotalReduce += rDMReduce;
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		return rTotalReduce;
	}
	
	private clsPrimaryDataStructureContainer getBestMatch (ArrayList<clsPair<Double,clsDataStructureContainer>> poInput) {
		//Get the first structure from the sorted list
		clsPrimaryDataStructureContainer oRetVal;
		if (poInput.size()!=0) {
			oRetVal = (clsPrimaryDataStructureContainer) poInput.get(0).b;
		} else {
			oRetVal = null;
		}
		
		return oRetVal;
	}
	
	private ArrayList<clsPair<clsDataStructurePA, clsAssociation>> getSpecificAssociatedContent(clsPrimaryDataStructureContainer poFromImage, clsPrimaryDataStructureContainer poToImage, eDataType poDataType, String poContentType) {
		ArrayList<clsPair<clsDataStructurePA, clsAssociation>> oRetVal = new ArrayList<clsPair<clsDataStructurePA, clsAssociation>>();
		clsPair<clsDataStructurePA, clsAssociation> oMatch = null;
		//Get the data structure, which could also have DMs
		//Only DM and TP can be copied
		
		if ((poFromImage != null) && (poToImage != null)) {	//Catch the problem if the data structure would be null
			//Get Target structures
			if ((poToImage.getMoDataStructure() instanceof clsTemplateImage)==false) {
				try {
					throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: Only data structures consisting of clsTemplateImage canbbe used.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			clsTemplateImage oToImageObject = (clsTemplateImage)poToImage.getMoDataStructure();
			//TODO AW: Only Template Images, which contain TPMs are concerned, expand to other data types and nested template images
			
			//For each DM or TP in the associated structures in the SourceContainer
			for (clsAssociation oAssInFromImage : poFromImage.getMoAssociatedDataStructures()) {	//The association in the source file. The root element shall be found in that target file
				if (poDataType == eDataType.DM) {
					if (oAssInFromImage instanceof clsAssociationDriveMesh) {
						if (poContentType != null) {	//Add only that content type of that structure type
							if (oAssInFromImage.getLeafElement().getMoContentType() == poContentType) {
								oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
							}
						} else {	//Add all
							oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
						}
					}
				} else if (poDataType == eDataType.TP) {
					if (oAssInFromImage instanceof clsAssociationAttribute) {
						if (poContentType != null) {	//Add only that content type of that structure type
							if (oAssInFromImage.getLeafElement().getMoContentType().toString() == "poContentType") {
								oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
							}
						} else {	//Add all
							oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
						}
					}
				} else {
					try {
						throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: A not allowed datatype was used");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (oMatch != null) {
					oRetVal.add(oMatch);
				}
			}
		}
		
		return oRetVal;
	}
	
	private clsPair<clsDataStructurePA, clsAssociation> getMatchInDataStructure (clsAssociation poSourceAssociation, clsTemplateImage poTargetDataStructure) {
		clsPair<clsDataStructurePA, clsAssociation> oRetVal = null;
		
		//Get root element
		clsDataStructurePA oCompareRootElement = poSourceAssociation.getRootElement();
		//Find the root element in the target image. Only an exact match is count
		//1. Check if the root element is the same as the data structure in the target container
		if ((oCompareRootElement.getMoDS_ID() == poTargetDataStructure.getMoDS_ID() && (oCompareRootElement.getMoDS_ID() > 0))) {
			oRetVal = new clsPair<clsDataStructurePA, clsAssociation>(poTargetDataStructure, poSourceAssociation);
		} else {
			//2. Check if the root element can be found in the associated data structures
			for (clsAssociation oAssToImage : poTargetDataStructure.getMoAssociatedContent()) {
				if ((oCompareRootElement.getMoDS_ID() == oAssToImage.getLeafElement().getMoDS_ID() && (oCompareRootElement.getMoDS_ID() > 0))) {
					oRetVal = new clsPair<clsDataStructurePA, clsAssociation>(oAssToImage.getLeafElement(), poSourceAssociation);
					break;
				}
			}
		}
		
		return oRetVal;
	}
	

	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_9(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:31:25
	 * 
	 * @see pa.interfaces.receive._v38.I2_8_receive#receive_I2_8(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_8(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		//moMergedPrimaryInformation_Rcv = poMergedPrimaryInformation;
		moEnvironmentalPerception_IN = poMergedPrimaryInformation;
		moAssociatedMemories_IN = poAssociatedMemories;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:32:47
	 * 
	 * @see pa.interfaces.send._v38.I2_16_send#send_I2_16(java.util.ArrayList)
	 */
	@Override
	public void send_I5_9(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		((I5_9_receive)moModuleList.get(18)).receive_I5_9(poMergedPrimaryInformation, poAssociatedMemories);
		
		putInterfaceData(I5_9_send.class, poMergedPrimaryInformation, poAssociatedMemories);
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
		moDescription = "F45 communicates with F41 via the libido buffer. Incoming perceptions are compared with memory to determine whether they qualify for libido discharge and thus for pleasure gain. If so, the value of the libido buffer is reduced (tension reduction is pleasure gain). The pleasure gain is forwarded to F18 as an additional value for the composition of the quota of affect.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChart#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		
		oValues.add(mrAvailableLibido);
		oValues.add(mrLibidoReducedBy);
		
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChart#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		return new ArrayList<String>(Arrays.asList("Available","Reduction"));
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Libido";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Libido Discharge";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 2;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.5;
	}	
}
