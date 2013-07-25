/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.physics2D.physicalObject;

import java.awt.image.BufferedImage;

import du.enums.eFacialExpression;
import bw.factories.eImages;
import ARSsim.physics2D.util.clsPose;
import sim.physics2D.shape.Shape;

/**
 * These interface contains the necessary functions to place the clsMobile- 
 * or clsStaticObject2D contained by the clsEntity in the environment. 
 * 
 * Since the clsEntity only contains the base class of both (=physicalObject2D)
 * the implemented interface enables a direct access of these functions  anyway.
 * 
 * Coefficients: Only mobile objects support friction and staticFriction. Restitution
 *     is supported by both, mobile and stationary. When using setCoefficients on
 *     stationary objects, specify NaN for friction and staticFriction (EH)
 * 
 * @author langr
 * 
 */
public interface itfSetupFunctions {
	public void setPose(clsPose poPose);
	public void setShape(Shape poShape, double poMass);
	public void setCoefficients(double poFriction, double poStaticFriction, double poRestitution);
	public void setOverlayImage(eImages poOverlay);
	public void setFacialExpressionOverlayImage(eFacialExpression poOverlay);
	public void setCarriedItem(BufferedImage poOverlay);
	
	public clsPose getPose();
    public Shape getShape();
    public void setMass(double mass);
    public double getMass();
}
