/**
 * clsExecutorFacialExpressions.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny D�nz
 * 28.08.2009, 15:45:17
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.clsComplexBody;
import bw.body.intraBodySystems.clsFacialExpression;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import decisionunit.itf.actions.*;

/**
 * Action Executor for facial expressions (1 executor for several commands)
 * Parameters:
 *    commands for eye size, left/right antenna position, lens shape, lens size  
 *
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 */

public class clsExecutorFacialExpressions extends clsActionExecutor{

	static double srStaminaDemand = 0.01f;		//Stamina demand 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorFacialExpressions(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;

	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = clsActionExecutor.getDefaultProperties(poPrefix);
		
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
		return srStaminaDemand;
	}

	@Override
	public boolean execute(clsActionCommand poCommand) {

		clsComplexBody oBody = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		clsFacialExpression oFExp = oBody.getIntraBodySystem().getFacialExpression();
		
		if (poCommand instanceof clsActionFacialExEyeSize) {
			clsActionFacialExEyeSize oCmd = (clsActionFacialExEyeSize) poCommand;
			oFExp.setEyeSize(oCmd.getSize());
		}
			
		if (poCommand instanceof clsActionFacialExLeftAntennaPosition) {
			clsActionFacialExLeftAntennaPosition oCmd = (clsActionFacialExLeftAntennaPosition) poCommand;
			oFExp.setAntennaLeft(oCmd.getPosition());
		}
		
		if (poCommand instanceof clsActionFacialExRightAntennaPosition) {
			clsActionFacialExRightAntennaPosition oCmd = (clsActionFacialExRightAntennaPosition) poCommand;
			oFExp.setAntennaRight(oCmd.getPosition());
		}

		if (poCommand instanceof clsActionFacialExLensShape) {
			clsActionFacialExLensShape oCmd = (clsActionFacialExLensShape) poCommand;
			oFExp.setLensShape(oCmd.getShape());
		}

		if (poCommand instanceof clsActionFacialExLensSize) {
			clsActionFacialExLensSize oCmd = (clsActionFacialExLensSize) poCommand;
			oFExp.setLensSize(oCmd.getSize());
		}

		return true;
	}	

}
