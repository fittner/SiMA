/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package decisionunit.itf.actions;

/**
 * Brain action command types for motion 
 * 
 * @author langr
 * 
 */
public enum eActionCommandMotion {

	UNDEFINED,
	MOVE_FORWARD,
	MOVE_LEFT,
	MOVE_RIGHT,
	MOVE_DIRECTION,
	MOVE_BACKWARD,
	ROTATE_LEFT,
	ROTATE_RIGHT,
	RUN_FORWARD,
	JUMP //just kidding - extend your own motion commands here!
	
}
