/**
 * clsExecutorFacialExpressions.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Dönz
 * 28.08.2009, 15:45:17
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import decisionunit.itf.actions.*;

/**
 * Action Executor for facial expressions (1 executor for several commands)
 * Parameters:
 *    commands for eye size, left/right antenna position, lens shape, lens size  
 *
 * @author Benny Dönz
 * 13.05.2009, 21:45:05
 * 
 */

public class clsExecutorFacialExpressions extends clsActionExecutor{

	static double srStaminaDemand = 0.01f;		//Stamina demand 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorFacialExpressions(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		moEntity=poEntity;

	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = new clsBWProperties();
		
		return oProp;
	}

	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS;
	}
	@Override
	protected void setName() {
		moName="Facial expressions executor";	
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
		return srStaminaDemand;
	}

	@Override
	public boolean execute(itfActionCommand poCommand) {

		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		
		if (poCommand instanceof clsActionFacialExEyeSize) {
		}
			
		if (poCommand instanceof clsActionFacialExLeftAntennaPosition) {
		}
		
		if (poCommand instanceof clsActionFacialExRightAntennaPosition) {
		}

		if (poCommand instanceof clsActionFacialExLensShape) {
		}

		if (poCommand instanceof clsActionFacialExLensSize) {
		}

		return true;
	}	

}
