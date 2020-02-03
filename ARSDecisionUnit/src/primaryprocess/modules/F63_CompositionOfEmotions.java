/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author schaat
 * 07.06.2012, 15:47:11
 */
package primaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorCombinedTimeChart;
import inspector.interfaces.itfInspectorGenericTimeChart;
import inspector.interfaces.itfInspectorMultipleBarChart;
import inspector.interfaces.itfInspectorStackedAreaChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import memorymgmt.storage.DT4_PleasureStorage;
import modules.interfaces.I5_10_receive;
import modules.interfaces.I5_21_receive;
import modules.interfaces.I5_21_send;
import modules.interfaces.I5_3_receive;
import modules.interfaces.eInterfaces;
import prementalapparatus.modules.F31_NeuroDeSymbolizationActionCommands;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsShortTermMemoryEntry;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.logging.DataCollector;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.clsSingletonAnalysisAccessor;
import base.tools.toText;

/**
 * (see document "Compositions of Emotions")
 * F63 generate Emotions based on  Pleasure and  Unpleasure. Six basic emotions exists: ANGER, Anxiety, Mourning, Pleasure, Elation and Saturation. They are not mutual exclusive. 
 * Dependent on the dominance of the basic categories Pleasure, Unpleasure and the aggressive and libidinous drive parts, the corresponding emotions are generated.
 * Dominance of Unpleasure +  Dominance of aggr. Drivecomponents --> ANGER
 * Dominance of Unpleasure +  Dominance of libid. Drivecomponents --> Mourning
 * Dominance of Unpleasure --> Anxiety
 * Dominance of Pleasure --> Pleasure
 * Dominance of Pleasure + Dominance of aggr. Drivecomponents -->  Elation
 * Dominance of Pleasure +  Dominance of libid. Drivecomponents --> Saturation
 * 
 * 
 * @author schaat
 * 07.06.2012, 15:47:11
 */
