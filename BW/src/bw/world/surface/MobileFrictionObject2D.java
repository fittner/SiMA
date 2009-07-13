package bw.world.surface;

import sim.physics2D.PhysicsState;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Double2D;

/**
 * This class is used for mobile objects which are not agents. It updates the friction depending on what surface this object is moved.
 * @author kohlhauser
 *
 */
public abstract class MobileFrictionObject2D extends MobileObject2D
{
	//values identical to superclass but are declared private there 
	protected final double zeroVelocity = 0.01;
	protected final double gravity = 0.1;
	
	protected double staticObjectFriction = 0.0;
	protected double kineticObjectFriction = 0.0;
	
    /** Calculates and adds the static and dynamic friction forces on the object
     * based on the coefficients of friction.
     * Overwrites inherited method. 
     */
	@Override
	public void addFrictionForce()
	{
		//getting necessary information for the calculation
		Double2D velocity = this.getVelocity(); 
    	double velLength = velocity.length();
    	Double2D position = PhysicsState.getInstance().getPosition(this.index);
    	
    	if (velLength < zeroVelocity)
        {
    		//get static friction from surface and add the own friction
    		setCoefficientOfStaticFriction(SurfaceHandler.getInstance().getStaticFriction(position) + staticObjectFriction);
        }
    	
    	if (velLength > 0)
        {
    		//get dynamic friction from surface and add the own friction
    		setCoefficientOfFriction(SurfaceHandler.getInstance().getKineticFriction(position) + kineticObjectFriction);
        }
    	
    	//the friction values are set and the method of the superclass is called for the calculation. 
    	super.addFrictionForce();
	}

	public double getStaticObjectFriction()
	{
		return staticObjectFriction;
	}

	public void setStaticObjectFriction(double staticObjectFriction)
	{
		this.staticObjectFriction = staticObjectFriction;
	}

	public double getKineticObjectFriction()
	{
		return kineticObjectFriction;
	}

	public void setKineticObjectFriction(double kineticObjectFriction)
	{
		this.kineticObjectFriction = kineticObjectFriction;
	}
}
