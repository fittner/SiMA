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

import sim.physics2D.util.Double2D;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsMotionAction;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eActionCommandType;
import bw.utils.enums.eEntityType;


import sim.display.clsKeyListener;

/**
 * Sample implementation of a clsAnimate, having sensors and actuators 
 * (therefore the cycle sensing and executing implemented). The thinking
 * is in this instance out-sourced to the human user directing the entity 
 * across defined keys on her/his keyboard
 * 
 * @author langr
 * 
 */
public class clsRemoteBot extends clsAnimate
    {
    clsCan currentCan;
    
    public clsBotHands e1;
    public clsBotHands e2;
    
    public int mnId;
    
	public clsRemoteBot(Double2D pos, Double2D vel, int pnId)
    {
	super(pos, vel, new sim.physics2D.shape.Circle(10, Color.CYAN), 300, pnId);
	
    // vary the mass with the size
	this.mnId = pnId;
            
    currentCan = null;
            
    }     
    
    public int getId() {
		return mnId;
	}

	public Object domBotState() { return new String[] { "HAVECAN", "APPROACHINGCAN", "RELEASINGCAN", "RETURNINGHOME", "SEARCHING" }; }

	public void setId(int mnId) {
		this.mnId = mnId;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution()
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
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
	public void processing(ArrayList<clsBrainAction> poActionList) {
		
		//the processing is taken over by the user via keyboard
		
	   	switch( clsKeyListener.getKeyPressed() )
    	{
    	case 38: //up
    		poActionList.add(new clsMotionAction(eActionCommandType.MOTION, eActionCommandMotion.MOVE_FORWARD) );
    		break;
    	case 40: //down
    		poActionList.add(new clsMotionAction(eActionCommandType.MOTION, eActionCommandMotion.MOVE_BACKWARD) );
    		break;
    	case 37: //rotate_left
    		poActionList.add(new clsMotionAction(eActionCommandType.MOTION, eActionCommandMotion.ROTATE_LEFT) );
    		break;
    	case 39: //rotate_right
    		poActionList.add(new clsMotionAction(eActionCommandType.MOTION, eActionCommandMotion.ROTATE_RIGHT) );
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