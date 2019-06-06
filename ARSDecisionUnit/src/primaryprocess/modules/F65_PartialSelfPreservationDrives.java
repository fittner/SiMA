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
import java.util.SortedMap;
import java.util.Map.Entry;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eDrive;
import memorymgmt.enums.eDriveProperty;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import memorymgmt.storage.DT4_PleasureStorage;
import modules.interfaces.D1_4_receive;
import modules.interfaces.I2_2_receive;
import modules.interfaces.I3_4_receive;
import modules.interfaces.I3_4_send;
import modules.interfaces.eInterfaces;

import org.apache.log4j.Logger;




import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;

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
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * F65 receives the neuro-symbolic homeostatic values from F2 and generates the corresponding drives.
 * 
 * @author herret
 * Apr 2, 2013, 1:56:16 PM
 * 
 */
public class F65_PartialSelfPreservationDrives extends clsModuleBase implements I2_2_receive, I3_4_send, D1_4_receive, itfInspectorGenericDynamicTimeChart{

	
	public static final String P_MODULENUMBER = "65";
	public static final String P_HOMEOSTASIS_BLOODSUGAR = "HOMEOSTASIS_IMPACT_FACTOR_BLOODSUGAR";
	public static final String P_HOMEOSTASIS_RECTUM = "HOMEOSTASIS_IMPACT_FACTOR_RECTUM";
	public static final String P_HOMEOSTASIS_STAMINA = "HOMEOSTASIS_IMPACT_FACTOR_STAMINA";
	public static final String P_RECTUM_PAIN_LIMIT = "RECTUM_PAIN_LIMIT";
	
	private ArrayList<clsDriveMesh> moHomeostaticDriveComponents_OUT;
	private HashMap<String, Double> moHomeostasisSymbols_IN;
	private double rectum_pain_limit;
	
	private DT1_PsychicIntensityBuffer moLibidoBuffer;
	
	private HashMap<String, Double> moCandidatePartitionFactor;
	
    private  DT4_PleasureStorage moPleasureStorage;
	
    private HashMap<eDrive,Double> oQoA_LastStep;
    private HashMap<eDrive,Double> o_LastStep;
    private HashMap<eDrive,Double> shiftFactorLastStep;
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	private HashMap<eOrgan, eOrifice> moOrificeMap;
	
	private HashMap<eDrive, eOrgan> moOrganMap;
	
	private HashMap<String,HashMap<eDriveProperty,Object>> moMapping;
	
	private HashMap<eDrive, Double> moErogenousZonesSave;
	
	//einfluess auf die normalisierung von body -> psyche
	private HashMap<String, Double> moHomeostaisImpactFactors;
    
