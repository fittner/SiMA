/**
 * clsPsychicApperatus.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 14:55:59
 */
package base.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import prementalapparatus.modules.F01_SensorsMetabolism;
import prementalapparatus.modules.F02_NeurosymbolizationOfNeeds;
import prementalapparatus.modules.F10_SensorsEnvironment;
import prementalapparatus.modules.F11_NeuroSymbolizationEnvironment;
import prementalapparatus.modules.F12_SensorsBody;
import prementalapparatus.modules.F13_NeuroSymbolizationBody;
import prementalapparatus.modules.F31_NeuroDeSymbolizationActionCommands;
import prementalapparatus.modules.F32_Actuators;
import prementalapparatus.modules.F39_SeekingSystem_LibidoSource;
import prementalapparatus.modules.F40_NeurosymbolizationOfLibido;
import prementalapparatus.modules.F67_BodilyReactionsOnEmotions;
import primaryprocess.modules.F06_DefenseMechanismsForDrives;
import primaryprocess.modules.F07_SuperEgoReactive;
import primaryprocess.modules.F08_ConversionToSecondaryProcessForDriveWishes;
import primaryprocess.modules.F14_ExternalPerception;
import primaryprocess.modules.F18_CompositionOfQuotaOfAffectsForPerception;
import primaryprocess.modules.F19_DefenseMechanismsForPerception;
import primaryprocess.modules.F35_EmersionOfBlockedContent;
import primaryprocess.modules.F37_PrimalRepressionForPerception;
import primaryprocess.modules.F45_DischargeOfPsychicIntensity;
import primaryprocess.modules.F46_MemoryTracesForPerception;
import primaryprocess.modules.F48_AccumulationOfQuotaOfAffectsForDrives;
import primaryprocess.modules.F49_PrimalRepressionForDrives;
import primaryprocess.modules.F54_EmersionOfBlockedDriveContent;
import primaryprocess.modules.F55_SuperEgoProactive;
import primaryprocess.modules.F56_Desexualization_Neutralization;
import primaryprocess.modules.F57_MemoryTracesForDrives;
import primaryprocess.modules.F63_CompositionOfEmotions;
import primaryprocess.modules.F64_PartialSexualDrives;
import primaryprocess.modules.F65_PartialSelfPreservationDrives;
import primaryprocess.modules.F71_CompositionOfExtendedEmotion;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.helpstructures.clsPair;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import memorymgmt.storage.DT2_BlockedContentStorage;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import memorymgmt.storage.DT4_PleasureStorage;
import modules.interfaces.eInterfaces;
import externalmessager.DatapointHandler;
import externalmessager.MonitorExecutor;
import secondaryprocess.functionality.decisionpreparation.DecisionEngine;
import secondaryprocess.functionality.decisionpreparation.GoalInitiationProcessor.GoalInitiator;
import secondaryprocess.modules.F20_CompositionOfFeelings;
import secondaryprocess.modules.F21_ConversionToSecondaryProcessForPerception;
import secondaryprocess.modules.F23_ExternalPerception_focused;
import secondaryprocess.modules.F26_DecisionMaking;
import secondaryprocess.modules.F29_EvaluationOfImaginaryActions;
import secondaryprocess.modules.F30_MotilityControl;
import secondaryprocess.modules.F47_ConversionToPrimaryProcess;
import secondaryprocess.modules.F51_RealityCheckWishFulfillment;
import secondaryprocess.modules.F52_GenerationOfImaginaryActions;
import secondaryprocess.modules.F53_RealityCheckActionPlanning;
import secondaryprocess.modules.F61_Localization;
import secondaryprocess.modules.F66_SpeechProduction;
import testfunctions.clsTester;
import timing.TimingStarter;
import utils.clsGetARSPath;

/**
 * This class holds all instances of model v38. It is responsible for their creation and configuration. Further it contains the
 * knowledgebase and various storages. This is the container for all instances that are relevant of the psychoanalytically inspired 
 * decision unit. The logic that executes the modules is located elsewhere (clsProcessor).
 *   
 * @see pa._v38.clsProcessor
 * 
 * @author deutsch
 * 03.03.2011, 14:55:59
 * 
 */
/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 15.11.2011, 12:38:16
 * 
 */
public class clsPsychicApparatus {
	/** Propertykeyprefix for all entries that are relevant for the knowledgebase; @since 13.07.2011 17:47:23 */
	public static final String P_INFORMATIONREPRESENTATIONMGMT = "INF_REP_MGMT";
	public static final String P_DEBUG_LEVEL = "debuglevel";
	
