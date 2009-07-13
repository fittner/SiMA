/**
 * @author deutsch
 * 23.04.2009, 16:03:42
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package ARSsim.physics2D.util;

import java.text.NumberFormat;

import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2009, 16:03:42
 * 
 */
public class clsPolarcoordinate {
	  public double mrLength;
	  public Angle moAzimuth;

	  public clsPolarcoordinate(double prLength, double prAzimuth) {
	    mrLength = prLength;
	    moAzimuth = new Angle(prAzimuth);
	  }
	  public clsPolarcoordinate(clsPolarcoordinate poPolarcoordinate) {
	    mrLength = poPolarcoordinate.mrLength;
	    moAzimuth = new Angle(poPolarcoordinate.moAzimuth.radians);
	  }
	  public clsPolarcoordinate(Double2D poFrom, Double2D poTo) {
		Double2D oTemp = diffAtoB(poFrom, poTo);
	    mrLength = oTemp.length();
	    moAzimuth = angleToPoint(oTemp);
	  }
	  public clsPolarcoordinate(Double2D poTo) {
		mrLength = poTo.length();
	    moAzimuth = angleToPoint(poTo);
	  }
	  
	  private static Double2D diffAtoB(Double2D poFrom, Double2D poTo) {
		  Double2D oTemp = new Double2D(poTo.x, poTo.x);
		  oTemp.subtract(poFrom);
		  
		  return oTemp;
	  }
	  
	  private static Angle angleToPoint(Double2D poTo) {
	    double alpha = Math.atan2(poTo.y, poTo.x);
	    return new Angle(alpha);
	  }
	  
	  @Override
	  public String toString() {
		    NumberFormat nf = java.text.NumberFormat.getInstance( );
		    nf.setMinimumFractionDigits( 1 );
		    nf.setMaximumFractionDigits( 3 );		  
	    return "[r:"+nf.format(mrLength)+"|a:"+nf.format(moAzimuth.radians)+"]";
	  }


	  public void rotateBy(Angle poAngle) {
		  moAzimuth = moAzimuth.add(poAngle);
	  }
	  
	  public void rotateBy(Angle poAngle, boolean pnInvertSign) {
		  if (pnInvertSign) {
			  moAzimuth = moAzimuth.add(-poAngle.radians);			  
		  } else {
			  rotateBy(poAngle);
		  }

	  }
}
