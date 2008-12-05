package bw;


/**
 * Entity contains basic physical values.
 * TODO: Includes helper to register the object in the MASON physics engine?
 * 
 * sub-classes: clsAnimate, clsInanimate
 * 
 * @author langr
 * 
 */
public class clsEntity {

	@SuppressWarnings("unused")
	private sim.physics2D.physicalObject.PhysicalObject2D moSimulatorEntity = null;
	
	public boolean registerEntity(
			//PhysicsEngine2D objPE, Continuous2D fieldEnvironment
			) throws Exception {
	
		boolean retVal = true;
		
		return retVal;
	}
	
}