	//charts
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData; 
	private HashMap<eDrive, ePartialDrive> moPartialDriveMapping;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
    
    
	/**
	 * DOCUMENT (herret) - insert description 
	 *
	 * @since Apr 2, 2013 1:56:22 PM
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F65_PartialSelfPreservationDrives(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsPersonalityParameterContainer poPersonalityParameterContainer,
			DT1_PsychicIntensityBuffer poLibidoBuffer,
			DT4_PleasureStorage poPleasureStorage,
			int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		//rectum_pain_limit = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_RECTUM_PAIN_LIMIT).getParameterDouble();
		
		moPleasureStorage= poPleasureStorage;
		moLibidoBuffer = poLibidoBuffer;
		moHomeostaisImpactFactors = new HashMap<String, Double>();
		moHomeostaisImpactFactors.put("BLOODSUGAR",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_BLOODSUGAR).getParameterDouble());
		moHomeostaisImpactFactors.put("RECTUM",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_RECTUM).getParameterDouble());
		moHomeostaisImpactFactors.put("STAMINA",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_STAMINA).getParameterDouble());
//		moHomeostaisImpactFactors.put("HEALTH",0.8);

		oQoA_LastStep = new HashMap<eDrive,Double>();
		
		o_LastStep = new HashMap<eDrive,Double>();
		o_LastStep.put(eDrive.STOMACH, 0.0);
		

		
		shiftFactorLastStep = new HashMap<eDrive,Double>();
		moMapping = new HashMap<String,HashMap<eDriveProperty,Object>>();
		
		fillMapping(poPersonalityParameterContainer);
		fillOrificeMapping();
		fillOrganMapping();
		
		moErogenousZonesSave = new HashMap<eDrive, Double>();
		moErogenousZonesSave.put(eDrive.STOMACH, -1.0);
		moErogenousZonesSave.put(eDrive.STAMINA, -1.0);
		moErogenousZonesSave.put(eDrive.RECTUM, -1.0);
		moErogenousZonesSave.put(eDrive.HEALTH, -1.0);
        
		moDriveChartData = new HashMap<String,Double>();
	}
	

    private void fillMapping(clsPersonalityParameterContainer poPersonalityParameterContainer) {

        HashMap<eDriveProperty, Object> moStomach = new HashMap<eDriveProperty,Object>();
        moStomach.put(eDriveProperty.ORGAN, "STOMACH");
        moStomach.put(eDriveProperty.ORIFICE, "ORAL_MUCOSA");
        moStomach.put(eDriveProperty.LIBIDINOUS_EROGENOUS_ZONE, "ORIFICE_ORAL_LIBIDINOUS_MUCOSA");
        moStomach.put(eDriveProperty.AGGRESSIV_EROGENOUS_ZONE, "ORIFICE_ORAL_AGGRESSIV_MUCOSA");
        moStomach.put(eDriveProperty.PERSONALITY_PARAMETERS, fillPersonalityStomach(poPersonalityParameterContainer));
        moMapping.put("BLOODSUGAR", moStomach);
        
        HashMap<eDriveProperty, Object> moRectum = new HashMap<eDriveProperty,Object>();
        moRectum.put(eDriveProperty.ORGAN, "RECTUM");
        moRectum.put(eDriveProperty.ORIFICE, "RECTAL_MUCOSA");
        moRectum.put(eDriveProperty.PERSONALITY_PARAMETERS, fillPersonalityRectum(poPersonalityParameterContainer));
        moMapping.put("RECTUM", moRectum);
        
        HashMap<eDriveProperty, Object> moStamina = new HashMap<eDriveProperty,Object>();
        moStamina.put(eDriveProperty.ORGAN, "STAMINA");
        moStamina.put(eDriveProperty.ORIFICE, "TRACHEA");
        moStamina.put(eDriveProperty.PERSONALITY_PARAMETERS, fillPersonalityStamina(poPersonalityParameterContainer));
        moMapping.put("STAMINA", moStamina);
        
        HashMap<eDriveProperty, Object> moHealth = new HashMap<eDriveProperty,Object>();
        moHealth.put(eDriveProperty.ORGAN, "HEALTH");
        moHealth.put(eDriveProperty.ORIFICE, "TRACHEA");
        moHealth.put(eDriveProperty.PERSONALITY_PARAMETERS, fillPersonalityStamina(poPersonalityParameterContainer));
        moMapping.put("HEALTH", moHealth);
        
    }
    
    private HashMap<String,Double> fillPersonalityRectum(clsPersonalityParameterContainer poPersonalityParameterContainer){
        HashMap<String,Double> oRetVal = new HashMap<String,Double>();
        oRetVal.put("EROGENOUS_ZONES_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"EROGENOUS_ZONES_IMPACT_RECTUM").getParameterDouble());
        oRetVal.put("PERSONALITY_SPLIT_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"PERSONALITY_SPLIT_IMPACT_RECTUM").getParameterDouble());
        oRetVal.put("PERSONALITY_SPLIT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"PERSONALITY_SPLIT_RECTUM").getParameterDouble());
        oRetVal.put("DRIFT_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"DRIFT_IMPACT_RECTUM").getParameterDouble());

        return oRetVal;
    }
    
    private HashMap<String,Double> fillPersonalityStamina(clsPersonalityParameterContainer poPersonalityParameterContainer){
        HashMap<String,Double> oRetVal = new HashMap<String,Double>();
        oRetVal.put("EROGENOUS_ZONES_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"EROGENOUS_ZONES_IMPACT_STAMINA").getParameterDouble());
        oRetVal.put("PERSONALITY_SPLIT_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"PERSONALITY_SPLIT_IMPACT_STAMINA").getParameterDouble());
        oRetVal.put("PERSONALITY_SPLIT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"PERSONALITY_SPLIT_STAMINA").getParameterDouble());
        oRetVal.put("DRIFT_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"DRIFT_IMPACT_STAMINA").getParameterDouble());

        return oRetVal;
    }
    
    private HashMap<String,Double> fillPersonalityStomach(clsPersonalityParameterContainer poPersonalityParameterContainer){
        HashMap<String,Double> oRetVal = new HashMap<String,Double>();
        oRetVal.put("EROGENOUS_ZONES_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"EROGENOUS_ZONES_IMPACT_STOMACH").getParameterDouble());
        oRetVal.put("PERSONALITY_SPLIT_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"PERSONALITY_SPLIT_IMPACT_STOMACH").getParameterDouble());
        oRetVal.put("PERSONALITY_SPLIT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"PERSONALITY_SPLIT_STOMACH").getParameterDouble());
        oRetVal.put("DRIFT_IMPACT", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,"DRIFT_IMPACT_STOMACH").getParameterDouble());

        return oRetVal;
    }

    private void fillOrificeMapping() {
		//this mapping is fixed for the PA body, no changes! (cm 18.07.2012)
		moOrificeMap = new HashMap<eOrgan, eOrifice>();
		moOrificeMap.put(eOrgan.RECTUM, eOrifice.RECTAL_MUCOSA);
		moOrificeMap.put(eOrgan.STAMINA, eOrifice.TRACHEA);
		moOrificeMap.put(eOrgan.BLADDER, eOrifice.URETHRAL_MUCOSA);
		moOrificeMap.put(eOrgan.STOMACH, eOrifice.ORAL_MUCOSA);
	}
	private void fillOrganMapping() {
	    moOrganMap = new HashMap<eDrive, eOrgan>();
	    moOrganMap.put(eDrive.STOMACH, eOrgan.STOMACH);
	    moOrganMap.put(eDrive.RECTUM, eOrgan.RECTUM);
	    moOrganMap.put(eDrive.STAMINA, eOrgan.STAMINA);
	}

	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}
	
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		text += toText.mapToTEXT("Homeostatic symbols_IN", moHomeostasisSymbols_IN);
		text += toText.listToTEXT("Homeostatic drives_OUT", moHomeostaticDriveComponents_OUT);
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		log.debug("PI Buffer befor processing F65:\n"+moLibidoBuffer.send_D1_5().toString());
	    
	    //OVERVIEW: from the body-symbol-tension list, create a set of psychic datastructures that represent the demands+sources+tensions
		HashMap<String, Double> oNormalizedHomeostatsisSymbols = null;
		
		
		// 1- Normalization of bodily demands according to table
		oNormalizedHomeostatsisSymbols = NormalizeHomeostaticSymbols(moHomeostasisSymbols_IN);
		
		
		moHomeostaticDriveComponents_OUT = new ArrayList<clsDriveMesh>();
		//2 - search for old QoA
		// 3- create a drivecandidate for every entry in the list, set the tension, organ orifice
		
		for( Entry<String, Double> oEntry : oNormalizedHomeostatsisSymbols.entrySet())
        {
		    String oInputValue = oEntry.getKey().toString();
		    if(moMapping.containsKey(oInputValue)){
		        changeDriveBuffer(moMapping.get(oInputValue),oEntry.getValue() );
		    }
		    
        }
		
		log.debug("PI Buffer after processing F65:\n"+moLibidoBuffer.send_D1_5().toString());
		/*
		// 3- create a drivecandidate for every entry in the list, set the tension, organ orifice
		for( Entry<String, Double> oEntry : oNormalizedHomeostatsisSymbols.entrySet())
		{

		    try{
    			if(oEntry.getKey().toString() == eSlowMessenger.BLOODSUGAR.name())
    			{
    				//bloodsugar is special, make it to a stomach drive
    			    
    					//updateDevisionFactorsStomach(oEntry.getValue());
    					//clsPair<Double,Double> oStomachF =generateDevisionFactorsStomach(oEntry.getValue());
    					//shift the division factor corresponding to the responses from the oral errogenous zones
    					//oStomachFactors = shiftDevivionFactorsStomach(oStomachFactors);
    			    
    			       // addTensionChange(oEntry.getValue(), eDrive.STOMACH);
    			      //  shiftPartialValues(oEntry.getValue(), eDrive.STOMACH);
    			        
    			        
    					clsPair<Double,Double> oStomach = moLibidoBuffer.send_D1_4(eDrive.STOMACH);
    					//moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.STOMACH, oStomach.a,eDriveComponent.AGGRESSIVE));
    					//moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.STOMACH, oStomach.b,eDriveComponent.LIBIDINOUS));
    					
    					//o_LastStep.put(eDrive.STOMACH, oEntry.getValue());
    
    			}
                else if (oEntry.getKey().toString() == "RECTUM"){
                    clsPair<Double,Double> oRectumFactors = generateDivisionFactors(oEntry.getValue(),rectum_pain_limit);

                 //   moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()), oRectumFactors.a,eDriveComponent.AGGRESSIVE));
                 //   moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()), oRectumFactors.b,eDriveComponent.LIBIDINOUS));
                    
                    //if one of the drives was fallen pleasure is generated and send to pleasure storage
                    moPleasureStorage.D4_2receive(oQoA_LastStep.get("RECTUM").a - oRectumFactors.a);
                    moPleasureStorage.D4_2receive(oQoA_LastStep.get("RECTUM").b - oRectumFactors.b);
                    oQoA_LastStep.put("RECTUM", oRectumFactors);
                }
    			
                else if(oEntry.getKey().toString() == (eOrgan.STAMINA.name()))
                {   
                    double rStaminaPainLimit = 0.3;
                    clsPair<Double,Double> oStaminaFactors = generateDivisionFactors(oEntry.getValue(),rStaminaPainLimit);
                    moHomeostaticDriveComponents_OUT.add( CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()),oStaminaFactors.a,eDriveComponent.AGGRESSIVE) );
                    moHomeostaticDriveComponents_OUT.add( CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()),oStaminaFactors.b,eDriveComponent.LIBIDINOUS) );
                    
                    //if one of the drives was fallen pleasure is generated and send to pleasure storage
                    moPleasureStorage.D4_2receive(oQoA_LastStep.get("STAMINA").a - oStaminaFactors.a);
                    moPleasureStorage.D4_2receive(oQoA_LastStep.get("STAMINA").b - oStaminaFactors.b);
                    oQoA_LastStep.put("STAMINA", oStaminaFactors);
                }
                else{
                // catch all other entries 
                    //STOMACH, STOMACH_PAIN, ADRENALIN, HEALTH, ORIFICE_ORAL_AGGRESSIV_MUCOSA, ORIFICE_ORAL_LIBIDINOUS_MUCOSA

                }
    				
    			
		    }
    		catch (Exception e) {
                   e.printStackTrace();
            }
    	}
		try{
		    createDrives();
		}
		catch(Exception e){
		    
		}
		//fill charts 
		for (clsDriveMesh oMesh :moHomeostaticDriveComponents_OUT){
		    moDriveChartData.put(oMesh.getChartShortString(), oMesh.getQuotaOfAffect());
		}
*/
		
       
        clsDriveMesh oAADM = CreateDriveRepresentations(eOrgan.STOMACH, eOrifice.UNDEFINED, eDriveComponent.AGGRESSIVE, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(eDrive.STOMACH).a);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        oAADM = CreateDriveRepresentations(eOrgan.STOMACH, eOrifice.UNDEFINED, eDriveComponent.LIBIDINOUS, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(eDrive.STOMACH).b);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        
        oAADM = CreateDriveRepresentations(eOrgan.STAMINA, eOrifice.UNDEFINED, eDriveComponent.AGGRESSIVE, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(eDrive.STAMINA).a);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        oAADM = CreateDriveRepresentations(eOrgan.STAMINA, eOrifice.UNDEFINED, eDriveComponent.LIBIDINOUS, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(eDrive.STAMINA).b);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        
        eOrgan oOrgan = eOrgan.HEALTH;
        eDrive oDrive = eDrive.HEALTH;
        oAADM = CreateDriveRepresentations(oOrgan, eOrifice.UNDEFINED, eDriveComponent.AGGRESSIVE, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(oDrive).a);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        oAADM = CreateDriveRepresentations(oOrgan, eOrifice.UNDEFINED, eDriveComponent.LIBIDINOUS, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(oDrive).b);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        
        oOrgan = eOrgan.RECTUM;
        oDrive = eDrive.RECTUM;
        oAADM = CreateDriveRepresentations(oOrgan, eOrifice.UNDEFINED, eDriveComponent.AGGRESSIVE, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(oDrive).a);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        oAADM = CreateDriveRepresentations(oOrgan, eOrifice.UNDEFINED, eDriveComponent.LIBIDINOUS, ePartialDrive.UNDEFINED);
        oAADM.setQuotaOfAffect( receive_D1_4(oDrive).b);
        moHomeostaticDriveComponents_OUT.add(oAADM);
        
