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
import decisionunit.itf.actions.clsActionDrop;
import decisionunit.itf.actions.clsActionEat;
import decisionunit.itf.actions.clsActionFromInventory;
import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionPickUp;
import decisionunit.itf.actions.clsActionToInventory;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;
import enums.eSensorIntType;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsSensorData;
import decisionunit.itf.sensors.clsRadiation;
import decisionunit.itf.sensors.clsEnergy;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import bfg.tools.shapes.clsPolarcoordinate;

import java.util.Random;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * DOCUMENT (horvath) - insert description 
 * 
 * @author horvath
 * 04.08.2009, 14:40:25
 * 
 */
public class clsReactive extends clsBaseDecisionUnit {
	
	private enum Action{
		NONE,
		TURN,
		GO,
		PICKUP,
		DROP,
		TO_INVENTORY,
		FROM_INVENTORY
	}	
	
	
	// constants
	private final double mrMIN_ENERGY = 0.2;
	private final double mrMAX_URANIUM = 10;
	private final double mrINRANGE_AZIMUTH = 0.1;
	private final double mrFORWARD_STEP = 0.6;
	private final double mrAT_ENTITY_MAX = 20;
	private final int mnRANDOM_TIME = 100;
	private final double mrRANDOM_TURN = Math.PI/2;
	private final long mnSEED = 1234;
	private final int mnAVOIDANCE_DISTANCE = 50;
	private final int mnAVOIDANCE_ANGLE = 150;
	private final int mnMAX_COLLECTED_URANIUM = 1;
	
	private final HashSet moOBSTACLES_NOURANIUM = new HashSet(Arrays.asList(	
			eEntityType.BASE,					
			eEntityType.FUNGUS,					
			eEntityType.STONE,						
			eEntityType.WALL));
	private final HashSet moOBSTACLES_NOURANIUMBASE = new HashSet(Arrays.asList(	
			eEntityType.FUNGUS,					
			eEntityType.STONE,						
			eEntityType.WALL));

	
	private Random moRand;
	private boolean mbTriedToMove;
	private ArrayList<Action> moActionQueue = new ArrayList<Action>();
	private int mnInventorySize;
	
	private double mrTurnForce;
	private eActionTurnDirection meTurnDirection;
	private double mrGoForce;
	private eActionMoveDirection meGoDirection;
	
	public clsReactive() {
		moRand = new Random(mnSEED);
		mbTriedToMove = false;
		mnInventorySize = 0;
	}
	