public class F63_CompositionOfEmotions extends clsModuleBase 
					implements  itfInspectorGenericTimeChart, I5_3_receive, I5_10_receive, I5_21_send,itfInspectorCombinedTimeChart, itfInspectorStackedAreaChart, itfInspectorMultipleBarChart{

	//Statics for the module
	public static final String P_MODULENUMBER = "63";
	
	private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
	private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
	        
	private double mrModuleStrength;
	private double mrInitialRequestIntensity;

	public static final String P_REALATIV_THRESHOLD = "REALATIV_THRESHOLD";
	public static final String P_THRESHOLD_RANGE = "THRESHOLD_RANGE";
	public static final String P_PERCEPTION_PLEASURE_IMPACT_FACTOR = "PERCEPTION_PLEASURE_IMPACT_FACTOR";
	public static final String P_PERCEPTION_UNPLEASURE_IMPACT_FACTOR = "PERCEPTION_UNPLEASURE_IMPACT_FACTOR";
	public static final String P_PERCEPTION_AGGRESSIVE_IMPACT_FACTOR = "PERCEPTION_AGGRESSIVE_IMPACT_FACTOR";
	public static final String P_PERCEPTION_LIBIDINOUS_IMPACT_FACTOR = "PERCEPTION_LIBIDINOUS_IMPACT_FACTOR";
    
	public static final String P_DRIVEDEMAND_IMPACT_FACTOR = "DRIVEDEMAND_IMPACT_FACTOR";
	public static final String P_MEMORIZEDDRIVE_IMPACT_FACTOR = "MEMORIZEDDRIVE_IMPACT_FACTOR";
    public static final String P_EMOTIONRECOGNITION_IMPACT_FACTOR = "EMOTIONRECOGNITION_IMPACT_FACTOR"; //koller
    public static final String P_EXPERIENCEDEMOTION_IMPACT_FACTOR = "EXPERIENCEDEMOTION_IMPACT_FACTOR";
    public static final String P_MEMORIZEDIMAGE_IMPACT_FACTOR = "MEMORIZEDIMAGE_IMPACT_FACTOR";
    private static  ArrayList<Double> transUnpleasure = new  ArrayList<Double>();

	
	//Private members for send and recieve
	private ArrayList<clsEmotion> moEmotions_OUT; 
	private ArrayList<clsDriveMesh> moDrives_IN;
	private clsThingPresentationMesh moPerceptions_IN;
	
	//Provide steadier emotion change
	private clsEmotion moLastEmotion = null;         /*ATTENTION: this is a local emotion that MUST NEVER be linked to ANYTHING*/
	private clsEmotion moTargetEmotion = null;       /*This is stored for visualization purposes only*/
	private double mrEmotionChangeFactor = 0.1;
	private static final boolean mbShowTargetEmotionInChart = false;
	
	// threshold to determine in which case domination of a emotion occurs
	private double mrRelativeThreshold;
	private double mrThresholdRange;
	
	DT4_PleasureStorage moPleasureStorage;
	
	// values from perception-track (triggered emotions). to get a better output in the inspectors and a capsulated function-call a hashmap is used instead of separate variables
	HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();
	
	// values from emotions assoziated to memorized scenes (only if the scenes have been activated in F46).
	// to get a better output in the inspectors and a capsulated function-call a hashmap is used instead of separate variables
    HashMap<String, Double> oMemoryExtractedValues = new HashMap<String, Double>();

	// values from drive-track . to get a better output in the inspectors a hashmap is used instead of separate variables
	HashMap<String, Double> oDrivesExtractedValues = new HashMap<String, Double>();
	
	// personality parameter, perceiving a drive object sould trigger less emotions than the bodily needs
	private double mrPerceptionPleasureImpactFactor;
	private double mrPerceptionUnpleasureImpactFactor;
	private double mrPerceptionAggressiveImpactFactor;
    private double mrPerceptionLibidinousImpactFactor;
    private double mrInfluenceRememberedImages;
    private double mrInfluenceDrivesOnPerceivedObjects;
    private double mrInfluenceCurrentDrives;
    
    private clsEmotion moAgentAttributedEmotion = null;
    private clsEmotion moAgentEmotionValuation = null;
    private clsEmotion moAgentTransferedEmotion = null;
    
    public static double rpain;
    
    private int counter2=0;
    
    private double socialRule = 0;
    private String moRuleList = "";
    
	// koller 
    private double mrEmotionrecognitionImpactFactor;
	
    //kollmann: impact factors for experienced emotions
    private double mrExperiencedEmotionImpactFactor;
    
	private clsWordPresentationMesh moWordingToContext;
    
	double mrGrade = 0;
	
	private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	private final Logger log = clsLogger.getLog("F" + P_MODULENUMBER);
	
	private clsShortTermMemoryMF moSTM_Learning;
	
	public F63_CompositionOfEmotions(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage,
			DT3_PsychicIntensityStorage poPsychicEnergyStorage,
			clsShortTermMemoryMF poSTM_Learning,
			clsPersonalityParameterContainer poPersonalityParameterContainer,
			int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F55", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F55", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
        
        
		applyProperties(poPrefix, poProp);	
		
		mrRelativeThreshold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_REALATIV_THRESHOLD).getParameterDouble();
		mrThresholdRange = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_THRESHOLD_RANGE).getParameterDouble();
		mrPerceptionPleasureImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_PLEASURE_IMPACT_FACTOR).getParameterDouble();
		mrPerceptionUnpleasureImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_UNPLEASURE_IMPACT_FACTOR).getParameterDouble();
		mrPerceptionAggressiveImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_AGGRESSIVE_IMPACT_FACTOR).getParameterDouble();
		mrPerceptionLibidinousImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERCEPTION_LIBIDINOUS_IMPACT_FACTOR).getParameterDouble();
		moPleasureStorage = poPleasureStorage;
		
		//koller
        mrEmotionrecognitionImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_IMPACT_FACTOR).getParameterDouble();
        
        mrExperiencedEmotionImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EXPERIENCEDEMOTION_IMPACT_FACTOR).getParameterDouble();
        mrInfluenceRememberedImages = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MEMORIZEDIMAGE_IMPACT_FACTOR).getParameterDouble();
        mrInfluenceDrivesOnPerceivedObjects = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MEMORIZEDDRIVE_IMPACT_FACTOR).getParameterDouble();
        mrInfluenceCurrentDrives = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_DRIVEDEMAND_IMPACT_FACTOR).getParameterDouble();
        
        moLastEmotion = null;
        
        moSTM_Learning = poSTM_Learning;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
	}

	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 02.07.2012, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("mrGrade", mrGrade);
		text += toText.valueToTEXT("rDriveLibid", oDrivesExtractedValues.get("rDriveLibid"));
		text += toText.valueToTEXT("rDriveAggr", oDrivesExtractedValues.get("rDriveAggr"));
		text += toText.valueToTEXT("rDrivePleasure",  oDrivesExtractedValues.get("rDrivePleasure"));
		text += toText.valueToTEXT("rDriveUnpleasure", oDrivesExtractedValues.get("rDriveUnpleasure"));
				
		text += toText.valueToTEXT("rPerceptionUnpleasure", oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
		text += toText.valueToTEXT("rPerceptionPleasure", oPerceptionExtractedValues.get("rPerceptionPleasure"));
		text += toText.valueToTEXT("rPerceptionLibid", oPerceptionExtractedValues.get("rPerceptionLibid"));
		text += toText.valueToTEXT("rPerceptionAggr", oPerceptionExtractedValues.get("rPerceptionAggr"));
		text += toText.valueToTEXT("rPerceptionUnpleasurePain", oPerceptionExtractedValues.get("rPerceptionUnpleasure"));
        
		text += toText.listToTEXT("moEmotions_OUT", moEmotions_OUT);
		
		text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getExternalAssociatedContent());
		
		//text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getMoInternalAssociatedContent());
		
		return text;
	}
	


	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 02.07.2012, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
	    
	    clsShortTermMemoryEntry STMentry;
		moEmotions_OUT = new ArrayList<clsEmotion>() ;
		
		
		// four basic categories ("Grundkategorien", see document "Compositions of Emotions")
		double rSystemPleasure = 0.0; 
		double rSystemUnpleasure = 0.0;
		double rSystemLibid = 0.0;
		double rSystemAggr = 0.0;
		
		// values from drive-track
		double rDrivePleasure = 0.0; 
		double rDriveLibid = 0.0;
		double rDriveAggr = 0.0;
		
		//1. Get Unpleasure from all drives, and the aggr. and libid parts
		for (clsDriveMesh oDM: moDrives_IN) {
			if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
				rDriveLibid = nonProportionalAggregation(rDriveLibid, oDM.getQuotaOfAffect());  
				
			} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
				rDriveAggr = nonProportionalAggregation(rDriveAggr, oDM.getQuotaOfAffect());
				
			}
		}
		
		//get Pleasure
		rDrivePleasure =  moPleasureStorage.send_D4_1();
		
		//rSystemUnpleasure = moPleasureStorage.send_D4_3() * mrInfluenceCurrentDrives;
		if(F31_NeuroDeSymbolizationActionCommands.share)
		{
	        rSystemUnpleasure = (rDriveLibid+rDriveAggr) * mrInfluenceCurrentDrives*0.6;
	        rSystemPleasure = rDrivePleasure * mrInfluenceCurrentDrives*10;
		}
		else
		{
	        rSystemUnpleasure = (rDriveLibid+rDriveAggr) * mrInfluenceCurrentDrives;
	        rSystemPleasure = rDrivePleasure * mrInfluenceCurrentDrives*10;
		}
        rSystemLibid = rDriveLibid * mrInfluenceCurrentDrives;
        rSystemAggr = rDriveAggr * mrInfluenceCurrentDrives;
        
		// TEMPORARY
        oDrivesExtractedValues.put("rDriveUnpleasure", rSystemUnpleasure);
        if(F31_NeuroDeSymbolizationActionCommands.share)
        { 
            oDrivesExtractedValues.put("rDrivePleasure", rSystemPleasure*10);
        }
        else
        {
            oDrivesExtractedValues.put("rDrivePleasure", rSystemPleasure);
        }
		oDrivesExtractedValues.put("rDriveLibid", rSystemLibid);
		oDrivesExtractedValues.put("rDriveAggr", rSystemAggr);
		
		/* emotions triggered by perception (from memory) influence emotion-generation
		 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (un-pleasure etc the emotion is based on) to influence the emotion generation in F63
		 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
		 * 
		 */
		oPerceptionExtractedValues = getEmotionValuesFromPerception();
		
		// aggregate values from drive- and perception track
		// normalize grundkategorien
		// important sentence
		// (if agent sees many objects the perception has more influence, otherwise drives have more influence on emotions)
		rSystemUnpleasure = nonProportionalAggregation(rSystemUnpleasure, oPerceptionExtractedValues.get("rPerceptionDriveMeshUnpleasure"));
		rSystemPleasure = nonProportionalAggregation(rSystemPleasure, oPerceptionExtractedValues.get("rPerceptionDriveMeshPleasure"));
		rSystemLibid = nonProportionalAggregation(rSystemLibid, oPerceptionExtractedValues.get("rPerceptionDriveMeshLibid"));
		rSystemAggr = nonProportionalAggregation(rSystemAggr, oPerceptionExtractedValues.get("rPerceptionDriveMeshAggr"));

		rSystemUnpleasure = nonProportionalAggregation(rSystemUnpleasure, oPerceptionExtractedValues.get("rPerceptionExperienceUnpleasure"));
        rSystemPleasure = nonProportionalAggregation(rSystemPleasure, oPerceptionExtractedValues.get("rPerceptionExperiencePleasure"));
        rSystemLibid = nonProportionalAggregation(rSystemLibid, oPerceptionExtractedValues.get("rPerceptionExperienceLibid"));
        rSystemAggr = nonProportionalAggregation(rSystemAggr, oPerceptionExtractedValues.get("rPerceptionExperienceAggr"));
        
        rSystemUnpleasure = nonProportionalAggregation(rSystemUnpleasure, oPerceptionExtractedValues.get("rPerceptionBodystateUnpleasure"));
        rSystemPleasure = nonProportionalAggregation(rSystemPleasure, oPerceptionExtractedValues.get("rPerceptionBodystatePleasure"));
        rSystemLibid = nonProportionalAggregation(rSystemLibid, oPerceptionExtractedValues.get("rPerceptionBodystateLibid"));
        rSystemAggr = nonProportionalAggregation(rSystemAggr, oPerceptionExtractedValues.get("rPerceptionBodystateAggr"));
		
		/* emotions associated to images (from memory) have a direct influence on emotions, if the scenes have been activated (happens in F46)
         * 
         */
        oMemoryExtractedValues = getEmotionValuesFromMemory();
        
        // aggregate values from drive- and perception track
        // normalize grundkategorien
        // (if agent sees many objects the perception has more influence, otherwise drives have more influence on emotions)
       
        for( String socialRule: moSTM_Learning.moShortTermMemoryMF.get(0).getSocialRules())
        {
            if (socialRule == "NO_DEVIDE" && moRuleList != "DEVIDE")
            {
                moRuleList = "NO_DEVIDE";
            }
            else
            {
                moRuleList = "DEVIDE";
            }
        }
        if(moRuleList=="NO_DEVIDE")
        {
            counter2++ ;
            if (counter2 >= 31)
            {
                socialRule = 0.2;
            }
        }
        else
        {
            socialRule = 0.0;
        }
        rSystemPleasure = nonProportionalAggregation(rSystemPleasure, oMemoryExtractedValues.get("rPerceptionPleasure"));
        rSystemLibid = nonProportionalAggregation(rSystemLibid, oMemoryExtractedValues.get("rPerceptionLibid"));
        rSystemAggr = nonProportionalAggregation(rSystemAggr, oMemoryExtractedValues.get("rPerceptionAggr"));
        
        rSystemUnpleasure = nonProportionalAggregation(rSystemUnpleasure, rpain);
        rSystemUnpleasure += socialRule;
        
        if(oMemoryExtractedValues.get("rPerceptionUnpleasure") < 0.1) {
            log.debug("Unpleasure missing");
        }
        
        //create the new base emotion target state