        //fill charts 
        for (clsDriveMesh oMesh :moHomeostaticDriveComponents_OUT){
            moDriveChartData.put(oMesh.getChartShortString(), oMesh.getQuotaOfAffect());
        }
        
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
     * Creates a DM out of the entry, and adds necessary information, source, etc
     * @throws Exception 
     *
     * @since 16.07.2012 15:20:26
     *
     */
    private clsDriveMesh CreateDriveCandidate(eDrive poDrive, double rTension, eDriveComponent peComponent) throws Exception {
        clsDriveMesh oDriveCandidate  = null;
        
        eOrgan oOrgan = moOrganMap.get(poDrive);
        eOrifice oOrifice = moOrificeMap.get(poDrive);
                
        //create a TPM for the organ
        clsThingPresentationMesh oOrganTPM = 
            (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
                    eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORGAN, new ArrayList<clsThingPresentation>(), oOrgan.toString()) );
        
        //create a TPM for the orifice
        clsThingPresentationMesh oOrificeTPM = 
            (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
                    eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORIFICE, new ArrayList<clsThingPresentation>(), oOrifice.toString()) );
        
        //create the DM
        oDriveCandidate = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, Object>(eContentType.DRIVECANDIDATE, new ArrayList<clsThingPresentationMesh>(), "") 
                ,peComponent, ePartialDrive.UNDEFINED );
        
        //supplement the information
        oDriveCandidate.setActualDriveSource(oOrganTPM, 1.0);
        
        oDriveCandidate.setActualBodyOrifice(oOrificeTPM, 1.0);
        
        oDriveCandidate.setQuotaOfAffect(rTension);
        
        oDriveCandidate.setPartialDrive(moPartialDriveMapping.get(poDrive));
        
        return oDriveCandidate;
    }
	
	/**
     * DOCUMENT (herret) - insert description
	 * @param oDrive 
	 * @param prTension 
     *
     * @since 28.05.2013 09:04:33
     *
     */
