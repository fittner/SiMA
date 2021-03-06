/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io;

import java.util.ArrayList;

import body.clsBaseBody;
import properties.clsProperties;
import complexbody.io.actuators.clsActionProcessor;
import complexbody.io.actuators.actionCommands.clsActionAgree;
//import complexbody.io.actuators.actionCommands.clsActionAgree;
//import complexbody.io.actuators.actionCommands.clsActionAgree;
import complexbody.io.actuators.actionCommands.clsActionAttackBite;
import complexbody.io.actuators.actionCommands.clsActionAttackLightning;
import complexbody.io.actuators.actionCommands.clsActionBeat;
import complexbody.io.actuators.actionCommands.clsActionBodyColor;
import complexbody.io.actuators.actionCommands.clsActionBodyColorBlue;
import complexbody.io.actuators.actionCommands.clsActionBodyColorGreen;
import complexbody.io.actuators.actionCommands.clsActionBodyColorRed;
import complexbody.io.actuators.actionCommands.clsActionCultivate;
import complexbody.io.actuators.actionCommands.clsActionDivide;
import complexbody.io.actuators.actionCommands.clsActionDrop;
import complexbody.io.actuators.actionCommands.clsActionEat;
import complexbody.io.actuators.actionCommands.clsActionExcrement;
import complexbody.io.actuators.actionCommands.clsActionFacialExEyeSize;
import complexbody.io.actuators.actionCommands.clsActionFacialExLeftAntennaPosition;
import complexbody.io.actuators.actionCommands.clsActionFacialExLensShape;
import complexbody.io.actuators.actionCommands.clsActionFacialExLensSize;
import complexbody.io.actuators.actionCommands.clsActionFacialExRightAntennaPosition;
import complexbody.io.actuators.actionCommands.clsActionFromInventory;
import complexbody.io.actuators.actionCommands.clsActionKiss;
import complexbody.io.actuators.actionCommands.clsActionMove;
import complexbody.io.actuators.actionCommands.clsActionMoveToEatableArea;
import complexbody.io.actuators.actionCommands.clsActionObjectTransfer;
//import complexbody.io.actuators.actionCommands.clsActionObjectTransfer;
import complexbody.io.actuators.actionCommands.clsActionPickUp;
import complexbody.io.actuators.actionCommands.clsActionRequest;
//import complexbody.io.actuators.actionCommands.clsActionRequest;
import complexbody.io.actuators.actionCommands.clsActionSleep;
import complexbody.io.actuators.actionCommands.clsActionSpeech;
import complexbody.io.actuators.actionCommands.clsActionToInventory;
import complexbody.io.actuators.actionCommands.clsActionTurn;
import complexbody.io.actuators.actionCommands.clsActionTurnVision;
import complexbody.io.actuators.actionCommands.clsActionWait;
import complexbody.io.actuators.actionExecutors.clsExecutorAgree;
//import complexbody.io.actuators.actionExecutors.clsExecutorAgree;
import complexbody.io.actuators.actionExecutors.clsExecutorAttackBite;
import complexbody.io.actuators.actionExecutors.clsExecutorAttackLightning;
import complexbody.io.actuators.actionExecutors.clsExecutorBeat;
import complexbody.io.actuators.actionExecutors.clsExecutorBodyColor;
import complexbody.io.actuators.actionExecutors.clsExecutorCultivate;
import complexbody.io.actuators.actionExecutors.clsExecutorDivide;
import complexbody.io.actuators.actionExecutors.clsExecutorDrop;
import complexbody.io.actuators.actionExecutors.clsExecutorEat;
import complexbody.io.actuators.actionExecutors.clsExecutorExcrement;
import complexbody.io.actuators.actionExecutors.clsExecutorFacialExpressions;
import complexbody.io.actuators.actionExecutors.clsExecutorFromInventory;
import complexbody.io.actuators.actionExecutors.clsExecutorKiss;
import complexbody.io.actuators.actionExecutors.clsExecutorMove;
import complexbody.io.actuators.actionExecutors.clsExecutorMoveToArea;
import complexbody.io.actuators.actionExecutors.clsExecutorObjectTransfer;
//import complexbody.io.actuators.actionExecutors.clsExecutorObjectTransfer;
import complexbody.io.actuators.actionExecutors.clsExecutorPickUp;
import complexbody.io.actuators.actionExecutors.clsExecutorRequest;
//import complexbody.io.actuators.actionExecutors.clsExecutorRequest;
import complexbody.io.actuators.actionExecutors.clsExecutorSleep;
import complexbody.io.actuators.actionExecutors.clsExecutorSpeech;
import complexbody.io.actuators.actionExecutors.clsExecutorSpeechExpression;
import complexbody.io.actuators.actionExecutors.clsExecutorToInventory;
import complexbody.io.actuators.actionExecutors.clsExecutorTurn;
import complexbody.io.actuators.actionExecutors.clsExecutorTurnVision;
import complexbody.io.actuators.actionExecutors.clsExecutorWait;
import complexbody.io.sensors.datatypes.enums.eSensorExtType;
import complexbody.io.sensors.external.clsSensorAcceleration;
import complexbody.io.sensors.external.clsSensorBump;
import complexbody.io.sensors.external.clsSensorCarriedItems;
import complexbody.io.sensors.external.clsSensorEatableArea;
import complexbody.io.sensors.external.clsSensorEngine;
import complexbody.io.sensors.external.clsSensorExt;
import complexbody.io.sensors.external.clsSensorManipulateArea;
import complexbody.io.sensors.external.clsSensorOlfactoric;
import complexbody.io.sensors.external.clsSensorPositionChange;
import complexbody.io.sensors.external.clsSensorRadiation;
import complexbody.io.sensors.external.clsSensorVision;
import entities.abstractEntities.clsEntity;
import entities.abstractEntities.clsMobile;
import entities.actionProxies.itfAPSleep;

