/**
 * clsPsychicApperatus.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 14:55:59
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import config.clsProperties;
import pa._v38.tools.clsPair;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.logger.clsDataLogger;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.storage.DT1_LibidoBuffer;
import pa._v38.storage.DT3_PsychicEnergyStorage;
import pa._v38.storage.clsShortTimeMemory;

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
	
	public F01_SensorsMetabolism moF01_SensorsMetabolism;
	public F02_NeurosymbolizationOfNeeds moF02_NeurosymbolizationOfNeeds;
	public F03_GenerationOfSelfPreservationDrives moF03_GenerationOfSelfPreservationDrives;
	public F04_FusionOfSelfPreservationDrives moF04_FusionOfSelfPreservationDrives;
	public F06_DefenseMechanismsForDrives moF06_DefenseMechanismsForDrives;
	public F08_ConversionToSecondaryProcessForDriveWishes moF08_ConversionToSecondaryProcessForDriveWishes;
	public F10_SensorsEnvironment moF10_SensorsEnvironment;
	public F11_NeuroSymbolizationEnvironment moF11_NeuroSymbolizationEnvironment;
	public F12_SensorsBody moF12_SensorsBody;
	public F13_NeuroSymbolizationBody moF13_NeuroSymbolizationBody;
	public F14_ExternalPerception moF14_ExternalPerception;
	public F18_CompositionOfAffectsForPerception moF18_CompositionOfAffectsForPerception;
	public F19_DefenseMechanismsForPerception moF19_DefenseMechanismsForPerception;
	public F20_InnerPerception_Affects moF20_InnerPerception_Affects;
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
	public F41_Libidostasis moF41_Libidostasis;
	public F43_SeparationIntoPartialSexualDrives moF43_SeparationIntoPartialSexualDrives;
	public F48_AccumulationOfAffectsForDrives moF48_AccumulationOfAffectsForDrives;
	public F57_MemoryTracesForDrives moF57_MemoryTracesForDrives;
	public F49_PrimalRepressionForDrives moF49_PrimalRepressionForDrives;
	public F54_EmersionOfBlockedDriveContent moF54_EmersionOfBlockedDriveContent;
	public F56_Desexualization_Neutralization moF56_Desexualization_Neutralization;
	public F55_SuperEgoProactive moF55_SuperEgoProactive; 
	public F07_SuperEgoReactive moF07_SuperEgoReactive; 
	public F52_GenerationOfImaginaryActions moF52_GenerationOfImaginaryActions; 
	public F45_LibidoDischarge moF45_LibidoDischarge;
	public F46_FusionWithMemoryTraces moF46_FusionWithMemoryTraces;
	public F47_ConversionToPrimaryProcess moF47_ConversionToPrimaryProcess;
	
	/** The knowlegdebase / aka memory; @since 13.07.2011 17:48:27 */
	public clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	/** Libido buffer storage. Necessary for DT1.; @since 13.07.2011 17:48:42 */
	public DT1_LibidoBuffer moLibidoBuffer;
	/** Blocked content storage. Necessary for DT2.; @since 13.07.2011 17:49:01 */
	public DT2_BlockedContentStorage moBlockedContentStorage;
	/** Free psychic energy storage. Necessary for DT3.; @since 12.10.2011 18:38:57 */
	public DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	/** (wendt) The instance of the short time memory; @since 15.11.2011 12:38:18 */
	public clsShortTimeMemory moShortTimeMemory;
	
	/** List of the currently transfered data via the interfaces. Has to be refilled each round at each send_I?_? method manually!; @since 13.07.2011 17:49:33 */
	public SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
	/** List of the modules defined above. Needed for references within them.; @since 13.07.2011 17:49:56 */
	public HashMap<Integer, clsModuleBase> moModules;

	/** The mesh created by all modules and the outgoing interfaces in combination to which module they are connecting to. Static data - does not change after it has been created.; @since 13.07.2011 17:50:19 */
	public HashMap<Integer, ArrayList<clsPair<eInterfaces, Integer>>> moInterfaceMesh;
	/** List of interfaces and the modules it connects to pair(source,target).; @since 13.07.2011 17:50:47 */
	public HashMap<eInterfaces, clsPair<ArrayList<Integer>, ArrayList<Integer>>> moInterfaces_Recv_Send;
	/** The data logger. Can log everything from any module that implements the corresponding interfaces. 
	 * @see pa._v38.logger 
	 * @since 13.07.2011 17:52:06 */
	public clsDataLogger moDataLogger;
	
	/** Unique identifier. The same for the body and the decision unit. Eases debugging and logging.; @since 13.07.2011 17:55:28 */
	private int uid;

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
	public clsPsychicApparatus(String poPrefix, clsProperties poProp, 
			clsKnowledgeBaseHandler poKnowledgeBaseHandler, int uid) {
		this.uid = uid;
		moModules = new HashMap<Integer, clsModuleBase>();
		moInterfaceData = new TreeMap<eInterfaces, ArrayList<Object>>();
		
		moKnowledgeBaseHandler = poKnowledgeBaseHandler; 
		
		moLibidoBuffer = new DT1_LibidoBuffer();
		moBlockedContentStorage = new DT2_BlockedContentStorage();
		moPsychicEnergyStorage = new DT3_PsychicEnergyStorage();
		moShortTimeMemory = new clsShortTimeMemory();
					
		applyProperties(poPrefix, poProp);
		
		moDataLogger = new clsDataLogger(moModules, this.uid);
		fillInterfaceMesh();
		fillInterfaces_Recv_Send();
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

		oProp.putAll( F01_SensorsMetabolism.getDefaultProperties( pre + F01_SensorsMetabolism.P_MODULENUMBER ));
		oProp.putAll( F02_NeurosymbolizationOfNeeds.getDefaultProperties( pre + F02_NeurosymbolizationOfNeeds.P_MODULENUMBER ));
		oProp.putAll( F03_GenerationOfSelfPreservationDrives.getDefaultProperties( pre + F03_GenerationOfSelfPreservationDrives.P_MODULENUMBER ));
		oProp.putAll( F04_FusionOfSelfPreservationDrives.getDefaultProperties( pre + F04_FusionOfSelfPreservationDrives.P_MODULENUMBER ));
		oProp.putAll( F06_DefenseMechanismsForDrives.getDefaultProperties( pre + F06_DefenseMechanismsForDrives.P_MODULENUMBER ));
		oProp.putAll( F08_ConversionToSecondaryProcessForDriveWishes.getDefaultProperties( pre + F08_ConversionToSecondaryProcessForDriveWishes.P_MODULENUMBER ));
		oProp.putAll( F10_SensorsEnvironment.getDefaultProperties( pre + F10_SensorsEnvironment.P_MODULENUMBER ));
		oProp.putAll( F11_NeuroSymbolizationEnvironment.getDefaultProperties( pre + F11_NeuroSymbolizationEnvironment.P_MODULENUMBER ));
		oProp.putAll( F12_SensorsBody.getDefaultProperties( pre + F12_SensorsBody.P_MODULENUMBER ));
		oProp.putAll( F13_NeuroSymbolizationBody.getDefaultProperties( pre + F13_NeuroSymbolizationBody.P_MODULENUMBER ));
		oProp.putAll( F14_ExternalPerception.getDefaultProperties( pre + F14_ExternalPerception.P_MODULENUMBER ));
		oProp.putAll( F18_CompositionOfAffectsForPerception.getDefaultProperties( pre + F18_CompositionOfAffectsForPerception.P_MODULENUMBER ));
		oProp.putAll( F19_DefenseMechanismsForPerception.getDefaultProperties( pre + F19_DefenseMechanismsForPerception.P_MODULENUMBER ));
		oProp.putAll( F20_InnerPerception_Affects.getDefaultProperties( pre + F20_InnerPerception_Affects.P_MODULENUMBER ));
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
		oProp.putAll( F41_Libidostasis.getDefaultProperties( pre + F41_Libidostasis.P_MODULENUMBER ));
		oProp.putAll( F43_SeparationIntoPartialSexualDrives.getDefaultProperties( pre + F43_SeparationIntoPartialSexualDrives.P_MODULENUMBER ));
		oProp.putAll( F48_AccumulationOfAffectsForDrives.getDefaultProperties( pre + F48_AccumulationOfAffectsForDrives.P_MODULENUMBER ));
		oProp.putAll( F57_MemoryTracesForDrives.getDefaultProperties( pre + F57_MemoryTracesForDrives.P_MODULENUMBER ));
		oProp.putAll( F49_PrimalRepressionForDrives.getDefaultProperties( pre + F49_PrimalRepressionForDrives.P_MODULENUMBER ));
		oProp.putAll( F54_EmersionOfBlockedDriveContent.getDefaultProperties( pre +  F54_EmersionOfBlockedDriveContent.P_MODULENUMBER ));
		oProp.putAll( F56_Desexualization_Neutralization.getDefaultProperties( pre + F56_Desexualization_Neutralization.P_MODULENUMBER ));
		oProp.putAll( F55_SuperEgoProactive.getDefaultProperties( pre + F55_SuperEgoProactive.P_MODULENUMBER ));
		oProp.putAll( F07_SuperEgoReactive.getDefaultProperties( pre + F07_SuperEgoReactive.P_MODULENUMBER ));
		oProp.putAll( F52_GenerationOfImaginaryActions.getDefaultProperties( pre + F52_GenerationOfImaginaryActions.P_MODULENUMBER ));
		oProp.putAll( F45_LibidoDischarge.getDefaultProperties( pre + F45_LibidoDischarge.P_MODULENUMBER ));
		oProp.putAll( F46_FusionWithMemoryTraces.getDefaultProperties( pre + F46_FusionWithMemoryTraces.P_MODULENUMBER ));
		oProp.putAll( F47_ConversionToPrimaryProcess.getDefaultProperties( pre + F47_ConversionToPrimaryProcess.P_MODULENUMBER ));

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
			
			moF01_SensorsMetabolism = new F01_SensorsMetabolism(pre + F01_SensorsMetabolism.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF02_NeurosymbolizationOfNeeds = new F02_NeurosymbolizationOfNeeds(pre + F02_NeurosymbolizationOfNeeds.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF03_GenerationOfSelfPreservationDrives = new F03_GenerationOfSelfPreservationDrives(pre + F03_GenerationOfSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF04_FusionOfSelfPreservationDrives = new F04_FusionOfSelfPreservationDrives(pre + F04_FusionOfSelfPreservationDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF06_DefenseMechanismsForDrives = new F06_DefenseMechanismsForDrives(pre + F06_DefenseMechanismsForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage);
			moF08_ConversionToSecondaryProcessForDriveWishes = new F08_ConversionToSecondaryProcessForDriveWishes(pre + F08_ConversionToSecondaryProcessForDriveWishes.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF10_SensorsEnvironment = new F10_SensorsEnvironment(pre + F10_SensorsEnvironment.P_MODULENUMBER, poProp, moModules, moInterfaceData, uid);
			moF11_NeuroSymbolizationEnvironment = new F11_NeuroSymbolizationEnvironment(pre + F11_NeuroSymbolizationEnvironment.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF12_SensorsBody = new F12_SensorsBody(pre + F12_SensorsBody.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF13_NeuroSymbolizationBody = new F13_NeuroSymbolizationBody(pre + F13_NeuroSymbolizationBody.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF14_ExternalPerception = new F14_ExternalPerception(pre + F14_ExternalPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF18_CompositionOfAffectsForPerception = new F18_CompositionOfAffectsForPerception(pre + F18_CompositionOfAffectsForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF19_DefenseMechanismsForPerception = new F19_DefenseMechanismsForPerception(pre + F19_DefenseMechanismsForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage, moKnowledgeBaseHandler);
			moF20_InnerPerception_Affects = new F20_InnerPerception_Affects(pre + F20_InnerPerception_Affects.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF21_ConversionToSecondaryProcessForPerception = new F21_ConversionToSecondaryProcessForPerception(pre + F21_ConversionToSecondaryProcessForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF23_ExternalPerception_focused = new F23_ExternalPerception_focused(pre + F23_ExternalPerception_focused.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF51_RealityCheckWishFulfillment = new F51_RealityCheckWishFulfillment(pre + F51_RealityCheckWishFulfillment.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler, moShortTimeMemory);
			moF26_DecisionMaking = new F26_DecisionMaking(pre + F26_DecisionMaking.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF29_EvaluationOfImaginaryActions = new F29_EvaluationOfImaginaryActions(pre + F29_EvaluationOfImaginaryActions.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF30_MotilityControl = new F30_MotilityControl(pre + F30_MotilityControl.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF31_NeuroDeSymbolizationActionCommands = new F31_NeuroDeSymbolizationActionCommands(pre + F31_NeuroDeSymbolizationActionCommands.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF32_Actuators = new F32_Actuators(pre + F32_Actuators.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF53_RealityCheckActionPlanning = new F53_RealityCheckActionPlanning(pre + F53_RealityCheckActionPlanning.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF35_EmersionOfBlockedContent = new F35_EmersionOfBlockedContent(pre + F35_EmersionOfBlockedContent.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler, moBlockedContentStorage);
			moF37_PrimalRepressionForPerception = new F37_PrimalRepressionForPerception(pre + F37_PrimalRepressionForPerception.P_MODULENUMBER, poProp, moModules, moInterfaceData, moBlockedContentStorage);
			moF39_SeekingSystem_LibidoSource = new F39_SeekingSystem_LibidoSource(pre + F39_SeekingSystem_LibidoSource.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer);
			moF40_NeurosymbolizationOfLibido = new F40_NeurosymbolizationOfLibido(pre + F40_NeurosymbolizationOfLibido.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF41_Libidostasis = new F41_Libidostasis(pre + F41_Libidostasis.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer);
			moF43_SeparationIntoPartialSexualDrives = new F43_SeparationIntoPartialSexualDrives(pre + F43_SeparationIntoPartialSexualDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF48_AccumulationOfAffectsForDrives = new F48_AccumulationOfAffectsForDrives(pre + F48_AccumulationOfAffectsForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF57_MemoryTracesForDrives = new F57_MemoryTracesForDrives(pre + F57_MemoryTracesForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF49_PrimalRepressionForDrives = new F49_PrimalRepressionForDrives(pre + F49_PrimalRepressionForDrives.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF54_EmersionOfBlockedDriveContent = new F54_EmersionOfBlockedDriveContent(pre + F54_EmersionOfBlockedDriveContent.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF56_Desexualization_Neutralization = new F56_Desexualization_Neutralization(pre + F56_Desexualization_Neutralization.P_MODULENUMBER, poProp, moModules, moInterfaceData, moPsychicEnergyStorage, moLibidoBuffer);
			moF55_SuperEgoProactive = new F55_SuperEgoProactive(pre + F55_SuperEgoProactive.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF07_SuperEgoReactive = new F07_SuperEgoReactive(pre + F07_SuperEgoReactive.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			moF52_GenerationOfImaginaryActions = new F52_GenerationOfImaginaryActions(pre + F52_GenerationOfImaginaryActions.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF45_LibidoDischarge = new F45_LibidoDischarge(pre + F45_LibidoDischarge.P_MODULENUMBER, poProp, moModules, moInterfaceData, moLibidoBuffer, moKnowledgeBaseHandler);
			moF46_FusionWithMemoryTraces = new F46_FusionWithMemoryTraces(pre + F46_FusionWithMemoryTraces.P_MODULENUMBER, poProp, moModules, moInterfaceData, moKnowledgeBaseHandler);
			moF47_ConversionToPrimaryProcess = new F47_ConversionToPrimaryProcess(pre + F47_ConversionToPrimaryProcess.P_MODULENUMBER, poProp, moModules, moInterfaceData);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}	
	
	/**
	 * Fetch the list of all receive and send interfaces from the created funcitonal modules.
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
