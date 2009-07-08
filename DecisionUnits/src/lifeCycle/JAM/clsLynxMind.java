package lifeCycle.JAM;

import decisionunit.clsBaseDecisionUnit;
//import decisionunit.itf.actions.clsActionEat;
//import decisionunit.itf.actions.clsActionKill;
import decisionunit.itf.actions.clsActionMove;
import decisionunit.itf.actions.clsActionTurn;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;
import enums.eActionMoveDirection;
import enums.eActionTurnDirection;
import enums.eEntityType;
import enums.eSensorExtType;
//import sim.display.clsKeyListener;
import simple.remotecontrol.clsRemoteControl; //for testing purpose only! remove after test


public class clsLynxMind extends clsRemoteControl  {

	@Override
	public void process(itfActionProcessor poActionProcessor) {

		//===========TESTING PURPOSE ONLY!
	   	switch( getKeyPressed() )
    	{
    	case 75: //'K'
    		killHare(poActionProcessor);
    		break;
    	default:
    		super.process(poActionProcessor);
    		break;
    	}
	  //=========== END
	}
	
	public void doLynxThinking(itfActionProcessor poActionProcessor) {
		
		clsVisionEntry oVisibleHare = checkVision();
		clsBump oBump = (clsBump) getSensorData().getSensorExt(eSensorExtType.BUMP);
		
		if( checkEatableArea() ) {
			killHare(poActionProcessor);
			eatHare(poActionProcessor);
		}
		
		else if( oVisibleHare != null ) {
			followHare(poActionProcessor, oVisibleHare);
		}
		else if( oBump.mnBumped )
		{
			handleColision(poActionProcessor);
		}
		else {
//			seekHare();
		}
		
	}
	
	public boolean checkEatableArea() 	{
		boolean nRetVal = false;
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0 && oEatArea.mnTypeOfFirstEntity == eEntityType.HARE)
		{
			nRetVal = true;
		}
		return nRetVal;
	}
	
	public clsVisionEntry checkVision() {
		clsVisionEntry oRetVal = null;
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			if( oVisionObj.mnEntityType == eEntityType.HARE)
			{
				oRetVal = oVisionObj;
				break;
			}
		}
		return oRetVal;
	}
	
	public void followHare(itfActionProcessor poActionProcessor, clsVisionEntry poVisionObj) {
		
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
		poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,4));
		poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
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
