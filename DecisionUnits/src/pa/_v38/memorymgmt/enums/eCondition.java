/**
 * CHANGELOG
 *
 * 16.07.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.07.2012, 13:34:39
 * 
 */
public enum eCondition {
	NULLOBJECT,
	NOTHING,
	
	//Goaltype conditions
	IS_DRIVE_SOURCE,
	IS_PERCEPTIONAL_SOURCE,
	IS_MEMORY_SOURCE,
	
	//Model Temporal condition
	IS_NEW_CONTINUED_GOAL,
	NEED_CONTINUOS_ANALYSIS,
	
	//Initial (deafult)
	NEED_BASIC_ACT_ANALYSIS,		//Needs an act analysis. This option has to be triggered by an action
	NEED_GOAL_FOCUS,				//Needs a set focus
	NEED_INTERNAL_INFO,				//Trigger phantasy
	
	
	FOCUS_ON_SET,					//Focus set on the supportive data structure in the action
	FOCUS_MOVEMENTACTION_SET,		//It has been focused on in front of the agent		
	NEED_PERCEPTIONAL_INFO, 		//Trigger search
	PERFORM_RECOMMENDED_ACTION,		//For acts, perform the recommended action, which is saved with the act
	GOAL_NOT_REACHABLE,				//This is put on acts, where there is no match, in order to exclude the act
	GOAL_MUST_BE_SEARCHED,			//Used for drives, in order to trigger search. This method is used, when nothing else works
	GOAL_REACHABLE_IN_PERCEPTION,
	GOAL_REACHABLE_IN_ACT,
	
	PANIC,
	
	NEED_INTERNAL_INFO_SET,			//Phantasy was already triggered
	
	//Actions - System
	EXECUTED_NONE,
	
	//Actions - Movement single
	EXECUTED_MOVE_FORWARD,
	EXECUTED_MOVE_BACKWARD,
	EXECUTED_TURN_RIGHT,
	EXECUTED_TURN_LEFT,
	
	//Actions - Movement composed
	EXECUTED_SEARCH1,
	EXECUTED_FLEE,
	
	//Actions - Manipulation
	EXECUTED_EXCREMENT,
	EXECUTED_REPRESS,
	EXECUTED_SLEEP,
	EXECUTED_RELAX,
	EXECUTED_EAT,
	EXECUTED_BITE,
	EXECUTED_SUCK, 

	//Actions - Unreal
	EXECUTED_UNREAL_MOVE_TO,
		
	//Internal actions
	EXECUTED_SEND_TO_PHANTASY,	//Send to phantasy
	EXECUTED_PERFORM_BASIC_ACT_ANALYSIS,
	EXECUTED_FOCUS_ON,	//Send to F23
	EXECUTED_FOCUS_MOVE_FORWARD,
	EXECUTED_FOCUS_TURN_RIGHT,
	EXECUTED_FOCUS_TURN_LEFT,
	EXECUTED_FOCUS_SEARCH1;
	
}
