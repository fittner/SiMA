// File clsAngle.java
// December 02, 2005
//

// Belongs to package
package bfg.tools.shapes;

// Imports
import java.text.NumberFormat;
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
  public float mrAlpha;

  public clsAngle() {
    mrAlpha = 0;
  }
  public clsAngle(float prAlpha) {
    mrAlpha = prAlpha;
    normalize(true);
  }
  public clsAngle(clsAngle poAngle) {
    mrAlpha = poAngle.mrAlpha;
  }

  public String toString() {
    NumberFormat nf = java.text.NumberFormat.getInstance( );
    nf.setMinimumFractionDigits( 3 );
    nf.setMaximumFractionDigits( 3 );

//    return "@"+nf.format(mrAlpha);
    return "@"+nf.format(getDegree());
  }

  public void set(float prAlpha) {
    mrAlpha = prAlpha;
    normalize(true);
  }

  public void set(clsAngle poAngle) {
    mrAlpha = poAngle.mrAlpha;
  }
  public void setDegree(float prAlpha) {
    mrAlpha = prAlpha*(float)Math.PI/180;
  }


  public void add(float prAlpha) {
    mrAlpha+=prAlpha;
    normalize(true);
  }
  public void add(clsAngle poAngle) {
    add(poAngle.mrAlpha);
  }

  public void substract(float prAlpha) {
    mrAlpha-=prAlpha;
    normalize(true);
  }
  public void substract(clsAngle poAngle) {
    substract(poAngle.mrAlpha);
  }

  public boolean angleEqualTolerance(clsAngle poAngle, float prAngleTolerance) {
    float cPI2 = (float)(2*Math.PI);  

    float rTemp = mrAlpha - poAngle.mrAlpha;
    
    rTemp = getNormalizedAngle(rTemp, true, cPI2);
    rTemp = Math.abs(rTemp);

    return (rTemp < prAngleTolerance);
  }

  public float getShortestSide() {
    float rResult = getNormalizedAngle(mrAlpha, true, (float)Math.PI);
    return rResult;
  }

  public static float getNormalizedAngle(float prAlpha) {
    return getNormalizedAngle(prAlpha, false, 2*(float)Math.PI);
  }

  public static float getNormalizedAngle(float prAlpha, boolean pnPlusMinus, float prMaxValue) {
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
    mrAlpha = clsAngle.getNormalizedAngle(mrAlpha, pnPlusMinus, 2*(float)Math.PI);
  }

  public void blur(float prSigma) {
    //mrAlpha = (float)DistrGaussian.sample(prSigma, mrAlpha);

    normalize(true);
  }

  public static float getDegree(float prAlpha) {
    return prAlpha*180/(float)Math.PI;
  }
  public float getDegree() {
    return mrAlpha*180/(float)Math.PI;
  }
};
