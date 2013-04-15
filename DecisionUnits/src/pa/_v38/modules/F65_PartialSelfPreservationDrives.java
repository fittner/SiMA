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
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I2_2_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I3_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.personality.parameter.clsPersonalityParameterContainer;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;
import du.enums.eFastMessengerSources;
import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * F65 receives the neuro-symbolic homeostatic values from F2 and generates the corresponding drives.
 * 
 * @author herret
 * Apr 2, 2013, 1:56:16 PM
 * 
 */
public class F65_PartialSelfPreservationDrives extends clsModuleBase implements I2_2_receive, I3_4_send, itfInspectorGenericDynamicTimeChart{

	
	public static final String P_MODULENUMBER = "65";
	public static final String P_HOMEOSTASIS_STOMACH = "HOMEOSTASIS_IMPACT_FACTOR_STOMACH";
	public static final String P_HOMEOSTASIS_RECTUM = "HOMEOSTASIS_IMPACT_FACTOR_RECTUM";
	public static final String P_HOMEOSTASIS_STAMINA = "HOMEOSTASIS_IMPACT_FACTOR_STAMINA";
	public static final String P_RECTUM_PAIN_LIMIT = "RECTUM_PAIN_LIMIT";
	
	private ArrayList<clsDriveMesh> moHomeostaticDriveComponents_OUT;
	private HashMap<String, Double> moHomeostasisSymbols_IN;
	private double rectum_pain_limit;
	
	
	private HashMap<String, Double> moCandidatePartitionFactor;
	
    private HashMap<String,clsPair<Double,Double>> oQoA_LastStep;
    private HashMap<String,Double> shiftFactorLastStep;
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	private HashMap<eOrgan, eOrifice> moOrificeMap;
	
	//einfluess auf die normalisierung von body -> psyche
	private HashMap<String, Double> moHomeostaisImpactFactors;
    
