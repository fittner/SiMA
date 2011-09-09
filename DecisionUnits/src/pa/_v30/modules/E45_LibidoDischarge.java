/**
 * E45_LibidoDischarge.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.storage.clsLibidoBuffer;
import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.interfaces.modules.I2_16_receive;
import pa._v30.interfaces.modules.I2_16_send;
import pa._v30.interfaces.modules.I2_8_receive;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.enums.eDataType;

import config.clsProperties;

/**
 *
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 * 
 */
public class E45_LibidoDischarge extends clsModuleBaseKB implements itfInspectorGenericTimeChart, I2_8_receive, I2_16_send {
	public static final String P_MODULENUMBER = "45";
	
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Rcv;
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Snd;	
	private ArrayList<clsPair<String, Double>> moLibidioDischargeCandidates; //pair of IDENTIFIER and qualification from 0 to 1
	private double mrDischargePiece = 0.2; //amount of the sotred libido which is going to be withtracted max. (see formula below)
	private double mrAvailableLibido;
	private double mrLibidoReducedBy;
	private clsLibidoBuffer moLibidoBuffer;	
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:30:00
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E45_LibidoDischarge(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
			clsLibidoBuffer poLibidoBuffer,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		moLibidoBuffer = poLibidoBuffer;
		
		applyProperties(poPrefix, poProp);	
		
		fillLibidioDischargeCandidates();
	}
	
	private void fillLibidioDischargeCandidates() {
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
		
		// (Zeilinger): bitte irgendwie aus dem protege auslesen
//		moLibidioDischargeCandidates = new ArrayList<clsPair<String,Double>>();
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("CAKE", 1.0) );
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("CARROT", 0.5) );
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("ARSIN", 0.1) );
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 22.04.2011, 10:41:34
	 *
	 * @param oSearchResultDM
	 * @param oDriveMeshList 
	 */
	private void extractDriveMatches(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult, 
									ArrayList<clsDataStructurePA> poDriveMatchList) {
				
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
				for (clsPair<Double, clsDataStructureContainer> oMatch : oEntry){
					poDriveMatchList.add(oMatch.b.getMoDataStructure());
				}
		}
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 22.04.2011, 11:29:37
	 *
	 * @param oSearchResultObjects
	 * @param oCandidateList
	 */
	private void extractCandidateMatches(
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResultObjects,
			ArrayList<clsDataStructurePA> poCandidateList) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResultObjects){
			for (clsPair<Double, clsDataStructureContainer> oMatch : oEntry){
				for(clsAssociation oAssociation : oMatch.b.getMoAssociatedDataStructures()){
					poCandidateList.add(oAssociation.getMoAssociationElementB());
				}
			}
		}
		
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
		
		text += toText.listToTEXT("moMergedPrimaryInformation_Rcv", moMergedPrimaryInformation_Rcv);	
		text += toText.listToTEXT("moMergedPrimaryInformation_Snd", moMergedPrimaryInformation_Snd);		
		text += toText.listToTEXT("moLibidioDischargeCandidates", moLibidioDischargeCandidates);
		text += toText.valueToTEXT("mrDischargePiece", mrDischargePiece);		
		text += toText.valueToTEXT("mrAvailableLibido", mrAvailableLibido);
		text += toText.valueToTEXT("mrReducedLibido", mrLibidoReducedBy);
		text += toText.valueToTEXT("moLibidoBuffer", moLibidoBuffer);
		
		return text;
	}		
	
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
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		mrAvailableLibido = moLibidoBuffer.send_D1_4();
		
		double rChunk = mrAvailableLibido * mrDischargePiece; //each match can reduce the libido by a maximum of rChunk.
		//: if more than ten piece fit 100% ... the last pieces will get nothing ...
		
		mrLibidoReducedBy = 0;
		
		moMergedPrimaryInformation_Snd = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		// (ZEILINGER): das ganze zeug geht noch nicht so ganz .. irgendwie ... plz - wobei. das kann ich morgen auch noch debuggen.
		for (clsPair<String,Double> oCandidate:moLibidioDischargeCandidates) {
			String oSearchPattern = oCandidate.a;
			Double rFactor = oCandidate.b;
			double rReduction = rChunk * rFactor;
			
			for (clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oData:moMergedPrimaryInformation_Rcv) {
				clsPrimaryDataStructureContainer oPDSC = oData.a;
				clsDataStructurePA oDS = oPDSC.getMoDataStructure(); 
				
				if (oDS instanceof clsThingPresentationMesh && ((clsThingPresentationMesh)oDS).getMoContent().contains(oSearchPattern)) {
					clsDriveMesh oDrive = createDriveMesh("LIBIDO", "LIBIDO");
					
					double r = rReduction;
					
					// (Zeilinger): dirty hack!!!! by TD : *5 is to be removed!
					//the problem is that later on, the pleasure value is converted into intervals with steplength 
					//of about 0.4. usually, libido provide pleasure gain of 0.2 max -> only in rare occasions, libido
					//gained has influence on decission making!
					r *= 5;
					
					if (rReduction > 1) {rReduction = 1;}
					if (rReduction < -1) {rReduction = -1;}

					oDrive.setPleasure(r);
					
					mrLibidoReducedBy += rReduction;
					
					clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oResult = 
						new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oPDSC, oDrive);
					moMergedPrimaryInformation_Snd.add(oResult);
				}
			}
		}
		
		
				
		moLibidoBuffer.receive_D1_3(mrLibidoReducedBy);

	}

	private clsDriveMesh createDriveMesh(String poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<String, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v30.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTripple<String, Object, Object>(poContentType, oContent, poContext)
				);
		
		return oRetVal;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_16(moMergedPrimaryInformation_Snd);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:29:55
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
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v30.clsModuleBase#setPsychicInstances()
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
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
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
	 * @see pa.interfaces.receive._v30.I2_8_receive#receive_I2_8(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_8(
		ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		moMergedPrimaryInformation_Rcv = poMergedPrimaryInformation;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:32:47
	 * 
	 * @see pa.interfaces.send._v30.I2_16_send#send_I2_16(java.util.ArrayList)
	 */
	@Override
	public void send_I2_16(
			ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I2_16_receive)moModuleList.get(18)).receive_I2_16(poMergedPrimaryInformation);
		putInterfaceData(I2_16_send.class, poMergedPrimaryInformation);
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
		moDescription = "E45 communicates with E41 via the libido buffer. Incoming perceptions are compared with memory to determine whether they qualify for libido discharge and thus for pleasure gain. If so, the value of the libido buffer is reduced (tension reduction is pleasure gain). The pleasure gain is forwarded to E18 as an additional value for the composition of the quota of affect.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 20:29:30
	 * 
	 * @see pa._v30.interfaces.itfInspectorTimeChart#getTimeChartData()
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
	 * @see pa._v30.interfaces.itfInspectorTimeChart#getTimeChartCaptions()
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
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartAxis()
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
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartTitle()
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
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
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
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.5;
	}	
}
