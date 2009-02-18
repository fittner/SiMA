/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import bw.physicalObject.eEntityType;


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

	private boolean useSimplePortrayal;
	
	public clsEntity() {
		setEntityType();
		useSimplePortrayal = true;
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
	
	public abstract void sensing();
	public abstract void thinking();
	public abstract void execution();

}