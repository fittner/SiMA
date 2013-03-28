/**
 * E43_SeparationIntoPartialSexualDrives.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:19:56
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I3_1_receive;
import pa._v38.interfaces.modules.I3_3_receive;
import pa._v38.interfaces.modules.I3_3_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.personality.parameter.clsPersonalityParameterContainer;
import config.clsProperties;
import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * Each sexual drive is split apart into four drives representing the four partial drives. Module {F43} 
 * takes the aggressive and libidinous drives transmitted from {E41} and splits them according to 
 * predefined but individual templates. The result are eight sexual drives.
 * 
 * @author muchitsch
 * 07.05.2012, 15:19:56
 * 
 */
public class F43_SeparationIntoPartialSexualDrives extends clsModuleBase implements I3_1_receive, I3_3_send, itfInspectorGenericDynamicTimeChart {
	public static final String P_MODULENUMBER = "43";
	
	public static final String P_DRIVE_IMPACT_FACTOR_ORAL = "DRIVE_IMPACT_FACTOR_ORAL";
	public static final String P_DRIVE_IMPACT_FACTOR_ANAL = "DRIVE_IMPACT_FACTOR_ANAL";
	public static final String P_DRIVE_IMPACT_FACTOR_PHALLIC = "DRIVE_IMPACT_FACTOR_PHALLIC";
	public static final String P_DRIVE_IMPACT_FACTOR_GENITAL = "DRIVE_IMPACT_FACTOR_GENITAL";

	

	private ArrayList< clsPair<ePartialDrive,Double> > moPartialSexualDrivesFactors;

	private ArrayList<clsPair<clsDriveMesh, clsDriveMesh>> moSexualDriveComponents_IN;
	
	private ArrayList<clsDriveMesh> moSexualDriveRepresentations_OUT;
	
	private HashMap<ePartialDrive, Double> moSexualDrivesImpactFactors = new HashMap<ePartialDrive, Double>();

	private boolean mnChartColumnsChanged = true;

	private HashMap<String, Double> moDriveChartData;
	
