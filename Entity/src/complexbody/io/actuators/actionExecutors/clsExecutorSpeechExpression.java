/**
 * clsExecutorFacialExpressions.java: BW - bw.body.io.actuators.actionExecutors
 * 
 * @author Benny Doenz
 * 28.08.2009, 15:45:17
 */
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;

import complexbody.intraBodySystems.clsSpeechExpression;
import complexbody.io.actuators.clsActionExecutor;
import complexbody.io.actuators.actionCommands.*;

import body.clsComplexBody;
import body.itfget.itfGetBody;
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

public class clsExecutorSpeechExpression extends clsActionExecutor{

	static double srStaminaDemand = 0.01f;		//Stamina demand 			

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private clsEntity moEntity;

	public clsExecutorSpeechExpression(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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
		mePartId = entities.enums.eBodyParts.ACTIONEX_OUTERSPEECH;
	}
	@Override
	protected void setName() {
		moName="Speech expressions executor";	
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
		clsSpeechExpression oFExp = oBody.getIntraBodySystem().getSpeechExpression();

		return true;
	}	

}
