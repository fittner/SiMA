/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.motionplatform;

import enums.eActionCommandType;

/**
 * Base class for all brain actions 
 * e.g. 
 * clsMotionAction ... movement
 * cls@@@          ... @@@
 *  
 * @author langr
 * 
 */
public class clsBrainAction {

	public eActionCommandType meType;
	
	/**
	 * @param meType
	 */
	public clsBrainAction(eActionCommandType meType) {
		this.meType = meType;
	}

	/**
	 * @return the meType
	 */
	public eActionCommandType getType() {
		return meType;
	}
}
