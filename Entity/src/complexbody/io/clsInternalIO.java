/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io;

import java.util.HashMap;

import properties.clsProperties;

import complexbody.io.sensors.datatypes.enums.eSensorIntType;
import complexbody.io.sensors.internal.clsEnergyConsumptionSensor;
import complexbody.io.sensors.internal.clsEnergySensor;
import complexbody.io.sensors.internal.clsFastMessengerSensor;
import complexbody.io.sensors.internal.clsHealthSensor;
import complexbody.io.sensors.internal.clsIntestinePressureSensor;
import complexbody.io.sensors.internal.clsSensorInt;
import complexbody.io.sensors.internal.clsSlowMessengerSensor;
import complexbody.io.sensors.internal.clsStaminaSensor;
import complexbody.io.sensors.internal.clsStomachSensor;
import complexbody.io.sensors.internal.clsStomachTensionSensor;
import complexbody.io.sensors.internal.clsTemperatureSensor;

import body.clsBaseBody;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO extends clsBaseIO{
	public static final String P_NUMSENSORS = "numsensors";
	public static final String P_SENSORTYPE = "sensortype";
	public static final String P_SENSORACTIVE = "sensoractive";	
	
	public static final String P_ACTIONS = "actions";
	public static final String P_SENSORS = "sensors";
	public static final String P_ACTIONEX = "actionexecutor";
	public static final String P_ACTIONPROCESSOR = "actionprocessor";
	public static final String P_ACTIONAVAILABLE = "actionavailable";
	public static final String P_INTERNALACTIONEX = "internalactionexecutor";
	
    private clsInternalActionProcessor moInternalActionProcessor = null;

	public HashMap<eSensorIntType, clsSensorInt> moSensorInternal;

	public clsBaseBody moBody;
	
	public clsEntity moEntity;
	
	public clsInternalIO(String poPrefix, clsProperties poProp, clsBaseBody poBody, clsEntity poEntity) {
		super(poPrefix, poProp, poBody);
		
		moSensorInternal = new HashMap<eSensorIntType, clsSensorInt>();
		moBody = poBody;
		moInternalActionProcessor = new clsInternalActionProcessor(poPrefix, poProp, poEntity);
		moEntity = poEntity;
		
		applyProperties(poPrefix, poProp, poBody);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll(getDefaultSensorProperties(pre));//+P_SENSORS));
		oProp.putAll(getDefaultActionProperties(pre));//+P_ACTIONS));
		
		return oProp;
	}

	public static clsProperties getDefaultSensorProperties(String poPrefix) {
		String pre = poPrefix;//clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		
		int i=0;
		oProp.putAll( clsEnergyConsumptionSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.ENERGY_CONSUMPTION.name());
		i++;
				
		oProp.putAll( clsHealthSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.HEALTH.name());
		i++;
		
		oProp.putAll( clsStaminaSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.STAMINA.name());
		i++;
				
		oProp.putAll( clsStomachSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.STOMACH.name());
		i++;
		
		oProp.putAll( clsHealthSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.TEMPERATURE.name());
		i++;
		
		oProp.putAll( clsFastMessengerSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.FASTMESSENGER.name());
		i++;		
				
		oProp.putAll( clsEnergySensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.ENERGY.name());
		i++;		
		
		oProp.putAll( clsStomachTensionSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.STOMACHTENSION.name());
		i++;		

		oProp.putAll( clsSlowMessengerSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.SLOWMESSENGER.name());
		i++;		

		oProp.putAll( clsIntestinePressureSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.INTESTINEPRESSURE.name());
		i++;		
		
		oProp.setProperty(pre+P_NUMSENSORS, i);
		
		return oProp;
	}	
	
	
	public static clsProperties getDefaultActionProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( clsActionProcessor.getDefaultProperties( pre+P_ACTIONPROCESSOR) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_EMOTIONAL_STRESS_SWEAT,1);
		oProp.putAll( clsExecutorInternalEmotionalStressSweat.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_EMOTIONAL_STRESS_SWEAT) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_GENERAL_SWEAT,1);
		oProp.putAll( clsExecutorInternalGeneralSweat.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_GENERAL_SWEAT) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_TENSE_MUSCLES,1);
		oProp.putAll( clsExecutorInternalTenseMuscles.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_TENSE_MUSCLES) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_HEART_PUMP,1);
		oProp.putAll( clsExecutorInternalHeartPumpSpeed.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_HEART_PUMP) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_MOUTH,1);
		oProp.putAll( clsExecutorInternalFacialChangeMouth.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_MOUTH) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYEBROWS,1);
		oProp.putAll( clsExecutorInternalFacialChangeEyeBrows.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYEBROWS) );

		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYES,1);
		oProp.putAll( clsExecutorInternalFacialChangeEyes.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYES) );
		
		
		// setProperty's from the ComplexBody's code parts
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,1);
		oProp.putAll( clsExecutorSpeechInvite.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,1);
		oProp.putAll( clsExecutorInternalTurnVision.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL) );
		
		oProp.setProperty(pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,1);
		oProp.putAll( clsExecutorSpeechShare.getDefaultProperties( pre+P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL) );
		
		return oProp;
	}
	
	

	private void applyProperties(String poPrefix, clsProperties poProp, clsBaseBody poBody) {
		String pre = clsProperties.addDot(poPrefix);

		int num = poProp.getPropertyInt(pre+P_NUMSENSORS);
		for (int i=0; i<num; i++) {
			String tmp_pre = pre+i+".";
			
			boolean nActive = poProp.getPropertyBoolean(tmp_pre+P_SENSORACTIVE);
			if (nActive) {
				String oType = poProp.getPropertyString(tmp_pre+P_SENSORTYPE);
				eSensorIntType eType = eSensorIntType.valueOf(oType);
				
				switch(eType) {
					case ENERGY_CONSUMPTION:
						moSensorInternal.put(eType, new clsEnergyConsumptionSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case HEALTH:
						moSensorInternal.put(eType, new clsHealthSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case STAMINA:
						moSensorInternal.put(eType, new clsStaminaSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case INTESTINEPRESSURE:
						moSensorInternal.put(eType, new clsIntestinePressureSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case STOMACH:
						moSensorInternal.put(eType, new clsStomachSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case TEMPERATURE:
						moSensorInternal.put(eType, new clsTemperatureSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case FASTMESSENGER:
						moSensorInternal.put(eType, new clsFastMessengerSensor(tmp_pre, poProp, this, poBody));
						break;
					case ENERGY:
						moSensorInternal.put(eType, new clsEnergySensor(tmp_pre, poProp, this, poBody));
						break;		
					case STOMACHTENSION:
						moSensorInternal.put(eType, new clsStomachTensionSensor(tmp_pre, poProp, this, poBody));
						break;
					case SLOWMESSENGER:
						moSensorInternal.put(eType, new clsSlowMessengerSensor(tmp_pre, poProp, this, poBody));
						break;
						
					default:
						throw new java.lang.NoSuchMethodError(eType.toString());
				}
			}
		} // end for
		
		// Register Internal Action Executors
		//moInternalActionProcessor.addCommand(clsInternalActionEmotionalStressSweat.class, new clsExecutorInternalEmotionalStressSweat(poPrefix+"." + bw.utils.enums.eBodyParts.ACTIONINT_EMOTIONAL_STRESS_SWEAT, poProp, moEntity));
		
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_EMOTIONAL_STRESS_SWEAT) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionEmotionalStressSweat.class, new clsExecutorInternalEmotionalStressSweat(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_EMOTIONAL_STRESS_SWEAT, poProp, moEntity));
		}
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_GENERAL_SWEAT) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionGeneralSweat.class, new clsExecutorInternalGeneralSweat(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_GENERAL_SWEAT, poProp, moEntity));
		}
		
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_HEART_PUMP) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionFasterHeartPump.class, new clsExecutorInternalHeartPumpSpeed(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_HEART_PUMP, poProp, moEntity)); 
		}
		
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_TENSE_MUSCLES) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionTenseMuscles.class, new clsExecutorInternalTenseMuscles(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_TENSE_MUSCLES, poProp, moEntity));
		}

		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_MOUTH) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionFacialChangeMouth.class, new clsExecutorInternalFacialChangeMouth(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_MOUTH, poProp, moEntity));
		}

		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYEBROWS) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionFacialChangeEyeBrows.class, new clsExecutorInternalFacialChangeEyeBrows(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYEBROWS, poProp, moEntity));
		}
			
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYES) == 1) {
			moInternalActionProcessor.addCommand(clsInternalActionFacialChangeEyes.class, new clsExecutorInternalFacialChangeEyes(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONINT_FACIAL_CHANGE_EYES, poProp, moEntity));
		}

		
		/* Last Properties are taken from the ComplexBody's code below */
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL)==1) {
			moInternalActionProcessor.addCommand(clsActionSpeechInvited.class, new clsExecutorSpeechInvite(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL, poProp, moEntity));
		}
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL)==1) {
			moInternalActionProcessor.addCommand(clsInternalActionTurnVision.class, new clsExecutorInternalTurnVision(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL, poProp, moEntity));
		}
		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE +"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL)==1) {
			moInternalActionProcessor.addCommand(clsActionShare.class, new clsExecutorSpeechShare(poPrefix+"." + 
					P_INTERNALACTIONEX	+ "." + bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL, poProp, moEntity));
		}
		
		
		
		
		/*  CODE BELOW IS TAKEN FROM ComplexBody.applyInternalActionProperties, WHICH IS NO LONGER NEEDED.  */
		
		//TODO AddInternalActions
		
