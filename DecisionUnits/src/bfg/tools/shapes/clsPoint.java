// File clsPoint.java
// December 02, 2005
//

// Belongs to package
package bfg.tools.shapes;

// Imports
import java.text.NumberFormat;
import java.io.Serializable;
//import pkgTools.clsCloneable;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsPoint implements Serializable {
  public float mrX;
  public float mrY;

  public clsPoint() {
    mrX = 0;
    mrY = 0;
  }
  public clsPoint(float prX, float prY) {
    mrX = prX;
    mrY = prY;
  }
  public clsPoint(clsPoint poPoint) {
    mrX = poPoint.mrX;
    mrY = poPoint.mrY;
  }


  public String toString() {
    NumberFormat nf = java.text.NumberFormat.getInstance( );
    nf.setMinimumFractionDigits( 3 );
    nf.setMaximumFractionDigits( 3 );

    return "("+nf.format(mrX)+"/"+nf.format(mrY)+")";
  }

  public float distance() {
    return (float)(Math.sqrt(Math.pow(mrX,2) + Math.pow(mrY,2)));
  }

  public float distance(clsPoint poPoint) {
    return (float)(Math.sqrt(Math.pow((mrX-poPoint.mrX),2) + Math.pow((mrY-poPoint.mrY),2)));
  }

  public float distance(float prX, float prY) {
    return (float)(Math.sqrt(Math.pow((mrX-prX),2) + Math.pow((mrY-prY),2)));
  }

  public void set(float prX, float prY) {
    mrX = prX;
    mrY = prY;
  }

  public void set(clsPoint poPoint) {
    mrX = poPoint.mrX;
    mrY = poPoint.mrY;
  }

  public void add(float prX, float prY) {
    mrX += prX;
    mrY += prY;
  }

  public void add(clsPoint poPoint) {
    add(poPoint.mrX, poPoint.mrY);
  }

  public void substract(float prX, float prY) {
    mrX -= prX;
    mrY -= prY;
  }

  public void substract(clsPoint poPoint) {
    substract(poPoint.mrX, poPoint.mrY);
  }

  public void scalarMult(float prScalar) {
    mrX *= prScalar;
    mrY *= prScalar;
  }

  public clsAngle angleToPoint(float prX, float prY) {
    float dX = prX - mrX;
    float dY = prY - mrY;

    clsAngle alpha = new clsAngle((float)Math.atan2(dY, dX));

    return alpha;
  }

  public clsAngle angleToPoint(clsPoint poPoint) {
    return angleToPoint(poPoint.mrX, poPoint.mrY);
  }

  public void normalize() {
    float length = distance();
    scalarMult(1/length);
  }

  public void blur(float prSigma) {
//    mrX = (float)DistrGaussian.sample(prSigma, mrX);
//    mrY = (float)DistrGaussian.sample(prSigma, mrY);
  }
};
