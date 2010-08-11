/**
 * clsMinu1to1.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 17.10.2009, 19:20:45
 */
package pa.datatypes;

import java.io.Serializable;

import pa.bfg.tools.clsCloneable;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 19:20:45
 * 
 */
@Deprecated
public class clsMinus1to1 extends clsCloneable implements Serializable {

	private static final long serialVersionUID = 4136585154085106000L;

	  double mrValue = 0;
	  static final double mrMinValue = -1;
	  static final double mrMaxValue = 1;

	  public clsMinus1to1() {
	  }

	  public clsMinus1to1(clsMinus1to1 poValue) {
	    set(poValue);
	  }

	    public clsMinus1to1(double prValue) {
		  set(prValue);
	  }  
	  
	  public clsMinus1to1(Double prValue) {
		  set(prValue.doubleValue());
	  }
	 
	  public double set(clsMinus1to1 poValue) {
	    return set(poValue.get());
	  }
	  public double set(double prValue) {
	    if (prValue > mrMaxValue) {
	      mrValue = mrMaxValue;
	    } else if (prValue < mrMinValue) {
	      mrValue = mrMinValue;
	    } else {
	      mrValue = prValue;
	    }

	    return mrValue;
	  }

	  public double get() {
	    return mrValue;
	  }
	  
	  @Override
	  public String toString() {
	    return ""+mrValue;
	  }
}