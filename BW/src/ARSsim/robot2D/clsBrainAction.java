/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.robot2D;

import bw.utils.enums.eActionCommandType;

/**
 * TODO (langr) - insert description 
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
