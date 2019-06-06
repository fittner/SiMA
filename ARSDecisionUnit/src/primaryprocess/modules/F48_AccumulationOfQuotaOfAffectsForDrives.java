/**
 * F48_AccumulationOfAffectsForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author muchitsch
 * 02.05.2011, 15:47:11
 */
package primaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import base.datatypes.enums.eOrgan;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import secondaryprocess.modules.F29_EvaluationOfImaginaryActions;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eDrive;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import memorymgmt.storage.DT4_PleasureStorage;
import memorymgmt.storage.DT5_LearningIntensityBuffer;
import modules.interfaces.I3_3_receive;
import modules.interfaces.I3_4_receive;
import modules.interfaces.I4_1_receive;
import modules.interfaces.I4_1_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.eOrifice;
import base.datatypes.enums.ePartialDrive;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.clsDriveMeshBEComparator;
import base.tools.clsDriveMeshQoAComparator;
import base.tools.toText;

/**
 * F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
 * on a splitter mechanism and outputs the result to a list of drive candidates.
 * 
 * @author muchitsch
 * 07.05.2012, 15:47:11
 */
public class F48_AccumulationOfQuotaOfAffectsForDrives extends clsModuleBase 
					implements I3_3_receive, I3_4_receive, I4_1_send, itfInspectorGenericDynamicTimeChart {
	public static final String P_MODULENUMBER = "48";


	private DT4_PleasureStorage moPleasureStorage;
	private DT1_PsychicIntensityBuffer moLibidoBuffer;
	DT5_LearningIntensityBuffer LIB;
	public static  double moLearningIntensityBuffer = 0.0;
    private double mnCurrentPleasure = 0.0;
	private double mnCurrentAggr = 0.0;
	private double mnCurrentLibido = 0.0;
	private double mnCurrentUnpleasure = 0.0;
	private double mnCurrentLearningIntensity = 0.0;
	private double mnCurrentMaxPleasureLiSto = 0.0;
    
	//We make use of DT3 in order to measure the ratio of actually used psychic intensity to
	//demanded psychic intensity (actuallyUsedIntensity/demandedIntensity). This ratio (now) represents
	//the extra pleasure gained and will be added to the mnCurrentPleasure of F48.
	//Aldo Martinez
	private DT3_PsychicIntensityStorage moPsychicIntensityStorage; //Aldo Martinez
	private double mnPsychicIntensityPleasure = 0.0; //Aldo Martinez
	
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData;

	private ArrayList<clsDriveMesh> moHomoestasisDriveComponents_IN;
	//holds the list of all sexual and homeoststic drives
	private ArrayList<clsDriveMesh> moAllDriveComponents_OUT;
	private ArrayList<clsDriveMesh> moDriveCanditates_OUT;

	private ArrayList<clsDriveMesh> moSexualDriveRepresentations_IN;
	
	private HashMap<eDrive, eOrifice> moOrificeMap;
	private HashMap<eDrive, eOrgan> moOrganMap;
	private HashMap<eDrive, ePartialDrive> moPartialDriveMapping;
	
	public static clsThingPresentationMesh moLastObject = null;
	public static clsThingPresentationMesh moLastAction = null;
	
	public static boolean change=false;
	

	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 *F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
	 * on a splitter mechanism and outputs the result to a list of drive candidates.
	 * 
	 * @author muchitsch
	 * 02.05.2011, 15:48:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F48_AccumulationOfQuotaOfAffectsForDrives(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage, DT1_PsychicIntensityBuffer poLibidoBuffer, 
			clsPersonalityParameterContainer poPersonalityParameterContainer, DT3_PsychicIntensityStorage poDT3_IntensityStorage, int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);

		moPleasureStorage = poPleasureStorage;
		moLibidoBuffer = poLibidoBuffer;
		moPsychicIntensityStorage = poDT3_IntensityStorage;
		moDriveChartData =  new HashMap<String, Double>(); //initialize charts
		
		fillOrificeMapping();
		fillOrganMapping();
		fillPartialDriveMapping();
		
		applyProperties(poPrefix, poProp);	
	}
	   private void fillOrificeMapping() {
	        //this mapping is fixed for the PA body, no changes! (cm 18.07.2012)
	        moOrificeMap = new HashMap<eDrive, eOrifice>();
	        moOrificeMap.put(eDrive.RECTUM, eOrifice.RECTAL_MUCOSA);
	        moOrificeMap.put(eDrive.STAMINA, eOrifice.TRACHEA);
	        moOrificeMap.put(eDrive.STOMACH, eOrifice.ORAL_MUCOSA);
	        moOrificeMap.put(eDrive.HEALTH, eOrifice.TRACHEA);
            moOrificeMap.put(eDrive.ANAL, eOrifice.RECTAL_MUCOSA);
	        moOrificeMap.put(eDrive.ORAL, eOrifice.ORAL_MUCOSA);
	        moOrificeMap.put(eDrive.PHALLIC, eOrifice.PHALLUS);
	        moOrificeMap.put(eDrive.GENITAL, eOrifice.MALE_GENITAL);
	        
	        log.debug("Mapping orifice -> drive: "+moOrificeMap.toString());
	    }
       private void fillOrganMapping() {
           moOrganMap = new HashMap<eDrive, eOrgan>();
           moOrganMap.put(eDrive.STOMACH, eOrgan.STOMACH);
           moOrganMap.put(eDrive.RECTUM, eOrgan.RECTUM);
           moOrganMap.put(eDrive.STAMINA, eOrgan.STAMINA);
           moOrganMap.put(eDrive.HEALTH, eOrgan.HEALTH);
           moOrganMap.put(eDrive.ANAL, eOrgan.LIBIDO);
           moOrganMap.put(eDrive.ORAL, eOrgan.LIBIDO);
           moOrganMap.put(eDrive.PHALLIC, eOrgan.LIBIDO);
           moOrganMap.put(eDrive.GENITAL, eOrgan.LIBIDO);
           
           log.debug("Mapping organ -> drive: "+moOrganMap.toString());

       }
       private void fillPartialDriveMapping() {
           moPartialDriveMapping = new HashMap<eDrive, ePartialDrive>();
           moPartialDriveMapping.put(eDrive.STOMACH, ePartialDrive.UNDEFINED);
           moPartialDriveMapping.put(eDrive.RECTUM, ePartialDrive.UNDEFINED);
           moPartialDriveMapping.put(eDrive.STAMINA, ePartialDrive.UNDEFINED);
           moPartialDriveMapping.put(eDrive.HEALTH, ePartialDrive.UNDEFINED);
           moPartialDriveMapping.put(eDrive.ANAL, ePartialDrive.ANAL);
           moPartialDriveMapping.put(eDrive.ORAL, ePartialDrive.ORAL);
           moPartialDriveMapping.put(eDrive.PHALLIC, ePartialDrive.PHALLIC);
           moPartialDriveMapping.put(eDrive.GENITAL, ePartialDrive.GENITAL);
           
           log.debug("Mapping partialdrive -> drive: "+moOrganMap.toString());


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

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("HomIN", moHomoestasisDriveComponents_IN);
		text += toText.listToTEXT("SexIN", moSexualDriveRepresentations_IN);
		text += toText.listToTEXT("OUT", moAllDriveComponents_OUT);
	    text += toText.listToTEXT("DM-Canditate", moDriveCanditates_OUT); 
		
		text += toText.valueToTEXT("Pleasure", mnCurrentPleasure);	
		
				
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		moAllDriveComponents_OUT = new ArrayList<clsDriveMesh>();
		
		generateAllDrives();
		
		//first calculate the tensions for homoestatic drives
	    for( clsDriveMesh oHeastaticDMPairEntry : moHomoestasisDriveComponents_IN){
	          //moAllDriveComponents_OUT.add(oHeastaticDMPairEntry);
	    }
		
		//second calculate the tensions for sexual drives
	    for( clsDriveMesh oSexualDMPairEntry : moSexualDriveRepresentations_IN){
	         // moAllDriveComponents_OUT.add(oSexualDMPairEntry);
	    }
		
	    try {
            clsThingPresentationMesh moObject = F29_EvaluationOfImaginaryActions.moTPM_Object;
            clsThingPresentationMesh moAction = F46_MemoryTracesForPerception.moAction;
            if(moObject != null && moAction != null)
            {
                if(moLastObject==null)
                {
                    moLastObject = moObject;
                }
                if(moLastAction==null)
                {
                    moLastAction = moAction;
                }
                if(  !(moLastObject.isEquivalentDataStructure(moObject))
                  || !(moLastAction.isEquivalentDataStructure(moAction))
                  )
                {
                    change = true;
                }
            }
            moLastObject = moObject;
            moLastAction = moAction;
        } catch (Exception e) {
            // TODO (noName) - Auto-generated catch block
            //e.printStackTrace();
        }
	    
		//calculate the pleasure gain from reduced tensions for DT4
	    //+ the pleasure gain from the efficient use of psychic intensity in F56 (Aldo Martinez)
	      clsShortTermMemoryMF test = new clsShortTermMemoryMF(null);
	        if(test.getChangedMoment())
	        {
	            ProcessPleasureCalculation();
	        }
		
		//add some meaningfull information to the debug info, comment this out for performance
		AddDebugInfoForUsProgrammers(this.moAllDriveComponents_OUT);
		mnCurrentLibido = 0;
		mnCurrentAggr = 0;
		
        /* Sum up all aggessif and libidinous quota of affect */
		for (clsDriveMesh oDriveMeshEntry : moAllDriveComponents_OUT )
        {
            if(oDriveMeshEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
            {
                mnCurrentLibido += oDriveMeshEntry.getQuotaOfAffect();  
            }
            else if (oDriveMeshEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
            {
                mnCurrentAggr += oDriveMeshEntry.getQuotaOfAffect();
            }
        }
		mnCurrentUnpleasure = mnCurrentLibido + mnCurrentAggr;
	    //mnCurrentUnpleasure = moPleasureStorage.send_D4_3();
		
		//add chart data for all drives:
		for (clsDriveMesh oDriveMeshEntry : moAllDriveComponents_OUT )
		{

		    //add some time chart data
			String oaKey = oDriveMeshEntry.getChartShortString();
	         if(oDriveMeshEntry.getActualDriveSource().equals(eOrgan.HEALTH))
	            {
	                oaKey = oDriveMeshEntry.getChartShortString();
	            }
			if ( !moDriveChartData.containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moDriveChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
			
		}
		
		//now add chart for pleasure
		String olKey = "pleasure";
		if ( !moDriveChartData.containsKey(olKey) ) {
			mnChartColumnsChanged = true;
		}
		moDriveChartData.put(olKey, mnCurrentPleasure*10);
		
//		//now add chart for pleasure
//        olKey = "AGGR";
//        if ( !moDriveChartData.containsKey(olKey) ) {
//            mnChartColumnsChanged = true;
//        }
//        moDriveChartData.put(olKey, mnCurrentAggr);
//        
//        //now add chart for pleasure
//        olKey = "LIBIDO";
//        if ( !moDriveChartData.containsKey(olKey) ) {
//            mnChartColumnsChanged = true;
//        }
//        moDriveChartData.put(olKey, mnCurrentLibido);
//        
        //now add chart for pleasure
        olKey = "unpleasure";
        if ( !moDriveChartData.containsKey(olKey) ) {
            mnChartColumnsChanged = true;
        }
        moDriveChartData.put(olKey, mnCurrentUnpleasure); 
//        
//        mnCurrentLearningIntensity = mnCurrentUnpleasure + mnCurrentPleasure;
//        //now add chart for pleasure
//        olKey = "LEARNING";
//        if ( !moDriveChartData.containsKey(olKey) ) {
//            mnChartColumnsChanged = true;
//        }
//        moDriveChartData.put(olKey, mnCurrentLearningIntensity);
        
        moLearningIntensityBuffer = mnCurrentLearningIntensity; 
        
	
		ArrayList<clsDriveMesh> loggingData = (ArrayList<clsDriveMesh>) moAllDriveComponents_OUT.clone();
		moDriveCanditates_OUT = (ArrayList<clsDriveMesh>) moAllDriveComponents_OUT.clone();
		Collections.sort(loggingData, new clsDriveMeshQoAComparator());
		Collections.sort(moDriveCanditates_OUT, new clsDriveMeshBEComparator());
		
		for(int i=0;;)
		{
		    if(  (moDriveCanditates_OUT.get(i).getQuotaOfAffect() < 0.002)
		      || (moDriveCanditates_OUT.get(i).getPleasureSumMax() < 0.002)
		      )
		    {
		        moDriveCanditates_OUT.remove(i);
		    }
		    else
		    {
		        i++;
		    }
		    if(i>= moDriveCanditates_OUT.size() )
		    {
		        break;
		    }
		}
		//clsShortTermMemoryMF test = new clsShortTermMemoryMF(null);
		ArrayList<clsPair<Integer, ArrayList<clsDriveMesh>>> snapshot;

		try {
		    clsThingPresentationMesh moObject = F29_EvaluationOfImaginaryActions.moTPM_Object;
		    clsThingPresentationMesh moAction = F46_MemoryTracesForPerception.moAction;
		    if(moObject != null && moAction != null)
		    {
    		    for(int i=0;i<moDriveCanditates_OUT.size();i++)
    		    {
    	            moDriveCanditates_OUT.get(i).setActualDriveObject(moObject, (double)1.0);
    	            moDriveCanditates_OUT.get(i).setActualDriveAim(moAction, (double)1.0);
    		    }
		    }
        } catch (Exception e) {
            // TODO (noName) - Auto-generated catch block
            e.printStackTrace();
        }
		
       for (clsDriveMesh oDriveCanditate: moDriveCanditates_OUT) {
            if (oDriveCanditate.getActualDriveSourceAsENUM() == eOrgan.STOMACH
                   // drive component have to be considered to
                && oDriveCanditate.getDriveComponent() == eDriveComponent.LIBIDINOUS
              ){
                //rSumSimilarDMsQoA += oMemoryDM.getQuotaOfAffect();
                mnCurrentMaxPleasureLiSto = oDriveCanditate.getPleasureSumMax();
            }
        }
        
//        olKey = "L.-.STOMACH_PLE_MAX";
//        if ( !moDriveChartData.containsKey(olKey) ) {
//            mnChartColumnsChanged = true;
//        }
//        moDriveChartData.put(olKey, mnCurrentMaxPleasureLiSto);
        
        moDriveChartData.remove("L.-.RECTUM");
        moDriveChartData.remove("A.-.RECTUM");
        moDriveChartData.put("A.-.RECTUM",0.0);
        moDriveChartData.put("L.-.RECTUM",0.0);
        
		ArrayList<clsDriveMesh> moAllDrivesLastStep;
		
		log.debug("Generated Drives: \n"+loggingData.toString());
	}
	
	/**
     * @since 21.08.2014 15:51:31
     * 
     * @return the moPsychicIntensityStorage
     */
    public DT3_PsychicIntensityStorage getMoPsychicIntensityStorage() {
        return moPsychicIntensityStorage;
    }
    /**
     * @since 21.08.2014 15:51:31
     * 
     * @param moPsychicIntensityStorage the moPsychicIntensityStorage to set
     */
    public void setMoPsychicIntensityStorage(DT3_PsychicIntensityStorage moPsychicIntensityStorage) {
        this.moPsychicIntensityStorage = moPsychicIntensityStorage;
    }
    /**
     * @since 21.08.2014 15:51:31
     * 
     * @return the mnPsychicIntensityPleasure
     */
    public double getMnPsychicIntensityPleasure() {
        return mnPsychicIntensityPleasure;
    }
    /**
     * @since 21.08.2014 15:51:31
     * 
     * @param mnPsychicIntensityPleasure the mnPsychicIntensityPleasure to set
     */
    public void setMnPsychicIntensityPleasure(double mnPsychicIntensityPleasure) {
        this.mnPsychicIntensityPleasure = mnPsychicIntensityPleasure;
    }
    /**
     * DOCUMENT (herret) - insert description
     *
     * @since 23.05.2013 15:46:49
     *
     */
    private void generateAllDrives() {
        HashMap<eDrive,clsPair<Double,Double>> moDriveBuffer = moLibidoBuffer.send_D1_5();
        for(Map.Entry<eDrive,clsPair<Double,Double>> oEntry : moDriveBuffer.entrySet()){
            try{
                moAllDriveComponents_OUT.add(CreateDriveCandidate(oEntry.getKey(), oEntry.getValue().a,eDriveComponent.AGGRESSIVE));
                moAllDriveComponents_OUT.add(CreateDriveCandidate(oEntry.getKey(), oEntry.getValue().b,eDriveComponent.LIBIDINOUS));
            }
            catch(Exception e){
                
            }

            
        }
        
        
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
	 * Guarantees that the provided scalar value r is within the range -1<r<1.
	 *
	 * @since 14.07.2011 11:53:36
	 *
	 * @param r
	 * @return r
	 */
	private double normalize(double r) {
		if (r>1) {return 1;}
		else if (r<-1) {return -1;}
		else {return r;}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
	}

	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 18.07.2012 12:56:23
	 *
	 */
	private void ProcessPleasureCalculation() {
		
		//set the actual drive list to DT4, this automatically calculates the pleasure and this value can the be used everywhere
		moPleasureStorage.receive_D4_1(moAllDriveComponents_OUT);
		
		//mnPsychicIntensityPleasure = getMoPsychicIntensityStorage().calculatePleasureProduction();
	    
	    mnCurrentPleasure = moPleasureStorage.send_D4_1() + mnPsychicIntensityPleasure;
	    logger.clsLogger.getLog("NeutralizedIntensity").debug("ProcessPleasureCalculation::mnPsychicIntensityPleasure = " + Double.toString(mnPsychicIntensityPleasure));

	}

	
	
	/**
	 * This add debug info only, no real model-info, deactivate this if performance is king
	 *
	 * @since 23.07.2012 14:41:43
	 *
	 * @param poDriveList
	 */
	private void AddDebugInfoForUsProgrammers(ArrayList<clsDriveMesh> poDriveList)
	{ 
		// see Deutsch p81 for the source for the names
		
		for( clsDriveMesh oDriveEntry : poDriveList){
			
			//the big IF:
			if(		oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STOMACH &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("bite");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STOMACH &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("nourish");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.RECTUM &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("expulsion");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.RECTUM &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("repress");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.BLADDER &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("squirt out");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.BLADDER &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("retain warm");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STAMINA &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("put to sleep");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.STAMINA &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("relax");
			
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.LIBIDO &&
					oDriveEntry.getPartialDrive()== ePartialDrive.GENITAL &&
					oDriveEntry.getDriveComponent() == eDriveComponent.AGGRESSIVE)
				oDriveEntry.setDebugInfo("male: amputate; female:to suffocate");
			else if(oDriveEntry.getActualDriveSourceAsENUM()== eOrgan.LIBIDO &&
					oDriveEntry.getPartialDrive()== ePartialDrive.GENITAL &&
					oDriveEntry.getDriveComponent() == eDriveComponent.LIBIDINOUS)
				oDriveEntry.setDebugInfo("male: to errect; female:to absorb/to complete");
			
			//TODO names for the other sexual/homeo drives

		}
		
	}
	


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I4_1(moAllDriveComponents_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based on a splitter mechanism and outputs the result to a list of drive candidates.";
	}
	


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I4_1_send#send_I4_1(java.util.ArrayList)
	 */
	@Override
	public void send_I4_1(ArrayList<clsDriveMesh> poDriveComponents) {
		((I4_1_receive)moModuleList.get(57)).receive_I4_1(poDriveComponents);
		putInterfaceData(I4_1_send.class, poDriveComponents);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I3_4_receive#receive_I3_4(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_4(
			ArrayList<clsDriveMesh> poDriveComponents) {
		moHomoestasisDriveComponents_IN = (ArrayList<clsDriveMesh>) deepCopy(poDriveComponents);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I3_3_receive#receive_I3_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I3_3(
			ArrayList<clsDriveMesh> poSexualDriveRepresentations) {
		moSexualDriveRepresentations_IN = (ArrayList<clsDriveMesh>)poSexualDriveRepresentations;
	}

	
	/*************************************************************/
	/***                        CHART METHODS                  ***/
	/*************************************************************/
	
	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Pleasure and Drives";
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
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
	 * @since 28.08.2012 13:00:17
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
	 * @since 28.08.2012 13:00:17
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
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }
}
