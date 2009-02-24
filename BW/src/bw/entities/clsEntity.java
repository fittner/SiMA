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
 * Entity contains basic physical values.
 * TODO: Includes helper to register the object in the MASON physics engine?
 * 
 * sub-classes: clsAnimate, clsInanimate
 * 
 * extends our clsRobot which is a agent class for mason connection
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
	
	public void setPosition(sim.util.Double2D poPos) {
		((itfSetupFunctions)moPhysicalObject2D).setPosition(poPos);
	}
	
	public void setShape(Shape poShape, double poMass) {
		((itfSetupFunctions)moPhysicalObject2D).setShape(poShape, poMass);
	}
	
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution) {
		((itfSetupFunctions)moPhysicalObject2D).setCoefficients(poFriction, poStaticFriction, poRestitution);
	}
	
	public void finalizeSetup() {
		((itfSetupFunctions)moPhysicalObject2D).finalizeSetup();
	}
	
	public abstract void sensing();
	public abstract void thinking();
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