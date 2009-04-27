/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package decisionunit.itf.actions;


import enums.eActionCommandType;

/**
 * message class to send a eat action from the brains thinking-cycle
 * to the body's execution-cycle and therefore (in case of physical 
 * changes) further to the clsMobileObject2D and the physics...
 * 
 * @author muchitsch
 * 
 */
public class clsEatAction extends clsActionCommands {

	
	/**
	 * @param 
	 * @param 
	 */
	public clsEatAction() {
		super(eActionCommandType.EAT);
	}

}
