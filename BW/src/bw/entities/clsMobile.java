/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

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
	
	private double mrDefaultCoeffFriction = 0.5; //0.5
	private double mrDefaultStaticFriction = 0.2; //0.2
	private double mrDefaultRestitution = 1.0; //1.0

	
	public clsMobile(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, Shape poShape, double prMass) {
		super(pnId);
		
		if(this.meEntityType.equals(meEntityType.REMOTEBOT)) 
			initPhysicalObject2D(new clsPose(poPose.getPosition(), new Angle(0d)), poStartingVelocity, poShape, prMass);
		else
			initPhysicalObject2D(poPose, poStartingVelocity, poShape, prMass);
	}
	
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
	
	public sim.physics2D.util.Double2D getPosition() {
		return getMobileObject2D().getPosition();
	}
	
	public sim.physics2D.util.Double2D getVelocity() {
		return getMobileObject2D().getVelocity();
	}
	
	public void setVelocity(sim.physics2D.util.Double2D poVelocity) {
		getMobileObject2D().setVelocity(poVelocity);
	}
}