/*    private void shiftPartialValues(Double prShift, eDrive oDrive) {
        clsPair<Double,Double> oOldValues = moLibidoBuffer.send_D1_4(oDrive);
        double shiftLastStep =o_LastStep.get(oDrive);
        
        double change = prShift - shiftLastStep;
        if(change > 0){
            moLibidoBuffer.receive_D1_6(oDrive , 0.5+change);
        }
        else if (change < 0){
            moLibidoBuffer.receive_D1_6(oDrive , 0.5-change);
        }
        else{
            //no shift
        }
        
    }
*/
    private void changeDriveBuffer(HashMap<eDriveProperty, Object> poProperties, double prTension){
        
        String drive = (String) poProperties.get(eDriveProperty.ORGAN);
        eDrive oDrive = eDrive.valueOf(drive);
        
        @SuppressWarnings("unchecked")
        HashMap<String,Double> oPP = (HashMap<String,Double>) poProperties.get(eDriveProperty.PERSONALITY_PARAMETERS);
        double rErogenousZonesImpactFactor = oPP.get("EROGENOUS_ZONES_IMPACT");
        double rPersonalitySplitImpactFactor = oPP.get("PERSONALITY_SPLIT_IMPACT");;
        String oLibErogenousZone =  (String) poProperties.get(eDriveProperty.LIBIDINOUS_EROGENOUS_ZONE);
        String oAgrErogenousZone =  (String) poProperties.get(eDriveProperty.AGGRESSIV_EROGENOUS_ZONE);
        
        double rPersonalitySplitFactor =oPP.get("PERSONALITY_SPLIT");
        
        double rLibStimulation = 0.0;
        double rAgrStimulation = 0.0;
        double rAgrFactorErogenousZones;
        double rLibFactor;
        
        clsPair<Double,Double> oOldValues = moLibidoBuffer.send_D1_4(oDrive);
  
        if(moHomeostasisSymbols_IN.containsKey(oAgrErogenousZone)){
            rAgrStimulation = moHomeostasisSymbols_IN.get(oAgrErogenousZone);
        }
        if(moHomeostasisSymbols_IN.containsKey(oLibErogenousZone)){
            rLibStimulation = moHomeostasisSymbols_IN.get(oLibErogenousZone);
        }
        
        if(rAgrStimulation + rLibStimulation > 0){
            rAgrFactorErogenousZones = rAgrStimulation / (rAgrStimulation + rLibStimulation);
           // rLibFactor = rLibStimulation / (rAgrStimulation + rLibStimulation);
            moErogenousZonesSave.put(oDrive, rAgrFactorErogenousZones);
        }
        else if(moErogenousZonesSave.get(oDrive)>=0){
            rAgrFactorErogenousZones = moErogenousZonesSave.get(oDrive);
        }
        else{
            rAgrFactorErogenousZones = 0.5;
            rLibFactor = 0.5;
            rErogenousZonesImpactFactor=0.0;
        }
        double rOldValue = 0.0;
        if(oQoA_LastStep.containsKey(oDrive)){
            rOldValue = oQoA_LastStep.get(oDrive);
        }
        oQoA_LastStep.put(oDrive, prTension);
        double rTensionChange = (rOldValue)-prTension;
        if(rTensionChange > 0){
            //Mittelwert der Einflussfaktoren beim Fallen 
            double rAgrMid =(rAgrFactorErogenousZones*rErogenousZonesImpactFactor + rPersonalitySplitFactor*rPersonalitySplitImpactFactor)/(rPersonalitySplitImpactFactor  +rErogenousZonesImpactFactor);
            double rLibMid = ((1-rAgrFactorErogenousZones)*rErogenousZonesImpactFactor + (1-rPersonalitySplitFactor)*rPersonalitySplitImpactFactor )/(rPersonalitySplitImpactFactor  +rErogenousZonesImpactFactor);
            moLibidoBuffer.receive_D1_3(oDrive,new clsPair<Double,Double>(rTensionChange*rAgrMid, rTensionChange*rLibMid));
            // send the tension change to the pleasure buffer
            //moPleasureStorage.D4_2receive(rTensionChange);
        }
        else if (rTensionChange <= 0){
            double rAgrMid = (rPersonalitySplitFactor * rPersonalitySplitImpactFactor)/(rPersonalitySplitImpactFactor);
            double rLibMid = ((1-rPersonalitySplitFactor) * rPersonalitySplitImpactFactor )/(rPersonalitySplitImpactFactor);
            moLibidoBuffer.receive_D1_2(oDrive,new clsPair<Double,Double>(-rTensionChange*rAgrMid, -rTensionChange*rLibMid));
            
            //delete the Erogenous Zones value because its not needed any more
            moErogenousZonesSave.put(oDrive,-1.0);
            
            
        }
        else {
            //Stomach Tension doesn't change
        }
        
        moLibidoBuffer.receive_D1_1(oDrive,shiftComponentsCorresponingToTension(oDrive,moLibidoBuffer.send_D1_4(oDrive),oPP.get("DRIFT_IMPACT")));

    }
    
    private clsPair<Double,Double> shiftComponentsCorresponingToTension(eDrive poDrive, clsPair<Double,Double> poValues, double poImpactFactor){
        double rShiftFactor = poValues.a + poValues.b;
        double rVal = rShiftFactor-0.5;
        rShiftFactor =0.5+rVal*poImpactFactor;
        
        if(shiftFactorLastStep.containsKey(poDrive)){
            double rShiftFactorLastStep = shiftFactorLastStep.get(poDrive);
            shiftFactorLastStep.put(poDrive, rShiftFactor);
            double changeFactor = rShiftFactor-rShiftFactorLastStep;
            
            return shiftQoA(poValues,changeFactor);
      
        }
        else{
            shiftFactorLastStep.put(poDrive, rShiftFactor);
            clsPair<Double,Double> oRetVal =shiftQoA_FirstStep(poValues,rShiftFactor);
            return oRetVal;
        }
    }
    
    /**
     * DOCUMENT (herret) - insert description
     *
     * @since 27.05.2013 12:25:08
     *
     */
 /*   private void addTensionChange(double prStomachTension, eDrive poDrive) {
        double rLibStimulation = 0.0;
        double rAgrStimulation = 0.0;
        double rAgrFactor;
        double rLibFactor;
        
        clsPair<Double,Double> oOldStomachValues = moLibidoBuffer.send_D1_4(eDrive.STOMACH);
        
        if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())){
            rAgrStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString());
        }
        if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())){
            rLibStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString());
        }
        
        if(rAgrStimulation + rLibStimulation > 0){
            rAgrFactor = rAgrStimulation / (rAgrStimulation + rLibStimulation);
            rLibFactor = rLibStimulation / (rAgrStimulation + rLibStimulation);
        }
        else{
            rAgrFactor = 0.5;
            rLibFactor = 0.5;
        }
        
       //shifting der existierenden Werte
        double shiftFactor = (prStomachTension+0.5)/2;
        
        double oPersAggr =0.4; //TODO: ersetzten druch PP
        
        double Aggrfactor;
        double Libfactor;
        if(o_LastStep.get(poDrive)!= 0){
            Aggrfactor = shiftFactor/o_LastStep.get(poDrive);
            Libfactor = (1-shiftFactor)/(1-o_LastStep.get(poDrive));
            
        }
        else{
            Aggrfactor=shiftFactor;
            Libfactor = (1-shiftFactor);
        }
        o_LastStep.put(poDrive,shiftFactor);
        System.out.println ("Stomach tension:"+prStomachTension);
        System.out.println ("Shift Factor:"+shiftFactor);

        System.out.println ("##################"+Aggrfactor+"..."+Libfactor);
        System.out.println(oOldStomachValues.a*Aggrfactor);
        System.out.println(oOldStomachValues.b*Libfactor);
        //moLibidoBuffer.receive_D1_1(poDrive, new clsPair<Double,Double>(oOldStomachValues.a*Aggrfactor,oOldStomachValues.b*Libfactor));
        
        double rTensionChange = (oOldStomachValues.a + oOldStomachValues.b)-prStomachTension;
        if(rTensionChange > 0){
            //Mittelwert der Einflussfaktoren beim Fallen 
            double rAgrMid =rAgrFactor*0.7 + oPersAggr*0.0 + 0.4+0.3;
            double rLibMid = rLibFactor*0.7 + (1-oPersAggr)*0.0+0.6*0.3;
            moLibidoBuffer.receive_D1_3(poDrive,new clsPair<Double,Double>(rTensionChange*rAgrMid, rTensionChange*rLibMid));
            // send the tension change to the pleasure buffer
            moPleasureStorage.D4_2receive(rTensionChange);
        }
        else if (rTensionChange < 0){
            //TODO: Change from hardcoded to personality parameter
            double rAgrMid = oPersAggr*0 + 0.6*1;
            double rLibMid = (1-oPersAggr)*0 + 0.4*1;
            moLibidoBuffer.receive_D1_2(poDrive,new clsPair<Double,Double>(-rTensionChange*rAgrMid, -rTensionChange*rLibMid));
            
            
        }
        else {
            //Stomach Tension doesn't change
        }
        
        
        
    }
*/
 /*   private void createDrives() throws Exception{
	    HashMap<eDrive,clsPair<Double,Double>> moDriveBuffer = moLibidoBuffer.send_D1_5();
	    for(Map.Entry<eDrive,clsPair<Double,Double>> oEntry : moDriveBuffer.entrySet()){
	        moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(moOrganMap.get(oEntry.getKey()), oEntry.getValue().a,eDriveComponent.AGGRESSIVE));
            moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(moOrganMap.get(oEntry.getKey()), oEntry.getValue().b,eDriveComponent.AGGRESSIVE));

	        
	    }
	}
*/	
/*	private clsPair<Double,Double>shiftQoA_alt(clsPair<Double,Double> prValues, double prRatio){
        double rSum = prValues.a + prValues.b;
        double rAggrFactor = prValues.a/rSum;
        double d= 0.0;
        double k = 0.0;
        if(prRatio>=0.5){
            k=(1-prRatio)/0.5;
            d=1-k;
        }
        else{
            k=prRatio/0.5;
            d=0.0;
        }
        rAggrFactor = rAggrFactor*k + d;
        
        return new clsPair<Double,Double>(rSum*rAggrFactor,rSum*(1-rAggrFactor));
    }
    */
    private clsPair<Double,Double>shiftQoA_FirstStep(clsPair<Double,Double> prValues, double prRatio){
        double rSum = prValues.a + prValues.b;
        double rAggrFactorOld = prValues.a/rSum;
        if(rSum == 0.0 ) rAggrFactorOld = 0.0;
        double rAggrFactorNew = (rAggrFactorOld + prRatio)/2;
        
        clsPair<Double,Double> oRetVal = new clsPair<Double,Double>(rSum*rAggrFactorNew,rSum*(1-rAggrFactorNew));
        return oRetVal;
    }
    private clsPair<Double,Double>shiftQoA(clsPair<Double,Double> prValues, double prRatio){
        double rShift = prRatio;
        if(rShift>=0){
            prValues.a += prValues.b*rShift;
            prValues.b -= prValues.b*rShift;
        }
        else if (rShift <0){
            prValues.b += prValues.a*(-rShift);
            prValues.a -= prValues.a*(-rShift);
        }
        else{
            
        }
        
        return prValues;
    }
	
	private void updateDevisionFactorsStomach(double prStomachTension) {
        clsPair<Double,Double> oOldStomachValues = moLibidoBuffer.send_D1_4(eDrive.STOMACH);
        
	    double rChange = prStomachTension - (oOldStomachValues.a + oOldStomachValues.b);
	    double rStomachTensionFactor = prStomachTension;
	        
	    double rPersonalityFactor = 0.7;
	    double rChangeFactor = rStomachTensionFactor * 0.8 + rPersonalityFactor * 0.2;
	    
	    double rErrogenousZonesFactor =0.5;
	    if(rChange <0 ){
            double rAgrStimulation =0.0;
            double rLibStimulation =0.0;
            if(moHomeostasisSymbols_IN.containsKey("ORIFICE_ORAL_AGGRESSIV_MUCOSA")){
                rAgrStimulation = moHomeostasisSymbols_IN.get("ORIFICE_ORAL_AGGRESSIV_MUCOSA");
            }
            if(moHomeostasisSymbols_IN.containsKey("ORIFICE_ORAL_LIBIDINOUS_MUCOSA")){
                rLibStimulation = moHomeostasisSymbols_IN.get("ORIFICE_ORAL_LIBIDINOUS_MUCOSA");
            }
            if(rAgrStimulation + rLibStimulation > 0){
                rErrogenousZonesFactor = rAgrStimulation / (rAgrStimulation + rLibStimulation);
            }
            rChangeFactor = rErrogenousZonesFactor * 0.5 + rStomachTensionFactor * 0.4 + rPersonalityFactor * 0.1;
	    }
	    
	    moLibidoBuffer.receive_D1_2(eDrive.STOMACH, new clsPair<Double,Double>(rChange*rChangeFactor, rChange*(1-rChangeFactor)));
        
        //TODO: fixme
/*        double rTensionFactor = prStomachTension;
        
        if(rChange > 0){ // Stomach tension is rising 
            moLibidoBuffer.receive_D1_2(eDrive.STOMACH, shiftValues(new clsPair<Double,Double>(rChange/2,rChange/2),rTensionFactor));
        }
        else if (rChange < 0 ) {// Stomach tension is falling 
            double rAgrStimulation =0.0;
            double rLibStimulation =0.0;
            if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())){
                rAgrStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString());
            }
            if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())){
                rLibStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString());
            }
            double rAgrFactor = 0.5;
            double rLibFactor = 0.5;
            if(rAgrStimulation + rLibStimulation > 0){
                rAgrFactor = rAgrStimulation / (rAgrStimulation + rLibStimulation);
                rLibFactor = rLibStimulation / (rAgrStimulation + rLibStimulation);
            }
            moLibidoBuffer.receive_D1_3(eDrive.STOMACH, shiftValues(new clsPair<Double,Double>(-rChange*rAgrFactor,-rChange*rLibFactor),rTensionFactor));
            
        }
        else{// Stomach tension is equal 
            //nothing happens
        }
        */
        /* depending on the stomach tension the portion factor is shifted */
       // moLibidoBuffer.receive_D1_6(eDrive.STOMACH, (prStomachTension-0.5)*0.5+0.5);
	}
	
