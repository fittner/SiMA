/**
 * clsSingletonConsole.java: BW - bw.factories
 * 
 * @author deutsch
 * 11.08.2009, 11:39:24
 */
package bw.factories;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:39:24
 * 
 */
public class clsSingletonConsole {
    private static clsSingletonConsole instance = null;
    private sim.display.Console moConsole = null;    
    private clsSingletonConsole() {}    
    private static clsSingletonConsole getInstance() {
       if (instance == null) {
            instance = new clsSingletonConsole();
       }
       return instance;
    }
	public static void setDisplay2D(sim.display.Console poConsole) {
		clsSingletonConsole.getInstance().moConsole = poConsole;
	}    
	public static sim.display.Console getDisplay2D() {
		return clsSingletonConsole.getInstance().moConsole;
	}    
}
