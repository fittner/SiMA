/**
 * CHANGELOG
 *
 * Apr 2, 2013 herret - File created
 *
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import du.enums.eFastMessengerSources;
import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;


import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I2_1_receive;
import pa._v38.interfaces.modules.I3_3_receive;
import pa._v38.interfaces.modules.I3_3_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eSexualDrives;
import pa._v38.memorymgmt.storage.DT1_LibidoBuffer;
import pa._v38.memorymgmt.storage.DT4_PleasureStorage;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * From F40 comes the newly formed libido and signals of the erogenous zones in form of neuro-symbols are. The newly formed libido is divided (on the basis of personality parameters) on the libido memory the partial drives. With the aid of signals from the erogenous zones, the libido of the partial drive storage is dissipated.
 *   The sexual drives are generated from the store of libido instincts.
 * 
 * @author herret
 * Apr 2, 2013, 9:51:18 AM
 * 
 */
public class F64_PartialSexualDrives extends clsModuleBase implements
		I2_1_receive, I3_3_send, itfInspectorGenericDynamicTimeChart {


	public static final String P_MODULENUMBER = "64";
	
	public static final String P_SPLITFACTOR_ORAL = "SPLITFACTOR_ORAL";
	public static final String P_SPLITFACTOR_ANAL = "SPLITFACTOR_ANAL";
	public static final String P_SPLITFACTOR_GENITAL = "SPLITFACTOR_GENITAL";
	public static final String P_SPLITFACTOR_PHALLIC = "SPLITFACTOR_PHALLIC";
	
	public static final String P_IMPACT_FACTOR_ORAL = "IMPACT_FACTOR_ORAL";
	public static final String P_IMPACT_FACTOR_ANAL = "IMPACT_FACTOR_ANAL";
	public static final String P_IMPACT_FACTOR_PHALLIC = "IMPACT_FACTOR_PHALLIC";
	public static final String P_IMPACT_FACTOR_GENITAL = "IMPACT_FACTOR_GENITAL";
	
	private HashMap<String, Double> moSplitterFactors;
	private HashMap<String, Double> moImpactFactors;
	
	private Double moLibidoInput;
	private ArrayList<clsDriveMesh> moOutput;
	
	/** instance of libidobuffer */
	private DT1_LibidoBuffer moLibidoBuffer;
	
	private DT4_PleasureStorage moPleasureStorage;
	
	private HashMap<eFastMessengerSources, Double> moErogenousZones_IN;

	private boolean mnChartColumnsChanged = true;

	private HashMap<String, Double> moDriveChartData;
	
	/**
	 * Constructor
	 *
	 * @since Apr 2, 2013 9:51:23 AM
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F64_PartialSexualDrives(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT1_LibidoBuffer poLibidoBuffer,
			clsPersonalityParameterContainer poPersonalityParameterContainer,
			DT4_PleasureStorage poPleasureStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moLibidoBuffer = poLibidoBuffer;
		moPleasureStorage = poPleasureStorage;
		
		moLibidoInput=0.0;
		moErogenousZones_IN = new HashMap<eFastMessengerSources, Double>();
		moSplitterFactors = new HashMap<String, Double>();
		moSplitterFactors.put("ORAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_ORAL).getParameterDouble());
		moSplitterFactors.put("ANAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_ANAL).getParameterDouble());
		moSplitterFactors.put("GENITAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_GENITAL).getParameterDouble());
		moSplitterFactors.put("PHALLIC", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_PHALLIC).getParameterDouble());
		
		moImpactFactors = new HashMap<String, Double>();
		moImpactFactors.put("ORAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_IMPACT_FACTOR_ORAL).getParameterDouble());
		moImpactFactors.put("ANAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_IMPACT_FACTOR_ANAL).getParameterDouble());
		moImpactFactors.put("GENITAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_IMPACT_FACTOR_GENITAL).getParameterDouble());
		moImpactFactors.put("PHALLIC", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_IMPACT_FACTOR_PHALLIC).getParameterDouble());

		moDriveChartData = new HashMap<String,Double>();
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

	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}
	
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {

		String text="";
		text += "Libido Input: " + moLibidoInput + "\n";
		text += toText.mapToTEXT("Erogenous Zones Input", moErogenousZones_IN);
		text += toText.listToTEXT("Drives_OUT", moOutput);
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.interfaces.modules.I3_3_send#send_I3_3(java.util.ArrayList)
	 */
	@Override
	public void send_I3_3(ArrayList<clsDriveMesh> poSexualDriveRepresentations) {
		((I3_3_receive)moModuleList.get(48)).receive_I3_3(poSexualDriveRepresentations);
		putInterfaceData(I3_3_send.class, poSexualDriveRepresentations);

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.interfaces.modules.I2_1_receive#receive_I2_1(java.lang.Double)
	 */
	@Override
	public void receive_I2_1(Double poLibidoSymbol, HashMap<eFastMessengerSources, Double> poData) {
		moLibidoInput = poLibidoSymbol;
		moErogenousZones_IN = poData;
	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		//partition of the incoming libido value to all partial drives corresponding to the fixation values (moSplitterFactors)
		
		//ORAL
		moLibidoBuffer.receive_D1_1(eSexualDrives.ORAL, moLibidoInput * moSplitterFactors.get("ORAL"));
		//ANAL
		moLibidoBuffer.receive_D1_1(eSexualDrives.ANAL, moLibidoInput * moSplitterFactors.get("ANAL"));
		//GENITAL
		moLibidoBuffer.receive_D1_1(eSexualDrives.GENITAL, moLibidoInput * moSplitterFactors.get("GENITAL"));
		//PHALLIC
		moLibidoBuffer.receive_D1_1(eSexualDrives.PHALLIC, moLibidoInput * moSplitterFactors.get("PHALLIC"));
		
		//decrease libodo buffer if erogenous zone gets stimulated
		double before = moLibidoBuffer.send_D1_4();
		for (eFastMessengerSources eType: moErogenousZones_IN.keySet()){
			if(eType == eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA){
			    moLibidoBuffer.receive_D1_5(moErogenousZones_IN.get(eType)*moImpactFactors.get("ORAL"), eSexualDrives.ORAL);
			}
			else if(eType == eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA){
			    moLibidoBuffer.receive_D1_5(moErogenousZones_IN.get(eType)*moImpactFactors.get("ORAL"), eSexualDrives.ORAL);
			}
			else if(eType == eFastMessengerSources.ORIFICE_PHALLIC_MUCOSA){
				moLibidoBuffer.receive_D1_5(moErogenousZones_IN.get(eType)*moImpactFactors.get("ANAL"), eSexualDrives.PHALLIC);

			}
			else if(eType == eFastMessengerSources.ORIFICE_RECTAL_MUCOSA){
				moLibidoBuffer.receive_D1_5(moErogenousZones_IN.get(eType)*moImpactFactors.get("GENITAL"), eSexualDrives.ANAL);

			}
			else if(eType == eFastMessengerSources.ORIFICE_GENITAL_MUCOSA){
				moLibidoBuffer.receive_D1_5(moErogenousZones_IN.get(eType)*moImpactFactors.get("PHALLIC"), eSexualDrives.GENITAL);

                
				
			}
		}
		double after = moLibidoBuffer.send_D1_4();
		moPleasureStorage.D4_2receive(before - after);
		
		
		moOutput = new ArrayList<clsDriveMesh>();
		

		
		
		//generation of the drives corresponding to the libido buffer types
		clsDriveMesh oAADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.AGGRESSIVE, ePartialDrive.ANAL);
		oAADM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.ANAL)/2);
		moOutput.add(oAADM);
		clsDriveMesh oLADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.LIBIDINOUS, ePartialDrive.ANAL);
		oLADM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.ANAL)/2);
		moOutput.add(oLADM);
		
		clsDriveMesh oAODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.AGGRESSIVE, ePartialDrive.ORAL);
		oAODM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.ORAL)/2);
		moOutput.add(oAODM);
		clsDriveMesh oLODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.LIBIDINOUS, ePartialDrive.ORAL);
		oLODM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.ORAL)/2);
		moOutput.add(oLODM);


		clsDriveMesh oAPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.AGGRESSIVE, ePartialDrive.PHALLIC);
		oAPDM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.PHALLIC)/2);
		moOutput.add(oAPDM);
		clsDriveMesh oLPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.LIBIDINOUS, ePartialDrive.PHALLIC);
		oLPDM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.PHALLIC)/2);
		moOutput.add(oLPDM);


		
		clsDriveMesh oAGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.AGGRESSIVE, ePartialDrive.GENITAL);
		oAGDM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.GENITAL)/2);
		moOutput.add(oAGDM);
		clsDriveMesh oLGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.LIBIDINOUS, ePartialDrive.GENITAL);
		oLGDM.setQuotaOfAffect( moLibidoBuffer.send_D1_2(eSexualDrives.GENITAL)/2);
		moOutput.add(oLGDM);


		
		//add chart data for all drives:
		for (clsDriveMesh oDriveMeshEntry : moOutput )
		{
			//add some time chart data
			String oaKey = oDriveMeshEntry.getChartShortString();
			if ( !moDriveChartData .containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moDriveChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
			
		}
		
	}
	
	

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (herret) - Auto-generated method stub

	}

	
	private clsDriveMesh CreateDriveRepresentations(eOrgan poOrgan, eOrifice poOrifice, eDriveComponent oComponent, ePartialDrive oPartialDrive) {
		clsDriveMesh oDriveCandidate  = null;
		
		String oOrgan = poOrgan.toString();
		String oOrifice = poOrifice.toString();
		
		try {
		
		
		//create a TPM for the organ
			clsThingPresentationMesh oOrganTPM = 
				(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
						eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORGAN, new ArrayList<clsThingPresentation>(), oOrgan) );

		
		//create a TPM for the orifice
			clsThingPresentationMesh oOrificeTPM = 
				(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
						eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORIFICE, new ArrayList<clsThingPresentation>(), oOrifice) );

		
		//create the DM
		oDriveCandidate = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, 
				Object>(eContentType.DRIVEREPRESENTATION, new ArrayList<clsThingPresentationMesh>(), "") 
				,eDriveComponent.UNDEFINED, ePartialDrive.UNDEFINED );
		
		//supplement the information
		
		oDriveCandidate.setDriveComponent(oComponent);
		oDriveCandidate.setPartialDrive(oPartialDrive);
		
		
		oDriveCandidate.setActualDriveSource(oOrganTPM, 1.0);
		
		
		oDriveCandidate.setActualBodyOrifice(oOrificeTPM, 1.0);
		
		} catch (Exception e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		}
		
	return oDriveCandidate;
	}
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_3(moOutput);

	}


	

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 9:51:19 AM
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */

	@Override
	public void setDescription() {
	    moDescription = "From F40 comes the newly formed libido and signals of the erogenous zones in form of neuro-symbols are. The newly formed libido is divided (on the basis of personality parameters) on the libido memory the partial drives. With the aid of signals from the erogenous zones, the libido of the partial drive storage is dissipated. The sexual drives are generated from the store of libido instincts.";

	}
	
	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:19 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1;
	}

	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:19 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:20 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:20 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Sexual Drives";
	}

	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:20 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moDriveChartData.values());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:20 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moDriveChartData.keySet());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Mar 14, 2013 3:07:20 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;	
		
	}

}