	public static final String P_PERSONALITY_PARAMETER ="personality.parameter.file";
	public static final String P_PERSONALITY_PARAMETER_DEFAULT ="personality.parameter.default_file";
	public static final String P_DEFAULT_PERSONALITY_PARAMETER_FILE_NAME="default.properties";
	public static final String P_DEFAULT_PERSONALITY_PARAMETER_DEFAULT_FILE_NAME="default.properties";
	
	public F01_SensorsMetabolism moF01_SensorsMetabolism;
	public F02_NeurosymbolizationOfNeeds moF02_NeurosymbolizationOfNeeds;
	//public F03_GenerationOfSelfPreservationDrives moF03_GenerationOfSelfPreservationDrives;
	//public F04_FusionOfSelfPreservationDrives moF04_FusionOfSelfPreservationDrives;
	public F06_DefenseMechanismsForDrives moF06_DefenseMechanismsForDrives;
	public F08_ConversionToSecondaryProcessForDriveWishes moF08_ConversionToSecondaryProcessForDriveWishes;
	public F10_SensorsEnvironment moF10_SensorsEnvironment;
	public F11_NeuroSymbolizationEnvironment moF11_NeuroSymbolizationEnvironment;
	public F12_SensorsBody moF12_SensorsBody;
	public F13_NeuroSymbolizationBody moF13_NeuroSymbolizationBody;
	public F14_ExternalPerception moF14_ExternalPerception;
	public F18_CompositionOfQuotaOfAffectsForPerception moF18_CompositionOfAffectsForPerception;
	public F19_DefenseMechanismsForPerception moF19_DefenseMechanismsForPerception;
	public F20_CompositionOfFeelings moF20_CompositionOfFeelings;
	public F21_ConversionToSecondaryProcessForPerception moF21_ConversionToSecondaryProcessForPerception;
	public F23_ExternalPerception_focused moF23_ExternalPerception_focused;
	public F51_RealityCheckWishFulfillment moF51_RealityCheckWishFulfillment;
	public F26_DecisionMaking moF26_DecisionMaking;
	public F52_GenerationOfImaginaryActions moF27_GenerationOfImaginaryActions;
	public F29_EvaluationOfImaginaryActions moF29_EvaluationOfImaginaryActions;
	public F30_MotilityControl moF30_MotilityControl;
	public F31_NeuroDeSymbolizationActionCommands moF31_NeuroDeSymbolizationActionCommands;
	public F32_Actuators moF32_Actuators;
	public F53_RealityCheckActionPlanning moF53_RealityCheckActionPlanning;
	public F35_EmersionOfBlockedContent moF35_EmersionOfBlockedContent;
	public F37_PrimalRepressionForPerception moF37_PrimalRepressionForPerception;
	public F39_SeekingSystem_LibidoSource moF39_SeekingSystem_LibidoSource;
	public F40_NeurosymbolizationOfLibido moF40_NeurosymbolizationOfLibido;
	public F48_AccumulationOfQuotaOfAffectsForDrives moF48_AccumulationOfQuotaOfAffectsForDrives;
	public F57_MemoryTracesForDrives moF57_MemoryTracesForDrives;
	public F49_PrimalRepressionForDrives moF49_PrimalRepressionForDrives;
	public F54_EmersionOfBlockedDriveContent moF54_EmersionOfBlockedDriveContent;
	public F56_Desexualization_Neutralization moF56_Desexualization_Neutralization;
	public F55_SuperEgoProactive moF55_SuperEgoProactive; 
	public F07_SuperEgoReactive moF07_SuperEgoReactive; 
	public F52_GenerationOfImaginaryActions moF52_GenerationOfImaginaryActions; 
	public F45_DischargeOfPsychicIntensity moF45_LibidoDischarge;
	public F46_MemoryTracesForPerception moF46_MemoryTracesForPerception;
	public F47_ConversionToPrimaryProcess moF47_ConversionToPrimaryProcess;
	public F63_CompositionOfEmotions moF63_CompositionOfEmotions;
	public F61_Localization moF61_Localization;
	public F64_PartialSexualDrives moF64_PartialSexualDrives;
	public F65_PartialSelfPreservationDrives moF65_PartialSelfPreservationDrives;
	public F66_SpeechProduction moF66_SpeechProduction;
	public F67_BodilyReactionsOnEmotions moF67_BodilyReactionOnEmotions;
	public F71_CompositionOfExtendedEmotion moF71_CompositionOfExtendedEmotion;
	
