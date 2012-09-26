/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import factories.clsSingletonSimState;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.field.grid.DoubleGrid2D;
import sim.physics2D.PhysicsEngine2D;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSingletonMasonGetter {

	public static void setPhysicsEngine2D(PhysicsEngine2D poPhysicsEngine2D) {
		clsSingletonPhysicsEngine2D.setPhysicsEngine2D(poPhysicsEngine2D);
	}
	public static PhysicsEngine2D getPhysicsEngine2D() {
		return clsSingletonPhysicsEngine2D.getPhysicsEngine2D();
	}
	
	public static void setFieldEnvironment(Continuous2D poFieldEnvironment) {
		clsSingletonField.setField(poFieldEnvironment);
	}
	public static Continuous2D getFieldEnvironment() {
		return clsSingletonField.getField();
	}
	
	//double grid for color map display
	public static void setArousalGridEnvironment(DoubleGrid2D poArousalGrid) {
		clsSingletonGridField.setField(poArousalGrid);
	}
	public static DoubleGrid2D getArousalGridEnvironment() {
		return clsSingletonGridField.getField();
	}
	
	public static void setSimState(SimState poSimState) {
		clsSingletonSimState.setSimState(poSimState);
	}
	public static SimState getSimState() {
		return clsSingletonSimState.getSimState();
	}
	
	public static void setDisplay2D(sim.display.Display2D poDisplay2D) {
		clsSingletonDisplay2D.setDisplay2D(poDisplay2D);
	}
	public static sim.display.Display2D getDisplay2D() {
		return clsSingletonDisplay2D.getDisplay2D();
	}
	
	public static void setConsole(sim.display.Console poConsole) {
		clsSingletonConsole.setDisplay2D(poConsole);
	}
	public static sim.display.Console getConsole() {
		return clsSingletonConsole.getDisplay2D();
	}

}
