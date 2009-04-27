/**
 * @author langr
 * 18.03.2009, 14:55:08
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package decisionunit.itf.actions;

import java.util.ArrayList;

/**
 * Contains the actions that have been initiated from the deliberative layer 
 * to be processed. 
 * Any type of action is represented by an arrayList for faster access
 * in the distribution to the actuators. 
 * 
 * @author langr
 * 18.03.2009, 14:55:08
 * 
 */
public class clsActionCommandContainer {

	private ArrayList<clsEatAction> moEatAction;
	private ArrayList<clsMotionAction> moMotionAction;
	
	/**
	 * ctor only ensures created ArrayList
	 * fill the lists   
	 * 
	 * @author langr
	 * 18.03.2009, 17:02:28
	 *
	 */
	public clsActionCommandContainer()
	{
		moEatAction = new ArrayList<clsEatAction>();
		moMotionAction = new ArrayList<clsMotionAction>();
	}

	public final ArrayList<clsEatAction> getEatAction()	{
		return  moEatAction;
	}
	
	public final ArrayList<clsMotionAction> getMoveAction()	{
		return moMotionAction;
	}
	
	public void addEatAction(clsEatAction poEatAction)	{
		moEatAction.add(poEatAction);
	}
	
	public void addMoveAction(clsMotionAction poMotionAction)	{
		moMotionAction.add(poMotionAction);
	}
	
	public void clearAll() 	{
		moEatAction.clear();
		moMotionAction.clear();
	}
}
