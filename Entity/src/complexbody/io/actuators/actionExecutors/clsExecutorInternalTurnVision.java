/**
 * CHANGELOG
 *
 * 13.08.2013 muchitsch - File created
 *
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import complexbody.io.actuators.clsInternalActionExecutor;

import physics2D.physicalObject.sensors.clsEntitySensorEngine;
import properties.clsProperties;
import body.clsComplexBody;
import body.itfget.itfGetBody;

import du.enums.eActionTurnDirection;
import du.itf.actions.*;
import entities.abstractEntities.clsEntity;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 13.08.2013, 16:03:51
 * 
 */
public class clsExecutorInternalTurnVision extends clsInternalActionExecutor{

		static double srStaminaDemand = 0; //0.5f;		//Stamina demand 			
		private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
		private clsEntity moEntity;
		public static final String P_RANGESENSOR = "rangesensor";
		public static final String P_BIZESIZE = "bitesize";

		public clsExecutorInternalTurnVision(String poPrefix, clsProperties poProp, clsEntity poEntity) {
			super(poPrefix, poProp);
			
			moEntity=poEntity;
			
			moMutEx.add(clsActionMove.class);
			moMutEx.add(clsActionTurn.class);

			applyProperties(poPrefix,poProp);
		}

		public static clsProperties getDefaultProperties(String poPrefix) {
			String pre = clsProperties.addDot(poPrefix);
			clsProperties oProp = clsInternalActionExecutor.getDefaultProperties(pre);
			//oProp.setProperty(pre+P_RANGESENSOR, eSensorExtType.EATABLE_AREA.toString());
			//oProp.setProperty(pre+P_BIZESIZE, 3f); //0.3f
			
			return oProp;
		}
		
		private void applyProperties(String poPrefix, clsProperties poProp) {
			String pre = clsProperties.addDot(poPrefix);
			//moRangeSensor=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGESENSOR));
			//mrBiteSize=poProp.getPropertyFloat(pre+P_BIZESIZE);
		}
		
		/*
		 * Set values for SensorActuator base-class
		 */
		@Override
		protected void setBodyPartId() {
			mePartId = entities.enums.eBodyParts.ACTIONEX_EAT;
		}
		@Override
		protected void setName() {
			moName="Eat executor";	
		}

		/*
		 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
		 */
		@Override
		public ArrayList<Class<?>> getMutualExclusions(clsInternalActionCommand poCommand) {
			return moMutEx; 
		}
		
		/*
		 * Energy and stamina demand 
		 */
		@Override
		public double getEnergyDemand(clsInternalActionCommand poCommand) {
			return getStaminaDemand(poCommand)*srEnergyRelation;
		}
		@Override
		public double getStaminaDemand(clsInternalActionCommand poCommand) {
			return srStaminaDemand;
		}

		/*
		 * Executor 
		 */
		@Override
		public boolean execute(clsInternalActionCommand poCommand) {
			clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
			//moEntity.setFacialExpressionOverlayImage(eFacialExpression.SURPRISE);  //if wanted :)
			
			clsInternalActionTurnVision oCommand = (clsInternalActionTurnVision) poCommand;
			double mnAngle = oCommand.getAngle();
			
			clsComplexBody oCBody = (clsComplexBody) moEntity.getBody();
			
			if (oCommand.getVisionDirection()==eActionTurnDirection.TURN_LEFT){
				mnAngle = (mnAngle/360*Math.PI*(-1.0));
	    	}
	    	if (oCommand.getVisionDirection()==eActionTurnDirection.TURN_RIGHT){
	    		mnAngle = (mnAngle/360*Math.PI);
	    	}
			
			clsEntitySensorEngine oEntityofSensor = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(20.0);
			oEntityofSensor.setFocusedOrientation(mnAngle);
			
			oEntityofSensor = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(40.0);
			oEntityofSensor.setFocusedOrientation(mnAngle);
			
			oEntityofSensor = oCBody.getExternalIO().moSensorEngine.getMeSensorAreas().get(60.0);
			oEntityofSensor.setFocusedOrientation(mnAngle);

			return true;
		}	

}