	/** Container for personality parameters ; @since 18.01.2013 15:09:03 */
	public clsPersonalityParameterContainer moPersonalityParameterContainer;
	
	/** The knowlegdebase / aka memory; @since 13.07.2011 17:48:27 */
	//public clsKnowledgeBaseHandler moKnowledgeBaseHandler;
    public itfModuleMemoryAccess moLongTermMemory;
    //public itfModuleMemoryAccess moWorkingMemory;
	/** Libido buffer storage. Necessary for DT1.; @since 13.07.2011 17:48:42 */
	public DT1_PsychicIntensityBuffer moLibidoBuffer;
	/** Blocked content storage. Necessary for DT2.; @since 13.07.2011 17:49:01 */
	public DT2_BlockedContentStorage moBlockedContentStorage;
	/** Free psychic energy storage. Necessary for DT3.; @since 12.10.2011 18:38:57 */
	public DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	public DT4_PleasureStorage moPleasureStorage;
	
	/** (wendt) The instance of the short time memory; @since 15.11.2011 12:38:18 */
	public clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTimeMemory;
	
//	/** (wendt) Instance of the goal memory; @since 24.05.2012 15:21:26 */
//	public clsShortTermMemory moGoalMemory;
	
	/** (havlicek) The instance of ShortTermMemory for managing the concepts; @since 12.10.2012 17:12:15 */
	public clsShortTermMemory moConceptMemory;
	
	//public clsCodeletHandler moCodeletHandler;
	public DecisionEngine moDecisionEngine;
	
	/** Datapoint handler can be seen as server, which keeps values from modules. External clients can access these modules to get updated values (wendt) - insert description; @since 03.01.2014 13:59:12 */
	public final DatapointHandler datapointHandler;

	/** The monitorexecutor is an engine with threads, which are monitoring certain parts of the system (wendt) - insert description; @since 03.01.2014 13:59:31 */
	public MonitorExecutor monitor;
	
	/** (wendt) This is a temporary localization storage, which will save the last perceived objects for some steps; @since 15.11.2011 14:36:56 */
	public clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	/** List of the currently transfered data via the interfaces. Has to be refilled each round at each send_I?_? method manually!; @since 13.07.2011 17:49:33 */
	public SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
	/** List of the modules defined above. Needed for references within them.; @since 13.07.2011 17:49:56 */
	public HashMap<Integer, clsModuleBase> moModules;

	/** The mesh created by all modules and the outgoing interfaces in combination to which module they are connecting to. Static data - does not change after it has been created.; @since 13.07.2011 17:50:19 */
	public HashMap<Integer, ArrayList<clsPair<eInterfaces, Integer>>> moInterfaceMesh;
	/** List of interfaces and the modules it connects to pair(source,target).; @since 13.07.2011 17:50:47 */
	public HashMap<eInterfaces, clsPair<ArrayList<Integer>, ArrayList<Integer>>> moInterfaces_Recv_Send;
	
	/** Unique identifier. The same for the body and the decision unit. Eases debugging and logging.; @since 13.07.2011 17:55:28 */
	private int uid;
	
	private String moDebugLevel;
	