/**
 * TODO (langr) - THIS CLASS NEEDS A REFACTORING - reason: not every
 * entity has the same Sensors/Actuators
 * Possible Solution without refactor: derivate subclasses that override the ctor
 * 
 * This class holds one list for all sensor-instances and 
 *                  one list for all actuator instances
 * within the current entity.
 * 
 * The method step is automatically called from clsBody and ensures
 * the sensor update for all sensors during the sensing-cycle.
 * 
 * TODO (langr) - the same should be done with the actuators:
 * The method @@@ is automatically called from clsBody and ensures
 * the actuator-execution for all actuators during the execution-cycle.
 * 
 * BD - adapted the class to host the actionprocessor
 * 
 * @author langr
 * 
 */
public class clsExternalIO extends clsBaseIO {
	/*TODO: HZ 30.07.2009 The variable sensorvision is set to false if the old sensor system should be 
	 * used, otherwise it is set to true. This is only an interim solution due to 
	 * too less testing time. 
	 * */
	
	public static final String P_NUMSENSORS = "numsensors";
	public static final String P_SENSORTYPE = "sensortype";
	public static final String P_SENSORACTIVE = "sensoractive";
	public static final String P_SENSORRANGE = "sensorrange";
	public static final String P_SENSORENGINE = "sensorengine";
	public static final String P_ACTIONPROCESSOR = "actionprocessor";
	public static final String P_ACTIONAVAILABLE = "actionavailable";
	public static final String P_ACTIONEX = "actionexecutor";
	public static final String P_ACTIONEX_FEX_LENSSHAPE = "lensshape";
	public static final String P_ACTIONEX_FEX_LENSSIZE = "lenssize";
	public static final String P_ACTIONEX_FEX_EYESIZE = "eyesize";
	public static final String P_ACTIONEX_FEX_LEFTANT = "leftant";
	public static final String P_ACTIONEX_FEX_RIGHTANT = "rightant";
	public static final String P_ACTIONEX_BODYCOLORRED = "red";
	public static final String P_ACTIONEX_BODYCOLORGREEN = "green";
	public static final String P_ACTIONEX_BODYCOLORBLUE = "blue";
	public static final String P_ACTIONS = "actions";
	public static final String P_SENSORS = "sensors";

	public clsSensorEngine moSensorEngine; 
	private clsActionProcessor moProcessor; 
	//public HashMap<eSensorExtType, clsSensorExt> moSensorExternal;
	public clsEntity moEntity;

	public clsExternalIO(String poPrefix, clsProperties poProp, clsBaseBody poBody, clsEntity poEntity) {
		super(poPrefix, poProp, poBody);
		moEntity = poEntity; //the entity for physics engine access

		//moSensorExternal = new HashMap<eSensorExtType, clsSensorExt>();
		moProcessor = new clsActionProcessor(poPrefix,poProp,poEntity);
	
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultSensorProperties(String poPrefix, boolean pnThreeRangeVision, boolean pnThreeRangeAcoustics) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();		

		oProp.putAll( clsSensorEngine.getDefaultProperties(pre+P_SENSORENGINE) );
		oProp.setProperty(pre+P_SENSORRANGE, 0.0); // Default - changed later on

		int numsensors = 0;
				
		oProp.putAll( clsSensorAcceleration.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.ACCELERATION.name());
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, 0.0);
		numsensors++;
			
