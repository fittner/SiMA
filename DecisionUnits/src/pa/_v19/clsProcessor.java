/**
 * clsProcessor.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 */
package pa._v19;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSensorExtern;
import pa.itfProcessor;
import pa.modules._v19.E01_Homeostases;
import pa.modules._v19.E02_NeurosymbolizationOfNeeds;
import pa.modules._v19.E03_GenerationOfDrives;
import pa.modules._v19.E04_FusionOfDrives;
import pa.modules._v19.E05_GenerationOfAffectsForDrives;
import pa.modules._v19.E06_DefenseMechanismsForDriveContents;
import pa.modules._v19.E07_SuperEgo_unconscious;
import pa.modules._v19.E08_ConversionToSecondaryProcess;
import pa.modules._v19.E09_KnowledgeAboutReality_unconscious;
import pa.modules._v19.E10_SensorsEnvironment;
import pa.modules._v19.E11_NeuroSymbolsEnvironment;
import pa.modules._v19.E12_SensorsBody;
import pa.modules._v19.E13_NeuroSymbolsBody;
import pa.modules._v19.E14_PreliminaryExternalPerception;
import pa.modules._v19.E16_ManagementOfMemoryTraces;
import pa.modules._v19.E17_FusionOfExternalPerceptionAndMemoryTraces;
import pa.modules._v19.E18_GenerationOfAffectsForPerception;
import pa.modules._v19.E19_DefenseMechanismsForPerception;
import pa.modules._v19.E20_InnerPerception_Affects;
import pa.modules._v19.E21_ConversionToSecondaryProcess;
import pa.modules._v19.E22_SuperEgo_preconscious;
import pa.modules._v19.E23_ExternalPerception_focused;
import pa.modules._v19.E24_RealityCheck;
import pa.modules._v19.E25_KnowledgeAboutReality;
import pa.modules._v19.E26_DecisionMaking;
import pa.modules._v19.E27_GenerationOfImaginaryActions;
import pa.modules._v19.E28_KnowledgeBase_StoredScenarios;
import pa.modules._v19.E29_EvaluationOfImaginaryActions;
import pa.modules._v19.E30_MotilityControl;
import pa.modules._v19.E31_NeuroDeSymbolization;
import pa.modules._v19.E32_Actuators;
import pa.modules._v19.E33_RealityCheck2;
import pa.modules._v19.E34_KnowledgeAboutReality2;
import pa.modules._v19.G00_PsychicApparatus;
import pa.modules._v19.S_ManagementOfRepressedContents_1;
import pa.modules._v19.S_ManagementOfRepressedContents_2;
import pa.modules._v19.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 * 
 */
public class clsProcessor implements itfProcessor {
	public static final String P_PSYCHICAPPARATUS = "psychicapparatus";
	
	private G00_PsychicApparatus moPsychicApparatus;
	
	private ArrayList<clsModuleBase> moModules;
	private clsInterfaceHandler moInterfaceHandler;
	
