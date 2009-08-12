/**
 * clsProcessor.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 */
package pa;

import memory.tempframework.clsActionContainer;
import config.clsBWProperties;
import decisionunit.itf.sensors.clsSensorData;
import pa.modules.C00_PsychicApparatus;
import pa.modules.E01_Homeostases;
import pa.modules.E02_NeurosymbolizationOfNeeds;
import pa.modules.E03_GenerationOfDrives;
import pa.modules.E04_FusionOfDrives;
import pa.modules.E05_GenerationOfAffectsForDrives;
import pa.modules.E06_DefenseMechanismsForDriveContents;
import pa.modules.E07_SuperEgo_unconscious;
import pa.modules.E08_ConversionToSecondaryProcess;
import pa.modules.E09_KnowledgeAboutReality_unconscious;
import pa.modules.E10_SensorsEnvironment;
import pa.modules.E11_NeuroSymbolsEnvironment;
import pa.modules.E12_SensorsBody;
import pa.modules.E13_NeuroSymbolsBody;
import pa.modules.E14_PreliminaryExternalPerception;
import pa.modules.E15_ManagementOfRepressedContents;
import pa.modules.E16_ManagmentOfMemoryTraces;
import pa.modules.E17_FusionOfExternalPerceptionAndMemoryTraces;
import pa.modules.E18_GenerationOfAffectsForPerception;
import pa.modules.E19_DefenseMechanismsForPerception;
import pa.modules.E20_InnerPerception_Affects;
import pa.modules.E21_ConversionToSecondaryProcess;
import pa.modules.E22_SuperEgo_preconscious;
import pa.modules.E23_ExternalPerception_focused;
import pa.modules.E24_RealityCheck;
import pa.modules.E25_KnowledgeAboutReality;
import pa.modules.E26_DecisionMaking;
import pa.modules.E27_GenerationOfImaginaryActions;
import pa.modules.E28_KnowledgeBase_StoredScenarios;
import pa.modules.E29_EvaluationOfImaginaryActions;
import pa.modules.E30_MotilityControl;
import pa.modules.E31_NeuroDeSymbolization;
import pa.modules.E32_Actuators;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 * 
 */
public class clsProcessor {
	public static final String P_PSYCHICAPPARATUS = "psychicapparatus";
	
	private C00_PsychicApparatus moPsychicApparatus;
	
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
	private E15_ManagementOfRepressedContents moE15ManagementOfRepressedContents;
	private E16_ManagmentOfMemoryTraces moE16ManagmentOfMemoryTraces;
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
	
	public clsProcessor(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
		assignModules();
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C00_PsychicApparatus.getDefaultProperties(pre+P_PSYCHICAPPARATUS) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moPsychicApparatus = new C00_PsychicApparatus(pre+P_PSYCHICAPPARATUS, poProp);

	}
	
