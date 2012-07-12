/**
 * CHANGELOG
 *
 * 07.07.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 07.07.2012, 06:23:11
 * 
 */
public enum eAction {
	//External actions
	MOVE_FORWARD,
	MOVE_BACKWARD,
	TURN_LEFT,
	TURN_RIGHT,
	DEPOSIT,
	SLEEP,
	RELAX,
	EAT,
	
	//Unreal actions
	UNREAL_MOVE_TO,
	
	//Internal actions
	FOCUS_ON,	//Send to F23
	THINK_ON,	//Send to phantasy
	
	//Composed actions
	SEARCH1;
}