		oProp.putAll( clsSensorBump.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.BUMP.name());
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, 20.0);
		numsensors++;
				
		if (pnThreeRangeVision) {
			//add 3-range-vision
			oProp.putAll( clsSensorVision.getDefaultProperties( pre+numsensors) );
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORACTIVE, true);
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_NEAR.name());
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORRANGE, 20);
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 0 );
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );
			numsensors++;
	
			oProp.putAll( clsSensorVision.getDefaultProperties( pre+numsensors) );
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORACTIVE, true);
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_MEDIUM.name());
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORRANGE, 40 );
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 20 );
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );
			numsensors++;
	
			oProp.putAll( clsSensorVision.getDefaultProperties( pre+numsensors) );
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORACTIVE, true);
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_FAR.name());
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORRANGE, 60);
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 40 );
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );
			numsensors++;
			
			//Vision Self
			oProp.putAll( clsSensorVision.getDefaultProperties( pre+numsensors) );
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORACTIVE, true);
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_SELF.name());
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORRANGE, 0);
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 0 );
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );
			numsensors++;
			
			//Vision Carried Items
			oProp.putAll( clsSensorVision.getDefaultProperties( pre+numsensors) );
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORACTIVE, true);
			oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_CARRIED_ITEMS.name());
			numsensors++;
			
		} else {
			oProp.putAll( clsSensorVision.getDefaultProperties( pre+numsensors) );
			oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
			oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.VISION.name());
			oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, oProp.getProperty(pre+P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE));
			oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MAX_DISTANCE, oProp.getProperty(pre+P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE));
			numsensors++;
		}
		

		oProp.putAll( clsSensorRadiation.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.RADIATION.name());
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, oProp.getProperty(pre+P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE));
		numsensors++;
		
		oProp.putAll( clsSensorEatableArea.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.EATABLE_AREA.name());
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, 20.0 ); /*oProp.getPropertyDouble(pre+P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE)/
												  oProp.getPropertyInt(pre+P_SENSORENGINE+"."+clsSensorEngine.P_RANGEDIVISION));*/
		numsensors++;
		
		
		
		oProp.putAll( clsSensorPositionChange.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.POSITIONCHANGE.name());		
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, 0.0);
		numsensors++;

		oProp.putAll( clsSensorManipulateArea.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.MANIPULATE_AREA.name());
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, oProp.getPropertyDouble(pre+P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE)/
												  oProp.getPropertyInt(pre+P_SENSORENGINE+"."+clsSensorEngine.P_RANGEDIVISION));
		numsensors++;
		
