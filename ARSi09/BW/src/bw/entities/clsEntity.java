/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.clsMeatBody;
import bw.body.clsSimpleBody;
import bw.body.itfget.itfGetBody;
import bw.utils.enums.eBodyType;
import config.clsBWProperties;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import statictools.clsSingletonUniqueIdGenerator;
import ARSsim.physics2D.physicalObject.itfSetupFunctions;
import ARSsim.physics2D.util.clsPose;
import enums.eEntityType;

/**
 * Entity is the base class of any object in the BubbleWorld.
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
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 
 */
public abstract class clsEntity implements itfGetBody {
	public static final String P_ID = "id";
	public static final String P_STRUCTURALWEIGHT = "weight_structural";
	//public static final String P_ENTITY_COLOR_RGB = "color_rgb"; // TD - moved to clsShapeCreator. if a differentiation between the color of the shape and the color of the agent is necessary - reactivate this property
	public static final String P_SHAPE = "shape"; //prefix used for shape definitions
	public static final String P_SHAPENAME = "_";
	public static final String P_BODY_TYPE = "body_type";
	public static final String P_BODY = "body";
	
	protected PhysicalObject2D moPhysicalObject2D;
	
	protected double mrStructuralWeight; // weight without flesh or similar dynamic parts - usually static during livetime. only changes in case of growth or similiar mechanisms
	protected double mrVariableWeight; // flesh + carried elements + ...
	
	protected eEntityType meEntityType;
	private String moId;
	private boolean mnRegistered;
	
	private int mnUniqueId = clsSingletonUniqueIdGenerator.getUniqueId();
	
	protected clsBaseBody moBody; // the instance of a body	
	
	public clsEntity(String poPrefix, clsBWProperties poProp) {
		setEntityType();
		moPhysicalObject2D = null;
		
		applyProperties(poPrefix, poProp);
		
		setRegistered(false);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1.0);
		oProp.setProperty(pre+P_ID, -1);
		
		oProp.putAll( clsSimpleBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.SIMPLE.toString());
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		setId( poProp.getPropertyString(pre+P_ID ) );
		setStructuralWeight(poProp.getPropertyDouble(pre+P_STRUCTURALWEIGHT));
		//setBody( createBody(pre, poProp) ); // has to be called AFTER the shape has been created. thus, moved to clsMobile and clsStationary.
	}
	
	protected void setBody(clsBaseBody poBody) {
		moBody = poBody;
	}
	
	protected clsBaseBody createBody(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		eBodyType oBodyType = eBodyType.valueOf( poProp.getPropertyString(pre+P_BODY_TYPE) );
		
		clsBaseBody oRetVal = null;
		switch( oBodyType ) {
		case MEAT:
			oRetVal = new clsMeatBody(pre+P_BODY, poProp, this);
			break;
		case COMPLEX:
			oRetVal = new clsComplexBody(pre+P_BODY, poProp, this);
			break;
		case SIMPLE:
		default:
			oRetVal = new clsSimpleBody(pre+P_BODY, poProp, this);
			break;
		}
		
		return oRetVal;	
	}
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.05.2009, 18:40:22
	 * 
	 * @see bw.body.itfGetBody#getBody()
	 */
	public clsBaseBody getBody() {
		return moBody;
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
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:51
	 *
	 * @return
	 */
	public abstract sim.physics2D.util.Double2D getPosition();
	
	
	/**
	 * DOCUMENT (deutsch) - insert description
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
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:15:30
	 *
	 */
	protected abstract void setEntityType();


	/**
	 * DOCUMENT (deutsch) - insert description
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
	 * DOCUMENT (deutsch) - insert description
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
	
	public double getTotalWeight() {
		return mrStructuralWeight + mrVariableWeight;
	}
	
	public double getStructuralWeight() {
		return mrStructuralWeight;
	}
	
	public double getVariableWeight() {
		return mrVariableWeight;
	}
	
	public void setStructuralWeight(double prWeight) {
		mrStructuralWeight = prWeight;
		updateMass();
	}
	
	public void setVariableWeight(double prWeight) {
		mrVariableWeight = prWeight;
		updateMass();
	}
	
	private void updateMass() {
		if (moPhysicalObject2D != null) {
			((itfSetupFunctions)moPhysicalObject2D).setMass(getTotalWeight());
		}
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:38
	 *
	 * @return
	 */
	public String getId() {	
		return moId;	
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 26.02.2009, 11:16:36
	 *
	 * @param pnId
	 */
	public void setId(String poId) {		
		this.moId = poId;	
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

	

	/**
	 * @author deutsch
	 * 08.07.2009, 15:05:13
	 * 
	 * @return the mnUniqueId
	 */
	public int getUniqueId() {
		return mnUniqueId;
	}
}