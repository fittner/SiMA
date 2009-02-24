/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import sim.physics2D.shape.Shape;
import bw.entities.clsEntity;
import bw.factories.clsSingletonMasonGetter;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsStationaryObject2D extends sim.physics2D.physicalObject.StationaryObject2D implements itfGetEntity, itfSetupFunctions {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 915012100712508497L;
	
	private clsEntity moEntity;
	
	public clsStationaryObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
	}
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @return
	 */
	public clsEntity getEntity() {
		// TODO Auto-generated method stub
		return moEntity;
	}	
	
	public void setPosition(sim.util.Double2D poPosition) {
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(this, poPosition);
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#finalizeSetup()
	 */
	@Override
	public void finalizeSetup() {
		clsSingletonMasonGetter.getPhysicsEngine2D().register(this);
		
		// TODO: check if there is no need to register the steppable... 
		//clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(this);			
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setShape(sim.physics2D.shape.Shape, double)
	 */
	@Override
	public void setShape(Shape poShape, double poMass) {
		// TODO Why is there no setShape - corresponding to clsMobileObject2D. Adopt it!
		
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setCoefficients(double, double, double)
	 */
	@Override
	public void setCoefficients(double poFriction, double poStaticFriction,
			double poRestitution) {
		setCoefficients(poFriction, poStaticFriction, poRestitution);
	}
		
}
