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
	MOVE_BACKWARD,	//Is this still used???
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
	
	//Internal actions
	FOCUS_ON,	//Send to F23
	SEND_TO_PHANTASY,	//Send to phantasy
	PERFORM_BASIC_ACT_ANALYSIS,
	//DO_BASIC_ACT_ANALYSIS,		//Analyse the act
	
	//Temp actions
	EXECUTE_EXTERNAL_ACTION,
	FOCUS_MOVEMENT,
	
	FOCUS_MOVE_FORWARD,
	FOCUS_TURN_RIGHT,
	FOCUS_TURN_LEFT,
	
	FOCUS_SEARCH1,
	
	//Composed actions
	SEARCH1,
	FLEE,
}