/*		// ** MW 
		oProp.putAll( clsSensorAcoustic.getDefaultProperties( pre+numsensors) );
		oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.ACOUSTIC.name());
		oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, oProp.getPropertyDouble(pre+P_SENSORENGINE+"."+clsSensorEngine.P_MAX_RANGE)/
												  oProp.getPropertyInt(pre+P_SENSORENGINE+"."+clsSensorEngine.P_RANGEDIVISION));
		numsensors++;
		// MW **
*/	
		oProp.putAll( clsSensorOlfactoric.getDefaultProperties( pre+numsensors) );
        oProp.setProperty(pre+numsensors+"."+P_SENSORACTIVE, true);
        oProp.setProperty(pre+numsensors+"."+P_SENSORTYPE, eSensorExtType.OLFACTORIC.name());
        oProp.setProperty(pre+numsensors+"."+P_SENSORRANGE, 60.0 ); 
        oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORACTIVE, true);
        oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORTYPE, eSensorExtType.OLFACTORIC.name());
        oProp.setProperty(pre+numsensors+"."+clsExternalIO.P_SENSORRANGE, 60);
        oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 1 );
        oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, 2*Math.PI );
        oProp.setProperty(pre+numsensors+"."+clsSensorVision.P_SENSOR_MAX_DISTANCE, 60);
        numsensors++;
        
		oProp.setProperty(pre+P_NUMSENSORS, numsensors);
		
		return oProp;
	}
	
	public static clsProperties getDefaultActionProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsActionProcessor.getDefaultProperties( pre+P_ACTIONPROCESSOR) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_DROP,1);
		oProp.putAll( clsExecutorDrop.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_DROP) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_EAT,1);
		oProp.putAll( clsExecutorEat.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_EAT) );
	
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FROMINVENTORY,1);
		oProp.putAll( clsExecutorFromInventory.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FROMINVENTORY) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_TOINVENTORY,1);
		oProp.putAll( clsExecutorToInventory.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_TOINVENTORY) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKBITE,1);
		oProp.putAll( clsExecutorAttackBite.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKBITE) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_MOVE,1);
		oProp.putAll( clsExecutorMove.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_MOVE) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_PICKUP,1);
		oProp.putAll( clsExecutorPickUp.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_PICKUP) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_TURN,1);
		oProp.putAll( clsExecutorTurn.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_TURN) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_TURNVISION,1);
		oProp.putAll( clsExecutorTurnVision.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_TURNVISION) );
				
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_CULTIVATE,1);
		oProp.putAll( clsExecutorCultivate.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_CULTIVATE) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_KISS,1);
		oProp.putAll( clsExecutorKiss.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_KISS) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING,1);
		oProp.putAll( clsExecutorAttackLightning.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_MOVETOAREA,1);
		oProp.putAll( clsExecutorMoveToArea.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_MOVETOAREA) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_EXCREMENT,1);
		oProp.putAll( clsExecutorExcrement.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_EXCREMENT) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BEAT,1);
		oProp.putAll( clsExecutorBeat.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BEAT) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE   +"."+entities.enums.eBodyParts.ACTIONEX_DIVIDE,1);
        oProp.putAll( clsExecutorDivide.getDefaultProperties( pre+P_ACTIONEX  +"."+entities.enums.eBodyParts.ACTIONEX_DIVIDE) );

        oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_OUTERSPEECH,1);
		oProp.putAll( clsExecutorExcrement.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_OUTERSPEECH) );
		
		// ** MW
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_SPEECH,1);
		oProp.putAll( clsExecutorExcrement.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_SPEECH) );
		// MW **
	
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORRED,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORGREEN,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORBLUE,1);
		oProp.putAll( clsExecutorBodyColor.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSHAPE,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSIZE,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_EYESIZE,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LEFTANT,1);
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_RIGHTANT,1);
		oProp.putAll( clsExecutorFacialExpressions.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS) );

		oProp.setProperty(pre + P_ACTIONAVAILABLE	+"."+ entities.enums.eBodyParts.ACTIONEX_AGREE, 1);
		oProp.putAll(clsExecutorAgree.getDefaultProperties( pre + P_ACTIONEX	+"."+ entities.enums.eBodyParts.ACTIONEX_AGREE));
		
		oProp.setProperty(pre + P_ACTIONAVAILABLE	+"."+ entities.enums.eBodyParts.ACTIONEX_REQUEST, 1);
		oProp.putAll(clsExecutorRequest.getDefaultProperties( pre + P_ACTIONEX	+"."+ entities.enums.eBodyParts.ACTIONEX_REQUEST));
		
		oProp.setProperty(pre + P_ACTIONAVAILABLE	+"."+ entities.enums.eBodyParts.ACTIONEX_OBJECTTRANSFER, 1);
		oProp.putAll(clsExecutorObjectTransfer.getDefaultProperties( pre + P_ACTIONEX	+"."+ entities.enums.eBodyParts.ACTIONEX_OBJECTTRANSFER));
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_SLEEP,1);
		oProp.putAll( clsExecutorSleep.getDefaultProperties( pre+P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_SLEEP) );
		
		oProp.setProperty(pre + P_ACTIONAVAILABLE	+"."+ entities.enums.eBodyParts.ACTIONEX_WAIT, 1);
		oProp.putAll(clsExecutorRequest.getDefaultProperties( pre + P_ACTIONEX	+"."+ entities.enums.eBodyParts.ACTIONEX_WAIT));

		return oProp;		
	}
		
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll(getDefaultSensorProperties(pre+P_SENSORS, false, false));
		oProp.putAll(getDefaultActionProperties(pre+P_ACTIONS));
		
		return oProp;
	}
	
	private void applySensorProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moSensorEngine = new clsSensorEngine(pre+P_SENSORENGINE, poProp, this);
		clsSensorExt sensorExt = null; 
		
		int num = poProp.getPropertyInt(pre+P_NUMSENSORS);
		for (int i=0; i<num; i++) {
			String tmp_pre = pre+i+".";
			
			boolean nActive = poProp.getPropertyBoolean(tmp_pre+P_SENSORACTIVE);
			if (nActive) {
				eSensorExtType eType = eSensorExtType.valueOf(poProp.getPropertyString(tmp_pre+P_SENSORTYPE));
				
			
					/*TODO: HZ 28.07.2009 - This part serves as interim solution as long as all sensors are implied
					 * to the sensor engine. For now it has to be done, as the reference would
					 * miss in clsBrainSocket - if it will still be here in 6 months, beat up the
					 * author and/or remove it by yourself.
					 * The basic idea is to integrate the switch statement to the sensor engine or
					 * get rid of the Hashmap moSensorExternal
					 * */
					if(eType.name().equals(eSensorExtType.ACCELERATION.name())) sensorExt=new clsSensorAcceleration(tmp_pre, poProp, this); 
					if(eType.name().equals(eSensorExtType.BUMP.name()))sensorExt=new clsSensorBump(tmp_pre, poProp, this);
					if(eType.name().equals(eSensorExtType.VISION_NEAR.name()))sensorExt=new clsSensorVision(tmp_pre, poProp, this);
					if(eType.name().equals(eSensorExtType.VISION_MEDIUM.name()))sensorExt=new clsSensorVision(tmp_pre, poProp, this);
					if(eType.name().equals(eSensorExtType.VISION_FAR.name()))sensorExt=new clsSensorVision(tmp_pre, poProp, this);
					if(eType.name().equals(eSensorExtType.VISION.name()))sensorExt=new clsSensorVision(tmp_pre, poProp, this); 
					if(eType.name().equals(eSensorExtType.EATABLE_AREA.name()))sensorExt=new clsSensorEatableArea(tmp_pre, poProp, this); 
					if(eType.name().equals(eSensorExtType.MANIPULATE_AREA.name()))sensorExt=new clsSensorManipulateArea(tmp_pre, poProp, this); 

					if(eType.name().equals(eSensorExtType.VISION_CARRIED_ITEMS.name()))sensorExt=new clsSensorCarriedItems(tmp_pre, poProp, this,moEntity); 
										
					if(eType.name().equals(eSensorExtType.VISION_SELF.name()))sensorExt=new clsSensorVision(tmp_pre, poProp, this);
					
					
					moSensorEngine.registerSensor(eType, sensorExt);
			}
		}		
	}
	
	private void applyActionProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		//Register actionexecutors
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_OUTERSPEECH)==1) moProcessor.addCommand(clsActionSpeech.class, new clsExecutorSpeechExpression(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_OUTERSPEECH,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_SPEECH)==1) moProcessor.addCommand(clsActionSpeech.class, new clsExecutorSpeech(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_SPEECH,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_SPEECH)==1) moProcessor.addCommand(clsActionSpeech.class, new clsExecutorSpeech(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_SPEECH,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_MOVE)==1) moProcessor.addCommand(clsActionMove.class, new clsExecutorMove(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_MOVE,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_TURN)==1) moProcessor.addCommand(clsActionTurn.class, new clsExecutorTurn(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_TURN,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_EAT)==1) moProcessor.addCommand(clsActionEat.class, new clsExecutorEat(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_EAT,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKBITE)==1) moProcessor.addCommand(clsActionAttackBite.class, new clsExecutorAttackBite(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKBITE,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_PICKUP)==1) moProcessor.addCommand(clsActionPickUp.class, new clsExecutorPickUp(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_PICKUP,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_DROP)==1) moProcessor.addCommand(clsActionDrop.class, new clsExecutorDrop(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_DROP,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FROMINVENTORY)==1) moProcessor.addCommand(clsActionFromInventory.class, new clsExecutorFromInventory(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FROMINVENTORY,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_TOINVENTORY)==1) moProcessor.addCommand(clsActionToInventory.class, new clsExecutorToInventory(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_TOINVENTORY,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_CULTIVATE)==1) moProcessor.addCommand(clsActionCultivate.class, new clsExecutorCultivate(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_CULTIVATE,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_TURNVISION)==1) moProcessor.addCommand(clsActionTurnVision.class, new clsExecutorTurnVision(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_TURNVISION,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_KISS)==1) moProcessor.addCommand(clsActionKiss.class, new clsExecutorKiss(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_KISS,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING)==1) moProcessor.addCommand(clsActionAttackLightning.class, new clsExecutorAttackLightning(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_MOVETOAREA)==1) moProcessor.addCommand(clsActionMoveToEatableArea.class, new clsExecutorMoveToArea(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_MOVETOAREA,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_EXCREMENT)==1) moProcessor.addCommand(clsActionExcrement.class, new clsExecutorExcrement(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_EXCREMENT,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BEAT)==1) moProcessor.addCommand(clsActionBeat.class, new clsExecutorBeat(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BEAT,poProp,moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE    +"."+entities.enums.eBodyParts.ACTIONEX_DIVIDE)==1) moProcessor.addCommand(clsActionDivide.class, new clsExecutorDivide(poPrefix+"." + P_ACTIONEX +"."+entities.enums.eBodyParts.ACTIONEX_DIVIDE,poProp,moEntity));

		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR)==1) moProcessor.addCommand(clsActionBodyColor.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORRED)==1) moProcessor.addCommand(clsActionBodyColorRed.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORGREEN)==1) moProcessor.addCommand(clsActionBodyColorGreen.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORBLUE)==1) moProcessor.addCommand(clsActionBodyColorBlue.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));

		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSHAPE)==1) moProcessor.addCommand(clsActionFacialExLensShape.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSIZE)==1) moProcessor.addCommand(clsActionFacialExLensSize.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_EYESIZE)==1) moProcessor.addCommand(clsActionFacialExEyeSize.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LEFTANT)==1) moProcessor.addCommand(clsActionFacialExLeftAntennaPosition.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_RIGHTANT)==1) moProcessor.addCommand(clsActionFacialExRightAntennaPosition.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));

		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_SPEECH)==1) moProcessor.addCommand(clsActionSpeech.class, new clsExecutorSpeech(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_SPEECH,poProp,(clsMobile) moEntity)); // MW 
		
		if (poProp.getPropertyInt( pre + P_ACTIONAVAILABLE	+ "."+entities.enums.eBodyParts.ACTIONEX_REQUEST) == 1) moProcessor.addCommand(clsActionRequest.class, new clsExecutorRequest(poPrefix +"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_AGREE, poProp, (clsMobile) moEntity)); // MW 
		if (poProp.getPropertyInt( pre + P_ACTIONAVAILABLE	+ "."+entities.enums.eBodyParts.ACTIONEX_OBJECTTRANSFER) == 1) moProcessor.addCommand(clsActionObjectTransfer.class, new clsExecutorObjectTransfer(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_OBJECTTRANSFER, poProp, (clsMobile) moEntity));
		if (poProp.getPropertyInt( pre + P_ACTIONAVAILABLE	+ "."+entities.enums.eBodyParts.ACTIONEX_AGREE) == 1) moProcessor.addCommand(clsActionAgree.class, new clsExecutorAgree(poPrefix +"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_AGREE, poProp)); // MW 
		if (poProp.getPropertyInt( pre + P_ACTIONAVAILABLE	+ "."+entities.enums.eBodyParts.ACTIONEX_WAIT) == 1) moProcessor.addCommand(clsActionWait.class, new clsExecutorWait(poPrefix +"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_AGREE, poProp, (clsMobile) moEntity));
		
		//TODO: Add itfAPSleep - objects to inform when sleeping!		
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+entities.enums.eBodyParts.ACTIONEX_SLEEP)==1) {
			ArrayList<itfAPSleep> oNotifyListLight = new ArrayList<itfAPSleep>();
			ArrayList<itfAPSleep> oNotifyListDeep = new ArrayList<itfAPSleep>();
			//oNotifyListLight.add(xxxxx);
			//oNotifyListDeep.add(xxxxx);			
			moProcessor.addCommand(clsActionSleep.class, new clsExecutorSleep(poPrefix+"." + P_ACTIONEX	+"."+entities.enums.eBodyParts.ACTIONEX_EXCREMENT,poProp,(clsMobile) moEntity,oNotifyListLight,oNotifyListDeep));
		}		
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		applySensorProperties(pre+P_SENSORS, poProp);
		applyActionProperties(pre+P_ACTIONS, poProp);
	}	
	
	public clsActionProcessor getActionProcessor() {
		return moProcessor;
	}
	
	
	/* (non-Javadoc)
	 *
	 * each sensor has to be updated to guarantee valid data for the next cycle 'processing'
	 *
	 * @author langr
	 * 25.02.2009, 16:50:36
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		moSensorEngine.updateSensorData(); 
	}
	
	/* (non-Javadoc)
	 *
	 * each actuator must execute the commands originated from the processing-cycle within the 'brain',
	 * within the 'mind'
	 *
	 * @author langr
	 * 25.02.2009, 16:50:36
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	@Override
	public void stepExecution() {
		moProcessor.dispatch();
	}

}
