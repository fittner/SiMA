/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import sim.physics2D.PhysicsEngine2D;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSingletonPhysicsEngineGetter {
    /**
     * 
     */
    private static clsSingletonPhysicsEngineGetter instance = null;
    
    /**
     * 
     */
    private PhysicsEngine2D moPhysicsEngine2D = null;
 
    /**
     * 
     */
    private clsSingletonPhysicsEngineGetter() {}

    /**
     * TODO (deutsch) - insert description
     *
     * @return
     */
    private static clsSingletonPhysicsEngineGetter getInstance() {
       if (instance == null) {
            instance = new clsSingletonPhysicsEngineGetter();
       }
       return instance;
    }

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poPhysicsEngine2D
	 */
	public static void setPhysicsEngine2D(PhysicsEngine2D poPhysicsEngine2D) {
		clsSingletonPhysicsEngineGetter.getInstance().moPhysicsEngine2D = poPhysicsEngine2D;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public static PhysicsEngine2D getPhysicsEngine2D() {
		return clsSingletonPhysicsEngineGetter.getInstance().moPhysicsEngine2D;
	}

}
