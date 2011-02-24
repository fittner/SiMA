/**
 * @author Benny D�nz
 * 13.05.2009, 21:44:55
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
public class clsNAOExecutorMove extends clsActionExecutor{

	static double srStaminaBase = 2f;			//Stamina demand =srStaminaScalingFactor*pow(srStaminaBase,Speed) ; 			
	static double srStaminaScalingFactor = 0.01f;  
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	
	private clsNAOBody moNAOBody;
	
	public static final String P_SPEEDCALINGFACTOR = "speedcalingfactor";

	public clsNAOExecutorMove(clsNAOBody poNAOBody) {
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
//		mePartId = bw.utils.enums.eBodyParts.ACTIONEX_MOVE;
	}
	@Override
	protected void setName() {
//		moName="Move executor";
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}
	
	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
		
		try {
			clsActionMove oCommand =(clsActionMove) poCommand; 
			
	    	switch( oCommand.getDirection() ) {
		    	case MOVE_FORWARD:
		    		moNAOBody.addCommand(CommandGenerator.move(oCommand.getSpeed(), true));
		    	case MOVE_BACKWARD:
		    		moNAOBody.addCommand(CommandGenerator.move(oCommand.getSpeed(), false));
	    	}
	    	
	    	System.out.println("EXCMD: move , dir:" + oCommand.getDirection() + ", speed: " + oCommand.getSpeed());
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return true;
	}	
}
