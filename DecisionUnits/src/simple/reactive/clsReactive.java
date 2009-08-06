/**
 * @author horvath
 * 04.08.2009, 14:40:25
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
import enums.eSensorIntType;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsRadiation;
import decisionunit.itf.sensors.clsStomachSystem;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;

/**
 * DOCUMENT (horvath) - insert description 
 * 
 * @author horvath
 * 04.08.2009, 14:40:25
 * 
 */
public class clsReactive extends clsBaseDecisionUnit {
	
	// constants
	private final double mrMIN_ENERGY = 0.2;
	private final double mrMAX_OBSTACLE_DISTANCE = 0.2;
	private final double mrMAX_BASE_DISTANCE = 0.2;
	private final double mrMAX_FUNGUS_DISTANCE = 0.2;
	private final double mrMAX_URANIUM_DISTANCE = 0.2;
		
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
	
	
	/*
	 * (horvath) - if the fungus eater has enough energy, returns true, if not, returns false
	 */
	private boolean isEnoughEnergy(clsSensorData inputs){
		clsStomachSystem oStomach = (clsStomachSystem) getSensorData().getSensorInt(eSensorIntType.STOMACH);
		if (oStomach.mrEnergy <= mrMIN_ENERGY ){
			return false;	
		}else{	
			return true;
		}
	}
	
	/*
	 * (horvath) - if there's some fungus in the visible range, returns true, if not, returns false
	 */
	private boolean isFungusInRange(clsSensorData inputs){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ){
			if( oVisionObj.mnEntityType == eEntityType.FUNGUS){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * (horvath) - if there's some uranium in the sensible range, returns true, if not, returns false
	 */
	private boolean isUraniumInRange(clsSensorData inputs){
		clsRadiation oRadiation = (clsRadiation) getSensorData().getSensorExt(eSensorExtType.RADIATION);
		if(oRadiation.mrIntensity == 0){
			return false;	
		}else{	
			return true;
		}
	}
	
	private boolean isUraniumCollected(clsSensorData inputs){
		
		return false;
	}
	
	/*
	 * (horvath) - if distance of an object from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	private boolean isObstacle(clsSensorData inputs){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.moPolarcoordinate.mrLength <= mrMAX_OBSTACLE_DISTANCE){
				return true;				
			}
		}
		return false;
	}
	
	private boolean isObstacleMovable(clsSensorData inputs){
		
		return true;
	}
	
	private boolean isStill(clsSensorData inputs){
		
		return true;
	}
	
	
	/*
	 * (horvath) - if distance of the base from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	private boolean isAtBase(clsSensorData inputs){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.mnEntityType == eEntityType.BASE){
				if(oVisionObj.moPolarcoordinate.mrLength <= mrMAX_BASE_DISTANCE){
					return true;				
				}				
			}			
		}
		return false;
	}
	
	/*
	 * (horvath) - if distance of a fungus from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	private boolean isAtFungus(clsSensorData inputs){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.mnEntityType == eEntityType.FUNGUS){
				if(oVisionObj.moPolarcoordinate.mrLength <= mrMAX_FUNGUS_DISTANCE){
					return true;				
				}				
			}			
		}
		return false;
	}
	
	/*
	 * (horvath) - if distance of an uranium from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	private boolean isAtUranium(clsSensorData inputs){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.mnEntityType == eEntityType.URANIUM){
				if(oVisionObj.moPolarcoordinate.mrLength <= mrMAX_URANIUM_DISTANCE){
					return true;				
				}				
			}			
		}
		return false;
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
