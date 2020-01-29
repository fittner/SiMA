/**
 * CHANGELOG
 * 
 * 2011/07/12 TD - added javadoc comments. code sanitation.
 */
package control;

import java.util.HashMap;

import org.slf4j.Logger;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import control.interfaces.itfProcessor;

import properties.clsProperties;
import base.datatypes.clsShortTermMemoryMF;
import base.modules.clsPsychicApparatus;
import memorymgmt.interfaces.itfModuleMemoryAccess;
//import pa._v38.memorymgmt.longtermmemory.clsLongTermMemoryHandler;
//import pa._v38.memorymgmt.old.clsInformationRepresentationManagement;
//import pa._v38.memorymgmt.searchspace.clsSearchSpaceManager;


/**
 * The clsProcessor of the implemenation v38 is responsible for feeding the sensor data into the decision unit, calling all modules to process this data
 * in the correct order, and to extract the produced action command from the decision unit. The modules themselves are created within the class clsPsychicApparatus.
 * Further the knowledgebasehandler is created in this class.
 * 
 * @see base.modules.clsPsychicApparatus
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 * 
 */
public class clsProcessor implements itfProcessor  {
	/** prefix to the properties file for all entries related to the psychic apparatus; @since 12.07.2011 10:58:05 */
	public static final String P_PSYCHICAPPARATUS = "psychicapparatus";
	/** prefix to the properties file for all entries related to the knowledgebase; @since 12.07.2011 10:58:32 */
	public static final String P_KNOWLEDGEABASE = "knowledgebase";
	/** the value entry that defines the constantly produced libido stream. used for the properties file; @since 12.07.2011 10:59:01 */
	public static final String P_LIBIDOSTREAM = "libidostream";
	
	/** the private instance of the psychic apparatus; @since 12.07.2011 11:00:15 */
	private clsPsychicApparatus moPsyApp;
	/** the private instance of the knowledgebasehandeler/memory; @since 12.07.2011 11:00:30 */
	//private clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	private itfModuleMemoryAccess moMemory;
	/** the rate of the constantly produced libido; @since 12.07.2011 11:00:52 */
	private double mrLibidostream;
	
	private final Logger log = logger.clsLogger.getLog("General");
	private final Logger logtiming = logger.clsLogger.getLog("Timing");
	private int steps=0;
	/**
	 * Creates an instance of the processor and thus the decision unit with the provided properties.
	 *
	 * @since 12.07.2011 11:01:51
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param uid A unique identifier. It is the same for the decision unit and the body for one agent.
	 */
	public clsProcessor(String poPrefix, clsProperties poProp, int uid,
            itfModuleMemoryAccess poMemory) {
	    moMemory = poMemory;
		applyProperties(poPrefix, poProp, uid);
		
	}
	
	/**
	 * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
	 *
	 * @since 12.07.2011 11:02:53
	 *
	 * @param poPrefix Prefix for the entries in the property file.
	 * @return An instance of clsProperties with the added default properties.
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		//oProp.putAll( clsInformationRepresentationManagement.getDefaultProperties(pre+P_KNOWLEDGEABASE) );
		oProp.putAll( clsPsychicApparatus.getDefaultProperties(pre+P_PSYCHICAPPARATUS) );
				
		oProp.setProperty( pre+P_LIBIDOSTREAM, 0.01);
		
		return oProp;
	}	
	
	/**
	 * Applies the provided properties to the class and creates the knowledgebasehandler and the psychicapparatus.
	 *
	 * @since 12.07.2011 11:04:27
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param uid A unique identifier. It is the same for the decision unit and the body for one agent.
	 */
	private void applyProperties(String poPrefix, clsProperties poProp, int uid) {
		String pre = clsProperties.addDot(poPrefix);
		
		//Create knowledgebase
		//moKnowledgeBaseHandler = new clsInformationRepresentationManagement(pre + P_KNOWLEDGEABASE, poProp);
		
		//moSearchSpace = new clsSearchSpaceManager(pre + P_KNOWLEDGEABASE, poProp);
		//moMemory = new clsLongTermMemoryHandler(moSearchSpace);
		
		//Create psychic apparatus
		//moPsyApp = new clsPsychicApparatus(pre + P_PSYCHICAPPARATUS, poProp, moKnowledgeBaseHandler, uid);
		moPsyApp = new clsPsychicApparatus(pre + P_PSYCHICAPPARATUS, poProp, moMemory, uid);

		mrLibidostream = poProp.getPropertyDouble(pre+P_LIBIDOSTREAM);
	}
		
