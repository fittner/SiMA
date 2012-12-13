/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.clsMeatBody;
import bw.body.clsSimpleBody;
import bw.body.clsUnrealBody;
import bw.body.itfget.itfGetBody;
import bw.entities.logger.clsPositionLogger;
import bw.factories.clsSingletonProperties;
import bw.factories.eImages;
import bw.utils.enums.eBodyType;
import config.clsProperties;
import du.enums.eEntityType;
import du.enums.eFacialExpression;
import sim.display.GUIState;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import statictools.cls3DUniverse;

import statictools.clsSimState;
import statictools.eventlogger.Event;
import statictools.eventlogger.clsEventLogger;
import statictools.eventlogger.eEvent;
import ARSsim.physics2D.physicalObject.itfSetupFunctions;
import ARSsim.physics2D.util.clsPose;

/**
 * Entity is the base class of any object in the ArsinWorld.
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
	//public static final String P_ENTITY_COLOR_RGB = "color_rgb"; // TD - moved to clsShape2DCreator. if a differentiation between the color of the shape and the color of the agent is necessary - reactivate this property
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
	
	private int mnUniqueId; //TD 2011/05/01 "= clsUniqueIdGenerator.getUniqueId();" - unnecessary - uid is autogenerated
	protected final int uid;
	
	protected clsBaseBody moBody; // the instance of a body
	protected clsPositionLogger moPositionLogger;
	
	private eImages mnCurrentOverlay; //overlay to display currently executed actions and other attributes
	private long mnLastSetOverlayCall = -1; //sim step of the last call of setOverlay
	private eFacialExpression mnCurrentFacialExpressionOverlay; //overlay to display currently executed actions and other attributes
	
	private BranchGroup shapes3D; 
	public abstract void registerEntity();
	public abstract void addEntityInspector(TabbedInspector poTarget, Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsEntity poEntity);
	
	public clsEntity(String poPrefix, clsProperties poProp, int uid) {
		this.uid = uid;
		mnCurrentOverlay = eImages.NONE;
		mnCurrentFacialExpressionOverlay = eFacialExpression.NONE;
		setEntityType();
		moPhysicalObject2D = null;
		shapes3D = null;
		
		applyProperties(poPrefix, poProp);
		
		setRegistered(false);
		
		mnUniqueId = uid;
		
		if (clsSingletonProperties.useLogger()){
			clsEventLogger.add(new Event(this, moId, eEvent.CREATE, "uid="+this.uid));
		}
		moPositionLogger = new clsPositionLogger(this.uid);
	}
	
	public int uid() {
		return uid;
	}
	
	public clsPositionLogger getPositionLogger() {
		return moPositionLogger;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		

		oProp.putAll( clsSimpleBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.SIMPLE.toString());
		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1.0);
		oProp.setProperty(pre+P_ID, -1);
		
		oProp.putAll( clsSimpleBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.SIMPLE.toString());
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		setId( poProp.getPropertyString(pre+P_ID ) );
		setStructuralWeight(poProp.getPropertyDouble(pre+P_STRUCTURALWEIGHT));
		//setBody( createBody(pre, poProp) ); // has to be called AFTER the shape has been created. thus, moved to clsMobile and clsStationary.
	}
	
	protected void setBody(clsBaseBody poBody) {
		moBody = poBody;
	}
	
	protected clsBaseBody createBody(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		eBodyType oBodyType = eBodyType.valueOf( poProp.getPropertyString(pre+P_BODY_TYPE) );
		
		clsBaseBody oRetVal = null;
		switch( oBodyType ) {
		case MEAT:
			oRetVal = new clsMeatBody(pre+P_BODY, poProp, this);
			break;
		case COMPLEX:
			oRetVal = new clsComplexBody(pre+P_BODY, poProp, this);
			break;
		case UNREAL:
			oRetVal = new clsUnrealBody(pre+P_BODY, poProp, this);
			break;
		case SIMPLE: //simple is the default, needed dont change!
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
	@Override
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
		
		if (shapes3D != null) {
/*			
			for (int i=0; i<shapes3D.numChildren(); i++) {
				TransformGroup oTG = (TransformGroup) shapes3D.getChild(i);
				
				Vector3f v = new Vector3f((float)poPose.getPosition().x, (float)poPose.getPosition().y, 0);
				AxisAngle4f a = new AxisAngle4f(0,0,1, (float)poPose.getAngle().radians);
				
				Transform3D tr = new Transform3D();
				tr.setTranslation(v);
				tr.setRotation(a);
				   
//				oTG.setTransform(tr); //FIXME - ERROR
			}
*/			
		}
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
	public void set2DShape(Shape poShape, double poMass) {
		
		//so we can set the shape null in constructor, for ex Stone //TODO (muchitsch) //TODO (langr)
		if( poShape != null ) {
			((itfSetupFunctions)moPhysicalObject2D).setShape(poShape, poMass);
		}
	}
	
	public void set3DShape(TransformGroup poShape) {
		//this boolean prevents opening the 3D frame, as the static cls3DUniverse opens it if not there CM
		if(clsSingletonProperties.use3DPerception())
		{
			if (shapes3D != null) {
				try {
					shapes3D.detach(); //remove previous shape(s)!
				} catch (javax.media.j3d.CapabilityNotSetException e) {
					
				}
			}
			
			if (poShape != null) { // add new shape(s)
				shapes3D = new BranchGroup();
				
				//encapsulate all entity related shapes in a transformgroup.
	//			TransformGroup oTG = new TransformGroup();
	//			oTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ | TransformGroup.ALLOW_TRANSFORM_WRITE);
	//			oTG.addChild(poShape);
	//			shapes3D.addChild(oTG);
				
				shapes3D.addChild(poShape);
				cls3DUniverse.getSimpleUniverse().addBranchGraph(shapes3D);
			}
		}
	}
	
	/**
	 * see implementation clsMobileObject2D
	 *
	 * @author langr
	 * 25.02.2009, 15:11:45
	 *

	 */
	public Shape get2DShape() {
		return ((itfSetupFunctions)moPhysicalObject2D).getShape();
	}
	
	public void setOverlayImage(eImages poOverlay) {
		mnLastSetOverlayCall = clsSimState.getSteps();
		mnCurrentOverlay = poOverlay;
	}
	
	private void updateOverlayImage() {
		((itfSetupFunctions)moPhysicalObject2D).setFacialExpressionOverlayImage(mnCurrentFacialExpressionOverlay);
		
		if (clsSimState.getSteps() > mnLastSetOverlayCall+10) /*10 is the time how long the overlay will be displayed*/ {
			mnCurrentOverlay = eImages.NONE;
		}
		((itfSetupFunctions)moPhysicalObject2D).setOverlayImage(mnCurrentOverlay);
		
	}
	
	public void setFacialExpressionOverlayImage(eFacialExpression poOverlay) {
		mnCurrentFacialExpressionOverlay = poOverlay;
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
	
	public void updateEntityInternals() { //called each sim step by getSteppableSensing (clsMobileObject2D and clsStationaryObject2D)
		if (clsSingletonProperties.useLogger()){
			updatePositionLogger();
		}
		updateOverlayImage();
	}
	
	private void updatePositionLogger() {
		clsPose oPose =  ((itfSetupFunctions)moPhysicalObject2D).getPose();
		moPositionLogger.add(oPose);
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