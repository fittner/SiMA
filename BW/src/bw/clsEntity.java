/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;

/**
 * Entity contains basic physical values.
 * TODO: Includes helper to register the object in the MASON physics engine?
 * 
 * sub-classes: clsAnimate, clsInanimate
 * 
 * extends our clsRobot which is a agent class for mason connection
 * 
 * @author langr
 * 
 */
public class clsEntity extends ARSsim.robot2D.clsMotionPlatform implements Steppable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2640414297770154233L;
	
	private boolean useSimplePortrayal = true;
	



	@SuppressWarnings("unused")
	private sim.physics2D.physicalObject.PhysicalObject2D moSimulatorEntity = null;
	
	private ConstraintEngine moPhysicsConstraintEngine; //object for the physical world representation
	
	
	public ConstraintEngine getPhysicsConstraintEngine() { return moPhysicsConstraintEngine;}
	public void setPhysicsConstraintEngine(	ConstraintEngine poPhysicsConstraintEngine) {
		this.moPhysicsConstraintEngine = poPhysicsConstraintEngine;
	}


	public boolean registerEntity(
			//PhysicsEngine2D objPE, Continuous2D fieldEnvironment
			) throws Exception {
	
		boolean retVal = true;
		
		return retVal;
	}
	

	/* (non-Javadoc)
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		
	}
}