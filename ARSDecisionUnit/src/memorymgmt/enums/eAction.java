/**
 * CHANGELOG
 *
 * 07.07.2012 wendt - File created
 *
 */
package memorymgmt.enums;

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
	TURN_RIGHT90,
	TURN_LEFT90,
	
	DEPOSIT, 
	SLEEP,
	RELAX,
	EAT,
	BITE,
	SUCK, 
	REPRESS,
	PICK_UP,
	DROP,
	NONE,
	BEAT,
	DIVIDE,
	GIVE,
	WAIT,
	//Actions with Objects
	GOTO,
	FLEE,
	LEECH,
    
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
	
	REQUEST,
	OBJECT_TRANSFER,
	AGREE,
	DISAGREE,
	
	FOCUS_SEARCH1,
	
	//Composed actions
	SEARCH1,
	STRAFE_RIGHT,
	STRAFE_LEFT,
	SPEAK,
	SPEAK_EAT,
	SPEAK_WELCOME,
    SHARE_FOOD;
	
	public static eAction getAction(String poAction) {
		return eAction.valueOf(poAction);
	}
}
