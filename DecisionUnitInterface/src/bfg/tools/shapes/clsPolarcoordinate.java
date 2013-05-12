// File clsPolarcoordinate.java
// December 02, 2005
//

// Belongs to package
package bfg.tools.shapes;

// Imports
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
public class clsPolarcoordinate implements Serializable { // extends clsCloneable
  
private static final long serialVersionUID = -3884035044687816319L;

public double mrLength;
  public clsAngle moAzimuth;

  public clsPolarcoordinate() {
    mrLength = 0;
    moAzimuth = new clsAngle();
  }
  public clsPolarcoordinate(double prLength, double prAzimuth) {
    mrLength = prLength;
    moAzimuth = new clsAngle(prAzimuth);
  }
  public clsPolarcoordinate(double prLength, clsAngle poAzimuth) {
    mrLength = prLength;
    moAzimuth = new clsAngle(poAzimuth);
  }
  public clsPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
    mrLength = poPolarcoordinate.mrLength;
    moAzimuth = new clsAngle(poPolarcoordinate.moAzimuth);
  }
  public clsPolarcoordinate(clsPoint poFrom, clsPoint poTo) {
    mrLength = poFrom.distance(poTo);
    moAzimuth = poFrom.angleToPoint(poTo);
  }
  public clsPolarcoordinate(clsPoint poTo) {
    clsPoint oFrom = new clsPoint(0,0);

    mrLength = oFrom.distance(poTo);
    moAzimuth = oFrom.angleToPoint(poTo);
  }
  @Override
  public String toString() {
	  //    NumberFormat nf = java.text.NumberFormat.getInstance( );
	  //    nf.setMinimumFractionDigits( 3 );
	  //    nf.setMaximumFractionDigits( 3 );

	  //    return "r"+nf.format(mrLength)+"/"+moAzimuth.toString();
    return "r"+mrLength+moAzimuth.toString();
  }

  public void set(clsPoint poFrom, clsPoint poTo) {
    mrLength = poFrom.distance(poTo);
    moAzimuth = poFrom.angleToPoint(poTo);
  }
  public void set(double prLength, double prAzimuth) {
    mrLength = prLength;
    moAzimuth.set(prAzimuth);
  }
  public void set(double prLength, clsAngle poAzimuth) {
    mrLength = prLength;
    moAzimuth.set(poAzimuth);
  }
  public void set(clsPolarcoordinate poPolarcoordinate) {
    mrLength = poPolarcoordinate.mrLength;
    moAzimuth.set(poPolarcoordinate.moAzimuth);
  }
  public void set(clsPoint poTo) {
    clsPoint oFrom = new clsPoint(0,0);

    mrLength = oFrom.distance(poTo);
    moAzimuth = oFrom.angleToPoint(poTo);
  }

  public void blur(double prQualityDistance, double prQualityAzimuth) {
    moAzimuth.blur(prQualityAzimuth);

    //mrLength = (double)DistrGaussian.sample(prQualityDistance, mrLength);
  }


  public void rotateBy(double prAlpha) {
    moAzimuth.add(prAlpha);
  }
  public void rotateBy(clsAngle poAngle) {
    moAzimuth.add(poAngle);
  }
  
  public clsPoint getVector() {
    clsPoint oResult = new clsPoint(mrLength, 0);
    
    double rAngle = clsAngle.getNormalizedAngle( moAzimuth.mrAlpha, false, 2.0d*Math.PI);

    oResult.mrX = Math.cos(rAngle) * mrLength;

// cos takes care of the correct signum ...
//    if (moAzimuth.mrAlpha > Math.PI/2 && moAzimuth.mrAlpha < 3*Math.PI/2 ) {
//      oResult.mrX = -oResult.mrX;
//    }
    
    oResult.mrY = Math.sqrt(Math.pow(mrLength,2) - Math.pow(oResult.mrX,2));
    if (rAngle > Math.PI) {
      oResult.mrY = -oResult.mrY;
    }

    return oResult;
  }
};
