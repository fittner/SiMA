/**
 * CHANGELOG
 *
 * Apr 2, 2013 herret - File created
 *
 */
package primaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;


import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eDrive;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import memorymgmt.storage.DT4_PleasureStorage;
import modules.interfaces.D1_2_send;
import modules.interfaces.D1_3_send;
import modules.interfaces.D1_4_receive;
import modules.interfaces.D1_5_receive;
import modules.interfaces.I2_1_receive;
import modules.interfaces.I3_3_receive;
import modules.interfaces.I3_3_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.eOrgan;
import base.datatypes.enums.eOrifice;
import base.datatypes.enums.ePartialDrive;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;



/**
 * From F40 comes the newly formed libido and signals of the erogenous zones in form of neuro-symbols are. The newly formed libido is divided (on the basis of personality parameters) on the libido memory the partial drives. With the aid of signals from the erogenous zones, the libido of the partial drive storage is dissipated.
 *   The sexual drives are generated from the store of libido instincts.
 * 
 * @author herret
 * Apr 2, 2013, 9:51:18 AM
 * 
 */
public class F64_PartialSexualDrives extends clsModuleBase implements
		D1_2_send, D1_3_send, D1_4_receive, D1_5_receive, I2_1_receive, I3_3_send, itfInspectorGenericDynamicTimeChart {


	public static final String P_MODULENUMBER = "64";
	
	public static final String P_SPLITFACTOR_ORAL = "SPLITFACTOR_ORAL";
	public static final String P_SPLITFACTOR_ANAL = "SPLITFACTOR_ANAL";
	public static final String P_SPLITFACTOR_GENITAL = "SPLITFACTOR_GENITAL";
	public static final String P_SPLITFACTOR_PHALLIC = "SPLITFACTOR_PHALLIC";
	
	public static final String P_IMPACT_FACTOR_ORAL = "IMPACT_FACTOR_ORAL";
	public static final String P_IMPACT_FACTOR_ANAL = "IMPACT_FACTOR_ANAL";
	public static final String P_IMPACT_FACTOR_PHALLIC = "IMPACT_FACTOR_PHALLIC";
	public static final String P_IMPACT_FACTOR_GENITAL = "IMPACT_FACTOR_GENITAL";
	
	public static final String P_PERSONALITY_SPLIT_ORAL = "PERSONALITY_SPLIT_ORAL";
	public static final String P_PERSONALITY_SPLIT_ANAL = "PERSONALITY_SPLIT_ANAL";
	public static final String P_PERSONALITY_SPLIT_GENITAL = "PERSONALITY_SPLIT_GENITAL";
	public static final String P_PERSONALITY_SPLIT_PHALLIC = "PERSONALITY_SPLIT_PHALLIC";

	private HashMap<String, Double> moSplitterFactors;
	private HashMap<String, Double> moImpactFactors;
	private HashMap<String, Double> moPersonalitySplitFactors;
	
	private Double moLibidoInput;
	private ArrayList<clsDriveMesh> moOutput;
	
	/** instance of libidobuffer */
	private DT1_PsychicIntensityBuffer moLibidoBuffer;
	
	private DT4_PleasureStorage moPleasureStorage;
	
	private HashMap<String, Double> moErogenousZones_IN;

	private boolean mnChartColumnsChanged = true;

	private HashMap<String, Double> moDriveChartData;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
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
			DT1_PsychicIntensityBuffer poLibidoBuffer,
			clsPersonalityParameterContainer poPersonalityParameterContainer,
			DT4_PleasureStorage poPleasureStorage, int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		
		moLibidoBuffer = poLibidoBuffer;
		moPleasureStorage = poPleasureStorage;
		
		moLibidoInput=0.0;
        moErogenousZones_IN = new HashMap<String, Double>();
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

		
		moPersonalitySplitFactors = new HashMap<String,Double>();
		moPersonalitySplitFactors.put("ORAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERSONALITY_SPLIT_ORAL).getParameterDouble());
        moPersonalitySplitFactors.put("ANAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERSONALITY_SPLIT_ANAL).getParameterDouble());
        moPersonalitySplitFactors.put("GENITAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERSONALITY_SPLIT_GENITAL).getParameterDouble());
        moPersonalitySplitFactors.put("PHALLIC", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERSONALITY_SPLIT_PHALLIC).getParameterDouble());
        
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
	public void receive_I2_1(Double poLibidoSymbol, HashMap<String, Double> poData) {
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
		log.debug("Before procession F64: "+receive_D1_5().toString());
		//ORAL
		send_D1_2(eDrive.ORAL,new clsPair<Double,Double>(moLibidoInput * moSplitterFactors.get("ORAL")*moPersonalitySplitFactors.get("ORAL"),moLibidoInput * moSplitterFactors.get("ORAL")*(1-moPersonalitySplitFactors.get("ORAL"))));
		//ANAL
		send_D1_2(eDrive.ANAL, new clsPair<Double,Double>(moLibidoInput * moSplitterFactors.get("ANAL")*moPersonalitySplitFactors.get("ANAL"),moLibidoInput * moSplitterFactors.get("ANAL")*(1-moPersonalitySplitFactors.get("ANAL"))));
		//GENITAL
		send_D1_2(eDrive.GENITAL, new clsPair<Double,Double>(moLibidoInput * moSplitterFactors.get("GENITAL")*moPersonalitySplitFactors.get("GENITAL"),moLibidoInput * moSplitterFactors.get("GENITAL")*(1-moPersonalitySplitFactors.get("GENITAL"))));
		//PHALLIC
		send_D1_2(eDrive.PHALLIC, new clsPair<Double,Double>(moLibidoInput * moSplitterFactors.get("PHALLIC")*moPersonalitySplitFactors.get("PHALLIC"),moLibidoInput * moSplitterFactors.get("PHALLIC")*(1-moPersonalitySplitFactors.get("PHALLIC"))));
		
		//decrease libodo buffer if erogenous zone gets stimulated
		double before=0.0;
		for(clsPair<Double,Double> oDriveValues: receive_D1_5().values()){
		    before += oDriveValues.a;
		    before += oDriveValues.b;
		}
		for (String eType: moErogenousZones_IN.keySet()){
            if(eType.equals("ORIFICE_ORAL_AGGRESSIV_MUCOSA")){
                send_D1_3(eDrive.ORAL,new clsPair<Double,Double>(moErogenousZones_IN.get(eType)*moImpactFactors.get("ORAL"),0.0));
            }
            else if(eType.equals("ORIFICE_ORAL_LIBIDINOUS_MUCOSA")){
                send_D1_3(eDrive.ORAL,new clsPair<Double,Double>(0.0,moErogenousZones_IN.get(eType)*moImpactFactors.get("ORAL")));
            }
            else if(eType.equals("ORIFICE_PHALLIC_MUCOSA")){
                double rVal = moErogenousZones_IN.get(eType)*moImpactFactors.get("PHALLIC");
                send_D1_3(eDrive.PHALLIC,new clsPair<Double,Double>(rVal/2,rVal/2));

            }
            else if(eType.equals("ORIFICE_RECTAL_MUCOSA")){
                 double rVal = moErogenousZones_IN.get(eType)*moImpactFactors.get("ANAL");
                 send_D1_3(eDrive.ANAL,new clsPair<Double,Double>(rVal/2,rVal/2));


            }
            else if(eType.equals("ORIFICE_GENITAL_MUCOSA")){
                 double rVal = moErogenousZones_IN.get(eType)*moImpactFactors.get("GENITAL");
                 send_D1_3(eDrive.GENITAL,new clsPair<Double,Double>(rVal/2,rVal/2));
            }
        }
		//TODO: handle pleasure calculation
	/*    double after=0.0;
	    for(clsPair<Double,Double> oDriveValues: receive_D1_5().values()){
	       after += oDriveValues.a;
	       after += oDriveValues.b;
	    }
		//moPleasureStorage.D4_2receive(before - after);
	*/
		
		moOutput = new ArrayList<clsDriveMesh>();
		

		
		
		//generation of the drives corresponding to the libido buffer types
		clsDriveMesh oAADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.AGGRESSIVE, ePartialDrive.ANAL);
		oAADM.setQuotaOfAffect( receive_D1_4(eDrive.ANAL).a);
		moOutput.add(oAADM);
		clsDriveMesh oLADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.LIBIDINOUS, ePartialDrive.ANAL);
		oLADM.setQuotaOfAffect( receive_D1_4(eDrive.ANAL).b);
		moOutput.add(oLADM);
		
		clsDriveMesh oAODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.AGGRESSIVE, ePartialDrive.ORAL);
		oAODM.setQuotaOfAffect( receive_D1_4(eDrive.ORAL).a);
		moOutput.add(oAODM);
		clsDriveMesh oLODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.LIBIDINOUS, ePartialDrive.ORAL);
		oLODM.setQuotaOfAffect( receive_D1_4(eDrive.ORAL).b);
		moOutput.add(oLODM);


		clsDriveMesh oAPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.AGGRESSIVE, ePartialDrive.PHALLIC);
		oAPDM.setQuotaOfAffect( receive_D1_4(eDrive.PHALLIC).a);
		moOutput.add(oAPDM);
		clsDriveMesh oLPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.LIBIDINOUS, ePartialDrive.PHALLIC);
		oLPDM.setQuotaOfAffect( receive_D1_4(eDrive.PHALLIC).b);
		moOutput.add(oLPDM);


		
		clsDriveMesh oAGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.AGGRESSIVE, ePartialDrive.GENITAL);
		oAGDM.setQuotaOfAffect( receive_D1_4(eDrive.GENITAL).a);
		moOutput.add(oAGDM);
		clsDriveMesh oLGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.LIBIDINOUS, ePartialDrive.GENITAL);
		oLGDM.setQuotaOfAffect( receive_D1_4(eDrive.GENITAL).b);
		moOutput.add(oLGDM);

		log.debug("After procession F64: "+receive_D1_5().toString());
		
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
		
		for (Entry<String, Double> item : moErogenousZones_IN.entrySet()) {
            InfluxDB.sendInflux("F"+P_MODULENUMBER,item.getKey(),item.getValue());
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

    /* (non-Javadoc)
     *
     * @since 15.05.2013 13:48:55
     * 
     * @see pa._v38.interfaces.modules.D1_2_send#send_D1_2(pa._v38.memorymgmt.enums.eDrive, pa._v38.tools.clsPair)
     */
    @Override
    public void send_D1_2(eDrive peType, clsPair<Double, Double> oValues) {
        moLibidoBuffer.receive_D1_2(peType, oValues);
        
    }
    
    @Override
    public void send_D1_3(eDrive peType, clsPair<Double, Double> oValues) {
        moLibidoBuffer.receive_D1_3(peType, oValues);
        
    }

    /* (non-Javadoc)
     *
     * @since 16.05.2013 11:41:36
     * 
     * @see pa._v38.interfaces.modules.D1_5_receive#receive_D1_5()
     */

    @Override
    public HashMap<eDrive, clsPair<Double, Double>> receive_D1_5() {
        return moLibidoBuffer.send_D1_5();
    }

    /* (non-Javadoc)
     *
     * @since 16.05.2013 11:54:37
     * 
     * @see pa._v38.interfaces.modules.D1_4_receive#receive_D1_4(pa._v38.memorymgmt.enums.eDrive)
     */
    @Override
    public clsPair<Double, Double> receive_D1_4(eDrive oDrive) {
        return moLibidoBuffer.send_D1_4(oDrive);
    }

    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }
}
