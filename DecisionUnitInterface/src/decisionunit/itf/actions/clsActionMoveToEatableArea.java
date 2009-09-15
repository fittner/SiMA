/**
 * clsMoveToEatableArea.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 14:27:38
 */
package decisionunit.itf.actions;

/**
 * Move to Eatable Area command (from manipulatable area)
 * Parameters
 * 	prForce = The force used to move the object (default ~4)
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionMoveToEatableArea implements itfActionCommand {

	private double mrForce;

	public clsActionMoveToEatableArea(double prForce) {
		mrForce=prForce;
	}
	
	public String getLog() {
		return "<MoveToEatableArea>" + mrForce + "</MoveToEatableArea>"; 
	}

	public double getForce() {
		return mrForce;
	}
	public void setForce(double prForce) {
		mrForce=prForce;
	}
}