/*	private clsPair<Double,Double> shiftValues(clsPair<Double,Double> poInput, double prShiftFactor){
	    double rSum =poInput.a+poInput.b;
	    double rFactorOld = poInput.a/rSum;
	    double rFactorNew = rFactorOld/0.5*prShiftFactor;
	    
	    return new clsPair<Double,Double>(rSum*rFactorNew,rSum*(1-rFactorNew));
	    
	}
*/	
    /**
     * Method to calculate the QoA of the aggressiv and libidinous parts of the Stomach drive
     * if QoA of hole stomach drive is rising: rise the aggressiv and libidinous 50/50
     * if QoA of hole stomach drive is falling: rise the aggressiv and libidinous parts corresponding to the oral erogenous zone
     * if QoA of hole stomach drive isn't rising or falling: aggressiv and libidinous parts stay constant   
     *
     * @since Apr 4, 2013 11:04:42 AM
     *
     */
/*    private clsPair<Double,Double> generateDevisionFactorsStomach(double prStomachTension) {
        double rAgrTensionOld = 0.0;
        double rLibTensionOld = 0.0;
        double rAgrTension = 0.0;
        double rLibTension = 0.0;
        double rAgrFactor;
        double rLibFactor;
        
        double rLibStimulation = 0.0;
        double rAgrStimulation = 0.0;
        if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())){
            rAgrStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString());
        }
        if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())){
            rLibStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString());
        }
        
        if(rAgrStimulation + rLibStimulation > 0){
            rAgrFactor = rAgrStimulation / (rAgrStimulation + rLibStimulation);
            rLibFactor = rLibStimulation / (rAgrStimulation + rLibStimulation);
        }
        else{
            rAgrFactor = 0.5;
            rLibFactor = 0.5;
        }
        
        rAgrTensionOld = oQoA_LastStep.get("STOMACH").a;
        rLibTensionOld = oQoA_LastStep.get("STOMACH").b;
        
        
        
        double rTensionChange = (rAgrTensionOld+rLibTensionOld)-prStomachTension;
        if(rTensionChange > 0){
            double rRelativAgrFactor=0.0;
            rRelativAgrFactor=rAgrFactor/(rLibFactor + rAgrFactor);
            
            //falling stomach tension
            rAgrTension = rAgrTensionOld - rTensionChange * (rRelativAgrFactor);
            //rLibTension = rLibTensionOld - rTensionChange * (0.7);
            
            if(rAgrTension < 0 ) rAgrTension = 0;
            
            if(rAgrTension > prStomachTension) rAgrTension = prStomachTension;
            rLibTension = prStomachTension-rAgrTension;
            
            // send the tension change to the pleasure buffer
            moPleasureStorage.D4_2receive(rTensionChange);
        }
        else if (rTensionChange < 0){
            //rising stomach tension
            rAgrTension = (rAgrTensionOld - (rTensionChange / 2)) ;
            
            if(rAgrTension > prStomachTension) rAgrTension = prStomachTension;
            rLibTension = prStomachTension - rAgrTension; 
        }
        else {
            //Stomach Tension doesn't change
            rAgrTension = rAgrTensionOld;
            rLibTension = rLibTensionOld;
        }
        
        
        oQoA_LastStep.put("STOMACH", new clsPair<Double,Double>(rAgrTension,rLibTension));
        // depending on the stomach tension the portion factor is shifted to aggressiv or libidinous
        return shiftQoA(new clsPair<Double,Double>(rAgrTension,rLibTension),(prStomachTension-0.5)*0.5+0.5);
    }
    
    private clsPair<Double,Double> generateDivisionFactors(double prTension, double prPainFactor){
        
        double rAgrTension = 0.0;
        double rLibTension = 0.0;
        //get External associated Content
        double rPartitionFactor;
        
       
        rPartitionFactor =prTension*(1/prPainFactor);
        
        
        if(rPartitionFactor > 1.0) rPartitionFactor = 1.0;
        if(rPartitionFactor < 0.0) rPartitionFactor = 0.0;
        
        
        
        rAgrTension = prTension*rPartitionFactor;
        rLibTension = prTension-rAgrTension;
        

        return new clsPair<Double,Double>(rAgrTension,rLibTension);
    }
*/
    /**
	 * Take the normalization map and produces values ready for translation to psy
	 *
	 * @since 16.07.2012 14:44:30
	 *
	 * @param moHomeostasisSymbols_IN2
	 * @return
	 */
	private HashMap<String, Double> NormalizeHomeostaticSymbols( HashMap<String, Double> poHomeostasisSymbols) {
		
		// look at every source of a demand, and normalize it according to the normalization map
		for( Entry<String, Double> oEntry : poHomeostasisSymbols.entrySet())
		{
			double rEntryTension = oEntry.getValue();
			
			//any special normalization needed for special types? do it here:
			//Special STOMACH_PAIN
			if(oEntry.getKey() == "STOMACH_PAIN" )
			{
				rEntryTension /= 7;
			}
			
			if(oEntry.getKey() == "RECTUM" )
            {
                rEntryTension /= 1;
            }
			//Special HEALTH
			if(oEntry.getKey() == "HEALTH")
			{
				rEntryTension = (1 - (rEntryTension / 100)) * 6.0;
				if(rEntryTension>1)
				{ rEntryTension = 1;
				}
			}
			
			//Special STOMACH
			if(oEntry.getKey() == "STOMACH")
			{
				rEntryTension = 1-rEntryTension ;
			}
			

			//Special STOMACH
			if(oEntry.getKey() == "BLOODSUGAR")
			{
				rEntryTension = 1-rEntryTension ;
			}
			
			//Special STAMINA
			if(oEntry.getKey() == "STAMINA")
			{
				rEntryTension = (1-rEntryTension)*2 ;
			}
			
			
			

			//if we have a normalization factor, use it
			if(moHomeostaisImpactFactors.containsKey( oEntry.getKey() ) )
			{
				try 
				{
					double rImpactFactor = moHomeostaisImpactFactors.get(oEntry.getKey());
					rEntryTension = rEntryTension * rImpactFactor;
				} catch (java.lang.Exception e) {
					log.error("Error in "  + this.getClass().getSimpleName() , e);

				}
			}
			
			
			
			// they can never be above 1 or below zero
			if (rEntryTension > 1.0) {
				rEntryTension = 1.0;
			} else if (rEntryTension < 0.0) {
				rEntryTension = 0.0;
			}
			
			oEntry.setValue(rEntryTension);
		}
	
		return poHomeostasisSymbols;
	}
	
	/**
	 * Creates a DM out of the entry, and adds necessary information, source, etc
	 * @throws Exception 
	 *
	 * @since 16.07.2012 15:20:26
	 *
	 */
	private clsDriveMesh CreateDriveCandidate(eOrgan poOrgan, double rTension, eDriveComponent peComponent) throws Exception {
		clsDriveMesh oDriveCandidate  = null;
		eOrifice oOrifice = moOrificeMap.get(poOrgan);
		
		//create a TPM for the organ
		clsThingPresentationMesh oOrganTPM = 
			(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
					eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORGAN, new ArrayList<clsThingPresentation>(), poOrgan.toString()) );
		
		//create a TPM for the orifice
		clsThingPresentationMesh oOrificeTPM = 
			(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
					eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORIFICE, new ArrayList<clsThingPresentation>(), oOrifice.toString()) );
		
		//create the DM
		oDriveCandidate = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, Object>(eContentType.DRIVECANDIDATE, new ArrayList<clsThingPresentationMesh>(), "") 
				,peComponent, ePartialDrive.UNDEFINED );
		
		//supplement the information
		oDriveCandidate.setActualDriveSource(oOrganTPM, 1.0);
		
		oDriveCandidate.setActualBodyOrifice(oOrificeTPM, 1.0);
		
		oDriveCandidate.setQuotaOfAffect(rTension);
		
		return oDriveCandidate;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (herret) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_4(moHomeostaticDriveComponents_OUT);
		

	}


	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:56:16 PM
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
	    moDescription ="F65 receives the neuro-symbolic homeostatic values from F2 and generates the corresponding drives.";

	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:57:27 PM
	 * 
	 * @see pa._v38.interfaces.modules.I3_4_send#send_I3_4(java.util.ArrayList)
	 */
	@Override
	public void send_I3_4(
			ArrayList<clsDriveMesh> poDriveComponents) {
		((I3_4_receive)moModuleList.get(48)).receive_I3_4(poDriveComponents);
		putInterfaceData(I3_4_send.class, poDriveComponents);
		
	}

	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 1:57:28 PM
	 * 
	 * @see pa._v38.interfaces.modules.I2_2_receive#receive_I2_2(java.util.HashMap)
	 */
	@Override
	public void receive_I2_2(HashMap<String, Double> poHomeostasisSymbols) {
		moHomeostasisSymbols_IN = poHomeostasisSymbols;
		
	}

	   /*************************************************************/
    /***                   TIME CHART METHODS                  ***/
    /*************************************************************/
    
    /* (non-Javadoc)
     *
     * @since 24.07.2012 15:51:16
     * 
     * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
     */
    @Override
    public double getTimeChartUpperLimit() {
        return 1.1;
    }

    /* (non-Javadoc)
     *
     * @since 24.07.2012 15:51:16
     * 
     * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
     */
    @Override
    public double getTimeChartLowerLimit() {
        return -0.1;
    }

    /* (non-Javadoc)
     *
     * @since 24.07.2012 15:51:16
     * 
     * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
     */
    @Override
    public String getTimeChartAxis() {
        return "0 to 1";
    }

    /* (non-Javadoc)
     *
     * @since 24.07.2012 15:51:16
     * 
     * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
     */
    @Override
    public String getTimeChartTitle() {
        return "self-preservation drives";
    }

    /* (non-Javadoc)
     *
     * @since 24.07.2012 15:51:16
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
     * @since 24.07.2012 15:51:16
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
     * @since 24.07.2012 15:51:16
     * 
     * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
     */
    @Override
    public boolean chartColumnsChanged() {
        return mnChartColumnsChanged;
    }

    /* (non-Javadoc)
     *
     * @since 24.07.2012 15:51:16
     * 
     * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
     */
    @Override
    public void chartColumnsUpdated() {
        mnChartColumnsChanged  = false;      
    }

    /*  
    private clsPair<Double,Double> shiftDevivionFactorsStomach(clsPair<Double,Double> prDivisionFactorsOld){
            double rAgrTensionOld = prDivisionFactorsOld.a;
            double rLibTensionOld = prDivisionFactorsOld.b;
            double rStomachTension = rAgrTensionOld + rLibTensionOld;
            
            
            double rAgrTensionLastStep = oQoA_LastStep.get("STOMACH").a;
            double rLibTensionLastStep = oQoA_LastStep.get("STOMACH").b;
            double rAgrFactor;

            
            
            double rLibStimulation = 0.0;
            double rAgrStimulation = 0.0;
            if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())){
                rAgrStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString());
            }
            if(moHomeostasisSymbols_IN.containsKey(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())){
                rLibStimulation = moHomeostasisSymbols_IN.get(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString());
            }
            
            if(rAgrStimulation + rLibStimulation > 0){
                rAgrFactor = rAgrStimulation / (rAgrStimulation + rLibStimulation);
            }
            else{
                rAgrFactor = 0.5;
            }
            
            double shiftFactor = shiftFactorLastStep.get("STOMACH");
            double rTensionChange = (rAgrTensionLastStep+rLibTensionLastStep)-rStomachTension;
          //falling stomach tension
            if(rTensionChange > 0){
                shiftFactor += ((1-rAgrFactor)-0.5)*rTensionChange;//*2;
                if(shiftFactor>1)shiftFactor =1;
                else if (shiftFactor <0) shiftFactor =0;
                
                
              // rAgrTension = rAgrTensionOld - rTensionChange * rAgrFactor;
              // rLibTension = rLibTensionOld - rTensionChange * rLibFactor;
            }
          //rising stomach tension
            else {
                if(shiftFactor > 0.5){
                    shiftFactor -= 0.005;
                    if(shiftFactor < 0.5)shiftFactor = 0.5;
                }
                else if (shiftFactor <0.5){ 
                    shiftFactor += 0.02;
                    if(shiftFactor > 0.5) shiftFactor = 0.5;
                }
                
            }
            
            shiftFactorLastStep.put("STOMACH",shiftFactor);
            clsPair<Double,Double> oRetVal =shiftQoA(prDivisionFactorsOld, shiftFactor);
            oQoA_LastStep.put("STOMACH",oRetVal);
            return oRetVal;
            
           // return new clsPair<Double,Double>(rAgrTension,rLibTension);

        
        
        
    }
*/
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
