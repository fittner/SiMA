/**
 * clsInspectorMappingPA.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 */
package inspectors.mind.pa._v30;

import javax.swing.JTree;

import inspectors.mind.pa._v30.functionalmodel.clsPAInspectorFunctional;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces._v30.eInterfaces;
import pa.loader.plan.clsPlanBaseMesh;
import pa.loader.scenario.clsScenarioBaseMesh;
import pa.modules._v30.clsPsychicApparatus;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 * 
 */
public class clsInspectorMappingPA {

	/**
	 * In the valueChanged() method of a inspector panel this function is called and creates a inspector (which is a JPanel in the long term) 
	 * according to the pressed tree or button or whatever 'poModulName' is given. PLEASE reuse Inspectors! as they only tell how to show some 
	 * Information sometimes. So you can fill them with different Informations to show! cm
	 *
	 * @author langr
	 * 13.08.2009, 01:32:58
	 *
	 * @param moPA
	 * @param poModuleName
	 */
	public static TabbedInspector getPAInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, 
													clsPsychicApparatus moPA, String poModuleName, JTree poTree) {
		TabbedInspector oRetVal = new TabbedInspector();
		
		if(poModuleName.equals("E01_SensorsMetabolism")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE01_SensorsMetabolism), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE01_SensorsMetabolism), "Desc");
		} else if(poModuleName.equals("E02_NeurosymbolizationOfNeeds")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE02_NeurosymbolizationOfNeeds), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE02_NeurosymbolizationOfNeeds), "Desc");
			oRetVal.addInspector( new clsE02InspectorInput(poSuperInspector, poWrapper, poState, moPA.moE02_NeurosymbolizationOfNeeds), "Input");
			oRetVal.addInspector( new clsE02InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moE02_NeurosymbolizationOfNeeds), "Output (same as Input)");
		} else if(poModuleName.equals("E03_GenerationOfSelfPreservationDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE03_GenerationOfSelfPreservationDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE03_GenerationOfSelfPreservationDrives), "Desc");
//			oRetVal.addInspector( new clsE03InspectorInput(poSuperInspector, poWrapper, poState, moPA.moE03_GenerationOfSelfPreservationDrives), "Input");
			oRetVal.addInspector( new clsE03InspectorDriveDefinitions(poSuperInspector, poWrapper, poState, moPA.moE03_GenerationOfSelfPreservationDrives), "Drive Definitions");
//			oRetVal.addInspector( new clsE03InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moE03_GenerationOfSelfPreservationDrives), "Output");
		} else if(poModuleName.equals("E04_FusionOfSelfPreservationDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE04_FusionOfSelfPreservationDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE04_FusionOfSelfPreservationDrives), "Desc");
		} else if(poModuleName.equals("E05_AccumulationOfAffectsForSelfPreservationDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE05_AccumulationOfAffectsForSelfPreservationDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE05_AccumulationOfAffectsForSelfPreservationDrives), "Desc");
//			oRetVal.addInspector( new clsE05InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moE05_AccumulationOfAffectsForSelfPreservationDrives), "Current Drives");
			oRetVal.addInspector( new clsE05DriveInspector(poSuperInspector, poWrapper, poState, moPA.moE05_AccumulationOfAffectsForSelfPreservationDrives, "moDriveList"), "Current Drives (Graph)");
			oRetVal.addInspector(new clsE_ChartInspector(poSuperInspector, poWrapper, poState, moPA.moE05_AccumulationOfAffectsForSelfPreservationDrives, "Quota of Affects", -0.05, 1.05, "Drive-Affect Chart"),	"Drive-Affect Chart");	
			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA, eInterfaces.I1_4 ), "rcv I1_4");
		} else if(poModuleName.equals("E06_DefenseMechanismsForDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE06_DefenseMechanismsForDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE06_DefenseMechanismsForDrives), "Desc");
		} else if(poModuleName.equals("E07_InternalizedRulesHandler")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE07_InternalizedRulesHandler), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE07_InternalizedRulesHandler), "Desc");
		} else if(poModuleName.equals("E08_ConversionToSecondaryProcessForDriveWishes")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE08_ConversionToSecondaryProcessForDriveWishes), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE08_ConversionToSecondaryProcessForDriveWishes), "Desc");
		} else if(poModuleName.equals("E09_KnowledgeAboutReality_unconscious")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE09_KnowledgeAboutReality_unconscious), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE09_KnowledgeAboutReality_unconscious), "Desc");
		} else if(poModuleName.equals("E10_SensorsEnvironment")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE10_SensorsEnvironment), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE10_SensorsEnvironment), "Desc");
		} else if(poModuleName.equals("E11_NeuroSymbolizationEnvironment")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE11_NeuroSymbolizationEnvironment), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE11_NeuroSymbolizationEnvironment), "Desc");
		} else if(poModuleName.equals("E12_SensorsBody")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE12_SensorsBody), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE12_SensorsBody), "Desc");
		} else if(poModuleName.equals("E13_NeuroSymbolizationBody")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE13_NeuroSymbolizationBody), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE13_NeuroSymbolizationBody), "Desc");
		} else if(poModuleName.equals("E14_ExternalPerception")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE14_ExternalPerception), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE14_ExternalPerception), "Desc");
			//@CLEMENS:NullpointerException			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moE14_ExternalPerception, "moEnvironmentalTP_old", false ), "Generated Thing Presentations");
		}