	@Override
	public void applySensorData(clsDataContainer poData) {
		moPsyApp.moF39_SeekingSystem_LibidoSource.receive_I0_1( separateLibido(poData) );
		moPsyApp.moF39_SeekingSystem_LibidoSource.receive_I0_2( separateFASTMESSENGERData(poData) );
		moPsyApp.moF01_SensorsMetabolism.receive_I0_3( separateHomeostaticData(poData) );
		moPsyApp.moF12_SensorsBody.receive_I0_5( separateBodyData(poData) );
		moPsyApp.moF10_SensorsEnvironment.receive_I0_4( separateEnvironmentalData(poData) );
	}
	
	/**
	 * get the fast messenger sensor information, where the erogenous zone sensor information is comming from
	 *
	 * @since 26.11.2012 14:53:36
	 *
	 * @param poData
	 * @return
	 */
	private clsDataContainer separateFASTMESSENGERData(clsDataContainer poData) {
        clsDataContainer oResult = new clsDataContainer();
        
        for(clsDataPoint oDataPoint :poData.getData()){
            if(oDataPoint.getType().equals("FAST_MESSENGER_SYSTEM")){
                oResult.addDataPoints(oDataPoint.getAssociatedDataPoints());
            }
        }
        return oResult;
	}
	
	   /**
     * seperate Libido
     *
     * @since 26.11.2012 14:53:36
     *
     * @param poData
     * @return
     */
    private double separateLibido(clsDataContainer poData) {
        double oRetVal=0.0;
        for(clsDataPoint oDataPoint :poData.getData()){
            if(oDataPoint.getType().equals("LIBIDO")){
                oRetVal = Double.parseDouble(oDataPoint.getValue().toString());
            }
        }
        return oRetVal;
    }

	/**
	 * Extracts the homeostatic data from the whole set of sensor data. Necessary to create the input for F1.
	 *
	 * @author langr
	 * 12.08.2009, 20:42:17
	 *
	 * @param poData The sensor data collected by the various sources of the body.
	 * @return
	 */
	private clsDataContainer separateHomeostaticData(clsDataContainer poData) {
        clsDataContainer oResult = new clsDataContainer();
        HashMap<String,String> oF01Data = new HashMap<String,String>();
        oF01Data.put("SLOW_MESSENGER_SYSTEM", "");
        oF01Data.put("FAST_MESSENGER_SYSTEM", "");
        oF01Data.put("STOMACH_ENERGY", "");
        oF01Data.put("STOMACH_TENSION", "");
        oF01Data.put("STOMACH_INTESTINE_TENSION", "");
        oF01Data.put("STAMINA", "");
        //oF01Data.put("HEALTH", "");
        oF01Data.put("TEMPERATUR", "");
        oF01Data.put("ENERGY_CONSUMPTION", "");
        
           for(clsDataPoint oDataPoint :poData.getData()){
                if(oF01Data.containsKey(oDataPoint.getType())){
                    oResult.addDataPoint(oDataPoint);
                }
            }        
           return oResult;
	}

