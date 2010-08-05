/**
 * clsInspectorMappingPA.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 */
package inspectors.mind.pa;

import javax.swing.JTree;

import inspectors.mind.pa.functionalmodel.clsPAInspectorFunctional;
import pa.datatypes.clsSecondaryInformation;
import pa.loader.plan.clsPlanBaseMesh;
import pa.loader.scenario.clsScenarioBaseMesh;
import pa.modules.G00_PsychicApparatus;
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
													G00_PsychicApparatus moPA, String poModuleName, JTree poTree) {
		TabbedInspector oRetVal = new TabbedInspector();
		
		if(poModuleName.equals("E02NeurosymbolizationOfNeeds")) {
			oRetVal.addInspector( new clsE02InspectorInput(poSuperInspector, poWrapper, poState, moPA.moG01Body.moE02NeurosymbolizationOfNeeds), "Input");
			oRetVal.addInspector( new clsE02InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moG01Body.moE02NeurosymbolizationOfNeeds), "Output (same as Input)");
		}
		else if(poModuleName.equals("E03GenerationOfDrives")) {
			oRetVal.addInspector( new clsE03InspectorInput(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives), "Input");
			oRetVal.addInspector( new clsE03InspectorDriveDefinitions(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives), "Drive Definitions");
			oRetVal.addInspector( new clsE03InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG05DriveHandling.moE03GenerationOfDrives), "Output");
		}
		else if(poModuleName.equals("E05GenerationOfAffectsForDrives")) {
			oRetVal.addInspector( new clsE05InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives), "Current Drives");
			oRetVal.addInspector( new clsE05DriveInspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives, "moDriveList"), "Current Drives (Graph)");
			oRetVal.addInspector( new clsE05DriveTiming(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE05GenerationOfAffectsForDrives, 150, "Drive-Affect Chart"), "Drive-Affect Chart");
		}		
		else if(poModuleName.equals("E14PreliminaryExternalPerception")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception, "moEnvironmentalTP", false ), "Generated Thing Presentations");
		}
		else if(poModuleName.equals("E15_1_ManagementOfRepressedContents")) {
			oRetVal.addInspector( new clsPrimaryInformationPairInspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moE15ManagementOfRepressedContents.moS_ManagementOfRepressedContents_1, "moAttachedRepressed_Output" ), "Output: TP + Attached Repressed TP");
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moRepressedContentsStore, "moRepressedContent", false ), "Repressed Content");
		}
		else if(poModuleName.equals("E16ManagementOfMemoryTraces")) {
			oRetVal.addInspector( new clsPrimaryInformationPairInspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG09PrimaryProcessor.moG13PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces, "moPerceptPlusRepressed_Input" ), "Input: TP + Attached Repressed TP");
		}
		else if(poModuleName.equals("E17FusionOfExternalPerceptionAndMemoryTraces")) {
			// FIXME HZ
			//oRetVal.addInspector( new clsTPMeshListInspector(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception, "moEnvironmentalTP" ), "Generated Thing Presentations");
		}
		else if(poModuleName.equals("E18GenerationOfAffectsForPerception")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moG02Id.moG06AffectGeneration.moE18GenerationOfAffectsForPerception, "moNewPrimaryInformation", false ), "Output: Final TP+Affect");
		}
		else if(poModuleName.equals("Psychic Apparatus")) {
			oRetVal.addInspector( new clsPAInspectorFunctional(poSuperInspector, poWrapper, poState, poTree, true), "FM Compact");
			oRetVal.addInspector( new clsPAInspectorFunctional(poSuperInspector, poWrapper, poState, poTree, false), "Functional Model");
			oRetVal.addInspector( new clsPAInspectorTopDown(poSuperInspector, poWrapper, poState, moPA), "Top-Down Design");			
			oRetVal.addInspector( new clsPAInspectorFuncModel(poSuperInspector, poWrapper, poState, moPA), "Functional View");
		}
		else if(poModuleName.equals("E26DecisionMaking")) {
			oRetVal.addInspector( new clsE26DecisionCalculation(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE26DecisionMaking), "Decision Calculation");
		}
		else if(poModuleName.equals("E27GenerationOfImaginaryActions")) {
			oRetVal.addInspector( new clsTimingDiagramInspector(poSuperInspector, poWrapper, poState, moPA.moG03Ego.moG08PsychicMediator.moG11SecondaryProcessor.moG15Deliberation.moE27GenerationOfImaginaryActions, 150, "Template Matches (Word Presentations)"), "Decision Calculation");
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
		else if(poModuleName.equals("TPM")) {
			oRetVal.addInspector( new clsSemanticInformationIspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moTemplateImageStorage, "moTemplateImages" ), "Memory v2.0 TEST");		
		}
		//put all memory things here
		
		
		
		
		else {
			
		}
		
		return oRetVal;
	}
	
}
