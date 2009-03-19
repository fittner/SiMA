/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.util.ArrayList;

import ARSsim.physics2D.util.clsPose;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.body.motionplatform.clsMotionAction;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eActionCommandType;
import bw.utils.enums.eEntityType;
import sim.display.clsKeyListener;
import sim.physics2D.util.Angle;

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
   
	private static double mrDefaultWeight = 300.0f;
	private static double mrDefaultRadius = 10.0f;
	private static Color moDefaultColor = Color.CYAN;
	private static Color moDefaultHandColor = Color.gray;


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

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.REMOTEBOT;
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#execution()
	 */
	@Override
	public void execution(clsBrainActionContainer poActionList) {
		super.execution(poActionList);
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		super.sensing();		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing(clsBrainActionContainer poActionList) {
		
		//the processing is taken over by the user via keyboard
		
	   	switch( clsKeyListener.getKeyPressed() )
    	{
    	case 38: //up
    		poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_FORWARD) );
    		break;
    	case 40: //down
    		poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.MOVE_BACKWARD) );
    		break;
    	case 37: //rotate_left
    		poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_LEFT) );
    		break;
    	case 39: //rotate_right
    		poActionList.addMoveAction(clsMotionAction.creatAction(eActionCommandMotion.ROTATE_RIGHT) );
    		break;
    	case 65: //'A'
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

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
	}

}