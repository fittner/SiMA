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
import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;
import enums.eSensorIntType;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsRadiation;
import decisionunit.itf.sensors.clsEnergy;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import bfg.tools.shapes.clsPolarcoordinate;
import java.util.Random;

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
	private final double mrMAX_STOP_DISTANCE = 0.2;
	private final double moINRANGE_AZIMUTH = 0.1;
	private final double moINRANGE_STEP = 0.5;
	private final double moRANDOM_STEP = 1.0;
	private final double moRANDOM_TURN = Math.PI / 4 * 3;
	private final long moSeed = 1234;
	
	private Random moRand;
	
	public clsReactive() {
		moRand = new Random(moSeed);
	}
	
	// the main run method
	public void stepProcessing(itfActionProcessor poActionProcessor) {
		// get sensor data at the moment
		clsSensorData inputs = getSensorData();
		
/*		if(isVisibleEntityInRange(inputs, eEntityType.FUNGUS)){
			if(!isAtEntity(inputs, eEntityType.FUNGUS)){
				goToEntity(inputs, poActionProcessor, eEntityType.FUNGUS);
			}			
		}*/
		
		goRandom(inputs, poActionProcessor);
		
		/*
		// If there is enough energy, do the job (collect uranium), if not, find something to eat.
		if(!isEnoughEnergy(inputs)){
			if(isVisibleEntityInRange(inputs, eEntityType.FUNGUS)){
				// go to fungus and eat it
				eating(inputs, poActionProcessor);
			}else{
				// desperately search for fungi
				exploring(inputs, poActionProcessor);
			}	
		// If the uranium container is full, go home and release it there 
		}else if(isUraniumCollected(inputs)){
			if(isVisibleEntityInRange(inputs, eEntityType.BASE)){
				// go to base and release ore
				homing(inputs, poActionProcessor);
			}else{
				// search for base
				exploring(inputs, poActionProcessor);
			}
		// If an uranium ore is detected, move towards it
		}else if(isUraniumInRange(inputs)){
			harvesting(inputs, poActionProcessor);
		// if there's nothing else to do, explore
		}else{
			exploring(inputs, poActionProcessor);
		}*/
	}
	
	
	private void homing(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isAtEntity(inputs, eEntityType.BASE)){
			release(inputs, poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToEntity(inputs, poActionProcessor, eEntityType.BASE);
		}
	}
	
	
	private void eating(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isAtEntity(inputs, eEntityType.FUNGUS)){
			eat(poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToEntity(inputs, poActionProcessor, eEntityType.FUNGUS);
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
		if(isAtEntity(inputs, eEntityType.URANIUM)){
			harvest(inputs, poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToEntity(inputs, poActionProcessor, eEntityType.URANIUM);
		}
	}
	
	
	/*
	 * (horvath) - if the fungus eater has enough energy, returns true, if not, returns false
	 */
	private boolean isEnoughEnergy(clsSensorData inputs){
		clsEnergy oStomach = (clsEnergy) getSensorData().getSensorInt(eSensorIntType.STOMACH);
		if (oStomach.mrEnergy <= mrMIN_ENERGY ){
			return false;	
		}else{	
			return true;
		}
	}
	
	/*
	 * (horvath) - if there's some fungus in the visible range, returns true, if not, returns false
	 */
	private boolean isVisibleEntityInRange(clsSensorData inputs, eEntityType entityType){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ){
			if( oVisionObj.mnEntityType == entityType){
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
	
	

	/*
	 * (horvath) - if distance of an entity from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	private boolean isAtEntity(clsSensorData inputs, eEntityType entityType){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.mnEntityType == entityType){
				if(oVisionObj.moPolarcoordinate.mrLength <= mrMAX_STOP_DISTANCE){
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
		
	/*
	 * (horvath) - Fungus eater - go to an entity of the defined type
	 * 
	 */
	private void goToEntity(clsSensorData inputs, itfActionProcessor poActionProcessor, eEntityType entityType){		
		clsPolarcoordinate closest = new clsPolarcoordinate();
		closest.mrLength = -1;
		
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		
		// Find the closest fungus 
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.mnEntityType == entityType){
				if(closest.mrLength < 0 || closest.mrLength > oVisionObj.moPolarcoordinate.mrLength){
					closest.mrLength = oVisionObj.moPolarcoordinate.mrLength;
					closest.moAzimuth = oVisionObj.moPolarcoordinate.moAzimuth;
				}
			}			
		}
		
		
		double rAngle = closest.moAzimuth.mrAlpha;
		
		if( rAngle < moINRANGE_AZIMUTH || rAngle > (2*Math.PI - moINRANGE_AZIMUTH))
		{
			//walk ahead to reach the can  
			//clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
			//oAction.setSpeed(2.5);
			//poActionList.addMoveAction(oAction);
			poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,moINRANGE_STEP));
		}
		else if( rAngle >= 0 && rAngle < Math.PI )
		{
			//rotate right
			//poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
			poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
		}
		else
		{
			//rotate left
			//poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
			poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
		}
		
		
	}

	/*
	 * (horvath) - Fungus eater - random movement
	 * 
	 */
	private void goRandom(clsSensorData inputs, itfActionProcessor poActionProcessor){ 
		switch(moRand.nextInt(3)){
			case(0):	poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,moRANDOM_STEP));
			case(1):	poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT,moRANDOM_TURN));
			case(2):	poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT,moRANDOM_TURN));		
		}	
	}
	
	/*
	 * (horvath) - Fungus eater - release uranium ore
	 * 
	 */
	private void release(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	
	/*
	 * (horvath) - Fungus eater - collect uranium ore in the eatable area
	 * 
	 */
	private void harvest(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		
	}
	
	/*
	 * (horvath) - Fungus eater - eat fungus in the eatable area
	 * 
	 */	
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
