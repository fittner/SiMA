/**
 * clsSingletonDisplay2D.java: BW - bw.factories
 * 
 * @author deutsch
 * 11.08.2009, 11:37:54
 */
package bw.factories;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:37:54
 * 
 */
public class clsSingletonDisplay2D {
    private static clsSingletonDisplay2D instance = null;
    private sim.display.Display2D moDisplay2D = null;    
    private clsSingletonDisplay2D() {}    
    private static clsSingletonDisplay2D getInstance() {
       if (instance == null) {
            instance = new clsSingletonDisplay2D();
       }
       return instance;
    }
	public static void setDisplay2D(sim.display.Display2D poDisplay2D) {
		clsSingletonDisplay2D.getInstance().moDisplay2D = poDisplay2D;
	}    
	public static sim.display.Display2D getDisplay2D() {
		return clsSingletonDisplay2D.getInstance().moDisplay2D;
	}    
}
