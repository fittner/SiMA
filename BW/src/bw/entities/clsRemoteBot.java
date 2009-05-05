/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.Iterator;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.util.clsPose;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.body.motionplatform.clsEatAction;
import bw.body.motionplatform.clsMotionAction;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.factories.clsSingletonUniqueIdGenerator;
import enums.eActionCommandMotion;
import enums.eActionCommandType;
import enums.eEntityType;
import enums.eSensorExtType;
import sim.display.clsKeyListener;
import sim.physics2D.util.Angle;
import sim.physics2D.physicalObject.PhysicalObject2D;

/**
 * Sample implementation of a clsAnimate, having sensors and actuators 
 * (therefore the cycle sensing and executing implemented). The thinking
 * is in this instance out-sourced to the human user directing the entity 
 * across defined keys on her/his keyboard
 * 
 * @author langr
 * 
 */
public class clsRemoteBot extends clsAnimate  {
    private clsBotHands moBotHand1;
    private clsBotHands moBotHand2;
       
	private static double mrDefaultWeight = 100.0f;
	private static double mrDefaultRadius = 10.0f;
	private static Color moDefaultColor = Color.CYAN;
	private static Color moDefaultHandColor = Color.gray;
	
	private int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();

//	private Array moCapturedKeys;

	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 26.02.2009, 11:29:23
	 *
	 * @param pnId
	 * @param poStartingPose
	 * @param poStartingVelocity
	 */
	public clsRemoteBot(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity) {
		super(pnId, poStartingPose, poStartingVelocity, new sim.physics2D.shape.Circle(clsRemoteBot.mrDefaultRadius, clsRemoteBot.moDefaultColor), clsRemoteBot.mrDefaultWeight);
		
		addBotHands();
	}
	
	private clsBotHands addHand(double offsetX, double offsetY) {
        double x = getPosition().x;
        double y = getPosition().y;
        sim.physics2D.util.Double2D oPos;
                    
        oPos = new sim.physics2D.util.Double2D(x + offsetX, y + offsetY);
        
        clsBotHands oHand = new clsBotHands(oPos, new sim.physics2D.util.Double2D(0, 0), 1, moDefaultHandColor);
        
        return oHand;
	}
	
	
	
	/**
	 * To be called AFTER clsRemoteBot has been initialized and registered to mason 
	 * 
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:38:59
	 *
	 */
	private void addBotHands() {
		//FIXME hands are only added correctly if - and only if - direction of bot is 0 ...
		Angle oDirection = new Angle(getMobileObject2D().getOrientation().radians); //TODO add getDirection to clsEntity
		getMobileObject2D().setPose(getPosition(), new Angle(0));
		
		moBotHand1 = addHand(12, 6);
		moBotHand2 = addHand(12, -6);

		getMobileObject2D().setPose(getPosition(), oDirection);
	}
	
	public clsBotHands getBotHand1() {
		return moBotHand1;
	}
	public clsBotHands getBotHand2() {
		return moBotHand2;
	}
	
	public clsEntityPartVision getVision()
	{
		return ((clsSensorVision)this.moAgentBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.VISION)).getMoVisionArea(); 
	}
	
	public clsEntityPartVision getEatableAreaVision()
	{
		return ((clsSensorEatableArea)this.moAgentBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.EATABLE_AREA)).getMoVisionArea(); 
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.REMOTEBOT;
	}
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		moActionList.clearAll();

		//the processing is taken over by the user via keyboard
		
	   	switch( clsKeyListener.getKeyPressed() )
    	{
    	case 38: //up
    		moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD) );
    		break;
    	case 40: //down
    		moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
    		break;
    	case 37: //rotate_left
    		moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
    		break;
    	case 39: //rotate_right
    		moActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
    		break;
    	case 65: //'A'
    		break;
    	case 69: //'E'
    		eat(this, moActionList);
    		break;
    	case 83: //'S'
//            if(botState==HAVECAN)
//            {
//	    		objCE.unRegisterForceConstraint(pj);                            
//	            botState = APPROACHINGCAN;
//	            objCE.removeNoCollisions(getMobile(), currentCan.getMobile());
//	            objCE.removeNoCollisions(e1, currentCan.getMobile());
//	            objCE.removeNoCollisions(e2, currentCan.getMobile());
//	            currentCan.visible = true;
//            }
    		break;
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
	private void eat(clsAnimate poEntity, clsBrainActionContainer poActionList) {
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
						

					}
				}
			}
		}
	}


	public void execution() {
		super.execution();
/*		
		if (moTemp.length() > 0) {
	      // System.out.println(mnUniqueId+": "+moTemp);
	      moTemp = "";
		}
*/
	}
}