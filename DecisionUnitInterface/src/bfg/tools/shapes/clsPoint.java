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

  private static final long serialVersionUID = -433673031048481304L;

  public double mrX;
  public double mrY;

  public clsPoint() {
    mrX = 0;
    mrY = 0;
  }
  public clsPoint(double prX, double prY) {
    mrX = prX;
    mrY = prY;
  }
  public clsPoint(clsPoint poPoint) {
    mrX = poPoint.mrX;
    mrY = poPoint.mrY;
  }


  @Override
  public String toString() {
    NumberFormat nf = java.text.NumberFormat.getInstance( );
    nf.setMinimumFractionDigits( 3 );
    nf.setMaximumFractionDigits( 3 );

    return "("+nf.format(mrX)+"/"+nf.format(mrY)+")";
  }

  public double distance() {
    return Math.sqrt(Math.pow(mrX,2) + Math.pow(mrY,2));
  }

  public double distance(clsPoint poPoint) {
    return Math.sqrt(Math.pow((mrX-poPoint.mrX),2) + Math.pow((mrY-poPoint.mrY),2));
  }

  public double distance(double prX, double prY) {
    return Math.sqrt(Math.pow((mrX-prX),2) + Math.pow((mrY-prY),2));
  }

  public void set(double prX, double prY) {
    mrX = prX;
    mrY = prY;
  }

  public void set(clsPoint poPoint) {
    mrX = poPoint.mrX;
    mrY = poPoint.mrY;
  }

  public void add(double prX, double prY) {
    mrX += prX;
    mrY += prY;
  }

  public void add(clsPoint poPoint) {
    add(poPoint.mrX, poPoint.mrY);
  }

  public void substract(double prX, double prY) {
    mrX -= prX;
    mrY -= prY;
  }

  public void substract(clsPoint poPoint) {
    substract(poPoint.mrX, poPoint.mrY);
  }

  public void scalarMult(double prScalar) {
    mrX *= prScalar;
    mrY *= prScalar;
  }

  public clsAngle angleToPoint(double prX, double prY) {
    double dX = prX - mrX;
    double dY = prY - mrY;

    clsAngle alpha = new clsAngle(Math.atan2(dY, dX));

    return alpha;
  }

  public clsAngle angleToPoint(clsPoint poPoint) {
    return angleToPoint(poPoint.mrX, poPoint.mrY);
  }

  public void normalize() {
    double length = distance();
    scalarMult(1/length);
  }

  public void blur(double prSigma) {
//    mrX = (double)DistrGaussian.sample(prSigma, mrX);
//    mrY = (double)DistrGaussian.sample(prSigma, mrY);
  }
};
