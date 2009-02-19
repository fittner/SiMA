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
     * TODO (deutsch) - insert description 
     * 
     * @author deutsch
     * 19.02.2009, 18:45:16
     */
    private static clsSingletonMasonGetter instance = null;
    
    /**
     * TODO (deutsch) - insert description 
     * 
     * @author deutsch
     * 19.02.2009, 18:45:18
     */
    private PhysicsEngine2D moPhysicsEngine2D = null;

    /**
     * TODO (deutsch) - insert description 
     * 
     * @author deutsch
     * 19.02.2009, 18:45:20
     */
    private Continuous2D moFieldEnvironment = null;
    
    /**
     * TODO (deutsch) - insert description 
     * 
     * @author deutsch
     * 19.02.2009, 18:45:06
     */
    private SimState moSimState = null;
    

    /**
     * TODO (deutsch) - insert description 
     * 
     * @author deutsch
     * 19.02.2009, 18:45:26
     *
     */
    private clsSingletonMasonGetter() {}


    /**
     * TODO (deutsch) - insert description
     *
     * @author deutsch
     * 19.02.2009, 18:45:31
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
	 * @author deutsch
	 * 19.02.2009, 18:45:36
	 *
	 * @param poPhysicsEngine2D
	 */
	public static void setPhysicsEngine2D(PhysicsEngine2D poPhysicsEngine2D) {
		clsSingletonMasonGetter.getInstance().moPhysicsEngine2D = poPhysicsEngine2D;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 18:45:40
	 *
	 * @return
	 */
	public static PhysicsEngine2D getPhysicsEngine2D() {
		return clsSingletonMasonGetter.getInstance().moPhysicsEngine2D;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 18:45:45
	 *
	 * @param poFieldEnvironment
	 */
	public static void setFieldEnvironment(Continuous2D poFieldEnvironment) {
		clsSingletonMasonGetter.getInstance().moFieldEnvironment = poFieldEnvironment;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 18:45:49
	 *
	 * @return
	 */
	public static Continuous2D getFieldEnvironment() {
		return clsSingletonMasonGetter.getInstance().moFieldEnvironment;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 18:45:56
	 *
	 * @param poSimState
	 */
	public static void setSimState(SimState poSimState) {
		clsSingletonMasonGetter.getInstance().moSimState = poSimState;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 19.02.2009, 18:46:01
	 *
	 * @return
	 */
	public static SimState getSimState() {
		return clsSingletonMasonGetter.getInstance().moSimState;
	}

}
