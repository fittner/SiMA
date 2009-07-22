/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import enums.eEntityType;
import bw.utils.container.clsConfigMap;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Angle;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.util.clsPose;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsMobile extends clsEntity {
	
	private int mnHolders; // number of bubles which picked-up and carry this mobile entity 
	private double mrDefaultCoeffFriction = 0.5; //0.5
	private double mrDefaultStaticFriction = 0.2; //0.2
	private double mrDefaultRestitution = 1.0; //1.0

	protected clsInventory moInventory;
	
	public clsMobile(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double prMass,  clsConfigMap poConfig) {
		super(pnId, clsMobile.getFinalConfig(poConfig));

		setEntityInventory();

		applyConfig();
		
		mnHolders = 0;
		
		if(this.meEntityType.equals(eEntityType.REMOTEBOT)) 
			initPhysicalObject2D(new clsPose(poPose.getPosition(), new Angle(0d)), poStartingVelocity, poShape, prMass);
		else
			initPhysicalObject2D(poPose, poStartingVelocity, poShape, prMass);
	}
	
	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
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
