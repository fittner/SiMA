/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.portrayal.DrawInfo2D;
import ARSsim.motionplatform.clsMotionPlatform;
import ARSsim.physics2D.util.clsPose;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.entities.clsRemoteBot;
import bw.factories.clsSingletonMasonGetter;
import bw.physicalObjects.sensors.clsEntityPartVision;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsMobileObject2D extends sim.physics2D.physicalObject.MobileObject2D implements Steppable, ForceGenerator, itfGetEntity, itfSetupFunctions {

	private static final long serialVersionUID = -7732669244848952049L;
	
	private clsEntity moEntity;
	public clsMotionPlatform moMotionPlatform;
	public ArrayList<clsCollidingObject> moCollisionList;
	public clsBrainActionContainer moActionList;
	
	/**
	 * @author muchitsch
	 * 24.03.2009, 12:54:30
	 * 
	 * @return the moActionList
	 */
	public clsBrainActionContainer getBrainActionList() {
		return moActionList;
	}

	public clsMobileObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
		moMotionPlatform = new clsMotionPlatform(this);
		moCollisionList = new ArrayList<clsCollidingObject>();
		moActionList = new clsBrainActionContainer();
	}

	/* (non-Javadoc)
	 *
	 * sets the position of the mobile object in the masons field-environment 
	 *
	 * @author langr
	 * 25.02.2009, 13:38:18
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setPosition(sim.util.Double2D)
	 */
	public void setPose(clsPose poPose) {
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation( this, new sim.util.Double2D(poPose.getPosition().getX(), poPose.getPosition().getY()) );
		setPose(poPose.getPosition(), poPose.getAngle());
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 26.02.2009, 12:00:49
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#getPose()
	 */
	public clsPose getPose() {
		return new clsPose(this.getPosition(), this.getOrientation());
	}
	
	/* (non-Javadoc)
	 *
	 * sets the physical frictions of the object
	 * 
	 * friction = friction of the object with the background surface. 0 is no friction
	 * staticFriction = static friction of the object with the background surface. 0 is no static friction
	 * restitution = elasticity of an object - determines how much momentum is conserved when objects collide
	 *
	 * @author langr
	 * 25.02.2009, 14:15:20
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setCoefficients(double, double, double)
	 */
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution) {
		setCoefficientOfFriction(poFriction);
		setCoefficientOfStaticFriction(poStaticFriction);
		setCoefficientOfRestitution(poRestitution);
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
	 * 
	 * The step function, called by the mason framework for each registered object starts the 
	 * perception and thinking cycle of the ARS-Entity with the calls:
	 * - update internal state
	 * - sensing
	 * - processing
	 * 
	 * The cycle is completed in the addForce, where the 
	 * -execution
	 * of the entity is called 
	 * 
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		
		moEntity.updateInternalState();
		moEntity.sensing();
		
		moActionList.clearAll();
		moEntity.processing(moActionList);
		
		//with these 2, physics work!
		sim.physics2D.util.Double2D position = this.getPosition();
	    clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, new sim.util.Double2D(position.x, position.y));
	    
	    // FIXME: clemens + roland - resetStepInfo should be called at the beginning of this function!
		resetStepInfo();
	}
	
	
	/**
	 * TODO (langr) - insert description
	 *
	 * local variables are reseted here
	 *
	 * @author langr
	 * 25.02.2009, 14:51:43
	 *
	 */
	public void resetStepInfo()
	{
		moCollisionList.clear();
	}
	
	/* (non-Javadoc)
	 * 
	 * The cycle (sensing-thinking-executing) is completed here, where the 
	 * -execution
	 * of the entity is called
	 * 
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		moEntity.execution(moActionList);
		
		//correct rotation-force to emulate static friction for rotation
		setStaticRotationFriction();
	}
	
    /* (non-Javadoc)
     *
     * supports message handling for the mouse-doubleclick / inspectors
     *
     * @author langr
     * 25.02.2009, 14:54:30
     * 
     * @see sim.portrayal.SimplePortrayal2D#hitObject(java.lang.Object, sim.portrayal.DrawInfo2D)
     */
    public boolean hitObject(Object object, DrawInfo2D range)
    {
    	return true;
    }

    public int handleCollision(PhysicalObject2D other, sim.physics2D.util.Double2D colPoint)
    {
    	if(!(other instanceof clsEntityPartVision)) {
    		
        	moCollisionList.add(new clsCollidingObject(other, colPoint));
    	}
    	   	
    	//return 2; // sticky collision
    	//return 1; // regular collision
    	//return 0; //happy guessing!
    	return 1; 
	}
    
    public void setStaticRotationFriction() {
    	if(moEntity instanceof clsRemoteBot)
    	{
	    	double nAngularVel = getAngularVelocity();
	    	double nToAdd = 0;
	    	if( nAngularVel > 0.002d ) {
	    		nToAdd = -0.3;
		    	addTorque(nToAdd);
	    	}
	    	else if( nAngularVel < -0.002d ) {
	    		nToAdd = 0.3;
		    	addTorque(nToAdd);
	    	}
	    	else
	    	{
	    		setAngularVelocity(0.0);
	    	}
    	}
    }
}