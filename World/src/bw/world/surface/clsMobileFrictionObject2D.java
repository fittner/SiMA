package bw.world.surface;

import sim.physics2D.PhysicsState;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Double2D;

//@deprecated
//content of this class will be moved to ARSsim.physics2D.physicalObject.clsMobileObject2D

/**
 * This class is used for mobile objects which are not agents. It updates the friction depending on what surface this object is moved.
 * @author kohlhauser
 *
 */
public abstract class clsMobileFrictionObject2D extends MobileObject2D
{
	/**
	 * @author deutsch
	 * Jul 24, 2009, 5:47:04 PM
	 */
	private static final long serialVersionUID = 304803507627904954L;
	//values identical to superclass but are declared private there 
	protected final double mrZeroVelocity = 0.01;
	protected final double mrGravity = 0.1;
	
	//If the object adds its own friction to the surface friction to make it easier (negative) or harder (positive)
	protected double mrStaticObjectFriction = 0.0;
	protected double mrKineticObjectFriction = 0.0;
	
	protected int nLastPositionX = 0, nLastPositionY = 0, nCurrentPositionX = 0, nCurrentPositionY = 0;
	
	protected clsSurfaceHandler moSurfaceHandler = clsSurfaceHandler.getInstance();
	
    /** Calculates and adds the static and dynamic friction forces on the object
     * based on the coefficients of friction.
     * Overwrites inherited method. 
     */
	@Override
	public void addFrictionForce()
	{
		//getting necessary information for the calculation
		Double2D oVelocity = this.getVelocity(); 
    	double rVelLength = oVelocity.length();
    	Double2D oPosition = PhysicsState.getInstance().getPosition(this.index);
    	
    	//calculate if position has changed enough to poll again for the friction coefficient
    	//may speed it up a little
    	nCurrentPositionX = (int) oPosition.getX();
    	nCurrentPositionY = (int) oPosition.getY();
    	
    	if (nCurrentPositionX != nLastPositionX || nCurrentPositionY != nLastPositionY)
    	{
	    	if (rVelLength < mrZeroVelocity)
	        {
	    		//get static friction from surface and add the own friction
	    		setCoefficientOfStaticFriction(moSurfaceHandler.getStaticFriction(nCurrentPositionX, nCurrentPositionY) + mrStaticObjectFriction);
	        }
	    	
	    	if (rVelLength > 0)
	        {
	    		//get dynamic friction from surface and add the own friction
	    		setCoefficientOfFriction(moSurfaceHandler.getKineticFriction(nCurrentPositionX, nCurrentPositionY) + mrKineticObjectFriction);
	        }
	    	nLastPositionX = nCurrentPositionX;
	    	nLastPositionY = nCurrentPositionY;
    	}
    	//the friction values are set and the method of the superclass is called for the calculation. 
    	super.addFrictionForce();
	}

	public double getStaticObjectFriction()
	{
		return mrStaticObjectFriction;
	}

	public void setStaticObjectFriction(double prStaticObjectFriction)
	{
		this.mrStaticObjectFriction = prStaticObjectFriction;
	}

	public double getKineticObjectFriction()
	{
		return mrKineticObjectFriction;
	}

	public void setKineticObjectFriction(double prKineticObjectFriction)
	{
		this.mrKineticObjectFriction = prKineticObjectFriction;
	}
}
