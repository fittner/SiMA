/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import ARSsim.robot2D.clsBrainAction;
import ARSsim.robot2D.clsMotionAction;
import ARSsim.robot2D.clsMotionPlatform;
import bw.clsEntity;
import bw.sim.clsBWMain;
import bw.utils.enums.eActionCommandType;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsMobileObject2D extends sim.physics2D.physicalObject.MobileObject2D implements Steppable, ForceGenerator{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7732669244848952049L;
	
	private clsEntity moEntity;
	public clsMotionPlatform moMotionPlatform;
	public ArrayList<clsCollidingObject> moCollisionList;
	
	
	public clsMobileObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
		moMotionPlatform = new clsMotionPlatform(this);
		moCollisionList = new ArrayList<clsCollidingObject>();
	}
	
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @return
	 */
	public clsEntity getEntity() {
		// TODO Auto-generated method stub
		return moEntity;
	}
	
	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		resetStepInfo();
		moEntity.sensing();
		moEntity.thinking();
		
		//with these 3 physics work!
		Double2D position = this.getPosition();
	    clsBWMain oMainSim = (clsBWMain)state;
	    oMainSim.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	}
	public void resetStepInfo()
	{
		moCollisionList.clear();
	}
	
	/* (non-Javadoc)
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		// TODO Auto-generated method stub
		
		ArrayList<clsBrainAction> oActionList = new ArrayList<clsBrainAction>();
		
		moEntity.execution();
		// TODO: (langr) --> moEntity.execution(oActionList);
		try
		{
			dispatchBrainActions(oActionList);
		}
		catch( Exception ex )
		{
			System.out.println(ex.getMessage());
		}
	}

    public int handleCollision(PhysicalObject2D other, Double2D colPoint)
    {
    	moCollisionList.add(new clsCollidingObject(other, colPoint));
    	
    	//return 1; // regular collision
    	//return 2; // sticky collision
    	return 0; //happy guessing!
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
    		
    		switch (oCmd.getType())
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
    		moMotionPlatform.moveForward(4.0);
    		break;
    	case MOVE_BACKWARD:
    		moMotionPlatform.backup();
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
    		moMotionPlatform.faceTowardsRelative(new Angle(-1));
    		break;
    	case ROTATE_RIGHT:
    		moMotionPlatform.faceTowardsRelative(new Angle(1));
    		break;
    	case RUN_FORWARD:
    		moMotionPlatform.moveForward(12.0);
    		break;
    	case JUMP:
    		throw new Exception("clsMobileObject2D:dispatchMotion - JUMP not yet implemented");
    		//break;
    	default:
    			break;
    	}
    }
    
}
