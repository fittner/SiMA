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
import bw.body.intraBodySystems.clsFacialExpression;
import bw.body.io.actuators.clsActionExecutor;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import bw.utils.enums.eAntennaPositions;
import bw.utils.enums.eEyeSize;
import bw.utils.enums.eLensShape;
import bw.utils.enums.eLensSize;
import decisionunit.itf.actions.*;
import enums.eActionFacialExAntennaPosition;
import enums.eActionFacialExEyeSize;
import enums.eActionFacialExLensShape;
import enums.eActionFacialExLensSize;

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
		clsFacialExpression oFExp = oBody.getIntraBodySystem().getFacialExpression();
		
		if (poCommand instanceof clsActionFacialExEyeSize) {
			clsActionFacialExEyeSize oCmd = (clsActionFacialExEyeSize) poCommand;
			if (oCmd.getSize()==eActionFacialExEyeSize.SMALL) oFExp.setEyeSize(eEyeSize.SMALL);
			if (oCmd.getSize()==eActionFacialExEyeSize.MEDIUM) oFExp.setEyeSize(eEyeSize.MEDIUM);
			if (oCmd.getSize()==eActionFacialExEyeSize.LARGE) oFExp.setEyeSize(eEyeSize.LARGE);
		}
			
		if (poCommand instanceof clsActionFacialExLeftAntennaPosition) {
			clsActionFacialExLeftAntennaPosition oCmd = (clsActionFacialExLeftAntennaPosition) poCommand;
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.DOWN) oFExp.setAntennaLeft(eAntennaPositions.DOWN);
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.HORIZONTAL) oFExp.setAntennaLeft(eAntennaPositions.HORIZONTAL);
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.INTERMEDIATE) oFExp.setAntennaLeft(eAntennaPositions.INTERMEDIATE);
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.UPRIGHT) oFExp.setAntennaLeft(eAntennaPositions.UPRIGHT);
		}
		
		if (poCommand instanceof clsActionFacialExRightAntennaPosition) {
			clsActionFacialExRightAntennaPosition oCmd = (clsActionFacialExRightAntennaPosition) poCommand;
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.DOWN) oFExp.setAntennaRight(eAntennaPositions.DOWN);
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.HORIZONTAL) oFExp.setAntennaRight(eAntennaPositions.HORIZONTAL);
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.INTERMEDIATE) oFExp.setAntennaRight(eAntennaPositions.INTERMEDIATE);
			if (oCmd.getPosition()==eActionFacialExAntennaPosition.UPRIGHT) oFExp.setAntennaRight(eAntennaPositions.UPRIGHT);
		}

		if (poCommand instanceof clsActionFacialExLensShape) {
			clsActionFacialExLensShape oCmd = (clsActionFacialExLensShape) poCommand;
			if (oCmd.getShape()==eActionFacialExLensShape.DASH) oFExp.setLensShape(eLensShape.DASH);
			if (oCmd.getShape()==eActionFacialExLensShape.LENTICULAR) oFExp.setLensShape(eLensShape.LENTICULAR);
			if (oCmd.getShape()==eActionFacialExLensShape.OVAL) oFExp.setLensShape(eLensShape.OVAL);
			if (oCmd.getShape()==eActionFacialExLensShape.ROUND) oFExp.setLensShape(eLensShape.ROUND);
		}

		if (poCommand instanceof clsActionFacialExLensSize) {
			clsActionFacialExLensSize oCmd = (clsActionFacialExLensSize) poCommand;
			if (oCmd.getSize()==eActionFacialExLensSize.LARGE) oFExp.setLensSize(eLensSize.LARGE);
			if (oCmd.getSize()==eActionFacialExLensSize.MEDIUM) oFExp.setLensSize(eLensSize.MEDIUM);
			if (oCmd.getSize()==eActionFacialExLensSize.SMALL) oFExp.setLensSize(eLensSize.SMALL);
		}

		return true;
	}	

}