    /**
     * Extracts the external sensor data (more or less the five senses) from the rest of the sensor data. Necessary to create the input for F10.
     *
     * @author langr
     * 12.08.2009, 20:41:51
     *
     * @param poData The sensor data collected by the various sources of the body.
     * @return
     */
    private clsDataContainer separateEnvironmentalData(clsDataContainer poData) {
        clsDataContainer oResult = new clsDataContainer();
            HashMap<String,String> oEnvData = new HashMap<String,String>();
            oEnvData.put("VISION", "");
            
               for(clsDataPoint oDataPoint :poData.getData()){
                    if(oEnvData.containsKey(oDataPoint.getType())){
                        oResult.addDataPoint(oDataPoint);
                    }
                }        
    return oResult;
}
    /**
     * Extract the internal sensor data (like pain or position of the elbow) from the rest of the sensor data. Necessary to create the input for F12.
     *
     * @author langr
     * 12.08.2009, 20:41:48
     *
     * @param poData The sensor data collected by the various sources of the body.
     * @return
     */
    private clsDataContainer separateBodyData(clsDataContainer poData) { 
        clsDataContainer oResult = new clsDataContainer();
        HashMap<String,String> oF12Data = new HashMap<String,String>();
        oF12Data.put("HEART_BEAT", "");
        oF12Data.put("SWEAT_INTENSITY", "");
        oF12Data.put("CRYING_INTENSITY", "");
        oF12Data.put("MUSCLE_TENSION_ARMS_INTENSITY", "");
        oF12Data.put("MUSCLE_TENSION_Legs_INTENSITY", "");
        oF12Data.put("HEALTH", "");

        for(clsDataPoint oDataPoint :poData.getData()){
            if(oF12Data.containsKey(oDataPoint.getType())){
                oResult.addDataPoint(oDataPoint);
            }
        }        
       return oResult;
    }
    
	
	@Override
	public void step() {
	    steps++;
	    log.info("================== START CYCLE OF ARS ===========================================================================================================================");
	    log.info("=================== SENSING ========================");
	    long start = System.currentTimeMillis();
		//BODY --------------------------------------------- 
		//data preprocessing
	    //Resets the pleasure value to 0
	    /* Nur alle 10 Schritte */
	    clsShortTermMemoryMF test = new clsShortTermMemoryMF(null);
	    if(test.getChangedMoment())
	    {
	        moPsyApp.moPleasureStorage.resetPleasure();
	        //calculate the dynamic portion of pleasure depending on the gradiant
	        moPsyApp.moPleasureStorage.calculateDynamicPortionOfPleasure();
	    }
	    moPsyApp.moLibidoBuffer.saveOld();
	    if(!(test.getActualStep()>110 && moPsyApp.getUid()==1))
	    {
	    moPsyApp.moF90_Learning.step();
        moPsyApp.moF01_SensorsMetabolism.step();
		moPsyApp.moF02_NeurosymbolizationOfNeeds.step();

		moPsyApp.moF10_SensorsEnvironment.step();
		moPsyApp.moF11_NeuroSymbolizationEnvironment.step();
		
		moPsyApp.moF12_SensorsBody.step();
		moPsyApp.moF13_NeuroSymbolizationBody.step();
		
		moPsyApp.moF39_SeekingSystem_LibidoSource.step();
		moPsyApp.moF40_NeurosymbolizationOfLibido.step();

		logtiming.info("Duration Sensing: {}", System.currentTimeMillis()-start);
		//PRIMARY PROCESSES -------------------------------
		log.info("=================== PRIMARY PROCESS ========================");
		start = System.currentTimeMillis();
		//Self-PreservationDrive generation
		moPsyApp.moF65_PartialSelfPreservationDrives.step();
		//moPsyApp.moF03_GenerationOfSelfPreservationDrives.step(); //todo
		//moPsyApp.moF04_FusionOfSelfPreservationDrives.step(); //todo
		
		//Libido generation
		moPsyApp.moF64_PartialSexualDrives.step();
        
		//Accumulation of affects and drives
		moPsyApp.moF48_AccumulationOfQuotaOfAffectsForDrives.step(); 
		

		
		moPsyApp.moF57_MemoryTracesForDrives.step(); 
		 
        		
		//perception to memory and repression
		moPsyApp.moF14_ExternalPerception.step();
		moPsyApp.moF46_MemoryTracesForPerception.step();
		moPsyApp.moF37_PrimalRepressionForPerception.step();
		
		//Repression for drives
		
		moPsyApp.moF49_PrimalRepressionForDrives.step();
		moPsyApp.moF54_EmersionOfBlockedDriveContent.step(); 
		
				
		//Emersion of blocked content
		moPsyApp.moF35_EmersionOfBlockedContent.step();
		moPsyApp.moF45_LibidoDischarge.step();
		moPsyApp.moF18_CompositionOfAffectsForPerception.step();
		
		//desexualization and emotions
		moPsyApp.moF63_CompositionOfEmotions.step();
		moPsyApp.moF56_Desexualization_Neutralization.step();

		//super-ego
		moPsyApp.moF55_SuperEgoProactive.step(); 
		moPsyApp.moF07_SuperEgoReactive.step(); 

		//defense mechanisms
		moPsyApp.moF06_DefenseMechanismsForDrives.step();
		moPsyApp.moF19_DefenseMechanismsForPerception.step();

		moPsyApp.moF71_CompositionOfExtendedEmotion.step();

        logtiming.info("Duration Primary Process: {}", System.currentTimeMillis()-start);
		//SECONDARY PROCESSES ----------------------------
		log.info("=================== SECONDARY PROCESS ========================");
		start = System.currentTimeMillis();
		
	    //primary to secondary
        moPsyApp.moF08_ConversionToSecondaryProcessForDriveWishes.step();
        moPsyApp.moF21_ConversionToSecondaryProcessForPerception.step();
        moPsyApp.moF20_CompositionOfFeelings.step();
		
		//Spech generation
        moPsyApp.moF66_SpeechProduction.step();
		
		//localization
		moPsyApp.moF61_Localization.step();
		
		//external perception
		moPsyApp.moF23_ExternalPerception_focused.step();
		moPsyApp.moF51_RealityCheckWishFulfillment.step();
		
		//decision making
		moPsyApp.moF26_DecisionMaking.step();
		moPsyApp.moF52_GenerationOfImaginaryActions.step(); 
					
		//evaluation and pre-execution
		moPsyApp.moF53_RealityCheckActionPlanning.step(); 
		moPsyApp.moF29_EvaluationOfImaginaryActions.step();
	      //fantasy
        moPsyApp.moF47_ConversionToPrimaryProcess.step();
        
		moPsyApp.moF30_MotilityControl.step();
		
		logtiming.info("Duration Secondary Process: {}", System.currentTimeMillis()-start);		
		//BODY --------------------------------------------- 
		log.info("=================== ACTION EXECUTION ========================");
		start = System.currentTimeMillis();
		//execution
	      // BODILY REACTIONS ON EMOTIONS
        moPsyApp.moF67_BodilyReactionOnEmotions.step();
	    }
		moPsyApp.moF31_NeuroDeSymbolizationActionCommands.step();
		moPsyApp.moF32_Actuators.step();
		
		logtiming.info("Duration action execution: {}", System.currentTimeMillis()-start);
		
		//UPDATE DataLogger Entries
		//moPsyApp.moDataLogger.step();
	}
	
	   @Override
    public clsDataContainer getActions(){
           return moPsyApp.moF32_Actuators.getActions();
       }
       @Override
    public clsDataContainer getInternalActions(){
           return moPsyApp.moF67_BodilyReactionOnEmotions.getBodilyReactions();
       }

	/**
	 * Getter method for the psychic apparatus. Needed primarily by the various inspectors.
	 *
	 * @author langr
	 * 13.08.2009, 00:08:10
	 *
	 * @return An instance of clsPsychicApparatus.
	 */
	public clsPsychicApparatus getPsychicApparatus() {
		return moPsyApp;
	}
	
	


	
}
