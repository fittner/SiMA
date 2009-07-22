/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import ARSsim.physics2D.physicalObject.itfSetupFunctions;
import ARSsim.physics2D.util.clsPose;
import bw.utils.config.clsBWProperties;
import bw.utils.tools.clsContentColumn;
import enums.eEntityType;

/**
 * Entity is the baseclass of any object in the BubbleWorld.
 * With the containing PhysicalObject2D it holds the reference to the physical-object within the 
 * mason-framework.
 * 
 *  It supports the automatic redirection of functions for e.g positioning, shape, ... towards the
 *  real physical object in the simulator, that implements the itfSetupFunctions.
 *  
 *  This interface has to be implemented in clsMobileObject2D and clsStaticObject2D
 * 
 * sub-classes: clsMobile, clsStatic
 *  
 * @author langr
 *
 * Changes:
 *    BD/2009-07-03: Added Inventory  
 * 
 */
/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public abstract class clsEntity {
	public static final String P_MASS = "mass";
	public static final String P_ID = "id";
	
	protected PhysicalObject2D moPhysicalObject2D;
	private double mrMass;
	protected eEntityType meEntityType;
	private int mnId;
	private boolean mnRegistered;
	
	public clsEntity(String poPrefix, clsBWProperties poProp) {
		setEntityType();
		moPhysicalObject2D = null;
		
		applyProperties(poPrefix, poProp);
		
		setRegistered(false);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsContentColumn.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_MASS, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		if (pre.length()>0) {
			pre = pre+".";
		}

		int nId = poProp.getPropertyInt(pre+P_ID );
		setId( nId );
		
		setEntityType();
		mrMass = poProp.getPropertyDouble(pre+P_MASS);
	}
	
	/**
	 * the entities cycle for perception-deliberation-action
	 * MUST implement these functions
	 *
	 * @author langr
	 * 25.02.2009, 15:12:12
	 *
	 */
	public abstract void updateInternalState();
	public abstract void sensing();
	public abstract void processing();
	public abstract void execution();
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:51
	 *
	 * @return
	 */
	public abstract sim.physics2D.util.Double2D getPosition();
	
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:33
	 *
	 * @param poPose
	 * @param poStartingVelocity
	 * @param poShape
	 * @param poMass
	 */
	protected abstract void initPhysicalObject2D(clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double poMass);
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:30
	 *
	 */
	protected abstract void setEntityType();


	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:25
	 *
	 * @return
	 */
	public eEntityType getEntityType() {
		return meEntityType;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:22
	 *
	 * @param peType
	 * @return
	 */
	public boolean isEntityType(eEntityType peType)
	{
		boolean retVal = false;
		
		if(peType == getEntityType()) {
			retVal = true;
		}

		return retVal;
	}
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:19
	 *
	 * @param poPos
	 */
	public void setPose(clsPose poPose) {
		((itfSetupFunctions)moPhysicalObject2D).setPose(poPose);
	}
	
	public clsPose getPose() {
		return ((itfSetupFunctions)moPhysicalObject2D).getPose();
	}
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:45
	 *
	 * @param poShape
	 * @param poMass
	 */
	public void setShape(Shape poShape, double poMass) {
		
		//so we can set the shape null in constructor, for ex Stone //TODO (muchitsch) //TODO (langr)
		if( poShape != null ) {
			((itfSetupFunctions)moPhysicalObject2D).setShape(poShape, poMass);
		}
		
	}
	
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:45
	 *

	 */
	public Shape getShape() {
		return ((itfSetupFunctions)moPhysicalObject2D).getShape();
	}	
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:50
	 *
	 * @param poFriction
	 * @param poStaticFriction
	 * @param poRestitution
	 */
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution) {
		((itfSetupFunctions)moPhysicalObject2D).setCoefficients(poFriction, poStaticFriction, poRestitution);
	}
	
	/**
	 * @author deutsch
	 * 25.02.2009, 16:21:36
	 * 
	 * @param mrMass the mrMass to set
	 */
	public void setMass(double mrMass) {
		this.mrMass = mrMass;
	}

	/**
	 * @author deutsch
	 * 25.02.2009, 16:21:36
	 * 
	 * @return the mrMass
	 */
	public double getMass() {
		return mrMass;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:38
	 *
	 * @return
	 */
	public int getId() {	
		return mnId;	
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:36
	 *
	 * @param pnId
	 */
	public void setId(int pnId) {		
		this.mnId = pnId;	
	}

	/**
	 * @author deutsch
	 * 26.02.2009, 11:42:42
	 * 
	 * @param mnRegistered the mnRegistered to set
	 */
	public void setRegistered(boolean mnRegistered) {
		this.mnRegistered = mnRegistered;
	}

	/**
	 * @author deutsch
	 * 26.02.2009, 11:42:42
	 * 
	 * @return the mnRegistered
	 */
	public boolean isRegistered() {
		return mnRegistered;
	}
	

}