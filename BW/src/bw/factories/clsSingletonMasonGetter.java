/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSingletonMasonGetter {
    /**
     * 
     */
    private static clsSingletonMasonGetter instance = null;
    
    /**
     * 
     */
    private PhysicsEngine2D moPhysicsEngine2D = null;

    /**
     * 
     */
    private Continuous2D moFieldEnvironment = null;
    
    /**
     * 
     */
    private SimState moSimState = null;
    
    /**
     * 
     */
    private clsSingletonMasonGetter() {}

    /**
     * TODO (deutsch) - insert description
     *
     * @return
     */
    private static clsSingletonMasonGetter getInstance() {
       if (instance == null) {
            instance = new clsSingletonMasonGetter();
       }
       return instance;
    }

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poPhysicsEngine2D
	 */
	public static void setPhysicsEngine2D(PhysicsEngine2D poPhysicsEngine2D) {
		clsSingletonMasonGetter.getInstance().moPhysicsEngine2D = poPhysicsEngine2D;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public static PhysicsEngine2D getPhysicsEngine2D() {
		return clsSingletonMasonGetter.getInstance().moPhysicsEngine2D;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poPhysicsEngine2D
	 */
	public static void setFieldEnvironment(Continuous2D poFieldEnvironment) {
		clsSingletonMasonGetter.getInstance().moFieldEnvironment = poFieldEnvironment;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public static Continuous2D getFieldEnvironment() {
		return clsSingletonMasonGetter.getInstance().moFieldEnvironment;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poPhysicsEngine2D
	 */
	public static void setSimState(SimState poSimState) {
		clsSingletonMasonGetter.getInstance().moSimState = poSimState;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public static SimState getSimState() {
		return clsSingletonMasonGetter.getInstance().moSimState;
	}

}
