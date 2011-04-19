/**
 * clsInspectorMappingPA.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 */
package inspectors.mind.pa._v30;

import java.lang.reflect.Field;

import javax.swing.JTree;

import inspectors.mind.pa._v30.functionalmodel.clsPAInspectorFunctional;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces._v30.itfInspectorInternalState;
import pa.interfaces._v30.itfInterfaceDescription;
import pa.interfaces._v30.itfInspectorGenericTimeChart;
import pa.interfaces._v30.itfInterfaceInterfaceData;
import pa.modules._v30.clsModuleBase;
import pa.modules._v30.clsPsychicApparatus;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 * 
 */
public class clsInspectorPATabFactory {

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
	public static TabbedInspector createInspectorModules(clsPsychicApparatus moPA, String poModuleName, JTree poLeftMenu) {
		TabbedInspector oRetVal = null;
		
		if (poModuleName.charAt(0) == 'E') {
			oRetVal = createModules(moPA, poModuleName, poLeftMenu);
			
		} else if (poModuleName.charAt(0) == 'I' || poModuleName.charAt(0) == 'D') {
			oRetVal = createInterfaces(moPA, poModuleName, poLeftMenu);
			
		} else if(poModuleName.equals("Psychic Apparatus")) {
			oRetVal = new TabbedInspector(); 
			
			oRetVal.addInspector( new clsPAInspectorFunctional(poLeftMenu, true, moPA), "FM Compact");
			oRetVal.addInspector( new clsPAInspectorFunctional(poLeftMenu, false, moPA), "Functional Model");
			oRetVal.addInspector( new clsPAInspectorInterfaceData(moPA.moInterfaceData), "Interface Data");
		} else {
			oRetVal = new TabbedInspector();
		}
		
		return oRetVal;
	}
	
	private static TabbedInspector createInterfaces(clsPsychicApparatus moPA, String poModuleName, JTree poLeftMenu) {
		TabbedInspector oRetVal = new TabbedInspector();
		
		try {
			eInterfaces eI = eInterfaces.valueOf(poModuleName);
			oRetVal.addInspector(
					new clsI_SimpleInterfaceDataInspector(eI, moPA.moInterfaceData, moPA.moInterfaces_Recv_Send),
					"Interface Data");
		} catch (java.lang.IllegalArgumentException e) {
			//do nothing
		}
		
		return oRetVal;
	}
	
