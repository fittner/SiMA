/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.io.actuators.actionExecutors;

import config.clsBWProperties;
import java.util.ArrayList;

import jnao.CommandGenerator;

import nao.body.io.actuators.clsActionExecutor;

import du.enums.eActionTurnDirection;
import du.itf.actions.*;
/**
 * Action Executor for turning
 * Parameters:
 *    none 
 *
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 */
public class clsNAOExecutorTurn extends clsActionExecutor{

	static double srStaminaBase = 1.1f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Angle) ; 			
	static double srStaminaScalingFactor = 0.001f;   

	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	
	public clsNAOExecutorTurn() {
		super();

	}

	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
//		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_TURN;
	}
	@Override
	protected void setName() {
//		moName="Turn executor";
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx;
	}

	
	@Override
	public boolean execute(clsActionCommand poCommand) {
		clsActionTurn oCommand = (clsActionTurn) poCommand;
    	if (oCommand.getDirection()==eActionTurnDirection.TURN_LEFT)
    	{
			try {
				CommandGenerator.turn(1.0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}

    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT)
    	{
			try {
				CommandGenerator.turn(-1.0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		
    	return true;
	}	
}
