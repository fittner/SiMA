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
	//System
	NULLOBJECT,
	
	//External actions
	MOVE_FORWARD,
	MOVE_BACKWARD,
	TURN_LEFT,
	TURN_RIGHT,
	DEPOSIT,
	SLEEP,
	RELAX,
	EAT,
	NONE,
	
	//Unreal actions
	UNREAL_MOVE_TO,
	
	//Higher level actions
	EXECUTE_EXTERNAL_ACTION,
	
	//Internal actions
	FOCUS_ON,	//Send to F23
	SEND_TO_PHANTASY,	//Send to phantasy
	MOVE_FORWARD_FOCUS,
	TURN_RIGHT_FOCUS,
	TURN_LEFT_FOCUS,
	
	
	//Composed actions
	SEARCH1;
}
