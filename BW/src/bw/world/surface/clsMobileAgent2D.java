package bw.world.surface;

import sim.physics2D.PhysicsState;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Double2D;

@Deprecated
//the learning which distinguishes this class from clsMobileFrictionObject2D is not needed anymore
// therefore clsMobileFrictionObject2D is to be used for all Objects and agents. 

/**
 * This class is the super class for mobile agents which have friction. The friction value of the
 *  super class is updated in addFrictionForce() and the method of the super class is called to
 *  calculate the force. 
 * The optional learning factor can be swiched on or off in setUseInternalLearning().
 * @author kohlhauser
 *
 */
public abstract class clsMobileAgent2D extends MobileObject2D
{
	/**
	 * DOCUMENT (tobias) - insert description 
	 * 
	 * @author tobias
	 * Jul 24, 2009, 5:46:40 PM
	 */
	private static final long serialVersionUID = 6025374867186032786L;
	//values identical to superclass but are declared private there 
	protected final double mrZeroVelocity = 0.01;
	protected final double mrGravity = 0.1;
	
	protected int mnCurrentSurface = 0;
	
	//holds the modified frictions of the agent for the different surfaces
	protected double[][] mrAgentFriction = new double[itfSurface.NUMBEROFSURFACES][2];  
	//if internal learning is used, this variable determines how fast the agent is learning. The higher the value, the slower he learns. 
	protected int mnLearningFactor = 1000;
	
	protected boolean mbUseInternalLearning = false;
	
	public clsMobileAgent2D()
	{
		super();
		//initializing the agentFriction table with the values from Surface. 
		for (int i = 0; i < itfSurface.NUMBEROFSURFACES; i++)
		{
			mrAgentFriction[i][itfSurface.STATICFRICTION] = itfSurface.FRICTIONTABLE[i][itfSurface.STATICFRICTION];
			mrAgentFriction[i][itfSurface.KINETICFRICTION] = itfSurface.FRICTIONTABLE[i][itfSurface.KINETICFRICTION];
		}
	}
	
    /** Calculates and adds the static and dynamic friction forces on the object
     * based on the coefficients of friction.
     * Overwrites inherited method. 
     */
	@Override
	public void addFrictionForce()
	{
		Double2D oVelocity = this.getVelocity(); 
    	double rVelLength = oVelocity.length();
    	//get position of the agent
    	Double2D oPosition = PhysicsState.getInstance().getPosition(this.index);
    	
    	mnCurrentSurface = clsSurfaceHandler.getInstance().getSurface(oPosition);
    	
    	//velocity determines what kind of friction is used
    	if (rVelLength < mrZeroVelocity)
        {
    		//get static friction from surface
    		setCoefficientOfStaticFriction(mrAgentFriction[mnCurrentSurface][itfSurface.STATICFRICTION]);
/*    		if (mbUseInternalLearning)
    			learnStaticFriction();*/
        }
    	if (rVelLength > 0)
        {
    		//get dynamic friction from surface
    		setCoefficientOfFriction(mrAgentFriction[mnCurrentSurface][itfSurface.KINETICFRICTION]);
/*    		if (mbUseInternalLearning)
    			learnKineticFriction();*/
        }
    	
    	super.addFrictionForce();
	}
	

	public void setLearningFactor(int pnFactor)
	{
		mnLearningFactor = Math.abs(pnFactor);
	}

	public boolean getUseInternalLearning()
	{
		return mbUseInternalLearning;
	}

	public void setUseInternalLearning(boolean pbUseInternalLearning)
	{
		this.mbUseInternalLearning = pbUseInternalLearning;
	}
}