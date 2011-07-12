/**
 * CHANGELOG
 * 
 * 2011/07/12 TD - added javadoc comments. code sanitation.
 */
package pa._v38;

import java.util.HashMap;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSensorExtern;
import pa.itfProcessor;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.informationrepresentation.clsInformationRepresentationManagement;
import pa._v38.modules.clsPsychicApparatus;

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
		
	public clsProcessor(String poPrefix, clsBWProperties poProp, int uid) {
		applyProperties(poPrefix, poProp, uid);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsInformationRepresentationManagement.getDefaultProperties(pre+P_KNOWLEDGEABASE) );
		oProp.putAll( clsPsychicApparatus.getDefaultProperties(pre+P_PSYCHICAPPARATUS) );
				
		oProp.setProperty( pre+P_LIBIDOSTREAM, 0.1);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp, int uid) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moKnowledgeBaseHandler = new clsInformationRepresentationManagement(pre + P_KNOWLEDGEABASE, poProp);
		moPsyApp = new clsPsychicApparatus(pre + P_PSYCHICAPPARATUS, poProp, moKnowledgeBaseHandler, uid);

		mrLibidostream = poProp.getPropertyDouble(pre+P_LIBIDOSTREAM);
	}
		
	@Override
	public void applySensorData(clsSensorData poData) {
		moPsyApp.moF39_SeekingSystem_LibidoSource.receive_I0_1( mrLibidostream );
		moPsyApp.moF39_SeekingSystem_LibidoSource.receive_I0_2( 0.0 );
		moPsyApp.moF01_SensorsMetabolism.receive_I0_3( separateHomeostaticData(poData) );
		moPsyApp.moF12_SensorsBody.receive_I0_5( separateBodyData(poData) );
		moPsyApp.moF10_SensorsEnvironment.receive_I0_4( separateEnvironmentalData(poData) );
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
		oResult.put(eSensorIntType.INTESTINEPRESSURE, poData.getSensorInt(eSensorIntType.INTESTINEPRESSURE));
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
		moPsyApp.moF32_Actuators.getOutput(poActionContainer);
	}
	
	@Override
	public void step() {
		//BODY --------------------------------------------- 
		//data preprocessing
		moPsyApp.moF01_SensorsMetabolism.step();
		moPsyApp.moF02_NeurosymbolizationOfNeeds.step();

		moPsyApp.moF10_SensorsEnvironment.step();
		moPsyApp.moF11_NeuroSymbolizationEnvironment.step();
		
		moPsyApp.moF12_SensorsBody.step();
		moPsyApp.moF13_NeuroSymbolizationBody.step();
		
		moPsyApp.moF39_SeekingSystem_LibidoSource.step();
		moPsyApp.moF40_NeurosymbolizationOfLibido.step();

		//PRIMARY PROCESSES -------------------------------
		//Self-PreservationDrive generation
		moPsyApp.moF03_GenerationOfSelfPreservationDrives.step();
		moPsyApp.moF04_FusionOfSelfPreservationDrives.step();
		
		//Libido generation
		moPsyApp.moF41_Libidostasis.step();
		moPsyApp.moF43_SeparationIntoPartialSexualDrives.step();
		
		//Accumulation of affects and drives
		moPsyApp.moF48_AccumulationOfAffectsForDrives.step(); 
				
		//perception to memory and repression
		moPsyApp.moF14_ExternalPerception.step();
		moPsyApp.moF46_FusionWithMemoryTraces.step();
		moPsyApp.moF37_PrimalRepressionForPerception.step();
		
		//Repression for drives
		moPsyApp.moF57_MemoryTracesForDrives.step(); 
		moPsyApp.moF49_PrimalRepressionForDrives.step();
		moPsyApp.moF54_EmersionOfBlockedDriveContent.step(); 
		moPsyApp.moF56_Desexualization_Neutralization.step();
				
		//Emersion of blocked content
		moPsyApp.moF35_EmersionOfBlockedContent.step();
		moPsyApp.moF45_LibidoDischarge.step();
		moPsyApp.moF18_CompositionOfAffectsForPerception.step();

		//super-ego
		moPsyApp.moF55_SuperEgoProactive.step(); 
		moPsyApp.moF07_SuperEgoReactive.step(); 

		//defense mechanisms
		moPsyApp.moF06_DefenseMechanismsForDrives.step();
		moPsyApp.moF19_DefenseMechanismsForPerception.step();

		//primary to secondary
		moPsyApp.moF08_ConversionToSecondaryProcessForDriveWishes.step();
		moPsyApp.moF21_ConversionToSecondaryProcessForPerception.step();
		moPsyApp.moF20_InnerPerception_Affects.step();

		//SECONDARY PROCESSES ----------------------------
		
		//external perception
		moPsyApp.moF23_ExternalPerception_focused.step();
		moPsyApp.moF51_RealityCheckWishFulfillment.step();
		
		//decision making
		moPsyApp.moF26_DecisionMaking.step();
		moPsyApp.moF52_GenerationOfImaginaryActions.step(); 
			
		//fantasy
		moPsyApp.moF47_ConversionToPrimaryProcess.step();
		
		//evaluation and pre-execution
		moPsyApp.moF53_RealityCheckActionPlanning.step(); 
		moPsyApp.moF29_EvaluationOfImaginaryActions.step();
		moPsyApp.moF30_MotilityControl.step();
		
		//BODY --------------------------------------------- 
		//execution
		moPsyApp.moF31_NeuroDeSymbolizationActionCommands.step();
		moPsyApp.moF32_Actuators.step();
		
		//UPDATE DataLogger Entries
		moPsyApp.moDataLogger.step();
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