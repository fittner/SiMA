/**
 * clsPsychicApperatus.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 14:55:59
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import config.clsBWProperties;
import pa.interfaces._v30.eInterfaces;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.clsKnowledgeBaseHandlerFactory;
import pa.storage.clsBlockedContentStorage;
import pa.storage.clsLibidoBuffer;
import pa.tools.clsPair;

/**
 * this class holds all instances of model v30. it is responsible for their creation and configuration.  
 * 
 * @author deutsch
 * 03.03.2011, 14:55:59
 * 
 */
public class clsPsychicApparatus {
	public static final String P_INFORMATIONREPRESENTATIONMGMT = "INF_REP_MGMT";

	public E01_SensorsMetabolism moE01_SensorsMetabolism;
	public E02_NeurosymbolizationOfNeeds moE02_NeurosymbolizationOfNeeds;
	public E03_GenerationOfSelfPreservationDrives moE03_GenerationOfSelfPreservationDrives;
	public E04_FusionOfSelfPreservationDrives moE04_FusionOfSelfPreservationDrives;
	public E05_AccumulationOfAffectsForSelfPreservationDrives moE05_AccumulationOfAffectsForSelfPreservationDrives;
	public E06_DefenseMechanismsForDrives moE06_DefenseMechanismsForDrives;
	public E07_InternalizedRulesHandler moE07_InternalizedRulesHandler;
	public E08_ConversionToSecondaryProcessForDriveWishes moE08_ConversionToSecondaryProcessForDriveWishes;
	public E09_KnowledgeAboutReality_unconscious moE09_KnowledgeAboutReality_unconscious;
	public E10_SensorsEnvironment moE10_SensorsEnvironment;
	public E11_NeuroSymbolizationEnvironment moE11_NeuroSymbolizationEnvironment;
	public E12_SensorsBody moE12_SensorsBody;
	public E13_NeuroSymbolizationBody moE13_NeuroSymbolizationBody;
	public E14_ExternalPerception moE14_ExternalPerception;
	public E18_CompositionOfAffectsForPerception moE18_CompositionOfAffectsForPerception;
	public E19_DefenseMechanismsForPerception moE19_DefenseMechanismsForPerception;
	public E20_InnerPerception_Affects moE20_InnerPerception_Affects;
	public E21_ConversionToSecondaryProcessForPerception moE21_ConversionToSecondaryProcessForPerception;
	public E22_SocialRulesSelection moE22_SocialRulesSelection;
	public E23_ExternalPerception_focused moE23_ExternalPerception_focused;
	public E24_RealityCheck_1 moE24_RealityCheck_1;
	public E25_KnowledgeAboutReality_1 moE25_KnowledgeAboutReality_1;
	public E26_DecisionMaking moE26_DecisionMaking;
	public E27_GenerationOfImaginaryActions moE27_GenerationOfImaginaryActions;
	public E28_KnowledgeBase_StoredScenarios moE28_KnowledgeBase_StoredScenarios;
	public E29_EvaluationOfImaginaryActions moE29_EvaluationOfImaginaryActions;
	public E30_MotilityControl moE30_MotilityControl;
	public E31_NeuroDeSymbolizationActionCommands moE31_NeuroDeSymbolizationActionCommands;
	public E32_Actuators moE32_Actuators;
	public E33_RealityCheck_2 moE33_RealityCheck_2;
	public E34_KnowledgeAboutReality_2 moE34_KnowledgeAboutReality_2;
	public E35_EmersionOfRepressedContent moE35_EmersionOfRepressedContent;
	public E36_RepressionHandler moE36_RepressionHandler;
	public E37_PrimalRepressionForPerception moE37_PrimalRepressionForPerception;
	public E38_PrimalRepressionForSelfPreservationDrives moE38_PrimalRepressionForSelfPreservationDrives;
	public E39_SeekingSystem_LibidoSource moE39_SeekingSystem_LibidoSource;
	public E40_NeurosymbolizationOfLibido moE40_NeurosymbolizationOfLibido;
	public E41_Libidostasis moE41_Libidostasis;
	public E42_AccumulationOfAffectsForSexualDrives moE42_AccumulationOfAffectsForSexualDrives;
	public E43_SeparationIntoPartialSexualDrives moE43_SeparationIntoPartialSexualDrives;
	public E44_PrimalRepressionForSexualDrives moE44_PrimalRepressionForSexualDrives;
	public E45_LibidoDischarge moE45_LibidoDischarge;
	public E46_FusionWithMemoryTraces moE46_FusionWithMemoryTraces;
	public E47_ConversionToPrimaryProcess moE47_ConversionToPrimaryProcess;
	
