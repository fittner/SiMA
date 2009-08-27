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
import java.util.Iterator;

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
		FROM_INVENTORY,
		EAT
	}	
	
	
	// constants
	private final double mrENERGY_LOWER = 0;
	private final double mrENERGY_UPPER = 2.4;
	private final double mrMAX_URANIUM = 1;
	private final double mrINRANGE_AZIMUTH = 0.1;
	private final double mrFORWARD_STEP = 0.6;
	private final double mrAT_BASE_MAX = 40;
	private final double mrAT_ENTITY_MAX = 17;
	private final double mrAT_FUNGUS_MAX = 25;
	private final double mrATENTITY_ANGLE_TOLERANCE = Math.PI / 4;
	private final double mrOBSTACLE_DIST_MAX = 30;
	private final int mnRANDOM_TIME = 100;
	private final double mrRANDOM_TURN = Math.PI/2;
	private final long mnSEED = 1234;
	private final int mnAVOIDANCE_DISTANCE = 40;
	private final int mnAVOIDANCE_ANGLE = 150;
	
	private final HashSet<eEntityType> moOBSTACLES = new HashSet<eEntityType>(Arrays.asList(	
			eEntityType.BASE,					
			eEntityType.STONE,						
			eEntityType.WALL));
	private final HashSet<eEntityType> moOBSTACLES_NOBASE = new HashSet<eEntityType>(Arrays.asList(	
			eEntityType.STONE,						
			eEntityType.WALL));

	
	private Random moRand;
	private ArrayList<Action> moActionQueue = new ArrayList<Action>();
	private Action moActionInProgress;
	private int mnInventorySize;
	private boolean mbHungry;
	private double mrTurnForce;
	private eActionTurnDirection meTurnDirection;
	private double mrGoForce;
	private eActionMoveDirection meGoDirection;
	private String moMode;
	private String moLayer;
	
	public clsReactive() {
		moRand = new Random(mnSEED);
		mnInventorySize = 0;
		mbHungry = false;
		moMode = "";
		moLayer = "";
	}
	
	// the main run method
	public void stepProcessing(itfActionProcessor poActionProcessor) {
		if(moActionQueue.size() == 0){
			moActionQueue.add(Action.NONE);
		}
		
		moActionInProgress = moActionQueue.get(0); 
		moActionQueue.remove(0);
		
		// get sensor data at the moment
		clsSensorData inputs = getSensorData();
		
		switch(moActionInProgress){
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
			case EAT:	eat(inputs, poActionProcessor);
						break;
			case NONE:								
				
				// If there is enough energy, do the job (collect uranium), if not, find something to eat.
				if(!isEnoughEnergy(inputs)){
					if(isVisibleEntityInRange(inputs, eEntityType.FUNGUS)){
						// go to fungus and eat it
						moMode = "Eating";
						eating(inputs, poActionProcessor);
					}else{
						// desperately search for fungi
						moMode = "Exploring <Eating>";
						exploring(inputs, poActionProcessor, moOBSTACLES);
					}	
					if(mnInventorySize != 0){
						moActionQueue.set(0, Action.DROP);
						mnInventorySize = 0;
					}
				// If the uranium container is full, go home and release it there 
				}else if(isUraniumCollected(inputs)){
					if(isVisibleEntityInRange(inputs, eEntityType.BASE)){
						// go to base and release ore
						moMode = "Homing";
						homing(inputs, poActionProcessor);
					}else{
						// search for base
						moMode = "Exploring <Homing>";
						exploring(inputs, poActionProcessor, moOBSTACLES_NOBASE);
					}
				// If an uranium ore is detected, move towards it
				}else if(isVisibleEntityInRange(inputs, eEntityType.URANIUM)){
					moMode = "Harvesting";
					harvesting(inputs, poActionProcessor);
				// if there's nothing else to do, explore
				}else{
					moMode = "Exploring <Harvesting>";
					exploring(inputs, poActionProcessor, moOBSTACLES);
				}
				
				break;
		}
	}
	
	
	private void homing(clsSensorData poInputs, itfActionProcessor poActionProcessor){
		if(isAtEntity(poInputs, eEntityType.BASE,mrAT_BASE_MAX)){
			moLayer = "Release";
			release(poActionProcessor);
		}else if(isObstacle(poInputs, moOBSTACLES_NOBASE)){
			moLayer = "Obstacle avoidance";
			avoidObstacle(poInputs, poActionProcessor,moOBSTACLES_NOBASE);
		}else{
			moLayer = "Go to entity <BASE>";
			goToEntity(poInputs, poActionProcessor, eEntityType.BASE);
		}
	}
	
	
	private void eating(clsSensorData poInputs, itfActionProcessor poActionProcessor){
		
		if(isAtEntity(poInputs, eEntityType.FUNGUS, mrAT_FUNGUS_MAX) && isFirstInEatableArea(poInputs, eEntityType.FUNGUS)){
			moLayer = "Eat";
			moActionQueue.add(Action.EAT);
		}else if(isObstacle(poInputs, moOBSTACLES)){
			moLayer = "Obstacle avoidance";
			avoidObstacle(poInputs, poActionProcessor, moOBSTACLES);	
		}else{
			moLayer = "Go to entity <FUNGUS>";
			goToEntity(poInputs, poActionProcessor, eEntityType.FUNGUS);
		}
	}
	
	
	private void exploring(clsSensorData poInputs, itfActionProcessor poActionProcessor, HashSet<eEntityType> poObstacles){
		if(isObstacle(poInputs, poObstacles)){
			moLayer = "Obstacle avoidance";
			avoidObstacle(poInputs, poActionProcessor, poObstacles);	
		}else{
			if(moActionQueue.size() == 0){
				moLayer = "Random walk";
				if(moActionQueue.size() == 0){
					goRandom(poActionProcessor);
				}
			}
		}
	}
	
	
	private void harvesting(clsSensorData poInputs, itfActionProcessor poActionProcessor){
		if(isAtEntity(poInputs, eEntityType.URANIUM, mrAT_ENTITY_MAX) && isFirstInEatableArea(poInputs, eEntityType.URANIUM)){
			moLayer = "Harvest";
			harvest(poInputs, poActionProcessor);
		}else if(isObstacle(poInputs, moOBSTACLES)){
			moLayer = "Obstacle avoidance";
			avoidObstacle(poInputs, poActionProcessor, moOBSTACLES);	
		}else{
			moLayer = "Go to entity <URANIUM>";
			goToEntity(poInputs, poActionProcessor, eEntityType.URANIUM);
		}
	}
	
	
	/*
	 * (horvath) - if the fungus eater has enough energy, returns true, if not, returns false
	 */
	private boolean isEnoughEnergy(clsSensorData poInputs){
		clsEnergy oStomach = (clsEnergy) poInputs.getSensorInt(eSensorIntType.ENERGY);
		
		if(oStomach.mrEnergy >= mrENERGY_UPPER){
			mbHungry = false;
		}
		if(oStomach.mrEnergy <= mrENERGY_LOWER){
			mbHungry = true;
		}	
		
		if (mbHungry){			
			return false;	
		}else{
			return true;
		}
	}
	
	private boolean isFirstInEatableArea(clsSensorData poInputs, eEntityType poEntityType){
		clsEatableArea oEatArea = (clsEatableArea) poInputs.getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnTypeOfFirstEntity == poEntityType){
			return true;
		}
		return false;		
	}
	
	
	/*
	 * (horvath) - if there's some fungus in the visible range, returns true, if not, returns false
	 */
	private boolean isVisibleEntityInRange(clsSensorData inputs, eEntityType entityType){
		clsVision oVision = (clsVision) inputs.getSensorExt(eSensorExtType.VISION);
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
		clsRadiation oRadiation = (clsRadiation) inputs.getSensorExt(eSensorExtType.RADIATION);
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
	private boolean isObstacle(clsSensorData poInputs, HashSet<eEntityType> poObstacleTypes){
		Iterator<eEntityType> itr = poObstacleTypes.iterator();
		while(itr.hasNext()){
			if(isAtEntity(poInputs, itr.next(),mrOBSTACLE_DIST_MAX)){
				return true;
			}
		}
		return false;
	}
		
	

	/*
	 * (horvath) - if distance of an entity from the fungus eater is less than a predefined value, returns true, 
	 * 			   otherwise returns false
	 */	
	private boolean isAtEntity(clsSensorData poInputs, eEntityType poEntityType, double pnDistance){
		clsVision oVision = (clsVision) poInputs.getSensorExt(eSensorExtType.VISION);
		// Find the closest entity 
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if (oVisionObj.mnEntityType == poEntityType){
				if(oVisionObj.moPolarcoordinate.mrLength <= pnDistance){
					if(oVisionObj.moPolarcoordinate.moAzimuth.mrAlpha <= mrATENTITY_ANGLE_TOLERANCE && oVisionObj.moPolarcoordinate.moAzimuth.mrAlpha <= 2*Math.PI - mrATENTITY_ANGLE_TOLERANCE){
						
					}				
					return true;
				}
			}
		}
		return false;
	}
	
	
	/*
	 * (horvath) - This method navigates the Fungus-Eater to avoid obstacle
	 * 
	 */
	private void avoidObstacle(clsSensorData poInputs, itfActionProcessor poActionProcessor, HashSet poObstacleTypes){
			moActionQueue.clear();
			if (isObstacle(poInputs, poObstacleTypes)) {
						switch (moRand.nextInt(2)) {
							case 0: // fungus eater is facing the original obstacle and will turn left
									turn(eActionTurnDirection.TURN_LEFT, 1, mnAVOIDANCE_ANGLE);
									break;
							case 1: // fungus eater is facing the original obstacle and will
									// turn right
									turn(eActionTurnDirection.TURN_RIGHT, 1, mnAVOIDANCE_ANGLE);
									break;
						}
						go(eActionMoveDirection.MOVE_FORWARD, mrFORWARD_STEP, mnAVOIDANCE_DISTANCE);
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
	private void turnCheck(eActionTurnDirection peDirection, double prForce, int pnRepetitions){
		meTurnDirection = peDirection;
		mrTurnForce = prForce;
		for(int i=0; i<pnRepetitions; i++){
			moActionQueue.add(Action.NONE);
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
	private void goToEntity(clsSensorData poInputs, itfActionProcessor poActionProcessor, eEntityType entityType){		
		moActionQueue.clear();
		clsPolarcoordinate closest = new clsPolarcoordinate();
		closest.mrLength = -1;
		
		clsVision oVision = (clsVision) poInputs.getSensorExt(eSensorExtType.VISION);
		
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
		switch(moRand.nextInt(2)){
			case(0):	turnCheck(eActionTurnDirection.TURN_RIGHT, mrRANDOM_TURN, mnRANDOM_TIME);
						goCheck(eActionMoveDirection.MOVE_FORWARD, mrFORWARD_STEP, mnRANDOM_TIME);
						break;
			case(1):	turnCheck(eActionTurnDirection.TURN_LEFT, mrRANDOM_TURN, mnRANDOM_TIME);
						goCheck(eActionMoveDirection.MOVE_FORWARD, mrFORWARD_STEP, mnRANDOM_TIME);
						break;
		}
	}
	
	/*
	 * (horvath) - Fungus eater - release uranium ore
	 * 
	 */
	private void release(itfActionProcessor poActionProcessor){
		// FIXME (horvath) moActionQueue.add(Action.FROM_INVENTORY);
		moActionQueue.add(Action.DROP);
		mnInventorySize = 0;
	}
		
	/*
	 * (horvath) - Fungus eater - collect uranium ore in the eatable area
	 * 
	 */
	private void harvest(clsSensorData inputs, itfActionProcessor poActionProcessor){
		clsEatableArea oEatArea = (clsEatableArea) inputs.getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0){
			if( oEatArea.mnTypeOfFirstEntity == eEntityType.URANIUM ){
				moActionQueue.add(Action.PICKUP);
				// FIXME (horvath) moActionQueue.add(Action.TO_INVENTORY);
				mnInventorySize++;
			}
		}
		
	}
	
	/*
	 * (horvath) - Fungus eater - eat fungus in the eatable area
	 * 
	 */	
	private void eat(clsSensorData poInputs, itfActionProcessor poActionProcessor) {
		//eat
		clsEatableArea oEatArea = (clsEatableArea) poInputs.getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0)
		{
				if( oEatArea.mnTypeOfFirstEntity == eEntityType.FUNGUS )
				{
						poActionProcessor.call(new clsActionEat());	
						
						// stop the agent

				}
		}
	}	
	
	


	@Override
	public void process() { 
		stepProcessing( getActionProcessor() );		
	}
	
	
	
	// Inspection methods
	
	public String getMode(){
		return moMode;
	}
	
	public String getLayer(){
		return moLayer;
	}
	
	public Action geActionInProgress(){
		return moActionInProgress;		
	}
}
