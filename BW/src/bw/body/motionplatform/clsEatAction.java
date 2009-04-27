/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.motionplatform;

import enums.eActionCommandType;
import bw.entities.clsEntity;

/**
 * message class to send a motion action from the brains thinking-cycle
 * to the body's execution-cycle and therefore (in case of physical 
 * changes) further to the clsMobileObject2D and the physics...
 * 
 * @author muchitsch
 * 
 */
public class clsEatAction extends clsBrainAction{

	private clsEntity moEatenEntity = null;
	
	/**
	 * @param meType
	 * @param meMotionType
	 */
	public clsEatAction(eActionCommandType meType, clsEntity poEatenEntity) {
		super(meType);
		this.moEatenEntity = poEatenEntity;
	}

	// region: getter/setter
	
	/**
	 * @return the mnSpeed
	 */
	public clsEntity getEatenEntity() {
		return moEatenEntity;
	}

	/**
	 * @param mnSpeed the mnSpeed to set
	 */
	public void setEatenEntity(clsEntity poEatenEntity) {
		this.moEatenEntity = poEatenEntity;
	}



}
