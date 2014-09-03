
package complexbody.io.actuators.actionExecutors;

import java.util.ArrayList;

import body.clsComplexBody;
import body.itfget.itfGetBody;

import complexbody.io.actuators.clsInternalActionExecutor;
import complexbody.io.actuators.actionCommands.clsInternalActionCommand;
import complexbody.io.actuators.actionCommands.clsInternalActionGeneralSweat;

import properties.clsProperties;

import entities.abstractEntities.clsEntity;
import entities.enums.eBodyParts;



public class clsExecutorInternalGeneralSweat extends clsInternalActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 	?		
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private String moPrefix; // necessary? -volkan
	private clsProperties moProp; // necessary? -volkan

	public clsExecutorInternalGeneralSweat(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		this.moPrefix = poPrefix;
		this.moProp = poProp;
		
		moEntity = poEntity;
		
	//	moMutEx.add(clsActionMove.class);
	//	moMutEx.add(clsActionTurn.class);
		// What are these actions, move and turn doing in sweat executor? Copied from eat executor? -volkan

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
		mePartId = eBodyParts.ACTIONINT_GENERAL_SWEAT;
	}
	@Override
	protected void setName() {
		moName="General Sweat executor";	
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
		clsInternalActionGeneralSweat oCommand =(clsInternalActionGeneralSweat) poCommand; 
		
		// 1. Effect the body
		oBody.getInternalSystem().getBOrganSystem().getBOSweatGlands().setSweatIntensity( oCommand.getIntensity() );

		return true;
	} // end execute

} // end class
