/**
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.mind.ai;

import java.util.Iterator;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.body.itfStepProcessing;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.body.motionplatform.clsEatAction;
import bw.body.motionplatform.clsMotionAction;
import bw.entities.clsAnimate;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsEntity;
import bw.mind.clsMind;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eActionCommandType;
import bw.utils.enums.eEntityType;
import bw.utils.enums.eSensorExtType;
import bw.utils.sound.AePlayWave;
import bw.utils.tools.clsFood;
import bw.utils.tools.clsVectorCalculation;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 */
public class clsDumbMindA extends clsMind implements itfStepProcessing{

	private boolean mnRoombaIntelligence = true;
	private boolean mnCollisionAvoidance = false;
	
	public void clsDumbMind()
	{
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 10:27:38
	 * 
	 * @see bw.body.itfStepProcessing#stepProcessing(bw.entities.clsAnimate, bw.body.motionplatform.clsBrainActionContainer)
	 */
	@Override
	public void stepProcessing(clsAnimate poAnimate,
			clsBrainActionContainer poActionList) {

		if(isCollisionAvoidance()) {
			followAnObject(poAnimate, poActionList);
		}
		
		if(isRoombaIntelligence()) {
			doRobotDance(poAnimate, poActionList);
		}
		
		eat(poAnimate, poActionList);
	}
	
	public void followAnObject(clsAnimate poEntity, clsBrainActionContainer poActionList)
	{
		clsSensorVision oVision = (clsSensorVision)(poEntity.moAgentBody.getExternalIO().moSensorExternal.get(eSensorExtType.VISION));
		
		for( PhysicalObject2D oVisionObj : oVision.getViewObj().values() ) {
			
			if( oVisionObj instanceof clsMobileObject2D )
			{
				clsMobileObject2D oMobile = (clsMobileObject2D)oVisionObj;
				clsEntity oEntity = oMobile.getEntity();
				if( oEntity.getEntityType() == eEntityType.CAKE)
				{
					clsCake oCan = (clsCake)oEntity;
					Double2D oTarget = oCan.getPosition();
					Double2D oMyPos = poEntity.getPosition();
					
					Double2D oTargetVector = oTarget.subtract(oMyPos);
					
					//this is the absolute direction (angle from x-axis) the agent has to rotate to
					double oAbsAngle = clsVectorCalculation.getDirectionPolar(oTargetVector);
					//this is the direction, the agent is looking to
					double oOrientation = poEntity.getMobileObject2D().getOrientation().radians;
					
					if( (oOrientation-0.1d) <= oAbsAngle && oAbsAngle <= (oOrientation+0.1d) )
					{
						//walk ahead to reach the can  
						clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
						oAction.setSpeed(2.5);
						poActionList.addMoveAction(oAction);
					}
					else if( !((oAbsAngle+Math.PI) < oOrientation ) && (oAbsAngle < oOrientation-0.1  || ((oOrientation+Math.PI) < oAbsAngle)) )
					{
						//rotate left
						poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
					}
					else
					{
						//rotate right
						poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
					}
					
				}
			}
		}
	}
	
	public void doRobotDance(clsAnimate poEntity, clsBrainActionContainer poActionList)
	{
		//move
		clsSensorBump oBump = (clsSensorBump)(poEntity.moAgentBody.getExternalIO().moSensorExternal.get(eSensorExtType.BUMP));
		
		if( oBump.isBumped() )
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

	/**
	 * TODO (langr) - insert description
	 *
	 * @author langr
	 * 02.04.2009, 13:04:55
	 *
	 * @param poEntity
	 * @param poActionList
	 */
	private void eat(clsAnimate poEntity,
			clsBrainActionContainer poActionList) {
		//eat
		clsSensorEatableArea oEatArea = (clsSensorEatableArea)(poEntity.moAgentBody.getExternalIO().moSensorExternal.get(eSensorExtType.EATABLE_AREA));
		if(oEatArea.getViewObj() != null)
		{

			Iterator<PhysicalObject2D> itr = oEatArea.getViewObj().values().iterator(); 			
			while(itr.hasNext())
			{
				PhysicalObject2D oPhysicalObj = itr.next();
				if(  oPhysicalObj instanceof clsMobileObject2D)
				{
					clsMobileObject2D oEatenMobileObject = (clsMobileObject2D)oPhysicalObj; 
					clsEntity oEatenEntity = oEatenMobileObject.getEntity();
					
					//TODO cm only cakes for now
					if(  oEatenEntity instanceof clsCake)
					{
						clsEatAction oEatAction = new clsEatAction(eActionCommandType.EAT, oEatenEntity);
						poActionList.addEatAction(oEatAction);
						
						// Roland: when the agent reaches the cake - ist stops 
						// (deactivating the following-the-food logic)
						setCollisionAvoidance(false);

					}
				}
			}
		}
	}

	/**
	 * @author langr
	 * 25.03.2009, 10:36:11
	 * 
	 * @param moRoombaIntelligence the moRoombaIntelligence to set
	 */
	public void setRoombaIntelligence(boolean moRoombaIntelligence) {
		this.mnRoombaIntelligence = moRoombaIntelligence;
		
		//this is for fun only (roland)
		if( moRoombaIntelligence) {
			new AePlayWave(bw.sim.clsBWMain.msArsPath + "/src/resources/sounds/r2d2.wav").start();
		}
		
	}

	/**
	 * @author langr
	 * 25.03.2009, 10:36:11
	 * 
	 * @return the moRoombaIntelligence
	 */
	public boolean isRoombaIntelligence() {
		return mnRoombaIntelligence;
	}

	/**
	 * @author langr
	 * 01.04.2009, 16:37:12
	 * 
	 * @param mnCollisionAvoidance the mnCollisionAvoidance to set
	 */
	public void setCollisionAvoidance(boolean mnCollisionAvoidance) {
		this.mnCollisionAvoidance = mnCollisionAvoidance;
		
		//this is for fun only (roland)
		if( mnCollisionAvoidance) {
			new AePlayWave(bw.sim.clsBWMain.msArsPath + "/src/resources/sounds/utini.wav").start();
		}
	}

	/**
	 * @author langr
	 * 01.04.2009, 16:37:12
	 * 
	 * @return the mnCollisionAvoidance
	 */
	public boolean isCollisionAvoidance() {
		return mnCollisionAvoidance;
	}


}
