/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.physics2D.physicalObject;

import sim.physics2D.shape.Shape;

/**
 * These interface contains the necessary functions to place the clsMobile- 
 * or clsStaticObject2D contained by the clsEntity in the environment. 
 * 
 * Since the clsEntity only contains the base class of both (=physicalObject2D)
 * the implemented interface enables a direct access of these functions  anyway.
 * 
 * @author langr
 * 
 */
public interface itfSetupFunctions {
	public void setPosition(sim.util.Double2D pos);
	public void setShape(Shape poShape, double poMass);
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution);
	
	/**
	 * mandatory for a valid Physics2D-object
	 *
	 */
	public void finalizeSetup();
}
