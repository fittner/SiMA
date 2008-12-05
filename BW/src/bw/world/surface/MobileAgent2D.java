package bw.world.surface;

import sim.physics2D.PhysicsState;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Double2D;

/**
 * This class is the super class for mobile agents which have friction. The friction value of the super class is updated in addFrictionForce() and the method of the super class is called to calculate the force. 
 * The optional learning factor can be swiched on or off in setUseInternalLearning().
 * @author kohlhauser
 *
 */
public abstract class MobileAgent2D extends MobileObject2D
{
	//values identical to superclass but are declared private there 
	protected final double zeroVelocity = 0.01;
	protected final double gravity = 0.1;
	
	protected int currentSurface = 0;
	
	//holds the mofified frictions of the agent for the diffent surfaces
	protected double[][] agentFriction = new double[Surface.NUMBEROFSURFACES][2];  
	//if internal learning is used, this variable determines how fast the agent is learning. The higher the value, the slower he learns. 
	protected int learningFactor = 1000;
	
	protected boolean useInternalLearning = false;
	
	public MobileAgent2D()
	{
		super();
		//initializing the agentFriction table with the values from Surface. 
		for (int i = 0; i < Surface.NUMBEROFSURFACES; i++)
		{
			agentFriction[i][Surface.STATICFRICTION] = Surface.FRICTIONTABLE[i][Surface.STATICFRICTION];
			agentFriction[i][Surface.KINETICFRICTION] = Surface.FRICTIONTABLE[i][Surface.KINETICFRICTION];
		}
	}
	
    /** Calculates and adds the static and dynamic friction forces on the object
     * based on the coefficients of friction.
     * Overwrites inherited method. 
     */
	public void addFrictionForce()
	{
		Double2D velocity = this.getVelocity(); 
    	double velLength = velocity.length();
    	//get position of the agent
    	Double2D position = PhysicsState.getInstance().getPosition(this.index);
    	
    	currentSurface = SurfaceHandler.getInstance().getSurface(position);
    	
    	//velocity determines what kind of friction is used
    	if (velLength < zeroVelocity)
        {
    		//get static friction from surface
    		setCoefficientOfStaticFriction(agentFriction[currentSurface][Surface.STATICFRICTION]);
    		if (useInternalLearning)
    			learnStaticFriction();
        }
    	if (velLength > 0)
        {
    		//get dynamic friction from surface
    		setCoefficientOfFriction(agentFriction[currentSurface][Surface.KINETICFRICTION]);
    		if (useInternalLearning)
    			learnKineticFriction();
        }
    	
    	super.addFrictionForce();
	}
	
	/**
	 * A very basic method to reduce the friction of the agent for a given surface. This shall simulate a learning factor.
	 * The formula is frictionModifier - frictionModifier / 100 * learningFactor.  
	 */
	private void learnStaticFriction()
	{
		double modifier = agentFriction[currentSurface][Surface.STATICFRICTION];
		modifier /= (100 * learningFactor);
		agentFriction[currentSurface][Surface.STATICFRICTION] -= modifier;
	}
	
	/**
	 * A very basic method to reduce the friction of the agent for a given surface. This shall simulate a learning factor.
	 * The formula is frictionModifier - frictionModifier / 100 * learningFactor.  
	 */
	private void learnKineticFriction()
	{
		double modifier = agentFriction[currentSurface][Surface.KINETICFRICTION];
		modifier /= (100 * learningFactor);
		agentFriction[currentSurface][Surface.KINETICFRICTION] -= modifier;
	}
	
	public void setLearningFactor(int factor)
	{
		learningFactor = Math.abs(factor);
	}

	public boolean getUseInternalLearning()
	{
		return useInternalLearning;
	}

	public void setUseInternalLearning(boolean useInternalLearning)
	{
		this.useInternalLearning = useInternalLearning;
	}
}