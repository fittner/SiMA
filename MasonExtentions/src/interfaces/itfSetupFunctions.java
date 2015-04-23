/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package interfaces;

import java.awt.image.BufferedImage;

import physical2d.physicalObject.datatypes.eFacialExpression;
import physical2d.physicalObject.datatypes.eSpeechExpression;
import sim.physics2D.shape.Shape;
import singeltons.eImages;
import tools.clsPose;

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
	public void setSpeechExpressionOverlayImage(eSpeechExpression poOverlay);
	public void setThoughtExpressionOverlayImage(eSpeechExpression poOverlay);
	public void setCarriedItem(BufferedImage mnCarriedItem);
	public void setLifeValue(double value);
	
	public clsPose getPose();
    public Shape getShape();
    public void setMass(double mass);
    public double getMass();
	
	
}
