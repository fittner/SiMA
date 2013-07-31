/**
 * 
 */
package ARSsim.physics2D.physicalObject;

import java.awt.image.BufferedImage;

import du.enums.eFacialExpression;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.shape.Shape;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.display.GUIState;
import ARSsim.physics2D.util.clsPose;
import bw.entities.base.clsEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.factories.eImages;
import bw.inspector.interfaces.itfEntityInspectorFactory;

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
	
	private itfEntityInspectorFactory moMasonInspectorFactory = null;
	
	public clsStationaryObject2D(clsEntity poEntity)
	{
		moEntity = poEntity;
	}
	
	/**
	 * returns the clsEntry
	 *
	 * @return
	 */
	@Override
	public clsEntity getEntity() {
		// TODO (muchitsch) - Auto-generated method stub
		return moEntity;
	}	
	
	@Override
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
	@Override
	public clsPose getPose() {
		return new clsPose(this.getPosition(), this.getOrientation());
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setShape(sim.physics2D.shape.Shape, double)
	 */
	@Override
	public void setShape(Shape poShape, double ignored) {
		// TODO Why is there no setShape - corresponding to clsMobileObject2D. Adopt it!
		setShape(poShape);
	}

	/* (non-Javadoc)
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setCoefficients(double, double, double)
	 * Note: Stationary objects don't support friction and staticFriction, only restitution.
	 *       Use NaN for the former two.
	 */
	@Override
	public void setCoefficients(double mustBeNaN1, double mustBeNaN2,
			double poRestitution) {
		if (! Double.isNaN(mustBeNaN1) || ! Double.isNaN(mustBeNaN2))
			throw new java.lang.UnsupportedOperationException("Cannot specify that argument for stationary objects, use NaN!");
		setCoefficientOfRestitution(poRestitution);
	}

	@Override
	@Deprecated
	public void step(SimState state) {
		//this block should be distributed to different steps
		moEntity.sensing();
		moEntity.updateInternalState();
		moEntity.processing();
		moEntity.exec();
	}
	
	public Steppable getSteppableSensing() {
		return new Steppable() {
			private static final long serialVersionUID = 6889902215107604312L;
			@Override
			public void step(SimState state) {
				moEntity.sensing();
				moEntity.updateEntityInternals();
			}
		};
	}
	
	public Steppable getSteppableProcessing() {
		return new Steppable() {
			private static final long serialVersionUID = -5218583360606426073L;
			@Override
			public void step(SimState state) {
				moEntity.processing();
			}
		};
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * supports message handling for the mouse-doubleclick / inspectors
	 * 
	 * @author langr 25.02.2009, 14:54:30
	 * 
	 * @see sim.portrayal.SimplePortrayal2D#hitObject(java.lang.Object,
	 * sim.portrayal.DrawInfo2D)
	 */
	@Override
	public boolean hitObject(Object object, DrawInfo2D range) {
		return true;
	}
	
	/*
	 * Assigning customized MASON-inspectors to specific objects The mapping is
	 * defined in the static method clsInspectorMapping.getInspector()
	 * 
	 * @author langr 25.03.2009, 14:57:20
	 * 
	 * @see
	 * sim.portrayal.SimplePortrayal2D#getInspector(sim.portrayal.LocationWrapper
	 * , sim.display.GUIState)
	 */
	@Override
	public Inspector getInspector(LocationWrapper wrapper, GUIState state) {
		// Override to get constantly updating inspectors = volatile

		// TODO: (langr) - testing purpose only! adapt tabs for selected entity
		// clsSingletonMasonGetter.getConsole().setView(moEntity.getEntityType().hashCode());

/*		if (moMasonInspector == null) {
			moMasonInspector = new TabbedInspector();
			Inspector oInspector = new clsInspectorEntity(super.getInspector(
					wrapper, state), wrapper, state, moEntity);
			moMasonInspector.addInspector(oInspector, "ARS Entity Inspector");
		}
*/		
		if (moMasonInspectorFactory == null) {
			return super.getInspector(wrapper, state);
		}
	
		return moMasonInspectorFactory.getInspector(wrapper, state, super.getInspector(wrapper, state), moEntity);
	}
	
	public void setInspectorFactory( itfEntityInspectorFactory poMasonInspector) {
		moMasonInspectorFactory = poMasonInspector;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * Jul 24, 2009, 10:37:04 PM
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#getMass()
	 */
	@Override
	public double getMass() {
		return java.lang.Double.MAX_VALUE; //stationary objects have infinity mass!
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * Jul 24, 2009, 10:37:04 PM
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setMass(double)
	 */
	@Override
	public void setMass(double mass) {
		//do nothing - stationary objects have infinite mass!
	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 03.05.2011, 14:53:44
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setOverlay(bw.utils.enums.eOverlay)
	 */
	@Override
	public void setOverlayImage(eImages poOverlay) {
		// TODO (muchitsch) - Auto-generated method stub
		// TODO do stationary objects need a overlay? not yet if needed later look at implementation of clsMobileObject
		
	}

	/* (non-Javadoc)
	 *
	 * @since 29.10.2012 21:47:08
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setFacialExpressionOverlayImage(du.enums.eFacialExpression)
	 */
	@Override
	public void setFacialExpressionOverlayImage(eFacialExpression poOverlay) {
		// TODO (muchitsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2013 10:48:35
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setCarriedItem(bw.factories.eImages)
	 */
	@Override
	public void setCarriedItem(BufferedImage poOverlay) {
		// TODO (herret) - Auto-generated method stub
		
	}
}
