/**
 * clsExecutorMoveToEatableArea.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny D�nz
 * 29.08.2009, 09:58:37
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;
import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.body.io.actuators.actionProxies.*;
import bw.body.io.sensors.ext.clsSensorExt;
import bw.body.io.sensors.ext.clsSensorRingSegment;
import bw.body.itfget.itfGetBody;
import decisionunit.itf.actions.*;
import enums.eSensorExtType;

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

	public clsExecutorMoveToArea(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;
		
		moMutEx.add(clsActionCultivate.class);
		moMutEx.add(clsActionDrop.class);
		moMutEx.add(clsActionPickUp.class);
		moMutEx.add(clsActionFromInventory.class);
		moMutEx.add(clsActionToInventory.class);
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);

		applyProperties(poPrefix,poProp);
	}

	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_RANGEDEST, eSensorExtType.EATABLE_AREA.toString());
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		moRangeDest=eSensorExtType.valueOf(poProp.getPropertyString(pre+P_RANGEDEST));
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
	public ArrayList<Class<?>> getMutualExclusions(itfActionCommand poCommand) {
		return moMutEx; 
	}

	/*
	 * Energy and stamina demand 
	 */
	@Override
	public double getEnergyDemand(itfActionCommand poCommand) {
		return getStaminaDemand(poCommand)*srEnergyRelation;
	}
	@Override
	public double getStaminaDemand(itfActionCommand poCommand) {
		return srStaminaBase ;
	}

	/*
	 * Executor 
	 */
	@Override
	public boolean execute(itfActionCommand poCommand) {
		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsMobile oMEntity = (clsMobile) moEntity;

		//Am I carrying something?
		itfAPCarryable oEntity=(itfAPCarryable) oMEntity.getInventory().getCarriedEntity();
		if (oEntity==null) return false;
		
		//Get Destination
		clsSensorExt oSensor = (clsSensorExt) oBody.getExternalIO().moSensorExternal.get(moRangeDest);
		if (!(oSensor instanceof clsSensorRingSegment))  return false;

		//sim.physics2D.util.Double2D oDest = new sim.physics2D.util.Double2D(((clsSensorRingSegment)oSensor).getOffsetX(),0);
		sim.physics2D.util.Double2D oDest = new sim.physics2D.util.Double2D(moEntity.getPosition().x+((clsSensorRingSegment)oSensor).getOffsetX(),moEntity.getPosition().y);
		
		//Move
		oMEntity.getInventory().moveCarriedEntity(oDest);
	return true;
	}	
	
}