//		else if(poModuleName.equals("E15_1_ManagementOfRepressedContents")) {
//			//oRetVal.addInspector( new clsPrimaryInformationPairInspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moE15ManagementOfRepressedContents.moS_ManagementOfRepressedContents_1, "moAttachedRepressed_Output_old" ), "Output: TP + Attached Repressed TP");
//			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moRepressedContentsStore, "moRepressedContent", false ), "Repressed Content_old");
//		}
//		else if(poModuleName.equals("E16ManagementOfMemoryTraces")) {
//			//oRetVal.addInspector( new clsPrimaryInformationPairInspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces, "moPerceptPlusRepressed_Input_old" ), "Input: TP + Attached Repressed TP");
//		}
//		else if(poModuleName.equals("E17FusionOfExternalPerceptionAndMemoryTraces")) {
//			// FIXME HZ
//			//oRetVal.addInspector( new clsTPMeshListInspector(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception, "moEnvironmentalTP" ), "Generated Thing Presentations");
//		}		
		else if(poModuleName.equals("E18_CompositionOfAffectsForPerception")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE18_CompositionOfAffectsForPerception), "State");			
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE18_CompositionOfAffectsForPerception), "Desc");
//@CLEMENS:NullpointerException			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moE18_CompositionOfAffectsForPerception, "moNewPrimaryInformation_old", false ), "Output: Final TP+Affect");
		} else if(poModuleName.equals("E19_DefenseMechanismsForPerception")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE19_DefenseMechanismsForPerception), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE19_DefenseMechanismsForPerception), "Desc");
		} else if(poModuleName.equals("E20_InnerPerception_Affects")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE20_InnerPerception_Affects), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE20_InnerPerception_Affects), "Desc");
		} else if(poModuleName.equals("E21_ConversionToSecondaryProcessForPerception")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE21_ConversionToSecondaryProcessForPerception), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE21_ConversionToSecondaryProcessForPerception), "Desc");
		} else if(poModuleName.equals("E22_SocialRulesSelection")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE22_SocialRulesSelection), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE22_SocialRulesSelection), "Desc");
		} else if(poModuleName.equals("E23_ExternalPerception_focused")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE23_ExternalPerception_focused), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE23_ExternalPerception_focused), "Desc");
		} else if(poModuleName.equals("E24_RealityCheck_1")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE24_RealityCheck_1), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE24_RealityCheck_1), "Desc");
		} else if(poModuleName.equals("E25_KnowledgeAboutReality_1")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE25_KnowledgeAboutReality_1), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE25_KnowledgeAboutReality_1), "Desc");
		} else if(poModuleName.equals("E26_DecisionMaking")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE26_DecisionMaking), "State");			
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE26_DecisionMaking), "Desc");
			oRetVal.addInspector( new clsE26DecisionCalculation(poSuperInspector, poWrapper, poState, moPA.moE26_DecisionMaking), "Decision Calculation");
		} else if(poModuleName.equals("E27_GenerationOfImaginaryActions")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE27_GenerationOfImaginaryActions), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE27_GenerationOfImaginaryActions), "Desc");
		} else if(poModuleName.equals("E28_KnowledgeBase_StoredScenarios")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE28_KnowledgeBase_StoredScenarios), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE28_KnowledgeBase_StoredScenarios), "Desc");
		} else if(poModuleName.equals("E29_EvaluationOfImaginaryActions")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE29_EvaluationOfImaginaryActions), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE29_EvaluationOfImaginaryActions), "Desc");
		} else if(poModuleName.equals("E30_MotilityControl")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE30_MotilityControl), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE30_MotilityControl), "Desc");			
		} else if(poModuleName.equals("E31_NeuroDeSymbolizationActionCommands")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE31_NeuroDeSymbolizationActionCommands), "State");			
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE31_NeuroDeSymbolizationActionCommands), "Desc");
			oRetVal.addInspector( new clsTimingDiagramInspector(poSuperInspector, poWrapper, poState, moPA.moE31_NeuroDeSymbolizationActionCommands, 150, "Current Act"), "Current Act");
		} else if(poModuleName.equals("E32_Actuators")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE32_Actuators), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE32_Actuators), "Desc");
		} else if(poModuleName.equals("E33_RealityCheck_2")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE33_RealityCheck_2), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE33_RealityCheck_2), "Desc");
		} else if(poModuleName.equals("E34_KnowledgeAboutReality_2")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE34_KnowledgeAboutReality_2), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE34_KnowledgeAboutReality_2), "Desc");
		} else if(poModuleName.equals("E35_EmersionOfRepressedContent")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE35_EmersionOfRepressedContent), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE35_EmersionOfRepressedContent), "Desc");
		} else if(poModuleName.equals("E36_RepressionHandler")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE36_RepressionHandler), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE36_RepressionHandler), "Desc");
		} else if(poModuleName.equals("E37_PrimalRepressionForPerception")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE37_PrimalRepressionForPerception), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE37_PrimalRepressionForPerception), "Desc");
		} else if(poModuleName.equals("E38_PrimalRepressionForSelfPreservationDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE38_PrimalRepressionForSelfPreservationDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE38_PrimalRepressionForSelfPreservationDrives), "Desc");
		} else if(poModuleName.equals("E39_SeekingSystem_LibidoSource")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE39_SeekingSystem_LibidoSource), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE39_SeekingSystem_LibidoSource), "Desc");
		} else if(poModuleName.equals("E40_NeurosymbolizationOfLibido")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE40_NeurosymbolizationOfLibido), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE40_NeurosymbolizationOfLibido), "Desc");
		} else if(poModuleName.equals("E41_Libidostasis")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE41_Libidostasis), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE41_Libidostasis), "Desc");
		} else if(poModuleName.equals("E42_AccumulationOfAffectsForSexualDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE42_AccumulationOfAffectsForSexualDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE42_AccumulationOfAffectsForSexualDrives), "Desc");
		} else if(poModuleName.equals("E43_SeparationIntoPartialSexualDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE43_SeparationIntoPartialSexualDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE43_SeparationIntoPartialSexualDrives), "Desc");
		} else if(poModuleName.equals("E44_PrimalRepressionForSexualDrives")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE44_PrimalRepressionForSexualDrives), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE44_PrimalRepressionForSexualDrives), "Desc");
		} else if(poModuleName.equals("E45_LibidoDischarge")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE45_LibidoDischarge), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE45_LibidoDischarge), "Desc");
		} else if(poModuleName.equals("E46_FusionWithMemoryTraces")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE46_FusionWithMemoryTraces), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE46_FusionWithMemoryTraces), "Desc");
		} else if(poModuleName.equals("E47_ConversionToPrimaryProcess")) {
			oRetVal.addInspector( new clsE_StateInspector(poSuperInspector, poWrapper, poState, moPA.moE47_ConversionToPrimaryProcess), "State");
			oRetVal.addInspector( new clsE_DescriptionInspector(poSuperInspector, poWrapper, poState, moPA.moE47_ConversionToPrimaryProcess), "Desc");
		}		
		else if(poModuleName.equals("Psychic Apparatus")) {
			oRetVal.addInspector( new clsPAInspectorFunctional(poSuperInspector, poWrapper, poState, poTree, true, moPA), "FM Compact");
			oRetVal.addInspector( new clsPAInspectorFunctional(poSuperInspector, poWrapper, poState, poTree, false, moPA), "Functional Model");
			//oRetVal.addInspector( new clsPAInspectorTopDown(poSuperInspector, poWrapper, poState, moPA), "Top-Down Design");			
			//oRetVal.addInspector( new clsPAInspectorFuncModel(poSuperInspector, poWrapper, poState, moPA), "Functional View");
		}

		//========== MEMORY ==============
		else if(poModuleName.equals("RepressedContentsStore")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moRepressedContentsStore, "moRepressedContent", true ), "Repressed Content");
		}
		else if(poModuleName.equals("AwareContentsStore")) {
		}
		else if(poModuleName.equals("ObjectSemanticsStorage")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moObjectSemanticsStorage, "moObjectSemanticsArray", true ), "Repressed Content");
		}
		else if(poModuleName.equals("CurrentContextStorage")) {
		}
		else if(poModuleName.equals("TemplateImageStorage")) {
			oRetVal.addInspector( new clsSecondaryInformationIspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moTemplateImageStorage, "moTemplateImages" ), "Template Images");		
		}
		else if(poModuleName.equals("TemplateScenarioStorage")) {
			
			for(clsSecondaryInformation oScenario : moPA.getMemoryForInspector().moTemplateScenarioStorage.moTemplateScenarios) {
				oRetVal.addInspector( new clsScenarioInspector(poSuperInspector, poWrapper, poState, (clsScenarioBaseMesh)oScenario ), oScenario.moWP.moContent.toString());
			}
		}
		else if(poModuleName.equals("TemplatePlanStorage")) {
			
			for(clsSecondaryInformation oScenario : moPA.getMemoryForInspector().moTemplatePlanStorage.moTemplatePlans) {
				oRetVal.addInspector( new clsPlanInspector(poSuperInspector, poWrapper, poState, (clsPlanBaseMesh)oScenario ), oScenario.moWP.moContent.toString());
			}
		}
		//========== MEMORY 2.0 ===========
		
		//special memory tree...
		else if(poModuleName.equals("TPM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moTemplateImageStorage, "moTemplateImages" ), "Memory v2.0 TEST");		
		}
		
		// Functions Memory Tree...
		//inspect the modules memory (all have 'MEM' at end!)
		
		else if(poModuleName.equals("E03GenerationOfDrivesMEM")) {
			//is a hash map! oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives, "mo"), "E03 - recieve");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives, "moDriveDefinition"), "E03 - send");
		}
		else if (poModuleName.equals("E05_AccumulationOfAffectsForSelfPreservationDrivesMEM")) {
			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA, eInterfaces.I1_4 ), "rcv I1_4");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives, "moDriveList" ), "E05 - send");
		}		
		else if(poModuleName.equals("E14PreliminaryExternalPerceptionMEM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception, "moEnvironmentalTP" ), "E14 - rend (I2.5)");
		}
		else if(poModuleName.equals("E15_1_ManagementOfRepressedContentsMEM")) {
		}
		else if(poModuleName.equals("E16ManagementOfMemoryTracesMEM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces, "moPerceptPlusRepressed_Input" ), "E16 - recieve");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces, "moPerceptPlusMemories_Output" ), "E16 - send");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces, "moRetrievedEnvironmentalMatches" ), "E16 - env. match");
		}
		else if(poModuleName.equals("E17FusionOfExternalPerceptionAndMemoryTracesMEM")) {
		}
		else if(poModuleName.equals("E18GenerationOfAffectsForPerceptionMEM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE18GenerationOfAffectsForPerception, "moMergedPrimaryInformation_Input"), "E18 - recieve");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE18GenerationOfAffectsForPerception, "moNewPrimaryInformation"), "E18 - send");
		}
		else if(poModuleName.equals("E22SuperEgoPreconsciousMEM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG04SuperEgo.moE22SuperEgoPreconscious, "moPerception"), "E22 - recieve");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG04SuperEgo.moE22SuperEgoPreconscious, "moRuleList"), "E22 - send");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG04SuperEgo.moE22SuperEgoPreconscious, "moRetrieveResult4Inspectors"), "E22 - matchretrieval");
		}
		else if(poModuleName.equals("E26DecisionMakingMEM")) {
			//TODO oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE26DecisionMaking, "TODO"), "E26 - recieve");
		}
		else if(poModuleName.equals("E27GenerationOfImaginaryActionsMEM")) {
			//oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE27GenerationOfImaginaryActions., "TODO"), "E27 - recieve");
		}
		else if(poModuleName.equals("E28KnowledgeBase_StoredScenariosMEM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG16SecondaryKnowledgeUtilizer.moE28KnowledgeBase_StoredScenarios, "getGoal_Input"), "E28 - recieve");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG16SecondaryKnowledgeUtilizer.moE28KnowledgeBase_StoredScenarios, "getPlan_Output"), "E28 - send");
		}
		
		
		
		


		
		
		
		
		
		else {
			
		}
		
		return oRetVal;
	}
	
}
