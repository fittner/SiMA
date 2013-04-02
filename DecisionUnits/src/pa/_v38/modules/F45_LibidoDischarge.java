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

import pa._v38.personality.parameter.clsPersonalityParameterContainer;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.modules.I5_9_receive;
import pa._v38.interfaces.modules.I5_9_send;
import pa._v38.interfaces.modules.I5_8_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.itfModuleMemoryAccess;
import pa._v38.memorymgmt.datatypes.clsAssociation;

import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.framessearchspace.tools.clsDataStructureComparisonTools;
import pa._v38.memorymgmt.storage.DT1_LibidoBuffer;

import config.clsProperties;

/**
 * F45 communicates with F41 via the libido buffer. Incoming perceptions are compared with memory to determine 
 * whether they qualify for libido discharge and thus for pleasure gain. If so, the value of the libido buffer 
 * is reduced (tension reduction is pleasure gain). The pleasure gain is forwarded to F18 as an additional value 
 * for the composition of the quota of affect. 
 * 
 * @author deutsch
 * 07.05.2012, 16:29:55
 * 
 */
public class F45_LibidoDischarge extends clsModuleBaseKB implements itfInspectorGenericTimeChart, I5_8_receive, I5_9_send {
	public static final String P_MODULENUMBER = "45";
	
	public static final String P_MATCH_THRESHOLD = "MATCH_THRESHOLD";
	public static final String P_PERCEPTION_REDUCE_FACTOR = "PERCEPTION_REDUCE_FACTOR";
	public static final String P_MEMORY_REDUCE_FACTOR = "MEMORY_REDUCE_FACTOR";
	public static final String P_FANTASY_REDUCE_FACTOR = "FANTASY_REDUCE_FACTOR";
	
	/** Perceived Image in; @since 14.07.2011 14:02:10 */
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	/** Associated memories in; @since 14.07.2011 14:02:10 */
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	/** Perceived Image in, enriched with LIBIDO DMs; @since 14.07.2011 14:02:10 */
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	/** Associated memories out, which are enriched with LIBIDO DM; @since 14.07.2011 14:02:10 */
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	//private ArrayList<clsPair<String, Double>> moLibidioDischargeCandidates; //pair of IDENTIFIER and qualification from 0 to 1
	
	/* Module parameters */
	/** Threshold for the matching process of images */
	private double mrMatchThreshold;
	/** The perceived image is compared to stored images in the libido-image storage in protege. For each found object, the attached
	 * libido DM is copied to the perceived image. With this factor, the attached libido DM of an image is multiplicated. The 
	 * resulting libido in the DM (mrPleasure) reduces the libido storage. Perception generally reduces more libido than 
	 * memories */
	private double mrPerceptionReduceFactor;
	/** With this factor, the attached libido DM of a memory is multiplicated. */
	private double mrMemoryReduceFactor;
	
	private double mrPhantasyReduceFactor;
	
