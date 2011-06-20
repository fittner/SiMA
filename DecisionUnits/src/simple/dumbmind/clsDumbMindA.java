/**
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package simple.dumbmind;

import config.clsBWProperties;
import decisionunit.clsBaseDecisionUnit;
import du.enums.eActionMoveDirection;
import du.enums.eActionTurnDirection;
import du.enums.eDecisionType;
import du.enums.eEntityType;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.clsActionEat;
import du.itf.actions.clsActionMove;
import du.itf.actions.clsActionTurn;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsEatableAreaEntry;
import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsStaminaSystem;
import du.itf.sensors.clsVision;
import du.itf.sensors.clsVisionEntry;

/**
 *  (langr) - insert description 
 * 
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 */
public class clsDumbMindA extends clsBaseDecisionUnit {

	private boolean mnRoombaIntelligence = true;
	private boolean mnCollisionAvoidance = false;
	
	public clsDumbMindA(String poPrefix, clsBWProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsBaseDecisionUnit.getDefaultProperties(poPrefix) );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);

	}
	
//	//: (langr) new config
//	public clsDumbMindA(String poPrefix, clsBWProperties poProp) {
//		applyProperties(poPrefix, poProp);
//	}
//	
//	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);
//
//		return oProp;
//	}
//	
//	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);
//		
//	}
	
	
	public void stepProcessing(itfActionProcessor poActionProcessor) {
		
		if(isCollisionAvoidance()) {
			followAnObject(poActionProcessor);
		}
		
		if(isRoombaIntelligence()) {
			doRobotDance(poActionProcessor);
		}
		
		eat(poActionProcessor);
		
	}
	
	public void followAnObject(itfActionProcessor poActionProcessor)
	{
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		
		for( clsSensorExtern oVisionObj : oVision.getDataObjects() ) {
			
				if(((clsVisionEntry)oVisionObj).getEntityType() == eEntityType.CAKE)
				{

					double rAngle = ((clsVisionEntry)oVisionObj).getPolarcoordinate().moAzimuth.mrAlpha;

					
					if( rAngle < 0.1 || rAngle > (2*Math.PI - 0.1))
					{
						//walk ahead to reach the can  
						//clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
						//oAction.setSpeed(2.5);
						//poActionList.addMoveAction(oAction);
						poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
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
					
					break;
					
				}
		}
	}	
	
	private void eat(itfActionProcessor poActionProcessor) {
		//eat
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		
	
		for(clsSensorExtern oEatAreaObj : oEatArea.getDataObjects() ) {
			if( ((clsEatableAreaEntry)oEatAreaObj).getEntityType() == eEntityType.CAKE ){
			   //&& ((clsEatableAreaEntries)oEatAreaObj). .moColorOfFirstEntity != null && oEatArea.moColorOfFirstEntity.equals(Color.orange))
				//clsEatAction oEatAction = new clsEatAction();
				//poActionList.addEatAction(oEatAction);
				poActionProcessor.call(new clsActionEat());	 
				
				// Roland: when the agent reaches the cake - ist stops 
				// (deactivating the following-the-food logic)
				setCollisionAvoidance(false);
			}
		}
	}	
	
	public void doRobotDance(itfActionProcessor poActionProcessor)
	{
		//move
		clsBump oBump = (clsBump) getSensorData().getSensorExt(eSensorExtType.BUMP);
		clsStaminaSystem oSSys = (clsStaminaSystem) getSensorData().getSensorInt(eSensorIntType.STAMINA);
		
		if (oSSys.getStaminaValue()<0.2) return;
		
		if( oBump.getBumped() )
		{
			//poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
			//poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
			poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,1.0));
			poActionProcessor.call(new clsActionTurn(eActionTurnDirection.TURN_LEFT));
		}
		else if( !isCollisionAvoidance() )
		{
			//clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
			//oAction.setSpeed(2.5);
			//poActionList.addMoveAction(oAction);
			poActionProcessor.call(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
		}
	}	

	public void setRoombaIntelligence(boolean moRoombaIntelligence) {
		this.mnRoombaIntelligence = moRoombaIntelligence;
		
		//this is for fun only (roland)
//		if( moRoombaIntelligence) {
//			new AePlayWave(clsGetARSPath.getArsPath() + "/src/resources/sounds/r2d2.wav").start();
//		}
		
	}


	public void setCollisionAvoidance(boolean mnCollisionAvoidance) {
		this.mnCollisionAvoidance = mnCollisionAvoidance;
		
		//this is for fun only (roland)
//		if( mnCollisionAvoidance) {
//			new AePlayWave(clsGetARSPath.getArsPath() + "/src/resources/sounds/utini.wav").start();
//		}
	}

	public boolean isRoombaIntelligence() {
		return mnRoombaIntelligence;
	}

	public boolean isCollisionAvoidance() {
		return mnCollisionAvoidance;
	}

	@Override
	public void process() { 
		stepProcessing(  getActionProcessor()  );
		
		//clsActionCommandContainer oCommands = new clsActionCommandContainer();
		//oCommands.addMoveAction( clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD));
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 02.09.2010, 15:54:24
	 * 
	 * @see decisionunit.clsBaseDecisionUnit#setDecisionUnitType()
	 */
	@Override
	protected void setDecisionUnitType() {
		meDecisionType = eDecisionType.DUMB_MIND_A;
		
	}


}
