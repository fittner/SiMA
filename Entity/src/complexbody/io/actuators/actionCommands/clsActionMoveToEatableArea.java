/**
 * clsMoveToEatableArea.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:27:38
 */
package complexbody.io.actuators.actionCommands;

/**
 * Move to Eatable Area command (from manipulatable area)
 * Parameters
 * 	prForce = The force used to move the object (default ~4)
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionMoveToEatableArea extends clsActionCommand {

	public clsActionMoveToEatableArea() {
	}
	
	@Override
	public String getLog() {
		return "<MoveToEatableArea></MoveToEatableArea>"; 
	}

}
