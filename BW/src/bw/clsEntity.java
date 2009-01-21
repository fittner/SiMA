/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

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
public class clsEntity extends ARSsim.robot2D.clsRobot {

	@SuppressWarnings("unused")
	private sim.physics2D.physicalObject.PhysicalObject2D moSimulatorEntity = null;
	
	public boolean registerEntity(
			//PhysicsEngine2D objPE, Continuous2D fieldEnvironment
			) throws Exception {
	
		boolean retVal = true;
		
		return retVal;
	}
	
}
