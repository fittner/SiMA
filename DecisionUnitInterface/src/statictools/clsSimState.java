/**
 * clsSimState.java: DecisionUnitInterface - statictools
 * 
 * @author deutsch
 * 23.04.2011, 12:40:28
 */
package statictools;

import sim.engine.SimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 12:40:28
 * 
 */
public class clsSimState {
    private static clsSimState instance = null;
    private SimState moSimState = null;
    
    private clsSimState() {}
    private static clsSimState getInstance() {
       if (instance == null) {
            instance = new clsSimState();
       }
       return instance;
    }

	public static SimState getSimState() {
		return clsSimState.getInstance().moSimState;
	}
	
	public static void setSimState(SimState poSimState) {
		clsSimState.getInstance().moSimState = poSimState;
	}	
	
	public static long getSteps() {
		return clsSimState.getInstance().moSimState.schedule.getSteps();
	}
}