	private static TabbedInspector createModules(clsPsychicApparatus moPA, String poModuleName, JTree poLeftMenu) {
		TabbedInspector oRetVal = new TabbedInspector();
	
		try {
			String oName = "mo"+poModuleName;
			Field oField = moPA.getClass().getField(oName);
 			clsModuleBase oModule = (clsModuleBase) oField.get( moPA );
			
			if (oModule instanceof itfInspectorInternalState) {
				oRetVal.addInspector( 
						new clsE_StateInspector(oModule), 
						"State");
			}
			
			if (oModule instanceof itfInterfaceDescription) {
				oRetVal.addInspector( 
						new clsE_DescriptionInspector(oModule), 
						"Desc");				
			}
			
			if (oModule instanceof itfInspectorGenericTimeChart) {
				oRetVal.addInspector(
						new clsE_ChartInspector((itfInspectorGenericTimeChart) oModule),	
						"Time Chart");
			}
			
			if (oModule instanceof itfInterfaceInterfaceData) {
				oRetVal.addInspector(
						new clsE_SimpleInterfaceDataInspector(oModule, moPA.moInterfaceData),
						"Interface Data");
						
				// TODO MUCHITSCH ADD INSPECTOR CALL
			}
		} catch (java.lang.NoSuchFieldException e) {
			// do nothing
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		
		if(poModuleName.equals("E01_SensorsMetabolism")) {
		} else if(poModuleName.equals("E02_NeurosymbolizationOfNeeds")) {
		} else if(poModuleName.equals("E03_GenerationOfSelfPreservationDrives")) {
			oRetVal.addInspector( new clsE03InspectorDriveDefinitions(moPA.moE03_GenerationOfSelfPreservationDrives), "Drive Definitions");
		} else if(poModuleName.equals("E04_FusionOfSelfPreservationDrives")) {
		} else if(poModuleName.equals("E05_AccumulationOfAffectsForSelfPreservationDrives")) {
			oRetVal.addInspector( new clsE05DriveInspector(moPA.moE05_AccumulationOfAffectsForSelfPreservationDrives, "moDriveList"), "Current Drives (Graph)");
			oRetVal.addInspector( new clsSemanticInformationIspector(moPA, eInterfaces.I1_4 ), "rcv I1_4");
		} else if(poModuleName.equals("E06_DefenseMechanismsForDrives")) {
		} else if(poModuleName.equals("E07_InternalizedRulesHandler")) {
		} else if(poModuleName.equals("E08_ConversionToSecondaryProcessForDriveWishes")) {
		} else if(poModuleName.equals("E09_KnowledgeAboutReality_unconscious")) {
		} else if(poModuleName.equals("E10_SensorsEnvironment")) {
		} else if(poModuleName.equals("E11_NeuroSymbolizationEnvironment")) {
		} else if(poModuleName.equals("E12_SensorsBody")) {
		} else if(poModuleName.equals("E13_NeuroSymbolizationBody")) {
		} else if(poModuleName.equals("E14_ExternalPerception")) {
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
//@CLEMENS:NullpointerException			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moE18_CompositionOfAffectsForPerception, "moNewPrimaryInformation_old", false ), "Output: Final TP+Affect");
		} else if(poModuleName.equals("E19_DefenseMechanismsForPerception")) {
		} else if(poModuleName.equals("E20_InnerPerception_Affects")) {
		} else if(poModuleName.equals("E21_ConversionToSecondaryProcessForPerception")) {
		} else if(poModuleName.equals("E22_SocialRulesSelection")) {
		} else if(poModuleName.equals("E23_ExternalPerception_focused")) {
		} else if(poModuleName.equals("E24_RealityCheck_1")) {
		} else if(poModuleName.equals("E25_KnowledgeAboutReality_1")) {
		} else if(poModuleName.equals("E26_DecisionMaking")) {
			oRetVal.addInspector( new clsE26DecisionCalculation(moPA.moE26_DecisionMaking), "Decision Calculation");
		} else if(poModuleName.equals("E27_GenerationOfImaginaryActions")) {
		} else if(poModuleName.equals("E28_KnowledgeBase_StoredScenarios")) {
		} else if(poModuleName.equals("E29_EvaluationOfImaginaryActions")) {
		} else if(poModuleName.equals("E30_MotilityControl")) {
		} else if(poModuleName.equals("E31_NeuroDeSymbolizationActionCommands")) {
			oRetVal.addInspector( new clsE31_ChartInspector(moPA.moE31_NeuroDeSymbolizationActionCommands, "Probes", "Current Act"), "Current Act");
			//oRetVal.addInspector( new clsTimingDiagramInspector(poSuperInspector, poWrapper, poState, moPA.moE31_NeuroDeSymbolizationActionCommands, 150, "Current Act"), "Current Act");
		} else if(poModuleName.equals("E32_Actuators")) {
		} else if(poModuleName.equals("E33_RealityCheck_2")) {
		} else if(poModuleName.equals("E34_KnowledgeAboutReality_2")) {
		} else if(poModuleName.equals("E35_EmersionOfRepressedContent")) {
		} else if(poModuleName.equals("E36_RepressionHandler")) {
		} else if(poModuleName.equals("E37_PrimalRepressionForPerception")) {
		} else if(poModuleName.equals("E38_PrimalRepressionForSelfPreservationDrives")) {
		} else if(poModuleName.equals("E39_SeekingSystem_LibidoSource")) {
		} else if(poModuleName.equals("E40_NeurosymbolizationOfLibido")) {
		} else if(poModuleName.equals("E41_Libidostasis")) {			
		} else if(poModuleName.equals("E42_AccumulationOfAffectsForSexualDrives")) {
		} else if(poModuleName.equals("E43_SeparationIntoPartialSexualDrives")) {
		} else if(poModuleName.equals("E44_PrimalRepressionForSexualDrives")) {
		} else if(poModuleName.equals("E45_LibidoDischarge")) {
		} else if(poModuleName.equals("E46_FusionWithMemoryTraces")) {
		} else if(poModuleName.equals("E47_ConversionToPrimaryProcess")) {
		} 
		
		return oRetVal;
	}
		
	public static TabbedInspector createInspectorMemory(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, 
				clsPsychicApparatus moPA, String poModuleName, JTree poTree) {

		TabbedInspector oRetVal = new TabbedInspector();

		//special memory tree...
		if(poModuleName.equals("TPM")) {
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moTemplateImageStorage, "moTemplateImages" ), "Memory v2.0 TEST");		
		}
		
		// Functions Memory Tree...
		//inspect the modules memory (all have 'MEM' at end!)
		
		else if(poModuleName.equals("E03GenerationOfDrivesMEM")) {
			//is a hash map! oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives, "mo"), "E03 - recieve");
//			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives, "moDriveDefinition"), "E03 - send");
		}
		else if (poModuleName.equals("E05_AccumulationOfAffectsForSelfPreservationDrivesMEM")) {
			oRetVal.addInspector( new clsSemanticInformationIspector(moPA, eInterfaces.I1_4 ), "rcv I1_4");
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
