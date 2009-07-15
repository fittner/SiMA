/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.shape.Shape;
import ARSsim.physics2D.util.clsPose;
import bw.entities.clsEntity;
import bw.factories.clsSingletonMasonGetter;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 *
 */
public class clsStationaryObject2D extends sim.physics2D.physicalObject.StationaryObject2D  implements Steppable, itfGetEntity, itfSetupFunctions {
	
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
	
	public void setPose(clsPose poPose) {
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation( this, new sim.util.Double2D(poPose.getPosition().getX(), poPose.getPosition().getY()) );
		setPose(poPose.getPosition(), poPose.getAngle());
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 26.02.2009, 12:00:49
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#getPose()
	 */
	public clsPose getPose() {
		return new clsPose(this.getPosition(), this.getOrientation());
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setShape(sim.physics2D.shape.Shape, double)
	 */
	public void setShape(Shape poShape, double ignored) {
		// TODO Why is there no setShape - corresponding to clsMobileObject2D. Adopt it!
		setShape(poShape);
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setCoefficients(double, double, double)
	 * Note: Stationary objects don't support friction and staticFriction, only restitution.
	 *       Use NaN for the former two.
	 */
	public void setCoefficients(double mustBeNaN1, double mustBeNaN2,
			double poRestitution) {
		if (! Double.isNaN(mustBeNaN1) || ! Double.isNaN(mustBeNaN2))
			throw new java.lang.UnsupportedOperationException("Cannot specify that argument for stationary objects, use NaN!");
		setCoefficientOfRestitution(poRestitution);
	}

	public void step(SimState state) {
		//this block should be distributed to different steps
		moEntity.sensing();
		moEntity.updateInternalState();
		moEntity.processing();
		moEntity.execution();
	}
	
	public Steppable getSteppableSensing() {
		return new Steppable() {
			private static final long serialVersionUID = 6889902215107604312L;
			public void step(SimState state) {
				moEntity.sensing();
			}
		};
	}
	
	public Steppable getSteppableProcessing() {
		return new Steppable() {
			private static final long serialVersionUID = -5218583360606426073L;
			public void step(SimState state) {
				moEntity.processing();
			}
		};
	}
}
