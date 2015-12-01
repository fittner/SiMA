/**
 * CHANGELOG
 *
 * 16.07.2012 wendt - File created
 *
 */
package memorymgmt.enums;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.07.2012, 13:34:39
 * 
 */
public enum eCondition {
	// --- System --- //
	NULLOBJECT,
	NOTHING,
	
	// --- Precondition intentions --- //
	START_WITH_FIRST_IMAGE,
	OBSTACLE_SOLVING,
	CYCLIC_ACT,
	
	// --- Goaltype conditions --- //
	IS_DRIVE_SOURCE,
	IS_PERCEPTIONAL_SOURCE,
	IS_MEMORY_SOURCE,
	IS_CONTEXT_SOURCE,

	// --- Decision codelets preprocessing --- //
	IS_CONTINUED_GOAL,
	IS_CONTINUED_PLANGOAL,
	IS_UNPROCESSED_GOAL,
	IS_NEW_GOAL,
	
	// --- Executed actions as preconditions --- //
	//Actions - Movement single
	EXECUTED_MOVE_FORWARD,
	EXECUTED_MOVE_BACKWARD,
	EXECUTED_TURN_RIGHT,
	EXECUTED_TURN_LEFT,
	//Actions - Movement composed
	EXECUTED_SEARCH1,
	EXECUTED_FLEE,
	//Actions - Manipulation
	EXECUTED_DEPOSIT,
	EXECUTED_REPRESS,
	EXECUTED_SLEEP,
	EXECUTED_RELAX,
	EXECUTED_EAT,
	EXECUTED_BEAT,
	EXECUTED_PICKUP,
	EXECUTED_BITE,
	EXECUTED_SUCK,
	EXECUTED_DIVIDE,
	EXECUTED_DROP,
	//SPEAK
	EXECUTED_SPEAK_EAT,
	//Actions - Unreal
	//EXECUTED_UNREAL_MOVE_TO,
	//Internal actions
	EXECUTED_SEND_TO_PHANTASY,	//Send to phantasy
	EXECUTED_PERFORM_BASIC_ACT_ANALYSIS,
	EXECUTED_FOCUS_ON,	//Send to F23
	EXECUTED_FOCUS_MOVE_FORWARD,
	EXECUTED_FOCUS_TURN_RIGHT,
	EXECUTED_FOCUS_TURN_LEFT,
	EXECUTED_FOCUS_SEARCH1,
	EXECUTED_STRAFE_LEFT,
	EXECUTED_STRAFE_RIGHT,
	//Actions - System
	EXECUTED_NONE,
	EXECUTED_REQUEST,
	EXECUTED_OBJECT_TRANSFER,
	EXECUTED_AGREE,
	EXECUTED_DISAGREE,
	//--- Post conditions for actions by decision codelets --- //
	SET_FOCUS_ON,					//Focus set on the supportive data structure in the action
	SET_FOCUS_MOVEMENT,		//It has been focused on in front of the agent		
	SET_INTERNAL_INFO,			//Phantasy was already triggered
	SET_BASIC_ACT_ANALYSIS,
	SET_FOLLOW_ACT,
	SET_MOVEMENT_EXECUTED,
	GOAL_NOT_REACHABLE,				//This is put on acts, where there is no match, in order to exclude the act
	GOAL_REACHABLE,
	GOAL_COMPLETED,
	RESET_GOAL,
	ACT_MATCH_TOO_LOW,		//This is used to suppress the pleasure level for goals, which are not suitable in a certain situation
	INSUFFICIENT_PIMATCH_INFO,
	SET_DECISION_PHASE_COMPLETE,
		
	//--- Preconditions for action codelets set by decision codelets --- //
	//Initial (deafult)
	NEED_BASIC_ACT_ANALYSIS,		//Needs an act analysis. This option has to be triggered by an action
	NEED_GOAL_FOCUS,				//Needs a set focus
	NEED_INTERNAL_INFO,				//Trigger phantasy
	//Other
	NEED_FOCUS_MOVEMENT,
	NEED_MOVEMENT,
	NEED_SEARCH_INFO, 				//Trigger search
	NEED_PERFORM_RECOMMENDED_ACTION,		//For acts, perform the recommended action, which is saved with the act

	//--- Composed codeletes --- //
	GOTO_GOAL_IN_PERCEPTION,
	COMPOSED_CODELET,
	
	ACT_FINISHED,
	
	//Special condition by emotions
	PANIC,
	SPEECH;
	
	
	
	
	

	

	

	
}
