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
import sim.physics2D.util.Double2D;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public abstract class clsMobile extends clsEntity {
	
	/**
	 * if you use this constructor, you have to call getMobile().finalizeSetup() manually!
	 */
	public clsMobile() {
		super();
		moPhysicalObject2D = new clsMobileObject2D(this);
	}
	
	public clsMobile(Double2D poPosition, Double2D poStartingVelocity, Shape poShape, double poMass, int pnId) {
		super();
		clsMobileObject2D oMobile = new clsMobileObject2D(this);
		moPhysicalObject2D = oMobile;
		
		oMobile.setPose(poPosition, new Angle(0));
		oMobile.setVelocity(poStartingVelocity);
		
		setPosition( new sim.util.Double2D(poPosition.x, poPosition.y) );
		setShape(poShape, poMass);
		setCoefficients(.5, 0, 1); //default coefficients
		
		oMobile.finalizeSetup();
		
		setId(pnId);
	}
	

	/**
	 * @return the moMobile
	 */
	public clsMobileObject2D getMobileObject2D() {
		return (clsMobileObject2D)moPhysicalObject2D;
	}
	
	public Double2D getPosition() {
		return getMobileObject2D().getPosition();
	}
	
	public Double2D getVelocity() {
		return getMobileObject2D().getVelocity();
	}
}