	// the main run method
	public void stepProcessing(itfActionProcessor poActionProcessor) {
		if(moActionQueue.size() == 0){
			moActionQueue.add(Action.NONE);
		}
		
		Action oActionInProgress = moActionQueue.get(0); 
		moActionQueue.remove(0);
		
		
		switch(oActionInProgress){
			case TURN: 	turn(poActionProcessor);
						break;
			case GO: 	go(poActionProcessor);
						break;
			case PICKUP:	poActionProcessor.call(new clsActionPickUp() );
							break;
			case DROP:	poActionProcessor.call(new clsActionDrop() );
						break;
			case TO_INVENTORY:	poActionProcessor.call(new clsActionToInventory() );
						break;
			case FROM_INVENTORY: poActionProcessor.call(new clsActionFromInventory() );
						break;
			case NONE:				
				// get sensor data at the moment
				clsSensorData inputs = getSensorData();
				
				if(mnInventorySize == 0){
				if(isVisibleEntityInRange(inputs,eEntityType.URANIUM)){
					if(isAtEntity(eEntityType.URANIUM, 11)){
						harvest(inputs, poActionProcessor);
						System.out.println("harvest");
					}else if(isObstacle(moOBSTACLES_NOURANIUM)){
						avoidObstacle(poActionProcessor, moOBSTACLES_NOURANIUM);
						System.out.println("avoid obstacle");
					}else{
						goToEntity(poActionProcessor, eEntityType.URANIUM);
						System.out.println("go to entity");
					}
				}else{
					if(isObstacle(moOBSTACLES_NOURANIUM)){
						avoidObstacle(poActionProcessor, moOBSTACLES_NOURANIUM);
						System.out.println("avoid obstacle");
					}else{
						if(moActionQueue.size() == 0){
							goRandom(poActionProcessor);
							System.out.println("go random");
						}
					}
				}
				}
				if(mnInventorySize == mnMAX_COLLECTED_URANIUM){	
					if(isVisibleEntityInRange(inputs,eEntityType.BASE)){
						if(isAtEntity(eEntityType.BASE, mrAT_ENTITY_MAX)){
							//release(poActionProcessor);
							System.out.println("homing: release");
						}else if(isObstacle(moOBSTACLES_NOURANIUMBASE)){
							avoidObstacle(poActionProcessor,moOBSTACLES_NOURANIUMBASE);
							System.out.println("homing: avoid obstacle");
						}
						else{
							goToEntity(poActionProcessor, eEntityType.BASE);
							System.out.println("homing: go to entity");
						}
					}else{
						if(isObstacle(moOBSTACLES_NOURANIUMBASE)){
							avoidObstacle(poActionProcessor,moOBSTACLES_NOURANIUMBASE);
							System.out.println("homing: avoid obstacle");
						}else{
							if(moActionQueue.size() == 0){
								goRandom(poActionProcessor);
								System.out.println("homing: go random");
							}
						}
					}
				}
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
				break;
		}
	}
	
	
	/*private void homing(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isAtEntity(inputs, eEntityType.BASE)){
			release(poActionProcessor);
		}else if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goToEntity(inputs, poActionProcessor, eEntityType.BASE);
		}
	}*/
	
	
	/*private void eating(clsSensorData inputs, itfActionProcessor poActionProcessor){
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
	}*/
	
	
	/*private void exploring(clsSensorData inputs, itfActionProcessor poActionProcessor){
		if(isObstacle(inputs)){
			if(isObstacleMovable(inputs)){
				moveObstacle(inputs, poActionProcessor);
			}else{
				avoidObstacle(inputs, poActionProcessor);
			}
		}else{
			goRandom(poActionProcessor);
		}
	}*/
	
	
	/*private void harvesting(clsSensorData inputs, itfActionProcessor poActionProcessor){
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
	}*/
	
	
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
		if(mnInventorySize >= mrMAX_URANIUM){
			return true;			
		}
		return false;
	}
	
	/*
	 * (horvath) - if distance of an object from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	private boolean isObstacle(HashSet poObstacleTypes){
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);	

		HashSet intersection = (HashSet)poObstacleTypes.clone();
		intersection.retainAll(oEatArea.moEntityTypesPresent);	// calculate intersection of seen object types and obstacle types
		if( intersection.size() != 0){							// if the intersection is not empty -> there is an obstacle
			return true;	
		}				
		return false;
	}
	
	/*
	 * (horvath) - if the agent tried to move an obstacle, and the obstacle is still there 
	 * (the function isObstacleMovable was called again), then the obstacle is not movable 
	 * 
	 */
	private boolean isObstacleMovable(clsSensorData inputs){
		if(mbTriedToMove){
			return false;
		}else{
			return true;
		}
	}
	
	

	/*
	 * (horvath) - if distance of an entity from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */
	/*private boolean isAtEntity(eEntityType poEntityType, boolean pbCloseDist){
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		clsBump oBumper = (clsBump)getSensorData().getSensorExt(eSensorExtType.BUMP);
		if(oEatArea.moEntityTypesPresent.contains(poEntityType)){
			if(pbCloseDist){
				if(oBumper.mnBumped){
					return true;
				}
			}else{
				return true;
			}
		}		
		return false;
	}*/
	
	private boolean isAtEntity(eEntityType poEntityType, double pnDistance){
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		clsBump oBumper = (clsBump)getSensorData().getSensorExt(eSensorExtType.BUMP);
		// Find the closest fungus 
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			
			if (oVisionObj.mnEntityType == poEntityType){
				if(oVisionObj.moPolarcoordinate.mrLength <= pnDistance){
					return true;
				}
			}
		}
		return false;
	}
	
	/*private void moveObstacle(clsSensorData inputs, itfActionProcessor poActionProcessor){
		
		switch(mnMoveStep){
			case 0: // pick up the obstacle
					poActionProcessor.call(new clsActionPickUp() );
					break;
			case 1:	// turn 90deg to some side
					poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
					break;
			case 2: // drop the obstacle
					poActionProcessor.call(new clsActionDrop() );
					break;
			case 3: // turn back ; end of try to move an obstacle
					poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
					break;
		}
		mnMoveStep++;		
	}*/
	
	/*
	 * (horvath) - This method navigates the Fungus-Eater to avoid obstacle
	 * 
	 */
	private void avoidObstacle(itfActionProcessor poActionProcessor, HashSet poObstacleTypes){
			moActionQueue.clear();
			if (isObstacle(poObstacleTypes)) {
						go(eActionMoveDirection.MOVE_BACKWARD, mrFORWARD_STEP, 10);
						switch (moRand.nextInt(2)) {
							case 0: // fungus eater is facing the original obstacle and will turn left
									turn(eActionTurnDirection.TURN_LEFT, 1, mnAVOIDANCE_ANGLE);
									break;
							case 1: // fungus eater is facing the original obstacle and will
									// turn right
									turn(eActionTurnDirection.TURN_RIGHT, 1, mnAVOIDANCE_ANGLE);
									break;
						}
						goCheck(eActionMoveDirection.MOVE_FORWARD, mrFORWARD_STEP, mnAVOIDANCE_DISTANCE);
			}
	}	
	
	
	private void turn(itfActionProcessor poActionProcessor){
		poActionProcessor.call(new clsActionTurn(meTurnDirection,mrTurnForce));
	}
	
	private void turn(eActionTurnDirection peDirection, double prForce, int pnRepetitions){
		meTurnDirection = peDirection;
		mrTurnForce = prForce;
		for(int i=0; i<pnRepetitions; i++){
			moActionQueue.add(Action.TURN);
		}
	}
	
	private void go(itfActionProcessor poActionProcessor){
		poActionProcessor.call(new clsActionMove(meGoDirection,mrGoForce));
	}
	
	private void go(eActionMoveDirection peDirection, double prForce, int pnRepetitions){
		meGoDirection = peDirection;
		mrGoForce = prForce;
		for(int i=0; i<pnRepetitions; i++){
			moActionQueue.add(Action.GO);
		}
	}
	
	private void goCheck(eActionMoveDirection peDirection, double prForce, int pnRepetitions){
		meGoDirection = peDirection;
		mrGoForce = prForce;
		for(int i=0; i<pnRepetitions; i++){
			moActionQueue.add(Action.NONE);
			moActionQueue.add(Action.GO);
		}
	}
	
		
	/*
	 * (horvath) - Fungus eater - go to an entity of the defined type
	 * 
	 */
	private void goToEntity(itfActionProcessor poActionProcessor, eEntityType entityType){		
		moActionQueue.clear();
		clsPolarcoordinate closest = new clsPolarcoordinate();
		closest.mrLength = -1;
		
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		
		// Find the closest fungus 
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if(oVisionObj.mnEntityType == entityType){
				//if(oVisionObj.mnRegistered){
				if(closest.mrLength < 0 || closest.mrLength > oVisionObj.moPolarcoordinate.mrLength){
					closest.mrLength = oVisionObj.moPolarcoordinate.mrLength;
					closest.moAzimuth = oVisionObj.moPolarcoordinate.moAzimuth;
				//}
				}
			}			
		}
		
		
		
		double rAngle = closest.moAzimuth.mrAlpha;
		
		
		if( rAngle < mrINRANGE_AZIMUTH || rAngle > (2*Math.PI - mrINRANGE_AZIMUTH))
		{
			//walk ahead to reach the can  
			go(eActionMoveDirection.MOVE_FORWARD,mrFORWARD_STEP,1);
		}
		else if( rAngle >= 0 && rAngle < Math.PI )
		{
			//rotate right
			turn(eActionTurnDirection.TURN_RIGHT,Math.PI/2,1);
		}
		else
		{
			//rotate left
			turn(eActionTurnDirection.TURN_LEFT,Math.PI/2,1);
		}
		
		
	}

	/*
	 * (horvath) - Fungus eater - random movement
	 * 
	 */
	private void goRandom(itfActionProcessor poActionProcessor){ 	
		moActionQueue.clear();
		switch(moRand.nextInt(2)){
			case(0):	turn(eActionTurnDirection.TURN_RIGHT, mrRANDOM_TURN, mnRANDOM_TIME);
						goCheck(eActionMoveDirection.MOVE_FORWARD, mrFORWARD_STEP, mnRANDOM_TIME);
						break;
			case(1):	turn(eActionTurnDirection.TURN_LEFT, mrRANDOM_TURN, mnRANDOM_TIME);
						goCheck(eActionMoveDirection.MOVE_FORWARD, mrFORWARD_STEP, mnRANDOM_TIME);
						break;
		}
	}
	
	/*
	 * (horvath) - Fungus eater - release uranium ore
	 * 
	 */
	private void release(itfActionProcessor poActionProcessor){
		//moActionQueue.add(Action.FROM_INVENTORY);
		moActionQueue.add(Action.DROP);
		mnInventorySize = 0;
	}
		
	/*
	 * (horvath) - Fungus eater - collect uranium ore in the eatable area
	 * 
	 */
	private void harvest(clsSensorData inputs, itfActionProcessor poActionProcessor){
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0){
			if( oEatArea.mnTypeOfFirstEntity == eEntityType.URANIUM ){
				moActionQueue.add(Action.PICKUP);
				//moActionQueue.add(Action.TO_INVENTORY);
				mnInventorySize++;
			}
		}
		
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
