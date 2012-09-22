/**
 * CHANGELOG
 * 
 * 2011/07/12 TD - added javadoc comments. code sanitation.
 */
package pa._v38;

import java.util.HashMap;

import org.apache.log4j.*;

import config.clsProperties;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
import du.itf.sensors.clsSensorExtern;
import pa.itfProcessor;
import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.informationrepresentation.clsInformationRepresentationManagement;
import pa._v38.modules.clsPsychicApparatus;

/**
 * The clsProcessor of the implemenation v38 is responsible for feeding the sensor data into the decision unit, calling all modules to process this data
 * in the correct order, and to extract the produced action command from the decision unit. The modules themselves are created within the class clsPsychicApparatus.
 * Further the knowledgebasehandler is created in this class.
 * 
 * @see pa._v38.modules.clsPsychicApparatus
 * 
 * @author deutsch
 * 12.08.2009, 09:30:54
 * 
 */
public class clsProcessor implements itfProcessor  {
	/** prefix to the properties file for all entries related to the psychic apparatus; @since 12.07.2011 10:58:05 */
	public static final String P_PSYCHICAPPARATUS = "psychicapparatus";
	/** prefix to the properties file for all entries related to the knowledgebase; @since 12.07.2011 10:58:32 */
	public static final String P_KNOWLEDGEABASE = "knowledgebase";
	/** the value entry that defines the constantly produced libido stream. used for the properties file; @since 12.07.2011 10:59:01 */
	public static final String P_LIBIDOSTREAM = "libidostream";
	
	/** the private instance of the psychic apparatus; @since 12.07.2011 11:00:15 */
	private clsPsychicApparatus moPsyApp;
	/** the private instance of the knowledgebasehandeler/memory; @since 12.07.2011 11:00:30 */
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	/** the rate of the constantly produced libido; @since 12.07.2011 11:00:52 */
	private double mrLibidostream;
		
	/**
	 * Creates an instance of the processor and thus the decision unit with the provided properties.
	 *
	 * @since 12.07.2011 11:01:51
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param uid A unique identifier. It is the same for the decision unit and the body for one agent.
	 */
	public clsProcessor(String poPrefix, clsProperties poProp, int uid) {
		applyProperties(poPrefix, poProp, uid);
		
		// --- Set logger properties --- //
		clsLogger.jlog.removeAllAppenders();
		clsLogger.jlog.setLevel(Level.ALL);
		//Layout layout = new PatternLayout("%p [%t] %c (%F:%L) - %m%n");
		Layout layout = new PatternLayout("(%F:%L) - %m%n");
		clsLogger.jlog.addAppender(new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT));
	}
	
	/**
	 * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
	 *
	 * @since 12.07.2011 11:02:53
	 *
	 * @param poPrefix Prefix for the entries in the property file.
	 * @return An instance of clsProperties with the added default properties.
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsInformationRepresentationManagement.getDefaultProperties(pre+P_KNOWLEDGEABASE) );
		oProp.putAll( clsPsychicApparatus.getDefaultProperties(pre+P_PSYCHICAPPARATUS) );
				
		oProp.setProperty( pre+P_LIBIDOSTREAM, 0.1);
		
		return oProp;
	}	
	
	/**
	 * Applies the provided properties to the class and creates the knowledgebasehandler and the psychicapparatus.
	 *
	 * @since 12.07.2011 11:04:27
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param uid A unique identifier. It is the same for the decision unit and the body for one agent.
	 */
	private void applyProperties(String poPrefix, clsProperties poProp, int uid) {
		String pre = clsProperties.addDot(poPrefix);
	
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
	 * Extracts the homeostatic data from the whole set of sensor data. Necessary to create the input for F1.
	 *
	 * @author langr
	 * 12.08.2009, 20:42:17
	 *
	 * @param poData The sensor data collected by the various sources of the body.
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
	 * Extracts the external sensor data (more or less the five senses) from the rest of the sensor data. Necessary to create the input for F10.
	 *
	 * @author langr
	 * 12.08.2009, 20:41:51
	 *
	 * @param poData The sensor data collected by the various sources of the body.
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
	 * Extract the internal sensor data (like pain or position of the elbow) from the rest of the sensor data. Necessary to create the input for F12.
	 *
	 * @author langr
	 * 12.08.2009, 20:41:48
	 *
	 * @param poData The sensor data collected by the various sources of the body.
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
		moPsyApp.moF48_AccumulationOfQuotaOfAffectsForDrives.step(); 
		moPsyApp.moF57_MemoryTracesForDrives.step(); 
				
		//perception to memory and repression
		moPsyApp.moF14_ExternalPerception.step();
		moPsyApp.moF46_MemoryTracesForPerception.step();
		moPsyApp.moF37_PrimalRepressionForPerception.step();
		
		//Repression for drives
		
		moPsyApp.moF49_PrimalRepressionForDrives.step();
		moPsyApp.moF54_EmersionOfBlockedDriveContent.step(); 
		
				
		//Emersion of blocked content
		moPsyApp.moF35_EmersionOfBlockedContent.step();
		moPsyApp.moF45_LibidoDischarge.step();
		moPsyApp.moF18_CompositionOfAffectsForPerception.step();
		
		//desexualization and emotions
		moPsyApp.moF63_CompositionOfEmotions.step();
		moPsyApp.moF56_Desexualization_Neutralization.step();

		//super-ego
		moPsyApp.moF55_SuperEgoProactive.step(); 
		moPsyApp.moF07_SuperEgoReactive.step(); 

		//defense mechanisms
		moPsyApp.moF06_DefenseMechanismsForDrives.step();
		moPsyApp.moF19_DefenseMechanismsForPerception.step();

		//primary to secondary
		moPsyApp.moF08_ConversionToSecondaryProcessForDriveWishes.step();
		moPsyApp.moF21_ConversionToSecondaryProcessForPerception.step();
		moPsyApp.moF20_CompositionOfFeelings.step();

		//SECONDARY PROCESSES ----------------------------
		
		//localization
		moPsyApp.moF61_Localization.step();
		
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
	 * Getter method for the psychic apparatus. Needed primarily by the various inspectors.
	 *
	 * @author langr
	 * 13.08.2009, 00:08:10
	 *
	 * @return An instance of clsPsychicApparatus.
	 */
	public clsPsychicApparatus getPsychicApparatus() {
		return moPsyApp;
	}
}