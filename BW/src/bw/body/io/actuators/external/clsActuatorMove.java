/**
 * @author langr
 * 25.02.2009, 17:40:16
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.external;

import java.util.ArrayList;

import sim.physics2D.util.Angle;

import bw.body.io.clsBaseIO;
import bw.body.io.clsSensorActuatorBaseExt;
import bw.body.io.actuators.itfActuatorUpdate;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsMotionAction;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.utils.enums.eActionCommandType;

/**
 * class is the interface that communicates to clsMotionPlatform to let our entity move.
 * 
 * @author langr
 * 25.02.2009, 17:40:16
 * 
 */
public class clsActuatorMove extends clsSensorActuatorBaseExt implements itfActuatorUpdate {

	private clsEntity moEntity;
	
	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 25.02.2009, 17:40:59
	 *
	 * @param poBaseIO
	 */
	public clsActuatorMove(clsEntity poEntity, clsBaseIO poBaseIO) {
		super(poBaseIO);

		moEntity = poEntity;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:41:16
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:41:16
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:41:16
	 * 
	 * @see bw.body.io.actuators.itfActuatorUpdate#updateActuatorData(java.util.ArrayList)
	 */
	@Override
	public void updateActuatorData(ArrayList<clsBrainAction> poActionList) {

		// TODO: (langr) --> moEntity.execution(oActionList);
		try
		{
			dispatchBrainActions(poActionList);
		}
		catch( Exception ex )
		{
			System.out.println(ex.getMessage());
		}
		
	}
	
    // ******************************   ******************************************
    // * HELPER for motion commands *   ************
    // ******************************   ******************************************
    // TODO: RL outsource in other class!
    
    /**
     * langr - Transforms brain actions into physical force commands to impact physics engine
     *
     * @param poActionList
     */
    public void dispatchBrainActions(ArrayList<clsBrainAction> poActionList) throws Exception
    {
    	for (clsBrainAction oCmd : poActionList) {
    		
    		eActionCommandType eType = oCmd.getType(); 
    		switch (eType)
    		{
    		case MOTION:
    			dispatchMotion(oCmd);
    			break;
    		default:
    			break;
    		}
    	}
    	
    	
    }
    
    public void dispatchMotion(clsBrainAction poCmd) throws Exception
    {
    	if( !(poCmd instanceof clsMotionAction) )
    		return;
    	
    	clsMotionAction oMotion = (clsMotionAction)poCmd;
    	
    	switch( oMotion.getMotionType() )
    	{
    	case MOVE_FORWARD:
    		((clsMobile)moEntity).getMobile().moMotionPlatform.moveForward(4.0);
    		break;
    	case MOVE_BACKWARD:
    		((clsMobile)moEntity).getMobile().moMotionPlatform.backup();
    		break;
    	case MOVE_DIRECTION:
    		throw new Exception("clsMobileObject2D:dispatchMotion - MOVE_DIRECTION not yet implemented");
    		//break;
    	case MOVE_LEFT:
    		throw new Exception("clsMobileObject2D:dispatchMotion - MOVE_LEFT not yet implemented");
    		//break;
    	case MOVE_RIGHT:
    		throw new Exception("clsMobileObject2D:dispatchMotion - MOVE_RIGHT not yet implemented");
    		//break;
    	case ROTATE_LEFT:
    		((clsMobile)moEntity).getMobile().moMotionPlatform.faceTowardsRelative(new Angle(-1));
    		break;
    	case ROTATE_RIGHT:
    		((clsMobile)moEntity).getMobile().moMotionPlatform.faceTowardsRelative(new Angle(1));
    		break;
    	case RUN_FORWARD:
    		((clsMobile)moEntity).getMobile().moMotionPlatform.moveForward(12.0);
    		break;
    	case JUMP:
    		throw new Exception("clsMobileObject2D:dispatchMotion - JUMP not yet implemented");
    		//break;
    	default:
    			break;
    	}
    }

}
