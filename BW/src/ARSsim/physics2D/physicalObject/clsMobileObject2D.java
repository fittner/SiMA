/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import ARSsim.motionplatform.clsMotionAction;
import ARSsim.motionplatform.clsMotionPlatform;
import bw.clsEntity;
import bw.body.brain.clsBrainAction;
import bw.factories.clsSingletonMasonGetter;
import bw.physicalObject.entityParts.clsEntityPartVision;
import bw.sim.clsBWMain;
import bw.utils.enums.eActionCommandType;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsMobileObject2D extends sim.physics2D.physicalObject.MobileObject2D implements Steppable, ForceGenerator, itfGetEntity, itfSetupFunctions {

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

	public void setPosition(sim.util.Double2D poPosition) {
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, poPosition);
	}
	
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution) {
		setCoefficientOfFriction(poFriction);
		setCoefficientOfStaticFriction(poStaticFriction);
		setCoefficientOfRestitution(poRestitution);
	}
	
	public void finalizeSetup()
	{
		clsSingletonMasonGetter.getPhysicsEngine2D().register(this);
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(this);		
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
		moEntity.sensing();
		moEntity.thinking();
		
		//with these 3, physics work!
		Double2D position = this.getPosition();
	    clsBWMain oMainSim = (clsBWMain)state;
	    oMainSim.moGameGridField.setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	    
	    // FIXME: clemens + roland - resetStepInfo should be called at the beginning of this function!
		resetStepInfo();

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
		
		moEntity.execution(oActionList);
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
	
    public boolean hitObject(Object object, DrawInfo2D range)
    {
    	return true;
    }

    public int handleCollision(PhysicalObject2D other, Double2D colPoint)
    {
    	if(!(other instanceof clsEntityPartVision)) {
    		
        	moCollisionList.add(new clsCollidingObject(other, colPoint));
   		
    	}
    	
    	
    	//return 1; // regular collision
    	//return 2; // sticky collision
    	return 1; //happy guessing!
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