	/**
	 * basic constructor
	 * 
	 * @author muchitsch
	 * 03.03.2011, 17:56:43
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F43_SeparationIntoPartialSexualDrives(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData , clsPersonalityParameterContainer poPersonalityParameterContainer)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);
		
		moSexualDrivesImpactFactors.put(ePartialDrive.ORAL, poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_DRIVE_IMPACT_FACTOR_ORAL).getParameterDouble());
		moSexualDrivesImpactFactors.put(ePartialDrive.ANAL, poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_DRIVE_IMPACT_FACTOR_ANAL).getParameterDouble());
		moSexualDrivesImpactFactors.put(ePartialDrive.PHALLIC, poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_DRIVE_IMPACT_FACTOR_PHALLIC).getParameterDouble());
		moSexualDrivesImpactFactors.put(ePartialDrive.GENITAL, poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_DRIVE_IMPACT_FACTOR_GENITAL).getParameterDouble());

		
		moDriveChartData = new HashMap<String,Double>();
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
		
		//text += toText.listToTEXT("moLibidoDriveDemands", moLibidoDriveDemands);
		//text += toText.listToTEXT("moDriveCandidates", moDriveCandidates);
		
		
		text += toText.listToTEXT("moPartialSexualDrives", moPartialSexualDrivesFactors);
		text += toText.listToTEXT("moSexualDriveComponents_IN", moSexualDriveComponents_IN);
		text += toText.listToTEXT("moSexualDriveRepresentations_OUT", moSexualDriveRepresentations_OUT);
		
		return text;
	}	
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	}	
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic()  {
		
		moSexualDriveRepresentations_OUT  = new ArrayList<clsDriveMesh>();
		
		if(moSexualDriveComponents_IN.size() > 1)
			System.out.printf("ERROR: There can only be 2 sexual drives components commin to F43, thus Arrayhas only one entry, always");
		
		//even if it only can have one entry, safety first...
		for( clsPair<clsDriveMesh, clsDriveMesh> oEntry :  moSexualDriveComponents_IN)
		{
			//create A,O,P,G for the agressive component
			CreateAgressiveDriveRepresentations((clsDriveMesh)oEntry.a);
			
			//create A,O,P,G for the libidoneus component
			CreateLibidoneusDriveRepresentations((clsDriveMesh)oEntry.b);
		}
		
		//add chart data for all drives:
		for (clsDriveMesh oDriveMeshEntry : moSexualDriveRepresentations_OUT )
		{
			//add some time chart data
			String oaKey = oDriveMeshEntry.getChartShortString();
			if ( !moDriveChartData .containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moDriveChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
			
		}
		
	}
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 19.07.2012 11:25:00
	 *
	 * @param a
	 */
	private void CreateAgressiveDriveRepresentations(clsDriveMesh a) {
		double rAgressiveTension = a.getQuotaOfAffect();
		
		clsDriveMesh oAADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.AGGRESSIVE, ePartialDrive.ANAL);
		clsDriveMesh oAODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.AGGRESSIVE, ePartialDrive.ORAL);
		//TODO, create according to sex femal/male
		clsDriveMesh oAPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.AGGRESSIVE, ePartialDrive.PHALLIC);
		clsDriveMesh oAGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.AGGRESSIVE, ePartialDrive.GENITAL);
		
		//setTension by dividing agressive /4
		oAADM.setQuotaOfAffect( rAgressiveTension/4);
		oAODM.setQuotaOfAffect( rAgressiveTension/4);
		oAPDM.setQuotaOfAffect( rAgressiveTension/4);
		oAGDM.setQuotaOfAffect( rAgressiveTension/4);
		
		//calculate tension according to personality
		oAADM.setQuotaOfAffect( CalculateNewPartialTension(oAADM));
		oAODM.setQuotaOfAffect( CalculateNewPartialTension(oAODM));
		oAPDM.setQuotaOfAffect( CalculateNewPartialTension(oAPDM));
		oAGDM.setQuotaOfAffect( CalculateNewPartialTension(oAGDM));
		
		//add the 4 to the out list
		moSexualDriveRepresentations_OUT.add(oAADM);
		moSexualDriveRepresentations_OUT.add(oAODM);
		moSexualDriveRepresentations_OUT.add(oAPDM);
		moSexualDriveRepresentations_OUT.add(oAGDM);
	}
	
	private double CalculateNewPartialTension(clsDriveMesh poSexualDrive)
	{
		double rNewTension = 0.0;
		if(moSexualDrivesImpactFactors.containsKey( poSexualDrive.getPartialDrive() ) )
		{
			try 
			{
				double rImpactFactor = moSexualDrivesImpactFactors.get(poSexualDrive.getPartialDrive());
				rNewTension = poSexualDrive.getQuotaOfAffect() * rImpactFactor;
			} catch (java.lang.Exception e) {
				System.out.print(e);
			}
		}
		return rNewTension;
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
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 19.07.2012 11:25:00
	 *
	 * @param a
	 */
	private void CreateLibidoneusDriveRepresentations(clsDriveMesh a) {
		double rLibidonuesTension = a.getQuotaOfAffect();
		
		clsDriveMesh oLADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.LIBIDINOUS, ePartialDrive.ANAL);
		clsDriveMesh oLODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.LIBIDINOUS, ePartialDrive.ORAL);
		//TODO, create according to sex femal/male
		clsDriveMesh oLPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.LIBIDINOUS, ePartialDrive.PHALLIC);
		clsDriveMesh oLGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.LIBIDINOUS, ePartialDrive.GENITAL);
		
		//setTension by dividing agressive /4
		oLADM.setQuotaOfAffect( rLibidonuesTension/4);
		oLODM.setQuotaOfAffect( rLibidonuesTension/4);
		oLPDM.setQuotaOfAffect( rLibidonuesTension/4);
		oLGDM.setQuotaOfAffect( rLibidonuesTension/4);
		
		//calculate tension according to personality
		oLADM.setQuotaOfAffect( CalculateNewPartialTension(oLADM));
		oLODM.setQuotaOfAffect( CalculateNewPartialTension(oLODM));
		oLPDM.setQuotaOfAffect( CalculateNewPartialTension(oLPDM));
		oLGDM.setQuotaOfAffect( CalculateNewPartialTension(oLGDM));
		
		//add the 4 to the out list
		moSexualDriveRepresentations_OUT.add(oLADM);
		moSexualDriveRepresentations_OUT.add(oLODM);
		moSexualDriveRepresentations_OUT.add(oLPDM);
		moSexualDriveRepresentations_OUT.add(oLGDM);
		
	}

	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	/* (non-Javadoc)
	 *
	 * @since 12.07.2011 10:49:10
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_3(moSexualDriveRepresentations_OUT);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.send._v38.I2_17_send#send_I2_17(java.util.ArrayList)
	 */
	@Override
	public void send_I3_3(ArrayList<clsDriveMesh> poSexualDriveRepresentations) {
		
		((I3_3_receive)moModuleList.get(48)).receive_I3_3(poSexualDriveRepresentations);
		putInterfaceData(I3_3_send.class, poSexualDriveRepresentations);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.receive._v38.I1_10_receive#receive_I1_10(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_1(ArrayList< clsPair<clsDriveMesh, clsDriveMesh> > poSexualDriveComponents) {
		moSexualDriveComponents_IN = (ArrayList< clsPair<clsDriveMesh, clsDriveMesh> >)deepCopy(poSexualDriveComponents);
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
		moDescription = "Each sexual drive is split apart into four drives representing the four partial drives. Module {E43} takes the aggressive and libidinous drives transmitted from {E41} and splits them according to predefined but individual templates. The result are eight sexual drives.  ";
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
