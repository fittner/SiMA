// File clsAngle.java
// December 02, 2005
//

// Belongs to package
package bfg.tools.shapes;

// Imports
import java.io.Serializable;

//langr --> import comment
//import com.xj.random.DistrGaussian;
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
public class clsAngle implements Serializable { //extends clsCloneable
  public double mrAlpha;

  public clsAngle() {
    mrAlpha = 0;
  }
  public clsAngle(double prAlpha) {
    mrAlpha = prAlpha;
    normalize(true);
  }
  public clsAngle(clsAngle poAngle) {
    mrAlpha = poAngle.mrAlpha;
  }

  @Override
  public String toString() {
//    NumberFormat nf = java.text.NumberFormat.getInstance( );
//    nf.setMinimumFractionDigits( 3 );
//    nf.setMaximumFractionDigits( 3 );

//    return "@"+nf.format(mrAlpha);
//    return "@"+nf.format(getDegree());
	  return "@"+getDegree();
  }

  public void set(double prAlpha) {
    mrAlpha = prAlpha;
    normalize(true);
  }

  public void set(clsAngle poAngle) {
    mrAlpha = poAngle.mrAlpha;
  }
  public void setDegree(double prAlpha) {
    mrAlpha = prAlpha*Math.PI/180.0d;
  }


  public void add(double prAlpha) {
    mrAlpha+=prAlpha;
    normalize(true);
  }
  public void add(clsAngle poAngle) {
    add(poAngle.mrAlpha);
  }

  public void substract(double prAlpha) {
    mrAlpha-=prAlpha;
    normalize(true);
  }
  public void substract(clsAngle poAngle) {
    substract(poAngle.mrAlpha);
  }

  public boolean angleEqualTolerance(clsAngle poAngle, double prAngleTolerance) {
    double cPI2 = 2.0d*Math.PI;  

    double rTemp = mrAlpha - poAngle.mrAlpha;
    
    rTemp = getNormalizedAngle(rTemp, true, cPI2);
    rTemp = Math.abs(rTemp);

    return (rTemp < prAngleTolerance);
  }

  public double getShortestSide() {
    double rResult = getNormalizedAngle(mrAlpha, true, Math.PI);
    return rResult;
  }

  public static double getNormalizedAngle(double prAlpha) {
    return getNormalizedAngle(prAlpha, false, 2.0d*Math.PI);
  }

  public static double getNormalizedAngle(double prAlpha, boolean pnPlusMinus, double prMaxValue) {
    while (prAlpha>=prMaxValue) {
      prAlpha-=2*Math.PI;
    }
    while (prAlpha<-prMaxValue) {
      prAlpha+=2*Math.PI;
    }
    while (prAlpha<0 && !pnPlusMinus) {
      prAlpha+=2*Math.PI;
    }

    return prAlpha;
  }

  public void normalize(boolean pnPlusMinus) {
    mrAlpha = clsAngle.getNormalizedAngle(mrAlpha, pnPlusMinus, 2.0d*Math.PI);
  }

  public void blur(double prSigma) {
    //mrAlpha = (double)DistrGaussian.sample(prSigma, mrAlpha);

    normalize(true);
  }

  public static double getDegree(double prAlpha) {
    return prAlpha*180.0d/Math.PI;
  }
  public double getDegree() {
    return mrAlpha*180.0d/Math.PI;
  }
};