//        if(F31_NeuroDeSymbolizationActionCommands.share)
//        {
            moTargetEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0, rSystemPleasure, rSystemUnpleasure, rSystemLibid, rSystemAggr);
//            
//        }
//        else
//        {
//            moTargetEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0, rSystemPleasure/10, rSystemUnpleasure, rSystemLibid, rSystemAggr);
//        }
        
        if(moLastEmotion == null) {
            //if this is the first step
//            if(F31_NeuroDeSymbolizationActionCommands.share)
//            {
                moLastEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0, rSystemPleasure, rSystemUnpleasure, rSystemLibid, rSystemAggr);
//            }
//            else
//            {
//                moLastEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0, rSystemPleasure/10, rSystemUnpleasure, rSystemLibid, rSystemAggr);
//            }
            //kollmann: debug code
            //moLastEmotion = clsEmotion.zeroEmotion(eContentType.BASICEMOTION, eEmotionType.UNDEFINED);
        } else {
            //move last emotion state towards this target
            moLastEmotion.gradualChange(moTargetEmotion, mrEmotionChangeFactor);
        }
        
        clsEmotion oBaseEmotion = moLastEmotion.flatCopy();
        oBaseEmotion.setRelativeThreshold(mrRelativeThreshold);
        oBaseEmotion.setThresholdRange(mrThresholdRange);
        moEmotions_OUT.add(oBaseEmotion);
        
        moTargetEmotion.setRelativeThreshold(mrRelativeThreshold);
        moTargetEmotion.setThresholdRange(mrThresholdRange);
        
	    double rRequestedPsychicIntensity = 0.0;
	                
	    double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	            
	    double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
	            
	    moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
	
	    log.debug("Current emotions: ");
        
        for(clsEmotion oEmotion: moEmotions_OUT) {
            log.debug(oEmotion.toString());
        }
        
        DataCollector.all().putEmotionVectorsF63(oDrivesExtractedValues, oPerceptionExtractedValues, oMemoryExtractedValues);
        DataCollector.all().putBasicEmotionF63(moEmotions_OUT.get(0));
        
        clsEmotion oFromDrives = getEmotionFromMap(oDrivesExtractedValues, "rDrive");
        clsEmotion oFromPerceptionDrive = getEmotionFromMap(oPerceptionExtractedValues, "rPerceptionDriveMesh");
        clsEmotion oFromPerceptionExperiences = getEmotionFromMap(oPerceptionExtractedValues, "rPerceptionExperience");
        clsEmotion oFromPerceptionBodystates = getEmotionFromMap(oPerceptionExtractedValues, "rPerceptionBodystate");
        clsEmotion oFromMemorizedValuations = getEmotionFromMap(oMemoryExtractedValues, "rPerception");
        
        //Analysis logging
        clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).put_F63_emotionContributors(oFromDrives, oFromPerceptionDrive, oFromPerceptionExperiences, oFromPerceptionBodystates, oFromMemorizedValuations);
        clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).put_F63_basicEmotion(moTargetEmotion);
	
        for(clsEmotion oEmotion: moEmotions_OUT)
        {
            moSTM_Learning.moShortTermMemoryMF.get(0).setEmotions(oEmotion);
        }
    }
	
	/**
	 * DOCUMENT - 
	 *
	 * @author Kollmann
	 * @since 06. Feb. 2016 16:28:08
	 *
	 * @return
	 */
	private clsEmotion getEmotionFromMap(Map<String, Double> map, String prefix) {
	    clsEmotion oEmotion = clsEmotion.zeroEmotion(eContentType.EMOTION,  eEmotionType.UNDEFINED);
	    oEmotion.setSourceUnpleasure(map.get(prefix + "Unpleasure"));
	    oEmotion.setSourcePleasure(map.get(prefix + "Pleasure"));
	    oEmotion.setSourceLibid(map.get(prefix + "Libid"));
	    oEmotion.setSourceAggr(map.get(prefix + "Aggr"));
	    
	    return oEmotion;
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 18.12.2012, 15:48:45
	 * 
	 */
	private double nonProportionalAggregation (double rBaseValue, double rAddValue) {
	    /*if(rBaseValue < 0 || rBaseValue > 1) {
            throw new RuntimeException("Attempting aggregation a base value not between 0 and 1");
        }*/
	    
	    rBaseValue = rBaseValue + (1 - rBaseValue) * rAddValue;
	    
	    /*if(rBaseValue < 0 || rBaseValue > 1) {
	        throw new RuntimeException("Aggregation changed base value to value outside of 0 - 1");
	    }*/
	    
	    return rBaseValue;
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 05.07.2012, 15:48:45
	 * emotions triggered by perception (by - to PI- similar RIs from memory) influence emotion-generation
	 * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emotion is based on) to influence the emotion generation in F63
	 * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
	 * ZK: use memorized drives (from entities) AND memorized emotions (from images) from perception track for emotion-generation
	 */
	private HashMap<String, Double> getEmotionValuesFromPerception() {
		// values from perception-track (triggered emotions)
		double rPerceptionPleasure_DM = 0.0; 
		double rPerceptionUnpleasure_DM = 0.0;
		double rPerceptionLibid_DM = 0.0;
		double rPerceptionAggr_DM = 0.0;
		
		double rPerceptionPleasure_EXP = 0.0; 
        double rPerceptionUnpleasure_EXP = 0.0;
        double rPerceptionLibid_EXP = 0.0;
        double rPerceptionAggr_EXP = 0.0;
        
        double rPerceptionPleasure_BS = 0.0; 
        double rPerceptionUnpleasure_BS = 0.0;
        double rPerceptionLibid_BS = 0.0;
        double rPerceptionAggr_BS = 0.0;
				
		double rInfluencePerception = 0;
		
		//kollmann: entity valuation ranges from -1 (maximum dislike) to 1 (maximum like)
		double nEntityValuation = 0.0;
		
		clsDriveMesh oDM = null;
		//clsEmotion oExperiencedEmotion = null;
		
		// use QoA of the PI's entities  for emotion-generation
		for(clsAssociation oPIINtAss: moPerceptions_IN.getInternalAssociatedContent()) {
			if(oPIINtAss.getContentType() == eContentType.PARTOFASSOCIATION){
				clsEmotion oEmotionFromBodystate = null;
			    clsEmotion oExperiencedEmotion = null;
			    
			    for (clsAssociation oEntityAss: ((clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getExternalAssociatedContent()) {
					// exclude empty spaces (they are currently associated with drives). just use entities. (this is not nice, but due to the use of emptySpaces-objekte in ars necessary)
					if ( oEntityAss.getContentType() == eContentType.ASSOCIATIONDM && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")   ) {
																		
						oDM = (clsDriveMesh)oEntityAss.getAssociationElementA();
						
						if(oDM.getContentType() == eContentType.LIBIDO) {
							//rPerceptionPleasure += mrPerceptionPleasureImpactFactor*oDM.getQuotaOfAffect();
							// Change by Kollmann - should be calculated as any other emotion value, by nonProportionalAggregation with PerceptionInfluence
							rPerceptionPleasure_DM = nonProportionalAggregation(rPerceptionPleasure_DM, mrInfluenceDrivesOnPerceivedObjects*oDM.getQuotaOfAffect());
						}
						else {
							
							// influence of perceived objects is dependent on agent's actual drives. e.g. if the agent is hungry and it perceives a schnitzel, 
							// the perception influences emotion-generation more than in the case of an agent without hunger  
//							for (clsDriveMesh oActualDM: moDrives_IN) {
//								if (oDM.compareTo(oActualDM) >= 0.75) {
//									rInfluencePerception = oDM.getQuotaOfAffect() * oActualDM.getQuotaOfAffect();
//									if(rInfluencePerception>1) rInfluencePerception = 1;
//								}
//							}
							
							List<clsDriveMesh> oMatchingDMs = clsDriveMesh.filterListByDM(moDrives_IN, oDM, clsDriveMesh.StructureComparator);
							if(oMatchingDMs.size() > 1) {
							    log.error("DM " + oDM + " has more than one simmilar DMs in the drives list.");
							    rInfluencePerception = 0;
							}
							
							if(oMatchingDMs.size() > 0) {
							    rInfluencePerception = oMatchingDMs.get(0).getQuotaOfAffect() * oDM.getQuotaOfAffect();
							} else {
							    log.warn("DM " + oDM + " has no matching DMs in drive list.");
							    rInfluencePerception = 0;
							}
							
						    //rPerceptionUnpleasure = nonProportionalAggregation(rPerceptionUnpleasure, oDM.getQuotaOfAffect());
							if(oDM.getDriveComponent() == eDriveComponent.LIBIDINOUS) {
								rPerceptionLibid_DM = nonProportionalAggregation(rPerceptionLibid_DM, mrInfluenceDrivesOnPerceivedObjects*rInfluencePerception*oDM.getQuotaOfAffect());
							} else if (oDM.getDriveComponent() == eDriveComponent.AGGRESSIVE){
								rPerceptionAggr_DM = nonProportionalAggregation(rPerceptionAggr_DM, mrInfluenceDrivesOnPerceivedObjects*rInfluencePerception*oDM.getQuotaOfAffect());
							}
							
							rPerceptionPleasure_DM = nonProportionalAggregation(rPerceptionPleasure_DM, mrInfluenceDrivesOnPerceivedObjects*rInfluencePerception*oDM.getQuotaOfAffect());
						}
						
					}
					
					//koller Emotionsuebertragung. Hier wirken sich die Emotionen wahrgenommener Bodystates anderer Agenten auf die eigenen Affektbeträge aus.

                    if ( oEntityAss.getContentType() == eContentType.ASSOCIATIONATTRIBUTE && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")   ) {
                        if(oEntityAss.getAssociationElementA().getContentType() == eContentType.ENTITY){
                            
                            clsThingPresentationMesh oTPMA = (clsThingPresentationMesh)oEntityAss.getAssociationElementA();
                            
                            /*Kollmann: THE IF STATEMENT BELLOW IS THE LAST OPPERTUNITY FOR TURNING THE SELF-PERCEPTION LOOP OFF (or on)
                             * 
                             * The self perception currently (06/2015) moves the agents emotion state towards the emotion state associated to the
                             * bodystate the agent perceives at himself. E.g. if the agents emotion state would slowly change from anxious to joyful,
                             * as the agent starts emiting the expressions associated with joy, the target emotion state would immediately take a leap
                             * towards the emotion state associated with the JOY-bodystate.
                             * 
                             *  ATTENTION: Which expression variables are used for which emotion state is NOT stored in memory but hardcoded!
                             *  This means, if you change the memorized bodystates or the emotions associated to these bodystates, the self perception
                             *  behavior could become confusing! E.g. the agent shows ANGER but interprets it as JOY, which would result in the agent
                             *  getting happier while being angry - the result will probably stabilize somewhere between these two emotions.
                             */
                            
                            if(!(oTPMA.getContent().equals("SELF"))){ //koller wenn der bodystate am TPM Self angehängt ist, wird er ignoriert. Es kann duch Entfernen dieses ifs wieder Einfluss bekommen. 
                                if(oEntityAss.getAssociationElementB().getContentType() == eContentType.ENTITY){
                            
                                    clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oEntityAss.getAssociationElementB();
                                    
                                    if(oTPM.getContent().equals("Bodystate")){
                                        for(clsAssociation oAssoc : oTPM.getInternalAssociatedContent()){
                                            if(oAssoc.getAssociationElementB().getContentType() == eContentType.BASICEMOTION){
                                                if(oEmotionFromBodystate != null) {
                                                    log.warn("Entity {} seems to have more than one bodystate associated", oTPMA.getContent());
                                                }
                                                oEmotionFromBodystate = (clsEmotion)oAssoc.getAssociationElementB();
                                            }
                                        }
                                    }
                                }
                            }
                        }  
                    }//end koller  
                    
                    //Kollmann: influence of experienced emotion (memorized emotional valuation of other agents, e.g. "I like him") for entities
                    if(oEntityAss.getContentType().equals(eContentType.ASSOCIATIONEMOTION) && !(( clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equalsIgnoreCase("EMPTYSPACE")) {
                        //check if the association really connects an emotion with a TPM
                        if(oEntityAss.getAssociationElementA() instanceof clsEmotion && oEntityAss.getAssociationElementB() instanceof clsThingPresentationMesh) {
                            if(oExperiencedEmotion != null) {
                                log.warn("Entity {} seems to have more than one experienced emotion associated", ((clsThingPresentationMesh)oEntityAss.getAssociationElementB()).getContent());
                            }
                            oExperiencedEmotion = (clsEmotion) oEntityAss.getAssociationElementA();
                        } else {
                            log.warn("Found ASSOCIATIONEMOTION that does NOT connect clsEmotion to clsThingPresentationMesh:");
                            log.warn(oEntityAss.toString());
                        }
                    }
				}
			    
			    //kollmann: calculate valuation of entity - this will later be used to influence how emotion is transfered
                //          positive evaluation -> direct transfer (e.g. 0.5 pleasure -> 0.5 pleasure)
                //          negative evaluation -> reverse transfer (e.g. 0.5 pleasure -> 0.5 un-pleasure)
			    //The default value of 1 means that, if a bodystate emotion but no experienced emotion is associated to the agent, the transfer will be fully positive
			    
			    double rPleasureFactor = 0.0;
			    double rUnpleasureFactor = 0.0;
			    
			    if(oExperiencedEmotion != null) {
			        
			        //let the expierenced emotion have influence on the current emotion state
			        rPerceptionPleasure_EXP = nonProportionalAggregation(rPerceptionPleasure_EXP, mrExperiencedEmotionImpactFactor * oExperiencedEmotion.getSourcePleasure()); 
			        rPerceptionUnpleasure_EXP = nonProportionalAggregation(rPerceptionUnpleasure_EXP, mrExperiencedEmotionImpactFactor * oExperiencedEmotion.getSourceUnpleasure());
			        rPerceptionLibid_EXP = nonProportionalAggregation(rPerceptionLibid_EXP, mrExperiencedEmotionImpactFactor * oExperiencedEmotion.getSourceLibid());
			        rPerceptionAggr_EXP = nonProportionalAggregation(rPerceptionAggr_EXP, mrExperiencedEmotionImpactFactor * oExperiencedEmotion.getSourceAggr());
			        
                    if(!((clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equals("SELF")) {
                        moAgentEmotionValuation = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 1.0,
                                rPerceptionPleasure_EXP,
                                rPerceptionUnpleasure_EXP,
                                rPerceptionLibid_EXP,
                                rPerceptionAggr_EXP);
                        //moAgentEmotionValuation = oExperiencedEmotion.flatCopy();
                    }
                    
                    
                    
                    //rPositiveInfluenceFactor = oExperiencedEmotion.getSourcePleasure() / (oExperiencedEmotion.getSourcePleasure() + oExperiencedEmotion.getSourceUnpleasure());

                    //calculate scaled transfer factors so their sum does not exceed 1.0
                    rPleasureFactor = oExperiencedEmotion.getSourcePleasure() / Math.max(oExperiencedEmotion.getSourcePleasure() + oExperiencedEmotion.getSourceUnpleasure(), 1);
                    rUnpleasureFactor = oExperiencedEmotion.getSourceUnpleasure() / Math.max(oExperiencedEmotion.getSourcePleasure() + oExperiencedEmotion.getSourceUnpleasure(), 1);
                }
                
                if(oEmotionFromBodystate != null) {
                    //koller EmotionRecognitionFactor * PerceptionPleasureImpactFactor * SourcePleasure/Unpleasure/Aggr/Libid * EmotionIntensity
                    
                    //calculate the transference values depending on the agents valuation from the experienced emotions
                    //first of the positive transfers
                    double rTransferPleasure = (mrPerceptionPleasureImpactFactor*oEmotionFromBodystate.getSourcePleasure()) * rPleasureFactor;
                    double rTransferUnpleasure = (mrPerceptionUnpleasureImpactFactor*oEmotionFromBodystate.getSourceUnpleasure()) * rPleasureFactor;
                    double rTransferAggressive = (mrPerceptionAggressiveImpactFactor*oEmotionFromBodystate.getSourceAggr()) * rPleasureFactor;
                    double rTransferLibidinous = (mrPerceptionLibidinousImpactFactor*oEmotionFromBodystate.getSourceLibid()) * rPleasureFactor;

                    if(!((clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equals("SELF")) {
//                        moAgentAttributedEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 1.0,
//                                mrPerceptionPleasureImpactFactor*oEmotionFromBodystate.getSourcePleasure(),
//                                mrPerceptionUnpleasureImpactFactor*oEmotionFromBodystate.getSourceUnpleasure(),
//                                mrPerceptionAggressiveImpactFactor*oEmotionFromBodystate.getSourceAggr(),
//                                mrPerceptionLibidinousImpactFactor*oEmotionFromBodystate.getSourceLibid());
                        
                        moAgentAttributedEmotion = oEmotionFromBodystate.flatCopy();
                    }
                    
                    //now the "reversed" transfers
                    rTransferPleasure += mrPerceptionUnpleasureImpactFactor*oEmotionFromBodystate.getSourceUnpleasure() * rUnpleasureFactor;
                    rTransferUnpleasure += mrPerceptionPleasureImpactFactor*oEmotionFromBodystate.getSourcePleasure() * rUnpleasureFactor;
                    rTransferAggressive += mrPerceptionLibidinousImpactFactor*oEmotionFromBodystate.getSourceLibid() * rUnpleasureFactor;
                    rTransferLibidinous += mrPerceptionAggressiveImpactFactor*oEmotionFromBodystate.getSourceAggr() * rUnpleasureFactor;
                    
                    rPerceptionPleasure_BS = nonProportionalAggregation(rPerceptionPleasure_BS, mrEmotionrecognitionImpactFactor * rTransferPleasure);
                    rPerceptionUnpleasure_BS = nonProportionalAggregation(rPerceptionUnpleasure_BS, mrEmotionrecognitionImpactFactor * rTransferUnpleasure);
                    rPerceptionLibid_BS = nonProportionalAggregation(rPerceptionLibid_BS, mrEmotionrecognitionImpactFactor * rTransferLibidinous);
                    rPerceptionAggr_BS = nonProportionalAggregation(rPerceptionAggr_BS, mrEmotionrecognitionImpactFactor * rTransferAggressive);
                    if(!F31_NeuroDeSymbolizationActionCommands.share)
                    {
                        rPerceptionUnpleasure_BS=rPerceptionUnpleasure_BS;
                    }
                    if(!((clsThingPresentationMesh)oPIINtAss.getAssociationElementB()).getContent().equals("SELF")) {
                        moAgentTransferedEmotion = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 1.0,
                                rPerceptionPleasure_BS,
                                rPerceptionUnpleasure_BS,
                                rPerceptionLibid_BS,
                                rPerceptionAggr_BS);
                    }
                    if(this.getAgentIndex()==0)
                    {
                        transUnpleasure.add(rPerceptionUnpleasure_BS);
                    }
                }
			}
        }
		
		rPerceptionUnpleasure_DM = nonProportionalAggregation(rPerceptionUnpleasure_DM, rPerceptionLibid_DM+rPerceptionAggr_DM);
         
		HashMap<String, Double> oPerceptionExtractedValues = new HashMap<String, Double>();
		oPerceptionExtractedValues.put("rPerceptionDriveMeshPleasure", rPerceptionPleasure_DM);
		oPerceptionExtractedValues.put("rPerceptionDriveMeshUnpleasure", rPerceptionUnpleasure_DM);
		oPerceptionExtractedValues.put("rPerceptionDriveMeshLibid", rPerceptionLibid_DM);
		oPerceptionExtractedValues.put("rPerceptionDriveMeshAggr", rPerceptionAggr_DM);
		
		oPerceptionExtractedValues.put("rPerceptionExperiencePleasure", rPerceptionPleasure_EXP);
        oPerceptionExtractedValues.put("rPerceptionExperienceUnpleasure", rPerceptionUnpleasure_EXP);
        oPerceptionExtractedValues.put("rPerceptionExperienceLibid", rPerceptionLibid_EXP);
        oPerceptionExtractedValues.put("rPerceptionExperienceAggr", rPerceptionAggr_EXP);
        
        oPerceptionExtractedValues.put("rPerceptionBodystatePleasure", rPerceptionPleasure_BS);
        oPerceptionExtractedValues.put("rPerceptionBodystateUnpleasure", rPerceptionUnpleasure_BS);
        oPerceptionExtractedValues.put("rPerceptionBodystateLibid", rPerceptionLibid_BS);
        oPerceptionExtractedValues.put("rPerceptionBodystateAggr", rPerceptionAggr_BS);
		
		return oPerceptionExtractedValues;
	}
	
	/* (non-Javadoc)
    *
    * @author schaat
    * 05.07.2012, 15:48:45
    * emotions triggered by perception (by - to PI- similar RIs from memory) influence emotion-generation
    * how does the triggered emotions influence the generated emotion? KD: save basic-categories in emotion and use them (unpleasure etc the emotion is based on) to influence the emotion generation in F63
    * hence, the basic info of the triggered emotion is "mixed" with the categories form the drive track and the emotions are generated based on these mixed information
    * ZK: use memorized drives (from entities) AND memorized emotions (from images) from perception track for emotion-generation
    */
   private HashMap<String, Double> getEmotionValuesFromMemory() {
       double rMemoryPleasure = 0.0; 
       double rMemoryUnpleasure = 0.0;
       double rMemoryLibid = 0.0;
       double rMemoryAggr = 0.0;
               
       double rAssociationWeight = 0;
       
       clsEmotion oEmotionFromMemory = null;
       clsThingPresentationMesh oRI = null;
       
       // use  assoc. emotions from PI's RIs for emotion-generation        
       for (clsAssociation oPIExtAss : moPerceptions_IN.getExternalAssociatedContent()){
           if(oPIExtAss.getContentType() == eContentType.ASSOCIATIONPRI){
               if(oPIExtAss.getAssociationElementB().getContentType() == eContentType.RI ||
                  oPIExtAss.getAssociationElementB().getContentType() == eContentType.RPI ||
                  oPIExtAss.getAssociationElementB().getContentType() == eContentType.RPA) {
                   oRI = (clsThingPresentationMesh)oPIExtAss.getAssociationElementB();
                   
                   for (clsAssociation oRIAss: oRI.getExternalAssociatedContent()) {
                       if (oRIAss.getContentType() == eContentType.ASSOCIATIONEMOTION && oRIAss instanceof clsAssociationEmotion) {
                           oEmotionFromMemory = ((clsAssociationEmotion)oRIAss).getEmotion();
                           
                           //kollmann: to allow control over which memories influence the state in which do not (neccessray for reflected feelings evaluation, for example)
                           //          we only consider emotions with content type MEMORIZEDEMOTION
                           
                           if(oEmotionFromMemory.getContentType().equals(eContentType.MEMORIZEDEMOTION)) {
                               // the more similar the memorized image is, the more influence the associated emotion has on emotion-generation
                               rAssociationWeight = oPIExtAss.getMrWeight();
                               rMemoryPleasure = nonProportionalAggregation(rMemoryPleasure, mrInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourcePleasure()); 
                               rMemoryUnpleasure = nonProportionalAggregation(rMemoryUnpleasure, mrInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourceUnpleasure());
                               rMemoryLibid = nonProportionalAggregation(rMemoryLibid, mrInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourceLibid());
                               rMemoryAggr = nonProportionalAggregation(rMemoryAggr, mrInfluenceRememberedImages*rAssociationWeight*oEmotionFromMemory.getSourceAggr());
                           }
                       }
                   }
               }
           }
           
       }
       
       HashMap<String, Double> oMemoryExtractedValues = new HashMap<String, Double>();
       oMemoryExtractedValues.put("rPerceptionPleasure", rMemoryPleasure);
       oMemoryExtractedValues.put("rPerceptionUnpleasure", rMemoryUnpleasure + socialRule);
       oMemoryExtractedValues.put("rPerceptionLibid", rMemoryLibid);
       oMemoryExtractedValues.put("rPerceptionAggr", rMemoryAggr + socialRule);
       
       return oMemoryExtractedValues;
   }
	
//	/* (non-Javadoc)
//	 *
//	 * @author schaat
//	 * 03.07.2012, 15:48:45
//	 * 
//	 */
//	private void generateEmotion(eEmotionType poEmotionType, double prEmotionIntensity, double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr) {
//		moEmotions_OUT.add(clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, poEmotionType, prEmotionIntensity),  prSourcePleasure,  prSourceUnpleasure,  prSourceLibid,  prSourceAggr));
//	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
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
		send_I5_21(moEmotions_OUT, moWordingToContext);
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
		mnPsychicInstances = ePsychicInstances.EGO;
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
		moDescription = "F63: F63 generate Emotions based on  Pleasure and  Unpleasure from the drive- and perception track. ";
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:23:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(pa._v38.memorymgmt.datatypes.clsThingPresentationMesh)
	 */
	@Override
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {
	    moWordingToContext = moWordingToContext2;
	    try {
			moPerceptions_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:23:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_receive#receive_I5_3(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
    @Override
	public void receive_I5_3(
			ArrayList<clsDriveMesh> poDrives) {
		moDrives_IN = (ArrayList<clsDriveMesh>) deepCopy(poDrives); 
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:33:59
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_send#send_I5_21(java.util.ArrayList)
	 */
	@Override
	public void send_I5_21(ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
		((I5_21_receive)moModuleList.get(55)).receive_I5_21(poEmotions, moWordingToContext2);
		
	}

	
	/*************************************************************/
	/***                   TIME CHART METHODS                  ***/
	/*************************************************************/
	
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
		
		oValues.add(oDrivesExtractedValues.get("rDrivePleasure"));
		oValues.add(oDrivesExtractedValues.get("rDriveUnpleasure"));
		
		oValues.add(oPerceptionExtractedValues.get("rPerceptionDriveMeshAggr") + oPerceptionExtractedValues.get("rPerceptionExperienceAggr") + oPerceptionExtractedValues.get("rPerceptionBodystateAggr"));
		
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
		return new ArrayList<String>(Arrays.asList("rDrivePleasure","rDriveUnpleasure", "rPerceptionUnpleasure"));
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
		return "";
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
		return "Composition of Emotions";
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
		return 20;
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

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartAxis()
	 */
	@Override
	public String getCombinedTimeChartAxis() {
		return "";
	}

	/**
	 * DOCUMENT - Searaches a List of clsEmotion objects for emotions of a specific type and return the intensity
	 * 
	 *            If an emotion occurs more than once, the QoAs will be summed up.
	 *
	 * @author Kollmann
	 * @since 18.05.2015 13:53:08
	 *
	 * @param poEmotionList
	 * @param poEmotionType
	 * @return
	 */
	private Double extractEmotionValueForType(List<clsEmotion> poEmotionList, eEmotionType poEmotionType) {
	    //EMOTIONS
        Double oQoA= 0.0;
        for(clsEmotion oEmotion : poEmotionList) {
            if(oEmotion.getContent().equals(poEmotionType))
                oQoA += oEmotion.getEmotionIntensity();
        }
        
        return oQoA;
	}
	
	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getCombinedTimeChartData() {
	    ArrayList<clsEmotion> oEmotions = moEmotions_OUT.get(0).generateExtendedEmotions();
	    ArrayList<clsEmotion> oTargetEmotions = moTargetEmotion.generateExtendedEmotions();
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		
		//EMOTIONS
		ArrayList<Double> oAnger =new ArrayList<Double>();
		oAnger.add(extractEmotionValueForType(oEmotions, eEmotionType.ANGER));
		if (mbShowTargetEmotionInChart)
		{
		    oAnger.add(extractEmotionValueForType(oTargetEmotions, eEmotionType.ANGER));
		}
		oResult.add(oAnger);
		
		ArrayList<Double> oAnxiety =new ArrayList<Double>();
		oAnxiety.add(extractEmotionValueForType(oEmotions, eEmotionType.ANXIETY));
		if (mbShowTargetEmotionInChart)
        {
            oAnxiety.add(extractEmotionValueForType(oTargetEmotions, eEmotionType.ANXIETY));
        }
        oResult.add(oAnxiety);
        
        ArrayList<Double> oMourning =new ArrayList<Double>();
        oMourning.add(extractEmotionValueForType(oEmotions, eEmotionType.MOURNING));
        if (mbShowTargetEmotionInChart)
        {
            oMourning.add(extractEmotionValueForType(oTargetEmotions, eEmotionType.MOURNING));
        }
        oResult.add(oMourning);
        
        ArrayList<Double> oSaturation =new ArrayList<Double>();
        oSaturation.add(extractEmotionValueForType(oEmotions, eEmotionType.SATURATION));
        if (mbShowTargetEmotionInChart)
        {
            oSaturation.add(extractEmotionValueForType(oTargetEmotions, eEmotionType.SATURATION));
        }
        oResult.add(oSaturation);
        
        ArrayList<Double> oElation =new ArrayList<Double>();
        oElation.add(extractEmotionValueForType(oEmotions, eEmotionType.ELATION));
        if (mbShowTargetEmotionInChart)
        {
            oElation.add(extractEmotionValueForType(oTargetEmotions, eEmotionType.ELATION));
        }
        oResult.add(oElation);
        
        ArrayList<Double> oJoy =new ArrayList<Double>();
        oJoy.add(extractEmotionValueForType(oEmotions, eEmotionType.JOY));
        if (mbShowTargetEmotionInChart)
        {
            oJoy.add(extractEmotionValueForType(oTargetEmotions, eEmotionType.JOY));
        }
        oResult.add(oJoy);
        
		//Chart Drive
		ArrayList<Double> oDrive =new ArrayList<Double>();
		oDrive.add(oDrivesExtractedValues.get("rDriveAggr"));
		oDrive.add(oDrivesExtractedValues.get("rDriveLibid"));
		oDrive.add(oDrivesExtractedValues.get("rDriveUnpleasure"));
		oDrive.add(oDrivesExtractedValues.get("rDrivePleasure"));
		oResult.add(oDrive);
		
			
		//Chart Perception
		ArrayList<Double> oPerception =new ArrayList<Double>();
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionDriveMeshAggr") + oPerceptionExtractedValues.get("rPerceptionExperienceAggr") + oPerceptionExtractedValues.get("rPerceptionBodystateAggr"));
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionDriveMeshLibid") + oPerceptionExtractedValues.get("rPerceptionExperienceLibid") + oPerceptionExtractedValues.get("rPerceptionBodystateLibid"));
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionDriveMeshUnpleasure") + oPerceptionExtractedValues.get("rPerceptionExperienceUnpleasure") + oPerceptionExtractedValues.get("rPerceptionBodystateUnpleasure"));
		oPerception.add(oPerceptionExtractedValues.get("rPerceptionDriveMeshPleasure") + oPerceptionExtractedValues.get("rPerceptionExperiencePleasure") + oPerceptionExtractedValues.get("rPerceptionBodystatePleasure"));
		oResult.add(oPerception);

        //Chart Memory
        ArrayList<Double> oMemory =new ArrayList<Double>();
        oMemory.add(oMemoryExtractedValues.get("rPerceptionAggr"));
        oMemory.add(oMemoryExtractedValues.get("rPerceptionLibid"));
        oMemory.add(oMemoryExtractedValues.get("rPerceptionUnpleasure"));
        oMemory.add(oMemoryExtractedValues.get("rPerceptionPleasure"));
        oResult.add(oMemory);
		
		return oResult;
	}


	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getChartTitles()
	 */
	@Override
	public ArrayList<String> getChartTitles() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add(eEmotionType.ANGER.toString());
		oResult.add(eEmotionType.ANXIETY.toString());
		oResult.add(eEmotionType.MOURNING.toString());
		
		oResult.add(eEmotionType.SATURATION.toString());
		oResult.add(eEmotionType.ELATION.toString());
		oResult.add(eEmotionType.JOY.toString());
		
		
		oResult.add("DRIVES");
		oResult.add("PERCEPTION");
		oResult.add("MEMORY");
        
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 1:31:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getValueCaptions()
	 */
	@Override
	public ArrayList<ArrayList<String>> getValueCaptions() {
		ArrayList<ArrayList<String>> oResult = new ArrayList<ArrayList<String>>();
		
		//Emotions
		
		//ChartAnger
		ArrayList<String> chartAnger = new ArrayList<String>();
		chartAnger.add("Current Emotion "+eEmotionType.ANGER.toString());
		if (mbShowTargetEmotionInChart)
        {
            chartAnger.add("Target Emotion "+eEmotionType.ANGER.toString());
        }
		oResult.add(chartAnger);
		
		//ChartFear
		ArrayList<String> chartFear = new ArrayList<String>();
		chartFear.add("Current Emotion "+eEmotionType.ANXIETY.toString());
		if (mbShowTargetEmotionInChart)
        {
            chartFear.add("Target Emotion "+eEmotionType.ANXIETY.toString());
        }
        oResult.add(chartFear);
		
		//ChartGrief
		ArrayList<String> chartGrief = new ArrayList<String>();
		chartGrief.add("Current Emotion "+eEmotionType.MOURNING.toString());
		if (mbShowTargetEmotionInChart)
        {
            chartGrief.add("Target Emotion "+eEmotionType.MOURNING.toString());
        }
        oResult.add(chartGrief);	
		
		//ChartLoveSaturation
		ArrayList<String> chartLoveSaturation = new ArrayList<String>();
		chartLoveSaturation.add("Current Emotion "+eEmotionType.SATURATION.toString());
		if (mbShowTargetEmotionInChart)
        {
            chartLoveSaturation.add("Target Emotion "+eEmotionType.SATURATION.toString());
        }
        oResult.add(chartLoveSaturation);
		
		//ChartLoveexhilaration
		ArrayList<String> chartLoveExhilaration = new ArrayList<String>();
		chartLoveExhilaration.add("Current Emotion "+eEmotionType.ELATION.toString());
		if (mbShowTargetEmotionInChart)
        {
            chartLoveExhilaration.add("Target Emotion "+eEmotionType.ELATION.toString());
        }
        oResult.add(chartLoveExhilaration);	
		
		//ChartPleasure
		ArrayList<String> chartPleasure= new ArrayList<String>();
		chartPleasure.add("Current Emotion "+eEmotionType.JOY.toString());
		if (mbShowTargetEmotionInChart)
        {
            chartPleasure.add("Target Emotion "+eEmotionType.JOY.toString());
        }
        oResult.add(chartPleasure);	
		
		
		//ChartDrive
		ArrayList<String> chartDrive = new ArrayList<String>();
		chartDrive.add("Aggr");
		chartDrive.add("Libid");
		chartDrive.add("Unpleasure");
		chartDrive.add("Pleasure");
		oResult.add(chartDrive);
		
		
		//ChartPerception
		ArrayList<String> chartPerception = new ArrayList<String>();
		chartPerception.add("Aggr");
		chartPerception.add("Libid");
		chartPerception.add("Unpleasure");
		chartPerception.add("Pleasure");
		oResult.add(chartPerception);
		
		//ChartPerception
        ArrayList<String> chartMemory = new ArrayList<String>();
        chartMemory.add("Aggr");
        chartMemory.add("Libid");
        chartMemory.add("Unpleasure");
        chartMemory.add("Pleasure");
        oResult.add(chartMemory);
        
		return oResult;
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

    /* (non-Javadoc)
     *
     * @since 30.04.2015 13:20:13
     * 
     * @see inspector.interfaces.itfInspectorStackedAreaChart#getTitle()
     */
    @Override
    public String getTitle(String poLabel) {
        return poLabel + " development";
    }
    
    /**
     * DOCUMENT - Returns a List of double values representing the contributions to the dimension of the base emotion vector.
     * 
     *            The dimensions of the base emotion vector are calculated by non-proportional aggregation of several values.
     *            This method simply scales these contribution values to the actual value in the base emotion vector.
     *            E.g. NPA ... non-propotional aggregation (rNewBaseValue = rBaseValue + (1 - rBaseValue) * rAddValue;
     *                 A ... 0.6
     *                 B ... 0.6
     *                 NPA(A, B) = 0.84
     *                 SUM(A, B) = 1.2
     *                 The method would than calculate the contribution of A like this:
     *                 cont(A) = NPA(A, B) * (A / SUM(A,B)) = 0,42
     *                 cont(B) = NPA(A, B) * (B / SUM(A,B)) = 0,42
     *
     * @author Kollmann
     * @since 04.05.2015 11:49:10
     *
     * @param prAggregatedValue The value calculated via non-proportional aggreagtion
     * @param poPostfix The postfix that will be appended to the value hashmaps to retrieve the contributor value
     * @return List of double values representing the different contributions to the provided aggregated value
     */
    Collection<Double> getDimensionContributers(double prAggregatedValue, String poPostfix) {
        Collection<Double> rResults = new ArrayList<Double>();
        
        double rDrive = oDrivesExtractedValues.get("rDrive" + poPostfix);
        double rPerceptionDM = oPerceptionExtractedValues.get("rPerceptionDriveMesh" + poPostfix);
        double rPerceptionEXP = oPerceptionExtractedValues.get("rPerceptionExperience" + poPostfix);
        double rPerceptionBS = oPerceptionExtractedValues.get("rPerceptionBodystate" + poPostfix);
        double rMemory = oMemoryExtractedValues.get("rPerception" + poPostfix);
        double rpaintemp=0;
        if(poPostfix == "Unpleasure")
        {
            rpaintemp = rpain;
        }
//        if(poPostfix == "Pleasure")
//        {
//            rDrive = oDrivesExtractedValues.get("rDrive" + poPostfix)*10;
//        }
        double rSum = rDrive + rPerceptionDM + rPerceptionEXP + rPerceptionBS + rMemory + rpaintemp;
        
        rResults.add(prAggregatedValue * rDrive / rSum);
        rResults.add(prAggregatedValue * rPerceptionDM / rSum);
        rResults.add(prAggregatedValue * rPerceptionEXP / rSum);
        rResults.add(prAggregatedValue * rPerceptionBS / rSum);
        rResults.add(prAggregatedValue * rMemory / rSum);
        rResults.add(prAggregatedValue * rpaintemp / rSum);
        
        return rResults;
    }
    
    /* (non-Javadoc)
     *
     * @since 30.04.2015 13:20:13
     * 
     * @see inspector.interfaces.itfInspectorStackedAreaChart#getData()
     */
    @Override
    public ArrayList<Double> getData(String poLabel) {
        ArrayList<Double> oData = new ArrayList<>();
        double[] rContributions = new double[5];
        
        switch(poLabel) {
        case "PLEASURE":
            oData.addAll(getDimensionContributers(moTargetEmotion.getSourcePleasure(), "Pleasure"));
            break;
        case "UNPLEASURE":
            oData.addAll(getDimensionContributers(moTargetEmotion.getSourceUnpleasure(), "Unpleasure"));
            break;
        case "AGGRESSIVE":
            oData.addAll(getDimensionContributers(moTargetEmotion.getSourceAggr(), "Aggr"));
            break;
        case "LIBIDINOUS":
            oData.addAll(getDimensionContributers(moTargetEmotion.getSourceLibid(), "Libid"));
            break;
        default:
            log.warn("Unknown label for StackedBarChartInspector in getData method");
            break;
        }
        
        return oData;
    }
    
    /* (non-Javadoc)
     *
     * @since 30.04.2015 13:20:13
     * 
     * @see inspector.interfaces.itfInspectorStackedAreaChart#getCategoryCaptions()
     */
    @Override
    public ArrayList<String> getCategoryCaptions(String poLabel) {
        ArrayList<String> oData = new ArrayList<String>();

        oData.add("Current Drives");
        oData.add("Memorized Drive Satisfactions");
        oData.add("Memorized Emotions - Entities");
        oData.add("Transferred Emotions");
        oData.add("Memorized Emotions - Situations");
        oData.add("Pain(Body)");

        return oData;
    }

    /* (non-Javadoc)
     *
     * @since 10.07.2015 11:40:16
     * 
     * @see inspector.interfaces.itfInspectorMultipleBarChart#getBarChartTitle(java.lang.String)
     */
    @Override
    public String getBarChartTitle(String poLabel) {
        return poLabel;
    }

    /* (non-Javadoc)
     *
     * @since 10.07.2015 11:40:16
     * 
     * @see inspector.interfaces.itfInspectorMultipleBarChart#getBarChartData(java.lang.String)
     */
    @Override
    public ArrayList<ArrayList<Double>> getBarChartData(String poLabel) {
        ArrayList<ArrayList<Double>> oOuterData = new ArrayList<ArrayList<Double>>();
        
        switch(poLabel) {
        case "Associated Emotion":
            if(moAgentAttributedEmotion != null) {
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentAttributedEmotion.getSourcePleasure())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentAttributedEmotion.getSourceUnpleasure())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentAttributedEmotion.getSourceAggr())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentAttributedEmotion.getSourceLibid())));
            } else {
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
            }
            break;
        case "(weighted) Valuation":
            if(moAgentEmotionValuation != null) {
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentEmotionValuation.getSourcePleasure())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentEmotionValuation.getSourceUnpleasure())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentEmotionValuation.getSourceAggr())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentEmotionValuation.getSourceLibid())));
            } else {
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
            }
            break;
        case "Transfered Emotion":
            if(moAgentTransferedEmotion != null) {
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentTransferedEmotion.getSourcePleasure())));
                if(F31_NeuroDeSymbolizationActionCommands.share)
                {
                    oOuterData.add(new ArrayList<>(Arrays.asList(moAgentTransferedEmotion.getSourceUnpleasure())));
                }
                else
                {
                    oOuterData.add(new ArrayList<>(Arrays.asList(moAgentTransferedEmotion.getSourceUnpleasure()/2)));
                }
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentTransferedEmotion.getSourceAggr())));
                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentTransferedEmotion.getSourceLibid())));
            } else {
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
            }
            break;
