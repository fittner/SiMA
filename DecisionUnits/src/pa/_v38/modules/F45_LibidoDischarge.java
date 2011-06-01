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
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.modules.I5_9_receive;
import pa._v38.interfaces.modules.I5_9_send;
import pa._v38.interfaces.modules.I5_8_receive;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 16:29:55
 * 
 */
public class F45_LibidoDischarge extends clsModuleBaseKB implements itfInspectorGenericTimeChart, I5_8_receive, I5_9_send {
	public static final String P_MODULENUMBER = "45";
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	//AW 20110521: Old input
	//private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Rcv;
	//AW 20110521: Old output
	//private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Snd;	
	private ArrayList<clsPair<String, Double>> moLibidioDischargeCandidates; //pair of IDENTIFIER and qualification from 0 to 1
	
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
		
		//FIXME (Zeilinger): bitte irgendwie aus dem protege auslesen
//		moLibidioDischargeCandidates = new ArrayList<clsPair<String,Double>>();
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("CAKE", 1.0) );
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("CARROT", 0.5) );
//		moLibidioDischargeCandidates.add( new clsPair<String, Double>("BUBBLE", 0.1) );
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
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
	 * DOCUMENT (zeilinger) - insert description
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
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);	
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);		
		text += toText.listToTEXT("moLibidioDischargeCandidates", moLibidioDischargeCandidates);
		text += toText.valueToTEXT("mrDischargePiece", mrDischargePiece);		
		text += toText.valueToTEXT("mrAvailableLibido", mrAvailableLibido);
		text += toText.valueToTEXT("mrReducedLibido", mrLibidoReducedBy);
		text += toText.valueToTEXT("moLibidoBuffer", moLibidoBuffer);
		
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
	 * 03.03.2011, 16:29:55
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		mrAvailableLibido = moLibidoBuffer.send_D1_4();
		
		double rChunk = mrAvailableLibido * mrDischargePiece; //each match can reduce the libido by a maximum of rChunk.
		//FIXME: if more than ten piece fit 100% ... the last pieces will get nothing ...
		
		mrLibidoReducedBy = 0;
		
		try {
			moEnvironmentalPerception_OUT = (clsPrimaryDataStructureContainer) moEnvironmentalPerception_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		moLibidoBuffer.receive_D1_3(mrLibidoReducedBy);
		
		//Pass the memories forward. Later, they are enriched repressed content
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
	}

	private clsDriveMesh createDriveMesh(String poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<String, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v38.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTripple<String, Object, Object>(poContentType, oContent, poContext)
				);
		
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
		moDescription = "E45 communicates with E41 via the libido buffer. Incoming perceptions are compared with memory to determine whether they qualify for libido discharge and thus for pleasure gain. If so, the value of the libido buffer is reduced (tension reduction is pleasure gain). The pleasure gain is forwarded to E18 as an additional value for the composition of the quota of affect.";
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