	private void assignModules() {
		moE01Homeostases = moPsychicApparatus.moC01Body.moE01Homeostases;
		moE02NeurosymbolizationOfNeeds = moPsychicApparatus.moC01Body.moE02NeurosymbolizationOfNeeds;
		moE03GenerationOfDrives = moPsychicApparatus.moC02Id.moC05DriveHandling.moE03GenerationOfDrives;
		moE04FusionOfDrives = moPsychicApparatus.moC02Id.moC05DriveHandling.moE04FusionOfDrives;
		moE05GenerationOfAffectsForDrives = moPsychicApparatus.moC02Id.moC06AffectGeneration.moE05GenerationOfAffectsForDrives;
		moE06DefenseMechanismsForDriveContents = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC09PrimaryProcessor.moC13PrimaryDecision.moE06DefenseMechanismsForDriveContents;
		moE07SuperEgoUnconscious = moPsychicApparatus.moC04SuperEgo.moE07SuperEgoUnconscious;
		moE08ConversionToSecondaryProcess = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC11PrimaryToSecondaryInterface1.moE08ConversionToSecondaryProcess;
		moE09KnowledgeAboutRealityUnconscious = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC09PrimaryProcessor.moC14PrimaryKnowledgeUtilizer.moE09KnowledgeAboutReality_unconscious;
		moE10SensorsEnvironment = moPsychicApparatus.moC01Body.moE10SensorsEnvironment;
		moE11NeuroSymbolsEnvironment = moPsychicApparatus.moC01Body.moE11NeuroSymbolsEnvironment;
		moE12SensorsBody = moPsychicApparatus.moC01Body.moE12SensorsBody;
		moE13NeuroSymbolsBody = moPsychicApparatus.moC01Body.moE13NeuroSymbolsBody;
		moE14PreliminaryExternalPerception = moPsychicApparatus.moC03Ego.moC07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception;
		moE15ManagementOfRepressedContents = moPsychicApparatus.moC02Id.moE15ManagementOfRepressedContents;
		moE16ManagmentOfMemoryTraces = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC09PrimaryProcessor.moC14PrimaryKnowledgeUtilizer.moE16ManagmentOfMemoryTraces;
		moE17FusionOfExternalPerceptionAndMemoryTraces = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC09PrimaryProcessor.moE17FusionOfExternalPerceptionAndMemoryTraces;
		moE18GenerationOfAffectsForPerception = moPsychicApparatus.moC02Id.moC06AffectGeneration.moE18GenerationOfAffectsForPerception;
		moE19DefenseMechanismsForPerception = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC09PrimaryProcessor.moC13PrimaryDecision.moE19DefenseMechanismsForPerception;
		moE20InnerPerceptionAffects = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC11PrimaryToSecondaryInterface1.moE20InnerPerception_Affects;
		moE21ConversionToSecondaryProcess = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC11PrimaryToSecondaryInterface1.moE21ConversionToSecondaryProcess;
		moE22SuperEgoPreconscious = moPsychicApparatus.moC04SuperEgo.moE22SuperEgoPreconscious;
		moE23ExternalPerceptionFocused = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC15PerceptualPreprocessing.moE23ExternalPerception_focused;
		moE24RealityCheck = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC15PerceptualPreprocessing.moE24RealityCheck;
		moE25KnowledgeAboutReality = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC17SecondaryKnowledgeUtilizer.moE25KnowledgeAboutReality;
		moE26DecisionMaking = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC16Deliberation.moE26DecisionMaking;
		moE27GenerationOfImaginaryActions = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC16Deliberation.moE27GenerationOfImaginaryActions;
		moE28KnowledgeBaseStoredScenarios = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC17SecondaryKnowledgeUtilizer.moE28KnowledgeBase_StoredScenarios;
		moE29EvaluationOfImaginaryActions = moPsychicApparatus.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC16Deliberation.moE29EvaluationOfImaginaryActions;
		moE30MotilityControl = moPsychicApparatus.moC03Ego.moC07EnvironmentalInterfaceFunctions.moE30MotilityControl;
		moE31NeuroDeSymbolization = moPsychicApparatus.moC01Body.moE31NeuroDeSymbolization;
		moE32Actuators = moPsychicApparatus.moC01Body.moE32Actuators;
	}
	
	public void applySensorData(clsSensorData poData) {
		moPsychicApparatus.receiveBody(poData);
		moPsychicApparatus.receiveEnvironment(poData);
		moPsychicApparatus.receiveHomeostases(poData);		
	}

	public clsActionContainer getActionCommands(clsActionContainer poActionContainer) {
		return moPsychicApparatus.getActionCommands(poActionContainer);
	}
	
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
		moE15ManagementOfRepressedContents.step();
		moE16ManagmentOfMemoryTraces.step();
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
		moE29EvaluationOfImaginaryActions.step();
		moE30MotilityControl.step();
		
		//BODY --------------------------------------------- 
		//execution
		moE31NeuroDeSymbolization.step();
		moE32Actuators.step();
	}
}