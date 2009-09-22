package lifeCycle.IfThenElse;

//import decisionunit.itf.actions.clsActionEat;
//import decisionunit.itf.actions.clsActionKill;
import java.awt.Color;

import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsEnergy;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsSensorRingSegmentEntries;
import decisionunit.itf.sensors.clsVisionEntries;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;
import enums.eSensorIntType;
//import sim.display.clsKeyListener;
import simple.remotecontrol.clsRemoteControl; //for testing purpose only! remove after test


public class clsLynxMind extends clsRemoteControl  {

	@Override
	public void process() {

//		//===========USE THIS CODE FOR REMOTE CONTROL
//	   	switch( getKeyPressed() )
//    	{
//    	case 75: //'K'
//    		killHare(poActionProcessor);
//    		break;
//    	default:
//    		super.process(poActionProcessor);
//    		break;
//    	}
//	  	//=========== END
		
		//===========USE THIS CODE FOR AUTOMATIC IF/THEN-ACTION
		doLynxThinking( getActionProcessor() );
		//=========== END
	}
	
	private int mnStepCounter = 0; //local step counter to measure the steps until the next action has to be selected
	private int mnStepsToRepeatLastAction = 20; //after these steps the next action is considered
	private  static int mnRepeatRange = 100; //random generator goes from 0 to mnRepeatRange
	private int mnCurrentActionCode = 0; //default move forward
	private static double mnHungryThreasholed = 7; //energy level, where hare gets hungry
	
	public void doLynxThinking(itfActionProcessor poActionProcessor) {
		clsSensorRingSegmentEntries oVisibleHare = checkVision();
		clsBump oBump = (clsBump) getSensorData().getSensorExt(eSensorExtType.BUMP);
		
		if( checkEatableArea() && isHungry() ) {
			if(((clsVisionEntries)oVisibleHare).moColor.equals(Color.red)) {
				eatHare(poActionProcessor);
			} else {				
				killHare(poActionProcessor);
			}
		} else if( oVisibleHare != null && isHungry() ) {
			followHare(poActionProcessor, oVisibleHare);
		} else if( oBump.mnBumped )	{
			handleColision(poActionProcessor);
		} else {
			seekHare(poActionProcessor);
		}
	}
	
	public boolean checkEatableArea() 	{
		boolean nRetVal = false;
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0 && oEatArea.mnTypeOfFirstEntity == eEntityType.HARE && !oEatArea.moColorOfFirstEntity.equals(Color.BLACK))
		{
			nRetVal = true;
		}
		return nRetVal;
	}
	
	public clsSensorRingSegmentEntries checkVision() {
		clsSensorRingSegmentEntries oRetVal = null;
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsSensorRingSegmentEntries oVisionObj : oVision.getList() ) {
			if( oVisionObj.mnEntityType == eEntityType.HARE && !((clsVisionEntries)oVisionObj).moColor.equals(Color.BLACK))
			{
				oRetVal = oVisionObj;
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
	
	public void seekHare(itfActionProcessor poActionProcessor) {
		
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
	
	public void followHare(itfActionProcessor poActionProcessor, clsSensorRingSegmentEntries poVisionObj) {
		
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
	public void eatHare(itfActionProcessor poActionProcessor) {
		super.eat(poActionProcessor, eEntityType.HARE);
	}
	
	/**
	 * Hurts the entity with 4 hit-points - using calsActionKill as command-processor interface 
	 * 
	 * @param poActionProcessor
	 */
	protected void killHare(itfActionProcessor poActionProcessor) {
		super.kill(poActionProcessor, eEntityType.HARE);
	}
}
