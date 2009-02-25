/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import ARSsim.physics2D.physicalObject.itfSetupFunctions;
import bw.actionresponses.clsDefaultEntityActionResponse;
import bw.actionresponses.clsEntityActionResponses;
import bw.body.motionplatform.clsBrainAction;
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
	
	protected PhysicalObject2D moPhysicalObject2D = null;
	private clsEntityActionResponses moEntityActionResponses;

	private boolean useSimplePortrayal;
	
	public clsEntity() {
		setEntityType();
		useSimplePortrayal = true;
		setEntityActionResponse(new clsDefaultEntityActionResponse());
	}
	
	protected void setEntityActionResponse(clsEntityActionResponses poResponse) {
		setEntityActionResponses(poResponse);
	}

	protected eEntityType meEntityType;
	
	protected abstract void setEntityType();
	
	public eEntityType getEntityType() {
		return meEntityType;
	}
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
	public void setPosition(sim.util.Double2D poPos) {
		((itfSetupFunctions)moPhysicalObject2D).setPosition(poPos);
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
		((itfSetupFunctions)moPhysicalObject2D).setShape(poShape, poMass);
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
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:56
	 *
	 */
	public void finalizeSetup() {
		((itfSetupFunctions)moPhysicalObject2D).finalizeSetup();
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
	public abstract void processing(ArrayList<clsBrainAction> poActionList);
	public abstract void execution(ArrayList<clsBrainAction> poActionList);

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

}