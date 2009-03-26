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
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.body.itfStepProcessing;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.body.motionplatform.clsEatAction;
import bw.body.motionplatform.clsMotionAction;
import bw.entities.clsAnimate;
import bw.entities.clsCan;
import bw.entities.clsEntity;
import bw.mind.clsMind;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eActionCommandType;
import bw.utils.enums.eEntityType;
import bw.utils.enums.eSensorExtType;
import bw.utils.sound.AePlayWave;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 */
public class clsDumbMindA extends clsMind implements itfStepProcessing{

	private boolean mnRoombaIntelligence = true;
	
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

		//followAnObject(poEntity, poActionList);
		
		if(isRoombaIntelligence()) {
			doRobotDance(poAnimate, poActionList);
		}
		
	}
	
	public void followAnObject(clsAnimate poEntity, clsBrainActionContainer poActionList)
	{
		clsSensorVision oVision = (clsSensorVision)(poEntity.moAgentBody.getExternalIO().moSensorExternal.get(eSensorExtType.VISION));
		
		for( PhysicalObject2D oVisionObj : oVision.getViewObj().values() ) {
			
			if( oVisionObj instanceof clsMobileObject2D )
			{
				clsMobileObject2D oMobile = (clsMobileObject2D)oVisionObj;
				clsEntity oEntity = oMobile.getEntity();
				if( oEntity.getEntityType() == eEntityType.CAN)
				{
					clsCan oCan = (clsCan)oEntity;
//					oCan.
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
		else
		{
			clsMotionAction oAction = clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD);
			oAction.setSpeed(2);
			poActionList.addMoveAction(oAction);
		}
		
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
					
					clsEatAction poEatAction = new clsEatAction(eActionCommandType.EAT, oEatenMobileObject.getEntity());
					poActionList.addEatAction( poEatAction );
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
			new AePlayWave("S:\\ARS\\PA\\BWv1\\BW\\src\\resources\\sounds\\r2d2.wav").start();
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


}
