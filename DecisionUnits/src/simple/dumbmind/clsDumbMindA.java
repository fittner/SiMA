/**
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package simple.dumbmind;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionCommandContainer;
import decisionunit.itf.actions.clsEatAction;
import decisionunit.itf.actions.clsMotionAction;
import enums.eActionCommandMotion;
import enums.eActionCommandType;
import enums.eEntityType;
import enums.eSensorExtType;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsVision;
import decisionunit.itf.sensors.clsVisionEntry;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 */
public class clsDumbMindA extends clsBaseDecisionUnit {

	private boolean mnRoombaIntelligence = true;
	private boolean mnCollisionAvoidance = false;
	
	public clsDumbMindA() {
	}
	
	public clsActionCommandContainer stepProcessing() {
		
		clsActionCommandContainer oActionList = new clsActionCommandContainer();

		if(isCollisionAvoidance()) {
			followAnObject(oActionList);
		}
		
		if(isRoombaIntelligence()) {
			doRobotDance(oActionList);
		}
		
		eat(oActionList);
		
		return oActionList;
	}
	
	public void followAnObject(clsActionCommandContainer poActionList)
	{
		clsVision oVision = (clsVision) getSensorData().getSensorExt(eSensorExtType.VISION);
		
		for( clsVisionEntry oVisionObj : oVision.getList() ) {
			
				if( oVisionObj.mnEntityType == eEntityType.CAKE)
				{

					double rAngle = oVisionObj.moPolarcoordinate.moAzimuth.mrAlpha;

					
					if( rAngle < 0.1 || rAngle > (2*Math.PI - 0.1))
					{
						//walk ahead to reach the can  
						clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
						oAction.setSpeed(2.5);
						poActionList.addMoveAction(oAction);
					}
					else if( rAngle >= 0 && rAngle < Math.PI )
					{
						//rotate right
						poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
					}
					else
					{
						//rotate left
						poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
					}
					
					break;
					
				}
		}
	}	
	
	private void eat(clsActionCommandContainer poActionList) {
		//eat
		clsEatableArea oEatArea = (clsEatableArea) getSensorData().getSensorExt(eSensorExtType.EATABLE_AREA);
		if(oEatArea.mnNumEntitiesPresent > 0)
		{

				if( oEatArea.mnTypeOfFirstEntity == eEntityType.CAKE )
				{
						clsEatAction oEatAction = new clsEatAction();
						poActionList.addEatAction(oEatAction);
						
						// Roland: when the agent reaches the cake - ist stops 
						// (deactivating the following-the-food logic)
						setCollisionAvoidance(false);

				}
		}
	}	
	
	public void doRobotDance(clsActionCommandContainer poActionList)
	{
		//move
		clsBump oBump = (clsBump) getSensorData().getSensorExt(eSensorExtType.BUMP);
		
		if( oBump.mnBumped )
		{
			poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
			poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
		
		}
		else if( !isCollisionAvoidance() )
		{
			clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
			oAction.setSpeed(2.5);
			poActionList.addMoveAction(oAction);
		}
	}	

	public void setRoombaIntelligence(boolean moRoombaIntelligence) {
		this.mnRoombaIntelligence = moRoombaIntelligence;
		
		//this is for fun only (roland)
//		if( moRoombaIntelligence) {
//			new AePlayWave(bw.sim.clsBWMain.msArsPath + "/src/resources/sounds/r2d2.wav").start();
//		}
		
	}


	public void setCollisionAvoidance(boolean mnCollisionAvoidance) {
		this.mnCollisionAvoidance = mnCollisionAvoidance;
		
		//this is for fun only (roland)
//		if( mnCollisionAvoidance) {
//			new AePlayWave(bw.sim.clsBWMain.msArsPath + "/src/resources/sounds/utini.wav").start();
//		}
	}

	public boolean isRoombaIntelligence() {
		return mnRoombaIntelligence;
	}

	public boolean isCollisionAvoidance() {
		return mnCollisionAvoidance;
	}

	@Override
	public clsActionCommandContainer process() {
		clsActionCommandContainer oCommands = stepProcessing();
		
		//clsActionCommandContainer oCommands = new clsActionCommandContainer();
		//oCommands.addMoveAction( clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD));
		
		return oCommands;
	}


}