	/**
	 * Creates an instance of this class with the provided properties and parameters. Further, the list of the incoming and outgoing
	 * interfaces that connect the functional modules is generated by introspection.
	 *
	 * @since 13.07.2011 17:56:41
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param poKnowledgeBaseHandler A reference to the knowledgebasehandler.
	 * @param uid Unique identifier. The same for the body and the decision unit.
	 */
	public clsPsychicApparatus(String poPrefix, clsProperties poProp, itfModuleMemoryAccess poMemory, int uid) {
		this.uid = uid;
		
		// --- Set logger properties --- //
		//clsLogger.initLogger(Level.ERROR);	//Init root logger level
//		clsLogger.jlog.removeAllAppenders();
//		clsLogger.jlog.setLevel(Level.DEBUG);
//		//clsLogger.jlog.setLevel(Level.INFO);
//		//Layout layout = new PatternLayout("%p [%t] %c (%F:%L) - %m%n");
//		Layout layout = new PatternLayout("%t: (%F:%L) - %m%n");
//		clsLogger.jlog.addAppender(new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT));
		
		
		moModules = new HashMap<Integer, clsModuleBase>();
		moInterfaceData = new TreeMap<eInterfaces, ArrayList<Object>>();
		
		//moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		moLongTermMemory = poMemory;
		

		//Initialize short time memory
		moShortTimeMemory = new clsShortTermMemory<clsWordPresentationMeshMentalSituation>(60, 7, clsWordPresentationMeshMentalSituation.getNullObject());
		moEnvironmentalImageStorage = new clsEnvironmentalImageMemory(4, 30);	//Memorize 3 turns, 30 entities
		moConceptMemory = new clsShortTermMemory<clsWordPresentationMeshMentalSituation>(60, 7, clsWordPresentationMeshMentalSituation.getNullObject());
		
		//Init codelethandler
		moDecisionEngine = new DecisionEngine(moEnvironmentalImageStorage, moShortTimeMemory, moLibidoBuffer, new GoalInitiator());
		//moCodeletHandler = new clsCodeletHandler(moEnvironmentalImageStorage, moShortTimeMemory);
		//this.registerCodelets();
		
		//Set testmode
		clsTester.getTester().setActivated(false);
		
		//=== Set time monitoring ===//
		//Init datapointhandler
		this.datapointHandler =  new DatapointHandler();
		
		//Init Monitorexecutor singleton - just connect to datapointhandler
		MonitorExecutor.init(this.datapointHandler);
		//Set activated or not
		MonitorExecutor.getMonitor().setActivated(false);
		
		//Start clients, which are running as individual threads. This is a temporal solution
		if (MonitorExecutor.getMonitor().isActivated()==true) {
		      TimingStarter ts = new TimingStarter();
		      ts.startDebugInspectors(this.datapointHandler);
		}

		
		//=== time monitoring end === //
		applyProperties(poPrefix, poProp);
		moDecisionEngine.getCodeletHandler().setLibidoBuffer(moLibidoBuffer);
		fillInterfaceMesh();
		fillInterfaces_Recv_Send();
	}
	
	public int getUid() {
	    return this.uid;
	}
	