	public clsMemory moMemory;
	public clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	public clsLibidoBuffer moLibidoBuffer;
	public clsBlockedContentStorage moBlockedContentStorage;
	
	public HashMap<Integer, clsModuleBase> moModules; // list of the modules defined above. needed for references within them.
	public SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData; //list of the currently transfered data via the interfaces. has to be refilled each round at each send_I?_? method manually!
	public HashMap<Integer, ArrayList<clsPair<eInterfaces, Integer>>> moInterfaceMesh; //the mesh created by all modules and the outgoing interfaces in combination to which module they are connecting to
	public HashMap<eInterfaces, clsPair<ArrayList<Integer>, ArrayList<Integer>>> moInterfaces_Recv_Send; 

	public clsPsychicApparatus(String poPrefix, clsBWProperties poProp, 
			clsMemory poMemory,	clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		
		moModules = new HashMap<Integer, clsModuleBase>();
		moInterfaceData = new TreeMap<eInterfaces, ArrayList<Object>>();
		
		moMemory = poMemory;
		moKnowledgeBaseHandler = poKnowledgeBaseHandler; 
		
		moLibidoBuffer = new clsLibidoBuffer();
		moBlockedContentStorage = new clsBlockedContentStorage();
					
		applyProperties(poPrefix, poProp);
		
		fillInterfaceMesh();
		fillInterfaces_Recv_Send();
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E01_SensorsMetabolism.getDefaultProperties( pre + E01_SensorsMetabolism.P_MODULENUMBER ));
		oProp.putAll( E02_NeurosymbolizationOfNeeds.getDefaultProperties( pre + E02_NeurosymbolizationOfNeeds.P_MODULENUMBER ));
		oProp.putAll( E03_GenerationOfSelfPreservationDrives.getDefaultProperties( pre + E03_GenerationOfSelfPreservationDrives.P_MODULENUMBER ));
		oProp.putAll( E04_FusionOfSelfPreservationDrives.getDefaultProperties( pre + E04_FusionOfSelfPreservationDrives.P_MODULENUMBER ));
		oProp.putAll( E05_AccumulationOfAffectsForSelfPreservationDrives.getDefaultProperties( pre + E05_AccumulationOfAffectsForSelfPreservationDrives.P_MODULENUMBER ));
		oProp.putAll( E06_DefenseMechanismsForDrives.getDefaultProperties( pre + E06_DefenseMechanismsForDrives.P_MODULENUMBER ));
		oProp.putAll( E07_InternalizedRulesHandler.getDefaultProperties( pre + E07_InternalizedRulesHandler.P_MODULENUMBER ));
		oProp.putAll( E08_ConversionToSecondaryProcessForDriveWishes.getDefaultProperties( pre + E08_ConversionToSecondaryProcessForDriveWishes.P_MODULENUMBER ));
		oProp.putAll( E09_KnowledgeAboutReality_unconscious.getDefaultProperties( pre + E09_KnowledgeAboutReality_unconscious.P_MODULENUMBER ));
		oProp.putAll( E10_SensorsEnvironment.getDefaultProperties( pre + E10_SensorsEnvironment.P_MODULENUMBER ));
		oProp.putAll( E11_NeuroSymbolizationEnvironment.getDefaultProperties( pre + E11_NeuroSymbolizationEnvironment.P_MODULENUMBER ));
		oProp.putAll( E12_SensorsBody.getDefaultProperties( pre + E12_SensorsBody.P_MODULENUMBER ));
		oProp.putAll( E13_NeuroSymbolizationBody.getDefaultProperties( pre + E13_NeuroSymbolizationBody.P_MODULENUMBER ));
		oProp.putAll( E14_ExternalPerception.getDefaultProperties( pre + E14_ExternalPerception.P_MODULENUMBER ));
		oProp.putAll( E18_CompositionOfAffectsForPerception.getDefaultProperties( pre + E18_CompositionOfAffectsForPerception.P_MODULENUMBER ));
		oProp.putAll( E19_DefenseMechanismsForPerception.getDefaultProperties( pre + E19_DefenseMechanismsForPerception.P_MODULENUMBER ));
		oProp.putAll( E20_InnerPerception_Affects.getDefaultProperties( pre + E20_InnerPerception_Affects.P_MODULENUMBER ));
		oProp.putAll( E21_ConversionToSecondaryProcessForPerception.getDefaultProperties( pre + E21_ConversionToSecondaryProcessForPerception.P_MODULENUMBER ));
		oProp.putAll( E22_SocialRulesSelection.getDefaultProperties( pre + E22_SocialRulesSelection.P_MODULENUMBER ));
		oProp.putAll( E23_ExternalPerception_focused.getDefaultProperties( pre + E23_ExternalPerception_focused.P_MODULENUMBER ));
		oProp.putAll( E24_RealityCheck_1.getDefaultProperties( pre + E24_RealityCheck_1.P_MODULENUMBER ));
		oProp.putAll( E25_KnowledgeAboutReality_1.getDefaultProperties( pre + E25_KnowledgeAboutReality_1.P_MODULENUMBER ));
		oProp.putAll( E26_DecisionMaking.getDefaultProperties( pre + E26_DecisionMaking.P_MODULENUMBER ));
		oProp.putAll( E27_GenerationOfImaginaryActions.getDefaultProperties( pre + E27_GenerationOfImaginaryActions.P_MODULENUMBER ));
		oProp.putAll( E28_KnowledgeBase_StoredScenarios.getDefaultProperties( pre + E28_KnowledgeBase_StoredScenarios.P_MODULENUMBER ));
		oProp.putAll( E29_EvaluationOfImaginaryActions.getDefaultProperties( pre + E29_EvaluationOfImaginaryActions.P_MODULENUMBER ));
		oProp.putAll( E30_MotilityControl.getDefaultProperties( pre + E30_MotilityControl.P_MODULENUMBER ));
		oProp.putAll( E31_NeuroDeSymbolizationActionCommands.getDefaultProperties( pre + E31_NeuroDeSymbolizationActionCommands.P_MODULENUMBER ));
		oProp.putAll( E32_Actuators.getDefaultProperties( pre + E32_Actuators.P_MODULENUMBER ));
		oProp.putAll( E33_RealityCheck_2.getDefaultProperties( pre + E33_RealityCheck_2.P_MODULENUMBER ));
		oProp.putAll( E34_KnowledgeAboutReality_2.getDefaultProperties( pre + E34_KnowledgeAboutReality_2.P_MODULENUMBER ));
		oProp.putAll( E35_EmersionOfRepressedContent.getDefaultProperties( pre + E35_EmersionOfRepressedContent.P_MODULENUMBER ));
		oProp.putAll( E36_RepressionHandler.getDefaultProperties( pre + E36_RepressionHandler.P_MODULENUMBER ));
		oProp.putAll( E37_PrimalRepressionForPerception.getDefaultProperties( pre + E37_PrimalRepressionForPerception.P_MODULENUMBER ));
		oProp.putAll( E38_PrimalRepressionForSelfPreservationDrives.getDefaultProperties( pre + E38_PrimalRepressionForSelfPreservationDrives.P_MODULENUMBER ));
		oProp.putAll( E39_SeekingSystem_LibidoSource.getDefaultProperties( pre + E39_SeekingSystem_LibidoSource.P_MODULENUMBER ));
		oProp.putAll( E40_NeurosymbolizationOfLibido.getDefaultProperties( pre + E40_NeurosymbolizationOfLibido.P_MODULENUMBER ));
		oProp.putAll( E41_Libidostasis.getDefaultProperties( pre + E41_Libidostasis.P_MODULENUMBER ));
		oProp.putAll( E42_AccumulationOfAffectsForSexualDrives.getDefaultProperties( pre + E42_AccumulationOfAffectsForSexualDrives.P_MODULENUMBER ));
		oProp.putAll( E43_SeparationIntoPartialSexualDrives.getDefaultProperties( pre + E43_SeparationIntoPartialSexualDrives.P_MODULENUMBER ));
		oProp.putAll( E44_PrimalRepressionForSexualDrives.getDefaultProperties( pre + E44_PrimalRepressionForSexualDrives.P_MODULENUMBER ));
		oProp.putAll( E45_LibidoDischarge.getDefaultProperties( pre + E45_LibidoDischarge.P_MODULENUMBER ));
		oProp.putAll( E46_FusionWithMemoryTraces.getDefaultProperties( pre + E46_FusionWithMemoryTraces.P_MODULENUMBER ));
		oProp.putAll( E47_ConversionToPrimaryProcess.getDefaultProperties( pre + E47_ConversionToPrimaryProcess.P_MODULENUMBER ));

		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		try {
			//TODO HZ - Integrate to Properties
			moKnowledgeBaseHandler = clsKnowledgeBaseHandlerFactory.createInformationRepresentationManagement("ARSI10_MGMT", pre+P_INFORMATIONREPRESENTATIONMGMT, poProp);
			
			moE01_SensorsMetabolism = new E01_SensorsMetabolism(pre + E01_SensorsMetabolism.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE02_NeurosymbolizationOfNeeds = new E02_NeurosymbolizationOfNeeds(pre + E02_NeurosymbolizationOfNeeds.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE03_GenerationOfSelfPreservationDrives = new E03_GenerationOfSelfPreservationDrives(pre + E03_GenerationOfSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE04_FusionOfSelfPreservationDrives = new E04_FusionOfSelfPreservationDrives(pre + E04_FusionOfSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE05_AccumulationOfAffectsForSelfPreservationDrives = new E05_AccumulationOfAffectsForSelfPreservationDrives(pre + E05_AccumulationOfAffectsForSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE06_DefenseMechanismsForDrives = new E06_DefenseMechanismsForDrives(pre + E06_DefenseMechanismsForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE07_InternalizedRulesHandler = new E07_InternalizedRulesHandler(pre + E07_InternalizedRulesHandler.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE08_ConversionToSecondaryProcessForDriveWishes = new E08_ConversionToSecondaryProcessForDriveWishes(pre + E08_ConversionToSecondaryProcessForDriveWishes.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE09_KnowledgeAboutReality_unconscious = new E09_KnowledgeAboutReality_unconscious(pre + E09_KnowledgeAboutReality_unconscious.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE10_SensorsEnvironment = new E10_SensorsEnvironment(pre + E10_SensorsEnvironment.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE11_NeuroSymbolizationEnvironment = new E11_NeuroSymbolizationEnvironment(pre + E11_NeuroSymbolizationEnvironment.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE12_SensorsBody = new E12_SensorsBody(pre + E12_SensorsBody.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE13_NeuroSymbolizationBody = new E13_NeuroSymbolizationBody(pre + E13_NeuroSymbolizationBody.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE14_ExternalPerception = new E14_ExternalPerception(pre + E14_ExternalPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE18_CompositionOfAffectsForPerception = new E18_CompositionOfAffectsForPerception(pre + E18_CompositionOfAffectsForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE19_DefenseMechanismsForPerception = new E19_DefenseMechanismsForPerception(pre + E19_DefenseMechanismsForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE20_InnerPerception_Affects = new E20_InnerPerception_Affects(pre + E20_InnerPerception_Affects.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE21_ConversionToSecondaryProcessForPerception = new E21_ConversionToSecondaryProcessForPerception(pre + E21_ConversionToSecondaryProcessForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE22_SocialRulesSelection = new E22_SocialRulesSelection(pre + E22_SocialRulesSelection.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE23_ExternalPerception_focused = new E23_ExternalPerception_focused(pre + E23_ExternalPerception_focused.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE24_RealityCheck_1 = new E24_RealityCheck_1(pre + E24_RealityCheck_1.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE25_KnowledgeAboutReality_1 = new E25_KnowledgeAboutReality_1(pre + E25_KnowledgeAboutReality_1.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE26_DecisionMaking = new E26_DecisionMaking(pre + E26_DecisionMaking.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE27_GenerationOfImaginaryActions = new E27_GenerationOfImaginaryActions(pre + E27_GenerationOfImaginaryActions.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE28_KnowledgeBase_StoredScenarios = new E28_KnowledgeBase_StoredScenarios(pre + E28_KnowledgeBase_StoredScenarios.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE29_EvaluationOfImaginaryActions = new E29_EvaluationOfImaginaryActions(pre + E29_EvaluationOfImaginaryActions.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE30_MotilityControl = new E30_MotilityControl(pre + E30_MotilityControl.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE31_NeuroDeSymbolizationActionCommands = new E31_NeuroDeSymbolizationActionCommands(pre + E31_NeuroDeSymbolizationActionCommands.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE32_Actuators = new E32_Actuators(pre + E32_Actuators.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE33_RealityCheck_2 = new E33_RealityCheck_2(pre + E33_RealityCheck_2.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE34_KnowledgeAboutReality_2 = new E34_KnowledgeAboutReality_2(pre + E34_KnowledgeAboutReality_2.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE35_EmersionOfRepressedContent = new E35_EmersionOfRepressedContent(pre + E35_EmersionOfRepressedContent.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler, moMemory, moBlockedContentStorage);
			moE36_RepressionHandler = new E36_RepressionHandler(pre + E36_RepressionHandler.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage);
			moE37_PrimalRepressionForPerception = new E37_PrimalRepressionForPerception(pre + E37_PrimalRepressionForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE38_PrimalRepressionForSelfPreservationDrives = new E38_PrimalRepressionForSelfPreservationDrives(pre + E38_PrimalRepressionForSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE39_SeekingSystem_LibidoSource = new E39_SeekingSystem_LibidoSource(pre + E39_SeekingSystem_LibidoSource.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer);
			moE40_NeurosymbolizationOfLibido = new E40_NeurosymbolizationOfLibido(pre + E40_NeurosymbolizationOfLibido.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE41_Libidostasis = new E41_Libidostasis(pre + E41_Libidostasis.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer);
			moE42_AccumulationOfAffectsForSexualDrives = new E42_AccumulationOfAffectsForSexualDrives(pre + E42_AccumulationOfAffectsForSexualDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE43_SeparationIntoPartialSexualDrives = new E43_SeparationIntoPartialSexualDrives(pre + E43_SeparationIntoPartialSexualDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE44_PrimalRepressionForSexualDrives = new E44_PrimalRepressionForSexualDrives(pre + E44_PrimalRepressionForSexualDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE45_LibidoDischarge = new E45_LibidoDischarge(pre + E45_LibidoDischarge.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moE46_FusionWithMemoryTraces = new E46_FusionWithMemoryTraces(pre + E46_FusionWithMemoryTraces.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moE47_ConversionToPrimaryProcess = new E47_ConversionToPrimaryProcess(pre + E47_ConversionToPrimaryProcess.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		//nothing to do
	}	
	
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
	
	public clsMemory getMemoryForInspector() {
		return moMemory;
	}
}
