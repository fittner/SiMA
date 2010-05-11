/**
 * @author Benny D�nz
 * 13.05.2009, 21:44:55
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import bw.body.io.actuators.clsActionExecutor;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import du.itf.actions.*;

/**
 * Action Executor for movement
 * Parameters:
 *    prSpeedScalingFactor = Relation of Speed to Energy. For Average Speed of "4", default ist 10 
 * 
 * @author Benny D�nz
 * 13.05.2009, 21:44:55
 * 
 */
public class clsExecutorMove extends clsActionExecutor{

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Speed) ; 			
	static double srStaminaScalingFactor = 0.01f;  
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();

	private clsEntity moEntity;
	private double mrSpeedScalingFactor;
	
	public static final String P_SPEEDCALINGFACTOR = "speedcalingfactor";

	public clsExecutorMove(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
		super(poPrefix, poProp);
		
		moEntity=poEntity;
		applyProperties(poPrefix,poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		clsBWProperties oProp = clsActionExecutor.getDefaultProperties(pre);
		oProp.setProperty(pre+P_SPEEDCALINGFACTOR, 10f);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		mrSpeedScalingFactor=poProp.getPropertyFloat(pre+P_SPEEDCALINGFACTOR);
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_MOVE;
	}
	@Override
	protected void setName() {
		moName="Move executor";
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
		clsActionMove oCommand =(clsActionMove) poCommand;
		return srStaminaScalingFactor* Math.pow(srStaminaBase,oCommand.getSpeed()) ;
	}
	
	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionMove oCommand =(clsActionMove) poCommand; 
    	switch(oCommand.getDirection() )
    	{
    	case MOVE_FORWARD:
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.moveForward(mrSpeedScalingFactor*oCommand.getSpeed());
    	case MOVE_BACKWARD:
    		((clsMobile)moEntity).getMobileObject2D().moMotionPlatform.backup();
    	}
    	return true;
	}	
}
