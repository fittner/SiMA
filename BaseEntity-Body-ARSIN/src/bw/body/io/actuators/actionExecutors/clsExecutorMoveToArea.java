/**
 * clsExecutorMoveToEatableArea.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 29.08.2009, 09:58:37
 */
package bw.body.io.actuators.actionExecutors;

import config.clsProperties;
import java.util.ArrayList;
import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.base.clsEntity;
import bw.entities.base.clsMobile;
import bw.body.io.actuators.actionProxies.*;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.external.clsSensorRingSegment;
import bw.body.itfget.itfGetBody;
import du.enums.eSensorExtType;
import du.itf.actions.*;

/**
 * Action Executor for moving objects from one area to another
 * Parameters: none
 *   mrForceScalingFactor = Relation of Force to energy  (Default=1)
 * 	 poRangeSource = Range to search for the object
 *   poRangeDest = Range to move the object to
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */

public class clsExecutorMoveToArea extends clsActionExecutor{

	static double srStaminaBase = 0.05f;			//Stamina demand 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private eSensorExtType moRangeDest;
	
	public static final String P_RANGEDEST = "rangedest";

	public clsExecutorMoveToArea(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		
		moMutEx.add(clsActionCultivate.class);
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);
		moMutEx.add(clsActionInnerSpeech.class);

		applyProperties(poPrefix,poProp);
	}

	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		//oProp.setProperty(pre+P_RANGEDEST, eSensorExtType.EATABLE_AREA.toString());
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
	//	String pre = clsProperties.addDot(poPrefix);
	//	moRangeDest=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGEDEST));
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_MOVETOAREA;
	}
	@Override
	protected void setName() {
		moName="Move to area executor";	
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
		return srStaminaBase ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsMobile oMEntity = (clsMobile) moEntity;

		//Am I carrying something?
		itfAPCarryable oEntity=(itfAPCarryable) oMEntity.getInventory().getCarriedEntity();
		if (oEntity==null) return false;
		
		//Get Destination
		clsSensorExt oSensor = (clsSensorExt) oBody.getExternalIO().moSensorEngine.getMeRegisteredSensors().get(moRangeDest);
		if (!(oSensor instanceof clsSensorRingSegment))  return false;

		//sim.physics2D.util.Double2D oDest = new sim.physics2D.util.Double2D(((clsSensorRingSegment)oSensor).getOffsetX(),0);
		sim.physics2D.util.Double2D oDest = new sim.physics2D.util.Double2D(moEntity.getPose().getPosition().x+((clsSensorRingSegment)oSensor).getOffsetX(),moEntity.getPose().getPosition().y);
		
		//Move
		oMEntity.getInventory().moveCarriedEntity(oDest);
	return true;
	}	
	
}