	//charts
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData; 
    
    
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
			clsPersonalityParameterContainer poPersonalityParameterContainer)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		rectum_pain_limit = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_RECTUM_PAIN_LIMIT).getParameterDouble();
		
		moHomeostaisImpactFactors = new HashMap<String, Double>();
		moHomeostaisImpactFactors.put("STOMACH",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_STOMACH).getParameterDouble());
		moHomeostaisImpactFactors.put("RECTUM",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_RECTUM).getParameterDouble());
		moHomeostaisImpactFactors.put("STAMINA",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_STAMINA).getParameterDouble());

		oQoA_LastStep = new HashMap<String,clsPair<Double,Double>>();
		oQoA_LastStep.put("STOMACH", new clsPair<Double,Double>(0.0,0.0));
		shiftFactorLastStep = new HashMap<String,Double>();
		shiftFactorLastStep.put("STOMACH", 0.5);
		fillOrificeMapping();
		
		moDriveChartData = new HashMap<String,Double>();
	}
	
	private void fillOrificeMapping() {
		//this mapping is fixed for the PA body, no changes! (cm 18.07.2012)
		moOrificeMap = new HashMap<eOrgan, eOrifice>();
		moOrificeMap.put(eOrgan.RECTUM, eOrifice.RECTAL_MUCOSA);
		moOrificeMap.put(eOrgan.STAMINA, eOrifice.TRACHEA);
		moOrificeMap.put(eOrgan.BLADDER, eOrifice.URETHRAL_MUCOSA);
		moOrificeMap.put(eOrgan.STOMACH, eOrifice.ORAL_MUCOSA);
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
		//OVERVIEW: from the body-symbol-tension list, create a set of psychic datastructures that represent the demands+sources+tensions
		HashMap<String, Double> oNormalizedHomeostatsisSymbols = null;
		
		// 1- Normalization of bodily demands according to table
		oNormalizedHomeostatsisSymbols = NormalizeHomeostaticSymbols(moHomeostasisSymbols_IN);
		
		
		moHomeostaticDriveComponents_OUT = new ArrayList<clsDriveMesh>();
		//2 - search for old QoA
		
		
		// 3- create a drivecandidate for every entry in the list, set the tension, organ orifice
		for( Entry<String, Double> oEntry : oNormalizedHomeostatsisSymbols.entrySet())
		{

		    try{
    			if(oEntry.getKey().toString() == eSlowMessenger.BLOODSUGAR.name())
    			{
    				//bloodsugar is special, make it to a stomach drive
    			        double rStomachPainLimit = 1.0;
    					clsPair<Double,Double> oStomachFactors = generateDivisionFactors(oEntry.getValue(),rStomachPainLimit, 0.5);
    					
    					//shift the division factor corresponding to the responses from the oral errogenous zones
    					oStomachFactors = shiftDevivionFactorsStomach(oStomachFactors);
    					moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.STOMACH, oStomachFactors.a,eDriveComponent.AGGRESSIVE));
    					moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.STOMACH, oStomachFactors.b,eDriveComponent.LIBIDINOUS));
    					
    					
    
    			}
                else if (oEntry.getKey().toString() == "RECTUM"){
                    clsPair<Double,Double> oRectumFactors = generateDivisionFactors(oEntry.getValue(),rectum_pain_limit,1.0);
                    moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()), oRectumFactors.a,eDriveComponent.AGGRESSIVE));
                    moHomeostaticDriveComponents_OUT.add(CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()), oRectumFactors.b,eDriveComponent.LIBIDINOUS));
                }
    			
                else if(oEntry.getKey().toString() == (eOrgan.STAMINA.name()))
                {   
                    double rStaminaPainLimit = 0.3;
                    clsPair<Double,Double> oStaminaFactors = generateDivisionFactors(oEntry.getValue(),rStaminaPainLimit,1.0);
                    moHomeostaticDriveComponents_OUT.add( CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()),oStaminaFactors.a,eDriveComponent.AGGRESSIVE) );
                    moHomeostaticDriveComponents_OUT.add( CreateDriveCandidate(eOrgan.valueOf(oEntry.getKey()),oStaminaFactors.b,eDriveComponent.LIBIDINOUS) );
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
		
		//fill charts 
		for (clsDriveMesh oMesh :moHomeostaticDriveComponents_OUT){
		    moDriveChartData.put(oMesh.getChartShortString(), oMesh.getQuotaOfAffect());
		}
	
		

	}
	
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
	
	private clsPair<Double,Double>shiftQoA(clsPair<Double,Double> prValues, double prRatio){
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
	
    /**
     * Method to calculate the QoA of the aggressiv and libidinous parts of the Stomach drive
     * if QoA of hole stomach drive is rising: rise the aggressiv and libidinous 50/50
     * if QoA of hole stomach drive is falling: rise the aggressiv and libidinous parts corresponding to the oral erogenous zone
     * if QoA of hole stomach drive isn't rising or falling: aggressiv and libidinous parts stay constant   
     *
     * @since Apr 4, 2013 11:04:42 AM
     *
     */
    private clsPair<Double,Double> generateDevisionFactorsStomach(double prStomachTension) {
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
            //rLibTension = rLibTensionOld - rTensionChange *(0.7);
            
            if(rAgrTension < 0 ) rAgrTension = 0;
            
            if(rAgrTension > prStomachTension) rAgrTension = prStomachTension;
            rLibTension = prStomachTension-rAgrTension;
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
        return new clsPair<Double,Double>(rAgrTension,rLibTension);
    }
    
    private clsPair<Double,Double> generateDivisionFactors(double prTension, double prPainFactor, double scalingFactor){
        
        double rAgrTension = 0.0;
        double rLibTension = 0.0;
        //get External associated Content
        double rPartitionFactor;
        
       
        rPartitionFactor =prTension*(1/prPainFactor);
        // scales the distance of partial drives to each other
        rPartitionFactor -= (rPartitionFactor-0.5)*scalingFactor;
        
        
        if(rPartitionFactor > 1.0) rPartitionFactor = 1.0;
        if(rPartitionFactor < 0.0) rPartitionFactor = 0.0;
        
        
        
        rAgrTension = prTension*rPartitionFactor;
        rLibTension = prTension-rAgrTension;
        
        return new clsPair<Double,Double>(rAgrTension,rLibTension);
    }

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
			//Special HEALTH
			if(oEntry.getKey() == eSensorIntType.HEALTH.name())
			{
				rEntryTension /= 100;
			}
			
			//Special STOMACH
			if(oEntry.getKey() == eSensorIntType.STOMACH.name())
			{
				rEntryTension = 1-rEntryTension ;
			}
			

			//Special STOMACH
			if(oEntry.getKey() == eSlowMessenger.BLOODSUGAR.name())
			{
				rEntryTension = 1-rEntryTension ;
			}
			
			//Special STAMINA
			if(oEntry.getKey() == eSensorIntType.STAMINA.name())
			{
				rEntryTension = 1-rEntryTension ;
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

}
