/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities.abstractEntities;

import interfaces.itfEntity;
import interfaces.itfEntityInspectorFactory;
import interfaces.itfSetupFunctions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import complexbody.io.actuators.actionExecutors.clsAction;

import physics2D.shape.itfImageShape;
import properties.clsProperties;


import body.clsBaseBody;
import body.clsSimpleBody;
import body.itfget.itfGetBody;



import du.enums.eEntityType;
import du.enums.eFacialExpression;
import du.enums.eOdor;
import du.enums.eSpeechExpression;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.shape.Shape;
import singeltons.clsSimState;
import singeltons.eImages;

import tools.clsPose;


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
public abstract class clsEntity implements itfGetBody, itfEntity {
	
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
	
	protected double mrStartTotalWeight;
	protected eEntityType meEntityType;
	public String moId;
	private boolean mnRegistered;
	
	private int mnUniqueId; //TD 2011/05/01 "= clsUniqueIdGenerator.getUniqueId();" - unnecessary - uid is autogenerated
	protected final int uid;
	
	protected clsBaseBody moBody; // the instance of a body
	
	private eImages mnCurrentOverlay; //overlay to display currently executed actions and other attributes
	private long mnLastSetOverlayCall = -1; //sim step of the last call of setOverlay
	private eFacialExpression mnCurrentFacialExpressionOverlay; //overlay to display currently executed actions and other attributes
	private eSpeechExpression mnCurrentSpeechExpressionOverlay; //overlay to display currently executed actions and other attributes
	private eSpeechExpression mnCurrentThoughtExpressionOverlay; //overlay to display currently executed actions and other attributes
	
	private ArrayList<clsAction> moExecutedActions;
		
	
	public abstract void registerEntity();
	//public abstract void addEntityInspector(TabbedInspector poTarget, Inspector poSuperInspector, LocationWrapper poWrapper, GUIState poState, clsEntity poEntity);
	
	//additional perception values
	protected double mrVisionBrightness = 0.0;
	private BufferedImage mnCarriedItem;
	private eOdor moOdor = eOdor.UNDEFINED;
	
	public clsEntity(String poPrefix, clsProperties poProp, int uid) {
		this.uid = uid;
		mnCurrentOverlay = eImages.NONE;
		mnCarriedItem = null;
		mnCurrentFacialExpressionOverlay = eFacialExpression.NONE;
		mnCurrentSpeechExpressionOverlay = eSpeechExpression.NONE;
		mnCurrentThoughtExpressionOverlay = eSpeechExpression.NONE;
		setEntityType();
		moPhysicalObject2D = null;
		
		moExecutedActions= new ArrayList<clsAction>();
		
		applyProperties(poPrefix, poProp);
		
		setRegistered(false);
		
		mnUniqueId = uid;
		

	}
	
	public abstract void setMasonInspectorFactory(itfEntityInspectorFactory poMasonInspector);
	
	public int uid() {
		return uid;
	}
	

	
	public abstract boolean isAlive();
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		

	//	oProp.putAll( clsSimpleBody.getDefaultProperties(pre+P_BODY) );
	//	oProp.setProperty(pre+P_BODY_TYPE, eBodyType.SIMPLE.toString());
		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 1.0);
		oProp.setProperty(pre+P_ID, -1);
		
		oProp.putAll( clsSimpleBody.getDefaultProperties(pre+P_BODY) );
	//	oProp.setProperty(pre+P_BODY_TYPE, eBodyType.SIMPLE.toString());
		
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
	
/*	protected clsBaseBody createBody(String poPrefix, clsProperties poProp) {
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
	
*/
	protected  abstract  clsBaseBody createBody(String poPrefix, clsProperties poProp);


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
	

	
	@Override
	public void exec(){
		//delete all expired actions 
		Iterator<clsAction> it = moExecutedActions.iterator();
		while(it.hasNext()){
			if(it.next().step() == false)it.remove();
		}
		execution();
	}

	
	
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
	public abstract sim.physics2D.util.Double2D getPosition();


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
	public void addAction(clsAction poAction){
		moExecutedActions.add(poAction);
	}
	
	public ArrayList<clsAction> getExecutedActions(){
		return moExecutedActions;
	}
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
	
	@Override
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
	
	public PhysicalObject2D getPhsycalObject2D(){
		return moPhysicalObject2D;
	}
	
	public void setOverlayImage(eImages poOverlay) {
		mnLastSetOverlayCall = clsSimState.getSteps();
		mnCurrentOverlay = poOverlay;
	}
	
	public void setCarriedItem(BufferedImage poOverlay) {
		mnCarriedItem = poOverlay;
	}
	
	private void updateOverlayImage() {
		((itfSetupFunctions)moPhysicalObject2D).setFacialExpressionOverlayImage(mnCurrentFacialExpressionOverlay);
		
		//IH
		((itfSetupFunctions)moPhysicalObject2D).setSpeechExpressionOverlayImage(mnCurrentSpeechExpressionOverlay);
		
		//IH
		((itfSetupFunctions)moPhysicalObject2D).setThoughtExpressionOverlayImage(mnCurrentThoughtExpressionOverlay);
				
		((itfSetupFunctions)moPhysicalObject2D).setCarriedItem(mnCarriedItem);
		
		if (clsSimState.getSteps() > mnLastSetOverlayCall+10) /*10 is the time how long the overlay will be displayed*/ {
			mnCurrentOverlay = eImages.NONE;
		}
		((itfSetupFunctions)moPhysicalObject2D).setOverlayImage(mnCurrentOverlay);
		
	}
	
	public void setLifeValue(double value){
		((itfSetupFunctions)moPhysicalObject2D).setLifeValue(value);
	}
	
	public void setSpeechExpressionOverlayImage(eSpeechExpression poOverlay) {
		mnCurrentSpeechExpressionOverlay = poOverlay;
	}
	
	public void setThoughtExpressionOverlayImage(eSpeechExpression poOverlay) {
		mnCurrentThoughtExpressionOverlay = poOverlay;
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
		if(mrVariableWeight==0.0){
			mrStartTotalWeight = mrStructuralWeight + prWeight;
		}
		mrVariableWeight = prWeight;
		updateMass();
	}
	
	private void updateMass() {
		if (moPhysicalObject2D != null) {
			((itfSetupFunctions)moPhysicalObject2D).setMass(getTotalWeight());
			
			if(true)((itfImageShape)moPhysicalObject2D.getShape()).setDisplaySize(getTotalWeight()/mrStartTotalWeight);

		}
	}
	
	
	
	@Override
	public void updateEntityInternals() { //called each sim step by getSteppableSensing (clsMobileObject2D and clsStationaryObject2D)

		updateOverlayImage();
		
		
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
	@Override
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
	

	public double getVisionBrightness() {
		return mrVisionBrightness;
	}

	public void setVisionBrightness(double mrVisionBrightness) {
		this.mrVisionBrightness = mrVisionBrightness;
	}
	
	public eOdor getOdor() {
		return this.moOdor;
	}
	
	public eOdor setOdor( eOdor moOdor) {
		return this.moOdor  = moOdor;
	}
	
	public abstract clsEntity dublicate(clsProperties poPrperties, double poDistance, double poSplitFactor);
	
}
