package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import properties.clsProperties;
import body.clsComplexBody;
import body.itfget.itfGetBody;

import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionAffectEyesForCrying;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;




public class clsExecutorInternalAffectEyesForCrying extends clsInternalActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?		
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;

	public clsExecutorInternalAffectEyesForCrying(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity = poEntity;

		applyProperties(poPrefix,poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = clsInternalActionExecutor.getDefaultProperties(pre);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.ACTIONINT_FACIAL_AFFECT_EYES_FOR_CRYING;
	}
	@Override
	protected void setName() {
		moName="Affect Eyes For Crying executor";	
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
		clsInternalActionAffectEyesForCrying oCommand =(clsInternalActionAffectEyesForCrying) poCommand; 
		
		// 1. Affect the body
		oBody.getIntraBodySystem().getFacialExpression().getBOFacialEyes().addMourningIntensity( oCommand.getIntensity() );
		

		return true;
	} // end execute

} // end class
