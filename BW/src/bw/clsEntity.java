/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import sim.physics2D.physicalObject.PhysicalObject2D;
import ARSsim.physics2D.physicalObject.itfSetupFunctions;
import bw.actionresponses.clsDefaultEntityActionResponse;
import bw.actionresponses.clsEntityActionResponses;
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
	
	public abstract void sensing();
	public abstract void thinking();
	public abstract void execution();

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