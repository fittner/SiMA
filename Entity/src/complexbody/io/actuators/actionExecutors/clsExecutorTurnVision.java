/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.*;
import complexbody.io.sensors.datatypes.enums.eActionTurnDirection;

import physics2D.physicalObject.sensors.clsEntitySensorEngine;
import properties.clsProperties;
import body.clsComplexBody;
import entities.abstractEntities.clsEntity;
/**
 * Action Executor for turning
 * Parameters:
 *    none 
 *
 * @author Muchitsch
 * 13.05.2009, 21:45:05
 * 
 */
public class clsExecutorTurnVision extends clsActionExecutor{

	static double srStaminaBase = 0.2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Angle) ; 			
	static double srStaminaScalingFactor = 0.00001f;   

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;
	
	public clsExecutorTurnVision(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);

		return oProp;
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = entities.enums.eBodyParts.ACTIONEX_TURNVISION;
	}
	@Override
	protected void setName() {
		moName="TurnVision executor";
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx;
	}
	
	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(clsActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(clsActionCommand poCommand) {
		clsActionTurnVision oCommand =(clsActionTurnVision) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,Math.abs( oCommand.getAngle())) ;
	}

	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionTurnVision oCommand = (clsActionTurnVision) poCommand;
		double mnAngle = oCommand.getAngle();
		//((clsMobile)moEntity).getMobileObject2D().
		
		clsComplexBody oCBody = (clsComplexBody) moEntity.getBody();
		
		//moEntity.setOverlayImage(eImages.Overlay_Action_TurnVision);
		
		//clsSensorExt oSensExt = oCBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(eSensorExtType.VISION_NEAR);
		
		if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT){
			mnAngle = (mnAngle/360*Math.PI*(-1.0));
    	}
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT){
    		mnAngle = (mnAngle/360*Math.PI);
    	}
				
		//bug somwhere, its null
		//Object testobj = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(0.0);
		
		clsEntitySensorEngine oEntityofSensor = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(20.0);
		oEntityofSensor.setFocusedOrientation(mnAngle);
		
		oEntityofSensor = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(40.0);
		oEntityofSensor.setFocusedOrientation(mnAngle);
		
		oEntityofSensor = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(60.0);
		oEntityofSensor.setFocusedOrientation(mnAngle);
		
		//clsSensorRingSegment oRS = (clsSensorRingSegment)oSensExt;
		
		

    	return true;
	}	
}
