/**
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.mind.ai;

import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.body.itfStepProcessing;
import bw.body.io.sensors.external.clsSensorBump;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.body.motionplatform.clsMotionAction;
import bw.entities.clsAnimate;
import bw.entities.clsCan;
import bw.entities.clsEntity;
import bw.mind.clsMind;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eEntityType;
import bw.utils.enums.eSensorExtType;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 19.03.2009, 15:57:25
 * 
 */
public class clsDumbMindA extends clsMind implements itfStepProcessing{

	private boolean moRoombaIntelligence = true;
	
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
		
		doRobotDance(poAnimate, poActionList);
		
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
		
	}

	/**
	 * @author langr
	 * 25.03.2009, 10:36:11
	 * 
	 * @param moRoombaIntelligence the moRoombaIntelligence to set
	 */
	public void setRoombaIntelligence(boolean moRoombaIntelligence) {
		this.moRoombaIntelligence = moRoombaIntelligence;
	}

	/**
	 * @author langr
	 * 25.03.2009, 10:36:11
	 * 
	 * @return the moRoombaIntelligence
	 */
	public boolean isRoombaIntelligence() {
		return moRoombaIntelligence;
	}


}