	private E01_Homeostases moE01Homeostases;
	private E02_NeurosymbolizationOfNeeds moE02NeurosymbolizationOfNeeds;
	private E03_GenerationOfDrives moE03GenerationOfDrives;
	private E04_FusionOfDrives moE04FusionOfDrives;
	private E05_GenerationOfAffectsForDrives moE05GenerationOfAffectsForDrives;
	private E06_DefenseMechanismsForDriveContents moE06DefenseMechanismsForDriveContents;
	private E07_SuperEgo_unconscious moE07SuperEgoUnconscious;
	private E08_ConversionToSecondaryProcess moE08ConversionToSecondaryProcess;
	private E09_KnowledgeAboutReality_unconscious moE09KnowledgeAboutRealityUnconscious;
	private E10_SensorsEnvironment moE10SensorsEnvironment;
	private E11_NeuroSymbolsEnvironment moE11NeuroSymbolsEnvironment;
	private E12_SensorsBody moE12SensorsBody;
	private E13_NeuroSymbolsBody moE13NeuroSymbolsBody;
	private E14_PreliminaryExternalPerception moE14PreliminaryExternalPerception;
	private S_ManagementOfRepressedContents_1 moE15_1_ManagementOfRepressedContents;
	private S_ManagementOfRepressedContents_2 moE15_2_ManagementOfRepressedContents;	
	private E16_ManagementOfMemoryTraces moE16ManagementOfMemoryTraces;
	private E17_FusionOfExternalPerceptionAndMemoryTraces moE17FusionOfExternalPerceptionAndMemoryTraces;
	private E18_GenerationOfAffectsForPerception moE18GenerationOfAffectsForPerception;
	private E19_DefenseMechanismsForPerception moE19DefenseMechanismsForPerception;
	private E20_InnerPerception_Affects moE20InnerPerceptionAffects;
	private E21_ConversionToSecondaryProcess moE21ConversionToSecondaryProcess;
	private E22_SuperEgo_preconscious moE22SuperEgoPreconscious;
	private E23_ExternalPerception_focused moE23ExternalPerceptionFocused;
	private E24_RealityCheck moE24RealityCheck;
	private E25_KnowledgeAboutReality moE25KnowledgeAboutReality;
	private E26_DecisionMaking moE26DecisionMaking;
	private E27_GenerationOfImaginaryActions moE27GenerationOfImaginaryActions;
	private E28_KnowledgeBase_StoredScenarios moE28KnowledgeBaseStoredScenarios;
	private E29_EvaluationOfImaginaryActions moE29EvaluationOfImaginaryActions;
	private E30_MotilityControl moE30MotilityControl;
	private E31_NeuroDeSymbolization moE31NeuroDeSymbolization;
	private E32_Actuators moE32Actuators;
	private E33_RealityCheck2 moE33RealityCheck2;
	private E34_KnowledgeAboutReality2 moE34KnowledgeAboutReality2;
	
