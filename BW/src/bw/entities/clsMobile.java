/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.util.clsPose;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsMobile extends clsEntity {
	
	public static final String P_POS_X = "pos_x";
	public static final String P_POS_Y = "pos_y";
	public static final String P_POS_ANGLE = "pos_angle";
	public static final String P_START_VELOCITY_X = "start_velocity_x";
	public static final String P_START_VELOCITY_Y = "start_velocity_y";

	public static final String P_DEF_COEFF_FRICTION = "def_coeff_friction";
	public static final String P_DEF_STATIC_FRICTION = "def_static_friction";
	public static final String P_DEF_RESTITUTION = "def_restitution";
	
	public static final String P_SHAPE_TYPE = "mobile_shape_type";
	public static final String P_SHAPE_RADIUS = "mobile_shape_radius";
	public static final String P_SHAPE_WIDTH = "mobile_shape_width";
	public static final String P_SHAPE_HEIGHT = "mobile_shape_height";
	
	private int mnHolders; // number of bubles which picked-up and carry this mobile entity 
	private double mrDefaultCoeffFriction; 	//0.5
	private double mrDefaultStaticFriction;	//0.2
	private double mrDefaultRestitution;	//1.0

	protected clsInventory moInventory;
	

	public clsMobile(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		setEntityInventory();
		
		applyProperties(poPrefix, poProp);
		
		mnHolders = 0;
	}


	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( clsEntity.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_POS_X, 0.0);
		oProp.setProperty(pre+P_POS_Y, 0.0);
		oProp.setProperty(pre+P_POS_ANGLE, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_X, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_Y, 0.0);
		
		oProp.setProperty(pre+P_DEF_COEFF_FRICTION , 0.5);
		oProp.setProperty(pre+P_DEF_STATIC_FRICTION , 0.2);
		oProp.setProperty(pre+P_DEF_RESTITUTION , 1.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		mrDefaultCoeffFriction = poProp.getPropertyDouble(pre+P_DEF_COEFF_FRICTION);
		mrDefaultStaticFriction = poProp.getPropertyDouble(pre+P_DEF_STATIC_FRICTION);
		mrDefaultRestitution = poProp.getPropertyDouble(pre+P_DEF_RESTITUTION);
		
		double oPosX = poProp.getPropertyDouble(pre+P_POS_X);
		double oPosY = poProp.getPropertyDouble(pre+P_POS_Y);
		double oPosAngle = poProp.getPropertyDouble(pre+P_POS_ANGLE);
		Double2D oVelocity = new Double2D(  poProp.getPropertyDouble(pre+P_START_VELOCITY_X), 
											poProp.getPropertyDouble(pre+P_START_VELOCITY_Y) );
		
		Shape oShape = createShape(pre, poProp); //depends on the config
		initPhysicalObject2D(new clsPose(oPosX, oPosY, oPosAngle), oVelocity, oShape, getMass());
	}
	
	private Shape createShape(String pre, clsBWProperties poProp) {
		
		Shape oShape = null; 
			
		eShapeType oShapeType = eShapeType.valueOf( poProp.getPropertyString(pre +P_SHAPE_TYPE) );
		
		switch( oShapeType ) {
		case SHAPE_CIRCLE:
			oShape = new sim.physics2D.shape.Circle(poProp.getPropertyDouble(pre +P_SHAPE_RADIUS), 
					 poProp.getPropertyColor(pre +P_ENTITY_COLOR_RGB));
			break;
		case SHAPE_RECTANGLE:
			oShape = new sim.physics2D.shape.Rectangle(	poProp.getPropertyDouble(pre +P_SHAPE_WIDTH),
														poProp.getPropertyDouble(pre +P_SHAPE_HEIGHT), 
														 poProp.getPropertyColor(pre +P_ENTITY_COLOR_RGB));
			break;
		case SHAPE_POLYGON:
			//TODO: (everyone) - add list for points of polygon in config!
			break;
		default:
			oShape = new sim.physics2D.shape.Circle(poProp.getPropertyDouble(pre +P_SHAPE_RADIUS), 
					 poProp.getPropertyColor(pre +P_ENTITY_COLOR_RGB));
			break;
		}
		return oShape;
	}

	/*
	 * Override to configure inventory-size
	 */
	protected void setEntityInventory() {
		moInventory= new clsInventory(this,10,100);
	}
	public clsInventory getInventory() {
		return moInventory;
	}

	@Override
	protected void initPhysicalObject2D(clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double prMass) {
		moPhysicalObject2D = new clsMobileObject2D(this);
		
		setPose(poPose);
		setVelocity(poStartingVelocity);	
		setShape(poShape, prMass);
		setCoefficients(mrDefaultCoeffFriction, mrDefaultStaticFriction, mrDefaultRestitution); //default coefficients
	}

	/**
	 * @return the moMobile
	 */
	public clsMobileObject2D getMobileObject2D() {
		return (clsMobileObject2D)moPhysicalObject2D;
	}
	
	@Override
	public sim.physics2D.util.Double2D getPosition() {
		return getMobileObject2D().getPosition();
	}
	
	public sim.physics2D.util.Double2D getVelocity() {
		return getMobileObject2D().getVelocity();
	}
	
	public double getAngularVelocity() {
		return getMobileObject2D().getAngularVelocity();
	}
	
	public void setVelocity(sim.physics2D.util.Double2D poVelocity) {
		getMobileObject2D().setVelocity(poVelocity);
	}
	
	// increases number of bubles which picked-up and carry this mobile entity 
	public void incHolders(){
		mnHolders++;		
	}
	
	// decreases number of bubles which picked-up and carry this mobile entity 
	public void decHolders(){
		if (mnHolders != 0){
			mnHolders--;
		}
	}
	
	// returns number of bubles which picked-up and carry this mobile entity 
	public int getHolders(){
		return mnHolders;		
	}
}