//        case "Pain(Body)":
//                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
//                oOuterData.add(new ArrayList<>(Arrays.asList(moAgentTransferedEmotion.getSourceUnpleasure())));
//                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
//                oOuterData.add(new ArrayList<Double>(Arrays.asList(0.0)));
//            break;
        default:
            log.warn("Trying to generate bar chart data for non-existant label {}", poLabel);
            break;
        }
        
        return oOuterData;
    }

    /* (non-Javadoc)
     *
     * @since 10.07.2015 11:40:16
     * 
     * @see inspector.interfaces.itfInspectorMultipleBarChart#getBarChartCategoryCaptions(java.lang.String)
     */
    @Override
    public ArrayList<String> getBarChartCategoryCaptions(String poLabel) {
        ArrayList<String> oCaptions = new ArrayList<String>();
        
        if(!poLabel.isEmpty()) {
            oCaptions.add("Pleasure");
            oCaptions.add("Unpleasure");
            oCaptions.add("Aggressive");
            oCaptions.add("Libidinous");
        }
        
        return oCaptions;
    }

    /* (non-Javadoc)
     *
     * @since 10.07.2015 11:40:16
     * 
     * @see inspector.interfaces.itfInspectorMultipleBarChart#getBarChartColumnCaptions(java.lang.String)
     */
    @Override
    public ArrayList<String> getBarChartColumnCaptions(String poLabel) {
        ArrayList<String> oCaptions = new ArrayList<String>();
        
        if(!poLabel.isEmpty()) {
            oCaptions.add("Value");
        }
        
        return oCaptions;
    }
}