	// Other variables
	//private double mrDischargePiece = 0.2; //amount of the sotred libido which is going to be withtracted max. (see formula below)
	/** Available Libido, double */
	private double mrAvailableLibido;
	/** The amount of libido, of which the libido buffer is reduced by */
	private double mrLibidoReducedBy;
	/** instance of libidobuffer */
	private DT1_LibidoBuffer moLibidoBuffer;	
	
	
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
	public F45_LibidoDischarge(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
			DT1_LibidoBuffer poLibidoBuffer,
			itfModuleMemoryAccess poLongTermMemory , clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
		moLibidoBuffer = poLibidoBuffer;
		
		applyProperties(poPrefix, poProp);
		mrMatchThreshold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MATCH_THRESHOLD).getParameterDouble();
		mrPerceptionReduceFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_REDUCE_FACTOR).getParameterDouble();
		mrMemoryReduceFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MEMORY_REDUCE_FACTOR).getParameterDouble();
		mrPhantasyReduceFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_FANTASY_REDUCE_FACTOR).getParameterDouble();
		
		
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
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		//text += toText.valueToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		//text += toText.valueToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
		
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
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
		//nothing to do
	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() {
		//Get available amount of free libido 
		

		mrAvailableLibido=moLibidoBuffer.send_D1_4();
		mrLibidoReducedBy = 0.0;
		//Clone input structure and make modification directly on the output
		try {
			//moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.cloneGraph();
			moPerceptionalMesh_OUT = (clsThingPresentationMesh) moPerceptionalMesh_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Perception and phantasy are treated equal, therefore, get all images in the mesh
		ArrayList<clsThingPresentationMesh> oImageList = clsMeshTools.getAllTPMImages(moPerceptionalMesh_OUT, 2);
		
		// consider relative reduction (relative to currently available libido) --> the higher the libido, the higher the absolute reduction
		mrPerceptionReduceFactor = mrPerceptionReduceFactor * mrAvailableLibido;
		mrPerceptionReduceFactor = mrPhantasyReduceFactor * mrAvailableLibido;
		mrPerceptionReduceFactor = mrMemoryReduceFactor * mrAvailableLibido;	
		
		//Go through all images
		for (clsThingPresentationMesh oImage : oImageList) {
			if(oImage.getMoContentType() == eContentType.PI){
				mrLibidoReducedBy += setImageLibido(oImage, mrPerceptionReduceFactor, mrAvailableLibido);
			}
			// temporarily deactivated, since perception of "emptyspace" always trigger an RI with a cake. hence there would always be libidodischarge 
//			else if(oImage.getMoContentType() == eContentType.RI){
//				mrLibidoReducedBy += setImageLibido(oImage, mrMemoryReduceFactor, mrAvailableLibido);
//
//			}
			else if(oImage.getMoContentType() == eContentType.PHI){
				mrLibidoReducedBy += setImageLibido(oImage, mrPhantasyReduceFactor, mrAvailableLibido);

			}
		}
		
		
		//This function searches the memory for LIBIDO-Images and if a match is found (> Threshold), then the drive meshes are
		//added to the image and in mrLibidoReducedBy is set as mrPerceptionReduceFactor * Quota of affect
		//mrLibidoReducedBy = setImageLibido(moEnvironmentalPerception_OUT, mrPerceptionReduceFactor, mrAvailableLibido);
		
		//Go through all associated memories
		//moAssociatedMemories_OUT = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(moAssociatedMemories_IN);
		//for (clsPrimaryDataStructureContainer oContainer : moAssociatedMemories_OUT) {
		//	mrLibidoReducedBy += setImageLibido(oContainer, mrMemoryReduceFactor);
		//}
		
		moLibidoBuffer.receive_D1_3(mrLibidoReducedBy);


	}
	
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
	private double setImageLibido(clsThingPresentationMesh poInput, double prLibidoReduceRate, double prAvailableLibido) {
		//Search for matches for the input image
		double rReducedValue = 0.0;
		ArrayList<clsPair<Double,clsDataStructurePA>> oSearchResultContainer = new ArrayList<clsPair<Double,clsDataStructurePA>>();
		
		//Find matching images for the input image
		oSearchResultContainer = this.getLongTermMemory().searchMesh(poInput, eContentType.RILIBIDO, mrMatchThreshold, 2);	//About "2" = search only one level except the current one, i. e. direct matches
		
		// Here, spread activation for Libido shall be placed.
		//searchContainer(oPerceptionInput, oSearchResultContainer);
		// 2. In spread activation, only very little psychic energy is available
		// 3. if an object match > 60% in an image is found, the libido-DM in the image is added to the input image 
		
		//Get the match with the highest match
		//FIXME AW: Check if the result is correctly sorted
		//Only TPMs are searched for if meshes are searched
		clsThingPresentationMesh oBestCompareImage = (clsThingPresentationMesh) getBestMatch(oSearchResultContainer);
		//Get a list of corresponding objects for the libido DMs in the input image
		ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>> oLibidoDM = clsDataStructureComparisonTools.getSpecificAssociatedContent(oBestCompareImage, poInput, eDataType.DM, eContentType.LIBIDO);
		
		rReducedValue = addDriveMeshes(new clsPair<clsThingPresentationMesh, ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>>>(poInput, oLibidoDM), prLibidoReduceRate, prAvailableLibido);
		
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
	private double addDriveMeshes (clsPair<clsThingPresentationMesh, ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>>> poAssignment, double prLibidoReduceRate, double prAvailableLibido) {
		//For each Pair, assign the drive meshes
		double rTotalReduce = 0.0;
		for (clsPair<clsThingPresentationMesh, clsAssociation> oAssignmentElement : poAssignment.b) {
			clsDriveMesh oDM = (clsDriveMesh) oAssignmentElement.b.getLeafElement();
			//With this amount the libido puffer shall be reduced
			double rDMReduce = oDM.getQuotaOfAffect() * prLibidoReduceRate;
			//if the total reduction of libido is smaller than the buffer, then the DM can be assigned
			//For perception, the reduce rate shall be 1.0 and for associated content, only 0.5
			if (rTotalReduce + rDMReduce <= prAvailableLibido) {
				try {
					//Clone the original DM
					clsDriveMesh oNewDriveMesh = (clsDriveMesh) oDM.clone();
					//Set new QoA, which depends on the reducevalue
					oNewDriveMesh.setQuotaOfAffect(rDMReduce);
					//Create new identifier
					clsTriple<Integer, eDataType, eContentType> oIdentifyer = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM);
					//Create new association drivemesh but with the new root element
					clsAssociationDriveMesh oDriveAss = new clsAssociationDriveMesh(oIdentifyer, oNewDriveMesh, (clsThingPresentationMesh)oAssignmentElement.a);
					//Add the assocation to the input container
					oAssignmentElement.a.getExternalMoAssociatedContent().add(oDriveAss);
					//poAssignment.a.assignDataStructure(oDriveAss);
					rTotalReduce += rDMReduce;
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			else {
				rTotalReduce = prAvailableLibido;
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
	private clsDataStructurePA getBestMatch (ArrayList<clsPair<Double,clsDataStructurePA>> poInput) {
		//Get the first structure from the sorted list
		clsDataStructurePA oRetVal;
		if (poInput.size()!=0) {
			oRetVal = (clsDataStructurePA) poInput.get(0).b;
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
		send_I5_9(moPerceptionalMesh_OUT);

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
	public void receive_I5_8(clsThingPresentationMesh poPerceptionalMesh) {
		//moMergedPrimaryInformation_Rcv = poMergedPrimaryInformation;
		
		try {
			//moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:32:47
	 * 
	 * @see pa.interfaces.send._v38.I2_16_send#send_I2_16(java.util.ArrayList)
	 */
	@Override
	public void send_I5_9(clsThingPresentationMesh poPerceptionalMesh) {
		((I5_9_receive)moModuleList.get(18)).receive_I5_9(poPerceptionalMesh);
		
		putInterfaceData(I5_9_send.class, poPerceptionalMesh);
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
		
		oValues.add(moLibidoBuffer.send_D1_4());
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
		return 1;
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
