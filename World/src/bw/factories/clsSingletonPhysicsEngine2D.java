/**
 * clsSingletonPhysicsEngine2D.java: BW - bw.factories
 * 
 * @author deutsch
 * 11.08.2009, 11:34:22
 */
package bw.factories;

import sim.physics2D.PhysicsEngine2D;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:34:22
 * 
 */
public class clsSingletonPhysicsEngine2D {
    private static clsSingletonPhysicsEngine2D instance = null;
    private PhysicsEngine2D moPhysicsEngine2D = null;    
    private clsSingletonPhysicsEngine2D() {}    
    private static clsSingletonPhysicsEngine2D getInstance() {
       if (instance == null) {
            instance = new clsSingletonPhysicsEngine2D();
       }
       return instance;
    }
	public static void setPhysicsEngine2D(PhysicsEngine2D poPhysicsEngine2D) {
		clsSingletonPhysicsEngine2D.getInstance().moPhysicsEngine2D = poPhysicsEngine2D;
	}    
	public static PhysicsEngine2D getPhysicsEngine2D() {
		return clsSingletonPhysicsEngine2D.getInstance().moPhysicsEngine2D;
	}    
}
