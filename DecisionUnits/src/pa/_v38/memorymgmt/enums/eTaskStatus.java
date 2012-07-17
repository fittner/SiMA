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
public enum eTaskStatus {
	NULLOBJECT,
	NOTHING,
	
	NEED_ATTENTION,					//The goal must get any attention by selecting it in decision making
	NEED_FOCUS,						//Needs a set focus
	FOCUS_ON_SET,					//Focus set on the supportive data structure in the action
	FOCUS_MOVE_FORWARD_SET,			//It has been focused on in front of the agent
	NEED_PERCEPTIONAL_INFO, 		//Trigger search
	NEED_INTERNAL_INFO,				//Trigger phantasy
	NEED_INTERNAL_INFO_SET;	
}