	public clsProcessor(String poPrefix, clsBWProperties poProp) {
		moModules = new ArrayList<clsModuleBase>();
		moInterfaceHandler = new clsInterfaceHandler();

		applyProperties(poPrefix, poProp);
		
		assignModules();
		moInterfaceHandler.setModules(moModules);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( G00_PsychicApparatus.getDefaultProperties(pre+P_PSYCHICAPPARATUS) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moPsychicApparatus = new G00_PsychicApparatus(pre+P_PSYCHICAPPARATUS, poProp, moInterfaceHandler);

	}
	
	private void assignModules() {
		moE01Homeostases = moPsychicApparatus.moG01Body.moE01Homeostases;
		moModules.add(moE01Homeostases);
		moE02NeurosymbolizationOfNeeds = moPsychicApparatus.moG01Body.moE02NeurosymbolizationOfNeeds;
		moModules.add(moE02NeurosymbolizationOfNeeds);		
		moE03GenerationOfDrives = moPsychicApparatus.moG02Id.moG05DriveHandling.moE03GenerationOfDrives;
		moModules.add(moE03GenerationOfDrives);		
		moE04FusionOfDrives = moPsychicApparatus.moG02Id.moG05DriveHandling.moE04FusionOfDrives;
		moModules.add(moE04FusionOfDrives);	
		moE05GenerationOfAffectsForDrives = moPsychicApparatus.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives;
		moModules.add(moE05GenerationOfAffectsForDrives);
		moE06DefenseMechanismsForDriveContents = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG12PrimaryDecision.moE06DefenseMechanismsForDriveContents;
		moModules.add(moE06DefenseMechanismsForDriveContents);
		moE07SuperEgoUnconscious = moPsychicApparatus.moG04SuperEgo.moE07SuperEgoUnconscious;
		moModules.add(moE07SuperEgoUnconscious);
		moE08ConversionToSecondaryProcess = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG10PrimaryToSecondaryInterface1.moE08ConversionToSecondaryProcess;
		moModules.add(moE08ConversionToSecondaryProcess);
		moE09KnowledgeAboutRealityUnconscious = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE09KnowledgeAboutReality_unconscious;
		moModules.add(moE09KnowledgeAboutRealityUnconscious);
		moE10SensorsEnvironment = moPsychicApparatus.moG01Body.moE10SensorsEnvironment;
		moModules.add(moE10SensorsEnvironment);
		moE11NeuroSymbolsEnvironment = moPsychicApparatus.moG01Body.moE11NeuroSymbolsEnvironment;
		moModules.add(moE11NeuroSymbolsEnvironment);
		moE12SensorsBody = moPsychicApparatus.moG01Body.moE12SensorsBody;
		moModules.add(moE12SensorsBody);
		moE13NeuroSymbolsBody = moPsychicApparatus.moG01Body.moE13NeuroSymbolsBody;
		moModules.add(moE13NeuroSymbolsBody);
		moE14PreliminaryExternalPerception = moPsychicApparatus.moG03Ego.moG07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception;
		moModules.add(moE14PreliminaryExternalPerception);
		moE15_1_ManagementOfRepressedContents = moPsychicApparatus.moG02Id.moE15ManagementOfRepressedContents.moS_ManagementOfRepressedContents_1;
		moModules.add(moE15_1_ManagementOfRepressedContents);
		moE15_2_ManagementOfRepressedContents = moPsychicApparatus.moG02Id.moE15ManagementOfRepressedContents.moS_ManagementOfRepressedContents_2;
		moModules.add(moE15_2_ManagementOfRepressedContents);
		moE16ManagementOfMemoryTraces = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces;
		moModules.add(moE16ManagementOfMemoryTraces);
		moE17FusionOfExternalPerceptionAndMemoryTraces = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moE17FusionOfExternalPerceptionAndMemoryTraces;
		moModules.add(moE17FusionOfExternalPerceptionAndMemoryTraces);
		moE18GenerationOfAffectsForPerception = moPsychicApparatus.moG02Id.moG06AffectGeneration.moE18GenerationOfAffectsForPerception;
		moModules.add(moE18GenerationOfAffectsForPerception);
		moE19DefenseMechanismsForPerception = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG12PrimaryDecision.moE19DefenseMechanismsForPerception;
		moModules.add(moE19DefenseMechanismsForPerception);
		moE20InnerPerceptionAffects = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG10PrimaryToSecondaryInterface1.moE20InnerPerception_Affects;
		moModules.add(moE20InnerPerceptionAffects);
		moE21ConversionToSecondaryProcess = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG10PrimaryToSecondaryInterface1.moE21ConversionToSecondaryProcess;
		moModules.add(moE21ConversionToSecondaryProcess);
		moE22SuperEgoPreconscious = moPsychicApparatus.moG04SuperEgo.moE22SuperEgoPreconscious;
		moModules.add(moE22SuperEgoPreconscious);
		moE23ExternalPerceptionFocused = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG14PerceptualPreprocessing.moE23ExternalPerception_focused;
		moModules.add(moE23ExternalPerceptionFocused);
		moE24RealityCheck = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG14PerceptualPreprocessing.moE24RealityCheck;
		moModules.add(moE24RealityCheck);
		moE25KnowledgeAboutReality = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG16SecondaryKnowledgeUtilizer.moE25KnowledgeAboutReality;
		moModules.add(moE25KnowledgeAboutReality);
		moE26DecisionMaking = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE26DecisionMaking;
		moModules.add(moE26DecisionMaking);
		moE27GenerationOfImaginaryActions = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE27GenerationOfImaginaryActions;
		moModules.add(moE27GenerationOfImaginaryActions);
		moE28KnowledgeBaseStoredScenarios = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG16SecondaryKnowledgeUtilizer.moE28KnowledgeBase_StoredScenarios;
		moModules.add(moE28KnowledgeBaseStoredScenarios);
		moE29EvaluationOfImaginaryActions = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE29EvaluationOfImaginaryActions;
		moModules.add(moE29EvaluationOfImaginaryActions);
		moE30MotilityControl = moPsychicApparatus.moG03Ego.moG07EnvironmentalInterfaceFunctions.moE30MotilityControl;
		moModules.add(moE30MotilityControl);
		moE31NeuroDeSymbolization = moPsychicApparatus.moG01Body.moE31NeuroDeSymbolization;
		moModules.add(moE31NeuroDeSymbolization);
		moE32Actuators = moPsychicApparatus.moG01Body.moE32Actuators;
		moModules.add(moE32Actuators);
		moE33RealityCheck2 = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE33RealityCheck2;
		moModules.add(moE33RealityCheck2);
		moE34KnowledgeAboutReality2 = moPsychicApparatus.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG16SecondaryKnowledgeUtilizer.moE34KnowledgeAboutReality2;
		moModules.add(moE34KnowledgeAboutReality2);
	}
	
	@Override
	public void applySensorData(clsSensorData poData) {
		moPsychicApparatus.receiveBody( separateBodyData(poData) );
		moPsychicApparatus.receiveEnvironment( separateEnvironmentalData(poData) );
		moPsychicApparatus.receiveHomeostases( separateHomeostaticData(poData) );		
	}

	/**
	 * collects homeostatic value only
	 *
	 * @author langr
	 * 12.08.2009, 20:42:17
	 *
	 * @param poData
	 * @return
	 */
	private HashMap<eSensorIntType, clsDataBase> separateHomeostaticData(clsSensorData poData) {
		HashMap<eSensorIntType, clsDataBase> oResult = new HashMap<eSensorIntType, clsDataBase>();
		
		oResult.put(eSensorIntType.ENERGY, poData.getSensorInt(eSensorIntType.ENERGY));
		oResult.put(eSensorIntType.ENERGY_CONSUMPTION, poData.getSensorInt(eSensorIntType.ENERGY_CONSUMPTION));
		oResult.put(eSensorIntType.HEALTH, poData.getSensorInt(eSensorIntType.HEALTH));
		oResult.put(eSensorIntType.STAMINA, poData.getSensorInt(eSensorIntType.STAMINA));
		oResult.put(eSensorIntType.STOMACH, poData.getSensorInt(eSensorIntType.STOMACH));
		oResult.put(eSensorIntType.STOMACHTENSION, poData.getSensorInt(eSensorIntType.STOMACHTENSION));
		oResult.put(eSensorIntType.TEMPERATURE, poData.getSensorInt(eSensorIntType.TEMPERATURE));
		oResult.put(eSensorIntType.FASTMESSENGER, poData.getSensorInt(eSensorIntType.FASTMESSENGER));
		oResult.put(eSensorIntType.SLOWMESSENGER, poData.getSensorInt(eSensorIntType.SLOWMESSENGER));
		
		
		return oResult;
	}

	/**
	 * collects environmental data only
	 *
	 * @author langr
	 * 12.08.2009, 20:41:51
	 *
	 * @param poData
	 * @return
	 */
	private HashMap<eSensorExtType, clsSensorExtern> separateEnvironmentalData(clsSensorData poData) {
		HashMap<eSensorExtType, clsSensorExtern> oResult = new HashMap<eSensorExtType, clsSensorExtern>();
//      |
		
		//collect environmental data only
		oResult.put(eSensorExtType.ACCELERATION, poData.getSensorExt(eSensorExtType.ACCELERATION));
		oResult.put(eSensorExtType.ACOUSTIC, poData.getSensorExt(eSensorExtType.ACOUSTIC));
		oResult.put(eSensorExtType.BUMP, poData.getSensorExt(eSensorExtType.BUMP));
		oResult.put(eSensorExtType.EATABLE_AREA, poData.getSensorExt(eSensorExtType.EATABLE_AREA));
		oResult.put(eSensorExtType.MANIPULATE_AREA, poData.getSensorExt(eSensorExtType.MANIPULATE_AREA));
		oResult.put(eSensorExtType.OLFACTORIC, poData.getSensorExt(eSensorExtType.OLFACTORIC));
		oResult.put(eSensorExtType.TACTILE, poData.getSensorExt(eSensorExtType.TACTILE));
		oResult.put(eSensorExtType.VISION_NEAR, poData.getSensorExt(eSensorExtType.VISION_NEAR));
		oResult.put(eSensorExtType.VISION_MEDIUM, poData.getSensorExt(eSensorExtType.VISION_MEDIUM));
		oResult.put(eSensorExtType.VISION_FAR, poData.getSensorExt(eSensorExtType.VISION_FAR));
		oResult.put(eSensorExtType.POSITIONCHANGE, poData.getSensorExt(eSensorExtType.POSITIONCHANGE));
		oResult.put(eSensorExtType.RADIATION, poData.getSensorExt(eSensorExtType.RADIATION));
		
		return oResult;
	}

	/**
	 * collects bodily data only
	 *
	 * @author langr
	 * 12.08.2009, 20:41:48
	 *
	 * @param poData
	 * @return
	 */
	private HashMap<eSensorExtType, clsSensorExtern> separateBodyData(clsSensorData poData) {
		HashMap<eSensorExtType, clsSensorExtern> oResult = new HashMap<eSensorExtType, clsSensorExtern>();
		
//		//TODO: (all) collect (but first generate) bodily data only
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));
//		oResult.put(eSensorExtType., poData.eSensorExtType(eSensorIntType.));
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));
//		oResult.put(eSensorExtType., poData.getSensorInt(eSensorExtType.));

		return oResult;
	}

	@Override
	public void getActionCommands(itfActionProcessor poActionContainer) {
		moPsychicApparatus.getActionCommands(poActionContainer);
	}
	
	@Override
	public void step() {
		//BODY --------------------------------------------- 
		//data preprocessing
		moE01Homeostases.step();
		moE02NeurosymbolizationOfNeeds.step();

		moE10SensorsEnvironment.step();
		moE11NeuroSymbolsEnvironment.step();
		
		moE12SensorsBody.step();
		moE13NeuroSymbolsBody.step();

		//PRIMARY PROCESSES -------------------------------
		//Drive generation
		moE03GenerationOfDrives.step();
		moE04FusionOfDrives.step();
		moE05GenerationOfAffectsForDrives.step();
		moE09KnowledgeAboutRealityUnconscious.step();
		
		//perception to memory and repression
		moE14PreliminaryExternalPerception.step();
		moE15_1_ManagementOfRepressedContents.step();
		moE15_2_ManagementOfRepressedContents.step();
		moE16ManagementOfMemoryTraces.step();
		moE17FusionOfExternalPerceptionAndMemoryTraces.step();
		moE18GenerationOfAffectsForPerception.step();

		//super-ego
		moE07SuperEgoUnconscious.step();

		//defense mechanisms
		moE06DefenseMechanismsForDriveContents.step();
		moE19DefenseMechanismsForPerception.step();

		//primary to secondary
		moE08ConversionToSecondaryProcess.step();
		moE21ConversionToSecondaryProcess.step();
		moE20InnerPerceptionAffects.step();

		//SECONDARY PROCESSES ----------------------------
		//super-ego
		moE22SuperEgoPreconscious.step();

		//external perception
		moE23ExternalPerceptionFocused.step();
		moE25KnowledgeAboutReality.step();
		moE24RealityCheck.step();
		
		//decision making
		moE26DecisionMaking.step();
		moE28KnowledgeBaseStoredScenarios.step();
		moE27GenerationOfImaginaryActions.step();
		
		//evaluation and pre-execution
		moE34KnowledgeAboutReality2.step();
		moE33RealityCheck2.step();
		moE29EvaluationOfImaginaryActions.step();
		moE30MotilityControl.step();
		
		//BODY --------------------------------------------- 
		//execution
		moE31NeuroDeSymbolization.step();
		moE32Actuators.step();
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 13.08.2009, 00:08:10
	 *
	 * @return
	 */
	public G00_PsychicApparatus getPsychicApparatus() {
		return moPsychicApparatus;
	}
}