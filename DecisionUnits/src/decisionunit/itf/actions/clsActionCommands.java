/**
* @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package decisionunit.itf.actions;

import decisionunit.itf.actions.eActionCommandType;

/**
 * Base class for all brain actions 
 * e.g. 
 * clsMotionAction ... movement
 * cls@@@          ... @@@
 *  
 * @author langr
 * 
 */
public class clsActionCommands {

	public eActionCommandType meType;
	
	/**
	 * @param meType
	 */
	public clsActionCommands(eActionCommandType meType) {
		this.meType = meType;
	}

	/**
	 * @return the meType
	 */
	public eActionCommandType getType() {
		return meType;
	}
}
