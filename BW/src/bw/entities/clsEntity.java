/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import ARSsim.physics2D.physicalObject.itfSetupFunctions;
import ARSsim.physics2D.util.clsPose;
import bw.actionresponses.clsDefaultEntityActionResponse;
import bw.actionresponses.clsEntityActionResponses;
import bw.utils.enums.eEntityType;

/**
 * Entity is the baseclass of any object in the BubbleWorld.
 * With the containing PhysicalObject2D it holds the reference to the physical-object within the 
 * mason-framework.
 * 
 *  It supports the automatic redirection of functions for e.g positioning, shape, ... towards the
 *  real physical object in the simulator, that implements the itfSetupFunctions.
 *  
 *  This interface has to be implemented in clsMobileObject2D and clsStaticObject2D
 * 
 * sub-classes: clsMobile, clsStatic
 *  
 * @author langr
 * 
 */
/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public abstract class clsEntity {
	
	protected PhysicalObject2D moPhysicalObject2D;
	private clsEntityActionResponses moEntityActionResponses;
	private float mrMass;
	protected eEntityType meEntityType;
	private int mnId;
	private boolean mnRegistered;
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 26.02.2009, 11:15:36
	 *
	 * @param pnId
	 */
	public clsEntity(int pnId) {
		setId(pnId);
		
		setEntityType();
		
		setEntityActionResponse(new clsDefaultEntityActionResponse());

		moPhysicalObject2D = null;
		mrMass = 0.0f;
		setRegistered(false);
	}	
	
	/**
	 * the entities cycle for perception-deliberation-action
	 * MUST implement these functions
	 *
	 * @author langr
	 * 25.02.2009, 15:12:12
	 *
	 */
	public abstract void updateInternalState();
	public abstract void sensing();
	public abstract void processing();
	public abstract void execution();
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:51
	 *
	 * @return
	 */
	public abstract sim.physics2D.util.Double2D getPosition();
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:33
	 *
	 * @param poPose
	 * @param poStartingVelocity
	 * @param poShape
	 * @param poMass
	 */
	protected abstract void initPhysicalObject2D(clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double poMass);
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:30
	 *
	 */
	protected abstract void setEntityType();

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:27
	 *
	 * @param poResponse
	 */
	protected void setEntityActionResponse(clsEntityActionResponses poResponse) {
		setEntityActionResponses(poResponse);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:25
	 *
	 * @return
	 */
	public eEntityType getEntityType() {
		return meEntityType;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:22
	 *
	 * @param peType
	 * @return
	 */
	public boolean isEntityType(eEntityType peType)
	{
		boolean retVal = false;
		
		if(peType == getEntityType()) {
			retVal = true;
		}

		return retVal;
	}
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:19
	 *
	 * @param poPos
	 */
	public void setPose(clsPose poPose) {
		((itfSetupFunctions)moPhysicalObject2D).setPose(poPose);
	}
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:45
	 *
	 * @param poShape
	 * @param poMass
	 */
	public void setShape(Shape poShape, double poMass) {
		
		//so we can set the shape null in constructor, for ex Stone //TODO (muchitsch) //TODO (langr)
		if( poShape != null ) {
			((itfSetupFunctions)moPhysicalObject2D).setShape(poShape, poMass);
		}
		
	}
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:50
	 *
	 * @param poFriction
	 * @param poStaticFriction
	 * @param poRestitution
	 */
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution) {
		((itfSetupFunctions)moPhysicalObject2D).setCoefficients(poFriction, poStaticFriction, poRestitution);
	}
	


	/**
	 * @param moEntityActionResponses the moEntityActionResponses to set
	 */
	public void setEntityActionResponses(clsEntityActionResponses poEntityActionResponses) {
		this.moEntityActionResponses = poEntityActionResponses;
	}

	/**
	 * @return the moEntityActionResponses
	 */
	public clsEntityActionResponses getEntityActionResponses() {
		return moEntityActionResponses;
	}


	/**
	 * @author deutsch
	 * 25.02.2009, 16:21:36
	 * 
	 * @param mrMass the mrMass to set
	 */
	public void setMass(float mrMass) {
		this.mrMass = mrMass;
	}

	/**
	 * @author deutsch
	 * 25.02.2009, 16:21:36
	 * 
	 * @return the mrMass
	 */
	public float getMass() {
		return mrMass;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:38
	 *
	 * @return
	 */
	public int getId() {	
		return mnId;	
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:36
	 *
	 * @param pnId
	 */
	public void setId(int pnId) {		
		this.mnId = pnId;	
	}

	/**
	 * @author deutsch
	 * 26.02.2009, 11:42:42
	 * 
	 * @param mnRegistered the mnRegistered to set
	 */
	public void setRegistered(boolean mnRegistered) {
		this.mnRegistered = mnRegistered;
	}

	/**
	 * @author deutsch
	 * 26.02.2009, 11:42:42
	 * 
	 * @return the mnRegistered
	 */
	public boolean isRegistered() {
		return mnRegistered;
	}
}