	/**
	 * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface. 
	 *
	 * @since 13.07.2011 17:56:42
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @return
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();

		oProp.setProperty(pre +P_PERSONALITY_PARAMETER_DEFAULT, P_DEFAULT_PERSONALITY_PARAMETER_DEFAULT_FILE_NAME);
		oProp.setProperty(pre +P_PERSONALITY_PARAMETER, P_DEFAULT_PERSONALITY_PARAMETER_FILE_NAME);
		
		oProp.setProperty(pre+P_DEBUG_LEVEL, "DEBUG");
		
		oProp.putAll( F01_SensorsMetabolism.getDefaultProperties( pre + F01_SensorsMetabolism.P_MODULENUMBER ));
		oProp.putAll( F02_NeurosymbolizationOfNeeds.getDefaultProperties( pre + F02_NeurosymbolizationOfNeeds.P_MODULENUMBER ));
		oProp.putAll( F06_DefenseMechanismsForDrives.getDefaultProperties( pre + F06_DefenseMechanismsForDrives.P_MODULENUMBER ));
		oProp.putAll( F08_ConversionToSecondaryProcessForDriveWishes.getDefaultProperties( pre + F08_ConversionToSecondaryProcessForDriveWishes.P_MODULENUMBER ));
		oProp.putAll( F10_SensorsEnvironment.getDefaultProperties( pre + F10_SensorsEnvironment.P_MODULENUMBER ));
		oProp.putAll( F11_NeuroSymbolizationEnvironment.getDefaultProperties( pre + F11_NeuroSymbolizationEnvironment.P_MODULENUMBER ));
		oProp.putAll( F12_SensorsBody.getDefaultProperties( pre + F12_SensorsBody.P_MODULENUMBER ));
		oProp.putAll( F13_NeuroSymbolizationBody.getDefaultProperties( pre + F13_NeuroSymbolizationBody.P_MODULENUMBER ));
		oProp.putAll( F14_ExternalPerception.getDefaultProperties( pre + F14_ExternalPerception.P_MODULENUMBER ));
		oProp.putAll( F18_CompositionOfQuotaOfAffectsForPerception.getDefaultProperties( pre + F18_CompositionOfQuotaOfAffectsForPerception.P_MODULENUMBER ));
		oProp.putAll( F19_DefenseMechanismsForPerception.getDefaultProperties( pre + F19_DefenseMechanismsForPerception.P_MODULENUMBER ));
		oProp.putAll( F20_CompositionOfFeelings.getDefaultProperties( pre + F20_CompositionOfFeelings.P_MODULENUMBER ));
		oProp.putAll( F21_ConversionToSecondaryProcessForPerception.getDefaultProperties( pre + F21_ConversionToSecondaryProcessForPerception.P_MODULENUMBER ));
		oProp.putAll( F23_ExternalPerception_focused.getDefaultProperties( pre + F23_ExternalPerception_focused.P_MODULENUMBER ));
		oProp.putAll( F51_RealityCheckWishFulfillment.getDefaultProperties( pre + F51_RealityCheckWishFulfillment.P_MODULENUMBER ));
		oProp.putAll( F26_DecisionMaking.getDefaultProperties( pre + F26_DecisionMaking.P_MODULENUMBER ));
		oProp.putAll( F52_GenerationOfImaginaryActions.getDefaultProperties( pre + F52_GenerationOfImaginaryActions.P_MODULENUMBER ));
		oProp.putAll( F29_EvaluationOfImaginaryActions.getDefaultProperties( pre + F29_EvaluationOfImaginaryActions.P_MODULENUMBER ));
		oProp.putAll( F30_MotilityControl.getDefaultProperties( pre + F30_MotilityControl.P_MODULENUMBER ));
		oProp.putAll( F31_NeuroDeSymbolizationActionCommands.getDefaultProperties( pre + F31_NeuroDeSymbolizationActionCommands.P_MODULENUMBER ));
		oProp.putAll( F32_Actuators.getDefaultProperties( pre + F32_Actuators.P_MODULENUMBER ));
		oProp.putAll( F53_RealityCheckActionPlanning.getDefaultProperties( pre + F53_RealityCheckActionPlanning.P_MODULENUMBER ));
		oProp.putAll( F35_EmersionOfBlockedContent.getDefaultProperties( pre + F35_EmersionOfBlockedContent.P_MODULENUMBER ));
		oProp.putAll( F37_PrimalRepressionForPerception.getDefaultProperties( pre + F37_PrimalRepressionForPerception.P_MODULENUMBER ));
		oProp.putAll( F39_SeekingSystem_LibidoSource.getDefaultProperties( pre + F39_SeekingSystem_LibidoSource.P_MODULENUMBER ));
		oProp.putAll( F40_NeurosymbolizationOfLibido.getDefaultProperties( pre + F40_NeurosymbolizationOfLibido.P_MODULENUMBER ));
		oProp.putAll( F48_AccumulationOfQuotaOfAffectsForDrives.getDefaultProperties( pre + F48_AccumulationOfQuotaOfAffectsForDrives.P_MODULENUMBER ));
		oProp.putAll( F57_MemoryTracesForDrives.getDefaultProperties( pre + F57_MemoryTracesForDrives.P_MODULENUMBER ));
		oProp.putAll( F49_PrimalRepressionForDrives.getDefaultProperties( pre + F49_PrimalRepressionForDrives.P_MODULENUMBER ));
		oProp.putAll( F54_EmersionOfBlockedDriveContent.getDefaultProperties( pre +  F54_EmersionOfBlockedDriveContent.P_MODULENUMBER ));
		oProp.putAll( F56_Desexualization_Neutralization.getDefaultProperties( pre + F56_Desexualization_Neutralization.P_MODULENUMBER ));
		oProp.putAll( F55_SuperEgoProactive.getDefaultProperties( pre + F55_SuperEgoProactive.P_MODULENUMBER ));
		oProp.putAll( F07_SuperEgoReactive.getDefaultProperties( pre + F07_SuperEgoReactive.P_MODULENUMBER ));
		oProp.putAll( F52_GenerationOfImaginaryActions.getDefaultProperties( pre + F52_GenerationOfImaginaryActions.P_MODULENUMBER ));
		oProp.putAll( F45_DischargeOfPsychicIntensity.getDefaultProperties( pre + F45_DischargeOfPsychicIntensity.P_MODULENUMBER ));
		oProp.putAll( F46_MemoryTracesForPerception.getDefaultProperties( pre + F46_MemoryTracesForPerception.P_MODULENUMBER ));
		oProp.putAll( F47_ConversionToPrimaryProcess.getDefaultProperties( pre + F47_ConversionToPrimaryProcess.P_MODULENUMBER ));
		oProp.putAll( F61_Localization.getDefaultProperties( pre + F61_Localization.P_MODULENUMBER ));
		oProp.putAll( F63_CompositionOfEmotions.getDefaultProperties( pre + F63_CompositionOfEmotions.P_MODULENUMBER ));
		oProp.putAll( F64_PartialSexualDrives.getDefaultProperties( pre + F64_PartialSexualDrives.P_MODULENUMBER ));
		oProp.putAll( F65_PartialSelfPreservationDrives.getDefaultProperties( pre + F64_PartialSexualDrives.P_MODULENUMBER ));
	    oProp.putAll( F66_SpeechProduction.getDefaultProperties( pre + F66_SpeechProduction.P_MODULENUMBER ));
	    oProp.putAll( F67_BodilyReactionsOnEmotions.getDefaultProperties( pre + F67_BodilyReactionsOnEmotions.P_MODULENUMBER ));
	    oProp.putAll( F71_CompositionOfExtendedEmotion.getDefaultProperties( pre + F71_CompositionOfExtendedEmotion.P_MODULENUMBER));
	    
		return oProp;
	}	
	
	/**
 	 * Applies the provided properties to the class and sets the selected implementation stage. All functional modules are created in
 	 * this method.
 	 * 
	 * @since 13.07.2011 17:56:46
	 * 
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 */
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		try {
			//TODO HZ - Integrate to Properties
			//FIXME (Zeilinger) - TD 2011/04/11 commented the (recreation) of moKnowledgeBaseHandler. see clsProcessor.applyProperties. knowlegebasehandler is created twice!
			//moKnowledgeBaseHandler = clsKnowledgeBaseHandlerFactory.createInformationRepresentationManagement("ARSI10_MGMT", pre+P_INFORMATIONREPRESENTATIONMGMT, poProp);
		    String oFilename= poProp.getProperty(pre +P_PERSONALITY_PARAMETER);
		    String oDefaultFilename = poProp.getProperty(pre + P_PERSONALITY_PARAMETER_DEFAULT);
			moPersonalityParameterContainer= new clsPersonalityParameterContainer(clsGetARSPath.getDecisionUnitPeronalityParameterConfigPath(), Arrays.asList(oDefaultFilename, oFilename),P_DEFAULT_PERSONALITY_PARAMETER_DEFAULT_FILE_NAME);

            // init buffers
            moLibidoBuffer = new DT1_PsychicIntensityBuffer(moPersonalityParameterContainer);
            moBlockedContentStorage = new DT2_BlockedContentStorage();
            moPsychicEnergyStorage = new DT3_PsychicIntensityStorage(moPersonalityParameterContainer);
            moPleasureStorage = new DT4_PleasureStorage();

			moF01_SensorsMetabolism = new F01_SensorsMetabolism(pre + F01_SensorsMetabolism.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF02_NeurosymbolizationOfNeeds = new F02_NeurosymbolizationOfNeeds(pre + F02_NeurosymbolizationOfNeeds.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			//moF03_GenerationOfSelfPreservationDrives = new F03_GenerationOfSelfPreservationDrives(pre + F03_GenerationOfSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moPersonalityParameterContainer);
			//moF04_FusionOfSelfPreservationDrives = new F04_FusionOfSelfPreservationDrives(pre + F04_FusionOfSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPersonalityParameterContainer);
			moF06_DefenseMechanismsForDrives = new F06_DefenseMechanismsForDrives(pre + F06_DefenseMechanismsForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage, moPersonalityParameterContainer, uid);
			moF08_ConversionToSecondaryProcessForDriveWishes = new F08_ConversionToSecondaryProcessForDriveWishes(pre + F08_ConversionToSecondaryProcessForDriveWishes.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF10_SensorsEnvironment = new F10_SensorsEnvironment(pre + F10_SensorsEnvironment.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF11_NeuroSymbolizationEnvironment = new F11_NeuroSymbolizationEnvironment(pre + F11_NeuroSymbolizationEnvironment.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF12_SensorsBody = new F12_SensorsBody(pre + F12_SensorsBody.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF13_NeuroSymbolizationBody = new F13_NeuroSymbolizationBody(pre + F13_NeuroSymbolizationBody.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
            moF14_ExternalPerception = new F14_ExternalPerception(pre + F14_ExternalPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moPersonalityParameterContainer, uid); //koller
			moF18_CompositionOfAffectsForPerception = new F18_CompositionOfQuotaOfAffectsForPerception(pre + F18_CompositionOfQuotaOfAffectsForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF19_DefenseMechanismsForPerception = new F19_DefenseMechanismsForPerception(pre + F19_DefenseMechanismsForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage, moLongTermMemory, moPersonalityParameterContainer, uid);
			
			//moLongTermMemory is added
			moF20_CompositionOfFeelings = new F20_CompositionOfFeelings(pre + F20_CompositionOfFeelings.P_MODULENUMBER, poProp, moModules, moInterfaceData,moLongTermMemory, moPsychicEnergyStorage,moPersonalityParameterContainer, moShortTimeMemory, uid);
			//-------------------
			
			moF21_ConversionToSecondaryProcessForPerception = new F21_ConversionToSecondaryProcessForPerception(pre + F21_ConversionToSecondaryProcessForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moEnvironmentalImageStorage, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF23_ExternalPerception_focused = new F23_ExternalPerception_focused(pre + F23_ExternalPerception_focused.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF51_RealityCheckWishFulfillment = new F51_RealityCheckWishFulfillment(pre + F51_RealityCheckWishFulfillment.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moDecisionEngine, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF26_DecisionMaking = new F26_DecisionMaking(pre + F26_DecisionMaking.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moDecisionEngine, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF29_EvaluationOfImaginaryActions = new F29_EvaluationOfImaginaryActions(pre + F29_EvaluationOfImaginaryActions.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moDecisionEngine, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF30_MotilityControl = new F30_MotilityControl(pre + F30_MotilityControl.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF31_NeuroDeSymbolizationActionCommands = new F31_NeuroDeSymbolizationActionCommands(pre + F31_NeuroDeSymbolizationActionCommands.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF32_Actuators = new F32_Actuators(pre + F32_Actuators.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF53_RealityCheckActionPlanning = new F53_RealityCheckActionPlanning(pre + F53_RealityCheckActionPlanning.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF35_EmersionOfBlockedContent = new F35_EmersionOfBlockedContent(pre + F35_EmersionOfBlockedContent.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moBlockedContentStorage, uid);
			moF37_PrimalRepressionForPerception = new F37_PrimalRepressionForPerception(pre + F37_PrimalRepressionForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage, moPersonalityParameterContainer, uid);
			moF39_SeekingSystem_LibidoSource = new F39_SeekingSystem_LibidoSource(pre + F39_SeekingSystem_LibidoSource.P_MODULENUMBER, poProp, moModules, moInterfaceData,  moPersonalityParameterContainer, uid);
			moF40_NeurosymbolizationOfLibido = new F40_NeurosymbolizationOfLibido(pre + F40_NeurosymbolizationOfLibido.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF48_AccumulationOfQuotaOfAffectsForDrives = new F48_AccumulationOfQuotaOfAffectsForDrives(pre + F48_AccumulationOfQuotaOfAffectsForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPleasureStorage, moLibidoBuffer, moPersonalityParameterContainer, moPsychicEnergyStorage, uid);
			moF57_MemoryTracesForDrives = new F57_MemoryTracesForDrives(pre + F57_MemoryTracesForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moPersonalityParameterContainer, moPleasureStorage, uid);
			moF49_PrimalRepressionForDrives = new F49_PrimalRepressionForDrives(pre + F49_PrimalRepressionForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPersonalityParameterContainer, uid);
			moF54_EmersionOfBlockedDriveContent = new F54_EmersionOfBlockedDriveContent(pre + F54_EmersionOfBlockedDriveContent.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF56_Desexualization_Neutralization = new F56_Desexualization_Neutralization(pre + F56_Desexualization_Neutralization.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF55_SuperEgoProactive = new F55_SuperEgoProactive(pre + F55_SuperEgoProactive.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF07_SuperEgoReactive = new F07_SuperEgoReactive(pre + F07_SuperEgoReactive.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF52_GenerationOfImaginaryActions = new F52_GenerationOfImaginaryActions(pre + F52_GenerationOfImaginaryActions.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moShortTimeMemory, moEnvironmentalImageStorage, moDecisionEngine, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF45_LibidoDischarge = new F45_DischargeOfPsychicIntensity(pre + F45_DischargeOfPsychicIntensity.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer, moLongTermMemory, moPersonalityParameterContainer, uid);
			moF46_MemoryTracesForPerception = new F46_MemoryTracesForPerception(pre + F46_MemoryTracesForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLongTermMemory, moEnvironmentalImageStorage, moPersonalityParameterContainer, uid);
			moF47_ConversionToPrimaryProcess = new F47_ConversionToPrimaryProcess(pre + F47_ConversionToPrimaryProcess.P_MODULENUMBER, poProp, moModules, moInterfaceData, moShortTimeMemory, uid, moLongTermMemory);
			moF63_CompositionOfEmotions = new F63_CompositionOfEmotions(pre + F63_CompositionOfEmotions.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPleasureStorage, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF61_Localization = new F61_Localization(pre + F61_Localization.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPsychicEnergyStorage, moPersonalityParameterContainer, uid);
			moF64_PartialSexualDrives = new F64_PartialSexualDrives(pre + F64_PartialSexualDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer, moPersonalityParameterContainer, moPleasureStorage, uid);
			moF65_PartialSelfPreservationDrives = new F65_PartialSelfPreservationDrives(pre + F64_PartialSexualDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPersonalityParameterContainer, moLibidoBuffer, moPleasureStorage, uid);
			moF66_SpeechProduction = new F66_SpeechProduction(pre + F66_SpeechProduction.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPsychicEnergyStorage, moPersonalityParameterContainer, moConceptMemory, uid);
			moF67_BodilyReactionOnEmotions =  new F67_BodilyReactionsOnEmotions(pre + F67_BodilyReactionsOnEmotions.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
            moF71_CompositionOfExtendedEmotion = new F71_CompositionOfExtendedEmotion(pre + F71_CompositionOfExtendedEmotion.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPersonalityParameterContainer, moPsychicEnergyStorage, uid);
			
			moDebugLevel = poProp.getPropertyString(pre+P_DEBUG_LEVEL);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}	
	
	/**
	 * Fetch the list of all receive and send interfaces from the created functional modules.
	 *
	 * @since 13.07.2011 17:57:01
	 *
	 */
	private void fillInterfaces_Recv_Send() {
		moInterfaces_Recv_Send = new HashMap<eInterfaces, clsPair<ArrayList<Integer>,ArrayList<Integer>>>();
		for (eInterfaces eI:eInterfaces.values()) {
			moInterfaces_Recv_Send.put(eI, new clsPair<ArrayList<Integer>, ArrayList<Integer>>(new ArrayList<Integer>(), new ArrayList<Integer>()));
		}
		for (Map.Entry<Integer, clsModuleBase>  e:moModules.entrySet()) {
			Integer oKey = e.getKey();
			clsModuleBase oMod = e.getValue();
			
			for (eInterfaces eI:oMod.getInterfacesRecv()) {
				clsPair<ArrayList<Integer>,ArrayList<Integer>> oP = moInterfaces_Recv_Send.get(eI);
				oP.a.add(oKey);
			}
			
			for (eInterfaces eI:oMod.getInterfacesSend()) {
				clsPair<ArrayList<Integer>,ArrayList<Integer>> oP = moInterfaces_Recv_Send.get(eI);
				oP.b.add(oKey);
			}			
		}
	}
	
	/**
	 * Extract the mesh - the modules and how they are connected by the various interfaces - from the send and receive interfaces 
	 * information retrieved by method fillInterfaces_Recv_Send.
	 *
	 * @since 13.07.2011 17:57:03
	 *
	 */
	private void fillInterfaceMesh() {
		moInterfaceMesh = new HashMap<Integer, ArrayList<clsPair<eInterfaces,Integer>>>();
		
		HashMap<eInterfaces, ArrayList<Integer>> moTargetList = new HashMap<eInterfaces, ArrayList<Integer>>();
		//create a complete list of all interfaces. each entry has a corresponding empty list of target modules. 
		//thus an entry consists of a single interface and a list of modules it points at
		for (eInterfaces eI:eInterfaces.values()) {
			moTargetList.put(eI, new ArrayList<Integer>());
		}
		
		//fill the empty list of target modules
		for (Map.Entry<Integer, clsModuleBase>  e:moModules.entrySet()) {
			clsModuleBase oMod = e.getValue();
			Integer oKey = e.getKey();
			
			for (eInterfaces eI: oMod.getInterfacesRecv()) {
				ArrayList<Integer> moTargets = moTargetList.get(eI);
				moTargets.add(oKey);
			}
		}
		
		//now we use the created list of target modules to create a list of connection pairs. a pair consists of the interface
		//and the target. this target list is stored for each module
		for (Map.Entry<Integer, clsModuleBase>  e:moModules.entrySet()) {
			clsModuleBase oMod = e.getValue();
			Integer oKey = e.getKey();
			
			ArrayList<clsPair<eInterfaces,Integer>> oList = new ArrayList<clsPair<eInterfaces,Integer>>();
			for (eInterfaces eI:oMod.getInterfacesSend()) {
				for (Integer oTarget:moTargetList.get(eI)) {
					clsPair<eInterfaces,Integer> oPair = new clsPair<eInterfaces, Integer>(eI, oTarget);
					oList.add(oPair);
				}
			}
			
			moInterfaceMesh.put(oKey, oList); 
		}
	}
}
