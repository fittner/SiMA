/**
 * clsSingletonSimState.java: BW - bw.factories
 * 
 * @author deutsch
 * 11.08.2009, 11:32:34
 */
package factories;

import sim.engine.SimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:32:34
 * 
 */
public class clsSingletonSimState {
    private static clsSingletonSimState instance = null;
    private SimState moSimState = null;    
    private clsSingletonSimState() {}    
    private static clsSingletonSimState getInstance() {
       if (instance == null) {
            instance = new clsSingletonSimState();
       }
       return instance;
    }
	public static void setSimState(SimState poSimState) {
		clsSingletonSimState.getInstance().moSimState = poSimState;
	}    
	public static SimState getSimState() {
		return clsSingletonSimState.getInstance().moSimState;
	}    
}
