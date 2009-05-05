/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import inspectors.clsInspectorMapping;

import java.util.ArrayList;

import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import ARSsim.motionplatform.clsMotionPlatform;
import ARSsim.physics2D.util.clsPose;
import bw.entities.clsAnimate;
import bw.entities.clsEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.factories.clsSingletonUniqueIdGenerator;
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
	
	private Inspector moMasonInspector = null;
	
	public clsMobileObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
		moMotionPlatform = new clsMotionPlatform(this);
		moCollisionList = new ArrayList<clsCollidingObject>();
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
	public void step(SimState state) {
		//this block should be distributed to different steps
		moEntity.sensing();
		moEntity.updateInternalState();
		moEntity.processing();
		//moEntity.execution();
		
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
	public void addForce() {
//		System.out.println("Execution # "+clsSingletonUniqueIdGenerator.getCurrentUniqueId());
		moEntity.execution();
		
		//correct rotation-force to emulate static friction for rotation
		addAngularFriction();
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
    
    /**
     * This method simulates an angular-friction that is not supported by the physics-engine.
     * Problem until now: Each clsMobileObject2D has the same angular friction 
     *
     * @author langr
     * 25.03.2009, 09:44:04
     *
     */
    public void addAngularFriction() {
    	//if(moEntity instanceof clsRemoteBot)
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
    
    
    /* Assigning customized MASON-inspectors to specific objects
     * The mapping is defined in the static method clsInspectorMapping.getInspector()
     *
     * @author langr
     * 25.03.2009, 14:57:20
     * 
     * @see sim.portrayal.SimplePortrayal2D#getInspector(sim.portrayal.LocationWrapper, sim.display.GUIState)
     */
    public Inspector getInspector(LocationWrapper wrapper, GUIState state){
		//Override to get constantly updating inspectors = volatile
    	
    	if( moMasonInspector == null)
    	{
    		if (moEntity instanceof clsAnimate) {
    		  moMasonInspector = clsInspectorMapping.getInspector(super.getInspector(wrapper,state), wrapper, state, ((clsAnimate)moEntity).getAgentBody().getBrain().getDecisionUnit());
    		}
    	}
    	return moMasonInspector;
    }
}