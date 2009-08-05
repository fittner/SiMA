/**
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package simple.reactive;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionEat;
import decisionunit.itf.actions.itfActionProcessor;
import enums.eEntityType;
import enums.eSensorExtType;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsSensorData;

/**
 * DOCUMENT (horvath) - insert description 
 * 
 * @author horvath
 * 04.08.2009, 14:40:25
 * 
 */
public class clsReactive extends clsBaseDecisionUnit {
	
	public clsReactive() {
	}
	
	// the main run method
	public void stepProcessing(itfActionProcessor poActionProcessor) {
		// get sensor data at the moment
		clsSensorData inputs = getSensorData();
	
		// If there is enough energy, do the job (collect uranium), if not, find something to eat.
		if(!isEnoughEnergy(inputs)){
			if(isFungusInRange(inputs)){
				// go to fungus and eat it
				eating(inputs, poActionProcessor);
			}else{
				// desperately search for fungi
				exploring(inputs, poActionProcessor);
			}	
		// If the uranium container is full, go home and release it there 
		}else if(isUraniumCollected(inputs)){
			homing(inputs, poActionProcessor);
		// If an uranium ore is detected, move towards it
		}else if(isUraniumInRange(inputs)){
			harvesting(inputs, poActionProcessor);
		// if there's nothing else to do, explore
		}else{
			exploring(inputs, poActionProcessor);
		}	
	}
	
	
	private void homing(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isStill(inputs) && isAtBase(inputs)){
			release(inputs, poActionProcessor);
		}else if(!isStill(inputs) && isAtBase(inputs)){
			stop(inputs, poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToBase(inputs, poActionProcessor);
		}
	}
	
	
	private void eating(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isStill(inputs) && isAtFungus(inputs)){
			eat(poActionProcessor);
		}else if(!isStill(inputs) && isAtFungus(inputs)){
			stop(inputs, poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToFungus(inputs, poActionProcessor);
		}
	}
	
	
	private void exploring(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goRandom(inputs, poActionProcessor);
		}
	}
	
	
	private void harvesting(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isStill(inputs) && isAtUranium(inputs)){
			harvest(inputs, poActionProcessor);
		}else if(!isStill(inputs) && isAtUranium(inputs)){
			stop(inputs, poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToUranium(inputs, poActionProcessor);
		}
	}
	
	
	
	private boolean isEnoughEnergy(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isFungusInRange(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isUraniumInRange(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isUraniumCollected(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isObstacle(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isObstacleMovable(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isStill(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isAtBase(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isAtFungus(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isAtUranium(clsSensorData inputs){
		
		return true;
	}
	
	private void moveObstacle(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	
	private void avoidObstacle(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void goToBase(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void goToFungus(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void goToUranium(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void goRandom(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void release(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void stop(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	private void harvest(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
		
	private void eat(itfActionProcessor poActionProcessor) {
		//eat
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0)
		{

				if( oEatArea.mnTypeOfFirstEntity == eEntityType.FUNGUS )
				{
						//clsEatAction oEatAction = new clsEatAction();
						//poActionList.addEatAction(oEatAction);
						poActionProcessor.call(new clsActionEat());	
						
						// stop the agent

				}
		}
	}	
	
	


	@Override
	public void process() { 
		stepProcessing( getActionProcessor() );
		
		//clsActionCommandContainer oCommands = new clsActionCommandContainer();
		//oCommands.addMoveAction( clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD));
		
	}


}
