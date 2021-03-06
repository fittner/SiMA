/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entities.abstractEntities;



import physics2D.physicalObject.clsMobileObject2D;
import properties.clsProperties;
import interfaces.itfEntityInspectorFactory;
import interfaces.itfMobile;
import entities.systems.clsInventory;
import entities.tools.clsShape2DCreator;
import registration.clsRegisterEntity;
//import bw.utils.inspectors.entity.clsInspectorBasic;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;
import tools.clsPose;



/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsMobile extends clsEntity implements itfMobile{
	
	
	public static final String P_START_VELOCITY_X = "start_velocity_x";
	public static final String P_START_VELOCITY_Y = "start_velocity_y";

	public static final String P_DEF_COEFF_FRICTION = "def_coeff_friction";
	public static final String P_DEF_STATIC_FRICTION = "def_static_friction";
	public static final String P_DEF_RESTITUTION = "def_restitution";
	
	public static final String P_INVENTORY_MAX_MASS = "inventory_max_mass";
	public static final String P_INVENTORY_MAX_ITEMS = "inventory_max_items";

	
	private int mnHolders; // number of bubles which picked-up and carry this mobile entity 
	private double mrDefaultCoeffFriction; 	//0.5
	private double mrDefaultStaticFriction;	//0.2
	private double mrDefaultRestitution;	//1.0

	
	protected clsInventory moInventory;
	
	private int mnMaxItems, mnMaxMass; //maxMass and MaxItem for the inventory
	


	public clsMobile(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poPrefix, poProp);
		setEntityInventory();
		
		mnHolders = 0;
	}

	@Override
	public void registerEntity(){
		clsRegisterEntity.registerEntity(this);
	}

	 
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		oProp.putAll( clsEntity.getDefaultProperties(pre) );
		
		
		oProp.setProperty(pre+clsPose.P_POS_X, 0.0);
		oProp.setProperty(pre+clsPose.P_POS_Y, 0.0);
		oProp.setProperty(pre+clsPose.P_POS_ANGLE, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_X, 0.0);
		oProp.setProperty(pre+P_START_VELOCITY_Y, 0.0);
		
		oProp.setProperty(pre+P_DEF_COEFF_FRICTION , 0.5);
		oProp.setProperty(pre+P_DEF_STATIC_FRICTION , 0.2);
		oProp.setProperty(pre+P_DEF_RESTITUTION , 0.3);
		
		oProp.setProperty(pre+P_INVENTORY_MAX_MASS, 10000);
		oProp.setProperty(pre+P_INVENTORY_MAX_ITEMS, 10);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		mrDefaultCoeffFriction = poProp.getPropertyDouble(pre+P_DEF_COEFF_FRICTION);
		mrDefaultStaticFriction = poProp.getPropertyDouble(pre+P_DEF_STATIC_FRICTION);
		mrDefaultRestitution = poProp.getPropertyDouble(pre+P_DEF_RESTITUTION);
		
		mnMaxItems = poProp.getPropertyInt(pre+P_INVENTORY_MAX_ITEMS);
		mnMaxMass = poProp.getPropertyInt(pre+P_INVENTORY_MAX_MASS);
		
		double oPosX = poProp.getPropertyDouble(pre+clsPose.P_POS_X);
		double oPosY = poProp.getPropertyDouble(pre+clsPose.P_POS_Y);
		double oPosAngle = poProp.getPropertyDouble(pre+clsPose.P_POS_ANGLE);
		Double2D oVelocity = new Double2D(  poProp.getPropertyDouble(pre+P_START_VELOCITY_X), 
											poProp.getPropertyDouble(pre+P_START_VELOCITY_Y) );
		
		
		String oDefaultShape = poProp.getPropertyString(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE);
		Shape oShape2D = clsShape2DCreator.createShape(pre+P_SHAPE+"."+oDefaultShape, poProp);
		
		initPhysicalObject2D(new clsPose(oPosX, oPosY, oPosAngle), oVelocity, oShape2D, getTotalWeight());
		
		
		setBody( createBody(pre, poProp) ); // has to be called AFTER the shape has been created. thus, moved to clsMobile and clsStationary.
	}
/*	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorBasic(poSuperInspector, poWrapper, poState, poEntity),meEntityType.name());		
	}*/
	
	/*
	 * Override to configure inventory-size
	 */
	
	@Override
	public sim.physics2D.util.Double2D getPosition() {
		return getMobileObject2D().getPosition();
	}
	protected void setEntityInventory() {
		moInventory= new clsInventory(this, mnMaxItems, mnMaxMass);
	}
	public clsInventory getInventory() {
		
		return moInventory;
		
	}

	@Override
	protected void initPhysicalObject2D(clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double prMass) {
		moPhysicalObject2D = new clsMobileObject2D(this);
		
		setPose(poPose);
		setVelocity(poStartingVelocity);	
		set2DShape(poShape, prMass);
		setCoefficients(mrDefaultCoeffFriction, mrDefaultStaticFriction, mrDefaultRestitution); //default coefficients
	}
	

	@Override
	public void setMasonInspectorFactory(itfEntityInspectorFactory poMasonInspector){
		((clsMobileObject2D)moPhysicalObject2D).setInspectorFactory(poMasonInspector);
	}
	/**
	 * @return the moMobile
	 */
	@Override
	public clsMobileObject2D getMobileObject2D() {
		return (clsMobileObject2D)moPhysicalObject2D;
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
