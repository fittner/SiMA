/**
 * 
 */
package ARSsim.physics2D.physicalObject;

//import inspectors.clsInspectorMapping;
import java.util.ArrayList;

import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;

import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import ARSsim.motionplatform.clsMotionPlatform;
import ARSsim.physics2D.shape.clsCircleImage;
import ARSsim.physics2D.util.clsPose;

import bw.entities.clsEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.factories.eImages;
import bw.utils.inspectors.entity.clsInspectorEntity;
import bw.world.surface.clsSurfaceHandler;

/**
 * Our representative of the mason physics class
 * 
 * @author muchitsch
 * 
 */
public class clsMobileObject2D extends
		sim.physics2D.physicalObject.MobileObject2D implements ForceGenerator,
		itfGetEntity, itfSetupFunctions {

	private static final long serialVersionUID = -7732669244848952049L;

	private clsEntity moEntity;
	public clsMotionPlatform moMotionPlatform;
	public ArrayList<clsCollidingObject> moCollisionList;

	private Double2D forceAccu;
	private double torqueAccu;
	
	/* Flag to disallow access to addForce and addTorque */
	private boolean permitAccessToAddForce = true; 

	private TabbedInspector moMasonInspector = null;

	//added by SK: the following members are used for surfaces
	//values identical to superclass but are declared private there 
	protected final double mrZeroVelocity = 0.01;
	protected final double mrGravity = 0.1;
	protected double mrNormalForce = 80 * mrGravity; //assumes a bubble with 80kg and the given gravity resulting in N = 8
	//other things used for friction
	protected clsSurfaceHandler moSurfaceHandler = clsSurfaceHandler.getInstance();
	protected boolean mbUseSurfaces = false;
	protected int nCurrentPositionX = 0, nCurrentPositionY = 0;
	
	public clsMobileObject2D(clsEntity poEntity) {
		moEntity = poEntity;
		moMotionPlatform = new clsMotionPlatform(this);
		moCollisionList = new ArrayList<clsCollidingObject>();
		forceAccu = new Double2D();
		torqueAccu = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * sets the position of the mobile object in the masons field-environment
	 * 
	 * @author langr 25.02.2009, 13:38:18
	 * 
	 * @see
	 * ARSsim.physics2D.physicalObject.itfSetupFunctions#setPosition(sim.util
	 * .Double2D)
	 */
	@Override
	public void setPose(clsPose poPose) {
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(
				this,
				new sim.util.Double2D(poPose.getPosition().getX(), poPose
						.getPosition().getY()));
		setPose(poPose.getPosition(), poPose.getAngle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 26.02.2009, 12:00:49
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#getPose()
	 */
	@Override
	public clsPose getPose() {
		return new clsPose(this.getPosition(), this.getOrientation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * sets the physical frictions of the object
	 * 
	 * friction = friction of the object with the background surface. 0 is no
	 * friction staticFriction = static friction of the object with the
	 * background surface. 0 is no static friction restitution = elasticity of
	 * an object - determines how much momentum is conserved when objects
	 * collide
	 * 
	 * @author langr 25.02.2009, 14:15:20
	 * 
	 * @see
	 * ARSsim.physics2D.physicalObject.itfSetupFunctions#setCoefficients(double,
	 * double, double)
	 */
	@Override
	public void setCoefficients(double poFriction, double poStaticFriction,
			double poRestitution) {
		setCoefficientOfFriction(poFriction);
		setCoefficientOfStaticFriction(poStaticFriction);
		setCoefficientOfRestitution(poRestitution);
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

	public Steppable getSteppableBeforeStepping() {
		return new Steppable() {
			private static final long serialVersionUID = 8277569961105957056L;

			@Override
			public void step(SimState state) {
				resetStepInfo();
			}
		};
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

	public Steppable getSteppableUpdateInternalState() {
		return new Steppable() {
			private static final long serialVersionUID = -1672763372988537963L;

			@Override
			public void step(SimState state) {
				moEntity.updateInternalState();
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

	public Steppable getSteppableExecution() {
		return new Steppable() {
			private static final long serialVersionUID = -7785659205720901693L;

			@Override
			public void step(SimState state) {
				moEntity.execution();
			}
		};
	}

	public Steppable getSteppableAfterStepping() {
		return new Steppable() {
			private static final long serialVersionUID = 8796719574709310639L;

			@Override
			public void step(SimState state) {
				postprocessStep();
			}
		};
	}

	/**
	 * May be called while Stepping, in any of the step-functions. Accumulates
	 * values. Contrary to physics2D-addForce(Double2D) should not be called any
	 * more during addForce()-handling.
	 * 
	 * @author holleis 15.07.2009, 19:16:30
	 */
	public void addForceComponent(Double2D force) {
		forceAccu = forceAccu.add(force);
	}

	/**
	 * This function is to be called exclusively by the Physics Engine. Never
	 * call this function directly. Call {@link addForceComponent(Double2D)} instead.
	 *
	 * @author holleis
	 * 16.07.2009, 15:49:05
	 * 
	 * @see sim.physics2D.physicalObject.MobileObject2D#addForce(sim.physics2D.util.Double2D)
	 */
	@Override
	public void addForce(Double2D force) {
		if (permitAccessToAddForce)
			super.addForce(force);
		else
			throw new IllegalStateException("Never call addForce directly, use addForceComponent(Double2D)");
	}

	/**
	 * May be called while Stepping, in any of the step-functions. Accumulates
	 * values. Contrary to physics2D-addTorque(double) should not be called any
	 * more during addForce()-handling.
	 * 
	 * @author holleis 15.07.2009, 19:18:57
	 */
	public void addTorqueComponent(double torque) {
		torqueAccu += torque;
	}
	
	/**
	 * This function is to be called exclusively by the Physics Engine. Never
	 * call this function directly. Call {@link addTorqueComponent(double)} instead.
	 *
	 * @author holleis
	 * 16.07.2009, 15:49:05
	 * 
	 * @see sim.physics2D.physicalObject.MobileObject2D#addTorque(double)
	 */
	@Override
	public void addTorque(double torque) {
		if (permitAccessToAddForce)
			super.addTorque(torque);
		else
			throw new IllegalStateException("Never call addTorque directly, use addTorqueComponent(double)");
	}

	/**
	 * The forces accumulated by addForceComponent(Double2D) and
	 * addTorqueComponent(double) are reported to the physics-engine.
	 * 
	 * @author holleis 15.07.2009, 19:15:27
	 * 
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		addForce(forceAccu);
		addTorque(torqueAccu);
	}

	/**
	 * Called before other step-functions. Local variables are reseted here
	 * 
	 * @author langr 25.02.2009, 14:51:43
	 * 
	 */
	public void resetStepInfo() {
		permitAccessToAddForce = false;
		forceAccu = new Double2D();
		torqueAccu = 0;
	}

	/*
	 * Called after other step-functions.
	 */
	public void postprocessStep() {
		// correct rotation-force to emulate static friction for rotation
		addAngularFriction();

		// with these 2, physics work!
		sim.physics2D.util.Double2D position = getPosition();
		clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(
				clsMobileObject2D.this,
				new sim.util.Double2D(position.x, position.y));
		
		moCollisionList.clear();
		
		permitAccessToAddForce = true;
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

	@Override
	public int handleCollision(PhysicalObject2D other,
			sim.physics2D.util.Double2D colPoint) {
		// return 2; // sticky collision
		// return 1; // regular collision
		// return 0; //happy guessing!
		return 1;
	}

	/**
	 * This method simulates an angular-friction that is not supported by the
	 * physics-engine. Problem until now: Each clsMobileObject2D has the same
	 * angular friction
	 * 
	 * @author langr 25.03.2009, 09:44:04
	 * 
	 */
	public void addAngularFriction() {
		// if(moEntity instanceof clsRemoteBot)
		{
			double nAngularVel = getAngularVelocity();
			double nToAdd;
			//added by SK: dynamic friction calculation
			if (mbUseSurfaces == true)
				nToAdd = mrNormalForce * moSurfaceHandler.getKineticFriction(nCurrentPositionX, nCurrentPositionY);
			else
				nToAdd = 0.3;
			if (nAngularVel > 0.002d) {
				nToAdd *= -1;
				addTorqueComponent(nToAdd);
			} else if (nAngularVel < -0.002d) {
				addTorqueComponent(nToAdd);
			} else {
				setAngularVelocity(0.0);
			}
		}
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

		if (moMasonInspector == null) {
			moMasonInspector = new TabbedInspector();
			Inspector oInspector = new clsInspectorEntity(super.getInspector(
					wrapper, state), wrapper, state, moEntity);
			moMasonInspector.addInspector(oInspector, "ARS Entity Inspector");
		}

		return moMasonInspector;
	}
	
    /** Calculates and adds the static and dynamic friction forces on the object
     * based on the coefficients of friction.
     * Overwrites inherited method. 
     * 
     * @author kohlhauser
     */
	@Override
	public void addFrictionForce()
	{	
		if (mbUseSurfaces == true)
		{
			//getting necessary information for the calculation
			Double2D oVelocity = this.getVelocity(); 
	    	double rVelLength = oVelocity.length();
	    	Double2D oPosition = physicsState.getPosition(this.index);
	    	
	    	nCurrentPositionX = (int) oPosition.getX();
	    	nCurrentPositionY = (int) oPosition.getY();
		    	
	    	if (rVelLength < mrZeroVelocity)
	        {
		    	//get static friction from surface and add the own friction
		    	//setCoefficientOfStaticFriction(moSurfaceHandler.getStaticFriction(nCurrentPositionX, nCurrentPositionY) + mrStaticObjectFriction);
	            Double2D externalForce = this.getForceAccumulator();
	            //if external force on sitting object can overcome the normal Force (keeping it on its spot), the object 
	            //moves according to the force
	            if (mrNormalForce * moSurfaceHandler.getStaticFriction(nCurrentPositionX, nCurrentPositionY) > externalForce.length())
	                this.addForceComponent(new Double2D(-externalForce.x, -externalForce.y));
	        }
	    	
	    	if (rVelLength > 0)
		    {
		    	//get dynamic friction from surface and add the own friction
		    	//setCoefficientOfFriction(moSurfaceHandler.getKineticFriction(nCurrentPositionX, nCurrentPositionY) + mrKineticObjectFriction);
		    	Double2D velRot = new Double2D(-oVelocity.x, -oVelocity.y);
	            this.addForceComponent(velRot.scalarMult(mrNormalForce * moSurfaceHandler.getKineticFriction(nCurrentPositionX, nCurrentPositionY)));
	        }
		}
		else
			super.addFrictionForce();
	}
	
	/**
	 * 
	 * Set to true if surfaces should be used.
	 *
	 * @author kohlhauser
	 * 12.08.2009, 13:01:14
	 *
	 * @param useSurfaces
	 */
	public void setUseSurfaces(boolean useSurfaces)
	{
		this.mbUseSurfaces = useSurfaces;
	}
	
	/**
	 * 
	 * This function assumes that the setMass function of the superclass is ignored.
	 * This allows an avoidance of the mass moment of inertia which is not defined for all shapes.
	 *
	 * @author kohlhauser
	 * 19.08.2009, 12:30:26
	 *
	 * @param prMass
	 */
	public void setNormalForce(double prMass)
	{
		mrNormalForce = prMass * mrGravity; 
	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 03.05.2011, 14:53:24
	 * 
	 * @see ARSsim.physics2D.physicalObject.itfSetupFunctions#setOverlay(bw.utils.enums.eOverlay)
	 */
	@Override
	public void setOverlayImage(eImages poOverlay) {
		Shape oShape = this.getShape();
		if(oShape instanceof clsCircleImage){
			((clsCircleImage) oShape).setOverlayImage(poOverlay);
		}
		
	}
}