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
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.modules.I5_9_receive;
import pa._v38.interfaces.modules.I5_9_send;
import pa._v38.interfaces.modules.I5_8_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.informationrepresentation.modules.clsDataStructureComparison;

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
	
	//private ArrayList<clsPair<String, Double>> moLibidioDischargeCandidates; //pair of IDENTIFIER and qualification from 0 to 1
	
	/* Module parameters */
	/** Threshold for the matching process of images */
	// TODO AW: This function is not in use yet, but will be very soon
	private double mrMatchThreshold = 0.6;
	
	// Other variables
	//private double mrDischargePiece = 0.2; //amount of the sotred libido which is going to be withtracted max. (see formula below)
	/** Available Libido, double */
	private double mrAvailableLibido;
	/** The amount of libido, of which the libido buffer is reduced by */
	private double mrLibidoReducedBy;
	/** instance of libidobuffer */
	private clsLibidoBuffer moLibidoBuffer;	
	/**
	 * Constructor of the libido buffer. Here the libido buffer is assigned 
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
		
		//fillLibidioDischargeCandidates();
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
		
		text += toText.valueToTEXT("mrMatchThreshold", mrMatchThreshold);
		
		text += toText.valueToTEXT("mrAvailableLibido", mrAvailableLibido);
		text += toText.valueToTEXT("mrReducedLibido", mrLibidoReducedBy);
		text += toText.valueToTEXT("moLibidoBuffer", moLibidoBuffer);
		
		return text;
	}	
	
	//TODO AW: Remove outcommented code as soon as the functionality is confirmed
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
		
	}*/
	
	//TODO AW: Remove outcommented code as soon as the functionality is confirmed
	/*private void extractDriveMatches(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult, 
									ArrayList<clsDataStructurePA> poDriveMatchList) {
				
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
				for (clsPair<Double, clsDataStructureContainer> oMatch : oEntry){
					poDriveMatchList.add(oMatch.b.getMoDataStructure());
				}
		}
	}*/
	
	//TODO AW: Remove outcommented code as soon as the functionality is confirmed
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
		
		//Go through all associated memories
		moAssociatedMemories_OUT = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(moAssociatedMemories_IN);
		for (clsPrimaryDataStructureContainer oContainer : moAssociatedMemories_OUT) {
			mrLibidoReducedBy += setImageLibido(oContainer, 0.5);
		}
		
		moLibidoBuffer.receive_D1_3(mrLibidoReducedBy);

		
		//TODO AW: Remove outcommented code as soon as the functionality is confirmed	
		//moMergedPrimaryInformation_Snd = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		//ZEILINGER): das ganze zeug geht noch nicht so ganz .. irgendwie ... plz - wobei. das kann ich morgen auch noch debuggen.
		/*for (clsPair<String,Double> oCandidate:moLibidioDischargeCandidates) {
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
	
	//TODO AW: Remove outcommented code as soon as the functionality is confirmed
	/*private clsDriveMesh createDriveMesh(String poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<String, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v38.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTripple<String, Object, Object>(poContentType, oContent, poContext)
				);
		
		return oRetVal;
	}*/
	
	
	/**
	 * Search matches for the input image. Assign the libido drive meshes from the best match with the objects (TPM) 
	 * in the input image. Return the reduced amount of libido
	 *
	 * @since 16.07.2011 10:19:10
	 *
	 * @param poInput
	 * @param prLibidoReduceRate
	 * @return
	 */
	private double setImageLibido(clsPrimaryDataStructureContainer poInput, double prLibidoReduceRate) {
		//Search for matches for the input image
		double rReducedValue = 0.0;
		ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResultContainer = new ArrayList<clsPair<Double,clsDataStructureContainer>>();
		
		//Find matching images for the input image
		//FIXME AW: Set Threshold for matching = 0.9
		
		searchContainer(poInput, oSearchResultContainer, "LIBIDOIMAGE", 0.7);
		
		// Here, spread activation for Libido shall be placed.
		//searchContainer(oPerceptionInput, oSearchResultContainer);
		// 2. In spread activation, only very little psychic energy is available
		// 3. if an object match > 60% in an image is found, the libido-DM in the image is added to the input image 
		
		//Get the match with the highest match
		//FIXME AW: Check if the result is correctly sorted
		clsPrimaryDataStructureContainer oBestCompareContainer = getBestMatch(oSearchResultContainer);
		//Get a list of corresponding objects for the libido DMs in the input image
		ArrayList<clsPair<clsDataStructurePA, clsAssociation>> oLibidoDM = clsDataStructureComparison.getSpecificAssociatedContent(oBestCompareContainer, poInput, eDataType.DM, "LIBIDO");
		
		rReducedValue = addDriveMeshes(new clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPair<clsDataStructurePA, clsAssociation>>>(poInput, oLibidoDM), prLibidoReduceRate);
		
		return rReducedValue;
	}
	
	/**
	 * With a match pair, which consist of the target container as element a and a list of the objects within that container and the fitting
	 * drivemesh association as element b. This function copies the drive assignments to the objects in the target container. The 
	 * libido reduce rate sets how much of the mrPleasure in the drive mesh shall be "transferred" to the new drive mesh. The sum of all
	 * mrPleasure of all created drive meshes forms the output, which is the total reduction of libido within this turn.
	 *
	 * @since 16.07.2011 10:21:08
	 *
	 * @param poAssignment
	 * @param prLibidoReduceRate
	 * @return
	 */
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
					clsTriple<Integer, eDataType, String> oIdentifyer = new clsTriple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, eDataType.ASSOCIATIONDM.toString());
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
	
	/**
	 * Equal to the function in F46. It extracts the first element in the arraylist. Precondition: The arrayList is sorted.
	 * This funtion is only a temporary function and shall be replaced by a more flexible one.
	 *
	 * @since 16.07.2011 10:25:21
	 *
	 * @param poInput
	 * @return
	 */
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
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_8(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		//moMergedPrimaryInformation_Rcv = poMergedPrimaryInformation;
		
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer) poMergedPrimaryInformation.clone();
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
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
