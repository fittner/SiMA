package lifeCycle.IfThenElse;


import java.awt.Color;

import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsEatableAreaEntry;
import decisionunit.itf.sensors.clsEnergy;
import decisionunit.itf.sensors.clsSensorExtern;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsSensorRingSegmentEntries;
import decisionunit.itf.sensors.clsVisionEntries;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;
import enums.eSensorIntType;
import enums.eTriState;

import simple.remotecontrol.clsRemoteControl; //for testing purpose only! remove after test

public class clsHareMind extends clsRemoteControl { //should be derived from clsBaseDecisionUit

	@Override
	public void process() {

//		//===========USE THIS CODE FOR REMOTE CONTROL
//		super.process(poActionProcessor);
//		
//	   	switch( getKeyPressed() )
//    	{
//    	case 75: //'K'
//    		break;
//    	}
//	  //=========== END
	   	
		//===========USE THIS CODE FOR AUTOMATIC IF/THEN-ACTION
		doHareThinking(  getActionProcessor() );
		//=========== END
	}
	
	
	
	private int mnStepCounter = 0; //local step counter to measure the steps until the next action has to be selected
	private int mnStepsToRepeatLastAction = 20; //after these steps the next action is considered
	private  static int mnRepeatRange = 100; //random generator goes from 0 to mnRepeatRange
	private int mnCurrentActionCode = 0; //default move forward
	private static double mnHungryThreasholed = 4.5; //energy level, where hare gets hungry
	
	public void doHareThinking(itfActionProcessor poActionProcessor) {
		
		clsSensorRingSegmentEntries oVisibleCarrot = checkVision();
		clsBump oBump = (clsBump) getSensorData().getSensorExt(eSensorExtType.BUMP);
		
		if( checkEatableArea() && isHungry() ) {
			eatCarrot(poActionProcessor);
		} else if( oVisibleCarrot != null && isHungry()) {
			reachCarrot(poActionProcessor, oVisibleCarrot);
			//todo: flee when lynx in range!!!
		} else if( oBump.mnBumped ) {
			handleColision(poActionProcessor);
		} else {
			seekCarrot(poActionProcessor);
		}
	}
	
	public boolean checkEatableArea() 	{
		
		boolean nRetVal = false;

		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		
		if (oEatArea.moEntries.size() > 0) {
			clsEatableAreaEntry oEntry = (clsEatableAreaEntry)oEatArea.moEntries.get(0);
			if (oEntry.mnEntityType == eEntityType.CARROT && oEntry.mnIsConsumeable == eTriState.TRUE) {
				nRetVal = true;
			}
		}
		return nRetVal;
	}
	
	private boolean isCarrotOrange(clsSensorRingSegmentEntries oVisionObj) {
		if (oVisionObj.mnEntityType == eEntityType.CARROT && 
				((clsVisionEntries)oVisionObj).moColor != null &&
				((clsVisionEntries)oVisionObj).moColor.equals(Color.orange) 
				) {
			return true;
		}
		
		return false;
	}
	
	public clsSensorRingSegmentEntries checkVision() {
		clsSensorRingSegmentEntries oRetVal = null;
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsSensorExtern oVisionObj : oVision.getList() ) {
			if( isCarrotOrange((clsEatableAreaEntry)oVisionObj) )
			{
				oRetVal = (clsEatableAreaEntry)oVisionObj;
				break;
			}
		}
		return oRetVal;
	}
	
	private boolean isHungry() {

		boolean nRetVal = false;
		clsEnergy oStomach = (clsEnergy) getSensorData().getSensorInt(eSensorIntType.ENERGY);

		if( oStomach.mrEnergy <= mnHungryThreasholed ) {
			nRetVal = true;
		}
		
		return nRetVal;
	}
	
	public void seekCarrot(itfActionProcessor poActionProcessor) {
		
		if(	mnStepCounter >= mnStepsToRepeatLastAction ) {
			mnStepCounter = 0; //reset stepcounter
			mnStepsToRepeatLastAction = (int)(Math.random()*mnRepeatRange); //generate new action repeat time to avoid always the same boring behaviour;
			
			mnCurrentActionCode = (int)(Math.random()*4); //determine new random action
		}
		else
		{
			//do as the action command says...
			switch(mnCurrentActionCode) {
			case 0:
				poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,4));
				break;
			case 1:
	    		//poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,4));
				//don't move backwards - its better for seeking to only move forward... 
				poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,4));
				break;
			case 2:
				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
				break;
			case 3:
				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
				break;
			case 5:
				poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,4));
				poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
				break;
			default:
				poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,4));
				break;
			}
		}
		mnStepCounter++;		
	}
	
	public void reachCarrot(itfActionProcessor poActionProcessor, clsSensorRingSegmentEntries poVisionObj) {
		
		double rAngle = poVisionObj.moPolarcoordinate.moAzimuth.mrAlpha;
		
		if( rAngle < 0.1 || rAngle > (2*Math.PI - 0.1))
		{
			//walk ahead to reach the can  
			poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,2.5f));
		}
		else if( rAngle >= 0 && rAngle < Math.PI )
		{
			//rotate right
			poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_RIGHT));
		}
		else
		{
			//rotate left
			poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
		}
	}
	
	public void handleColision(itfActionProcessor poActionProcessor) {
		mnStepCounter = 0;
		mnStepsToRepeatLastAction = 60;
		mnCurrentActionCode = 5;
	}
	
	//public void 
	
	/**
	 * Eats the dead hare  
	 * 
	 * @param poActionProcessor
	 */
	public void eatCarrot(itfActionProcessor poActionProcessor) {
		super.eat(poActionProcessor, eEntityType.CARROT);
	}
	
}