/*		moInternalActionProcessor.addCommand(clsInternalActionEmotionalStressSweat.class, 
			new clsExecutorInternalEmotionalStressSweat(poPrefix+"." + P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,poProp, moEntity));
		
		moInternalActionProcessor.addCommand(clsActionSpeechInvited.class, 
				new clsExecutorSpeechInvite(poPrefix+"." + P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,poProp, moEntity));
		
		moInternalActionProcessor.addCommand(clsActionShare.class, 
				new clsExecutorSpeechShare(poPrefix+"." + P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,poProp, moEntity));

		moInternalActionProcessor.addCommand(clsInternalActionTurnVision.class, 
								new clsExecutorInternalTurnVision(poPrefix+"." + P_INTERNALACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INTERNAL,poProp, moEntity));
		*/
		
//		//Register actionexecutors
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_DROP)==1) moProcessor.addCommand(clsActionMove.class, new clsExecutorMove(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_MOVE,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TURN)==1) moProcessor.addCommand(clsActionTurn.class, new clsExecutorTurn(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TURN,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EAT)==1) moProcessor.addCommand(clsActionEat.class, new clsExecutorEat(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EAT,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INNERSPEECH)==1) moProcessor.addCommand(clsActionInnerSpeech.class, new clsExecutorMoveToArea(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_INNERSPEECH,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKBITE)==1) moProcessor.addCommand(clsActionAttackBite.class, new clsExecutorAttackBite(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKBITE,poProp,moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_PICKUP)==1) moProcessor.addCommand(clsActionPickUp.class, new clsExecutorPickUp(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_PICKUP,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_DROP)==1) moProcessor.addCommand(clsActionDrop.class, new clsExecutorDrop(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_DROP,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FROMINVENTORY)==1) moProcessor.addCommand(clsActionFromInventory.class, new clsExecutorFromInventory(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FROMINVENTORY,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TOINVENTORY)==1) moProcessor.addCommand(clsActionToInventory.class, new clsExecutorToInventory(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_TOINVENTORY,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_CULTIVATE)==1) moProcessor.addCommand(clsActionCultivate.class, new clsExecutorCultivate(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_CULTIVATE,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_KISS)==1) moProcessor.addCommand(clsActionKiss.class, new clsExecutorKiss(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_KISS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING)==1) moProcessor.addCommand(clsActionAttackLightning.class, new clsExecutorAttackLightning(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_ATTACKLIGHTNING,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_MOVETOAREA)==1) moProcessor.addCommand(clsActionMoveToEatableArea.class, new clsExecutorMoveToArea(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_MOVETOAREA,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT)==1) moProcessor.addCommand(clsActionExcrement.class, new clsExecutorExcrement(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT,poProp,(clsMobile) moEntity));
//
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR)==1) moProcessor.addCommand(clsActionBodyColor.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORRED)==1) moProcessor.addCommand(clsActionBodyColorRed.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORGREEN)==1) moProcessor.addCommand(clsActionBodyColorGreen.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR + P_ACTIONEX_BODYCOLORBLUE)==1) moProcessor.addCommand(clsActionBodyColorBlue.class, new clsExecutorBodyColor(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_BODYCOLOR,poProp,(clsMobile) moEntity));
//
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSHAPE)==1) moProcessor.addCommand(clsActionFacialExLensShape.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LENSSIZE)==1) moProcessor.addCommand(clsActionFacialExLensSize.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_EYESIZE)==1) moProcessor.addCommand(clsActionFacialExEyeSize.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_LEFTANT)==1) moProcessor.addCommand(clsActionFacialExLeftAntennaPosition.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS + P_ACTIONEX_FEX_RIGHTANT)==1) moProcessor.addCommand(clsActionFacialExRightAntennaPosition.class, new clsExecutorFacialExpressions(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS,poProp,(clsMobile) moEntity));
//
//		//TODO: Add itfAPSleep - objects to inform when sleeping!		
//		if (poProp.getPropertyInt( pre+P_ACTIONAVAILABLE	+"."+bw.utils.enums.eBodyParts.ACTIONEX_SLEEP)==1) {
//			ArrayList<itfAPSleep> oNotifyListLight = new ArrayList<itfAPSleep>();
//			ArrayList<itfAPSleep> oNotifyListDeep = new ArrayList<itfAPSleep>();
//			//oNotifyListLight.add(xxxxx);
//			//oNotifyListDeep.add(xxxxx);			
//			moProcessor.addCommand(clsActionSleep.class, new clsExecutorSleep(poPrefix+"." + P_ACTIONEX	+"."+bw.utils.enums.eBodyParts.ACTIONEX_EXCREMENT,poProp,(clsMobile) moEntity,oNotifyListLight,oNotifyListDeep));
//		}
		
		
		
	} // end applyProperties
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	@Override
	public void stepSensing() {
		for (clsSensorInt sensor : moSensorInternal.values()) {
			sensor.updateSensorData();
		}		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.07.2009, 23:19:16
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	@Override
	public void stepExecution() {
		// nothing to do
		moInternalActionProcessor.dispatch();
	}


	public clsInternalActionProcessor getInternalActionProcessor() {
		return moInternalActionProcessor;
	}
	
	

}
