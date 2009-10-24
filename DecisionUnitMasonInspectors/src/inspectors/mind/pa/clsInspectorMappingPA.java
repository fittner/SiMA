/**
 * clsInspectorMappingPA.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 13.08.2009, 01:36:00
 */
package inspectors.mind.pa;

import javax.swing.JTree;

import inspectors.mind.pa.functionalmodel.clsPAInspectorFunctional;
import pa.modules.C00_PsychicApparatus;
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
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 13.08.2009, 01:32:58
	 *
	 * @param moPA
	 * @param poModuleName
	 */
	public static TabbedInspector getPAInspector(Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, 
													C00_PsychicApparatus moPA, String poModuleName, JTree poTree) {
		TabbedInspector oRetVal = new TabbedInspector();
		
		if(poModuleName.equals("E02NeurosymbolizationOfNeeds")) {
			oRetVal.addInspector( new clsE02InspectorInput(poSuperInspector, poWrapper, poState, moPA.moC01Body.moE02NeurosymbolizationOfNeeds), "Input");
			oRetVal.addInspector( new clsE02InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moC01Body.moE02NeurosymbolizationOfNeeds), "Output (same as Input)");
		}
		else if(poModuleName.equals("E03GenerationOfDrives")) {
			oRetVal.addInspector( new clsE03InspectorInput(poSuperInspector, poWrapper, poState, moPA.moC02Id.moC05DriveHandling.moE03GenerationOfDrives), "Input");
			oRetVal.addInspector( new clsE03InspectorDriveDefinitions(poSuperInspector, poWrapper, poState, moPA.moC02Id.moC05DriveHandling.moE03GenerationOfDrives), "Drive Definitions");
			oRetVal.addInspector( new clsE03InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moC02Id.moC05DriveHandling.moE03GenerationOfDrives), "Output");
		}
		else if(poModuleName.equals("E05GenerationOfAffectsForDrives")) {
			oRetVal.addInspector( new clsE05InspectorOutput(poSuperInspector, poWrapper, poState, moPA.moC02Id.moC06AffectGeneration.moE05GenerationOfAffectsForDrives), "Current Drives");
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moC02Id.moC06AffectGeneration.moE05GenerationOfAffectsForDrives, "moDriveList"), "Current Drives (Graph)");
		}		
		else if(poModuleName.equals("E14PreliminaryExternalPerception")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception, "moEnvironmentalTP" ), "Generated Thing Presentations");
		}
		else if(poModuleName.equals("E15_1_ManagementOfRepressedContents")) {
			oRetVal.addInspector( new clsPrimaryInformationPairInspector(poSuperInspector, poWrapper, poState, moPA.moC02Id.moE15ManagementOfRepressedContents.moE15_1_ManagementOfRepressedContents, "moAttachedRepressed_Output" ), "Output: TP + Attached Repressed TP");
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moRepressedContentsStore, "moRepressedContent" ), "Repressed Content");
		}
		else if(poModuleName.equals("E16ManagementOfMemoryTraces")) {
			oRetVal.addInspector( new clsPrimaryInformationPairInspector(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC08PsychicMediator.moC09PrimaryProcessor.moC14PrimaryKnowledgeUtilizer.moE16ManagementOfMemoryTraces, "moPerceptPlusRepressed_Input" ), "Input: TP + Attached Repressed TP");
		}
		else if(poModuleName.equals("E17FusionOfExternalPerceptionAndMemoryTraces")) {
			// FIXME HZ
			//oRetVal.addInspector( new clsTPMeshListInspector(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC07EnvironmentalInterfaceFunctions.moE14PreliminaryExternalPerception, "moEnvironmentalTP" ), "Generated Thing Presentations");
		}
		else if(poModuleName.equals("E18GenerationOfAffectsForPerception")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.moC02Id.moC06AffectGeneration.moE18GenerationOfAffectsForPerception, "moNewPrimaryInformation" ), "Output: Final TP+Affect");
		}
		else if(poModuleName.equals("Psychic Apparatus")) {
			oRetVal.addInspector( new clsPAInspectorFunctional(poSuperInspector, poWrapper, poState, poTree, true), "FM Compact");
			oRetVal.addInspector( new clsPAInspectorFunctional(poSuperInspector, poWrapper, poState, poTree, false), "Functional Model");
			oRetVal.addInspector( new clsPAInspectorTopDown(poSuperInspector, poWrapper, poState, moPA), "Top-Down Design");			
			oRetVal.addInspector( new clsPAInspectorFuncModel(poSuperInspector, poWrapper, poState, moPA), "Functional View");
		}
		else if(poModuleName.equals("E26DecisionMaking")) {
			oRetVal.addInspector( new clsE26DecisionCalculation(poSuperInspector, poWrapper, poState, moPA.moC03Ego.moC08PsychicMediator.moC10SecondaryProcessor.moC16Deliberation.moE26DecisionMaking), "Decision Calculation");
		}
		//========== MEMORY ==============
		else if(poModuleName.equals("RepressedContentsStore")) {
			oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moRepressedContentsStore, "moRepressedContent" ), "Repressed Content");
		}
		else if(poModuleName.equals("AwareContentsStore")) {
		}
		else if(poModuleName.equals("ObjectSemanticsStorage")) {
			//oRetVal.addInspector( new clsPrimaryInformationInspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moRepressedContentsStore, "moRepressedContent" ), "Repressed Content");
		}
		else if(poModuleName.equals("CurrentContextStorage")) {
		}
		else if(poModuleName.equals("TemplateImageStorage")) {
			oRetVal.addInspector( new clsSecondaryInformationIspector(poSuperInspector, poWrapper, poState, moPA.getMemoryForInspector().moTemplateImageStorage, "moTemplateImages" ), "Template Images");		
		}
		
		
		
		else {
			
		}
		
		return oRetVal;
	}
	
}
