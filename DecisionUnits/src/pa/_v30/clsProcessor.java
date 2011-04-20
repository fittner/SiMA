/**
 * clsProcessor.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 */
package pa._v30;

import java.util.HashMap;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSensorExtern;
import pa.itfProcessor;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.informationrepresentation.clsInformationRepresentationManagement;
import pa.modules._v30.clsPsychicApparatus;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 * 
 */
public class clsProcessor implements itfProcessor  {
	public static final String P_PSYCHICAPPARATUS = "psychicapparatus";
	public static final String P_KNOWLEDGEABASE = "knowledgebase";
	public static final String P_LIBIDOSTREAM = "libidostream";
	
	private clsPsychicApparatus moPsyApp;
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	private double mrLibidostream;
		
	public clsProcessor(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsInformationRepresentationManagement.getDefaultProperties(pre+P_KNOWLEDGEABASE) );
		oProp.putAll( clsPsychicApparatus.getDefaultProperties(pre+P_PSYCHICAPPARATUS) );
				
		oProp.setProperty( pre+P_LIBIDOSTREAM, 0.001);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moKnowledgeBaseHandler = new clsInformationRepresentationManagement(pre + P_KNOWLEDGEABASE, poProp);
		moPsyApp = new clsPsychicApparatus(pre + P_PSYCHICAPPARATUS, poProp, moKnowledgeBaseHandler);

		mrLibidostream = poProp.getPropertyDouble(pre+P_LIBIDOSTREAM);
	}
		
	@Override
	public void applySensorData(clsSensorData poData) {
		moPsyApp.moE39_SeekingSystem_LibidoSource.receive_I0_1( mrLibidostream );
		moPsyApp.moE39_SeekingSystem_LibidoSource.receive_I0_2( 0.0 );
		moPsyApp.moE01_SensorsMetabolism.receive_I0_3( separateHomeostaticData(poData) );
		moPsyApp.moE12_SensorsBody.receive_I0_5( separateBodyData(poData) );
		moPsyApp.moE10_SensorsEnvironment.receive_I0_4( separateEnvironmentalData(poData) );
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
		moPsyApp.moE32_Actuators.getOutput(poActionContainer);
	}
	
	@Override
	public void step() {
		//BODY --------------------------------------------- 
		//data preprocessing
		moPsyApp.moE01_SensorsMetabolism.step();
		moPsyApp.moE02_NeurosymbolizationOfNeeds.step();

		moPsyApp.moE10_SensorsEnvironment.step();
		moPsyApp.moE11_NeuroSymbolizationEnvironment.step();
		
		moPsyApp.moE12_SensorsBody.step();
		moPsyApp.moE13_NeuroSymbolizationBody.step();
		
		moPsyApp.moE39_SeekingSystem_LibidoSource.step();
		moPsyApp.moE40_NeurosymbolizationOfLibido.step();

		//PRIMARY PROCESSES -------------------------------
		//Self-PreservationDrive generation
		moPsyApp.moE03_GenerationOfSelfPreservationDrives.step();
		moPsyApp.moE04_FusionOfSelfPreservationDrives.step();
		moPsyApp.moE05_AccumulationOfAffectsForSelfPreservationDrives.step();
		moPsyApp.moE38_PrimalRepressionForSelfPreservationDrives.step();
		//Libido generation
		moPsyApp.moE41_Libidostasis.step();
		moPsyApp.moE43_SeparationIntoPartialSexualDrives.step();
		moPsyApp.moE42_AccumulationOfAffectsForSexualDrives.step();
		moPsyApp.moE44_PrimalRepressionForSexualDrives.step();
		//perception to memory and repression
		moPsyApp.moE14_ExternalPerception.step();
		moPsyApp.moE46_FusionWithMemoryTraces.step();
		moPsyApp.moE37_PrimalRepressionForPerception.step();
		moPsyApp.moE35_EmersionOfRepressedContent.step();
		moPsyApp.moE45_LibidoDischarge.step();
		moPsyApp.moE18_CompositionOfAffectsForPerception.step();
		//super-ego
		moPsyApp.moE07_InternalizedRulesHandler.step();

		//defense mechanisms
		moPsyApp.moE09_KnowledgeAboutReality_unconscious.step();
		moPsyApp.moE06_DefenseMechanismsForDrives.step();
		moPsyApp.moE19_DefenseMechanismsForPerception.step();
		moPsyApp.moE36_RepressionHandler.step();

		//primary to secondary
		moPsyApp.moE08_ConversionToSecondaryProcessForDriveWishes.step();
		moPsyApp.moE21_ConversionToSecondaryProcessForPerception.step();
		moPsyApp.moE20_InnerPerception_Affects.step();

		//SECONDARY PROCESSES ----------------------------
		//super-ego
		moPsyApp.moE22_SocialRulesSelection.step();

		//external perception
		moPsyApp.moE23_ExternalPerception_focused.step();
		moPsyApp.moE25_KnowledgeAboutReality_1.step();
		moPsyApp.moE24_RealityCheck_1.step();
		
		//decision making
		moPsyApp.moE26_DecisionMaking.step();
		moPsyApp.moE28_KnowledgeBase_StoredScenarios.step();
		moPsyApp.moE27_GenerationOfImaginaryActions.step();
		
		//fantasy
		moPsyApp.moE47_ConversionToPrimaryProcess.step();
		
		//evaluation and pre-execution
		moPsyApp.moE34_KnowledgeAboutReality_2.step();
		moPsyApp.moE33_RealityCheck_2.step();
		moPsyApp.moE29_EvaluationOfImaginaryActions.step();
		moPsyApp.moE30_MotilityControl.step();
		
		//BODY --------------------------------------------- 
		//execution
		moPsyApp.moE31_NeuroDeSymbolizationActionCommands.step();
		moPsyApp.moE32_Actuators.step();
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 13.08.2009, 00:08:10
	 *
	 * @return
	 */
	public clsPsychicApparatus getPsychicApparatus() {
		return moPsyApp;
	}
}