/**
 * @author Benny D�nz
 * 13.05.2009, 21:45:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.io.actuators.actionExecutors;

import NAOProxyClient.CommandGenerator;
import java.util.ArrayList;


import nao.body.clsNAOBody;
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
	private clsNAOBody moNAOBody;
	
	public clsNAOExecutorTurn(clsNAOBody poNAOBody) {
		super();
		
		moNAOBody = poNAOBody;
		
		
		moMutEx.add(clsActionMove.class);
		moMutEx.add(clsActionTurn.class);
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
				//turn left must be negeative
				moNAOBody.addCommand(CommandGenerator.turn( (-1*oCommand.getAngle()) ));
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
    	}

    	if (oCommand.getDirection()==eActionTurnDirection.TURN_RIGHT)
    	{
			try {
				moNAOBody.addCommand(CommandGenerator.turn(oCommand.getAngle()));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
    	}
    	
    	System.out.println("EXCMD: turn , dir:" + oCommand.getDirection() + ", angle: " + oCommand.getAngle());
		
    	return true;
	}	
}
