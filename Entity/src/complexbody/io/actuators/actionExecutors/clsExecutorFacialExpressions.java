/**
 * clsExecutorFacialExpressions.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 28.08.2009, 15:45:17
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;
import body.clsComplexBody;
import body.itfget.itfGetBody;

import complexbody.intraBodySystems.clsFacialExpression;
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.clsActionCommand;
import complexbody.io.actuators.actionCommands.clsActionFacialExEyeSize;
import complexbody.io.actuators.actionCommands.clsActionFacialExLeftAntennaPosition;
import complexbody.io.actuators.actionCommands.clsActionFacialExLensShape;
import complexbody.io.actuators.actionCommands.clsActionFacialExLensSize;
import complexbody.io.actuators.actionCommands.clsActionFacialExRightAntennaPosition;

import entities.abstractEntities.clsEntity;

/**
 * Action Executor for facial expressions (1 executor for several commands)
 * Parameters:
 *    commands for eye size, left/right antenna position, lens shape, lens size  
 *
 * @author Benny Doenz
 * 13.05.2009, 21:45:05
 * 
 */

public class clsExecutorFacialExpressions extends clsActionExecutor{

	static double srStaminaDemand = 0.01f;		//Stamina demand 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorFacialExpressions(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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
		mePartId = entities.enums.eBodyParts.ACTIONEX_FACIALEXPRESSIONS;
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
